const {
    listarPessoaAgendamentosByBot,
} = require("../webhooks/agendamento/agendamentoWebhooks");

function validaCpf(cpf) {
    let Soma;
    let Resto;
    Soma = 0;
    if (cpf == "00000000000") return false;

    for (i = 1; i <= 9; i++)
        Soma = Soma + parseInt(cpf.substring(i - 1, i)) * (11 - i);
    Resto = (Soma * 10) % 11;

    if (Resto == 10 || Resto == 11) Resto = 0;
    if (Resto != parseInt(cpf.substring(9, 10))) return false;

    Soma = 0;
    for (i = 1; i <= 10; i++)
        Soma = Soma + parseInt(cpf.substring(i - 1, i)) * (12 - i);
    Resto = (Soma * 10) % 11;

    if (Resto == 10 || Resto == 11) Resto = 0;
    if (Resto != parseInt(cpf.substring(10, 11))) return false;
    return true;
}

function validaCelular(numero) {
    const celularPattern =
        /^(11|12|13|14|15|16|17|18|19|21|22|24|27|28|31|32|33|34|35|37|38|41|42|43|44|45|46|47|48|49|51|53|54|55|61|62|63|64|65|66|67|68|69|71|73|74|75|77|79|81|82|83|84|85|86|87|88|89|91|92|93|94|95|96|97|98|99)(9)\d{8}$/;
    if (celularPattern.test(numero)) {
        return true;
    }
    return false;
}

function isValidDate(dateString) {
    const datePattern = /^\d{2}\/\d{2}\/\d{4}$/;
    if (!datePattern.test(dateString)) return false;

    const dateParts = dateString.split("/");
    const day = parseInt(dateParts[0]);
    const month = parseInt(dateParts[1]) - 1;
    const year = parseInt(dateParts[2]);
    const date = new Date(year, month, day);

    return (
        date.getDate() === day &&
        date.getMonth() === month &&
        date.getFullYear() === year
    );
}

async function dataAgendamento(dataEscolhida, pessoa) {
    const agendamentos = await listarPessoaAgendamentosByBot(pessoa, false);

    const result = agendamentos.data.find(
        (data) => data.dataAgendamento === dataEscolhida
    );

    if (result) {
        return true;
    }
    return false;
}

function isValidBirthDay(dateString) {
    const datePattern = /^\d{2}\/\d{2}\/\d{4}$/;
    if (!datePattern.test(dateString)) return false;

    const dateParts = dateString.split("/");
    const day = parseInt(dateParts[0]);
    const month = parseInt(dateParts[1]) - 1;
    const year = parseInt(dateParts[2]);
    const inputDate = new Date(year, month, day);
    if (
        inputDate.getDate() !== day ||
        inputDate.getMonth() !== month ||
        inputDate.getFullYear() !== year
    ) {
        return false;
    }
    const currentDate = new Date();
    return inputDate <= currentDate;

}

module.exports = {validaCpf, validaCelular, isValidDate, dataAgendamento, isValidBirthDay};
