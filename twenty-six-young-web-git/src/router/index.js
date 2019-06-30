import Vue from 'vue'
import Router from 'vue-router'
import Login from '@/components/Login'
import MainMenu from '@/components/MainMenu'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'Login',
      component: Login
    },
    {
      path: '/',
      name: 'MainMenu',
      redirect: '/views/home/Home',
      component: MainMenu,
      children: [{
        path: '/views/home/Home',
        name: 'Home',
        component: () => import('@/views/home/Home'),
        meta: { requiresAuth: true }
      }, {
        path: '/views/data/JavaLanguageList',
        name: 'JavaLanguageList',
        component: () => import('@/views/data/JavaLanguageList'),
        meta: { requiresAuth: true }
      }, {
        path: '/views/data/ReadingArticle',
        name: 'ReadingArticle',
        component: () => import('@/views/data/ReadingArticle'),
        meta: { requiresAuth: true }
      }, {
        path: '/views/data/SysDictList',
        name: 'SysDictList',
        component: () => import('@/views/data/SysDictList'),
        meta: { requiresAuth: true }
      }, {
        path: '/views/data/WritingArticle',
        name: 'WritingArticle',
        component: () => import('@/views/data/WritingArticle'),
        meta: { requiresAuth: true }
      }
      ]
    }
  ]
})
