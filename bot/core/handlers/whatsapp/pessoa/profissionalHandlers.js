const {
    montarDataAgendamento,
} = require("../agendamento/dataAgendamentoHandlers");

const {
    setProfissionalDisponivel,
} = require("../../../../whatsapp/config/objetos/setProfissional");
const {setState} = require("../../../../whatsapp/config/objetos/setState");
const listarProfissionaisByAgendamentoBot = require("../../../webhooks/pessoa/profissionalWebhooks");

async function montarProfissionaisByAgendamento(
    msg,
    bot,
    organizacao,
    dataAgendamento,
    tipoAgendamento,
    comPreferencia,
    pessoa
) {
    try {
        const profissionais = await listarProfissionaisByAgendamentoBot(
            organizacao,
            dataAgendamento,
            tipoAgendamento,
            0,
            comPreferencia,
            bot
        );

        if (!profissionais || profissionais.data.length === 0) {
            await bot.sendText(
                msg.from,
                "Não há profissionais disponíveis para o dia e horário selecionados."
            );
            montarDataAgendamento(msg, bot, organizacao, false, pessoa);
            return null;
        }

        const opcoes = profissionais.data.map((profissional) => {
            return {
                id: profissional.id,
                profissional: profissional.nomeProfissional,
            };
        });
        opcoes.sort(function (x, y) {
            return x.id - y.id;
        });

        let listaOpcoes = "";
        for (let i = 0; i < opcoes.length; i++) {
            listaOpcoes +=
                "*" + opcoes[i].id + "* - " + opcoes[i].profissional + "\n";
        }
        let message =
            "Aqui estão os nossos *profissionais* disponíveis.\n\nSelecione qual você deseja agendar o seu atendimento:\n\n" +
            listaOpcoes +
            "\n```digite o número de uma das opções```";
        setProfissionalDisponivel(msg.from, opcoes);
        setState(msg.from, "aguardando_profissional");
        await bot.sendText(msg.from, message);
    } catch (error) {
        console.error(
            "Erro ao verificar a disponibilidade do profissional:",
            error
        );
    }
}

async function montarPreferenciaProfissional(msg, bot) {
    const opcoes = [
        {id: 1, nome: "Sim"},
        {id: 2, nome: "Não"},
    ];
    opcoes.sort(function (x, y) {
        return x.id - y.id;
    });
    let listaOpcoes = "";
    for (let i = 0; i < opcoes.length; i++) {
        listaOpcoes += "*" + opcoes[i].id + "* - " + opcoes[i].nome + "\n";
    }

    let message =
        "Você tem preferência por um profissional específico?\n\n" +
        listaOpcoes +
        "\n```digite o número de uma das opções```";

    setState(msg.from, "aguardando_preferenciaProfissional");
    await bot.sendText(msg.from, message);
}

module.exports = {
    montarProfissionaisByAgendamento,
    montarPreferenciaProfissional,
};
