const {
    getPessoaByTelegram,
    getPessoaByIdent,
    createPessoaByBot,
    updatePessoaByBot,
    removeTelegramIdPessoaByBot,
} = require("../../../webhooks/pessoa/pessoaWebhooks");
const {
    formatDateReverse,
    formatDate,
    formatCelular,
    formatCPF,
} = require("../../../utils/formatters");
const {validaCelular, isValidBirthDay, validaCpf} = require("../../../utils/validators");

async function getPessoaByTelegramId(msg, bot) {
    try {
        let telegramId = msg?.from?.id.toString();

        let response = await getPessoaByTelegram(telegramId, bot);
        return response.data;
    } catch (error) {
        return null;
    }
}

async function getPessoa(
    parameter,
    msg,
    bot,
    listaOpcoesComandos,
    reincidente
) {
    return new Promise(async (resolve, reject) => {
        try {
            parameter = parameter.replace(/\D/g, "");
            let ident = parameter;
            let pessoa;

            let response = await getPessoaByIdent(ident, bot);

            if (response && response.data.length > 0 && response.data[0]?.nome) {
                pessoa = response.data[0];
            }

            let pessoaTelegram = await getPessoaByTelegramId(msg, bot);

            if (pessoa?.nome && pessoa?.id) {
                if (!pessoa.telegramId && !pessoaTelegram?.telegramId) {
                    await updatePessoaWithTelegramId(msg, bot, pessoa);
                }
                listaOpcoesComandos(msg, bot, listaOpcoesComandos, pessoa);
            }
            if (!pessoa) {
                makePessoaByBot(parameter, msg, bot, listaOpcoesComandos, reincidente);
            }
        } catch (error) {
            reject(error);
        }
    });
}

async function makePessoaByBot(
    parameter,
    msg,
    bot,
    listaOpcoesComandos,
    reincidente
) {
    let nome = await makeNomePessoa(msg, bot, reincidente);
    let dataNascimento = await makeDataNascimentoPessoa(msg, bot);
    dataNascimento = formatDateReverse(dataNascimento);
    let cpf;
    let celular;

    if (reincidente) {
        celular = await makeCelularPessoa(msg, bot);
        cpf = await makeCpfPessoa(msg, bot);
    }
    if (!reincidente) {
        if (validaCpf(parameter)) {
            cpf = parameter;
            celular = await makeCelularPessoa(msg, bot);
        }
        if (validaCelular(parameter)) {
            cpf = await makeCpfPessoa(msg, bot);
            celular = parameter;
        }
    }

    const pessoa = {
        nome: nome,
        dataNascimento: dataNascimento,
        celular: celular,
        cpf: cpf,
    };

    let pessoaTelegram = await getPessoaByTelegramId(msg, bot);

    if (pessoa?.nome && pessoa?.id) {
        if (!pessoa.telegramId && !pessoaTelegram?.telegramId) {
            let vincularTelegram = await updatePessoaWithTelegramId(msg, bot, pessoa);
            if (vincularTelegram) {
                pessoa.telegramId = msg?.from?.id;
            }
        }
    }

    let confirmarPessoaCadastro = await confirmarPessoa(
        msg,
        bot,
        listaOpcoesComandos,
        pessoa,
        parameter
    );

    if (confirmarPessoaCadastro) {
        try {
            let response = await createPessoa(
                parameter,
                msg,
                bot,
                listaOpcoesComandos,
                reincidente,
                pessoa
            );
            let newPessoa = response.data;
            await bot.sendMessage(msg.chat.id, response.messages[0]);
            listaOpcoesComandos(msg, bot, listaOpcoesComandos, newPessoa);
        } catch (error) {
        }
    }
}

async function makeNomePessoa(msg, bot, reincidente) {
    bot.removeTextListener(/^(.*)$/);
    let message = "";

    if (!reincidente) {
        message +=
            "Não foi possível localizar o cadastro da pessoa em nossos sistemas.\n";
    }

    message +=
        "Por favor, digite o nome completo da pessoa que deseja atendimento:";

    await bot.sendMessage(msg.chat.id, message);

    let nomeRegex = /^[a-zA-Z\u00C0-\u00FF ]+$/;

    let listener = /^(.*)$/;

    return new Promise((resolve) => {
        bot.onText(listener, async (msg, match) => {
            const userInput = match[1].replace(/\d/g, "");
            if (userInput && nomeRegex.test(userInput) && userInput.length >= 3) {
                bot.removeTextListener(listener);
                resolve(userInput);
            }
            if (userInput && nomeRegex.test(userInput) && userInput.length < 3) {
                let errorMessage =
                    "O nome deve ter pelo menos 3 caracteres. Por favor, digite novamente.";
                await bot.sendMessage(msg.chat.id, errorMessage);
                makeNomePessoa(msg, bot, true).then(resolve);
            }
        });
    });
}

