<template>
  <d1-form-page :title="entity.id?'站点编辑':'站点新增'">
    <el-form ref="form" :model="entity" :rules="rules" label-width="150px">
      <el-form-item label="站点名称" prop="name">
        <el-input v-model="entity.name"></el-input>
      </el-form-item>
      <el-form-item label="站点标识" prop="identifier">
        <el-input v-model="entity.identifier"></el-input>
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
        id: this.$route.query.id
      },
      rules: {
        name: [{
          required: true,
          message: '请输入名称'
        }],
        identifier: [{
          required: true,
          message: '请输入标识'
        }]
      }
    }
  },
  methods: {
    submit() {

    }
  }
}
</script>