<template>

  <div>
    <div class="ui segment" >
      <div class="ui threaded comments" >
        <h3 class="ui dividing header">归档栏</h3>
        <div v-for="(blog,index) in blogs" class="comment">
          <div class="ui circular label" :class="color[index]">
            {{blog.tag}}
          </div>
          <div class="comments" >
            <div class="ui divided selection list">
              <router-link :to="'/article/'+item.id" v-for="(item,index) in blog.data" :key="item.id" class="item">
                <div class="ui horizontal label" :class="color[index]">{{item.createTime.slice(8,10)}}</div>
                <span class="ui text">{{item.title}}</span>
              </router-link>
            </div>
          </div>
        </div>
        <div class="comment">
          <a class="ui blue circular label">
            开始
          </a>
        </div>
      </div>
    </div>
  </div>

</template>

<script>
  import {getArchive} from "@/api/blog";
  export default {
    name: "index",
    data(){
      return {
        color:['red','orange','yellow','olive','green','teal','blue','violet','purple','pink','brown','grey','black'],
        blogs:[]
      }
    },
    created() {
      this.getArchiveData()
    },
    methods:{
      //分组
      getArchiveData(){
        getArchive().then(res=>{
          this.blogs = this.formData(res.rows.reverse(),item=>{
            return item.createTime.slice(0,7)
          })
        })
      },
      formData(list,fn){
        const groups = [];
        const map = {};
        list.forEach(function (o) {
          let tag =fn(o)
          if(!map[tag]){
            groups.push({'tag':tag,data:[o]})
            map[tag]=tag
          }else{
            groups.some(item=>{
              if(item.tag===tag){
                item.data.push(o)
                return true
              }
            })
          }
        });
        return groups;
      }
      ,
      reverseData(data){
        return data.reverse()
      }
    }
  }
</script>

