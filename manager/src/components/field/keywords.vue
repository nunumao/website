<template>
  <div class="cms-field-keywords d-flex">
    <el-input style="width:300px !important" v-model="value" @input="change" :placeholder="placeholder">
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
      default: '请输入关键词'
    }
  },
  data() {
    return {
    }
  },
  methods: {
    change(v) {
      if (v instanceof Event) {
        return
      }
      this.$emit('change', this.field, v);
    }
  }
}
</script>
<style lang="scss" scoped>
</style>
