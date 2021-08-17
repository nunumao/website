<template>
  <d1-form-page :title="entity.id?'站点编辑':'站点新增'">
    <el-form ref="form" :rules="rules" :model="entity" label-width="150px">
      <el-form-item label="模型名称" prop="name">
        <el-input v-model="entity.name"></el-input>
      </el-form-item>
      <el-form-item label="表名称" prop="tableName">
        <el-input v-model="entity.tableName"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="submit">保存</el-button>
      </el-form-item>
    </el-form>
  </d1-form-page>
</template>
<script>
export default {
  data() {
    return {
      entity: {
        id: this.$route.query.id,
        name: '',
        status: 1
      },
      rules: {
        name: [{
          required: true,
          message: '请输入模型名称'
        }],
        tableName: [{
          validator: (rule, value, callback) => {
            this.$axios.get('manager/model/hasTable', { table: value }).then(data => {
              callback();
            }).catch(e => {
              callback(new Error('表名称已存在'));
            })
          },
          trigger: 'blur'
        }, {
          required: true,
          message: '请输入数据表名'
        }, {
          pattern: /^[a-z]+$/,
          message: '数据表名只能为英文'
        }]
      }
    }
  },
  methods: {
    submit() {
      this.$refs['form'].validate(valid => {
        if (valid) {
          if (this.entity.id != null) {
            this.$axios.put('manager/model/edit', this.entity).then(data => {
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
            this.$axios.post('manager/model/add', this.entity).then(data => {
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