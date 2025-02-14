export const endpoints = {
  apihealth: {
    check: '/health',
  },
  login: {
    refresh: `/auth/refresh`,
    auth: `/auth`,
    logout: `/auth/logout`,
  },
  agendamento: {
    base: `/agendamento`,
    listarPessoaAgendamentos: (reagendar) =>
      `/agendamento/bot/listar/meusAgendamentos?reagendar=${reagendar}`,
    listarAgendamentos: (reagendar) => `/agendamento/bot/listar?reagendar=${reagendar}`,
    marcarAgendamento: `/agendamento/bot/marcar`,
    remarcarAgendamento: `/agendamento/bot/remarcar`,
    verificarDataAgendamento: `/agendamento/bot/verificarData`,
    count: `/agendamento/count`,
    getByid: (id) => `/agendamento/${id}`,
    reactive: `/agendamento/reactivate`,
  },
  atendimento: {
    base: `/atendimento`,
    count: `/atendimento/count`,
    getByid: (id) => `/atendimento/${id}`,
    reactive: `/atendimento/reactivate`,
  },
  configuradorAgendamento: {
    base: `/configuradorAgendamento`,
    count: `/configuradorAgendamento/count`,
    getByid: (id) => `/configuradorAgendamento/${id}`,
  },
  configuradorAgendamentoEspecial: {
    base: `/configuradorAgendamentoEspecial`,
    count: `/configuradorAgendamentoEspecial/count`,
    getByid: (id) => `/configuradorAgendamentoEspecial/${id}`,
  },
  configuradorAusencia: {
    base: `/configuradorAusencia`,
    count: `/configuradorAusencia/count`,
    getByid: (id) => `/configuradorAusencia/${id}`,
  },
  configuradorFeriado: {
    base: `/configuradorFeriado`,
    count: `/configuradorFeriado/count`,
    getByid: (id) => `/configuradorFeriado/${id}`,
    reactive: `/configuradorFeriado/reactivate`,
  },
  contrato: {
    base: `/contrato`,
    count: `/contrato/count`,
    getByid: (id) => `/contrato/${id}`,
    reactive: `/contrato/reactivate`,
  },
  endereco: {
    base: `/endereco`,
    count: `/endereco/count`,
    getByid: (id) => `/endereco/${id}`,
    reactive: `/endereco/reactivate`,
  },
  genero: {
    base: `/genero`,
    count: `/genero/count`,
    getByid: (id) => `/genero/${id}`,
  },
  historicoPessoa: {
    base: `/historicoPessoa`,
    count: `/historicoPessoa/count`,
    getByid: (id) => `/historicoPessoa/${id}`,
    reactive: `/historicoPessoa/reactivate`,
  },
  organizacao: {
    base: `/organizacao`,
    listaOrganizacoes: `/organizacao/bot/`,
    count: `/organizacao/count`,
    getByid: (id) => `/organizacao/${id}`,
    reactive: `/organizacao/reactivate`,
  },
  perfilAcesso: {
    base: `/perfilAcesso`,
    count: `/perfilAcesso/count`,
    getByid: (id) => `/perfilAcesso/${id}`,
  },
  pessoa: {
    base: `/pessoa`,
    getByPhone: (telefone) => `/pessoa/phone?telefone=${telefone}`,
    getByCPF: (cpf) => `/pessoa/cpf?cpf=${cpf}`,
    getByTelegram: (telegramId) => `/pessoa/telegram?telegramId=${telegramId}`,
    getByIdent: (ident) => `/pessoa/ident?ident=${ident}`,
    createPessoa: `/pessoa/bot/create`,
    updatePessoa: (telegramId) => `/pessoa/telegram?telegramId=${telegramId}`,
    removeTelegramId: (telegramId) => `/pessoa/telegram?telegramId=${telegramId}`,
    sendNotification: `/enviarLembrete/:id`,
    count: `/pessoa/count`,
    getByid: (id) => `/pessoa/${id}`,
    reactive: `/pessoa/reactivate`,
    telegram: `/pessoa/telegram`,
    telegramEnable: (enabled) => {
      ;`/pessoa/telegram/enable?=${enabled}`
    },
    whatsApp: `/pessoa/whatsapp`,
    whatsAppEnable: (enabled) => {
      ;`/pessoa/whatsapp/enable?=${enabled}`
    },
  },

  recoveryPassword: {
    base: `/recoverPassword`,
    recoveryPassword: (user) => `/recoverPassword/${user}`,
  },
  role: {
    base: `/role`,
    count: `/role/count`,
    getByid: (id) => `/role/${id}`,
  },
  rotina: {
    base: `/rotina`,
    count: `/rotina/count`,
    getByid: (id) => `/rotina/${id}`,
  },
  scheduler: {
    enviarLembrete: `/scheduler/enviarLembrete`,
    dispararLembretes: `/scheduler/execute`,
  },
  statusAgendamento: {
    base: `/statusAgendamento`,
    count: `/statusAgendamento/count`,
    getByid: (id) => `/statusAgendamento/${id}`,
  },
  timeZone: {
    base: `/timezones`,
    getByid: (id) => `/timezones/${id}`,
    list: `/timezones/list`,
  },
  tipoAgendamentos: {
    base: `/tipoAgendamento`,
    listarTipoAgendamentos: (organizacoes) => `/tipoAgendamento/bot?organizacoes=${organizacoes}`,
    count: `/tipoAgendamento/count`,
    getByid: (id) => `/tipoAgendamento/${id}`,
  },
  profile: {
    base: `/uploads`,
    count: `/uploads/count`,
    getByid: (id) => `/uploads/${id}`,
    reactivate: `/uploads/reactivate`,
  },

  profissional: {
    base: `/usuario`,
    listaProfissionais: (
      organizacao,
      dataAgendamento,
      tipoAgendamento,
      profissional,
      comPreferencia,
    ) =>
      `/usuario/bot?organizacao=${organizacao}&dataAgendamento=${dataAgendamento}&tipoAgendamento=${tipoAgendamento}&profissional=${profissional}&comPreferencia=${comPreferencia}`,
    count: `/usuario/count`,
    getByid: (id) => `/usuario/${id}`,
    reactivate: `/usuario/reactivate`,
    copy: `/usuario/copy`,
  },
}
