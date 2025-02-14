const {axiosApiNewInstance} = require("../../config/axios/apiService");
const {endpoints, tenant} = require("../../endpoints/endpoints");

async function getPessoaByPhone(telefone, bot) {
    try {
        const axios = axiosApiNewInstance(bot.tenant);
        return await axios.get(endpoints.pessoa.getByPhone(telefone));
    } catch (error) {

    }
}

async function getPessoaByCPF(cpf, bot) {
    try {
        const axios = axiosApiNewInstance(bot.tenant);
        return await axios.get(endpoints.pessoa.getByCPF(cpf));
    } catch (error) {

    }
}

async function getPessoaByIdent(ident, bot) {
    try {
        const axios = axiosApiNewInstance(bot.tenant);
        return await axios.get(endpoints.pessoa.getByIdent(ident));
    } catch (error) {

    }
}

async function getPessoaByTelegram(telegramId, bot) {
    try {
        const axios = axiosApiNewInstance(bot.tenant);
        let response = await axios.get(endpoints.pessoa.getByTelegram(telegramId));
        return response;
    } catch (error) {

    }
}

async function createPessoaByBot(pessoa, bot) {
    const axios = axiosApiNewInstance(bot.tenant);
    let response = await axios.post(endpoints.pessoa.createPessoa, pessoa);
    return response;
}

async function updatePessoaByBot(pessoa, telegramId, bot) {
    const axios = axiosApiNewInstance(bot.tenant);
    let response = await axios.post(endpoints.pessoa.updatePessoa(telegramId),
        pessoa
    );
    return response;
}

async function removeTelegramIdPessoaByBot(pessoa, telegramId, bot) {
    const axios = axiosApiNewInstance(bot.tenant);
    let response = await axios.delete(endpoints.pessoa.removeTelegramId(telegramId),
        pessoa
    );
    return response;
}

module.exports = {
    getPessoaByPhone,
    getPessoaByCPF,
    getPessoaByIdent,
    getPessoaByTelegram,
    createPessoaByBot,
    updatePessoaByBot,
    removeTelegramIdPessoaByBot,
};
