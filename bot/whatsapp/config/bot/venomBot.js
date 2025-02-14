const {
    confirmarAgendamento,
    confirmarReagendamento,
    listarAgendamentoByPessoa,
    marcarAgendamento,
    montarAgendamento,
    remarcarAgendamento,
} = require("../../../core/handlers/whatsapp/agendamento/agendamentoHandlers");

const venom = require("venom-bot");
const {
    botStates,
    setState,
    reagendar,
    getReagendar,
    setPreferencia,
    getPreferencia,
} = require("../objetos/setState");
const termos = require("../../commands/termos");
const greetings = require("../../commands/greetings");
const {
    getPessoa,
    makeDataNascimentoPessoa,
    makeCpfPessoa,
    confirmarPessoa,
    createPessoa,
    makeNomePessoa,
    makeCelularPessoa,
} = require("../../../core/handlers/whatsapp/pessoa/pessoaHandlers");
const {iniciarAtendimento, listaOpcoes} = require("../../commands/opcoes");

const {
    agendamento,
    setAgendamento,
    getAgendamentoOrganizacao,
    getAgendamentoTipo,
    getAgendamentoData,
    getAgendamentoProfissional,
    getAgendamentoHorario,
} = require("../objetos/setAgendamento");
const montarOrganizacoesByAgendamento = require("../../../core/handlers/whatsapp/organizacao/organizacaoHandlers");
const {
    setOrganizacaoDisponivel,
    getOrganizacaoDisponivel,
    organizacaoDisponivel,
} = require("../objetos/setOrganizacao");
const montarTiposAgendamentoByOrganizacao = require("../../../core/handlers/whatsapp/agendamento/tipoAgendamentoHandlers");
const {getTipoDisponivel, tipoDisponivel} = require("../objetos/setTipo");
const {
    montarDataAgendamento,
    montarHorarioAgendamento,
    listenForDateSelection,
    montarDataAgendamentoIgual,
} = require("../../../core/handlers/whatsapp/agendamento/dataAgendamentoHandlers");
const {
    getDataDisponivel,
    dataDisponivel,
    getDataEscolhida,
    setDataEscolhida,
    dataEscolhida,
} = require("../objetos/setData");
const {
    montarProfissionaisByAgendamento,
    montarPreferenciaProfissional,
} = require("../../../core/handlers/whatsapp/pessoa/profissionalHandlers");
const {
    getProfissionalDisponivel,
    profissionalDisponivel,
} = require("../objetos/setProfissional");
const {
    horarioDisponivel,
    getHorarioDisponivel,
} = require("../objetos/setHorario");
const {
    getAgendamentoConfirmado,
    agendamentoConfirmado,
} = require("../objetos/setAgendamentoConfirmado");
const {
    getAgendamentosReagendar,
} = require("../objetos/setAgendamentosReagendar");
const {
    setReagendamentoConfirmado,
    getReagendamentoConfirmado,
} = require("../objetos/setReagendamentoConfirmado");
const {
    limparAgedamentoById,
    limparBotStateById,
} = require("../objetos/limparObjetos");
const {
    setPessoa,
    getCelularPessoa,
    getPessoaObj,
    getCpfPessoa,
} = require("../objetos/setPessoa");
const {
    isValidDate,
    validaCpf,
    validaCelular,
    dataAgendamento,
} = require("../../../core/utils/validators");
const {formatDateReverse} = require("../../../core/utils/formatters");

const sessionPath = "./session.json";

const config = {
    session: `AgendaFacil`,
    path: sessionPath,
    headless: "new",
    browserArgs: ["--no-sandbox", "--disable-setuid-sandbox"]
};

const saudacoesRegExp =
    /^(oi|ola|iniciar|start|comecar|\/start|olá|oi\s+bot|oi\s+amigo|hey|oi\s+ai|bom\s+dia|boa\s+tarde|boa\s+noite)$/i;

const regexGlobal = /^(.*)$/;

const bot = venom
    .create(config)
    .then((bot) => startBot(bot))
    .catch((error) => console.error("Erro ao iniciar o bot:", error));
