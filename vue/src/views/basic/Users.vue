/**
* 基本管理 用户管理
*/
<template>
  <div>
    <!-- 面包屑导航 -->
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>用户管理</el-breadcrumb-item>
    </el-breadcrumb>
    <!-- 搜索筛选 -->
    <el-form :inline="true" :model="formInline" class="user-search">
      <el-form-item label="搜索：">
        <el-select size="small" v-model="formInline.usertype" placeholder="请选择类别" style="width:120px">
          <el-option label="类别：全部" :value="-1"></el-option>
          <el-option label="普通用户" :value="1"></el-option>
          <el-option label="管理员" :value="0"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="">
        <el-input size="small" v-model="formInline.name" placeholder="输入用户名" style="width:120px"></el-input>
      </el-form-item>
      <el-form-item label="">
        <el-input size="small" v-model="formInline.email" placeholder="输入邮箱" style="width:150px"></el-input>
      </el-form-item>
      <el-form-item label="">
        <el-select size="small" v-model="formInline.sex" placeholder="请选择性别" style="width:120px">
          <el-option label="性别：全部" :value="-1"></el-option>
          <el-option label="男" :value="1"></el-option>
          <el-option label="女" :value="0"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="">
        <el-input size="small" v-model="minAge" placeholder="输入年龄范围" style="width:110px"></el-input>
      </el-form-item>
      <el-form-item>~</el-form-item>
      <el-form-item label="">
        <el-input size="small" v-model="maxAge" placeholder="输入年龄范围" style="width:110px"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button size="small" type="primary" icon="el-icon-search" @click="search">搜索</el-button>
        <el-button size="small" type="primary" icon="el-icon-delete" @click="resetSearch()">重置</el-button>
        <el-button size="small" type="primary" icon="el-icon-plus" @click="handleEdit()">添加</el-button>
        <el-button size="small" type="danger" @click="batchDelete()">批量删除</el-button>
      </el-form-item>
    </el-form>
    <!--列表-->
    <el-table size="small" @selection-change="selectChange" :data="userData" highlight-current-row v-loading="loading"
              border element-loading-text="拼命加载中" style="width: 100%;" :default-sort ="{prop:'userid',order:'ascending'}">
      <el-table-column align="center" type="selection" width="50">
      </el-table-column>
      <el-table-column align="center" sortable prop="userid" label="UID" width="100">
      </el-table-column>
      <el-table-column align="center" sortable prop="name" label="用户名" width="120">
      </el-table-column>
      <el-table-column align="center" sortable prop="password" label="密码" width="120">
      </el-table-column>
      <el-table-column align="center" sortable prop="registertime" label="注册时间" width="120">
        <template slot-scope="scope">
          <div>{{ scope.row.registertime|timestampToTime }}</div>
        </template>
      </el-table-column>
      <el-table-column align="center" sortable prop="usertype" label="用户类别" width="120">
        <template slot-scope="scope">
          <div>{{ scope.row.usertype == 0 ? '管理员' : '普通用户' }}</div>
        </template>
      </el-table-column>
      <el-table-column align="center" sortable prop="sex" label="性别" min-width="50">
        <template slot-scope="scope">
          <div>{{ scope.row.sex == 0 ? '女' : '男' }}</div>
        </template>
      </el-table-column>
      <el-table-column align="center" sortable prop="age" label="年龄" min-width="70">
      </el-table-column>
      <el-table-column align="center" sortable prop="birth" label="生日" min-width="70">
        <template slot-scope="scope">
          <div>{{ scope.row.birth|timestampToTime }}</div>
        </template>
      </el-table-column>
      <el-table-column align="center" sortable prop="email" label="邮箱" min-width="120">
      </el-table-column>
      <el-table-column label="操作" min-width="120">
        <template slot-scope="scope">
          <el-button size="mini" @click="handleEdit(scope.$index, scope.row)">编辑</el-button>
          <el-button size="mini" type="danger" @click="deleteUser(scope.$index, scope.row)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <!-- 分页组件 -->
    <Pagination v-bind:child-msg="pageparm" @callFather="callFather"></Pagination>
    <!-- 编辑界面 -->
    <el-dialog :title="title" :visible.sync="editFormVisible" width="30%" @click='closeDialog("edit")'>
      <el-form label-width="80px" ref="editForm" :model="editForm" :rules="rules">
        <el-form-item label="用户名" prop="name">
          <el-input size="small" v-model="editForm.name" auto-complete="off" placeholder="请输入用户名"></el-input>
        </el-form-item>
        <el-form-item label="密码" prop="password">
          <el-input size="small" v-model="editForm.password" auto-complete="off" placeholder="请输入密码"></el-input>
        </el-form-item>
        <el-form-item label="用户类别" prop="usertype">
          <el-select size="small" v-model="editForm.usertype" placeholder="请选择" class="usertype">
            <el-option label="管理员" :value="0"></el-option>
            <el-option label="普通用户" :value="1"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="性别" prop="sex">
          <el-radio v-model="editForm.sex" :label=1>男</el-radio>
          <el-radio v-model="editForm.sex" :label=0>女</el-radio>
        </el-form-item>
        <el-form-item label="生日" prop="birth">
          <el-date-picker v-model="editForm.birth" type="date" placeholder="选择生日"></el-date-picker>
        </el-form-item>
        <el-form-item label="邮箱" prop="email">
          <el-input size="small" v-model="editForm.email" placeholder="请输入邮箱地址"></el-input>
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
  userList,
  userAdd,
  userEdit,
  userDelete, userBatchDelete
} from '../../api/userMG'
import Pagination from '../../components/Pagination'

