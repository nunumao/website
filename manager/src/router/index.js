import { createRouter, createWebHashHistory } from 'vue-router'

let subRoutes = [];

const routes = [
    {
        path: '/login',
        meta: {
            login: false,
            name: '登录'
        },
        component: () => import('@/view/login.vue')
    },
    {
        name: 'frame',
        path: '/',
        component: () => import('@/view/index.vue'),
        children: [...[
            {
                path: '',
                redirect: '/dashboard'
            }, {
                path: '/dashboard',
                meta: { name: '控制台' },
                component: () => import('@/view/dashboard.vue')
            }, {
                path: '/system/rule/list',
                meta: { name: '菜单管理' },
                component: () => import('@/view/system/rule/list.vue')
            }, {
                path: '/system/rule/add',
                meta: { name: '菜单管理' },
                component: () => import('@/view/system/rule/edit.vue')
            }, {
                path: '/system/rule/edit',
                meta: { name: '菜单管理' },
                component: () => import('@/view/system/rule/edit.vue')
            }
        ], ...subRoutes]
    }, {
        path: '/test',
        meta: {
            name: '测试页面'
        },
        component: () => import('@/view/test.vue')
    }, {
        path: '/:pathMatch(.*)*',
        meta: {
            name: '404'
        },
        component: () => import('@/view/error.vue')
    }
]

const router = createRouter({
    history: createWebHashHistory(),
    routes
})

export default router
