const endpoints = {
    apihealth: {
        check: "/health",
    },
    login: {
        refresh: `/auth/refresh`,
        auth: `/auth`,
    },
    pessoa: {
        getByPhone: (telefone) => `/pessoa/phone?telefone=${telefone}`,
        getByCPF: (cpf) => `/pessoa/cpf?cpf=${cpf}`,
        getByTelegram: (telegramId) => `/pessoa/telegram?telegramId=${telegramId}`,
        getByIdent: (ident) => `/pessoa/ident?ident=${ident}`,
        createPessoa: `/pessoa/bot/create`,
        updatePessoa: (telegramId) => `/pessoa/telegram?telegramId=${telegramId}`,
        removeTelegramId: (telegramId) =>
            `/pessoa/telegram?telegramId=${telegramId}`,
        sendNotification: `/enviarLembrete/:id`,
    },
    agendamento: {
        listarPessoaAgendamentos: (reagendar) =>
            `/agendamento/bot/listar/meusAgendamentos?reagendar=${reagendar}`,
        listarAgendamentos: (reagendar) =>
            `/agendamento/bot/listar?reagendar=${reagendar}`,
        marcarAgendamento: `/agendamento/bot/marcar`,
        remarcarAgendamento: `/agendamento/bot/remarcar`,
        verificarDataAgendamento: `/agendamento/bot/verificarData`,
    },
    organizacao: {
        listaOrganizacoes: `/organizacao/bot/`,
    },
    tipoAgendamentos: {
        listarTipoAgendamentos: (organizacoes) =>
            `/tipoAgendamento/bot?organizacoes=${organizacoes}`,
    },
    profissional: {
        listaProfissionais: (
            organizacao,
            dataAgendamento,
            tipoAgendamento,
            profissional,
            comPreferencia
        ) =>
            `/usuario/bot?organizacao=${organizacao}&dataAgendamento=${dataAgendamento}&tipoAgendamento=${tipoAgendamento}&profissional=${profissional}&comPreferencia=${comPreferencia}`,
    },
};

let baseURL = process.env.QUARKUS_BASEURL || `${QUARKUS_BASEURL}`;

function tenant(tenant) {
    return `${baseURL}/${tenant}`;
}

module.exports = {endpoints, tenant};
