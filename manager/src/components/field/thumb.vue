<template>
  <div class="cms-field-thumb d-flex">
    <d1-upload v-model:value="list"></d1-upload>
  </div>
</template>
<script>
export default {
  props: {
    value: {
      type: String,
      required: true
    },
    field: {
      type: String,
      required: true
    },
    placeholder: {
      type: String,
      default: '请输入关键词'
    }
  },
  data() {
    return {
      list: []
    }
  },
  watch: {
    value: function (v, o) {
      this.list = [v];
    },
    list: function (v, o) {
      this.$emit('change', this.field, v[0]);
    }
  },
  methods: {
    change(v) {

    }
  }
}
</script>
