<template>
  <div class="app-container">
    <el-row :gutter="15">
      <el-form ref="elForm" :model="formData" :rules="rules" size="medium" label-width="100px"
               label-position="top">
        <el-col :span="24">
          <el-form-item label="首图" prop="img">
            <el-input v-model="formData.img" placeholder="请输入首图" clearable :style="{width: '100%'}">
            </el-input>
          </el-form-item>
        </el-col>
        <el-col :span="24">
          <el-form-item label="标题" prop="title">
            <el-input v-model="formData.title" placeholder="请输入标题" clearable :style="{width: '100%'}">
            </el-input>
          </el-form-item>
        </el-col>
        <el-col :span="24">
          <el-form-item label="文章内容" prop="text">
            <el-form-item id="vditor" />
            <!--            <el-input v-model="formData.field107" type="textarea" placeholder="请输入多行文本"-->
            <!--                      :autosize="{minRows: 4, maxRows: 4}" :style="{width: '100%'}"></el-input>-->
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="分类" prop="categoryId">
            <el-select v-model="formData.categoryId" placeholder="请选择分类" clearable :style="{width: '100%'}">
              <el-option v-for="(item, index) in categoryOptions" :key="index" :label="item.name"
                         :value="item.id"></el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="标签" prop="tag">
            <el-select v-model="test" multiple filterable allow-create default-first-option  placeholder="请选择标签" clearable :style="{width: '100%'}">
              <el-option v-for="(item, index) in tagOptions" :key="index" :label="item.name"
                         :value="item.id"></el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="24">
          <el-form-item label="功能选项" prop="setting">
            <el-checkbox v-model="formData.allowFeed" label="开启赞赏"
                         checked></el-checkbox>
            <el-checkbox v-model="formData.allowShare" label="转载声明"
                         checked></el-checkbox>
            <el-checkbox v-model="formData.allowComment" label="开启评论"
                         checked></el-checkbox>
          </el-form-item>
        </el-col>
        <el-col :span="24">
          <el-form-item size="large">
            <el-button type="">撤销</el-button>
            <el-button type="primary" @click="submitForm">更新</el-button>
          </el-form-item>
        </el-col>
      </el-form>
    </el-row>
  </div>
</template>
<script>
  import {getBlog,updBlog} from '@/api/blog/blog'
  import {listMetaOfCategory} from "@/api/blog/meta";
  import {addImages} from "@/api/blog/images"
  import Vditor from 'vditor'
  export default {
    name: "edit",
    props: [],
    data() {
      return {
        vditorContent: '',
        test:[],
        formData: {
          id: undefined,
          img: undefined,
          title: undefined,
          text: undefined,
          userId: undefined,
          categoryId: undefined,
          tags: undefined,
          allowFeed: undefined,
          allowComment: undefined,
          allowShare: undefined,
        },
        rules: {
          img: [{
            message: '请输入首图',
            trigger: 'blur'
          }],
          title: [{
            required: true,
            message: '请输入标题',
            trigger: 'blur'
          }],
          text: [{
            required: true,
            message: '请输入文章内容',
            trigger: 'blur'
          }],
          categoryId: [{
            required: true,
            message: '请选择分类',
            trigger: 'change'
          }]
        },
        categoryOptions: [],
        tagOptions: [],
        queryMeta:{
          type: "category",
          deleted: 0
        }
      }
    },
    watch: {},
    created() {
      this.getCategories()
      this.getList()
    },
    mounted() {},
    methods: {
      // 获取分类列表
      getCategories(){
        listMetaOfCategory(this.queryMeta).then(res=>{
          this.categoryOptions = res.rows
        })
      },
      uploadFile(e,callback){
        let formData = new FormData();
        formData.append("file", e[0]);
        addImages(formData).then(res=>{
          callback(res)
        })
      },
      //回显当前文章数据
      getList(){

        getBlog(this.$route.query[0]).then(res=>{
          this.formData = res.data
          this.tagOptions = res.data.tags
          console.log(this.tagOptions)
          this.test = this.tagOptions.map(item=>{return item.id})
          let that = this
          this.vditorContent = new Vditor('vditor',{
            height: 360,
            toolbarConfig: {
              pin: true,
            },
            counter: {
              enable: true,
            },
            cache: {
              enable: false,
            },
            resize: {
              enable: true
            },
            upload:{
              accept: "image/*",
              handler(files) {
                function callBack(res){
                  let title = res.rows[0].name
                  let url = "https://pic.ilvyu.cn"+res.rows[0].pathname
                  console.log(url)
                  document.execCommand("insertHTML", false, "!["+title+"]("+url+")")
                }
                that.uploadFile(files,callBack)
              }
            },
            after: () => {
              this.vditorContent.setValue(this.formData.text)
            },
          })
        })
      },
      submitForm() {
        this.formData.text = this.vditorContent.getValue()
        this.$refs['elForm'].validate(valid => {
          if (!valid) return
          // 提交表单
          if(this.formData.categoryId == 2){
            this.formData.status = 1
          }else{
            this.formData.status = 2
          }
          this.formData.tags = []
          this.test.forEach(item=>{
            if(typeof item == 'number'){
              this.formData.tags.push(this.tagOptions.find(tag=>{
                return tag.id === item
              }))
            }else{
              this.formData.tags.push({"name":item,"type":"tag"})
            }
          })
          updBlog(this.formData).then(res=>{
            this.$router.push({path:"/blog/index"})
          })
        })
      },
    }
  }

</script>
<style>
  @import "https://cdn.jsdelivr.net/npm/vditor/dist/index.css";
</style>