export default {
  data() {
    return {
      loading: false, //是显示加载
      minAge: '',   //搜索的最小最大年龄
      maxAge: '',
      title: '添加用户',
      isAdd: false,   //是否为添加新用户
      editFormVisible: false, //控制编辑页面显示与隐藏
      // 编辑与添加
      editForm: {
        userid: '',
        name: '',
        password: '',
        registertime: '',
        usertype: '',
        sex: '',
        age: '',
        birth: '',
        email: ''
        //token: localStorage.getItem('logintoken')
      },
      // 选择数据和选择的id
      selectdata: [],
      ids: [],
      // rules表单验证
      rules: {
        name: [
          {required: true, message: '请输入用户名', trigger: 'blur'}
        ],
        password: [
          {required: true, message: '请输入密码', trigger: 'blur'}
        ],
        usertype: [{required: true, message: '请选择类别', trigger: 'blur'}],
        email: [
          {required: true, message: '请输入邮箱', trigger: 'blur'},
          {
            pattern: /^[A-Za-z0-9\u4e00-\u9fa5]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$/,
            required: true,
            message: '请输入正确的邮箱',
            trigger: 'blur'
          }
        ],
        sex: [{required: true, message: '请选择性别', trigger: 'blur'}],
        birth: [{required: true, message: '请选择生日', trigger: 'blur'}]
      },
      // 请求数据参数
      formInline: {
        page: 1,
        limit: 10,
        name: '',
        email: '',
        usertype: -1,
        sex: -1,
        min: 0,
        max: 2147483647
      },
      //用户数据
      userData: [],
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
      //获取用户列表
      userList(parameter).then(res => {
        this.loading = false
        if (res.success == false) {
          this.$message({
            type: 'info',
            message: res.msg
          })
        } else {
          //console.log(res.data.records)
          this.userData = res.data.records
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
      if (this.minAge === "") {
        this.formInline.min = 0
      } else {
        if (!r.test(this.minAge)) {
          this.$message.error('请输入正确的年龄范围')
          return
        }
        this.formInline.min = Number(this.minAge)
      }
      if (this.maxAge === "") {
        this.formInline.max = 2147483647
      } else {
        if (!r.test(this.maxAge)) {
          this.$message.error('请输入正确的年龄范围')
          return
        }
        this.formInline.max = Number(this.maxAge)
      }
      if (this.formInline.max - this.formInline.min < 0) {
        this.$message.error('请输入正确的年龄范围')
        return
      }
      //console.log(this.formInline)
      this.formInline.page = 1
      this.getdata(this.formInline)
    },
    //关闭编辑界面
    closeDialog() {
      this.editFormVisible = false
      this.editForm.userid = ''
      this.editForm.name = ''
      this.editForm.password = ''
      this.editForm.registertime = ''
      this.editForm.usertype = ''
      this.editForm.sex = ''
      this.editForm.age = ''
      this.editForm.birth = ''
      this.editForm.email = ''
    },
    //显示编辑界面
    handleEdit: function (index, row) {
      this.editFormVisible = true
      if (row != undefined && row != 'undefined') {
        this.isAdd = false;
        this.title = '修改用户'
        this.editForm.userid = row.userid
        this.editForm.name = row.name
        this.editForm.password = row.password
        this.editForm.registertime = new Date(row.registertime)
        this.editForm.usertype = row.usertype
        this.editForm.sex = row.sex
        this.editForm.age = row.age
        this.editForm.birth = new Date(row.birth)
        this.editForm.email = row.email
      } else {
        this.isAdd = true;
        this.title = '添加用户'
        this.editForm.userid = ''
        this.editForm.name = ''
        this.editForm.password = ''
        this.editForm.registertime = ''
        this.editForm.usertype = ''
        this.editForm.sex = ''
        this.editForm.age = ''
        this.editForm.birth = ''
        this.editForm.email = ''
      }
    },
    // 编辑、添加提交方法
    submitForm(editData) {
      this.$refs[editData].validate(valid => {
        if (valid) {
          // 请求方法
          //console.log(this.editForm)
          if (this.isAdd) {

            this.editForm.registertime = new Date()
            this.editForm.age = this.editForm.registertime.getFullYear() - this.editForm.birth.getFullYear()
            this.editForm.userid = 0

            userAdd(this.editForm)
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
            let now = new Date()
            this.editForm.age = now.getFullYear() - this.editForm.birth.getFullYear()
            userEdit(this.editForm)
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
        } else {
          return false
        }
      })
    },
    // 选择复选框事件
    selectChange(val) {
      this.selectdata = val
      this.ids = []
      this.selectdata.forEach(value => {
        this.ids.push(value.userid)
      })
    },
    // 删除用户
    deleteUser(index, row) {
      this.$confirm('确定要删除吗?', '信息', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      })
        .then(() => {
          // 删除
          //console.log(row.userid)
          userDelete(row.userid)
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
          //console.log(this.ids)
          userBatchDelete(this.ids)
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
      this.formInline.min = 0
      this.formInline.max = 2147483647
      this.formInline.usertype = -1
      this.formInline.sex = -1
      this.formInline.email = ''
      this.formInline.name = ''
      this.minAge = ''
      this.maxAge = ''

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

