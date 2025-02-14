const {axiosApiNewInstance} = require("../../config/axios/apiService");
const {endpoints, tenant} = require("../../endpoints/endpoints");

async function listarOrganizacoesByAgendamentoBot(bot) {
    try {
        const axios = axiosApiNewInstance(bot.tenant);
        return await axios.get(endpoints.organizacao.listaOrganizacoes);
    } catch (error) {
    }
}

module.exports = listarOrganizacoesByAgendamentoBot;
