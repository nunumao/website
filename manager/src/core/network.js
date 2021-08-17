// +---------------------------------------------------------------------- 
// | www.365d1.com 
// +---------------------------------------------------------------------- 
// | 功能描述:  网络框架类型
// +---------------------------------------------------------------------- 
// | 时　　间: 2021-06-30 12:25:52 
// +---------------------------------------------------------------------- 
// | 代码创建: 朱荻 <292018748@qq.com> 
// +---------------------------------------------------------------------- 
// | 版本信息: V1.0.0 
// +---------------------------------------------------------------------- 
// | 代码修改:（修改人 - 修改时间） 
// +----------------------------------------------------------------------

import config from "@/config";
import Axios from "axios";
import { ElMessage } from "element-plus";
import Qs from "qs";
import store from "@/store";
import session from './session'

export default class {

    constructor() {
        this.paddingList = [];
        this.refreshing = false;
        Axios.defaults.baseURL = config.BASE_API;
        Axios.defaults.headers.post['Content-Type'] = 'application/x-www-form-urlencoded';
        Axios.defaults.headers.put['Content-Type'] = 'application/x-www-form-urlencoded';
        this.axios = Axios;
        this._interceptors();
    }

    get(api, params = {}, cfg = {}) {
        return new Promise((resolve, reject) => {
            this._request(api, params, 'get', resolve, reject, cfg);
        })
    }

    post(api, params = {}, cfg = {}) {
        return new Promise((resolve, reject) => {
            this._request(api, params, 'post', resolve, reject, cfg);
        });
    }

    put(api, params = {}, cfg = {}) {
        return new Promise((resolve, reject) => {
            this._request(api, params, 'put', resolve, reject, cfg);
        });
    }

    delete(api, params = {}, cfg = {}) {
        return new Promise((resolve, reject) => {
            this._request(api, params, 'delete', resolve, reject, cfg);
        });
    }

    upload() {

    }

    request(api, params = {}, cfg = {}) {
        return Axios.create({ url: api, params: params });
    }

    // 刷新 TOKEN
    _refresh() {
        return new Promise((resolve, reject) => {
            if (!this.refreshing) {                
                let instance = Axios.create({
                    baseURL: config.BASE_API,
                    headers: { 'content-type': 'application/x-www-form-urlencoded' }
                })
                instance.post(config.REFRESH_TOKEN, Qs.stringify({
                    'grantType': 'refresh',
                    'clientId': config.CLIENT_ID,
                    'clientSecret': config.CLIENT_SECRET,
                    'scope': 'all',
                    'refreshToken': store.getters.token.refreshToken
                })).then(response => {
                    this.refreshing = false;
                    let data = response.data;
                    if (data.code == config.SUCCESS_CODE) {
                        store.commit('updateToken', {
                            accessToken: data.result.accessToken,
                            refreshToken: data.result.refreshToken
                        });
                        session.setSession('accessToken', data.result.accessToken);
                        session.setSession('refreshToken', data.result.refreshToken);
                        resolve();
                    } else {
                        console.error('刷新令牌失败');
                        reject();
                    }
                }).catch(e => {
                    this.refreshing = false;
                    console.error('刷新令牌失败', e);
                    reject();
                })
                this.refreshing = true;
            }
        })
    }


    _request(api, params, method, resolve, reject, cfg = {}) {
        if (this.refreshing) {
            this.paddingList.push({ api, params, method, resolve, reject, cfg });
            return;
        }
        let request = null;
        switch (method) {
            case 'get':
                request = this.axios.get(api, { params });
                break;
            case 'post':
                request = this.axios.post(api, Qs.stringify(params));
                break;
            case 'put':
                request = this.axios.put(api, Qs.stringify(params));
                break;
            case 'delete':
                request = this.axios.delete(api, { params });
                break;
        }
        request.then(response => {
            const data = response.data;
            if (data.code == 402) {
                this.paddingList.push({ api, params, method, resolve, reject, cfg });
                this._refresh().then(() => {
                    while (this.paddingList.length > 0) {
                        let retry = this.paddingList.shift();
                        this._request(retry.api, retry.params, retry.method, retry.resolve, retry.reject, retry.cfg);
                    }
                });
                reject();
            }
            if (cfg?.fullResult) {
                resolve(data);
            } else {
                if (data.code == config.SUCCESS_CODE) {
                    resolve({ message: data.message, result: data.result });
                } else {
                    reject(data.message);
                }
            }
        })
    }

    _interceptors() {
        this.axios.interceptors.request.use(
            config => {
                if (store.getters.token.accessToken) {
                    config.headers.Authorization = 'bearer ' + store.getters.token.accessToken;
                }
                return config;
            },
            err => {
                return Promise.reject(err);
            }
        )
        this.axios.interceptors.response.use(response => {
            return response;
        }, error => {
            ElMessage.error('网络连接发生异常~');
            return Promise.reject(error);
        });
    }

}