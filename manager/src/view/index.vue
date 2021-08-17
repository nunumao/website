<template>
  <div class="wrap h-100">
    <el-container class="h-100">
      <aside class="sidebar bg-white" :style="{width:menu.width+'px'}">
        <div class="logo d-flex justify-content-center align-items-center">管理后台</div>
        <div class="">
          <el-scrollbar :height="menu.height + 'px'">
            <el-menu :default-active="$store.getters.menu.active +''" class="el-menu-vertical-demo" active-text-color="#5b6be8" :collapse="menu.collapse" :unique-opened="true" @select="menuClick">
              <el-submenu v-for="item in menu.list" :key="item.id" :index="item.id + ''">
                <template #title>
                  <i class="el-icon-location"></i>
                  <span>{{item.name}}</span>
                </template>
                <el-menu-item v-for="jtem in item.child" :key="jtem.id" :index="jtem.id+''">
                  <div style="padding-left:20px">{{jtem.name}}</div>
                </el-menu-item>
              </el-submenu>
            </el-menu>
          </el-scrollbar>
        </div>
      </aside>
      <el-container class="flex-column">
        <header class="header d-flex" style="height:70px">
          <div class="d-flex nav h-100">
            <div class="fold h-100 d-flex justify-content-center align-items-center text-white" @click="foldMenu">
              <i class="el-icon-s-fold" v-if="!menu.collapse"></i>
              <i class="el-icon-s-unfold" v-if="menu.collapse"></i>
            </div>
          </div>
          <div class="top-nav flex-full"></div>
          <div class="profile d-flex h-100">
            <div class="message d-flex justify-content-center align-items-center">
              <div><i class="el-icon-message text-white"></i></div>
            </div>
            <div class="notify d-flex justify-content-center align-items-center">
              <div><i class="el-icon-bell text-white"></i></div>
            </div>
            <div class="user d-flex justify-content-center align-items-center">
              <div class="face d-flex text-white justify-content-center align-items-center"><i class="el-icon-user"></i></div>
            </div>
          </div>
        </header>
        <el-main class="main-content">
          <el-scrollbar :height="menu.height + 'px'">
            <div class="page-title d-flex justify-content-between align-items-center">
              <div class="title">{{ $store.getters.ui.pageTitle }}</div>
              <div class="breadcrumb">
                <el-breadcrumb separator="/">
                  <el-breadcrumb-item :to="{ path: '/' }">控制台</el-breadcrumb-item>
                  <el-breadcrumb-item>活动管理</el-breadcrumb-item>
                  <el-breadcrumb-item>活动列表</el-breadcrumb-item>
                </el-breadcrumb>
              </div>
            </div>
            <div class="content-box h-100">
              <router-view />
            </div>
          </el-scrollbar>
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>
<script>
import { ref, defineComponent } from "vue";
export default defineComponent({
  data() {
    return {
      menu: {
        width: 240,
        height: 0,
        collapse: false,
        list: [],
        path: []
      }
    }
  },
  mounted() {
    this.initView();
    this.init();
  },
  methods: {
    initView() {
      this.menu.height = this.$el.clientHeight - 70;
    },
    async init() {
      let list = this.$store.getters.menu.list;
      // 构建菜单路径
      list.forEach((item) => {
        if (item.child) {
          item.child.forEach((jtem) => {
            this.menu.path[jtem.id] = jtem.path;
          })
        }
      });
      this.menu.list = list;
    },
    loadMenu() {      
      return this.$axios.get('manager/rule/menu').catch(e => { console.log(e); });
    },
    foldMenu() {
      this.menu.collapse = !this.menu.collapse;
      let sidebar = this.$el.querySelector(".sidebar");
      if (this.menu.collapse) {
        sidebar.classList.add("fold-close");
        sidebar.classList.remove("fold-open");
      } else {
        sidebar.classList.remove("fold-close");
        sidebar.classList.add("fold-open");
      }
    },
    menuClick(index) {
      if (this.menu.path[index]) {
        this.$session.setSession('active', index);
        this.$router.push({
          path: "/" + this.menu.path[index]
        });
      } else {
        this.$router.push({
          path: "/error"
        });
      }
    }
  },
});
</script>
<style lang="scss" scoped>
@use "sass:math";
.wrap {
  .sidebar {
    box-shadow: 1px 0px 20px rgba(0, 0, 0, 0.05);
    z-index: 9;
    .logo {
      height: 70px;
      font-size: 24px;
      font-weight: 500;
    }
  }
  .header {
    background: #5b6be8;
    box-shadow: 1px 0px 20px rgba(0, 0, 0, 0.2);
    .nav {
      .fold {
        font-size: 28px;
        cursor: pointer;
      }
    }
    .profile {
      & > div {
        padding: 0 15px;
        cursor: pointer;
        i {
          font-size: 20px;
        }
      }
      .user {
        .face {
          $size: 36px;
          width: $size;
          height: $size;
          border-radius: math.div($size, 2);
          background: #999;
        }
      }
    }
  }
  .main-content {
    padding: 0 !important;
    overflow: hidden;
    .page-title {
      padding: 20px 20px 0;
      .title {
        font-size: 20px;
        font-weight: 500;
      }
    }
    .content-box {
      padding: 20px;
    }
  }
}
</style>
<style lang="scss">
.el-menu {
  border-right: none !important;
}
.fold-close {
  animation: sidebar-fold-close 1s forwards;
}
.fold-open {
  animation: sidebar-fold-open 1s forwards;
}
@keyframes sidebar-fold-close {
  from {
    width: 240px;
  }
  to {
    width: 64px;
  }
}
@keyframes sidebar-fold-open {
  from {
    width: 64px;
  }
  to {
    width: 240px;
  }
}
</style>