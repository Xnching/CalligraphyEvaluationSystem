import Vue from 'vue'
import VueRouter from 'vue-router'
import Login from '@/views/Login.vue'

Vue.use(VueRouter)

const routes = [ 
  {
    path: '/login',
    name: 'Login',
    component: Login
  }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

// 提供一个重置路由的方法
export const resetRouter=()=>{
  router.matcher=new VueRouter({
    mode: 'history',
    base: process.env.BASE_URL,
    routes
  })
}
export const setRoutes = () => {
  const storeMenus = localStorage.getItem("menus");
  if (storeMenus) {
    // 拼装动态路由
    const homeRoute = { path: '/', name: 'home', component: () => import('../views/HomeView.vue'), redirect: '/login', children: [] }
    const menus = JSON.parse(storeMenus)
    menus.forEach(item => {
      if (item.path) { // 当且仅当path不为空的时候才去设置路由
        let itemMenu = { path: item.path.replace("/", ""), name: item.name, component: () => import('../views/'+item.pagePath+'.vue') }
        homeRoute.children.push(itemMenu)
      } else if (item.children.length) {
        item.children.forEach(subItem => {
          if (subItem.path) {
            let subItemMenu = { path: subItem.path.replace("/", ""), name: subItem.name, component: () => import('../views/'+subItem.pagePath+'.vue') }
            homeRoute.children.push(subItemMenu)
          }
          // 处理三级菜单
          if (subItem.children && subItem.children.length) {
            subItem.children.forEach(thirdItem => {
              if (thirdItem.path) {
                let thirdItemMenu = { path: thirdItem.path.replace("/", ""), name: thirdItem.name, component: () => import('../views/'+thirdItem.pagePath+'.vue') }
                homeRoute.children.push(thirdItemMenu)
              }
            })
          }
        })
      }
    })
    // 获取当前的路由对象名称数组
    const currentRouteNames = router.getRoutes().map(v => v.name)
    if (!currentRouteNames.includes('home')) {
      // 动态加载到想在的路由对象
      router.addRoute(homeRoute)
    }
  }
}
setRoutes()
export default router

