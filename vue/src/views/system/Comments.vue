/**
* 系统管理 评论管理
*/
<template>
  <div>
    <!-- 面包屑导航 -->
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>评论管理</el-breadcrumb-item>
    </el-breadcrumb>
    <!-- 搜索筛选 -->
    <el-form :inline="true" :model="formInline" class="comment-search">
      <el-form-item label="搜索：">
        <el-select size="small" v-model="formInline.audit" placeholder="请选择类别" style="width:120px">
          <el-option label="状态：全部" :value="-1"></el-option>
          <el-option label="未审核" :value="0"></el-option>
          <el-option label="审核通过" :value="1"></el-option>
          <el-option label="不通过" :value="2"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="">
        <el-input size="small" v-model="formInline.author" placeholder="发布者" style="width:100px"></el-input>
      </el-form-item>
      <el-form-item label="">
        <el-input size="small" v-model="formInline.title" placeholder="文章标题" style="width:140px"></el-input>
      </el-form-item>
      <el-form-item label="">
        <el-input size="small" v-model="articleid" placeholder="文章ID" style="width:80px"></el-input>
      </el-form-item>
      <el-form-item label="">
        <el-date-picker size="small" v-model="publishBegin" type="date" placeholder="开始日期"
                        style="width:130px"></el-date-picker>
      </el-form-item>
      <el-form-item label="">
        <el-date-picker size="small" v-model="publishEnd" type="date" placeholder="截止日期"
                        style="width:130px"></el-date-picker>
      </el-form-item>
      <el-form-item label="">
        <el-input size="small" v-model="formInline.content" placeholder="内容" style="width:120px"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button size="small" type="primary" icon="el-icon-search" @click="search">搜索</el-button>
        <el-button size="small" type="primary" icon="el-icon-delete" @click="resetSearch()">重置</el-button>
        <el-button size="small" type="danger" @click="batchDelete()">批量删除</el-button>
      </el-form-item>
    </el-form>
    <!--列表-->
    <el-table size="small" @selection-change="selectChange" :data="commentData" highlight-current-row
              v-loading="loading"
              border element-loading-text="拼命加载中" style="width: 100%;"
              :default-sort="{prop:'commentid',order:'ascending'}">
      <el-table-column align="center" type="selection" width="50">
      </el-table-column>
      <el-table-column align="center" sortable prop="commentid" label="CID" width="100">
      </el-table-column>
      <el-table-column align="center" sortable prop="name" label="发布者" width="100">
      </el-table-column>
      <el-table-column align="center" sortable prop="title" label="所属文章" width="100">
      </el-table-column>
      <el-table-column align="center" sortable prop="articleid" label="文章ID" width="100">
      </el-table-column>
      <el-table-column align="center" sortable prop="publishtime" label="发布时间" width="100">
        <template slot-scope="scope">
          <div>{{ scope.row.publishtime|timestampToTime }}</div>
        </template>
      </el-table-column>
      <el-table-column align="center" sortable prop="content" label="内容" min-width="50">
      </el-table-column>
      <el-table-column align="center" sortable prop="audit" label="审核状态" width="100px">
        <template slot-scope="scope">
          <div>{{ scope.row.audit == 0 ? '未审核' : scope.row.audit == 1 ? '审核通过' : '不通过' }}</div>
        </template>
      </el-table-column>
      <el-table-column label="操作" min-width="50">
        <template slot-scope="scope">
          <el-popover trigger="click" placement="left" width="150" :ref="`popover-${scope.$index}`">
            <div style="text-align: center; margin: 0">
              <el-select size="small" v-model="scope.row.audit" placeholder="请选择类别" style="width:120px">
                <el-option label="未审核" :value="0"></el-option>
                <el-option label="审核通过" :value="1"></el-option>
                <el-option label="不通过" :value="2"></el-option>
              </el-select>
            </div>
            <div style="text-align: right; margin-top:5px;">
              <el-button size="mini" type="text" @click="scope._self.$refs[`popover-${scope.$index}`].doClose()">取消</el-button>
              <el-button type="primary" size="mini" @click="handleAudit(scope)">确定</el-button>
            </div>
            <el-button slot="reference" size="mini">审核</el-button>
          </el-popover>
          <el-button size="mini" type="danger" @click="deleteComment(scope.$index, scope.row)">删除</el-button>
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
  commentList,
  commentDetele,
  commentBatchDetele,
  updateCommentAudit
} from '../../api/basicMG'
import Pagination from '../../components/Pagination'

export default {
  data() {
    return {
      loading: false, //是显示加载
      //搜索的起始截止日期
      publishBegin: '',
      publishEnd: '',
      articleid:'',
      // 选择数据和选择的id
      selectdata: [],
      ids: [],
      // 请求数据参数
      formInline: {
        page: 1,
        limit: 10,
        author: '',
        title: '',
        audit: -1,
        articleid: -1,
        content: '',
        publishBegin: '',
        publishEnd: '',
      },
      //评论数据
      commentData: [],
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
      //获取文章列表
      commentList(parameter).then(res => {
        this.loading = false
        if (res.success == false) {
          this.$message({
            type: 'info',
            message: res.msg
          })
        } else {
          this.commentData = res.data.records

          this.commentData.forEach(function(item){
            item.visible2 = false;
          })
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
      if (this.articleid === "") {
        this.formInline.articleid = -1
      } else {
        if (!r.test(this.articleid)) {
          this.$message.error('ID字段请输入正整数')
          return
        }
        this.formInline.articleid = Number(this.articleid)
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
      this.formInline.page = 1
      this.getdata(this.formInline)
    },
    // 选择复选框事件
    selectChange(val) {
      this.selectdata = val
      this.ids = []
      this.selectdata.forEach(value => {
        this.ids.push(value.commentid)
      })
    },
    // 删除文章
    deleteComment(index, row) {
      this.$confirm('确定要删除吗?', '信息', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      })
        .then(() => {
          // 删除
          commentDetele(row.commentid)
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
          commentBatchDetele(this.ids)
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
    //重置搜索
    resetSearch() {
      this.formInline.author = ''
      this.formInline.title = ''
      this.formInline.audit = -1
      this.formInline.publishBegin = ''
      this.formInline.publishEnd = ''
      this.formInline.articleid = -1
      this.formInline.content = ''
      this.articleid = ''
      this.publishBegin = ''
      this.publishEnd = ''

      this.formInline.page = 1
      this.getdata(this.formInline)
    },
    //审核修改
    handleAudit(scope){
      scope._self.$refs[`popover-${scope.$index}`].doClose()
      updateCommentAudit(scope.row.articleid, scope.row.audit).then(res => {
        this.loading = false
        if (res.success == false) {
          this.$message({
            type: 'info',
            message: res.msg
          })
        } else {
          this.$message({
            type: 'success',
            message: '保存成功!'
          })
        }
      })
    }
  }
}
</script>

<style scoped>
.comment-search {
  margin-top: 20px;
}
</style>
