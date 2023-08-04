<template>
  <div class="ui threaded comments" style="max-width: 100%">
    <h3 class="ui dividing header">{{title}}</h3>

    <div v-for="comment in comments" v-if="comment.parentComment===null"  class="comment">
      <a class="avatar">
        <img :src="comment.avatar">
      </a>
      <div class="content">
        <a class="author">{{comment.nickName}}</a>
        <div class="metadata">
          <span class="date">{{comment.createTime}}时</span>
        </div>
        <div class="text">
          <p>{{comment.text}}</p>
        </div>
        <div class="actions">
          <a @click="getPreId(comment.id,comment.nickName)" class="reply">回复</a>
        </div>
      </div>
      <div class="comments">
        <div v-for="reply in comment.replyComment"  class="comment">
          <a class="avatar">
            <img :src="comment.avatar">
          </a>
          <div class="content">
            <a class="author">{{reply.nickName}} <span style="color: #2bbbff" v-if="reply.parentComment!=null">@{{reply.parentComment.nickName}}</span></a>
            <div class="metadata">
              <span class="date">{{reply.createTime}}时</span>
            </div>
            <div class="text">{{reply.text}}</div>
            <div class="actions">
              <a @click="getPreId(reply.id,reply.nickName)" class="reply">回复</a>
            </div>
          </div>
        </div>
      </div>
    </div>

    <div class="ui form">
      <input v-model="comment.blogId" type="hidden" name="id">
      <input v-model="comment.parentId" type="hidden" name="parentId">
      <div class="field">
        <!--  <div class="ui">123</div>-->
        <textarea id="text_area" v-model="comment.text" name="content" placeholder="请输入评论信息..."></textarea>
      </div>
      <div class="fields">
        <div class="field m-mobile-wide m-margin-bottom-small">
          <div class="ui left icon input">
            <i class="user icon"></i>
            <input id="nickname" v-model="comment.nickName" required type="text" name="nickname" placeholder="姓名">
          </div>
        </div>
        <div class="field m-mobile-wide m-margin-bottom-small">
          <div class="ui left icon input">
            <i class="mail icon"></i>
            <input v-model="comment.email" type="text" name="email" placeholder="邮箱">
          </div>
        </div>
        <div class="field  m-margin-bottom-small m-mobile-wide">
          <button @click="postComment" class="ui teal button m-mobile-wide"><i class="edit icon"></i>发布</button>
        </div>
      </div>
      </div>


  </div>

</template>

<script>
  import {getList,addComment} from "@/api/comment";
  export default {
    name: "Comment",
    props: {
      blogId:{
        type: Number,
        default: 0
      },
      title:{
        type: String,
        default: '评论'
      }
    },
    data(){
      return{
        comments: [],
        query:{
          blogId: 0
        },
        comment:{
          nickName: "",
          email: "",
          text:"",
          parentId: null,
          blogId: this.blogId
        }
      }
    },
    created() {
      this.query.blogId = this.blogId
      this.getCommentsData(this.query)
    }
    ,methods:{
      getCommentsData(query){
        getList(query).then(res=>{
          this.comments = res.rows
        })
      },
      getPreId(id,name){
        this.comment.parentId = id
        $('#text_area').attr('placeholder','@'+name).focus()
      },
      postComment(){
        if(this.comment.nickName.replace(/(^\s*)|(\s*$)/g, '')===""){
          $('#nickname').val("").attr('placeholder','请输入昵称').focus()
          return
        }
        this.comment.avatar = "static/image/avatar/"+Math.round(Math.random()*8+1)+".gif"
        addComment(this.comment).then(res=>{
          this.getCommentsData(this.query)
          this.clearText()
        })
      },
      clearText(){
        this.comment.text = " "
        this.comment.parentId = null
      }
    }
  }
</script>

<style scoped>

</style>
