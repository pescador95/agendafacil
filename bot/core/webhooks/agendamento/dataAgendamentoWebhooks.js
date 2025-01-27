const {axiosApiNewInstance} = require("../../config/axios/apiService");
const {endpoints, tenant} = require("../../endpoints/endpoints");

async function checkDataAgendamento(agendamentoVerificar, bot) {
    try {
        const axios = axiosApiNewInstance(bot.tenant);
        let response = await axios.post(endpoints.agendamento.verificarDataAgendamento,
            agendamentoVerificar
        );
        if (response) {
            return true;
        }
    } catch (e) {
        return false;
    }
}


module.exports = {checkDataAgendamento};