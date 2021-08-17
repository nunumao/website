<template>
  <div class="cms-field-editor">
    <d1-editor></d1-editor>
  </div>
</template>
<script>
export default {
  props: {
    field: {
      type: String,
      required: true
    },
    placeholder: {
      type: String,
      default: '请输入标题'
    }
  },
  data() {
    return {
      platform: 'pc',
      value: ''
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
<style lang="scss">
</style>
