import Vue from 'vue'
import ElementUI from 'element-ui'              //引入Element UI时添加
import 'element-ui/lib/theme-chalk/index.css'   //引入Element UI时添加，也不知道干啥
import App from './App.vue'
import router from './router'
import './assets/gloable.css'       //添加优化侧边栏样式
import request from '@/utils/request'
import store from './store/index'

Vue.config.productionTip = false
Vue.use(ElementUI);

Vue.prototype.request=request

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')
