<template>
  <div class="d1-mkeditor">
    <div class="d1-mkeditor-toolbar">
      <div class="d1-mkeditor-toolbar-container h-100 d-flex align-items-center">
        <div>
          <a href="javascript:;" title="撤销（Ctrl+Z）" unselectable="on">
            <i class="fa fa-undo" unselectable="on"></i>
          </a>
        </div>
        <div>
          <a href="javascript:;" title="重做（Ctrl+Y）" unselectable="on">
            <i class="fa fa-repeat" unselectable="on"></i>
          </a>
        </div>
        <div class="divider"></div>
      </div>
    </div>
    <div class="d1-mkeditor-content d-flex">
      <div>
        <textarea id="content" v-model="content" placeholder="请输入内容..."></textarea>
      </div>
      <div></div>
    </div>
  </div>
</template>
<script>
import * as CodeMirror from 'codemirror'
import 'codemirror/lib/codemirror.css'
// import 'codemirror/theme/dracula.css'
import marked from 'marked'

export default {
  data() {
    return {
      content: ''
    }
  },
  mounted() {
    console.log(marked('## ABC'));
    CodeMirror.fromTextArea(document.getElementById('content'), {
      lineNumbers: true,
      //   theme: 'dracula'
    })
  }
}
</script>
<style lang="scss" scoped>
@font-face {
  font-family: FontAwesome;
  src: url(./fonts/fontawesome-webfont.eot?v=4.3.0);
  src: url(./fonts/fontawesome-webfont.eot?#iefix&v=4.3.0)
      format("embedded-opentype"),
    url(./fonts/fontawesome-webfont.woff2?v=4.3.0) format("woff2"),
    url(./fonts/fontawesome-webfont.woff?v=4.3.0) format("woff"),
    url(./fonts/fontawesome-webfont.ttf?v=4.3.0) format("truetype"),
    url(./fonts/fontawesome-webfont.svg?v=4.3.0#fontawesomeregular)
      format("svg");
  font-weight: 400;
  font-style: normal;
}
.fa {
  font: normal normal normal 14px/1 FontAwesome;
  font-size: inherit;
  text-rendering: auto;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  transform: translate(0, 0);
}
.d1-mkeditor {
  width: 1000px;
  margin: 100px auto;
  border: 1px solid #ddd;
  &-toolbar {
    height: 40px;
    border-bottom: 1px solid #ddd;
    background: #fff;
    padding: 0 10px;
    &-container {
      & > div {
        padding: 5px;
        a {
          color: #666;
          font-size: 16px;
        }
      }
      .divider {
        height: 65%;
        border-right: 1px solid #ddd;
      }
    }
  }
  &-content {
    & > div {
      flex: 1;
    }
  }
}
</style>