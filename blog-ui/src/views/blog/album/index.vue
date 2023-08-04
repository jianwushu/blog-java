<template>

  <div>
    <div class="ui segment ">
      <div class="ui dividing header">相册</div>
      <div class="ui three stackable cards">
        <div class="card " v-if="albums" v-for="(album,index) in albums" :key="index" @click="albumDetail(album.text,album.title)">
          <div class="blurring dimmable image" style="height: 100%">
            <div class="ui active dimmer">
              <div class="content">
                <h2 class="ui inverted icon header">
                  <span>{{album.title}}</span>
                </h2>
                <div class="ui center aligned">{{album.createTime.slice(0,10)}}</div>
              </div>
            </div>
            <img class="ui fluid image" :src="album.img" alt="" style="height: 100%">
          </div>
        </div>
      </div>
    </div>

    <div class="ui basic modal">
      <div class="ui center aligned header">{{title}}</div>
      <div class="outer">

        <div class="ui primary basic inverted button" :class="curIndex<=0?'disabled':''" @click="lm">
          <i class="chevron left icon "></i>
        </div>

        <div id="slide">
          <img class="ui centered big image" v-if="pics" v-for="(pic,index) in pics"
               :class="curIndex !=index?'hidden':''"
               :src="pic.src"
               :title="pic.title">
        </div>

        <div class="ui primary basic inverted button" :class="curIndex>=pics.length-1?'disabled':''" @click="rm">
          <i class="chevron right icon"></i>
        </div>

      </div>
      <div class="actions">
        <div class="ui red basic cancel inverted button right">
          <i class="remove icon"></i>
          关闭
      </div>
      </div>

    </div>
  </div>

</template>

<script>
  import {getList} from "@/api/blog";
  export default {
    name: "index",
    data() {
      return {
        albums: [],
        pics:[{}],
        total: 0,
        pages: 0,
        curIndex: 0,
        title: '',
        query:{
          current: 1,
          size: 8,
          categoryId: 2,
          status: 0
        }
      };
    },
    created(){
      this.getBlogsData(this.query)
    },
    methods: {
      getBlogsData(query) {
        getList(query).then(res => {
          this.albums = res.rows
          this.total = res.total
        })
      },
      albumDetail(text,title){
        this.title = title
        this.pics = []
        let reg1 = /\((.+?)\)/g
        let reg2 = /\[(.+?)\]/g
        let srcs = text.match(reg1)
        let titles = text.match(reg2)
        for(let x in titles){
          this.pics.push({'title':titles[x].substr(1,titles[x].length - 2),'src':srcs[x].substr(1,srcs[x].length - 2)})
        }
        $('.ui.basic.modal')
            .modal('show')
        ;
      },
      lm(){
        this.curIndex = this.curIndex-1
      },
      rm(){
        this.curIndex = this.curIndex+1
      }
    }
  }
</script>

<style scoped>
  .outer{
    display: flex;
    justify-content: space-around;
    align-items: center;
  }
</style>
