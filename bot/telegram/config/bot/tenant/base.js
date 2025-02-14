const TelegramBot = require("node-telegram-bot-api");
const termosCommands = require("../../../commands/termos");
const {getPessoaByTelegramId} = require("../../../../core/handlers/telegram/pessoa/pessoaHandlers");
const {iniciarAtendimento} = require("../../../commands/opcoes");

let token = process.env.TELEGRAM_RELEASE_TOKEN || `${TELEGRAM_RELEASE_TOKEN}`;

const baseBot = new TelegramBot(token, {polling: true});

baseBot.tenant = "base";

const saudacoesRegExp = /^(oi|ola|iniciar|start|comecar|\/start|olá|oi\s+bot|oi\s+amigo|hey|oi\s+ai|bom\s+dia|boa\s+tarde|boa\s+noite)$/i;

baseBot.onText(saudacoesRegExp, async (msg) => {
    baseBot.removeTextListener(/^(.*)$/, termosCommands);
    let pessoa = await getPessoaByTelegramId(msg, baseBot);
    if (pessoa?.nome && pessoa?.id) {
        iniciarAtendimento(msg, baseBot, pessoa);
    }
    if (!pessoa) {
        termosCommands(msg, baseBot);
    }
});

async function enviarMensagemBase(id, mensagem) {

    await baseBot.sendMessage(id, mensagem);
}

module.exports = {enviarMensagemBase, baseBot};
