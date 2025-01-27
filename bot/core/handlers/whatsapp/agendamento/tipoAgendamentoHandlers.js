const {listaOpcoes} = require("../../../../whatsapp/commands/opcoes");
const {setState} = require("../../../../whatsapp/config/objetos/setState");
const {setTipoDisponivel} = require("../../../../whatsapp/config/objetos/setTipo");
const listarTiposAgendamentoByOrganizacaoBot = require("../../../webhooks/agendamento/tipoAgendamentoWebhooks");

async function montarTiposAgendamentoByOrganizacao(msg, bot, organizacao) {
    try {
        const tiposAgendamentos = await listarTiposAgendamentoByOrganizacaoBot(
            organizacao?.id,
            bot
        );

        if (!tiposAgendamentos || tiposAgendamentos.data.length === 0) {
            let message = `Não há nenhum tipo de agendamento na organização solicitada disponível para atendimento.`;
            await bot.sendText(msg.from, message).then(() => {
                return listaOpcoes(msg, bot, true);
            });
        }
        if (tiposAgendamentos || tiposAgendamentos.data.length > 0) {
            const opcoes = tiposAgendamentos.data.map((tipoAgendamento) => {
                return {
                    id: tipoAgendamento.id,
                    tipoAgendamento: tipoAgendamento.tipoAgendamento,
                };
            });

            opcoes.sort(function (x, y) {
                return x.id - y.id;
            });

            let listaOpcoes = "";
            for (let i = 0; i < opcoes.length; i++) {
                listaOpcoes +=
                    "*" + opcoes[i].id + "* - " + opcoes[i].tipoAgendamento + "\n";
            }
            let message =
                "Aqui estão os nossos *tipos de agendamentos* disponíveis.\n\nSelecional qual você deseja atendimento:\n\n" +
                listaOpcoes +
                "\n```digite o número de uma das opções```";
            setTipoDisponivel(msg.from, opcoes);
            setState(msg.from, "aguardando_tipo");
            await bot.sendText(msg.from, message);
        }
    } catch (error) {
        console.error(
            "Erro ao verificar a disponibilidade do tipo de agendamento:",
            error
        );
    }
}

module.exports = montarTiposAgendamentoByOrganizacao;
