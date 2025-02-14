const tipoDisponivel = {};

async function setTipoDisponivel(chatId, newtipoDisponivel) {
    if (!tipoDisponivel[chatId]) {
        tipoDisponivel[chatId] = {};
    }

    if (newtipoDisponivel) {
        tipoDisponivel[chatId].listaTipo = newtipoDisponivel;
    }
}

async function getTipoDisponivel(chatId, id) {
    if (!tipoDisponivel[chatId]) {
        tipoDisponivel[chatId] = {};
    }

    const listaTipo = tipoDisponivel[chatId]?.listaTipo;
    if (listaTipo) {
        const tipo = listaTipo.find((org) => org.id == id);
        return tipo || null;
    }
    return null;
}

module.exports = {
    tipoDisponivel,
    setTipoDisponivel,
    getTipoDisponivel,
};
