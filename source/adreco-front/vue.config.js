const path = require("path");

module.exports = {
    devServer: {
        proxy: {
            "/api": {
                target: "http://localhost:9000/api",
                changeOrigin: true,
                pathRewrite: {
                    "^/api": "",
                },
            },
        },
    },

    configureWebpack: {
        resolve: {
          alias: {
            "@": path.resolve(__dirname, "src/"),
            "@assets": path.resolve(__dirname, "src/assets"),
            "@components": path.resolve(__dirname, "src/components"),
            "@mixins": path.resolve(__dirname, "src/mixins"),
            "@views": path.resolve(__dirname, "src/views"),
          },
        },
      },

    pluginOptions: {
      express: {
        shouldServeApp: true,
        serverDir: 'y'
      }
    }
}
