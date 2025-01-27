const horarioDisponivel = {};

async function setHorarioDisponivel(chatId, newHorarioDisponivel) {
    if (!horarioDisponivel[chatId]) {
        horarioDisponivel[chatId] = {};
    }

    if (newHorarioDisponivel) {
        horarioDisponivel[chatId].listaHorario = newHorarioDisponivel;
    }
}

async function getHorarioDisponivel(chatId, id) {
    if (!horarioDisponivel[chatId]) {
        horarioDisponivel[chatId] = {};
    }

    const listaHorario = horarioDisponivel[chatId]?.listaHorario;
    if (listaHorario) {
        const horario = listaHorario.find((org) => org.id == id);
        return horario || null;
    }
    return null;
}

module.exports = {
    horarioDisponivel,
    setHorarioDisponivel,
    getHorarioDisponivel,
};
