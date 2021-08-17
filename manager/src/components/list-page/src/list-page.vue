<template>
  <div class="d1-page-list" :style="{'background-color': background,'padding':padding+'px'}" :class="{shadow:shadow}">
    <div class="list-tools d-flex justify-content-between">
      <div class="action">
        <slot name="action">
          <router-link :to="{ path: path.add}">
            <el-button type="primary">新增</el-button>
          </router-link>
        </slot>
      </div>
      <div class="search">
        <slot name="search">
          <div class="d-flex">
            <el-input v-model="conditon['search']" prefix-icon="el-icon-search" :clearable="true" placeholder="请输入查询条件"></el-input>
            <el-button style="margin-left:10px" @click="search">搜索</el-button>
          </div>
        </slot>
      </div>
    </div>
    <div class="list-main">
      <slot>
        <div>
          <el-table :data="list.records" border style="width: 100%">
            <slot name="column"></slot>
            <el-table-column label="操作" width="150">
              <template #default="scope">
                <router-link class="mr-2" :to="{path:path.edit,query:{id:scope.row.id}}">
                  <el-button size="mini" type="warning">编辑</el-button>
                </router-link>
                <el-button size="mini" type="danger" @click="del(scope.row.id)">删除</el-button>
              </template>
            </el-table-column>
          </el-table>
        </div>
      </slot>
    </div>
    <div class="list-footer d-flex justify-content-center">
      <slot name="page">
        <el-pagination v-if="list.total > 0" v-model:current-page="list.current" :total="list.total" @current-change="page" layout="total, prev, pager, next, jumper">
        </el-pagination>
      </slot>
    </div>
  </div>
</template>

<script>
export default {
  props: {
    title: {
      type: String,
      default: '页面标题'
    },
    shadow: {
      type: Boolean,
      default: true
    },
    background: {
      type: String,
      default: 'white'
    },
    padding: {
      type: Number,
      default: 20
    },
    apiList: {
      type: String,
      default: ''
    },
    apiDel: {
      type: String,
      default: ''
    }
  },
  data() {
    return {
      conditon: {

      },
      list: {
        records: [],
        current: 1,
        total: 0
      },
      path: {
        add: this.$route.path.replace(/\/(.+)\/(.+)\/(.+)/, '/$1/$2/add'),
        edit: this.$route.path.replace(/\/(.+)\/(.+)\/(.+)/, '/$1/$2/edit'),
        del: ''
      }
    }
  },
  mounted() {
    this.$store.commit('updateUiTitle', this.title);
    if (!this.$slots.default) {
      this.init();
    }
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
      return this.$axios.get(this.apiList, { search: this.conditon.search, page: this.list.current }).catch(e => { this.list.records = [] })
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
      if (!this.apiDel) {
        return;
      }
      this.$confirm('此操作将永久删除该数据, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.$axios.delete(this.apiDel, { id }).then(data => {
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
.shadow {
  box-shadow: 1px 0px 20px rgba(0, 0, 0, 0.05);
}
.d1-page-list {
  border-radius: 4px;
  .list-tools {
    margin-bottom: 20px;
  }
  .list-main {
    overflow-x: hidden;
  }
  .list-footer {
    margin-top: 20px;
  }
}
</style>
<style lang="scss">
.d1-page-list {
  .search {
    display: flex;
    & > div {
      margin-left: 10px;
    }
  }
}
</style>
