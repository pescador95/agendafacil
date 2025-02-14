const {axiosApiNewInstance} = require("../../config/axios/apiService");
const {endpoints, tenant} = require("../../endpoints/endpoints");
const moment = require("moment");

async function listarPessoaAgendamentosByBot(pessoa, reagendar, bot) {
    const axios = axiosApiNewInstance(bot.tenant);

    const pAgendamento = {
        pessoaAgendamento: {
            id: pessoa?.id,
        },
    };

    try {
        return await axios.post(endpoints.agendamento.listarPessoaAgendamentos(reagendar),
            pAgendamento
        );
    } catch (error) {
        console.error(error);
    }
}

async function listarAgendamentosByBot(pAgendamento, reagendar, bot) {
    try {
        const axios = axiosApiNewInstance(bot.tenant);
        let response = await axios.post(endpoints.agendamento.listarAgendamentos(reagendar),
            pAgendamento
        );
        return response;
    } catch (error) {
        console.error(error);
    }
}

async function marcarAgendamentosByBot(pAgendamento, bot, msg) {
    try {
        const axios = axiosApiNewInstance(bot.tenant);
        const response = await axios.post(endpoints.agendamento.marcarAgendamento,
            pAgendamento
        );
        await bot.sendMessage(msg.chat.id, response.data.messages[0]);
    } catch (error) {
        await bot.sendMessage(msg.chat.id, error.response.data.messages[0]);
    }
}

async function remarcarAgendamentosByBot(agendamentos, bot, msg) {
    try {
        const axios = axiosApiNewInstance(bot.tenant);
        const parametros = [
            {id: agendamentos[0]?.id},
            {
                tipoAgendamento: {
                    id: agendamentos[1]?.newAgendamento?.tipoAgendamento?.id,
                },
                pessoaAgendamento: {
                    id: agendamentos[1]?.newAgendamento?.pessoaAgendamento?.id,
                },
                dataAgendamento: moment(
                    agendamentos[1]?.newAgendamento?.dataAgendamento
                ).format("YYYY-MM-DD"),
                horarioAgendamento: agendamentos[1]?.newAgendamento?.horarioAgendamento,
                profissionalAgendamento: {
                    id: agendamentos[1]?.newAgendamento?.profissionalAgendamento?.id,
                },
                organizacaoAgendamento: {
                    id: agendamentos[1]?.newAgendamento?.organizacaoAgendamento?.id,
                },
                statusAgendamento: {
                    id: agendamentos[1]?.newAgendamento?.statusAgendamento?.id,
                },
            },
        ];
        const response = await axios.post(endpoints.agendamento.remarcarAgendamento,
            parametros
        );
        bot.removeTextListener(/^(.*)$/);
        await bot.sendMessage(msg.chat.id, response.data.messages[0]);
    } catch (error) {
        await bot.sendMessage(msg.chat.id, error.response.data.messages[0]);
    }
}

module.exports = {
    listarPessoaAgendamentosByBot,
    listarAgendamentosByBot,
    marcarAgendamentosByBot,
    remarcarAgendamentosByBot,
};
