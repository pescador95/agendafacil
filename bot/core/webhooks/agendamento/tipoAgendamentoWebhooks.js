const {axiosApiNewInstance} = require("../../../core/config/axios/apiService");
const {endpoints, tenant} = require("../../endpoints/endpoints");

async function listarTiposAgendamentoByOrganizacaoBot(organizacoes, bot) {
    try {
        const axios = axiosApiNewInstance(bot.tenant);
        return await axios.get(endpoints.tipoAgendamentos.listarTipoAgendamentos(organizacoes)
        );
    } catch (error) {
        console.error(error);
    }

}

module.exports = listarTiposAgendamentoByOrganizacaoBot;
