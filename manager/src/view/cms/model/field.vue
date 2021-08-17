<template>
  <d1-list-page title="字段列表">
    <template v-slot:action>
      <router-link :to="{ path: '/cms/model/addField',query:{mid:this.$route.query.id}}">
        <el-button type="primary">新增</el-button>
      </router-link>
    </template>
    <template v-slot:search>
      <div></div>
    </template>
    <el-table :data="list" border style="width: 100%">
      <el-table-column prop="name" label="字段名称">
      </el-table-column>
      <el-table-column prop="field" label="列名称" width="150">
      </el-table-column>
      <el-table-column label="字段类型" width="150">
        <template #default="scope">
          <span v-if="scope.row.type == 'category'">栏目</span>
          <span v-if="scope.row.type == 'title'">标题</span>
          <span v-if="scope.row.type == 'thumb'">缩略图</span>
          <span v-if="scope.row.type == 'keywords'">关键字</span>
          <span v-if="scope.row.type == 'description'">简介</span>
          <span v-if="scope.row.type == 'editor'">编辑器</span>
          <span v-if="scope.row.type == 'create_time'">创建时间</span>
        </template>
      </el-table-column>
      <el-table-column label="系统" width="60">
        <template #default="scope">
          <span v-if="scope.row.isCore == 0">√</span>
          <span v-if="scope.row.isCore == 1">×</span>
        </template>
      </el-table-column>
      <el-table-column label="必填" width="60">
        <template #default="scope">
          <span v-if="scope.row.isUnique == 1">√</span>
          <span v-if="scope.row.isUnique == 0">×</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" width="160">
        <template #default="scope">
          <router-link class="mr-2" :to="{path:'/cms/model/edit',query:{mid:scope.row.id}}">
            <el-button size="mini" type="warning">编辑</el-button>
          </router-link>
          <el-button size="mini" type="danger" :disabled="scope.row.isSystem == 1" @click="del(scope.row.id)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
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
    async init() {
      let data = await this.loadList().catch(e => {
        this.clear();
      })
      if (data) {
        this.list = data.result;
      }
    },
    loadList() {
      return this.$axios.get('manager/modelFiled/list', { id: this.$route.query.id }).catch(e => { this.clear() })
    },
    search() {
      this.clear();
      this.init();
    },
    page() {
      this.init();
    },
    clear() {
      this.list.records = [];
    },
    del(id) {
      this.$confirm('此操作将永久删除该数据, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.$axios.delete('manager/modelFiled/delete', { id }).then(data => {
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