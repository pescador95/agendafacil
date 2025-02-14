const TelegramBot = require("node-telegram-bot-api");
const termosCommands = require("../../../commands/termos");
const {getPessoaByTelegramId} = require("../../../../core/handlers/telegram/pessoa/pessoaHandlers");
const {iniciarAtendimento} = require("../../../commands/opcoes");

let token = process.env.TELEGRAM_TOKEN || `${$TELEGRAM_TOKEN}`;

const blackbeltBot = new TelegramBot(token, {polling: true});

blackbeltBot.tenant = "blackbelt";

const saudacoesRegExp = /^(oi|ola|iniciar|start|comecar|\/start|olá|oi\s+bot|oi\s+amigo|hey|oi\s+ai|bom\s+dia|boa\s+tarde|boa\s+noite)$/i;

blackbeltBot.onText(saudacoesRegExp, async (msg) => {
    blackbeltBot.removeTextListener(/^(.*)$/, termosCommands);
    let pessoa = await getPessoaByTelegramId(msg, blackbeltBot);

    if (pessoa?.nome && pessoa?.id) {
        iniciarAtendimento(msg, blackbeltBot, pessoa);
    }
    if (!pessoa) {
        termosCommands(msg, blackbeltBot);
    }
});

async function enviarMensagemBlackBelt(id, mensagem) {

    await blackbeltBot.sendMessage(id, mensagem);
}

module.exports = {enviarMensagemBlackBelt, blackbeltBot};
