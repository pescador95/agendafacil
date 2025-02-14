const profissionalDisponivel = {};

async function setProfissionalDisponivel(chatId, newProfissionalDisponivel) {
    if (!profissionalDisponivel[chatId]) {
        profissionalDisponivel[chatId] = {};
    }

    if (newProfissionalDisponivel) {
        profissionalDisponivel[chatId].listaProfissional =
            newProfissionalDisponivel;
    }
}

async function getProfissionalDisponivel(chatId, id) {
    if (!profissionalDisponivel[chatId]) {
        profissionalDisponivel[chatId] = {};
    }

    const listaProfissional = profissionalDisponivel[chatId]?.listaProfissional;
    if (listaProfissional) {
        const profissional = listaProfissional.find((org) => org.id == id);
        return profissional || null;
        s;
    }
    return null;
}

module.exports = {
    profissionalDisponivel,
    setProfissionalDisponivel,
    getProfissionalDisponivel,
};