async function makeDataNascimentoPessoa(msg, bot) {
    bot.removeTextListener(/^(.*)$/);
    let message =
        "Por favor, digite a data de nascimento da pessoa que deseja atendimento, seguindo o seguinte formato: DD/MM/AAAA.\n\nExemplo: 01/01/2000\n\n";

    await bot.sendMessage(msg.chat.id, message);

    const listener = /^(.*)$/;
    let dataRegex = /^\d{2}\/\d{2}\/\d{4}$/;

    return new Promise((resolve) => {
        bot.onText(listener, async (msg, match) => {
            bot.removeTextListener(listener);
            bot.removeTextListener(dataRegex);
            if (isValidBirthDay(match[0])) {
                const dataNascimento = match[0];
                resolve(dataNascimento);
            }
            if (!isValidBirthDay(match[0])) {
                await bot.sendMessage(
                    msg.chat.id,
                    "A data digitada não é válida.\nPor favor, digite a data no formato abaixo.\n\nDD/MM/AAAA\nExemplo: 01/01/2000"
                );
                resolve(makeDataNascimentoPessoa(msg, bot));
            }
        });
    });
}

async function makeCpfPessoa(msg, bot) {
    bot.removeTextListener(/^(.*)$/);

    let message =
        "Por favor, digite o número do CPF da pessoa que deseja atendimento.\n\nExemplo: 012.456.789-00 ou 01234567899";

    bot.sendMessage(msg.chat.id, message);

    const listener = /^(?:\d{11})$/;
    const cpfPattern = /^(?:\d{11})$/;

    return new Promise((resolve) => {
        bot.onText(listener, async (msg, match) => {
            bot.removeTextListener(listener);
            bot.removeTextListener(cpfPattern);

            match[0] = match[0].replace(/\D/g, "");

            if (match && cpfPattern.test(match[0])) {
                let cpf = match[0].replace(/\D/g, "");
                resolve(cpf);
            }
            let message =
                "O CPF informado não é válido.\nPor favor, digite o CPF no formato 012.456.789-00 ou 01234567899.";
            await bot.sendMessage(msg.chat.id, message);
            const cpf = await makeCpfPessoa(msg, bot);
            resolve(cpf);
        });
    });
}

async function makeCelularPessoa(msg, bot) {
    bot.removeTextListener(/^(.*)$/);
    const listener = /^(?:\d{11})$/;
    const celularPattern = /^(?:\d{11})$/;
    let message =
        "Por favor, digite o celular da pessoa que deseja atendimento, seguindo o formato DDD + número.\n\nExemplo: (45) 9 9123-4567 ou 45991234567";

    bot.removeTextListener(/^(.*)$/);
    bot.sendMessage(msg.chat.id, message);
    return new Promise((resolve) => {
        bot.onText(listener, async (msg, match) => {
            bot.removeTextListener(listener);
            bot.removeTextListener(celularPattern);

            match[0] = match[0].replace(/\D/g, "");

            if (match && match[0]) {
                let celular = match[0].replace(/\D/g, "");
                resolve(celular);
            }
            let message =
                "O celular não é válido.\nPor favor, digite o *número do celular* no formato *DDD + número*.";
            await bot.sendMessage(msg.chat.id, message);
            resolve(makeCelularPessoa(msg, bot));
        });
    });
}

