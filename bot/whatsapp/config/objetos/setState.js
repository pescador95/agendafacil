const botStates = {};

async function setState(chatId, newState, newPessoa) {
    if (!botStates[chatId]) {
        botStates[chatId] = {
            state: newState,
        };
    }
    if (newState) {
        botStates[chatId].state = newState;
    }
    if (newPessoa) {
        botStates[chatId].pessoa = newPessoa;
    }
    return;
}

async function reagendar(chatId, reagendar) {
    if (!botStates[chatId]) {
        botStates[chatId] = {};
    }
    botStates[chatId].reagendar = reagendar;
}

async function getReagendar(chatId) {
    if (!botStates[chatId]) {
        botStates[chatId] = {};
    }
    return botStates[chatId]?.reagendar;
}

async function setPreferencia(chatId, comPreferencia) {
    if (!botStates[chatId]) {
        botStates[chatId] = {};
    }
    botStates[chatId].comPreferencia = comPreferencia;
}

async function getPreferencia(chatId) {
    if (!botStates[chatId]) {
        botStates[chatId] = {};
    }
    return botStates[chatId]?.comPreferencia;
}

module.exports = {
    botStates,
    setState,
    getReagendar,
    reagendar,
    setPreferencia,
    getPreferencia,
};
