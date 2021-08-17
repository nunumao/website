import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'

const app = createApp(App);

// ------------------------
// |       框架配置         |
// ------------------------

import config from './config'
app.config.globalProperties.$config = config;

import session from './core/session'
app.config.globalProperties.$session = session;

import './core/extend'

// ------------------------
// |        UI框架         |
// ------------------------

import elementPlus from 'element-plus';
import './assets/theme/index.css'
import 'dayjs/locale/zh-cn'
import locale from 'element-plus/lib/locale/lang/zh-cn'
app.use(elementPlus, { locale })

import components from './components'
import './assets/scss/style.scss'
app.use(components)

// ------------------------
// |       网络配置         |
// ------------------------
import network from './core/network';
app.config.globalProperties.$axios = new network();

// ------------------------
// |         路由          |
// ------------------------
router.beforeEach((to, from, next) => {
    document.title = to.meta.name ? to.meta.name + ' | ' + config.SYSTEM_NAME : config.SYSTEM_NAME;
    store.commit('updateUiTitle', '')
    if (to.meta.login === false) {
        next();
    } else {
        if (store.getters.token.accessToken.length == 0) {
            const sessionAccessToken = session.getSession('accessToken');
            const sessionRefreshToken = session.getSession('refreshToken');
            if (sessionAccessToken && sessionRefreshToken) {
                store.commit('updateToken', {
                    accessToken: sessionAccessToken,
                    refreshToken: sessionRefreshToken
                });
                store.dispatch('initMenu', app).then(() => {
                    const active = session.getSession('active');
                    store.commit('updateMenuActive', active);
                    router.push({ path: to.fullPath, query: to.query })
                })
            } else {
                next({
                    path: '/login',
                    query: {
                        redirect: to.fullPath
                    }
                })
            }
        } else {
            store.dispatch('initMenu', app).then(() => {
                next()
            })
        }
    }
})


app.use(router)
app.use(store)
app.mount('#app')