async function confirmarPessoa(
    msg,
    bot,
    listaOpcoesComandos,
    pessoa,
    parameter
) {
    if (pessoa?.nome) {
        const opcoes = [
            {id: 1, nome: "Confirmar"},
            {id: 2, nome: "Corrigir as informações"},
            {id: 3, nome: "Cancelar"},
        ];

        let message = `Por favor, verifique as seguintes informações antes de confirmar o cadastro:\n\n`;

        message +=
            `Nome da pessoa: ${pessoa.nome}\n` +
            `Data de Nascimento: ${formatDate(pessoa.dataNascimento)}\n` +
            `Contato: ${formatCelular(pessoa.celular)}\n` +
            `CPF: ${formatCPF(pessoa.cpf)}\n`;

        const keyboard = opcoes.map((opcao) => [opcao.nome]);

        const replyMarkup = {
            keyboard,
            one_time_keyboard: true,
            resize_keyboard: true,
        };

        const opcoesMarkup = {
            reply_markup: JSON.stringify(replyMarkup),
        };

        bot.sendMessage(msg.chat.id, message, opcoesMarkup);

        bot.removeTextListener(/^(.*)$/);

        return new Promise((resolve) => {
            bot.onText(/^(.*)$/, async (msg, match) => {
                const selectedOptionLabel = match[1];
                switch (selectedOptionLabel) {
                    case "Confirmar":
                        bot.removeTextListener(/^(.*)$/);
                        return resolve(true);
                    case "Corrigir as informações":
                        await bot.sendMessage(
                            msg.chat.id,
                            "Vamos corrigir as informações."
                        );
                        bot.removeTextListener(/^(.*)$/);
                        return getPessoa(parameter, msg, bot, listaOpcoesComandos, true);
                    case "Cancelar":
                        bot.sendMessage(
                            msg.chat.id,
                            "A BlackBelt Fisio agradece ao contato e a preferência!"
                        );
                    default:
                        break;
                }
            });
        });
    }
}

async function createPessoa(
    parameter,
    msg,
    bot,
    listaOpcoesComandos,
    reincidente,
    pPessoa
) {
    try {
        let response = await createPessoaByBot(pPessoa, bot);
        if (response && response.data) {
            return response.data;
        }
    } catch (error) {
        await bot.sendMessage(msg.chat.id, error.response.data.messages[0]);
        makePessoaByBot(parameter, msg, bot, listaOpcoesComandos, true);
    }
}

async function updatePessoaWithTelegramId(msg, bot, pessoa) {
    const opcoes = [
        {id: 1, nome: "SIM"},
        {id: 2, nome: "NÃO"},
    ];

    let vincular;

    let message =
        "Deseja vincular essa conta do Telegram ao cadastro da pessoa: " +
        pessoa.nome +
        "?";

    const keyboard = opcoes.map((opcao) => [opcao.nome]);

    const replyMarkup = {
        keyboard,
        one_time_keyboard: true,
        resize_keyboard: true,
    };

    bot.sendMessage(msg.chat.id, message, {
        reply_markup: JSON.stringify(replyMarkup),
    });

    return new Promise(async (resolve) => {
        const callback = async (msg, match) => {
            const selectedOptionLabel = match[1];

            switch (selectedOptionLabel) {
                case "SIM":
                    pessoa.telegramId = msg?.from?.id;
                    let telegramId = msg?.from?.id.toString();
                    let response = await updatePessoaByBot(pessoa, telegramId, bot);
                    vincular = true;
                    await bot.sendMessage(msg.chat.id, response.data.messages[0]);
                    break;
                case "NÃO":
                    vincular = false;
                    break;
                default:
                    break;
            }
            bot.removeTextListener(callback);
            resolve(vincular);
        };

        bot.onText(/^(.*)$/, callback);
    });
}

async function updatePessoaRemoveTelegramId(
    msg,
    bot,
    pessoa,
    listaOpcoesComandos
) {
    const opcoes = [
        {id: 1, nome: "Remover"},
        {id: 2, nome: "Cancelar"},
    ];

    let vincular;

    let message =
        "Deseja remover o vínculo dessa conta do Telegram ao cadastro da pessoa: " +
        pessoa.nome +
        "?";

    const keyboard = opcoes.map((opcao) => [opcao.nome]);

    const replyMarkup = {
        keyboard,
        one_time_keyboard: true,
        resize_keyboard: true,
    };

    bot.sendMessage(msg.chat.id, message, {
        reply_markup: JSON.stringify(replyMarkup),
    });

    return new Promise(async (resolve) => {
        const callback = async (msg, match) => {
            const selectedOptionLabel = match[1];

            switch (selectedOptionLabel) {
                case "Remover":
                    pessoa.telegramId = msg?.from?.id;
                    let telegramId = msg?.from?.id.toString();
                    let response = await removeTelegramIdPessoaByBot(
                        pessoa,
                        telegramId,
                        bot
                    );
                    vincular = true;
                    await bot.sendMessage(msg.chat.id, response.data.messages[0]);
                    break;
                case "Cancelar":
                    vincular = false;
                    break;
                default:
                    break;
            }
            bot.removeTextListener(callback);
            resolve(listaOpcoesComandos(msg, bot, listaOpcoesComandos, pessoa));
        };

        bot.onText(/^(.*)$/, callback);
    });
}

module.exports = {
    getPessoa,
    getPessoaByTelegramId,
    updatePessoaRemoveTelegramId,
};
