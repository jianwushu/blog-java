<template>

  <div class="ui segment">
    <div class="ui tiny header">标签云</div>
    <div to="/" class="ui teal label " v-for="(item,index) in tags" :key="index">
      {{item.name}}
    </div>
  </div>

</template>

<script>
  import {getMetaList} from "@/api/meta"
  export default {
    name: "TagBar",
    data(){
      return{
        tags:[],
        query:{
          type: 'tag'
        }
      }
    },
    created() {
      this.getCategoryData(this.query)
    },
    methods:{
      // 获取标签数据
      getCategoryData(query){
        getMetaList(query).then(res=>{

          this.tags = res.rows
        })
      }
    }
  }
</script>

