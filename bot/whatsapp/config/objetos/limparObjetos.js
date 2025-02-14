const {agendamento} = require("./setAgendamento");
const {agendamentoConfirmado} = require("./setAgendamentoConfirmado");
const {listaAgendamentosReagendar} = require("./setAgendamentosReagendar");
const {dataDisponivel, dataEscolhida} = require("./setData");
const {horarioDisponivel} = require("./setHorario");
const {organizacaoDisponivel} = require("./setOrganizacao");
const {pessoa} = require("./setPessoa");
const {profissionalDisponivel} = require("./setProfissional");
const {reagendamentoConfirmado} = require("./setReagendamentoConfirmado");
const {botStates} = require("./setState");
const {tipoDisponivel} = require("./setTipo");

async function limparAgedamentoById(chatId) {
    if (agendamento[chatId]) {
        delete agendamento[chatId];
    }
    if (agendamentoConfirmado[chatId]) {
        delete agendamentoConfirmado[chatId];
    }
    if (dataDisponivel[chatId]) {
        delete dataDisponivel[chatId];
    }
    if (dataEscolhida[chatId]) {
        delete dataEscolhida[chatId];
    }
    if (horarioDisponivel[chatId]) {
        delete horarioDisponivel[chatId];
    }
    if (organizacaoDisponivel[chatId]) {
        delete organizacaoDisponivel[chatId];
    }
    if (profissionalDisponivel[chatId]) {
        delete profissionalDisponivel[chatId];
    }
    if (tipoDisponivel[chatId]) {
        delete tipoDisponivel[chatId];
    }
    if (listaAgendamentosReagendar[chatId]) {
        delete listaAgendamentosReagendar[chatId];
    }
    if (reagendamentoConfirmado[chatId]) {
        delete reagendamentoConfirmado[chatId];
    }
    if (pessoa[chatId]) {
        delete pessoa[chatId];
    }
    if (botStates[chatId]) {
        delete botStates[chatId].reagendar;
        delete botStates[chatId].comPreferencia;
    }
}

async function limparBotStateById(chatId) {
    if (botStates[chatId]) {
        delete botStates[chatId];
    }
    limparAgedamentoById(chatId);
}

module.exports = {
    limparAgedamentoById,
    limparBotStateById,
};
