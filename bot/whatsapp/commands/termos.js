module.exports = async (msg, bot) => {
    let message = `Olá! Meu nome é Beatriz, sou sua assistente virtual da *Clínica Black Belt Fisio*.\n\nNós estamos ansiosos para atender você!\n\nPara iniciar o seu atendimento e respeitando a Lei Geral de Proteção de Dados (LGPD), informamos que podemos coletar dados pessoais, e para dar continuidade ao seu atendimento precisamos que aceite nossa política de privacidade:\nhttps://exemplo.com.br\n\nVocê aceita nossa política de privacidade?\n\n*1* - Aceitar\n*2* - Recusar\n\n\`\`\`Digite o número de uma das opções\`\`\``;

    await bot.sendText(msg.from, message);
};
