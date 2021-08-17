<template>
  <el-dialog custom-class="d1-explorer" title="资源管理" v-model="show" width="960px" :close-on-click-modal="false" @close="close">
    <div class="msg" v-if="msg.show">
      <el-alert :title="msg.content" :type="msg.type" :closable="false" show-icon></el-alert>
    </div>
    <div class="upload d-flex justify-content-between">
      <div class="folder border">
        <div class="title border-bottom d-flex align-items-center justify-content-between">
          <div>目录</div>
          <div class="manager">
            <div v-if="!folder.edit" @click="changeFolderEdit">管理</div>
            <div v-if="folder.edit" class="edit d-flex">
              <div class="new" @click="changeFolderNew">新建</div>
              <div class="cancel" @click="changeFolderEdit">取消</div>
            </div>
          </div>
        </div>
        <div class="tree">
          <div v-if="folder.pid != '' && folder.pid != 0" @dblclick="backPrevious" class="folder-item d-flex align-items-center">
            <div class="icon"><i class="el-icon-folder"></i></div>
            <div>..</div>
          </div>
          <div v-if="folder.new && folder.edit" class="new-folder folder-item d-flex align-items-center">
            <div class="icon"><i class="el-icon-folder"></i></div>
            <div class="d-flex">
              <el-input size="mini" placeholder="请输入名称" clearable v-model="folder.newName">
              </el-input>
              <div style="width:8px"></div>
              <el-button type="success" size="mini" icon="el-icon-check" @click="folderAdd"></el-button>
            </div>
          </div>
          <div v-for="item in folder.list" :key="item.id" class="folder-item d-flex align-items-center">
            <div class="icon">
              <i v-if="!item.current" class="el-icon-folder"></i>
              <i v-if="item.current" class="el-icon-folder-opened"></i>
            </div>
            <div class="item-name d-flex">
              <template v-if="!item.edit">
                <div @click="fileOpen(item)" @dblclick="openFolder(item)">
                  {{ item.name }}
                </div>
              </template>
              <template v-else>
                <div class="d-flex" @blur="item.edit = false">
                  <el-input size="mini" placeholder="请输入名称" clearable v-model="item.name">
                  </el-input>
                  <div style="width:8px"></div>
                  <el-button type="success" size="mini" icon="el-icon-check" @click="folderPut(item)"></el-button>
                </div>
              </template>
              <div v-if="folder.edit && !item.edit" class="item-edit d-flex">
                <div class="rename" @click="folderEdit(item)">编辑</div>
                <div class="remove">
                  <el-popconfirm title="确定要删除吗？" @onConfirm="folderRemove(item.id)">
                    <template #reference>
                      <span>删除</span>
                    </template>
                  </el-popconfirm>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
      <div class="file border d-flex flex-column">
        <div class="action border-bottom d-flex justify-content-between">
          <template v-if="pageUpload">
            <div class="acl d-flex h-100">
              <div class="back h-100 d-flex align-items-center border-right" @click="back"><i class="el-icon-back"></i> 返回</div>
              <div class="add h-100 d-flex align-items-center">
                <el-button type="primary" icon="el-icon-circle-plus-outline" size="mini" @click="click">添加文件</el-button>
              </div>
              <div class="add h-100 d-flex align-items-center">
                <el-button type="success" icon="el-icon-video-play" size="mini" @click="start">开始上传</el-button>
              </div>
            </div>
          </template>
          <template v-else>
            <div class="acl d-flex h-100">
              <div class="add h-100 d-flex align-items-center">
                <el-button type="primary" icon="el-icon-circle-plus-outline" size="mini" @click="go">上传</el-button>
              </div>
              <div class="add h-100 d-flex align-items-center">
                <!-- <el-button type="danger" icon="el-icon-delete" size="mini" @click="click">删除</el-button> -->
              </div>
            </div>
            <div class="search h-100 d-flex align-items-center">
              <el-input size="mini" placeholder="请输入名称" clearable v-model="folder.newName">
              </el-input>
              <div></div>
              <el-button icon="el-icon-search" size="mini" @click="click">搜索</el-button>
            </div>
          </template>
        </div>
        <div class="bread d-flex align-items-center border-bottom">
          <el-breadcrumb separator="/">
            <el-breadcrumb-item>根目录</el-breadcrumb-item>
            <el-breadcrumb-item v-for="item in folder.path" :key="item.id">{{ item.name }}</el-breadcrumb-item>
          </el-breadcrumb>
        </div>
        <div class="file-list d-flex flex-wrap">
          <template v-if="!pageUpload">
            <div class="file-item" v-for="item in file.list.records" :key="item.id" @click="changeSelect(item)">
              <el-image class="file-item-image" :src="$config.STATIC_PATH + item.fileName" fit="cover"></el-image>
              <div class="file-item-select" v-if="item.selected">
                <div class="triangle-topright"></div>
                <i class="el-icon-check"></i>
              </div>
            </div>
          </template>
          <template v-else>
            <div class="up-item file-item" v-for="item in fileList" :key="item.id">
              <el-image class="file-item-image" :src="item.data" fit="cover"></el-image>
              <div v-if="item.uploading" class="item-progress d-flex align-items-center justify-content-center">
                <el-progress type="circle" :width="100" :percentage="item.percent" :status="item.status"></el-progress>
              </div>
              <div class="pos-hover" v-if="item.status == null">
                <div class="triangle-topright"></div>
                <i class="el-icon-close" @click="removeUpItem(item)"></i>
              </div>
            </div>
          </template>
        </div>
        <div class="file-footer d-flex justify-content-between border-top align-items-center">
          <template v-if="!pageUpload">
            <div>
              <el-pagination v-if="file.list.total > 0" @current-change="pageChange" v-model:current-page="file.list.current" :page-size="file.list.size" :total="file.list.total" layout="prev, pager, next, jumper">
              </el-pagination>
            </div>
            <div>
              <el-button type="primary" size="mini" :disabled="selectList.length ==0" @click="enter">确定</el-button>
            </div>
          </template>
          <template v-else>
            <div class="total-progress d-flex h-100">
              <div class="fpro d-flex align-items-center">
                <div style="width:50px">总进度</div>
                <div style="flex:1">
                  <el-progress :text-inside="true" :stroke-width="24" :percentage="percent" :status="status"></el-progress>
                </div>
              </div>
              <div class="info border-left d-flex align-items-center">
                共 {{fileList.length}} 个文件
                <template v-if="finish">
                  ,已全部完成
                </template>
                <template v-else>
                  <template v-if="!isRuning || current <= 0">,等待上传</template>
                  <template v-else>,正在上传第 {{current}} 个文件</template>
                </template>
              </div>
            </div>
          </template>
        </div>
      </div>
    </div>
  </el-dialog>
