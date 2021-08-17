<template>
  <d1-list-page title="内容列表">
    <template #action>
      <el-button type="primary" @click="add">新增</el-button>
    </template>
    <template #search>
      <div class="d-flex search">
        <div>
          <el-select v-model="condition.siteId" placeholder="请选择">
            <el-option v-for="item in site" :key="item.id" :label="item.name" :value="item.id">
            </el-option>
          </el-select>
        </div>
        <div>
          <el-input v-model="condition.title" placeholder="请输入内容标题"></el-input>
        </div>
        <div>
          <el-button>搜索</el-button>
        </div>
      </div>
    </template>
    <div class="d-flex">
      <div class="tree">
        <el-tree :data="category" :highlight-current="true" :props="{children: 'child',label: 'name'}" @node-click="categoryClick"></el-tree>
      </div>
      <div class="flex-full content-list">
        <div>
          <el-table :data="list.records" border style="width: 100%">
            <el-table-column prop="title" label="名称"></el-table-column>
            <el-table-column prop="create_time" label="发布时间" width="180"></el-table-column>
            <el-table-column label="状态" width="110">
              <template #default="scope">
                <span v-if="scope.row.status == 1" style="color:green">发布</span>
                <span v-if="scope.row.status == 0" style="color:red">草稿</span>
              </template>
            </el-table-column>
            <el-table-column label="操作" width="140">
              <template #default="scope">
                <router-link style="margin-right:5px" :to="{ path: '/cms/content/edit',query:{siteId: condition.siteId, categoryId: condition.categoryId,id:scope.row.id}}">
                  <el-button size="mini" type="warning">编辑</el-button>
                </router-link>
                <el-button size="mini" type="danger" @click="del(scope.row.id)">删除</el-button>
              </template>
            </el-table-column>
          </el-table>
        </div>
        <div class="mt-3 d-flex justify-content-center">
          <el-pagination v-if="list.total > 0" v-model:current-page="list.current" :total="list.total" @current-change="page" layout="total, prev, pager, next, jumper">
          </el-pagination>
        </div>
      </div>
    </div>
  </d1-list-page>
</template>
<script>
import moment from 'moment'
export default {
  data() {
    return {
      site: [],
      category: [],
      condition: {
        siteId: '',
        categoryId: '',
        title: ''
      },
      list: {}
    }
  },
  mounted() {
    this.init();
  },
  methods: {
    async init() {
      let siteData = await this.loadSite().catch(e => { });
      if (siteData) {
        this.site = siteData.result;
        this.condition.siteId = this.site[0].id;
      }
      let categoryDate = await this.loadCategory().catch(e => { });
      if (categoryDate) {
        this.category = categoryDate.result;
      }
    },
    loadList() {
      this.$axios.get('manager/content/list', {
        categoryId: this.condition.categoryId,
        page: this.list.current
      }).then(data => {
        data.result.records.forEach(item => {
          item.create_time = moment(item.create_time).format('YYYY-MM-DD HH:mm:ss');
        });
        this.list = data.result;
      }).catch(e => {
        this.list = [];
      })
    },
    loadSite() {
      return this.$axios.get('manager/content/site');
    },
    loadCategory() {
      return this.$axios.get('manager/content/category', { siteId: this.condition.siteId });
    },
    page() {
      this.loadList();
    },
    categoryClick(data) {
      if (data.child.length > 0) {
        return;
      }
      this.condition.categoryId = data.id;
      this.loadList();
    },
    add() {
      this.$router.push({ path: '/cms/content/add', query: { siteId: this.condition.siteId, categoryId: this.condition.categoryId } })
    }
  }
}
</script>
<style lang="scss" scoped>
.tree {
  width: 200px;
  border: solid 1px #dcdfe6;
  padding: 10px;
}
.content-list {
  margin-left: 10px;
}
.search {
  & > div {
    margin-left: 10px;
  }
}
</style>