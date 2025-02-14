const {listaOpcoesComandos, iniciarAtendimento} = require("./opcoes");
const {
    getPessoaByTelegramId,
    getPessoa,
} = require("../../core/handlers/telegram/pessoa/pessoaHandlers");

async function greetingsCommand(msg, bot, reincidencia) {
    bot.removeTextListener(/^(.*)$/);
    const listener = /^(?:\d{11})$/;
    const greetingsPhone = /^(?:\d{11})$/;
    let message = "";
    message = `Por favor,  digite o número do CPF ou telefone   da pessoa que deseja atendimento.\n\nPara telefone, digite o número do celular no formato DDD + número.\nExemplo: (45) 9 9123-4567 ou 45991234567\n\nPara CPF, siga o exemplo abaixo.\nExemplo: 012.456.789-00 ou 012345678900.`;
    let telefone = 0;

    telefone = msg.from.contact?.phone_number;

    let pessoa = await getPessoaByTelegramId(msg);
    if (pessoa?.nome && pessoa?.id) {
        iniciarAtendimento(msg, bot, pessoa);
    }
    if (!pessoa) {
        if (!telefone) {
            bot.removeTextListener(/^(.*)$/);
            bot.sendMessage(msg.chat.id, message);
        }

        bot.onText(listener, async (matchMsg, match) => {
            bot.removeTextListener(listener);

            match[0] = match[0].replace(/\D/g, "");

            try {
                bot.removeTextListener(greetingsPhone);
                if (match && greetingsPhone.test(match[0])) {
                    let ident = match[0].replace(/\D/g, "");
                    await getPessoa(ident, msg, bot, listaOpcoesComandos, reincidencia);
                }

                if (!match) {
                    let message =
                        "Desculpe, mas o número informado não possui exatamente 11 dígitos numéricos.";
                    await bot.sendMessage(msg.chat.id, message);
                    await greetingsCommand(msg, bot, reincidencia);
                }
            } catch (error) {
                console.error("Erro ao fazer a solicitação GET:", error.message);
            }
        });
    }
}

module.exports = greetingsCommand;
