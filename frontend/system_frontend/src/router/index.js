import Vue from 'vue'
import VueRouter from 'vue-router'
import HomeView from '../views/HomeView.vue'
import User from '../views/backend/User.vue'
import UserGroup from '@/views/backend/UserGroup.vue'
import Region from '@/views/base/Region.vue'
import School from '@/views/base/School.vue'
import Grade from '@/views/base/Grade.vue'
import AcademicYear from '@/views/base/AcademicYear.vue'
import Klass from '@/views/frontend/Klass.vue'
import Student from '@/views/frontend/Student.vue'
import Teacher from '@/views/frontend/Teacher.vue'
import SampleWord from '@/views/word/SampleWord.vue'
import TemplateWord from '@/views/word/TemplateWord.vue'
import Copybook from '@/views/word/Copybook.vue'
import Template from '@/views/word/Template.vue'
import Define from '@/views/word/Define.vue'
import ReleaseAnnouncement from '@/views/announcementHelp/ReleaseAnnouncement.vue'
import ViewAnnouncement from '@/views/announcementHelp/ViewAnnouncement.vue'
import Question from '@/views/announcementHelp/Question.vue'
import Feedback from '@/views/announcementHelp/Feedback.vue'
import CompetitionList from '@/views/competition/CompetitionList.vue'
import ReviewManagement from '@/views/competition/ReviewManagement.vue'
import ReviewWorks from '@/views/competition/ReviewWorks.vue'
import ReviewOutstanding from '@/views/outstanding/ReviewOutstanding.vue'
import HomeworkSubmissions from '@/views/outstanding/HomeworkSubmissions.vue'
import CompetitionSubmissions from '@/views/outstanding/CompetitionSubmissions.vue'
import Type from '@/views/resource/Type.vue'
import AddSource from '@/views/resource/AddSource.vue'
import ResourceManagement from '@/views/resource/ResourceManagement.vue'
import Collection from '@/views/resource/Collection.vue'
import CompetitionDivision from '@/views/competition/ReviewMangement/CompetitionDivision.vue'
import DtailedCompetition from '@/views/competition/DtailedCompetition.vue'
import FinalView from '@/views/competition/ReviewWorks/finalView.vue'
import JuniorView from '@/views/competition/ReviewWorks/juniorView.vue'
import DetailedReview from '@/views/competition/ReviewMangement/DetailedReview.vue'
import DetailedJuniorReview from '@/views/competition/ReviewMangement/DtailedJunoirReview.vue'
import DetailedFinalReview from '@/views/competition/ReviewMangement/DetailedFinalReview.vue'
import DataAnalysis from '@/views/analysis/DataAnalysis.vue'
import BehaviorAnalysis from '@/views/analysis/BehaviorAnalysis.vue'
import FontAnalysis from '@/views/analysis/FontAnalysis.vue'
import ResourceAnalysis from '@/views/analysis/ResourceAnalysis.vue'


//此文件路由
Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'home',
    redirect:'/home',
    component: HomeView,
    children:[
      {
        path:'user',
        name:'系统用户管理',
        component:User
      },
      {
        path:'userGroup',
        name:'用户组管理',
        component:UserGroup
      },
      {
        path:'region',
        name:'区域管理',
        component:Region
      },
      {
        path:'school',
        name:'学校管理',
        component:School
      },
      {
        path:'grade',
        name:'年级管理',
        component:Grade
      },
      {
        path:'academicYear',
        name:'学年管理',
        component:AcademicYear
      },
      {
        path:'klass',
        name:'班级管理',
        component:Klass
      },
      {
        path:'student',
        name:'学生管理',
        component:Student
      },
      {
        path:'teacher',
        name:'教师管理',
        component:Teacher
      },
      {
        path:'sampleWord',
        name:'样本字管理',
        component:SampleWord
      },
      {
        path:'templateWord',
        name:'模板字管理',
        component:TemplateWord
      },
      {
        path:'copybook',
        name:'字帖管理',
        component:Copybook
      },
      {
        path:'template',
        name:'模板管理',
        component:Template
      },
      {
        path:'define',
        name:'定义管理',
        component:Define
      },
      {
        path:'dataAnalysis',
        name:'用户数据分析',
        component:DataAnalysis
      },
      {
        path:'behaviorAnalysis',
        name:'用户行为分析',
        component:BehaviorAnalysis
      },
      {
        path:'fontAnalysis',
        name:'字库分析分析',
        component:FontAnalysis
      },
      {
        path:'resourceAnalysis',
        name:'书法知识资源分析分析',
        component:ResourceAnalysis
      },
      {
        path:'releaseAnnouncement',
        name:'发布公告',
        component:ReleaseAnnouncement
      },
      {
        path:'viewAnnouncement',
        name:'查看公告',
        component:ViewAnnouncement
      },
      {
        path:'question',
        name:'常见问题与答案设置',
        component:Question
      },
      {
        path:'feedback',
        name:'反馈管理',
        component:Feedback
      },
      {
        path:'competitionList',
        name:'竞赛列表',
        component:CompetitionList
      },
      {
        path:'competitionList/:competition',
        name:'竞赛列表-竞赛组别详情',
        component:DtailedCompetition
      },
      {
        path:'reviewManagement',
        name:'评阅管理',
        component:ReviewManagement
      },
      {
        path:'reviewManagement/:competitionDivision',
        name:'评阅管理-竞赛组别详情',
        component:CompetitionDivision
      },
      {
        path:'reviewManagement/teacher/:teacherName',
        name:'评阅管理-教师评阅详情',
        component:DetailedReview
      },
      {
        path:'reviewManagement/teacher/:teacherName/junior/:id',
        name:'评阅管理-教师评阅详情-初级作品评阅详情',
        component:DetailedJuniorReview
      },
      {
        path:'reviewManagement/teacher/:teacherName/final/:id',
        name:'评阅管理-教师评阅详情-最终作品评阅详情',
        component:DetailedFinalReview
      },
      {
        path:'reviewWorks',
        name:'作品评阅',
        component:ReviewWorks
      },
      {
        path:'reviewWorks/junior/:id',
        name:'作品评阅-初级作品评阅详情',
        component:JuniorView
      },
      {
        path:'reviewWorks/final/:id',
        name:'作品评阅-最终作品评阅详情',
        component:FinalView
      },
      {
        path:'reviewOutstanding',
        name:'作业评审',
        component:ReviewOutstanding
      },
      {
        path:'homeworkSubmissions',
        name:'作业作品',
        component:HomeworkSubmissions
      },
      {
        path:'competitionSubmissions',
        name:'竞赛作品',
        component:CompetitionSubmissions
      },
      {
        path:'type',
        name:'类型管理',
        component:Type
      },
      {
        path:'addSource',
        name:'添加书法知识',
        component:AddSource
      },
      {
        path:'resourceManagement',
        name:'资源管理',
        component:ResourceManagement
      },
      {
        path:'collection',
        name:'资源管理',
        component:Collection
      },
      {
        path: '/home',
        name: '主页',
        // route level code-splitting
        // this generates a separate chunk (about.[hash].js) for this route
        // which is lazy-loaded when the route is visited.
        component: () => import(/* webpackChunkName: "about" */ '../views/Home.vue')
      }
    ]
  }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
