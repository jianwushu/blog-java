<template>
  <div>

    <div class="ui segment">
      <div class="ui middle aligned two column grid">
        <div class="column">
          <div class="ui horizontal list">
            <a class="item">
              <div class="ui teal header">全站</div>
            </a>
          </div>
        </div>
        <div class="right aligned column">
          共<h2 class="ui orange header m-inline-block m-text-thin"> {{total}} </h2>篇博客
        </div>
      </div>
    </div>

    <!--文章卡片-->
    <article-list :blogs="blogs"></article-list>

    <!-- 分页-->
    <div class="ui center stackable aligned bottom attached segment" v-if="getPages>1">
      <div class="ui center middle aligned three column grid ">
        <div class="column m-padded-tiny">
          <button @click="previewPage" :disabled="query.pageNum===1" class="ui mini teal basic button">上一页
          </button>
        </div>
        <div class="column center aligned">
          <label class="ui teal label">{{query.pageNum}}/{{getPages}}</label>
        </div>
        <div class="right aligned column m-padded-tiny">
          <button @click="nextPage" :disabled="query.pageNum===getPages||getPages===0"
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
    name: "Index",
    components:{ArticleList},
    data() {
      return {
        blogs: {},
        total: 0,
        pages: 0,
        query:{
          pageNum: 1,
          pageSize: 2,
          status: 2,
          deleted: false
        }
      };
    },
    created() {
      this.getBlogsData(this.query)
    },
    methods:{
      getBlogsData(query){
        getList(query).then(res=>{
          this.blogs = res.rows
          this.total = res.total
        })
      },
      previewPage(){
        this.query.pageNum = this.query.pageNum-1
        this.getBlogsData(this.query)
      },
      nextPage(){
        this.query.pageNum = this.query.pageNum+1
        this.getBlogsData(this.query)
      }
    },
    computed:{
      getPages(){
        return Math.ceil(this.total/this.query.pageSize)
      }
    }
  }
</script>

