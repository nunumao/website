import listPage from './list-page'
import formPage from './form-page'
import explorer from './explorer'
import editor from './editor'
import upload from './upload'
import markdown from './markdown'
import mkeditor from './mkeditor'

const components = {
    listPage,
    formPage,
    explorer,
    editor,
    upload,
    markdown,
    mkeditor
}

const install = function (Vue, opt) {
    if (install.installed) return;


    // 加载组件
    Object.keys(components).forEach(key => {
        Vue.component('d1' + key.replace(/^\S/, s => s.toUpperCase()), components[key]);
    });

}

// auto install
if (typeof window !== 'undefined' && window.Vue) {
    install(window.Vue);
}

export default install;