<template>
  <div>
      <div class="ui segment">
        <div class="ui dividing header">订阅</div>
        <div class="ui horizontal list">
          <a class="item" @click="getRssData(index)" v-if="uris" v-for="(item,index) in uris" :key="index">
            <img class="ui avatar image" :src="item.icon">
            <div class="content">
              <div class="header">{{item.title}}</div>
              {{item.description}}
            </div>
          </a>
        </div>
      </div>
      <div class="ui segment">

        <div class="ui inverted dimmer" v-if="loading=='active'" :class="loading">
          <div class="ui text loader">加载中</div>
        </div>

        <div class="ui relaxed divided list">
          <div class="item" v-if="rss && rss.rssBeans" v-for="(item,index) in rss.rssBeans" :key="index">
            <i class="large book middle aligned icon"></i>
            <div class="content">
              <a :href="item.link" class="header">{{item.title}}</a>
              <div class="description">{{item.description}}</div>
            </div>
          </div>
        </div>
      </div>
  </div>
</template>

<script>
  import {parseRssUri} from "@/api/tool";
  import {getOptionsList} from "@/api/option";
  export default {
    name: "subscribe",
    data() {
      return {
        rss: {},
        loading: "active",
        uris:[],
        query: {
          type: 'rss',
        }
      }
    },
    created() {
      this.getOptionData()
    },
    methods: {
      getRssData(index) {
        this.loading = "active"
        parseRssUri({uri:this.uris[index].uri}).then(res => {
          this.rss = res.data
          this.loading = ""
        })
      },
      getOptionData(){
        getOptionsList(this.query).then(res=>{
          this.uris = JSON.parse(res.data.rss_uri)
          this.getRssData(0)
        })
      },
    }
  }
</script>

<style scoped>

</style>
