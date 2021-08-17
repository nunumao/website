<template>
  <d1-form-page title="栏目新增" v-model:entity="entity" :rules="rules" api-add="manager/category/add" api-edit="manager/category/edit" api-detail="manager/category/detail" :before-action="beforeAction">
    <template v-slot:field>
      <el-form-item label="上级栏目" prop="parentId">
        <el-select v-model="entity.parentId" placeholder="请选择栏目">
          <el-option label="顶级栏目" :value="0">
          </el-option>
          <el-option v-for="item in parentList" :key="item.id" :label="item.name" :value="item.id">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="栏目名称" prop="name">
        <el-input v-model="entity.name"></el-input>
      </el-form-item>
      <el-form-item label="栏目模型" prop="modelId">
        <el-select v-model="entity.modelId" placeholder="请选择模型">
          <el-option v-for="item in modelList" :key="item.id" :label="item.name" :value="item.id">
          </el-option>
        </el-select>
      </el-form-item>
    </template>
  </d1-form-page>
</template>
<script>
export default {
  data() {
    return {
      entity: {
        id: this.$route.query.id,
        siteId: '',
        name: '',
        parantId: ''
      },
      rules: {
        name: [{
          required: true,
          message: '请输入名称'
        }],
        parentId: [{
          required: true,
          message: '请选择上级'
        }],
        modelId: [{
          required: true,
          message: '请选择模型'
        }]
      },
      modelList: [],
      parentList: [],
      beforeAction: async () => {
        let parentData = await this.$axios.get('manager/category/parent').catch(e => { })
        if (parentData) {
          this.parentList = parentData.result;
        }
        let modelData = await this.$axios.get('manager/category/model').catch(e => { })
        if (modelData) {
          this.modelList = modelData.result;
        }
      }
    }
  },
  methods: {

  }
}
</script>