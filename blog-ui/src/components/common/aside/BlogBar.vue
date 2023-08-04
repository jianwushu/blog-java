<!-- TODO 文章推荐侧边栏-->
<template>
  <div class="ui segment">
    <div class="ui tiny header">博客信息</div>
    <div class="ui middle aligned relaxed animated list">
      <div class="item">
        <i class="large newspaper outline middle aligned icon"/>
        <div class="content">
          <div class="header">文章数量</div>
          <div class="description">{{blogCount}}</div>
        </div>

      </div>
      <div class="item">
        <i class="large bell outline middle aligned icon"/>
        <div class="content">
          <div class="header">评论数量</div>
          <div class="description">{{commentCount}}</div>
        </div>
      </div>
      <div class="item">
        <i class="large history middle aligned icon"/>
        <div class="content">
          <div class="header">运行天数</div>
          <div class="description">{{getTime()}}</div>
        </div>
      </div>
      <div class="item">
        <i class="large birthday cake middle aligned icon"/>
        <div class="content">
          <div class="header">建站日期</div>
          <div class="description">{{options.set_up_date}}</div>
        </div>
      </div>

    </div>
  </div>
</template>

<script>
  import bus from "components/eventBus"
  import {getBlogCount} from "@/api/blog";
  import {getCommentCount} from "@/api/comment";
  export default {
    name: "BlogBar",
    data(){
      return{
        blogCount: '',
        commentCount: '',
        options:{}
      }
    },
    created() {
      this.getCount()
    },
    mounted() {
      let self = this
      bus.$on('getOptionData',(options)=>{
        self.options = options
      })
    },
    methods:{
      getTime(){
        let ms = new Date().getTime()-new Date(this.options.set_up_date)
        let year = parseInt(ms/(1000*60*60*24)/365)
        let day = parseInt(ms/(1000*60*60*24)%365)
        return year+"年"+day+"天"
      },
      getCount(){
        getBlogCount().then(res=>{
          this.blogCount = res.msg
        })
        getCommentCount().then(res=>{
          this.commentCount = res.msg
        })
      }

    }

  }
</script>

