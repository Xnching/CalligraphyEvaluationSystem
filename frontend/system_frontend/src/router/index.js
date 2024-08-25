import Vue from 'vue'
import VueRouter from 'vue-router'
import Login from '@/views/Login.vue'
import CompetitionDivision from '@/views/competition/ReviewMangement/CompetitionDivision.vue'
import DtailedCompetition from '@/views/competition/DtailedCompetition.vue'
import FinalView from '@/views/competition/ReviewWorks/finalView.vue'
import JuniorView from '@/views/competition/ReviewWorks/juniorView.vue'
import DetailedReview from '@/views/competition/ReviewMangement/DetailedReview.vue'
import DetailedJuniorReview from '@/views/competition/ReviewMangement/DetailedJuniorReview.vue'
import DetailedFinalReview from '@/views/competition/ReviewMangement/DetailedFinalReview.vue'

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
            // 添加跳转的路由
          if (subItem.path === '/backend/competitionList') {
            console.log('ssssssssss'); // 打印所有路由信息
            homeRoute.children.push({ path: '/backend/competitionList/:competition', name: '竞赛列表-竞赛组别详情' ,component: DtailedCompetition });
          } else if (subItem.path === '/backend/reviewManagement') {
            homeRoute.children.push({ path: '/backend/reviewManagement/:competitionDivision', name: '评阅管理-竞赛组别详情', component: CompetitionDivision });
            homeRoute.children.push({ path: '/backend/reviewManagement/teacher/:teacherName', name: '评阅管理-教师评阅详情', component: DetailedReview });
            // homeRoute.children.push({ path: '/backend/reviewManagement/teacher/:teacherName/junior/:id', name: '评阅管理-教师评阅详情-初级作品评阅详情', component: DetailedJuniorReview });
            // homeRoute.children.push({ path: '/backend/reviewManagement/teacher/:teacherName/final/:id', name: '评阅管理-教师评阅详情-最终作品评阅详情', component: DetailedFinalReview });
          } else if (subItem.path === '/backend/reviewWorks') {
            homeRoute.children.push({ path: '/backend/reviewWorks/junior/:id', name: '作品评阅-初级作品评阅详情', component: JuniorView });
            homeRoute.children.push({ path: '/backend/reviewWorks/final/:id', name: '作品评阅-最终作品评阅详情', component: FinalView });
          }
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
    console.log(router.getRoutes()); // 打印所有路由信息
  }
}
setRoutes()
export default router

