<template>
  <footer class="ui inverted vertical segment m-padded-tb-large">
    <div class="ui center aligned container">
      <div class="ui inverted divided stackable grid">
        <div class="wide column">
          <h4 class="ui inverted header m-text-thin m-text-spaced">个人介绍</h4>
          <p class="m-opacity-mini m-text-thin m-text-spaced">{{options.introduction}}</p>
          <div class="ui inverted horizontal list" style="padding: 0">
            <a class="item" v-for="item in links" :title="item.description" :href="item.linkUrl">{{item.linkName}}</a>
          </div>
        </div>
      </div>

      <div class="ui inverted divider"></div>
      <div class="licence m-opacity-mini m-text-thin m-text-spaced">
        Copyright © 2020
        <a href="#">{{options.title}}</a>
      </div>
    </div>
  </footer>
</template>

<script>
  import {getLinkList} from "@/api/link";
  import bus from "components/eventBus"
  export default {
    name: "FooterBar",
    data(){
      return {
        links:[],
        options: {}
      }
    },
    created() {
      this.getLinksData()
    },
    mounted() {
      let self = this
      bus.$on('getOptionData',(options)=>{
        self.options = options
      })
    },
    methods:{
      // 获取主页友链数据
      getLinksData(){
        getLinkList().then(res=>{
          this.links = res.rows
        })
      },
    }
  }
</script>

