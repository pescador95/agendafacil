const {limparAgedamentoById} = require("../config/objetos/limparObjetos");
const {setState} = require("../config/objetos/setState");

require("venom-bot");

async function iniciarAtendimento(msg, bot, pessoa) {
    let message = `Bem-vindo ao Agenda Fácil, ${pessoa?.nome}!`;

    await bot.sendText(msg.from, message);
    listaOpcoes(msg, bot);
}

async function listaOpcoes(msg, bot, reincidente) {
    await limparAgedamentoById(msg.from);
    let message;
    if (!reincidente) {
        message = "Como posso ajudar?\n\n";
    }
    if (reincidente) {
        message = "Posso ajudar em algo mais?\n\n";
    }
    message +=
        "*1* - Agendar\n*2* - Reagendar\n*3* - Listar meus agendamentos\n*4* - Finalizar atendimento\n\n```Digite o número de uma das opções```";

    await bot.sendText(msg.from, message);
    await setState(msg.from, "menu");
}

module.exports = {listaOpcoes, iniciarAtendimento};
