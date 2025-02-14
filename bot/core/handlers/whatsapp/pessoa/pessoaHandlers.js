const {
    formatDate,
    formatDateReverse,
    formatCPF,
    formatCelular,
} = require("../../../utils/formatters");

const {
    isValidDate,
    validaCpf,
    validaCelular,
} = require("../../../utils/validators");
const {
    getPessoaByIdent,
    createPessoaByBot,
} = require("../../../webhooks/pessoa/pessoaWebhooks");

const {iniciarAtendimento} = require("../../../../whatsapp/commands/opcoes");
const {setState} = require("../../../../whatsapp/config/objetos/setState");
const {setPessoa} = require("../../../../whatsapp/config/objetos/setPessoa");

async function getPessoa(parameter, msg, bot, reincidente) {
    return new Promise(async (resolve, reject) => {
        try {
            parameter = parameter.replace(/\D/g, "");
            let ident = parameter;
            let pessoa;

            let response = await getPessoaByIdent(ident, bot);

            if (response && response.data.length > 0 && response.data[0]?.nome) {
                pessoa = response.data[0];
                setState(msg.from, false, pessoa);
            }

            if (pessoa?.nome && pessoa?.id) {
                iniciarAtendimento(msg, bot, pessoa);
            }
            if (!pessoa) {
                if (validaCpf(parameter)) {
                    cpf = parameter;
                    await setPessoa(msg.from, false, false, cpf);
                }
                if (validaCelular(parameter)) {
                    celular = parameter;
                    await setPessoa(msg.from, false, false, false, celular);
                }
                await makeNomePessoa(msg, bot, reincidente);
            }
        } catch (error) {
            reject(error);
        }
    });
}

async function makePessoaByBot(parameter, msg, bot, reincidente) {
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
        {
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

        let confirmarPessoaCadastro = await confirmarPessoa(
            msg,
            bot,
            pessoa,
            parameter
        );

        if (confirmarPessoaCadastro) {
            try {
                let response = await createPessoa(pessoa, bot);
                let newPessoa = response.data;
                await bot.sendText(msg.from, response.messages[0]);
                iniciarAtendimento(msg, bot, newPessoa);
            } catch (error) {
            }
        }
    }

    async function makeNomePessoa(msg, bot, reincidente) {
        let message = "";

        if (!reincidente) {
            message =
                "Não foi possível localizar o cadastro da pessoa em nossos sistemas.";

            await bot.sendText(msg.from, message);
        }
        await setState(msg.from, "aguardando_makeNomePessoa");
        message =
            "Por favor, digite o *nome completo* da pessoa que deseja atendimento:";
        await bot.sendText(msg.from, message);
    }

    async function makeDataNascimentoPessoa(msg, bot) {
        let message = "";

        message =
            "Por favor, digite a *data de nascimento* da pessoa que deseja atendimento, seguindo o formato DD/MM/AAAA.\n\nExemplo: 01/01/2000";

        await setState(msg.from, "aguardando_makeDataNascimentoPessoa");
        await bot.sendText(msg.from, message);
    }

    async function makeCelularPessoa(msg, bot) {
        let message = "";
        message =
            "Por favor, digite o *celular* da pessoa que deseja atendimento, seguindo o formato DDD + número.\n\nExemplo: (45) 9 9123-4567 ou 45991234567";

        await setState(msg.from, "aguardando_makeCelularPessoa");
        await bot.sendText(msg.from, message);
    }

    async function makeCpfPessoa(msg, bot) {
        let message = "";
        message =
            "Por favor, digite o número do *CPF* da pessoa que deseja atendimento.\n\nExemplo: 012.456.789-00 ou 01234567899";
        await setState(msg.from, "aguardando_makeCpfPessoa");
        await bot.sendText(msg.from, message);
    }

    async function confirmarPessoa(msg, bot, pessoa) {
        if (pessoa?.nome) {
            const opcoes = [
                {id: 1, nome: "Confirmar"},
                {id: 2, nome: "Corrigir as informações"},
                {id: 3, nome: "Cancelar"},
            ];

            opcoes.sort(function (x, y) {
                return x.id - y.id;
            });
            let listaOpcoes = "";
            for (let i = 0; i < opcoes.length; i++) {
                listaOpcoes += "*" + opcoes[i].id + "* - " + opcoes[i].nome + "\n";
            }

            let message = `Por favor, verifique as informações antes de confirmar o cadastro:\n\n`;
            message +=
                `Nome da pessoa: ${pessoa.nome}\n` +
                `Data de Nascimento: ${formatDate(pessoa.dataNascimento)}\n` +
                `Contato: ${formatCelular(pessoa.celular)}\n` +
                `CPF: ${formatCPF(pessoa.cpf)}\n`;

            let messageOpcoes =
                listaOpcoes + "\n```digite o número de uma das opções```";
            await setState(msg.from, "aguardando_confirmacaoPessoa");
            await bot.sendText(msg.from, message);
            await bot.sendText(msg.from, messageOpcoes);
        }
    }

    async function createPessoa(pPessoa, bot) {
        try {
            let response = await createPessoaByBot(pPessoa, bot);
            if (response && response.data) {
                return response.data;
            }
            return null;
        } catch (error) {
            return null;
        }
    }

    module.exports = {
        getPessoa,
        makePessoaByBot,
        createPessoa,
        confirmarPessoa,
        makeCelularPessoa,
        makeCpfPessoa,
        makeDataNascimentoPessoa,
        makeNomePessoa,
    }
}