bot.tenant = "blackbelt";

function startBot(bot) {
    bot.tenant = "blackbelt";
    bot.onMessage(async (msg) => {
        const chatId = msg.from;

        if (!botStates[chatId]) {
            botStates[chatId] = {
                state: "initial",
            };
        }

        const currentState = botStates[chatId].state;
        const pessoa = botStates[chatId].pessoa;

        switch (currentState) {
            case "initial":
                await termos(msg, bot, chatId);
                setState(chatId, "resposta_termo");
                break;

            case "resposta_termo":
                switch (msg.body) {
                    case "1":
                        await greetings(msg, bot, false);
                        break;
                    case "2":
                        await bot.sendText(
                            chatId,
                            'Precisamos que aceite nossa política de privacidade para que possamos dar continuidade em seu atendimento.\nSe precisar estou á disposição.\nPara me chamar é só mandar um "oi"'
                        );
                        setState(chatId, "initial");
                        break;
                    default:
                        await bot.sendText(
                            chatId,
                            "Desculpe, não entendi.\nPor gentileza, digite *1* para aceitar nossas políticas de privacidade ou *2* para recusá-las."
                        );
                        break;
                }
                break;
            case "aceita_termo":
                try {
                    const identPattern = /^(?:\d{11})$/;
                    const ident = msg.body.replace(/\D/g, "");
                    let cpf, celular;
                    if (identPattern.test(ident)) {
                        if (validaCpf(ident)) {
                            cpf = ident;
                            await getPessoa(cpf, msg, bot, false);
                        }
                        if (!cpf && validaCelular(ident)) {
                            celular = ident;
                            await getPessoa(celular, msg, bot, false);
                        }
                        if (!cpf && !celular) {
                            await bot.sendText(
                                chatId,
                                "Desculpe, não entendi.\nPor gentileza, confirme se o número inserido está seguindo um dos exemplos abaixo.\n\nExemplo celular: (45) 9 9123-4567 ou 45991234567.\n\nExemplo CPF: 012.456.789-00 ou 012345678900."
                            );
                        }
                    }
                    if (!identPattern.test(ident)) {
                        await bot.sendText(
                            chatId,
                            "Desculpe, mas o número informado não possui exatamente 11 dígitos numéricos.\nPor gentileza, confirme se o número inserido está seguindo um dos exemplos abaixo.\n\nExemplo celular: (45) 9 9123-4567 ou 45991234567.\n\nExemplo CPF: 012.456.789-00 ou 012345678900."
                        );
                    }
                } catch (error) {
                    console.error("Erro ao fazer a solicitação GET:", error.message);
                }
                break;

            case "aguardando_makeNomePessoa":
                try {
                    const nomePattern = /^[A-Za-záàâãéèêíïóôõöúçñÁÀÂÃÉÈÍÏÓÔÕÖÚÇÑ ]+$/;
                    const nome = msg.body;
                    if (nomePattern.test(nome)) {
                        await setPessoa(chatId, nome);
                        await makeDataNascimentoPessoa(msg, bot);
                    }
                    if (!nomePattern.test(nome)) {
                        await bot.sendText(
                            chatId,
                            "Desculpe, o nome foi considerado inválido.\nPor gentileza, verifique se foi inserido algum número ou simbolo e retire-o.\n\nDigite o *nome completo* novamente:"
                        );
                    }
                } catch (error) {
                    console.error("Erro ao fazer a solicitação GET:", error.message);
                }
                break;
            case "aguardando_makeDataNascimentoPessoa":
                try {
                    const dataNascimento = msg.body;
                    if (isValidDate(dataNascimento)) {
                        await setPessoa(chatId, false, formatDateReverse(dataNascimento));
                        let celular = await getCelularPessoa(chatId);
                        if (!celular) {
                            await makeCelularPessoa(msg, bot);
                        }
                        if (celular) {
                            await makeCpfPessoa(msg, bot);
                        }
                    }
                    if (!isValidDate(dataNascimento)) {
                        await bot.sendText(
                            chatId,
                            "A data digitada não é válida.\nPor favor, digite a *data* no formato abaixo.\n\nDD/MM/AAAA\nExemplo: 01/01/2000"
                        );
                    }
                } catch (error) {
                    console.error("Erro ao fazer a solicitação GET:", error.message);
                }
                break;
            case "aguardando_makeCelularPessoa":
                try {
                    let contato = msg.body.replace(/\D/g, "");

                    if (validaCelular(contato)) {
                        await setPessoa(chatId, false, false, false, contato);
                        let cpf = await getCpfPessoa(chatId);
                        if (!cpf) {
                            await makeCpfPessoa(msg, bot);
                        }
                        if (cpf) {
                            let pessoObjeto = await getPessoaObj(chatId);
                            await confirmarPessoa(msg, bot, pessoObjeto);
                        }
                    }
                    if (!validaCelular(contato)) {
                        await bot.sendText(
                            chatId,
                            "O celular não é válido.\nPor favor, digite o *número do celular* no formato *DDD + número*."
                        );
                    }
                } catch (error) {
                    console.error("Erro ao fazer a solicitação GET:", error.message);
                }
                break;
            case "aguardando_makeCpfPessoa":
                try {
                    let cpf = msg.body.replace(/\D/g, "");

                    if (validaCpf(cpf)) {
                        await setPessoa(chatId, false, false, cpf);
                        let pessoObjeto = await getPessoaObj(chatId);
                        await confirmarPessoa(msg, bot, pessoObjeto);
                    }
                    if (!validaCpf(cpf)) {
                        await bot.sendText(
                            chatId,
                            "O CPF informado não é válido.\nPor favor, digite o *CPF* no formato 012.456.789-00 ou 01234567899."
                        );
                    }
                } catch (error) {
                    console.error("Erro ao fazer a solicitação GET:", error.message);
                }
                break;
            case "menu":
                switch (msg.body) {
                    case "1":
                        await reagendar(chatId, false);
                        await montarOrganizacoesByAgendamento(msg, bot, pessoa);
                        break;
                    case "2":
                        await reagendar(chatId, true);
                        await listarAgendamentoByPessoa(msg, bot, pessoa, true);
                        break;
                    case "3":
                        await listarAgendamentoByPessoa(msg, bot, pessoa, false);
                        break;
                    case "4":
                        await limparAgedamentoById(chatId);
                        await limparBotStateById(chatId);
                        await bot.sendText(
                            chatId,
                            "Foi um prazer atender você! Se precisar de algum auxilio, sempre estarei disponível para lhe ajudar, basta retornar o contato por meio deste canal.\n\nAté a próxima!"
                        );
                        break;
                    default:
                        await bot.sendText(
                            chatId,
                            "Por gentileza, escolha *digitando apenas o número* correspondente a opção desejada:\n\n*1* - Agendar\n*2* - Reagendar\n*3* - Listar meus agendamentos\n*4* - Finalizar atendimento\n\n```digite o número de uma das opções```"
                        );
                        break;
                }
                break;
            case "aguardando_oldAgendamento":
                try {
                    let escolha = msg.body;
                    const oldAgendamentoSelecinado = await getAgendamentosReagendar(
                        chatId,
                        escolha
                    );

                    if (oldAgendamentoSelecinado) {
                        let reagendar = true;
                        let preferencia = await getPreferencia(chatId);
                        const oldAgendamento = await montarAgendamento(
                            msg,
                            bot,
                            oldAgendamentoSelecinado.organizacao,
                            oldAgendamentoSelecinado.dataAgendamento,
                            oldAgendamentoSelecinado.tipoAgendamento,
                            oldAgendamentoSelecinado.horarioAgendamento,
                            oldAgendamentoSelecinado.profissional,
                            preferencia,
                            pessoa,
                            reagendar,
                            oldAgendamentoSelecinado
                        );
                        setReagendamentoConfirmado(chatId, oldAgendamento);
                        await montarOrganizacoesByAgendamento(msg, bot, pessoa);
                    }
                    if (!oldAgendamentoSelecinado) {
                        await bot.sendText(chatId, "Agendamento não encontrado");
                        return listaOpcoes(msg, bot, true);
                    }
                } catch (error) {
                    console.error("Erro ao fazer a solicitação GET:", error.message);
                }
                break;
            case "aguardando_organização":
                try {
                    let escolha = msg.body;
                    const organizacaoSelecionada = await getOrganizacaoDisponivel(
                        chatId,
                        escolha
                    );

                    if (organizacaoSelecionada) {
                        let tipoAgendamento = await getAgendamentoTipo(chatId);
                        setAgendamento(
                            chatId,
                            pessoa,
                            tipoAgendamento,
                            false,
                            organizacaoSelecionada
                        );
                        montarTiposAgendamentoByOrganizacao(
                            msg,
                            bot,
                            organizacaoSelecionada
                        );
                    }
                    if (!organizacaoSelecionada) {
                        await bot.sendText(chatId, "Organização não encontrada");
                        await montarOrganizacoesByAgendamento(msg, bot, pessoa);
                    }
                } catch (error) {
                    console.error("Erro ao fazer a solicitação GET:", error.message);
                }
                break;
            case "aguardando_tipo":
                try {
                    let escolha = msg.body;
                    const tipoSelecionado = await getTipoDisponivel(chatId, escolha);
                    let organizacao = await getAgendamentoOrganizacao(chatId);
                    if (tipoSelecionado) {
                        setAgendamento(chatId, pessoa, tipoSelecionado);
                        montarDataAgendamento(
                            msg,
                            bot,
                            organizacao,
                            pessoa,
                            tipoSelecionado
                        );
                    }
                    if (!tipoSelecionado) {
                        await bot.sendText(chatId, "Tipo não encontrado");
                        await montarTiposAgendamentoByOrganizacao(msg, bot, organizacao);
                    }
                } catch (error) {
                    console.error("Erro ao fazer a solicitação GET:", error.message);
                }
                break;
            case "aguardando_data":
                try {
                    let escolha = msg.body;
                    const dataSelecionada = await getDataDisponivel(chatId, escolha);
                    let organizacao = await getAgendamentoOrganizacao(chatId);
                    let tipoAgendamento = await getAgendamentoTipo(chatId);
                    if (dataSelecionada) {
                        if (dataSelecionada.id == 6) {
                            await setState(chatId, "data_personalizada");
                            await bot.sendText(
                                chatId,
                                "Por gentileza, digite a *data* seguindo esse formato DD/MM/YYYY.\n\nExemplo: 01/01/2023"
                            );
                        }
                        if (!dataSelecionada.id == 6) {
                            let dataFormatada = await listenForDateSelection(
                                msg,
                                bot,
                                dataSelecionada,
                                false,
                                organizacao,
                                pessoa,
                                tipoAgendamento
                            );
                            if (dataFormatada) {
                                let possuiAgendamento = await dataAgendamento(
                                    dataFormatada,
                                    pessoa
                                );
                                if (!possuiAgendamento) {
                                    setAgendamento(
                                        chatId,
                                        pessoa,
                                        tipoAgendamento,
                                        false,
                                        false,
                                        false,
                                        false,
                                        dataFormatada
                                    );
                                    montarPreferenciaProfissional(msg, bot);
                                }
                                if (possuiAgendamento) {
                                    await setDataEscolhida(chatId, dataFormatada);
                                    await montarDataAgendamentoIgual(msg, bot, dataFormatada);
                                }
                            }
                        }
                    }
                    if (!dataSelecionada) {
                        await bot.sendText(chatId, "Data não encontrada");
                        let tipoAgendamento = await getAgendamentoTipo(chatId);
                        await montarDataAgendamento(
                            msg,
                            bot,
                            organizacao,
                            pessoa,
                            tipoAgendamento
                        );
                    }
                } catch (error) {
                    console.error("Erro ao fazer a solicitação GET:", error.message);
                }
                break;
            case "data_personalizada":
                try {
                    let dataInserida = msg.body;
                    if (isValidDate(dataInserida)) {
                        let tipoAgendamento = await getAgendamentoTipo(chatId);
                        let organizacao = await getAgendamentoOrganizacao(chatId);
                        let dataFormatada = await listenForDateSelection(
                            msg,
                            bot,
                            dataInserida,
                            true,
                            organizacao,
                            pessoa,
                            tipoAgendamento
                        );

                        if (dataFormatada) {
                            let possuiAgendamento = await dataAgendamento(
                                dataFormatada,
                                pessoa
                            );
                            if (!possuiAgendamento) {
                                await setAgendamento(
                                    chatId,
                                    pessoa,
                                    tipoAgendamento,
                                    false,
                                    false,
                                    false,
                                    false,
                                    dataFormatada
                                );
                                montarPreferenciaProfissional(msg, bot);
                            }
                            if (possuiAgendamento) {
                                await setDataEscolhida(chatId, dataFormatada);
                                await montarDataAgendamentoIgual(msg, bot, dataFormatada);
                            }
                        }
                    }
                    if (!isValidDate(dataInserida)) {
                        await bot.sendText(
                            chatId,
                            "A data digitada não é válida.\nPor favor, digite a data no formato abaixo.\n\nDD/MM/AAAA\nExemplo: 01/01/2000"
                        );
                    }
                } catch (error) {
                    console.error("Erro ao fazer a solicitação GET:", error.message);
                }

                break;

            case "aguardando_dataAgendamentoIgual":
                let tipoAgendamento = await getAgendamentoTipo(chatId);
                let organizacao = await getAgendamentoOrganizacao(chatId);
                let dataFormatada = await getDataEscolhida(chatId);
                switch (msg.body) {
                    case "1":
                        await setAgendamento(
                            chatId,
                            pessoa,
                            tipoAgendamento,
                            false,
                            false,
                            false,
                            false,
                            dataFormatada
                        );
                        montarPreferenciaProfissional(msg, bot);
                        break;
                    case "2":
                        if (dataEscolhida[chatId]) {
                            delete dataEscolhida[chatId];
                        }
                        await montarDataAgendamento(
                            msg,
                            bot,
                            organizacao,
                            pessoa,
                            tipoAgendamento,
                            true
                        );
                        break;
                    default:
                        await bot.sendText(chatId, "Desculpe, não entendi.");
                        await montarDataAgendamentoIgual(msg, bot, dataFormatada);
                        break;
                }
                break;
            case "aguardando_preferenciaProfissional":
                try {
                    let organizacao = await getAgendamentoOrganizacao(chatId);
                    let dataAgendamento = await getAgendamentoData(chatId);
                    let tipoAgendamento = await getAgendamentoTipo(chatId);
                    switch (msg.body) {
                        case "1":
                            await setPreferencia(chatId, true);
                            await montarProfissionaisByAgendamento(
                                msg,
                                bot,
                                organizacao,
                                dataAgendamento,
                                tipoAgendamento,
                                true,
                                pessoa
                            );
                            break;
                        case "2":
                            await setPreferencia(chatId, false);
                            let reagendar = await getReagendar(chatId);
                            await montarHorarioAgendamento(
                                msg,
                                bot,
                                organizacao,
                                dataAgendamento,
                                tipoAgendamento,
                                false,
                                pessoa,
                                null,
                                reagendar
                            );
                            break;
                        default:
                            await bot.sendText(chatId, "Desculpe, não entendi.");
                            montarPreferenciaProfissional(msg, bot);
                            break;
                    }
                } catch (error) {
                    console.error("Erro ao fazer a solicitação GET:", error.message);
                }
                break;
            case "aguardando_profissional":
                try {
                    let escolha = msg.body;
                    const profissionalSelecionado = await getProfissionalDisponivel(
                        chatId,
                        escolha
                    );
                    let dataAgendamento = await getAgendamentoData(chatId);
                    let tipoAgendamento = await getAgendamentoTipo(chatId);
                    let preferencia = await getPreferencia(chatId);
                    let organizacao = await getAgendamentoOrganizacao(chatId);
                    if (profissionalSelecionado) {
                        setAgendamento(chatId, pessoa, false, profissionalSelecionado);
                        await montarHorarioAgendamento(
                            msg,
                            bot,
                            organizacao,
                            dataAgendamento,
                            tipoAgendamento,
                            preferencia,
                            pessoa,
                            profissionalSelecionado
                        );
                    }
                    if (!profissionalSelecionado) {
                        await bot.sendText(chatId, "Profissional não encontrado");
                        await montarProfissionaisByAgendamento(
                            msg,
                            bot,
                            organizacao,
                            dataAgendamento,
                            tipoAgendamento,
                            preferencia,
                            pessoa
                        );
                    }
                } catch (error) {
                    console.error("Erro ao fazer a solicitação GET:", error.message);
                }
                break;
            case "Aguardando_horario":
                try {
                    let escolha = msg.body;
                    const horarioSelecionado = await getHorarioDisponivel(
                        chatId,
                        escolha
                    );
                    let organizacao = await getAgendamentoOrganizacao(chatId);
                    let dataAgendamento = await getAgendamentoData(chatId);
                    let tipoAgendamento = await getAgendamentoTipo(chatId);
                    let profissional = await getAgendamentoProfissional(chatId);
                    let reagendar = await getReagendar(chatId);
                    let comPreferencia = await getPreferencia(chatId);
                    if (horarioSelecionado) {
                        await setAgendamento(
                            chatId,
                            pessoa,
                            false,
                            false,
                            false,
                            false,
                            false,
                            false,
                            horarioSelecionado
                        );
                        const agendamento = await montarAgendamento(
                            msg,
                            bot,
                            organizacao,
                            dataAgendamento,
                            tipoAgendamento,
                            horarioSelecionado,
                            profissional,
                            comPreferencia,
                            pessoa,
                            false
                        );
                        if (!reagendar) {
                            confirmarAgendamento(msg, bot, agendamento, pessoa);
                        }
                        if (reagendar) {
                            const oldAgendamento = await getReagendamentoConfirmado(chatId);
                            confirmarReagendamento(
                                msg,
                                bot,
                                oldAgendamento,
                                agendamento,
                                pessoa
                            );
                        }
                    }
                    if (!horarioSelecionado) {
                        await bot.sendText(chatId, "Horário não encontrado");
                        await montarHorarioAgendamento(
                            msg,
                            bot,
                            organizacao,
                            dataAgendamento,
                            tipoAgendamento,
                            comPreferencia,
                            pessoa,
                            profissional,
                            reagendar
                        );
                    }
                } catch (error) {
                    console.error("Erro ao fazer a solicitação GET:", error.message);
                }
                break;
            case "aguardando_confirmação":
                switch (msg.body) {
                    case "1":
                        const pAgendamento = await getAgendamentoConfirmado(chatId);
                        await marcarAgendamento(pAgendamento?.newAgendamento, bot, msg);
                        await listaOpcoes(msg, bot, true);
                        break;
                    case "2":
                        await bot.sendText(chatId, "Agendamento não efetuado.");
                        await limparAgedamentoById(chatId);
                        await listaOpcoes(msg, bot, true);
                        break;
                    case "3":
                        await bot.sendText(chatId, "Agendamento cancelado.");
                        await limparAgedamentoById(chatId);
                        await listaOpcoes(msg, bot, true);
                        break;
                    default:
                        await bot.sendText(chatId, "Desculpe, não entendi.");
                        let organizacao = await getAgendamentoOrganizacao(chatId);
                        let dataAgendamento = await getAgendamentoData(chatId);
                        let tipoAgendamento = await getAgendamentoTipo(chatId);
                        let profissional = await getAgendamentoProfissional(chatId);
                        let horarioAgendamento = await getAgendamentoHorario(chatId);
                        let preferencia = await getPreferencia(chatId);
                        const agendamento = await montarAgendamento(
                            msg,
                            bot,
                            organizacao,
                            dataAgendamento,
                            tipoAgendamento,
                            horarioAgendamento,
                            profissional,
                            preferencia,
                            pessoa,
                            false
                        );
                        confirmarAgendamento(msg, bot, agendamento, pessoa);
                        break;
                }
                break;
            case "aguardando_confirmaçãoReagendar":
                switch (msg.body) {
                    case "1":
                        const pNewAgendamento = await getAgendamentoConfirmado(chatId);
                        const pOldAgendamento = await getReagendamentoConfirmado(chatId);
                        await remarcarAgendamento(
                            pOldAgendamento,
                            pNewAgendamento,
                            bot,
                            msg
                        );
                        await listaOpcoes(msg, bot, true);
                        break;
                    case "2":
                        await bot.sendText(chatId, "Reagendamento não efetuado.");
                        await limparAgedamentoById(chatId);
                        await listaOpcoes(msg, bot, true);
                        break;
                    case "3":
                        await bot.sendText(chatId, "Reagendamento cancelado.");
                        await limparAgedamentoById(chatId);
                        await listaOpcoes(msg, bot, true);
                        break;
                    default:
                        await bot.sendText(chatId, "Desculpe, não entendi.");
                        let organizacao = await getAgendamentoOrganizacao(chatId);
                        let dataAgendamento = await getAgendamentoData(chatId);
                        let tipoAgendamento = await getAgendamentoTipo(chatId);
                        let profissional = await getAgendamentoProfissional(chatId);
                        let horarioAgendamento = await getAgendamentoHorario(chatId);
                        let preferencia = await getPreferencia(chatId);
                        const agendamento = await montarAgendamento(
                            msg,
                            bot,
                            organizacao,
                            dataAgendamento,
                            tipoAgendamento,
                            horarioAgendamento,
                            profissional,
                            preferencia,
                            pessoa,
                            true
                        );
                        const oldAgendamento = await getReagendamentoConfirmado(chatId);
                        confirmarReagendamento(
                            msg,
                            bot,
                            oldAgendamento,
                            agendamento,
                            pessoa
                        );
                        break;
                }
                break;
            case "aguardando_confirmacaoPessoa":
                switch (msg.body) {
                    case "1":
                        try {
                            const pessoaConfirmado = await getPessoaObj(chatId);
                            let response = await createPessoa(pessoaConfirmado);
                            let newPessoa = response.data;
                            await setState(chatId, false, newPessoa);
                            await bot.sendText(chatId, "Cadastro realizado com sucesso!");
                            await iniciarAtendimento(msg, bot, newPessoa);
                        } catch (error) {
                            console.error("Erro ao fazer a solicitação GET:", error.message);
                            await limparAgedamentoById(chatId);
                            await bot.sendText(
                                chatId,
                                "Não foi possível realizar o cadastro."
                            );
                            await greetings(msg, bot, false);
                        }
                        break;
                    case "2":
                        await bot.sendText(chatId, "Vamos corrigir as informações.");
                        await limparAgedamentoById(chatId);
                        await makeNomePessoa(msg, bot, true);
                        break;
                    case "3":
                        await limparBotStateById(chatId);
                        await bot.sendText(
                            chatId,
                            "Foi um prazer atender você! Se precisar de algum auxilio, sempre estarei disponível para lhe ajudar, basta retornar o contato por meio deste canal.\n\nAté a próxima!"
                        );
                        await setState(chatId, "initial");
                        break;
                    default:
                        await bot.sendText(chatId, "Desculpe, não entendi.");
                        let pessoObjeto = await getPessoaObj(chatId);
                        await confirmarPessoa(msg, bot, pessoObjeto);
                        break;
                }
                break;

            default:
                break;
        }
    });
}

async function enviarMensagem(id, mensagem) {
    await bot.sendText(id, mensagem);
}

module.exports = {bot, enviarMensagem};
