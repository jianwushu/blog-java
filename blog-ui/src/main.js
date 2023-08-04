import Vue from 'vue'
import App from './App.vue'
import router from './router'

import 'aplayer/dist/APlayer.min.css'
import 'vditor/dist/index.css'
import bus from "components/eventBus"
Vue.config.productionTip = false

router.beforeEach((to, from, next) => {
  //设置标题
  if (to.meta.title) {
    document.title = to.meta.title
  }
  next()
})
new Vue({

  router,
  render: h => h(App),
  mounted(){
    this.getSeo()
  },
  methods:{
    getSeo(){
      bus.$on('getOptionData',(options)=>{
        document.querySelector('meta[name="keywords"]').setAttribute('content', options.keywords)
        document.querySelector('meta[name="description"]').setAttribute('content', options.description)
      })
    }
  }
}).$mount('#app')