</template>
<script>
import axios from 'axios'
export default {
  props: {
    show: {
      type: Boolean,
      default: false
    },
    api: {
      type: String,
      // default: 'manager/file/upload'
      default: 'manager/test/upload'
    }
  },
  emits: [
    'result',
    'update:show'
  ],
  data() {
    return {
      msg: {
        show: false,
        type: 'success',
        content: ''
      },
      // 目录
      folder: {
        // 目录管理状态
        edit: false,
        // 新建状态
        new: false,
        // 新建目录名称
        newName: '',
        // 目录列表
        list: [],
        // 路径
        path: [],
        // 当前 ID
        id: '',
        // 当前 PID
        pid: '0',
        // 上一个 PID
        previous: '',
        // 双击时间差
        time: null
      },
      // 文件
      file: {
        pid: null,
        list: {
          records: [],
          current: 1,
          total: 0
        }
      },
      pageUpload: false,
      // 上传文件列表
      fileList: [],
      current: 0,
      // 当前百分比
      percent: 0,
      // 步进值
      step: 0,
      // 进度条状态
      status: null,
      // 完成
      finish: false,
      // 切片大小 2M
      shardSize: 5 * 1024 * 1024,
      // 是否正在运行
      isRuning: false,
      // 是否显示
      isShow: true,
      // 选中列表
      selectList: [],
      // 返回列表
      resultList: []
    }
  },
  watch: {
    show(nv, ov) {
      if (nv) {
        this.init();
      }
    }
  },
  mounted() {

  },
  methods: {
    init() {
      // 清空选中列表
      this.selectList = [];
      this.resultList = [];
      this.folderList();
      this.readFileList();
    },
    folderList() {
      this.folder.list = [];
      this.$axios.get('manager/folder/list', { pid: this.folder.pid }).then(data => {
        for (let item of data.result) {
          item.edit = false;
          item.current = false;
        }
        this.folder.list = data.result;
      }).catch(e => {
        this.folder.list = [];
      })
    },
    readFileList() {
      let pid = this.folder.pid;
      if (this.file.pid != null) {
        pid = this.file.pid;
      }
      this.file.list.records = [];
      this.$axios.get('manager/file/list', { folderId: pid, page: this.file.list.current }).then(data => {
        for (let item of data.result.records) {
          item.selected = this.selectList.includes(item.id);
        }
        this.file.list = data.result;
      }).catch(e => {
        this.file.list.records = [];
        this.file.list.current = 1;
        this.file.list.total = 0;
      })
    },
    // 切换页码
    pageChange(page) {
      this.readFileList();
    },
    // 切换目录管理状态
    changeFolderEdit() {
      this.folder.newName = '';
      this.folder.edit = !this.folder.edit;
      if (!this.folder.edit) {
        this.folder.new = this.folder.edit;
        this.resetItemEdit();
      }
    },
    resetAllEdit() {
      this.folder.newName = '';
      this.folder.edit = false;
      this.folder.new = false;
      this.resetItemEdit();
    },
    changeFolderNew() {
      if (this.folder.edit) {
        this.folder.newName = '';
        this.folder.new = true;
      }
    },
    // 新增目录
    folderAdd() {
      this.$axios.post('manager/folder/add', { name: this.folder.newName, pid: this.folder.pid }).then(data => {
        this.folder.new = false;
        this.alert('新增成功');
        this.folderList();
      }).catch(e => {
        this.alert('新增失败', 'error');
      })
    },
    folderEdit(data) {
      this.resetItemEdit();
      data.edit = true;
    },
    folderPut(data) {
      this.$axios.put('manager/folder/edit', { id: data.id, name: data.name }).then(data => {
        if (data.code == this.$config.SUCCESS_CODE) {
          this.alert('编辑成功');
          this.folderList();
        } else {
          this.alert('编辑失败', 'error');
        }
      })
    },
    resetItemEdit() {
      for (let item of this.folder.list) {
        item.edit = false;
      }
    },
    folderRemove(id) {
      this.$axios.delete('manager/folder/delete', { id }).then(data => {
        if (data.code == this.$config.SUCCESS_CODE) {
          this.alert('删除成功');
          this.folderList();
        } else {

        }

      })
    },
    // 打开文件
    fileOpen(data) {
      if (this.isRuning) {
        this.alert('正在上传文件...', 'error');
        return;
      }
      clearTimeout(this.folder.time);
      this.folder.time = setTimeout(() => {
        this.file.pid = data.id;
        this.file.list.current = 1;
        this.file.list.size = 0;
        this.readFileList();
        this.resetCurrent();
        data.current = true;
      }, 300);
    },
    resetCurrent() {
      for (let item of this.folder.list) {
        item.current = false;
      }
    },
    // 打开目录
    openFolder(data) {
      if (this.isRuning) {
        this.alert('正在上传文件...', 'error');
        window.getSelection().removeAllRanges();
        return;
      }
      clearTimeout(this.folder.time);
      // 重置编辑状态
      this.resetAllEdit();
      // 记录当前信息
      this.folder.id = data.id;
      this.folder.pid = data.id;
      // 插入路径
      this.folder.path.push(data);
      // 查询目录
      this.folderList();
      // 查询文件
      this.file.pid = null;
      this.file.list.current = 1;
      this.file.list.size = 0;
      this.readFileList();
      // 清除双击选中文字
      window.getSelection().removeAllRanges();
    },
    // 返回上一个
    backPrevious() {
      if (this.isRuning) {
        this.alert('正在上传文件...', 'error');
        window.getSelection().removeAllRanges();
        return;
      }
      // 重置编辑状态
      this.resetAllEdit();
      // 弹出上一目录
      let data = this.folder.path.pop();
      // 记录当前信息
      this.folder.id = data.id;
      this.folder.pid = data.prarentId;
      // 查询目录
      this.file.pid = null;
      this.file.list.current = 1;
      this.file.list.size = 0;
      this.folderList();
      // 查询文件
      this.readFileList();
      // 清除双击选中文字
      window.getSelection().removeAllRanges();
    },
    alert(content, type = 'success') {
      this.msg.show = true;
      this.msg.type = type;
      this.msg.content = content;
      setTimeout(() => {
        this.msg.show = false;
        this.msg.content = '';
      }, 1500);
    },
    // 进入上传页
    go() {
      this.isRuning = false;
      this.fileList = [];
      this.percent = 0;
      this.finish = false;
      this.current = 0;
      this.pageUpload = true;
    },
    // 返回资源管理
    back() {
      // 查询文件
      this.readFileList();
      this.pageUpload = false;
    },
    // 移除要上传的文件
    removeUpItem(data) {
      this.fileList.remove(data);
    },
    // 选择文件   
    click() {
      let file = document.createElement('input');
      file.type = 'file';
      file.multiple = 'multiple';
      file.onchange = event => {
        for (const f of event.target.files) {
          // 读取文件
          const reader = new FileReader();
          reader.readAsDataURL(f);
          reader.onload = () => {
            this.fileList.push({
              data: reader.result,
              uploading: false,
              percent: 0,
              file: f,
              status: null
            })
          }
        }
      }
      file.click();
    },
    async start() {
      if (this.fileList.length <= 0) {
        this.alert('请添加上传的文件', 'error');
        return;
      }
      if (this.isRuning) {
        this.alert('文件正在上传中,请稍后...', 'error');
        return;
      }
      let hasNew = false;
      for (const f of this.fileList) {
        if (null == f.status) {
          hasNew |= true;
        }
      }
      if (!hasNew) {
        this.alert('没有新的文件需要上传', 'error');
        return;
      }
      // 设置开始上传标识
      this.isRuning = true;
      // 总进度条步进值
      this.step = Math.ceil(100 / this.fileList.length);

      for (const item of this.fileList) {
        // 跳过已上传文件
        if (item.status == 'success') {
          continue;
        }
        // 当前文件
        let file = item.file;
        // 文件总大小
        let size = file.size;
        // 文件名称
        let ext = file.name;
        // 进度
        let succeed = 0;
        // 总片数
        let shardCount = Math.ceil(size / this.shardSize);
        // 步进值
        let step = Math.ceil(100 / shardCount);

        let pw = 0;
        // 切片的 FORM 数量列表
        let shardList = [];
        // 计算切片
        for (let i = 0; i < shardCount; i++) {
          // 计算每一片的起始位置
          let start = i * this.shardSize;
          // 算每一片的结束位置
          let end = Math.min(size, start + this.shardSize);
          // 构造一个表单，FormData是HTML5新增的
          let form = new FormData();
          // slice方法用于切出文件的一部分          
          form.append('name', '');
          // 设置目录ID
          form.append('folder', this.folder.pid);
          form.append('file', new File([file.slice(start, end)], file.name, { type: file.type }));
          // form.append('ext', ext.split('.')[1]);
          form.append('ext', ext.substring(ext.lastIndexOf('.') + 1, ext.length));
          form.append('total', shardCount - 1);
          form.append('index', i);
          shardList.push(form);
        }
        for (let i = 0; i < shardList.length; i++) {
          // 开始上传
          item.uploading = true;
          // 上传切片
          let uData = await this.upload(shardList[i]);
          // 防止进度条超过数值
          let pv = item.percent + step;
          if (pv <= 100) {
            item.percent = pv;
          } else {
            item.percent = 100;
          }
          if (uData.code == 200) {
            item.status = 'success';
            // 更新总进度            
            let tpv = this.percent + this.step;
            if (tpv <= 100) {
              this.percent = tpv;
            } else {
              this.percent = 100;
            }
            this.current++;
          } else if (uData.code == 201) {
            // 判断是否为最后一片
            if (i < shardCount) {
              shardList[i + 1].set('name', uData.result);
            }
          }
        }
      }

      this.isRuning = false;

      if (this.current >= this.fileList.length) {
        this.finish = true;
      }

    },
    upload(data) {
      return new Promise((resolve, reject) => {
        axios.request({
          url: this.$config.BASE_API + this.api,
          method: 'post',
          data: data,
          headers: {
            'Content-Type': 'multipart/form-data;boundary = ' + new Date().getDate(),
            'Authorization': this.$store.getters.token.accessToken
          },
        }).then(response => {
          const data = response.data;
          resolve({ code: data.code, result: data.result });
        })
      })
    },
    // 切换选中
    changeSelect(data) {
      data.selected = !data.selected;
      if (data.selected) {
        this.selectList.push(data.id);
        this.resultList.push(data.fileName);
      } else {
        this.selectList.remove(data.id);
        this.resultList.remove(data.fileName);
      }
    },
    enter() {
      this.$emit('result', this.resultList);
      this.close();
    },
    close() {
      this.$emit('update:show', false)
    }
  }
}
</script>
<style lang="scss" scoped>
.d1-explorer {
  .upload {
    height: 410px;
  }
  .folder {
    width: 208px;
    margin-right: 10px;
    .title {
      height: 30px;
      padding: 0 10px;
      .manager {
        .new {
          margin-right: 8px;
        }
        & > div {
          cursor: pointer;
        }
      }
    }
    .tree {
      .folder-item {
        padding: 0 10px;
        margin-top: 5px;
        cursor: pointer;
        .icon {
          margin-right: 5px;
          color: #ffc001;
        }
        .item-name {
          flex: 1;
          position: relative;
          .item-edit {
            position: absolute;
            top: 0;
            right: 0;
            bottom: 0;
            color: #007bff;
            background: white;
            & > div {
              padding-left: 8px;
            }
          }
        }
      }
    }
  }
  .file {
    flex: 1;
    .action {
      height: 40px;
      padding: 0 10px;
      .acl {
        .back {
          padding-right: 10px;
          i {
            font-size: 16px;
            margin-right: 5px;
          }
        }
        & > div {
          cursor: pointer;
          margin-right: 10px;
        }
      }
    }
    .bread {
      height: 30px;
      padding: 0 10px;
      color: #999;
    }
    .file-list {
      padding: 10px 5px;
      overflow: hidden;
      flex: 1;
      .up-item {
        .pos-hover {
          display: none;
        }
        &:hover {
          .pos-hover {
            display: block;
            position: absolute;
            top: 0;
            right: 0;
            bottom: 0;
            left: 0;
            border: solid 4px #409eff;
            .triangle-topright {
              position: absolute;
              top: 0;
              right: 0;
              $size: 30px;
              width: 0;
              height: 0;
              border-top: $size solid #409eff;
              border-left: $size solid transparent;
            }
            i {
              position: absolute;
              top: 1px;
              right: 1px;
              color: white;
            }
          }
        }
      }
      .file-item {
        cursor: pointer;
        position: relative;
        $size: 128px;
        width: $size;
        height: $size;
        margin: 0 5px 10px;
        &-image {
          width: 100%;
          height: 100%;
        }
        .item-progress {
          position: absolute;
          top: 0;
          right: 0;
          bottom: 0;
          left: 0;
          background: rgba($color: #000000, $alpha: 0.4);
        }
        .file-item-select {
          position: absolute;
          top: 0;
          right: 0;
          bottom: 0;
          left: 0;
          border: solid 4px #409eff;
          .triangle-topright {
            position: absolute;
            top: 0;
            right: 0;
            $size: 30px;
            width: 0;
            height: 0;
            border-top: $size solid #409eff;
            border-left: $size solid transparent;
          }
          i {
            position: absolute;
            top: 1px;
            right: 1px;
            color: white;
          }
        }
      }
    }
    .file-footer {
      padding: 0 10px;
      height: 40px;
      .total-progress {
        width: 100%;
        .info {
          width: 230px;
          padding-left: 10px;
        }
        .fpro {
          padding-right: 10px;
          flex: 1;
        }
      }
    }
  }
  .msg {
    position: absolute;
    top: 15px;
    left: 50%;
    width: 350px;
    margin-left: -175px;
  }
}
</style>
<style lang="scss">
.d1-explorer {
  .file-list {
    .el-progress__text {
      color: white;
    }
  }
  .el-dialog__header {
    padding: 20px 20px 10px;
  }
  .el-dialog__body {
    padding: 10px 20px 20px;
  }
  .el-dialog__footer {
    padding: 0;
  }
}
</style>