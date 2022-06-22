/**
* 系统管理 标签管理
*/
<template>
  <div>
    <!-- 面包屑导航 -->
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>标签管理</el-breadcrumb-item>
    </el-breadcrumb>
    <!--搜索筛选-->
    <el-form :inline="true" :model="formInline" class="user-search">
      <el-form-item label="搜索：">
        <el-input size="small" v-model="labelid" placeholder="输入标签ID" style="width:120px"></el-input>
      </el-form-item>
      <el-form-item label="">
        <el-input size="small" v-model="formInline.labelname" placeholder="输入标签名称" style="width:150px"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button size="small" type="primary" icon="el-icon-search" @click="search">搜索</el-button>
        <el-button size="small" type="primary" icon="el-icon-delete" @click="resetSearch()">重置</el-button>
        <el-button size="small" type="primary" icon="el-icon-plus" @click="handleEdit()">添加</el-button>
        <el-button size="small" type="danger" @click="batchDelete()">批量删除</el-button>
      </el-form-item>
    </el-form>
    <!--列表-->
    <el-table size="small" @selection-change="selectChange" :data="labelData" highlight-current-row v-loading="loading"
              border element-loading-text="拼命加载中" style="width: 100%;" :default-sort ="{prop:'labelid',order:'ascending'}">
      <el-table-column align="center" type="selection" width="50">
      </el-table-column>
      <el-table-column align="center" sortable prop="labelid" label="LID" width="250">
      </el-table-column>
      <el-table-column align="center" sortable prop="labelname" label="名称" width="250">
      </el-table-column>
      <el-table-column align="center" sortable prop="quotenum" label="引用次数" width="250">
      </el-table-column>
      <el-table-column label="操作" min-width="120" align="center">
        <template slot-scope="scope">
          <el-button size="mini" @click="handleEdit(scope.$index, scope.row)">修改名称</el-button>
          <el-button size="mini" type="danger" @click="deleteLabel(scope.$index, scope.row)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <!-- 编辑界面 -->
    <el-dialog :title="title" :visible.sync="editFormVisible" width="30%" @click='closeDialog()'>
      <el-form label-width="80px" ref="editForm" :model="editForm" :rules="rules">
        <el-form-item label="名称" prop="labelname">
          <el-input size="small" v-model="editForm.labelname" auto-complete="off" placeholder="请输入标签名"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button size="small" @click='closeDialog()'>取消</el-button>
        <el-button size="small" type="primary" :loading="loading" class="title" @click="submitForm('editForm')">保存
        </el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
// 导入请求方法
import {
  allLabel,
  addLabel,
  labelBatchDetele,
  updateLabelname,
  labelDetele
} from '../../api/basicMG'
import Pagination from '../../components/Pagination'

export default {
  data() {
    return {
      loading: false, //是显示加载
      //搜索的起始截止日期和ID号
      labelid: '',
      title: '',
      isAdd: false,
      // 选择数据和选择的id
      selectdata: [],
      ids: [],
      //表单验证
      rules: {
        labelname: [{required: true, message: '请输入标签名', trigger: 'blur'}]
      },
      // 请求数据参数
      formInline: {
        page: 1,
        limit: 10,
        labelid: -1,
        labelname: '',
      },
      //标签数据
      labelData:[],
      // 分页参数
      pageparm: {
        currentPage: 1,
        pageSize: 10,
        total: 10
      },
      editFormVisible: false,
      editForm: {
        labelid: '',
        labelname: ''
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
      //获取所有标签列表
      allLabel(parameter).then(res => {
        this.loading = false
        if (res.success == false) {
          this.$message({
            type: 'info',
            message: res.msg
          })
        } else {
          this.labelData = res.data.records
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
      if (this.labelid !== '') {
        if (!r.test(this.labelid)) {
          this.$message.error('ID字段请输入正整数！')
          return
        }
        this.formInline.labelid = Number(this.labelid)
      } else {
        this.formInline.labelid = -1
      }
      this.formInline.page = 1
      this.getdata(this.formInline)
    },
    //重置搜索
    resetSearch() {
      this.formInline.labelid = -1
      this.formInline.labelname = ''

      this.labelid = ''

      this.formInline.page = 1
      this.getdata(this.formInline)
    },
    // 选择复选框事件
    selectChange(val) {
      this.selectdata = val
      this.ids = []
      this.selectdata.forEach(value => {
        this.ids.push(value.labelid)
      })
    },
    // 删除标签
    deleteLabel(index, row) {
      this.$confirm('确定要删除吗?', '信息', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      })
        .then(() => {
          // 删除
          labelDetele(row.labelid)
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
          labelBatchDetele(this.ids)
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
    //关闭编辑
    closeDialog() {
      this.editFormVisible = false;
      this.editForm.labelname = ''
    },
    //显示编辑界面
    handleEdit: function (index, row) {
      this.editFormVisible = true
      if (row != undefined && row != 'undefined') {
        this.isAdd = false;
        this.title = '修改名称'
        this.editForm.labelid = row.labelid
        this.editForm.labelname = row.labelname
      } else {
        this.isAdd = true;
        this.title = '添加标签'
        this.editForm.labelid = ''
        this.editForm.labelname = ''
      }
    },
    //提交添加标签
    submitForm(editData){
      this.$refs[editData].validate(valid => {
        if (valid) {
          if (this.isAdd){
            addLabel(this.editForm.labelname)
              .then(res => {
                this.editFormVisible = false
                this.loading = false
                //console.log(res)
                if (res.success) {
                  this.getdata(this.formInline)
                  this.$message({
                    type: 'success',
                    message: '数据保存成功！'
                  })
                } else {
                  this.$message({
                    type: 'info',
                    message: res.msg
                  })
                }
              })
              .catch(err => {
                this.editFormVisible = false
                this.loading = false
                this.$message.error('保存失败，请稍后再试！')
              })
          } else {
            updateLabelname(this.editForm)
              .then(res => {
                this.editFormVisible = false
                this.loading = false
                if (res.success) {
                  this.getdata(this.formInline)
                  this.$message({
                    type: 'success',
                    message: '数据保存成功！'
                  })
                } else {
                  this.$message({
                    type: 'info',
                    message: res.msg
                  })
                }
              })
              .catch(err => {
                this.editFormVisible = false
                this.loading = false
                this.$message.error('保存失败，请稍后再试！')
              })
          }
        }
      })
    }
  }
}
</script>


<style scoped>
.user-search {
  margin-top: 20px;
}

</style>
