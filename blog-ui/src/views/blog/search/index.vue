<template>
  <div>
    <!--统计栏-->
    <div v-if="query.blogs.length>0">
      <div class="ui segment">
        <div class="ui middle aligned two column grid">
          <div class="column">
            <router-link to="/" class="ui teal ">全站</router-link>&nbsp; 为您查到关于&nbsp; <span class="ui teal header">{{query.key}}</span>&nbsp; 的相关结果
          </div>
          <div class="right aligned column">
            共<h2 class="ui orange header m-inline-block m-text-thin"> {{query.total}} </h2>篇数据
          </div>
        </div>
      </div>
      <!--文章卡片-->
      <div class="ui middle aligned selection list">
        <div class="item" v-for="(item,index) in query.blogs" :key="index">
          <router-link :to="'/article/'+item.id">
            <div class="header">{{item.title}}</div>
            <p v-html="item.text"></p>
          </router-link>
        </div>
      </div>
    </div>
    <div v-else>
      <div class="ui center aligned segment">
        未找到相关数据
      </div>
    </div>
    <!-- 分页-->
    <div class="ui center stackable aligned segment" v-if="getPages>1">
      <div class="ui center middle aligned three column grid " >
        <div class="column m-padded-tiny">
          <button @click="previewPage" :disabled="query.current===1" class="ui mini teal basic button">上一页
          </button>
        </div>
        <div class="column center aligned">
          <label class="ui teal label">{{query.current}}/{{getPages}}</label>
        </div>
        <div class="right aligned column m-padded-tiny">
          <button @click="nextPage" :disabled="query.current===getPages||getPages===0"
                  class="ui mini teal basic button">下一页
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
  import {search} from "@/api/lucene"
  export default {
    name: "index",
    data(){
      return{
        query:{}
      }
    },
    mounted() {
      this.search(this.$route.query)
    },
    methods:{
      search(query){
        search(query).then(res=>{
          this.query = res.data
        })
      },
      previewPage(){
        this.query.current = this.query.current-1
        this.getBlogsData(this.query)
      },
      nextPage(){
        this.query.current = this.query.current+1
        this.getBlogsData(this.query)
      }
    },
    computed:{
      getPages(){
        return Math.ceil(this.total/this.query.size)
      }
    },
    watch:{
      '$route' (to, from) {
        console.log(123)
        this.search(this.$route.query)
      }
    }

  }
</script>

<style scoped>

</style>
