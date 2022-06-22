/**
* 系统管理 点赞管理
*/
<template>
  <div>
    <!-- 面包屑导航 -->
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>点赞信息</el-breadcrumb-item>
    </el-breadcrumb>
    <!-- 搜索筛选 -->
    <el-form :inline="true" :model="formInline" class="user-search">
      <el-form-item label="搜索：">
        <el-input size="small" v-model="formInline.author" placeholder="输入用户名" style="width:120px"></el-input>
      </el-form-item>
      <el-form-item label="">
        <el-input size="small" v-model="articleid" placeholder="输入文章ID" style="width:150px"></el-input>
      </el-form-item>
      <el-form-item label="">
        <el-input size="small" v-model="formInline.title" placeholder="输入文章标题" style="width:150px"></el-input>
      </el-form-item>
      <el-form-item label="">
        <el-date-picker size="small" v-model="publishBegin" type="date" placeholder="开始日期"
                        style="width:130px"></el-date-picker>
      </el-form-item>
      <el-form-item label="">
        <el-date-picker size="small" v-model="publishEnd" type="date" placeholder="截止日期"
                        style="width:130px"></el-date-picker>
      </el-form-item>
      <el-form-item>
        <el-button size="small" type="primary" icon="el-icon-search" @click="search">搜索</el-button>
        <el-button size="small" type="primary" icon="el-icon-delete" @click="resetSearch()">重置</el-button>
        <el-button size="small" type="danger" @click="batchDelete()">批量删除</el-button>
      </el-form-item>
    </el-form>
    <!--列表-->
    <el-table size="small" @selection-change="selectChange" :data="likeData" highlight-current-row v-loading="loading"
              border element-loading-text="拼命加载中" style="width: 100%;" :default-sort ="{prop:'likeid',order:'ascending'}">
      <el-table-column align="center" type="selection" width="50">
      </el-table-column>
      <el-table-column align="center" sortable prop="likeid" label="LID" width="200">
      </el-table-column>
      <el-table-column align="center" sortable prop="name" label="发布者" width="200">
      </el-table-column>
      <el-table-column align="center" sortable prop="articleid" label="文章ID" width="150">
      </el-table-column>
      <el-table-column align="center" sortable prop="title" label="文章标题" width="200">
      </el-table-column>
      <el-table-column align="center" sortable prop="liketime" label="点赞时间" width="200">
        <template slot-scope="scope">
          <div>{{ scope.row.liketime|timestampToTime }}</div>
        </template>
      </el-table-column>

      <el-table-column label="操作" min-width="100" align="center">
        <template slot-scope="scope">
          <el-button size="mini" type="danger" @click="deleteLike(scope.$index, scope.row)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <!-- 分页组件 -->
    <Pagination v-bind:child-msg="pageparm" @callFather="callFather"></Pagination>

  </div>
</template>

<script>
// 导入请求方法
import {
  likeList,
  likeDetele,
  likeBatchDetele
} from '../../api/basicMG'
import Pagination from '../../components/Pagination'

