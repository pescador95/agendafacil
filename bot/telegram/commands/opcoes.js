require("node-telegram-bot-api");

const {
    getPessoa,
    updatePessoaRemoveTelegramId,
} = require("../../core/handlers/telegram/pessoa/pessoaHandlers");
const {
    montarAgendamento, confirmarAgendamento, marcarAgendamento, selecionarAgendamentoRemarcacao,
    confirmarReagendamento, remarcarAgendamento, listarAgendamentoByPessoa
} = require("../../core/handlers/telegram/agendamento/agendamentoHandlers");

async function iniciarAtendimento(msg, bot, pessoa) {
    let message = `\nBem-vindo ao Agenda Fácil, ${pessoa?.nome}!\n\n`;

    await bot.sendMessage(msg.chat.id, message).then(() => {
        pessoaAtendimento(msg, bot, pessoa);
    });
}

async function listaOpcoesComandos(msg, bot, listaOpcoesComandos, pessoa) {
    bot.removeTextListener(/^(.*)$/);
    const opcoesComandos = [
        {id: 1, nome: "Agendar"},
        {id: 2, nome: "Reagendar"},
        {id: 3, nome: "Listar meus agendamentos"},
        {id: 4, nome: "Atendimento para outra pessoa"},
        {id: 5, nome: "Remover vinculo telegram"},
        {id: 6, nome: "Finalizar atendimento"},
    ];

    if (pessoa.nome && !pessoa.telegramId) {
        const indexToRemove = opcoesComandos.findIndex((opcao) => opcao.id === 5);
        if (indexToRemove !== -1) {
            opcoesComandos.splice(indexToRemove, 1);
        }
    }

    bot.removeTextListener(/^(.*)$/);

    let message =
        "Estou à disposição para ajudar, " +
        pessoa.nome +
        ".\nComo posso ser útil?\n\n";

    let agendamento;
    let oldAgendamento;
    let newAgendamento;
    let confirmar;

    const keyboard = opcoesComandos.map((opcao) => [opcao.nome]);

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

    bot.onText(/^(.*)$/, async (msg, match) => {
        const selectedOptionLabel = match[1];

        switch (selectedOptionLabel) {
            case "Agendar":
                agendamento = await montarAgendamento(
                    msg,
                    bot,
                    pessoa,
                    listaOpcoesComandos,
                    false
                );

                if (agendamento) {
                    confirmar = await confirmarAgendamento(
                        msg,
                        bot,
                        listaOpcoesComandos,
                        agendamento,
                        pessoa
                    );
                }

                if (confirmar) {
                    bot.removeTextListener(/^(.*)$/);
                    newAgendamento = await marcarAgendamento(agendamento, bot, msg);
                    listaOpcoesComandos(msg, bot, listaOpcoesComandos, pessoa);
                }
                break;
            case "Reagendar":
                oldAgendamento = await selecionarAgendamentoRemarcacao(
                    msg,
                    bot,
                    pessoa,
                    listaOpcoesComandos,
                    true
                );

                if (!oldAgendamento) {
                    return listaOpcoesComandos(msg, bot, listaOpcoesComandos, pessoa);
                }

                newAgendamento = await montarAgendamento(
                    msg,
                    bot,
                    pessoa,
                    listaOpcoesComandos,
                    true
                );

                if (oldAgendamento && newAgendamento) {
                    confirmar = await confirmarReagendamento(
                        msg,
                        bot,
                        listaOpcoesComandos,
                        oldAgendamento,
                        newAgendamento,
                        pessoa
                    );
                }

                if (confirmar) {
                    bot.removeTextListener(/^(.*)$/);
                    const agendamentoRemarcado = await remarcarAgendamento(
                        oldAgendamento,
                        newAgendamento,
                        bot,
                        msg
                    );
                    listaOpcoesComandos(msg, bot, listaOpcoesComandos, pessoa);
                }

                break;
            case "Listar meus agendamentos":
                listarAgendamentoByPessoa(msg, bot, pessoa, listaOpcoesComandos, false);
                return;
            case "Remover vinculo telegram":
                bot.removeTextListener(/^(.*)$/);
                updatePessoaRemoveTelegramId(msg, bot, pessoa, listaOpcoesComandos);
                return;
            case "Finalizar atendimento":
                bot.sendMessage(
                    msg.chat.id,
                    "A BlackBelt Fisio agradece ao contato e a preferência!"
                );
                return;
            case "Atendimento para outra pessoa":
                getPessoaAtendimento(msg, bot, false, listaOpcoesComandos);
                return;
            default:
                break;
        }
    });
}

async function pessoaAtendimento(msg, bot, pessoa) {
    const opcoes = [
        {id: 1, nome: "Atendimento para mim"},
        {id: 2, nome: "Atendimento para outra pessoa"},
    ];

    let message = `Para quem você deseja o atendimento?\n\n`;

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

    bot.onText(/^(.*)$/, async (msg, match) => {
        const selectedOptionLabel = match[1];

        switch (selectedOptionLabel) {
            case "Atendimento para mim":
                listaOpcoesComandos(msg, bot, listaOpcoesComandos, pessoa);
                return;
            case "Atendimento para outra pessoa":
                getPessoaAtendimento(msg, bot, false, listaOpcoesComandos);
                return;
            default:
                break;
        }
    });
}

async function getPessoaAtendimento(
    msg,
    bot,
    reincidencia,
    listaOpcoesComandos
) {
    bot.removeTextListener(/^(.*)$/);
    const listener = /^(.*)$/;
    const greetingsPhone = /^(?:\d{11})$/;

    let message = `Por favor,  digite o número do CPF ou telefone  da pessoa que deseja atendimento.\n\nPara telefone, digite o número do celular no formato DDD + número.\nExemplo: (45) 9 9123-4567 ou 45991234567\n\nPara CPF, siga o exemplo abaixo.\nExemplo: 012.456.789-00 ou 012345678900.`;
    let telefone = 0;

    telefone = msg.from.contact?.phone_number;

    if (!telefone) {
        bot.removeTextListener(/^(.*)$/);
        bot.sendMessage(msg.chat.id, message);
    }

    bot.onText(listener, async (matchMsg, match) => {
        bot.removeTextListener(listener);
        try {
            bot.removeTextListener(greetingsPhone);

            match[0] = match[0].replace(/\D/g, "");

            if (match && greetingsPhone.test(match[0])) {
                let ident = match[0];
                await getPessoa(ident, msg, bot, listaOpcoesComandos, reincidencia);
            }
            if (!match) {
                await bot.sendMessage(
                    msg.chat.id,
                    "Desculpe, mas o número informado não possui exatamente 11 dígitos numéricos."
                );
                getPessoaAtendimento(msg, bot, reincidencia, listaOpcoesComandos);
            }
        } catch (error) {
            console.error("Erro ao fazer a solicitação GET:", error.message);
        }
    });
}

module.exports = {listaOpcoesComandos, iniciarAtendimento};
