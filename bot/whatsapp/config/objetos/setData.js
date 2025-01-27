const dataDisponivel = {};
const dataEscolhida = {};

async function setDataDisponivel(chatId, newDataDisponivel) {
    if (!dataDisponivel[chatId]) {
        dataDisponivel[chatId] = {};
    }

    if (newDataDisponivel) {
        dataDisponivel[chatId].listaData = newDataDisponivel;
    }
}

async function getDataDisponivel(chatId, id) {
    if (!dataDisponivel[chatId]) {
        dataDisponivel[chatId] = {};
    }

    const listaData = dataDisponivel[chatId]?.listaData;
    if (listaData) {
        const data = listaData.find((dat) => dat.id == id);
        return data || null;
    }
    return null;
}

async function setDataEscolhida(chatId, newDataEscolhida) {
    if (!dataEscolhida[chatId]) {
        dataEscolhida[chatId] = {};
    }

    if (newDataEscolhida) {
        dataEscolhida[chatId].data = newDataEscolhida;
    }
}

async function getDataEscolhida(chatId) {
    if (!dataEscolhida[chatId]) {
        dataEscolhida[chatId] = {};
    }

    const data = dataEscolhida[chatId]?.data;
    if (data) {
        return data;
    }
    return null;
}

module.exports = {
    dataDisponivel,
    setDataDisponivel,
    getDataDisponivel,
    dataEscolhida,
    setDataEscolhida,
    getDataEscolhida,
};
