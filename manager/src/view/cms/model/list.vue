<template>
  <d1-list-page title="模型列表">
    <template v-slot:action>
      <router-link :to="{ path: '/cms/model/add'}">
        <el-button type="primary">新增</el-button>
      </router-link>
    </template>
    <template v-slot:search>
      <div class="d-flex">
        <el-input v-model="conditon['search']" prefix-icon="el-icon-search" :clearable="true" placeholder="请输入查询条件"></el-input>
        <el-button style="margin-left:10px" @click="search">搜索</el-button>
      </div>
    </template>
    <el-table :data="list.records" border style="width: 100%">
      <el-table-column prop="name" label="站点名称">
      </el-table-column>
      <el-table-column prop="identifier" label="站点标识" width="200">
      </el-table-column>
      <el-table-column label="状态" width="100">
        <template #default="scope">
          <span style="color:#dc3545" v-if="scope.row.status == 0">禁用</span>
          <span style="color:#28a745" v-if="scope.row.status == 1">启用</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" width="210">
        <template #default="scope">
          <router-link class="mr-2" :to="{path:'/cms/model/field',query:{id:scope.row.id}}">
            <el-button size="mini" type="success">字段</el-button>
          </router-link>
          <router-link class="mr-2" :to="{path:'/cms/model/edit',query:{id:scope.row.id}}">
            <el-button size="mini" type="warning">编辑</el-button>
          </router-link>
          <el-button size="mini" type="danger" @click="del(scope.row.id)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
  </d1-list-page>
</template>
<script>
export default {
  data() {
    return {
      conditon: {

      },
      list: {
        records: [],
        current: 1,
        total: 0
      }
    }
  },
  mounted() {
    this.init();
  },
  methods: {
    async init() {
      let data = await this.loadList().catch(e => {
        this.clear();
      })
      if (data) {
        this.list = data.result;
      }
    },
    loadList() {
      return this.$axios.get('manager/model/list', { search: this.conditon.search, page: this.list.current }).catch(e => { this.list.records = [] })
    },
    search() {
      this.clear();
      this.init();
    },
    page() {
      this.init();
    },
    clear() {
      this.list.current = 1;
      this.list.total = 0;
      this.list.records = [];
    },
    del(id) {
      this.$confirm('此操作将永久删除该数据, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.$axios.delete('manager/model/delete', { id }).then(data => {
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