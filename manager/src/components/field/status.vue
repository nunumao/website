<template>
  <el-radio-group v-model="value" @change="change">
    <el-radio :label="1">发布</el-radio>
    <el-radio :label="0">草稿</el-radio>
  </el-radio-group>
</template>
<script>
export default {
  props: {
    value: {
      type: Number,
      required: true
    },
    field: {
      type: String,
      required: true
    }
  },
  data() {
    return {
    }
  },
  watch: {
    value: function (v, o) {
      this.$emit('change', this.field, v);
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
