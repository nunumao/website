<template>
  <d1-form-page :title="entity.id?'字段编辑':'字段新增'">
    <el-form ref="form" :rules="rules" :model="entity" label-width="150px">
      <el-form-item label="字段类型" prop="name">
        <el-select v-model="entity.type" placeholder="请选择类型">
          <el-option v-for="item in typeList" :key="item.value" :label="item.name" :value="item.value">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="字段名称" prop="name">
        <el-input v-model="entity.name"></el-input>
      </el-form-item>
      <el-form-item label="列名称" prop="field">
        <el-input v-model="entity.field"></el-input>
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
      typeList: [{
        name: '单行文本',
        value: 'input'
      }, {
        name: '多行文本',
        value: 'textarea'
      }, {
        name: '编辑器',
        value: 'editor'
      }, {
        name: '选项',
        value: 'box'
      }, {
        name: '图片',
        value: 'image'
      }, {
        name: '多图片',
        value: 'images'
      }, {
        name: '视频',
        value: 'video'
      }, {
        name: '数字',
        value: 'number'
      }, {
        name: '日期时间',
        value: 'datetime'
      }],
      entity: {
        id: this.$route.query.id,
        name: '',
        field: ''
      },
      rules: {
        name: [{
          required: true,
          message: '请输入模型名称'
        }],
        field: [{
          validator: (rule, value, callback) => {
            this.$axios.get('manager/model/hasField', { table: value }).then(data => {
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