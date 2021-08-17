import { createStore } from 'vuex'

export default createStore({
    state: {
        token: {
            accessToken: '',
            refreshToken: ''
        },
        ui: {
            pageTitle: '页面标题'
        },
        menu: {
            active: -1,
            list: []
        }
    },
    getters: {
        token: state => state.token,
        ui: state => state.ui,
        menu: state => state.menu
    },
    mutations: {
        updateToken(state, data) {
            state.token = {
                accessToken: data.accessToken,
                refreshToken: data.refreshToken
            }
        },
        clearToken(state) {
            state.token = {
                accessToken: '',
                refreshToken: ''
            }
        },
        updateUiTitle(state, title) {
            state.ui.pageTitle = title;
        },
        updateMenu(state, list) {
            state.menu.list = list;
        },
        updateMenuActive(state, index) {
            state.menu.active = index;
        }
    },
    actions: {
        initMenu(context, app) {
            return new Promise((resolve, reject) => {
                if (!Array.isArray(context.getters.menu.list) || context.getters.menu.list.length <= 0) {
                    const modules = import.meta.glob('../view/**/*.vue')
                    const $axios = app.config.globalProperties.$axios;
                    $axios.get('manager/rule/menu').then(data => {
                        data.result.forEach(item => {
                            if (item.child) {
                                item.child.forEach(jtem => {
                                    if (jtem.child) {
                                        jtem.child.forEach(ktem => {
                                            if (ktem.menu == 1) {
                                                jtem.path = ktem.path;
                                                jtem.apis = ktem.apis;
                                            }
                                            app.config.globalProperties.$router.addRoute('frame', {
                                                path: `/${ktem.url}`,
                                                component: modules[`../view/${ktem.path}.vue`]
                                            })
                                        })
                                    }
                                });
                            }
                        })
                        context.commit('updateMenu', data.result);
                        resolve()
                    }).catch(e => {
                        resolve()
                    })
                } else {
                    resolve();
                }
            })
        }
    },
    modules: {
    }
})
