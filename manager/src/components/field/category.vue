<template>
  <div class="cms-field-category d-flex">
    <el-input v-model="value" @input="change" :readonly="true" suffix-icon="el-icon-search" :placeholder="placeholder">
    </el-input>
    <el-dialog title="选择栏目" v-model:visible="dialog" width="50%">
      <div class="mb-2 d-flex align-items-center">
        <div>
          <el-input v-model="condition.site" placeholder="请输入昵称"></el-input>
        </div>
      </div>
      <el-table :data="list" border style="width: 100%" height="300">
        <el-table-column prop="name" label="名称"></el-table-column>
        <el-table-column label="操作" width="100" fixed="right">
          <template #default="scope">
            <el-button size="mini" type="warning" @click="select(scope.row)">选择</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-dialog>
  </div>
</template>
<script>
export default {
  props: {
    field: {
      type: String,
      required: true
    },
    placeholder: {
      type: String,
      default: '请选择栏目'
    }
  },
  data() {
    return {
      dialog: false,
      value: '',
      list: [],
      condition: {
        site: ''
      },
      entity: {}
    }
  },
  mounted() {
    this.init();
  },
  methods: {
    init() {
      this.$axios.get('manager/content/categoryDetail', { categoryId: this.$route.query.categoryId }).then(data => {
        this.entity = data.result;
        this.value = data.result.name;
        this.$emit('change', this.field, this.entity.id);
      }).catch(e => { })
    },
    change(v) {
      this.$emit('change', this.field, v);
    }
  }
}
</script>
<style lang="scss" scoped>
</style>
