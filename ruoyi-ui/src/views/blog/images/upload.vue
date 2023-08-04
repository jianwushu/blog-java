<template>
  <div class="app-container">
    <el-header>
      图片上传
      <el-button align="right" style="margin-left: 10px;" size="small" type="success" @click="submitUpload">上传到服务器</el-button>
    </el-header>
    <el-upload
      align="center"
      ref="upload"
      class="upload-demo"
      :xs="12" :sm="6" :lg="4"
      drag
      action="/system/images"
      :http-request="uploadFile"
      multiple
      :on-preview="handlePreview"
      :on-success="(res,file)=>{uploadSucc(res,file)}"
      :on-remove="handleRemove"
      :file-list="fileList"
      list-type="picture"
      :auto-upload="false">
      <i class="el-icon-upload"></i>
      <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
      <div class="el-upload__tip" slot="tip">只能上传jpg/png文件，且不超过500kb</div>
    </el-upload>

  </div>
</template>

<script>
  import {addImages} from "@/api/blog/images"
  export default {
    name: "upload",
    data() {
      return {
        fileList: []
      };
    },
    methods: {
      uploadFile(e){
        let formData = new FormData();
        formData.append("file", e.file);
        addImages(formData).then(res=>{
          this.fileList.push({name:res.rows[0].name,url:'https://pic.ilvyu.cn'+res.rows[0].pathname})
        })
      },
      submitUpload() {
        this.fileList = []
        this.$refs.upload.submit();
      },
      uploadSucc(res,file){
        console.log(res)
        console.log(file)
      },
      handleRemove(file, fileList) {
        console.log(file, fileList);
      },
      handlePreview(file) {
        console.log(file);
      }
    }
  }
</script>

<style scoped>

</style>
