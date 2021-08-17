<template>
  <el-radio-group v-model="value" @change="change">
    <el-radio :label="0">否</el-radio>
    <el-radio :label="1">是</el-radio>
  </el-radio-group>
</template>
<script>
export default {
  props: {
    field: {
      type: String,
      required: true
    }
  },
  data() {
    return {
      value: 0
    }
  },
  mounted() {
    this.$emit('change', this.field, 0);
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
