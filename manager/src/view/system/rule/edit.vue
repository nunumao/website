<template>
  <d1-form-page :title="entity.id?'编辑菜单':'新增菜单'">
    <el-form ref="form" :model="entity" :rules="rules" label-width="150px">
      <el-form-item label="菜单分类" prop="type">
        <el-radio-group v-model="entity.type" @change="typeChange" :disabled="entity.id?true:false">
          <el-radio :label="0">分组</el-radio>
          <el-radio :label="1">模块</el-radio>
          <el-radio :label="2">操作</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item v-if="entity.type > 0" label="所属上级" prop="parentId">
        <el-select v-model="entity.parentId" @change="parantChange" placeholder="请选择">
          <el-option v-for="item in parentList" :key="item.id" :label="item.name" :value="item.id">
          </el-option>
        </el-select>
        <el-select class="ml-2" v-if="entity.type == 2" v-model="entity.childId" placeholder="请选择">
          <el-option v-for="item in childList" :key="item.id" :label="item.name" :value="item.id">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="菜单名称" prop="name">
        <el-input v-model="entity.name"></el-input>
      </el-form-item>
      <template v-if="entity.type == 2">
        <el-form-item label="菜单类型" prop="page">
          <el-radio-group v-model="entity.page">
            <el-radio :label="0">页面</el-radio>
            <el-radio :label="1">操作</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item v-if="entity.page == 0" label="URL" prop="url">
          <el-input v-model="entity.url" style="width:300px !important"></el-input>
        </el-form-item>
        <el-form-item v-if="entity.page == 0" label="页面路径" prop="path">
          <el-input v-model="entity.path" style="width:300px !important"></el-input>
        </el-form-item>
        <el-form-item v-if="entity.page == 0" label="菜单入口" prop="menu">
          <el-switch v-model="entity.menu" :active-value="1" :inactive-value="0"></el-switch>
        </el-form-item>
        <el-form-item label="关联接口" prop="apis">
          <table class="api-table table table-bordered">
            <thead>
              <tr>
                <th>API</th>
                <th width="80">操作</th>
              </tr>
            </thead>
            <tbody>
              <tr v-for="item in apiList" :key="item">
                <td class="va">{{item}}</td>
                <td class="va">
                  <a href="javascript:;" @click="apiDel(item)">删除</a>
                </td>
              </tr>
              <tr>
                <td colspan="2"><a href="javascript:;" @click="openApi"><i class="el-icon-plus"></i> 增加接口</a></td>
              </tr>
            </tbody>
          </table>
        </el-form-item>
      </template>
      <el-form-item label="排序" prop="sort">
        <el-input v-model="entity.sort"></el-input>
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
        parentId: '',
        childId: '',
        name: '',
        type: 0,
        page: 0,
        menu: 0,
        path: '',
        apis: '',
        sort: ''
      },
      parentList: [],
      childList: [],
      apiList: [],
      rules: {
        name: [{
          required: true,
          message: '请输入名称'
        }],
        parentId: [{
          required: true,
          message: '请选择上级'
        }],
        path: [{
          required: true,
          message: '请输入路径'
        }],
        url: [{
          required: true,
          message: '请输入URL'
        }]
      }
    }
  },
  mounted() {
    this.init();
  },
  methods: {
    init() {
      if (this.entity.id) {
        this.$axios.get('manager/rule/detail', { id: this.entity.id }).then(async data => {
          let parentData = await this.loadParent();
          if (parentData) {
            this.parentList = parentData.result;
          }
          data.result.childId = data.result.parentId;
          if (data.result.type == 2) {
            let childDetail = await this.$axios.get('manager/rule/detail', { id: data.result.parentId })
            if (childDetail) {
              data.result.parentId = childDetail.result.parentId;
              let childData = await this.loadParent(childDetail.result.parentId);
              if (childData) {
                this.childList = childData.result;
              }
            }
          }
          this.entity = data.result;
        }).catch(e => { })
      }
    },
    loadParent(parent = 0) {
      return this.$axios.get('manager/rule/parent', { parent }).catch(e => { });
    },
    async typeChange(type) {
      this.parentList = [];
      this.entity.parentId = null;
      this.childList = [];
      this.entity.childId = null;

      if (type > 0) {
        let parentData = await this.loadParent();
        if (parentData) {
          this.parentList = parentData.result;
        }
      }
    },
    parantChange(id) {
      if (this.entity.type == 2) {
        this.childList = [];
        this.entity.childId = null;
        this.loadParent(id).then(data => {
          this.childList = data.result;
        })
      }
    },
    submit() {
      // 转换 api
      this.entity.apis = JSON.stringify(this.apiList);
      if (this.entity.type == 2) {
        this.entity.parentId = this.entity.childId;
      }
      this.$refs['form'].validate(valid => {
        if (valid) {
          if (this.entity.id != null) {
            this.$axios.put('manager/rule/edit', this.entity).then(data => {
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
            this.$axios.post('manager/rule/add', this.entity).then(data => {
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
.api-table {
  width: 600px;
}
</style>