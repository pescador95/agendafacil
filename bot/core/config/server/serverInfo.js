const logo = require("./logo");

function appendServerInfo() {
    const os = require("os");

    const format = (bytes) => {
        return (bytes / (1024 * 1024)).toFixed(2) + " MB";
    };

    const totalMemory = os.totalmem();
    const freeMemory = os.freemem();
    const maxMemory = Math.max(
        os.totalmem() - os.freemem(),
        os.totalmem() * 0.15
    );

    let info = "";

    let lineFeed = "\n";

    info +=
        "========================== Memory Info ==========================" +
        lineFeed;
    info += "Free memory: " + format(freeMemory) + lineFeed;
    info += "Allocated memory: " + format(totalMemory - freeMemory) + lineFeed;
    info += "Max memory: " + format(maxMemory) + lineFeed;
    info +=
        "Total free memory: " +
        format(freeMemory + (maxMemory - (totalMemory - freeMemory))) +
        lineFeed;
    info +=
        "=================================================================" +
        lineFeed;
    info += "Using Node.js " + process.version + lineFeed;
    info += "Started in: " + new Date().toLocaleString() + lineFeed;
    info +=
        "=================================================================" +
        lineFeed;

    info += logo();
    return info;
}

function serverInfo() {
    console.log(appendServerInfo());
}

module.exports = {serverInfo, appendServerInfo};
