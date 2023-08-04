<template>
  <div>
  <!--统计栏-->
    <div class="ui segment">
      <div class="ui middle aligned two column grid">
        <div class="column">
          <router-link to="/" class="ui teal ">全站</router-link>&nbsp; <span class="ui teal header">{{category}}</span>
        </div>
        <div class="right aligned column">
          共<h2 class="ui orange header m-inline-block m-text-thin"> {{total}} </h2>篇博客
        </div>
      </div>
    </div>
    <!--文章卡片-->
    <article-list :blogs="blogs"></article-list>
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
  import {getList} from "@/api/blog";
  import ArticleList from "components/common/ArticleList"
  export default {
    name: "index",
    components:{
      ArticleList
    },
    data() {
      return {
        category: '',
        blogs: {},
        total: 0,
        pages: 0,
        query: {
          current: 1,
          size: 7,
          categoryId: null,
          status: 1,
          deleted: false
        }
      };
    }
  }
</script>

<style scoped>

</style>
