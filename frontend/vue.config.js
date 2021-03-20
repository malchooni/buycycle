const path = require("path");

module.exports = {
  "transpileDependencies": [
    "vuetify"
  ],
  outputDir: path.resolve(
    __dirname,
    "../src/main/resources/static"
  ),
  devServer: {
    proxy: {
      "/data": {
        target: "http://localhost:7771",
        ws: true,
        chageOrgin: true
      }
    }
  }
}