<template>
  <nav id="headBar" class="ui attached segment m-padded-tb-mini m-shadow-small ">
    <div class="ui container">
      <div class="ui secondary stackable menu">
        <router-link to="/"><h2 class="ui teal header item m-title">{{options.title}}</h2></router-link>
        <router-link
                @click.native="navActive"
                v-for="(item,index) in nav" :to="item.link"
                :key="index"
                class="m-item item"
                :class="{'active':isActive(item.link),'m-mobile-hide':!showNav}">
          <i :class="item.icon"></i>{{item.title}}
        </router-link>
        <div class="right m-item item" :class="{'m-mobile-hide':!showNav}">
          <div class="ui icon input transparent">
            <input type="text" v-model="lucene.key" placeholder="Search ...">
            <i class="search link icon" @click="search"></i>
          </div>
        </div>

      </div>
    </div>
    <a class="ui icon button m-right-top m-mobile-show" style="background-color:#fff;" @click="menuClick">
      <i class=" large sidebar icon" ></i>
    </a>
    <div :class="{'m-mobile-hide':!showNav}">
      <div id="player"></div>
    </div>
  </nav>
</template>

<script>
  import bus from "components/eventBus"
  import {getOptionsList} from "@/api/option"
  import {parseQMusic} from "@/api/tool"

  import APlayer from "aplayer";
  export default {
    name: "HeaderBar",
    data(){
      return{
        options: {},
        curIndex: 0,
        showNav: false,
        musics: [],
        nav: [
          {title: "首页",link: '/',icon: "home icon"},
          {title: "留言板",link: '/comment',icon: "idea icon"},
          {title: "相册",link: '/album',icon: "tags icon"},
          {title: "归档",link: '/archive',icon: "clone icon"},
          {title: "Rss订阅",link: '/subscribe',icon: "rss icon"},
          {title: "关于我",link: '/about',icon: "info icon"},
        ],
        query: {
          type: 'index'
        },
        lucene: {
          current: 1,
          limit: 10,
          key: ""
        },
        ap: {}
      }
    },
    created() {
      this.getOptionData()
    },
    methods: {
      getOptionData(){
        getOptionsList(this.query).then(res=>{
          this.options = res.data
          this.nav = JSON.parse(res.data.header_nav)
          this.getMusicList(this.options.album_id)
          bus.$emit('getOptionData',res.data)
        })
      },
       getMusicList(id){
        let query = {id:id}
        let musics = []
        parseQMusic(query).then(res=>{
          for(let b of res.data){
            let music = {}
            music.name = b['title']
            music.artist = b['author']
            music.url = b['url']
            music.lrc = b['lyric']
            music.cover = b['cover']
            musics.push(music)
          }
          this.showData(musics)
        })
      },
      search(){
        this.$router.push({path: "/search",query:this.lucene})
      },
      async showData(musics){
        this.ap = await new APlayer({
          container: document.getElementById('player'),
          listFolded: true,
          listMaxHeight: "400px",
          lrcType: 1,
          fixed: true,
          audio: musics
        }).lrc.hide()
      },
      navActive(){
        this.showNav = !this.showNav
      },
      menuClick(){
        this.showNav = !this.showNav
        this.ap.lrc.hide()
      },
      isActive(link){
        return this.$route.path === link
      }
    }
  }

</script>

<style lang="scss">
  .aplayer.aplayer-fixed .aplayer-lrc{
    padding-top: 120px;
    top: 150px !important;
    left: 50px !important;
    text-align: left !important;
  }
  .aplayer .aplayer-lrc{
    height: 120px !important;
  }

</style>
