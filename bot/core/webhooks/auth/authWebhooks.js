const {endpoints, tenant} = require("../../endpoints/endpoints");

async function login(api, login, password, bot) {
    try {
        const axios = axiosApiNewInstance(bot.tenant);
        return await axios.post(endpoints.login.auth, {
            login,
            password,
        });
    } catch (error) {
        console.error("Erro ao autenticar:", error.message);
    }
}

async function refresh(api, refreshToken, bot) {
    try {
        const axios = axiosApiNewInstance(bot.tenant);
        return await axios.post(endpoints.login.refresh, {
            refreshToken: refreshToken,
        });
    } catch (error) {
        console.error("Erro ao autenticar:", error.message);
    }
}

module.exports = {login, refresh};
