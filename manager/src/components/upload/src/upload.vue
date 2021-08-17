<template>
  <div class="d1-upload">
    <div class="upload-list d-flex">
      <div class="item" v-for="(item,index) in list" :key="item">
        <div class="action justify-content-between align-items-stretch">
          <div class="move d-flex align-items-center">
            <div class="left-move" @click="left(index)">
              <i class="el-icon-back"></i>
            </div>
            <div class="right-move" @click="right(index)">
              <i class="el-icon-right"></i>
            </div>
          </div>
          <div class="remove d-flex align-items-center" @click="remove(item)">
            <i class="el-icon-delete"></i>
          </div>
        </div>
        <img class="img-fluid" :src="$config.STATIC_PATH + item">
      </div>
      <div class="item d-flex justify-content-center align-items-center" v-if="list.length < length" @click="upload">
        <div>
          <div class="icon d-flex justify-content-center align-items-center">
            <i class="el-icon-upload2"></i>
          </div>
          <div class="tips">
            <div class="ext text-center">请选择图片</div>
            <div class="size text-center">限制 2M</div>
          </div>
        </div>
      </div>
    </div>
    <d1-explorer v-model:show="show" @result="result"></d1-explorer>
  </div>
</template>
<script>
export default {
  props: {
    value: {
      type: Array,
      default: []
    },
    length: {
      type: Number,
      default: 1
    }
  },
  emits: [
    'update:value'
  ],
  data() {
    return {
      show: false,
      list: []
    }
  },
  watch: {
    value: function (v, o) {      
      this.list = v;
    }
  },
  mounted() {

  },
  methods: {
    upload() {
      this.show = true;
    },
    result(data) {
      let count = this.length - this.list.length;
      if (count > 0) {
        for (let i = 0; i < count; i++) {
          this.list.push(data[i]);
        }
        this.returnValue();
      }
    },
    remove(data) {
      this.list.remove(data);
      this.returnValue();
    },
    left(index) {
      this.list.up(index);
      this.returnValue();
    },
    right(index) {
      this.list.down(index);
      this.returnValue();
    },
    returnValue() {
      this.$emit('update:value', this.list);
    }
  }
}
</script>
<style lang="scss" scoped>
.d1-upload {
  .upload-list {
    .item {
      position: relative;
      $size: 146px;
      width: $size;
      height: $size;
      line-height: normal !important;
      border-radius: 6px;
      border: 1px dashed #c0ccda;
      background: #fbfdff;
      cursor: pointer;
      overflow: hidden;
      margin-right: 10px;
      &:hover {
        border-color: #5b6be8;
        .action {
          display: flex;
        }
      }
      .action {
        display: none;
        position: absolute;
        top: 0;
        left: 0;
        right: 0;
        height: 28px;
        background: rgba($color: #000000, $alpha: 0.5);
        .move {
          color: white;
          & > div {
            margin-left: 5px;
          }
        }
        .remove {
          color: white;
          margin-right: 5px;
        }
      }
      .icon {
        font-size: 28px;
        color: #aaa;
      }
      .tips {
        font-size: 13px;
        color: #aaa;
        margin-top: 10px;
        .size {
          font-size: 12px;
          margin-top: 5px;
        }
      }
    }
  }
}
</style>