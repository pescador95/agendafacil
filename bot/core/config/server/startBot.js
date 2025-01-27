const bot = require("../../../telegram");

async function startBot() {
    try {
        console.log("Autenticação bem-sucedida. Iniciando o bot...");
        bot.startPolling();
    } catch (error) {
        console.error("Falha na autenticação:", error.message);
    }
}

startBot();