export default {
  data() {
    return {
      loading: false, //是显示加载
      //搜索的起始截止日期和ID号
      articleid: '',
      publishBegin: '',
      publishEnd: '',
      // 选择数据和选择的id
      selectdata: [],
      ids: [],
      // 请求数据参数
      formInline: {
        page: 1,
        limit: 10,
        author: '',
        articleid: -1,
        title: '',
        publishBegin: '',
        publishEnd: '',
      },
      //用户数据
      likeData: [],
      // 分页参数
      pageparm: {
        currentPage: 1,
        pageSize: 10,
        total: 10
      }
    }
  },
  // 注册组件
  components: {
    Pagination
  },

  /**
   * 创建完毕
   */
  created() {
    this.getdata(this.formInline)
  },

  /**
   * 里面的方法只有被调用才会执行
   */
  methods: {
    // 获取数据方法
    getdata(parameter) {
      this.loading = true
      //获取点赞列表
      likeList(parameter).then(res => {
        this.loading = false
        if (res.success == false) {
          this.$message({
            type: 'info',
            message: res.msg
          })
        } else {
          //console.log(res)
          this.likeData = res.data.records
          // 分页赋值
          this.pageparm.currentPage = this.formInline.page
          this.pageparm.pageSize = this.formInline.limit
          this.pageparm.total = res.data.total
        }
      })
    },
    // 分页插件事件
    callFather(parm) {
      this.formInline.page = parm.currentPage
      this.formInline.limit = parm.pageSize
      //console.log(this.formInline)
      this.getdata(this.formInline)
    },
    //搜索事件
    search() {
      const r = /^\+?[0-9][0-9]*$/;//正整数
      if (this.articleid !== '') {
        if (!r.test(this.articleid)) {
          this.$message.error('ID字段请输入正整数！')
          return
        }
      }
      if (this.publishBegin !== '' && this.publishEnd !== '' && this.publishEnd - this.publishBegin < 0) {
        this.$message.error('请输入正确的日期范围')
        return
      }
      if (this.publishBegin === '') {
        this.formInline.publishBegin = ''
      } else {
        this.formInline.publishBegin = this.publishBegin.getFullYear() + '-' + (this.publishBegin.getMonth() + 1) + '-' + this.publishBegin.getDate()
      }
      if (this.publishEnd === '') {
        this.formInline.publishEnd = ''
      } else {
        this.formInline.publishEnd = this.publishEnd.getFullYear() + '-' + (this.publishEnd.getMonth() + 1) + '-' + (this.publishEnd.getDate() + 1)
      }
      if (this.articleid!==''){
        this.formInline.articleid = Number(this.articleid)
      }
      this.formInline.page = 1
      console.log(this.formInline)
      this.getdata(this.formInline)
    },
    // 选择复选框事件
    selectChange(val) {
      this.selectdata = val
      this.ids = []
      this.selectdata.forEach(value => {
        this.ids.push(value.likeid)
      })
    },
    // 删除点赞记录
    deleteLike(index, row) {
      this.$confirm('确定要删除吗?', '信息', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      })
        .then(() => {
          // 删除
          //console.log(row.userid)
          likeDetele(row.likeid)
            .then(res => {
              if (res.success) {
                this.$message({
                  type: 'success',
                  message: '数据已删除!'
                })
                this.getdata(this.formInline)
              } else {
                this.$message({
                  type: 'info',
                  message: res.msg
                })
              }
            })
            .catch(err => {
              this.loading = false
              this.$message.error('数据删除失败，请稍后再试！')
            })
        })
        .catch(() => {
          this.$message({
            type: 'info',
            message: '已取消删除'
          })
        })
    },
    //批量删除
    batchDelete() {
      this.$confirm('确定要批量删除吗?', '信息', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      })
        .then(() => {
          // 删除
          likeBatchDetele(this.ids)
            .then(res => {
              if (res.success) {
                this.$message({
                  type: 'success',
                  message: '数据已删除!'
                })
                this.getdata(this.formInline)
              } else {
                this.$message({
                  type: 'info',
                  message: res.msg
                })
              }
            })
            .catch(err => {
              this.loading = false
              this.$message.error('数据删除失败，请稍后再试！')
            })
        })
        .catch(() => {
          this.$message({
            type: 'info',
            message: '已取消删除'
          })
        })
    },
    resetSearch() {
      this.formInline.author = ''
      this.formInline.title = ''
      this.formInline.articleid = -1
      this.formInline.publishEnd = ''
      this.formInline.publishBegin = ''

      this.articleid = ''
      this.publishBegin = ''
      this.publishEnd = ''

      this.formInline.page = 1
      this.getdata(this.formInline)
    }
  }
}
</script>

<style scoped>
.user-search {
  margin-top: 20px;
}

</style>
