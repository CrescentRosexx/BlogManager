/**
* 系统管理 评论管理
*/
<template>
  <div>
    <!-- 面包屑导航 -->
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>写博客</el-breadcrumb-item>
    </el-breadcrumb>
    <!--编辑-->
    <el-form label-width="80px" ref="article" :model="article" :rules="rules" style="margin-top: 30px">
      <el-form-item label="标题" prop="title">
        <el-input size="small" v-model="article.title" auto-complete="off" placeholder="请输入标题" style="width: 600px">
        </el-input>
      </el-form-item>
      <el-form-item label="正文" prop="content">
      <el-input type="textarea" :autosize="{ minRows: 15}" v-model="article.content" placeholder="请输入正文"
                style="width: 800px"></el-input>
      </el-form-item>
      <el-form-item label="标签" prop="labels">
          <el-tag :key="tag" v-for="tag in article.labels" closable :disable-transitions="false"
                  @close="handleClose(tag)">{{ tag }}
          </el-tag>
          <el-input class="input-new-tag" v-if="inputVisible" v-model="inputValue"
                    ref="saveTagInput" size="small" @keyup.enter.native="handleInputConfirm"
                    @blur="handleInputConfirm">
          </el-input>
          <el-button v-else class="button-new-tag" icon="el-icon-plus" size="small" @click="showInput">添加标签</el-button>
      </el-form-item>
    </el-form>
    <div style="text-align: center; width: 900px">
      <el-button type="primary" :loading="loading" class="title" @click="submitForm('article')" style="width: 100px " round>发布
      </el-button>
    </div>
  </div>
</template>

<script>
import {addArticle} from "../../api/basicMG";

export default {
  data() {
    return {
      article:{
        userid: 0,
        title: '',
        content: '',
        publishtime: '',
        audit: 1,
        labels:[]
      },
      inputVisible: false,
      inputValue: '',
      // rules表单验证
      rules: {
        title: [
          {required: true, message: '请输入标题', trigger: 'blur'}
        ],
        content: [
          {required: true, message: '请输入正文', trigger: 'blur'}
        ]
      },
    }
  },
  methods: {
    //标签管理
    handleClose(tag) {
      this.article.labels.splice(this.article.labels.indexOf(tag), 1);
    }, //标签管理
    showInput() {
      this.inputVisible = true;
      this.$nextTick(_ => {
        this.$refs.saveTagInput.$refs.input.focus();
      });
    }, //标签管理
    handleInputConfirm() {
      let inputValue = this.inputValue;
      if (inputValue) {
        this.article.labels.push(inputValue);
      }
      this.inputVisible = false;
      this.inputValue = '';
    },
    //提交文章
    submitForm(editData) {
      this.$refs[editData].validate(valid => {
        if (valid) {
          this.article.publishtime = new Date()
          addArticle(this.article)
            .then(res => {
              if (res.success) {
                this.article.title = ''
                this.article.content = ''
                this.article.labels = []
                this.article.publishtime = ''
                this.$message({
                  type: 'success',
                  message: '发布成功！'
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
              this.$message.error('发布失败，请稍后再试！')
            })
        }
      })
    }
  }
}
</script>

<style scoped>
.el-tag + .el-tag {
  margin-left: 10px;
}
.button-new-tag {
  margin-left: 10px;
  height: 32px;
  line-height: 30px;
  padding-top: 0;
  padding-bottom: 0;
}
.input-new-tag {
  width: 90px;
  margin-left: 10px;
  vertical-align: bottom;
}
</style>
