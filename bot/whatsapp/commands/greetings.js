const {setState} = require("../config/objetos/setState");

async function greetings(msg, bot) {
    let message =
        "Por favor, *digite o número do CPF ou telefone* da pessoa que deseja atendimento.\n\nPara telefone, digite o número do celular no formato DDD + número.\nExemplo: (45) 9 9123-4567 ou 45991234567\n\nPara CPF, siga o exemplo abaixo.\nExemplo: 012.456.789-00 ou 012345678900.";
    setState(msg.from, "aceita_termo");
    await bot.sendText(msg.from, message);
}

module.exports = greetings;
