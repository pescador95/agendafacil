const pessoa = {};

async function setPessoa(chatId, nome, dataNascimento, cpf, celular) {
    if (!pessoa[chatId]) {
        pessoa[chatId] = {};
    }
    if (await nome) {
        pessoa[chatId].nome = await nome;
    }
    if (await dataNascimento) {
        pessoa[chatId].dataNascimento = await dataNascimento;
    }
    if (await cpf) {
        pessoa[chatId].cpf = await cpf;
    }
    if (await celular) {
        pessoa[chatId].celular = await celular;
    }
}

async function getPessoaObj(chatId) {
    if (pessoa[chatId]) {
        return pessoa[chatId];
    }
    return null;
}

async function getNomePessoa(chatId) {
    if (!pessoa[chatId]) {
        pessoa[chatId] = {};
    }

    const nome = pessoa[chatId]?.nome;
    if (nome) {
        return nome;
    }
    return null;
}

async function getDataNascimentoPessoa(chatId) {
    if (!pessoa[chatId]) {
        pessoa[chatId] = {};
    }

    const dataNascimento = pessoa[chatId]?.dataNascimento;
    if (dataNascimento) {
        return dataNascimento;
    }
    return null;
}

async function getCpfPessoa(chatId) {
    if (!pessoa[chatId]) {
        pessoa[chatId] = {};
    }

    const cpf = pessoa[chatId]?.cpf;
    if (cpf) {
        return cpf;
    }
    return null;
}

async function getCelularPessoa(chatId) {
    if (!pessoa[chatId]) {
        pessoa[chatId] = {};
    }

    const celular = pessoa[chatId]?.celular;
    if (celular) {
        return celular;
    }
    return null;
}

module.exports = {
    pessoa,
    setPessoa,
    getPessoaObj,
    getNomePessoa,
    getDataNascimentoPessoa,
    getCpfPessoa,
    getCelularPessoa,
};
