<template>
  <d1-list-page title="站点列表" api-list="manager/site/list" api-del="manager/site/delete">
    <template v-slot:column>
      <el-table-column prop="name" label="站点名称">
      </el-table-column>
      <el-table-column prop="identifier" label="站点标识" width="200">
      </el-table-column>
      <el-table-column label="状态" width="100">
        <template #default="scope">
          <span style="color:#dc3545" v-if="scope.row.status == 0">禁用</span>
          <span style="color:#28a745" v-if="scope.row.status == 1">启用</span>
        </template>
      </el-table-column>
    </template>
  </d1-list-page>
</template>