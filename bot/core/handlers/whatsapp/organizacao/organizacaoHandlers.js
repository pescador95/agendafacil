const {listaOpcoes} = require("../../../../whatsapp/commands/opcoes");
const {
    setOrganizacaoDisponivel,
} = require("../../../../whatsapp/config/objetos/setOrganizacao");
const {setState} = require("../../../../whatsapp/config/objetos/setState");
const listarOrganizacoesByAgendamentoBot = require("../../../webhooks/organizacao/organizacaoWebhooks");

async function montarOrganizacoesByAgendamento(msg, bot, pessoa) {
    const organizacoes = await listarOrganizacoesByAgendamentoBot(bot);

    if (!organizacoes || organizacoes.data.length === 0) {
        let message = `Não há nenhuma organização disponível para atendimento.`;
        await bot.sendText(msg.from, message).then(() => {
            return listaOpcoes(msg, bot, true);
        });
    }
    if (organizacoes || organizacoes.data.length > 0) {
        const opcoes = organizacoes.data.map((organizacao) => {
            return {
                id: organizacao.id,
                nome: organizacao.nome,
                celular: organizacao.celular,
                zoneId: organizacao.zoneId,
                timeZoneOffset: organizacao.timeZoneOffset
            };
        });
        opcoes.sort(function (x, y) {
            return x.id - y.id;
        });

        let listaOganizacao = "";
        for (let i = 0; i < opcoes.length; i++) {
            listaOganizacao += "*" + opcoes[i].id + "* - " + opcoes[i].nome + "\n";
        }
        let message =
            "Aqui estão as nossas *organizações* disponíveis.\n\nSelecione em qual você deseja ser atendimento(a):\n\n" +
            listaOganizacao +
            "\n```digite o número de uma das opções```";

        setOrganizacaoDisponivel(msg.from, opcoes);
        setState(msg.from, "aguardando_organização");
        await bot.sendText(msg.from, message);
    }
}

module.exports = montarOrganizacoesByAgendamento;
