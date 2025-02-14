const {formatDate, formatDateReverse} = require("../../../utils/formatters");
const moment = require("moment");
require("moment-business-days");
require("moment/locale/pt-br");

const {
    listarAgendamentosByBot,
} = require("../../../webhooks/agendamento/agendamentoWebhooks");

const {
    checkDataAgendamento,
} = require("../../../webhooks/agendamento/dataAgendamentoWebhooks");

async function validarDataAgendamento(agendamentoVerificar, bot) {
    return checkDataAgendamento(agendamentoVerificar, bot);
}

async function montarDataAgendamento(
    msg,
    bot,
    organizacao,
    listaOpcoesComandos,
    pessoa,
    tipoAgendamento
) {
    let dataAgendamento = moment()
        .utcOffset(organizacao.timeZoneOffset)
        .format("YYYY-MM-DD");

    let diasDisponiveis = [];

    async function generateDateOptions() {
        let next6Days = [];

        let i = 0;

        let hoje = {
            dataAgendamento: moment()
                .utcOffset(organizacao.timeZoneOffset)
                .format("YYYY-MM-DD"),
            organizacaoAgendamento: organizacao,
            tipoAgendamento: tipoAgendamento,
            pessoaAgendamento: pessoa,
        };

        const dataAgendamentoDisponivel = await validarDataAgendamento(hoje, bot);

        while (i <= 7) {
            let numWeekDay = moment()
                .utcOffset(organizacao.timeZoneOffset)
                .add(i, "days")
                .weekday();
            let dia = moment()
                .utcOffset(organizacao.timeZoneOffset)
                .add(i, "days")
                .format("DD/MM/YYYY");
            let diaSemana = moment()
                .utcOffset(organizacao.timeZoneOffset)
                .weekday(numWeekDay)
                .format("dddd");
            let label = dia + " - (" + diaSemana + ")";

            const dataAgenda = {
                dia: dia,
                diaSemana: diaSemana,
                label: label,
            };

            if (
                dataAgenda.dia ===
                moment().utcOffset(organizacao.timeZoneOffset).format("DD/MM/YYYY")
            ) {
                dataAgenda.label = "" + dataAgenda.dia + " - (Hoje)";
            }

            if (numWeekDay >= 1 && numWeekDay <= 5) {
                next6Days.push(dataAgenda);
            }
            i++;
        }

        const todayDate = moment()
            .utcOffset(organizacao.timeZoneOffset)
            .format("DD/MM/YYYY");

        if (!dataAgendamentoDisponivel) {
            next6Days = next6Days.filter(
                (dataAgenda) => dataAgenda.dia !== todayDate
            );
        }

        const options = [
            ...next6Days.map((dataAgenda) => [dataAgenda.label]),
            ["Inserir data específica"],
            ["Voltar ao menu"],
        ];

        diasDisponiveis = next6Days;

        return options;
    }

    async function listenForDateSelection(bot) {
        let step = 0;
        let selectedDate = null;

        return new Promise((resolve) => {
            bot.onText(/^(.*)$/, (msg, match) => {
                const selectedOptionLabel = match[1];

                if (step === 0) {
                    const selectedOption = diasDisponiveis.find(
                        (opcao) => opcao.label === selectedOptionLabel
                    );

                    if (selectedOption) {
                        selectedDate = selectedOption.dia;
                        step = 1;
                    }
                    if (selectedOptionLabel === "Inserir data específica") {
                        step = 2;
                    }
                    if (selectedOptionLabel === "Voltar ao menu") {
                        bot.removeTextListener(/^(.*)$/);
                        listaOpcoesComandos(msg, bot, listaOpcoesComandos, pessoa);
                    }
                }
                if (step === 2) {
                    selectedDate = selectedOptionLabel;
                    step = 1;
                }

                if (step === 1) {
                    resolve(selectedDate);
                }

                if (step === 0) {
                    bot.sendMessage(
                        msg.chat.id,
                        "Selecione uma data para o agendamento:"
                    );
                }
                if (step === 2) {
                    bot.sendMessage(msg.chat.id, "Insira a data no formato DD/MM/YYYY:");
                }
            });
        });
    }

    const options = await generateDateOptions();

    const replyMarkup = {
        keyboard: options,
        one_time_keyboard: true,
        resize_keyboard: true,
    };

    const opcoesMarkup = {
        reply_markup: JSON.stringify(replyMarkup),
    };

    let message = "";
    bot.removeTextListener(/^(.*)$/);
    message = "Selecione uma data para o agendamento:\n\n";
    bot.sendMessage(msg.chat.id, message, opcoesMarkup);

    const selectedOption = await listenForDateSelection(bot);

    dataAgendamento = formatDateReverse(selectedOption);
    const parametros = {
        dataAgendamento: dataAgendamento,
        organizacaoAgendamento: organizacao,
        tipoAgendamento: tipoAgendamento,
        pessoaAgendamento: pessoa,
    };

    try {
        if (
            !moment(dataAgendamento, "YYYY-MM-DD").isValid() ||
            dataAgendamento <
            moment().utcOffset(organizacao.timeZoneOffset).format("YYYY-MM-DD")
        ) {
            bot.sendMessage(
                msg.chat.id,
                "Data inválida.\n\nPor favor, selecione ou informe outra data válida:"
            );
            return montarDataAgendamento(
                msg,
                bot,
                organizacao,
                listaOpcoesComandos,
                pessoa,
                tipoAgendamento
            );
        }
        if (
            dataAgendamento >
            moment()
                .utcOffset(organizacao.timeZoneOffset)
                .add(1, "year")
                .format("YYYY-MM-DD")
        ) {
            bot.sendMessage(
                msg.chat.id,
                "Só é permitido fazer agendamentos com até um ano de intervalo a partir da data atual.\n\nPor favor, selecione outra data:"
            );
            return montarDataAgendamento(
                msg,
                bot,
                organizacao,
                listaOpcoesComandos,
                pessoa,
                tipoAgendamento
            );
        }
        if (
            dataAgendamento <=
            moment()
                .utcOffset(organizacao.timeZoneOffset)
                .add(1, "year")
                .format("YYYY-MM-DD")
        ) {
            const dataAgendamentoDisponivel = await validarDataAgendamento(
                parametros,
                bot
            );

            if (dataAgendamentoDisponivel) {
                return dataAgendamento;
            }
            if (!dataAgendamentoDisponivel) {
                bot.sendMessage(
                    msg.chat.id,
                    "Data indisponível, selecione outra data:\n"
                );
                return montarDataAgendamento(
                    msg,
                    bot,
                    organizacao,
                    listaOpcoesComandos,
                    pessoa,
                    tipoAgendamento
                );
            }
        }
    } catch (error) {
        console.error(
            "Erro ao verificar a disponibilidade da data de agendamento:",
            error
        );
    }
}

