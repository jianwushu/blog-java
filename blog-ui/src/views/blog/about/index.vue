<template>
  <div class="ui center aligned">
<!--          <div class="ui segment">-->
<!--            <img :src="options.about_img" alt="" class="ui rounded image">-->
<!--          </div>-->

          <div class="ui segment">
            <div class="ui header center aligned">关于我</div>
          </div>
          <div class="ui center aligned  segment">
            <p class="m-text">{{options.about_text}}</p>
          </div>
<!--          <div class="ui attached segment">-->
<!--            <div class="ui orange basic label"-->
<!--                 v-for="(item,index) in options.about_hobbies" :key="index">-->
<!--              {{item}}-->
<!--            </div>-->
<!--          </div>-->
<!--          <div class="ui attached segment">-->
<!--            <div class="ui teal basic label m-margin-tb-tiny"-->
<!--                 v-for="(item,index) in options.about_skill" :key="index">-->
<!--              {{item}}-->
<!--            </div>-->
<!--          </div>-->

<!--          <div class="ui bottom attached segment center aligned">-->
<!--            <a :href="item.link" class="ui circular icon button" v-for="(item,index) in options.about_contact" :key="index">-->
<!--              <i :class="item.class"></i>-->
<!--            </a>-->
<!--          </div>-->



  </div>
</template>

<script>

  import {getOptionsList} from "@/api/option";
  export default {
    name: "About",
    data() {
      return {
        options: {},
        query: {
          type: 'about'
        }
      }
    },
    created() {
      this.getOptionData(this.query)
    },
    methods:{
      getOptionData(query){
        getOptionsList(query).then(res=>{

          this.options = res.data
          this.options.about_skill = (this.options.about_skill).split(",")
          this.options.about_hobbies = (this.options.about_hobbies).split(",")
          this.options.about_contact = JSON.parse(this.options.about_contact)
          console.log(this.options)
        })
      },

    }
  }

</script>

<style scoped>

</style>
