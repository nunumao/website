<template>
  <div class="d1-editor" :style="{width:width+'px'}">
    <textarea id="editor"></textarea>
  </div>
</template>
<script>
// import ClassicEditor from '@ckeditor/ckeditor5-build-classic'
// import './ckeditor'
// import '@ckeditor/ckeditor5-build-classic/build/translations/zh-cn'

// import Table from '@ckeditor/ckeditor5-table/src/table';
// import TableToolbar from '@ckeditor/ckeditor5-table/src/tabletoolbar';
// import TableProperties from '@ckeditor/ckeditor5-table/build/table';
// import TableCellProperties from '@ckeditor/ckeditor5-table/src/tablecellproperties';


export default {
  props: {
    width: {
      type: Number,
      default: 700
    }
  },
  mounted() {
    import('./ckeditor').then(() => {
      console.log('加载完毕');
      ClassicEditor
        .create(document.querySelector('#editor'), {
          // language: 'zh-cn',
          // plugins: [Table, TableToolbar],
          // toolbar: ['insertTable'],
          // table: {
          //   contentToolbar: [
          //     'tableColumn', 'tableRow', 'mergeTableCells'
          //   ],
          // }
        })
        .then(editor => {
          console.log(editor);
        })
        .catch(error => {
          console.error(error);
        })
    })

  }
}
</script>