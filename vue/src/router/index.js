// 导入组件
import Vue from 'vue';
import Router from 'vue-router';
// 登录
import login from '@/views/login';
// 首页
import index from '@/views/index';
/**
 * 基本管理
 */
//用户管理
import Users from '@/views/basic/Users'
//写博客
import WriteBlog from '@/views/basic/WriteBlog'
/**
 * 系统管理
 */
//博客管理
import Blogs from "@/views/system/Blogs";
//点赞信息
import Likes from "@/views/system/Likes"
//标签管理
import Labels from "@/views/system/Labels"
//评论管理
import Comments from "@/views/system/Comments"


// 启用路由
Vue.use(Router);

// 导出路由
export default new Router({
    routes: [{
        path: '/',
        name: '',
        component: login,
        hidden: true,
        meta: {
            requireAuth: false
        }
    }, {
        path: '/login',
        name: '登录',
        component: login,
        hidden: true,
        meta: {
            requireAuth: false
        }
    }, {
        path: '/index',
        name: '首页',
        component: index,
        iconCls: 'el-icon-tickets',
        children: [{
          path: '/basic/user',
          name: '用户管理',
          component: Users,
          meta: {
            requireAuth: true
          }
        }, {
            path: '/system/blog',
            name: '博客管理',
            component: Blogs,
            meta: {
              requireAuth: true
            }
        }, {
          path: '/system/like',
          name: '点赞信息',
          component: Likes,
          meta: {
            requireAuth: true
          }
        }, {
          path: '/system/comment',
          name: '评论管理',
          component: Comments,
          meta: {
            requireAuth: true
          }
        }, {
          path: '/system/label',
          name: '标签管理',
          component: Labels,
          meta: {
            requireAuth: true
          }
        }, {
          path: '/basic/write',
          name: '写博客',
          component: WriteBlog,
          meta: {
            requireAuth: true
          }
        }
        ]
    }]
})
