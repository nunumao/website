<template>
  <d1-list-page title="菜单管理">
    <template v-slot:action>
      <router-link :to="{ path: '/system/rule/add'}">
        <el-button type="primary">新增</el-button>
      </router-link>
    </template>
    <template v-slot:search></template>
    <div>
      <el-table :data="list" border style="width: 100%" row-key="id" :tree-props="{children: 'child', hasChildren: true}">
        <el-table-column prop="name" label="名称">
        </el-table-column>
        <el-table-column label="类型" width="100">
          <template #default="scope">
            <span style="color:#007bff" v-if="scope.row.type == 0">分组</span>
            <span style="color:#dc3545" v-if="scope.row.type == 1">模块</span>
            <span style="color:#28a745" v-if="scope.row.type == 2">操作</span>
          </template>
        </el-table-column>
        <el-table-column prop="url" label="URL" width="200"></el-table-column>
        <el-table-column prop="path" label="页面路径" width="200"></el-table-column>        
        <el-table-column label="操作" width="150">
          <template #default="scope">
            <router-link class="mr-2" :to="{path:'/system/rule/edit',query:{id:scope.row.id}}">
              <el-button size="mini" type="warning">编辑</el-button>
            </router-link>
            <el-button size="mini" type="danger" @click="del(scope.row.id)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>
    <template v-slot:page>
      <div></div>
    </template>
  </d1-list-page>
</template>
<script>
export default {
  data() {
    return {
      list: []
    }
  },
  mounted() {
    this.init();
  },
  methods: {
    init() {
      this.$axios.get('manager/rule/list').then(data => {
        this.list = data.result;
      }).catch(e => {
        this.list = [];
      })
    },
    del(id) {
      this.$confirm('此操作将永久删除该数据, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.$axios.delete('manager/rule/delete', { id }).then(data => {
          this.$message.success(data.message);
          this.init();
        }).catch(e => {
          this.$message.error(e);
        })
      })
    }
  }
}
</script>
<style lang="scss" scoped>
</style>