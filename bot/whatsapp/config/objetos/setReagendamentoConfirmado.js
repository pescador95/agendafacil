const reagendamentoConfirmado = {};

async function setReagendamentoConfirmado(chatId, newReagendamentoConfirmado) {
    if (!reagendamentoConfirmado[chatId]) {
        reagendamentoConfirmado[chatId] = {};
    }

    if (newReagendamentoConfirmado) {
        reagendamentoConfirmado[chatId].oldAgendamento = newReagendamentoConfirmado;
    }
}

async function getReagendamentoConfirmado(chatId) {
    if (!reagendamentoConfirmado[chatId]) {
        reagendamentoConfirmado[chatId] = {};
    }

    const listaReagendamento = reagendamentoConfirmado[chatId]?.oldAgendamento;
    if (listaReagendamento) {
        return listaReagendamento || null;
    }
    return null;
}

module.exports = {
    reagendamentoConfirmado,
    setReagendamentoConfirmado,
    getReagendamentoConfirmado,
};
