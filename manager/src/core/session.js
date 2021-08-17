// +----------------------------------------------------------------------
// | 广西西途比网络科技有限公司 www.c2b666.com
// +----------------------------------------------------------------------
// | 功能描述: session 辅助类
// +----------------------------------------------------------------------
// | 时　　间: 2018-10-08T15:18:11+08:00
// +----------------------------------------------------------------------
// | 代码创建: 朱荻 <292018748@qq.com>
// +----------------------------------------------------------------------
// | 版本信息: V1.0.0
// +----------------------------------------------------------------------
// | 代码修改:（修改人 - 修改时间）
// +----------------------------------------------------------------------


import config from '@/config'
import CryptoJS from 'crypto-js'
export default class SessionUtils {

    static setSession(key, value) {
        if (typeof (value) != 'undefined' && value != '' && value != null) {
            const ciphertext = CryptoJS.AES.encrypt(value, config.SESSION_SECRET).toString();
            sessionStorage.setItem(key, ciphertext);
        }
    }

    static getSession(key) {
        const ciphertext = sessionStorage.getItem(key);
        if (ciphertext !== null) {
            const bytes = CryptoJS.AES.decrypt(ciphertext, config.SESSION_SECRET);
            const originalText = bytes.toString(CryptoJS.enc.Utf8);
            return originalText;
        }
        return null;
    }

    static removeSession(key) {
        sessionStorage.removeItem(key);
    }

    static clearSession() {
        sessionStorage.clear();
    }

}
