<template>
  <d1-form-page title="新增内容">
    <el-form ref="form" :model="entity" :rules="rules" label-width="150px">
      <el-form-item v-for="item in fieldList" :key="item.id" :label="item.name" :prop="item.field">
        <category-field v-if="item.type == 'category'" :field="item.field" @change="fieldChange"></category-field>
        <title-field v-if="item.type == 'title'" :field="item.field" v-model:value="entity.title" @change="fieldChange"></title-field>
        <keywords-field v-if="item.type == 'keywords'" :field="item.field" v-model:value="entity.keywords" @change="fieldChange"></keywords-field>
        <description-field v-if="item.type == 'description'" :field="item.field" v-model:value="entity.description" @change="fieldChange"></description-field>
        <markdown-field ref="mk" v-if="item.type == 'editor'" :field="item.field" @change="fieldChange"></markdown-field>
        <thumb-field v-if="item.type == 'thumb'" :field="item.field" v-model:value="entity.thumb" @change="fieldChange"></thumb-field>
        <create-time-field v-if="item.type == 'create_time'" :field="item.field" v-model:value="entity.create_time" @change="fieldChange"></create-time-field>
        <top-field v-if="item.type == 'is_top'" :field="item.field" @change="fieldChange"></top-field>
        <status-field v-if="item.type == 'status'" :field="item.field" v-model:value="entity.status" @change="fieldChange"></status-field>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="submit">保存</el-button>
      </el-form-item>
    </el-form>
  </d1-form-page>
</template>
<script>
import categoryField from '@/components/field/category.vue'
import titleField from '@/components/field/title.vue'
import thumbField from '@/components/field/thumb.vue'
import keywordsField from '@/components/field/keywords.vue'
import descriptionField from '@/components/field/description.vue'
import editorField from '@/components/field/editor.vue'
import createTimeField from '@/components/field/createTime.vue'
import markdownField from '@/components/field/markdown.vue'
import topField from '@/components/field/top.vue'
import statusField from '@/components/field/status.vue'

export default {
  components: {
    categoryField,
    titleField,
    thumbField,
    keywordsField,
    descriptionField,
    editorField,
    createTimeField,
    markdownField,
    topField,
    statusField
  },
  data() {
    return {
      fieldList: [],
      entity: {
      },
      rules: {}
    }
  },
  mounted() {
    this.init();
  },
  methods: {
    async init() {
      let fieldData = await this.loadField().catch(e => { });
      if (fieldData) {
        this.fieldList = fieldData.result;
      }
      if (this.$route.query.id) {
        let data = await this.loadDetail().catch(e => { })
        if (data) {
          for (const i in data.result) {
            if (typeof (data.result[i]) == 'boolean') {
              data.result[i] = + data.result[i]
            }
          }          
          this.entity = data.result;
          this.$refs.mk.init(this.entity.origin);
        }
      } else {
        this.$nextTick(() => {
          this.$refs.mk.init('')
        })
      }
    },
    loadField() {
      return this.$axios.get('manager/content/model', { categoryId: this.$route.query.categoryId })
    },
    loadDetail() {
      return this.$axios.get('manager/content/detail', { categoryId: this.$route.query.categoryId, id: this.$route.query.id })
    },
    fieldChange(field, value) {
      if (field == 'content') {
        this.entity['content'] = value.html;
        this.entity['origin'] = value.md;
      } else {
        this.entity[field] = value;
      }
    },
    submit() {
      this.$refs['form'].validate(valid => {
        if (valid) {
          if (this.entity.id != null) {
            this.$axios.put('manager/content/edit', { id: this.$route.query.id, categoryId: this.$route.query.categoryId, content: JSON.stringify(this.entity) }).then(data => {
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
            this.$axios.post('manager/content/add', { categoryId: this.$route.query.categoryId, content: JSON.stringify(this.entity) }).then(data => {
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