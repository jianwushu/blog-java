<template>
  <!--分类栏-->
  <div class="ui segment">
    <div class="ui tiny header">分类栏</div>
    <router-link :to="'/category/'+item.id" v-if="item.id != 2" class="ui teal label" v-for="(item,index) in categories" :key="index">
      {{item.name}}
      {{item.count}}
    </router-link>
  </div>

</template>

<script>
  import {getMetaList} from "@/api/meta"
  export default {
    name: "CategoryBar",
    data(){
      return{
        categories:[],
        query:{
          type: 'category'
        }
      }
    },
    created() {
      this.getCategoryData(this.query)
    },
    methods:{
      // 获取分类数据
      getCategoryData(query){
        getMetaList(query).then(res=>{
          this.categories = res.rows
        })
      }
    }
  }
</script>

