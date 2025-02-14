const organizacaoDisponivel = {};

async function setOrganizacaoDisponivel(chatId, newOrganizacaoDisponivel) {
    if (!organizacaoDisponivel[chatId]) {
        organizacaoDisponivel[chatId] = {};
    }

    if (newOrganizacaoDisponivel) {
        organizacaoDisponivel[chatId].listaOrganizacao = newOrganizacaoDisponivel;
    }
}

async function getOrganizacaoDisponivel(chatId, id) {
    if (!organizacaoDisponivel[chatId]) {
        organizacaoDisponivel[chatId] = {};
    }

    const listaOrganizacao = organizacaoDisponivel[chatId]?.listaOrganizacao;

    if (listaOrganizacao) {
        const organizacao = listaOrganizacao.find((org) => org.id == id);
        return organizacao || null;
    }
    return null;
}

module.exports = {
    organizacaoDisponivel,
    setOrganizacaoDisponivel,
    getOrganizacaoDisponivel,
};
