<template>
  <div class="h-100 d-flex flex-column">
    <header class="header"></header>
    <div class="error-main">
      <el-card class="box-card" :body-style="{ padding: '0px' }">
        <div class="bg d-flex justify-content-center align-items-center flex-column">
          <div class="icon"><i class="el-icon-warning-outline"></i></div>
          <div class="tips">
            非常抱歉，您访问的页面不存在~
          </div>
          <div class="action">
            <el-button round @click="back">返回上一页</el-button>
          </div>
        </div>
      </el-card>
    </div>
  </div>
</template>
<script lang="ts">
import { ref, defineComponent } from "vue";
export default defineComponent({
  methods: {
    back() {
      this.$router.go(-1);
    },
  },
});
</script>
<style lang="scss" scoped>
.header {
  height: 70px;
  background: #5b6be8;
  box-shadow: 1px 0px 20px rgba(0, 0, 0, 0.2);
}
.error-main {
  width: 500px;
  margin: 0 auto;
  padding-top: 100px;
  .bg {
    background: #40a4f1;
    border-radius: 4px;
    padding: 20px;
  }
  .icon {
    i {
      font-size: 130px;
      color: white;
    }
  }
  .tips {
    margin-top: 30px;
    font-size: 28px;
    color: white;
  }
  .action {
    margin-top: 30px;
  }
}
</style>