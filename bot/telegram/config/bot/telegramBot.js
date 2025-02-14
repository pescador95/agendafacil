const {enviarMensagemBlackBelt} = require("./tenant/blackbelt");
const {enviarMensagemBase} = require("./tenant/base");

async function enviarMensagemByTenant(id, mensagem, tenant) {
    switch (tenant) {
        case "blackbelt":
            await enviarMensagemBlackBelt(id, mensagem);
            return true;
        case "base":
            await enviarMensagemBase(id, mensagem);
            return true;
        default:
            break;
    }
}

module.exports = {enviarMensagemByTenant};
