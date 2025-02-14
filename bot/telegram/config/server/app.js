const moment = require("moment");
const {
    serverInfo,
    appendServerInfo,
} = require("../../../core/config/server/serverInfo");
const {endpoints} = require("../../../core/endpoints/endpoints");
let data = moment().format("DD/MM/YYYY HH:mm:ss");

const bodyParser = require("express");
const express = require("express");
const {enviarMensagemByTenant} = require("../bot/telegramBot");


const app = express();
const port = process.env.TELEGRAM_PORT || 5000;

app.use(bodyParser.json());
app.use(express.urlencoded({extended: true}));

const promClient = require('prom-client');
const collectDefaultMetrics = promClient.collectDefaultMetrics;
collectDefaultMetrics({timeout: 5000});

function start() {
    serverInfo();

    app.listen(port, () => {
        console.log(
            `Servidor API Bot Telegram utilizando Express iniciado na porta ${port}` +
            ". Serviço iniciado em: " +
            data +
            ` \nConectado ao Servidor Backend: ${process.env.QUARKUS_BASEURL}`
        );
    });
}

app.get('/metrics', async (req, res) => {
    res.set('Content-Type', promClient.register.contentType);
    res.end(await promClient.register.metrics());
});

app.get(endpoints.apihealth.check, (req, res) => {
    res.status(200).send(appendServerInfo());
});

app.post(endpoints.pessoa.sendNotification, async (req, res) => {
    const {id} = req?.params;

    let {mensagem, tenant} = req?.body;

    newMessage = mensagem.replace(/\\n/g, "\n");

    if (!newMessage) {
        return res.status(400).json({
            error: "A mensagem é obrigatório no Body da solicitação.",
        });
    }
    try {

        await enviarMensagemByTenant(id, mensagem, tenant);

        return res.status(200).json({success: true});
    } catch (error) {
        console.error("Erro ao enviar mensagem:", error);
        return res.status(500).json({error: "Erro ao enviar mensagem."});
    }
});

module.exports = {app, start};
