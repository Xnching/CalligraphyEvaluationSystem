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
import CustomerService from '@/views/announcementHelp/CustomerService.vue'
import Feedback from '@/views/announcementHelp/Feedback.vue'
import CompetitionList from '@/views/competition/CompetitionList.vue'
import ReviewManagement from '@/views/competition/ReviewManagement.vue'
import ReviewWorks from '@/views/outstanding/ReviewWorks.vue'
import ReviewOutstanding from '@/views/outstanding/ReviewOutstanding.vue'
import HomeworkSubmissions from '@/views/outstanding/HomeworkSubmissions.vue'
import CompetitionSubmissions from '@/views/outstanding/CompetitionSubmissions.vue'
import Type from '@/views/resource/Type.vue'
import AddSource from '@/views/resource/AddSource.vue'
import ResourceManagement from '@/views/resource/ResourceManagement.vue'
import Collection from '@/views/resource/Collection.vue'

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
        name:'User',
        component:User
      },
      {
        path:'userGroup',
        name:'UserGroup',
        component:UserGroup
      },
      {
        path:'region',
        name:'Region',
        component:Region
      },
      {
        path:'school',
        name:'School',
        component:School
      },
      {
        path:'grade',
        name:'Grade',
        component:Grade
      },
      {
        path:'academicYear',
        name:'AcademicYear',
        component:AcademicYear
      },
      {
        path:'klass',
        name:'Klass',
        component:Klass
      },
      {
        path:'student',
        name:'Student',
        component:Student
      },
      {
        path:'teacher',
        name:'Teacher',
        component:Teacher
      },
      {
        path:'sampleWord',
        name:'SampleWord',
        component:SampleWord
      },
      {
        path:'templateWord',
        name:'TemplateWord',
        component:TemplateWord
      },
      {
        path:'copybook',
        name:'Copybook',
        component:Copybook
      },
      {
        path:'template',
        name:'Template',
        component:Template
      },
      {
        path:'define',
        name:'Define',
        component:Define
      },
      {
        path:'releaseAnnouncement',
        name:'ReleaseAnnouncement',
        component:ReleaseAnnouncement
      },
      {
        path:'viewAnnouncement',
        name:'ViewAnnouncement',
        component:ViewAnnouncement
      },
      {
        path:'question',
        name:'Question',
        component:Question
      },
      {
        path:'customerService',
        name:'CustomerService',
        component:CustomerService
      },
      {
        path:'feedback',
        name:'Feedback',
        component:Feedback
      },
      {
        path:'competitionList',
        name:'CompetitionList',
        component:CompetitionList
      },
      {
        path:'reviewManagement',
        name:'ReviewManagement',
        component:ReviewManagement
      },
      {
        path:'reviewWorks',
        name:'ReviewWorks',
        component:ReviewWorks
      },
      {
        path:'reviewOutstanding',
        name:'ReviewOutstanding',
        component:ReviewOutstanding
      },
      {
        path:'homeworkSubmissions',
        name:'HomeworkSubmissions',
        component:HomeworkSubmissions
      },
      {
        path:'competitionSubmissions',
        name:'CompetitionSubmissions',
        component:CompetitionSubmissions
      },
      {
        path:'type',
        name:'Type',
        component:Type
      },
      {
        path:'addSource',
        name:'AddSource',
        component:AddSource
      },
      {
        path:'resourceManagement',
        name:'ResourceManagement',
        component:ResourceManagement
      },
      {
        path:'collection',
        name:'Collection',
        component:Collection
      },
      {
        path: '/home',
        name: 'Home',
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
