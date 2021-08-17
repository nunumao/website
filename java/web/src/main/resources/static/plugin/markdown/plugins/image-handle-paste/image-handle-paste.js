(function () {
    var factory = function (exports) {
        var $ = jQuery;           // if using module loader(Require.js/Sea.js).
        var pluginName = "image-handle-paste";  // 定义插件名称
        //图片粘贴上传方法
        exports.fn.imagePaste = function (cb) {
            var _this = this;
            var cm = _this.cm;
            var settings = _this.settings;
            var editor = _this.editor;
            var classPrefix = _this.classPrefix;
            var id = _this.id;
            if (!settings.imageUpload || !settings.imageUploadURL) {
                console.log('你还未开启图片上传或者没有配置上传地址');
                return false;
            }
            //监听粘贴板事件
            $('#' + id).on('paste', function (e) {
                var items = (e.clipboardData || e.originalEvent.clipboardData).items;
                //判断图片类型
                if (items && items[0].type.indexOf('image') > -1) {

                    var file = items[0].getAsFile();

                    cb(_this, file);

                    // 生成blob
                    // var blobImg = URL.createObjectURL(file);
                    // // base64
                    // var reader = new FileReader();
                    // reader.readAsDataURL(file);
                    // reader.onload = function (e) {
                    //     //图片的base64
                    //     var base64Img = e.currentTarget.result;
                    //     // _ajax(settings.imageUploadURL,{'data':base64Img},function(result){
                    //     //     console.log(result)
                    //     // })

                    //     $.post(settings.imageUploadURL, { data: base64Img }, function (data) {
                    //         if (data.code == 200) {
                    //             _this.executePlugin("imageDialog", "image-dialog/image-dialog");
                    //             setTimeout(function () {
                    //                 $("." + classPrefix + "image-dialog").find("input[data-url]").val(data.result);
                    //             }, 100)
                    //         }
                    //     });
                    // }
                }
            })
        };

    };
    // CommonJS/Node.js
    if (typeof require === "function" && typeof exports === "object" && typeof module === "object") {
        module.exports = factory;
    } else if (typeof define === "function")  // AMD/CMD/Sea.js
    {
        if (define.amd) { // for Require.js
            define(["editormd"], function (editormd) {
                factory(editormd);
            });
        } else { // for Sea.js
            define(function (require) {
                var editormd = require("./../../editormd");
                factory(editormd);
            });
        }
    } else {
        factory(window.editormd);
    }
})();