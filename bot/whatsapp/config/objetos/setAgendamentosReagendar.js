const listaAgendamentosReagendar = {};

async function setAgendamentosReagendar(chatId, newAgendamentosReagendar) {
    if (!listaAgendamentosReagendar[chatId]) {
        listaAgendamentosReagendar[chatId] = {};
    }

    if (newAgendamentosReagendar) {
        listaAgendamentosReagendar[chatId].listaAgendamentos =
            newAgendamentosReagendar;
    }
}

async function getAgendamentosReagendar(chatId, id) {
    if (!listaAgendamentosReagendar[chatId]) {
        listaAgendamentosReagendar[chatId] = {};
    }

    const listaAgendamentos =
        listaAgendamentosReagendar[chatId]?.listaAgendamentos;

    if (listaAgendamentos) {
        const agendamentoReagendar = listaAgendamentos.find(
            (org) => org.idLabel == id
        );
        return agendamentoReagendar || null;
    }
    return null;
}

module.exports = {
    listaAgendamentosReagendar,
    setAgendamentosReagendar,
    getAgendamentosReagendar,
};
