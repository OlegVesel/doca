const { defineConfig } = require('@vue/cli-service')

let baseUrl = {
  target: 'http://localhost:7777',
  ws: true,
  changeOrigin: true
};

module.exports = defineConfig({
  transpileDependencies: [
    'vuetify'
  ],
  devServer: {
    port: 3000,
    proxy: {
      '/api/*' : baseUrl,
      '/auth/*' : baseUrl,
    }
  }
})
