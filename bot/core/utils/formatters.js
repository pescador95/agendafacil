function formatCelular(celular) {
    const formattedCelular = celular
        .toString()
        .replace(/(\d{2})(\d{1})(\d{4})(\d{4})/, "($1) $2 $3-$4");
    return formattedCelular;
}

function formatDate(data) {
    const [ano, mes, dia] = data.split("-");
    const formattedDate = `${dia}/${mes}/${ano}`;
    return formattedDate;
}

function formatDateReverse(data) {
    const [dia, mes, ano] = data.split("/");
    const formattedDate = `${ano}-${mes}-${dia}`;
    return formattedDate;
}

function formatTime(horario) {
    const formattedTime = horario.slice(0, 5);
    return formattedTime;
}

function formatCPF(cpf) {
    const cpfLimpo = cpf.replace(/\D/g, "");

    if (cpfLimpo.length !== 11) {
        console.error("O CPF deve ter exatamente 11 dígitos.");
    }

    const cpfFormatado = cpfLimpo.replace(
        /(\d{3})(\d{3})(\d{3})(\d{2})/,
        "$1.$2.$3-$4"
    );
    return cpfFormatado;
}

module.exports = {
    formatCelular,
    formatDate,
    formatTime,
    formatDateReverse,
    formatCPF,
};
