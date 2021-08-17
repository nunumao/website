<template>
  <div class="cms-field-createtime d-flex">
    <el-date-picker v-model="value" type="datetime" :placeholder="placeholder" @change="change">
    </el-date-picker>
  </div>
</template>
<script>
import moment from 'moment'
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
      default: '请输入创建时间'
    }
  },
  watch: {
    value: function (v, o) {
      this.change(v)
    }
  },
  data() {
    return {
    }
  },
  methods: {
    change(v) {
      this.$emit('change', this.field, moment(v).format('YYYY-MM-DD HH:mm:ss'));
    }
  }
}
</script>
<style lang="scss" scoped>
</style>
