const {axiosApiNewInstance} = require("../../config/axios/apiService");
const {endpoints, tenant} = require("../../endpoints/endpoints");
const moment = require("moment");

async function listarProfissionaisByAgendamentoBot(
    organizacao,
    dataAgendamento,
    tipoAgendamento,
    profissional,
    comPreferencia,
    bot
) {
    const parametros = {
        organizacao: organizacao?.id,
        dataAgendamento: moment(dataAgendamento).format("YYYY-MM-DD"),
        tipoAgendamento: tipoAgendamento?.id,
        profissional: profissional?.id ?? 0,
        comPreferencia: comPreferencia,
    };
    try {
        const response = await axios.get(tenant(bot.tenant) +
            endpoints.profissional.listaProfissionais(
                parametros.organizacao,
                parametros.dataAgendamento,
                parametros.tipoAgendamento,
                parametros.profissional,
                parametros.comPreferencia
            )
        );
        return response;
    } catch (error) {
    }
}

module.exports = listarProfissionaisByAgendamentoBot;
