require("node-telegram-bot-api");
const greetingsCommand = require("../commands/greetings");

async function termosCommands(msg, bot) {
    const opcoes = [
        {id: 1, nome: "Aceitar"},
        {id: 2, nome: "Recusar"},
    ];

    let message = `Olá! Meu nome é Beatriz, sou sua assistente virtual da  Clínica Black Belt Fisio .\n\nNós estamos ansiosos para atender você!\n\nPara iniciar o seu atendimento e respeitando a Lei Geral de Proteção de Dados (LGPD), informamos que podemos coletar dados pessoais, e para dar continuidade ao seu atendimento precisamos que aceite nossa política de privacidade:\nhttps://exemplo.com.br\n\nVocê aceita nossa política de privacidade?\n\n`;

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
            case "Aceitar":
                greetingsCommand(msg, bot, false);
                return;
            case "Recusar":
                bot.sendMessage(
                    msg.chat.id,
                    'Precisamos que aceite nossa política de privacidade para que possamos dar continuidade em seu atendimento.\nSe precisar estou á disposição.\nPara me chamar é só mandar um "oi"'
                );
                return;
            default:
                await bot.sendMessage(
                    msg.chat.id,
                    `Desculpe, não entendi.\nPor gentileza,  aceitar  nossas políticas de privacidade ou  recusar .`
                );
                break;
        }
    });
}

module.exports = termosCommands;
