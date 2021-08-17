<template>
  <d1-list-page title="栏目列表" api-list="manager/category/list">
    <template v-slot:action>
      <router-link :to="{ path: '/cms/category/add'}">
        <el-button type="primary">新增</el-button>
      </router-link>
    </template>
    <template v-slot:search>
      <div></div>
    </template>
    <div>
      <el-table :data="list" border style="width: 100%" row-key="id" :tree-props="{children: 'child', hasChildren: true}">
        <el-table-column prop="name" label="栏目名称">
        </el-table-column>
        <el-table-column label="操作" width="150">
          <template #default="scope">
            <router-link class="mr-2" :to="{path:'/cms/category/edit',query:{id:scope.row.id}}">
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
      this.$axios.get('manager/category/list').then(data => {
        this.list = data.result;
      }).catch(e => {
        this.list = []
      })
    },
    del(id) {
      this.$confirm('此操作将永久删除该数据, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.$axios.delete('manager/category/delete', { id }).then(data => {
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