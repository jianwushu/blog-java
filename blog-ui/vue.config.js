
const port = process.env.port || process.env.npm_config_port ||80// 端口
module.exports = {
  publicPath: process.env.NODE_ENV === "production" ? "/" : "/",
  outputDir: 'dist',
  // 用于放置生成的静态资源 (js、css、img、fonts) 的；（项目打包之后，静态资源会放在这个文件夹下）
  assetsDir: 'static',
  configureWebpack: {
    resolve: {
      alias: {
        'assets': '@/assets',
        'components': '@/components',
        'views': '@/views',
        'utils': '@/utils'
      }
    }
  },
  devServer: {
    host: '0.0.0.0',
    port: port,
    open: true,
    proxy:{
      [process.env.VUE_APP_BASE_API]: {
        target: `http://10.1.1.83:8083`,
        changeOrigin: true,
        pathRewrite: {
          ['^' + process.env.VUE_APP_BASE_API]: ''
        }
      }
    },
    disableHostCheck: true
  }
}

