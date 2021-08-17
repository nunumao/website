<template>
  <div class="cms-field-intro d-flex">
    <el-input v-model="value" type="textarea" resize="none" :autosize="{ minRows: 4, maxRows: 8 }" @input="change" :placeholder="placeholder">
    </el-input>
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
      default: '请输入摘要'
    }
  },
  data() {
    return {
    }
  },
  methods: {
    change(v) {
      this.$emit('change', this.field, v);
    }
  }
}
</script>
<style lang="scss" scoped>
</style>
