<template>
  <div class="cms-field-title d-flex">
    <el-input v-model="value" @input="change" style="width:300px !important" :style="{color:color}" :placeholder="placeholder"></el-input>
    <el-color-picker class="color" v-model="color"></el-color-picker>
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
      default: '请输入标题'
    }
  },
  data() {
    return {
      color: '#000000'
    }
  },
  methods: {
    change(v) {
      this.$emit('change', this.field, v);
    }
  }
}
</script>
<style lang="scss">
.cms-field-title {
  .color {
    margin-left: 10px;
  }
  input {
    background: url("@/assets/image/ruler.gif") repeat-x scroll 0 24px
      transparent;
  }
}
</style>
