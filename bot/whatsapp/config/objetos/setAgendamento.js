const agendamento = {};

async function setAgendamento(
    chatId,
    pessoaAgendamento,
    tipoAgendamento,
    profissionalAgendamento,
    organizacaoAgendamento,
    endereco,
    celularOrganizacao,
    dataAgendamento,
    horarioAgendamento
) {
    if (!agendamento[chatId]) {
        agendamento[chatId] = {};
    }

    if (await pessoaAgendamento) {
        agendamento[chatId].pessoaAgendamento = await pessoaAgendamento;
    }
    if (await tipoAgendamento) {
        agendamento[chatId].tipoAgendamento = await tipoAgendamento;
    }
    if (await profissionalAgendamento) {
        agendamento[chatId].profissionalAgendamento = await profissionalAgendamento;
    }
    if (await organizacaoAgendamento) {
        agendamento[chatId].organizacaoAgendamento = await organizacaoAgendamento;
    }
    if (await endereco) {
        agendamento[chatId].endereco = await endereco;
    }
    if (await celularOrganizacao) {
        agendamento[chatId].celularOrganizacao = await celularOrganizacao;
    }
    if (await dataAgendamento) {
        agendamento[chatId].dataAgendamento = await dataAgendamento;
    }
    if (await horarioAgendamento) {
        agendamento[chatId].horarioAgendamento = await horarioAgendamento;
    }
}

async function getAgendamentoOrganizacao(chatId) {
    if (!agendamento[chatId]) {
        agendamento[chatId] = {};
    }

    const organizacao = agendamento[chatId]?.organizacaoAgendamento;
    if (organizacao) {
        return organizacao;
    }
    return null;
}

async function getAgendamentoData(chatId) {
    if (!agendamento[chatId]) {
        agendamento[chatId] = {};
    }

    const data = agendamento[chatId]?.dataAgendamento;
    if (data) {
        return data;
    }
    return null;
}

async function getAgendamentoTipo(chatId) {
    if (!agendamento[chatId]) {
        agendamento[chatId] = {};
    }

    const tipo = agendamento[chatId]?.tipoAgendamento;
    if (tipo) {
        return tipo;
    }
    return null;
}

async function getAgendamentoProfissional(chatId) {
    if (!agendamento[chatId]) {
        agendamento[chatId] = {};
    }

    const profissional = agendamento[chatId]?.profissionalAgendamento;
    if (profissional) {
        return profissional;
    }
    return null;
}

async function getAgendamentoHorario(chatId) {
    if (!agendamento[chatId]) {
        agendamento[chatId] = {};
    }

    const horario = agendamento[chatId]?.horarioAgendamento;
    if (horario) {
        return horario;
    }
    return null;
}

module.exports = {
    agendamento,
    setAgendamento,
    getAgendamentoOrganizacao,
    getAgendamentoData,
    getAgendamentoTipo,
    getAgendamentoProfissional,
    getAgendamentoHorario
};
