const {start} = require("./config/server/app");

const dotenv = require("dotenv");

dotenv.config({path: "../.env"});

start();
