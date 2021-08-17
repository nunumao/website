<template>
  <div class="d1-page-form" :style="{'background-color': background,'padding':padding+'px'}" :class="{shadow:shadow}">
    <div class="form-main">
      <slot>
        <el-form ref="form" :model="entity" :rules="rules" label-width="150px">
          <slot name="field"></slot>
          <el-form-item>
            <el-button type="primary" @click="submit">保存</el-button>
          </el-form-item>
        </el-form>
      </slot>
    </div>
  </div>
</template>
<script>
export default {
  props: {
    title: {
      type: String,
      default: '页面标题'
    },
    shadow: {
      type: Boolean,
      default: true
    },
    background: {
      type: String,
      default: 'white'
    },
    padding: {
      type: Number,
      default: 20
    },
    entity: {
      type: Object,
      default: {}
    },
    rules: {
      type: Object,
      default: {}
    },
    apiAdd: {
      type: String,
      default: ''
    },
    apiEdit: {
      type: String,
      default: ''
    },
    apiDetail: {
      type: String,
      default: ''
    },
    beforeAction: {
      type: Function,
      default: null
    }
  },
  data() {
    return {
    }
  },
  mounted() {
    this.$store.commit('updateUiTitle', this.title);
    this.init();
  },
  methods: {
    back() {
      this.$router.go(-1);
    },
    async init() {
      if (!this.$slots.default) {
        if (this.beforeAction) {
          await this.beforeAction();
        }
        if (this.entity.id) {
          let data = await this.loadDetail().catch(e => { })
          if (data) {
            this.$emit('update:entity', data.result)
          }
        }
      }
    },
    loadDetail() {
      return this.$axios.get(this.apiDetail, { id: this.$route.query.id })
    },
    submit() {
      this.$refs['form'].validate(valid => {
        if (valid) {
          if (this.entity.id != null) {
            this.$axios.put(this.apiEdit, this.entity).then(data => {
              this.$message({
                message: data.message,
                type: 'success',
                duration: 1000,
                onClose: () => {
                  this.$router.back();
                }
              })
            }).catch(e => {
              this.$message.error(e)
            })
          } else {
            this.$axios.post(this.apiAdd, this.entity).then(data => {
              this.$message({
                message: data.message,
                type: 'success',
                duration: 1000,
                onClose: () => {
                  this.$router.back();
                }
              })
            }).catch(e => {
              this.$message.error(e)
            })
          }
        }
      })
    }
  }
}
</script>

<style lang="scss" scoped>
.shadow {
  box-shadow: 1px 0px 20px rgba(0, 0, 0, 0.05);
}
.d1-page-form {
  border-radius: 4px;
}
</style>
