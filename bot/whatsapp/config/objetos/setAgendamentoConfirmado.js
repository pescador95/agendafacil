const agendamentoConfirmado = {};

async function setAgendamentoConfirmado(chatId, newAgendamentoConfirmado) {
    if (!agendamentoConfirmado[chatId]) {
        agendamentoConfirmado[chatId] = {};
    }

    if (newAgendamentoConfirmado) {
        agendamentoConfirmado[chatId].newAgendamento = newAgendamentoConfirmado;
    }
}

async function getAgendamentoConfirmado(chatId) {
    if (!agendamentoConfirmado[chatId]) {
        agendamentoConfirmado[chatId] = {};
    }

    const listaAgendamento = agendamentoConfirmado[chatId]?.newAgendamento;
    if (listaAgendamento) {
        return listaAgendamento || null;
    }
    return null;
}

module.exports = {
    agendamentoConfirmado,
    setAgendamentoConfirmado,
    getAgendamentoConfirmado,
};
