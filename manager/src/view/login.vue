<template>
  <div class="login-bg h-100 d-flex justify-content-center align-items-center">
    <div class="login bg-white d-flex justify-content-between">
      <div class="banner flex-full d-flex justify-content-center align-items-center">
        <img class="img-fluid" src="../assets/image/login_banner.png" />
      </div>
      <div class="login-form">
        <div class="d-flex justify-content-center">
          <h1>系统登录</h1>
        </div>
        <el-form ref="form" :model="entity" :rules="rules" label-width="0px">
          <el-form-item label="" prop="account">
            <el-input v-model="entity.account" style="width:100% !important">
              <template #prefix>
                <i class="el-input__icon el-icon-user"></i>
              </template>
            </el-input>
          </el-form-item>
          <el-form-item label="" prop="password">
            <el-input v-model="entity.password" show-password style="width:100% !important">
              <template #prefix>
                <i class="el-input__icon el-icon-lock"></i>
              </template>
            </el-input>
          </el-form-item>
          <el-form-item>
            <div class="d-flex justify-content-center">
              <el-button type="primary" round style="width:200px" @click="login">登录</el-button>
            </div>
          </el-form-item>
        </el-form>
      </div>
    </div>
  </div>
</template>
<script>
export default {
  data() {
    return {
      entity: {
        account: "",
        password: "",
      },
      rules: {
        account: [{
          required: true,
          message: '请输入账号'
        }],
        password: [{
          required: true,
          message: '请输入密码'
        }]
      }
    }
  },
  methods: {
    login() {
      this.$refs['form'].validate(valid => {
        if (valid) {

          let params = {
            account: this.entity.account,
            password: this.entity.password,
            grantType: "password",
            clientId: this.$config.CLIENT_ID,
            clientSecret: this.$config.CLIENT_SECRET,
            scope: "all",
          };
          this.$axios
            .post("oauth2/token", params)
            .then((data) => {
              let isAdmin = data.result.authorities.find((value) => {
                return value === "ROLE_ADMIN";
              });
              if (isAdmin) {
                this.$store.commit("updateToken", {
                  accessToken: data.result.accessToken,
                  refreshToken: data.result.refreshToken,
                });
                this.$session.setSession("accessToken", data.result.accessToken);
                this.$session.setSession("refreshToken", data.result.refreshToken);
                this.successRedirect();
              } else {
                this.$message.error("登录的账户不存在");
              }
            })
            .catch((error) => {
              this.$message.error("登录失败");
            })

        }
      })
    },
    successRedirect() {
      this.$message({
        message: "登录成功，正在跳转...",
        type: "success",
        duration: 1500,
        onClose: () => {
          this.$router.replace({
            path: "/"
          })
        }
      })
    }
  }
}
</script>
<style lang="scss" scoped>
.login {
  width: 800px;
  height: 400px;
  border-radius: 10px;
  box-shadow: 0 0 20px #a1a1a1;
  .login-form {
    width: 300px;
    padding: 0 35px;
    border-left: solid 1px #eee;
  }
}
.login-bg {
  background: linear-gradient(to right, #4262e9, #558ef5);
}
</style>