async function montarHorarioAgendamento(
    msg,
    bot,
    organizacao,
    dataAgendamento,
    tipoAgendamento,
    comPreferencia,
    listaOpcoesComandos,
    pessoa,
    profissional,
    reagendar
) {
    try {
        let pAgendamento = {
            pessoaAgendamento: pessoa,
            organizacaoAgendamento: organizacao,
            tipoAgendamento: tipoAgendamento,
            dataAgendamento: dataAgendamento,
            profissionalAgendamento: profissional,
            comPreferencia: comPreferencia,
        };

        let horariosAgendamentos = null;

        horariosAgendamentos = await listarAgendamentosByBot(
            pAgendamento,
            reagendar,
            bot
        );

        if (!horariosAgendamentos) {
            return null;
        }

        let message =
            `Aqui estão os nossos horários de agendamentos disponíveis para a data ` +
            formatDate(dataAgendamento) +
            ` na Empresa ` +
            horariosAgendamentos.data.datas.flat()[0].organizacaoAgendamento.nome +
            `.\n Selecione qual você deseja agendar o seu atendimento:\n\n`;

        const opcoes = [
            ...horariosAgendamentos.data.datas.flat().map((horarioAgendamento) => {
                let dataFormatada = formatDate(horarioAgendamento.dataAgendamento);
                return {
                    dataAgendamento: dataFormatada,
                    horarioAgendamento: horarioAgendamento.horarioAgendamento,
                    organizacaoAgendamento: horarioAgendamento.organizacaoAgendamento,
                    profissionalAgendamento: horarioAgendamento.profissionalAgendamento,
                    label: `${horarioAgendamento.horarioAgendamento} - ${horarioAgendamento.profissionalAgendamento.nomeProfissional}`,
                    endereco: horarioAgendamento.endereco.enderecoCompleto,
                    celularOrganizacao: horarioAgendamento.organizacaoAgendamento.celular,
                };
            }),
            {id: 0, label: "Voltar ao menu"},
        ];

        const keyboard = opcoes.map((opcao) => [opcao.label]);

        const replyMarkup = {
            keyboard,
            one_time_keyboard: true,
            resize_keyboard: true,
        };

        const opcoesMarkup = {
            reply_markup: JSON.stringify(replyMarkup),
        };

        bot.sendMessage(msg.chat.id, message, opcoesMarkup);

        return new Promise((resolve) => {
            bot.onText(/^(.*)$/, async (msg, match) => {
                const selectedOptionLabel = match[1];

                const selectedOption = opcoes.find(
                    (opcao) => opcao.label === selectedOptionLabel
                );

                if (selectedOption) {
                    if (selectedOption.label === "Voltar ao menu") {
                        bot.removeTextListener(/^(.*)$/);
                        listaOpcoesComandos(msg, bot, listaOpcoesComandos, pessoa);
                    }
                    resolve(selectedOption);
                }
                resolve(null);
            });
        });
    } catch (error) {
        let message = `Não há nenhum horário para agendamento na organização e data solicitada disponível para atendimento.`;
        if (profissional) {
            message = `Não há nenhum horário para agendamento na organização, profissional e data solicitada disponível para atendimento.`;
        }
        await bot.sendMessage(msg.chat.id, message);
        return null;
    }
}

module.exports = {montarDataAgendamento, montarHorarioAgendamento};
