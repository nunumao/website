<template>
  <div class="cms-field-markdown">
    <d1-markdown ref="mk" :width="width" @change="change"></d1-markdown>
  </div>
</template>
<script>
export default {
  props: {
    value: {
      type: String,
      default: ''
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
      width: null
    }
  },
  mounted() {    
    this.width = this.$el.clientWidth + 'px';
  },
  methods: {
    init(v) {
      this.$refs.mk.init(v)
    },
    change(v) {
      if (!(v instanceof Event)) {
        this.$emit('change', this.field, v);
      }
    }
  }
}
</script>
<style lang="scss" scoped>
.cms-field-markdown {
  width: 100%;
}
</style>