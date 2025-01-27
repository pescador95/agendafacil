const axios = require("axios");

let baseURL = process.env.QUARKUS_BASEURL || `${QUARKUS_BASEURL}`;

function axiosApiNewInstance(tenant) {
    return axios.create({
        baseURL: baseURL,
        headers: {
            "Content-Type": "application/json",
            "X-Tenant": tenant
        },
    });
}

module.exports = {
    axiosApiNewInstance
};
