<template>
  <div id="editormd" class="d1-markdown">
    <textarea style="display:none"></textarea>
  </div>
</template>
<script>
import axios from 'axios'
export default {
  props: {
    api: {
      type: String,
      default: 'manager/file/upload'
    },
    width: {
      type: String,
      default: '100%'
    }
  },
  data() {
    return {
      editor: null
    }
  },
  mounted() {

  },
  methods: {
    init(v) {
      this.editor = editormd("editormd", {
        markdown: v,
        width: this.width,
        height: 640,
        syncScrolling: "single",
        path: "/markdown/lib/",
        saveHTMLToTextarea: true,
        imageUpload: true,
        imageFormats: ["jpg", "jpeg", "gif", "png", "bmp", "webp"],
        imageUploadURL: '{:url("upload")}',
        onload: () => {
          // 引入插件 执行监听方法
          editormd.loadPlugin("/markdown/plugins/image-handle-paste/image-handle-paste", () => {
            this.editor.imagePaste((ctx, file) => {
              let form = new FormData();
              form.append('file', file);
              form.append('total', 0);
              form.append('index', 0);
              this.upload(form).then(data => {
                if (data.code == 200) {
                  ctx.executePlugin("imageDialog", "image-dialog/image-dialog");
                  setTimeout(() => {
                    $("." + ctx.classPrefix + "image-dialog").find("input[data-url]").val(this.$config.STATIC_PATH + data.result);
                  }, 500)
                }
              })
            })
          })
        },
        onchange: () => {
          this.$emit('change', {
            html: this.editor.getHTML(),
            md: this.editor.getMarkdown()
          })
        }
      })
    },
    upload(data) {
      return new Promise((resolve, reject) => {
        axios.request({
          url: this.$config.BASE_API + this.api,
          method: 'post',
          data: data,
          headers: {
            'Content-Type': 'multipart/form-data;boundary = ' + new Date().getDate(),
            'Authorization': this.$store.getters.token.accessToken
          },
        }).then(response => {
          const data = response.data;
          resolve({ code: data.code, result: data.result });
        })
      })
    }
  }
}
</script>
<style lang="scss" scoped>
.d1-markdown {
  line-height: normal !important;
  z-index: 999;
}
</style>