<template>
  <div>
    <div class="ui segment" v-for="item in blogs">
      <router-link :to="'/article/'+item.id">
        <div class="ui mobile reversed stackable grid middle aligned">
          <div class="eleven wide column">
            <h3 class="ui center aligned header"><div v-if="item.status === 3" class="ui red horizontal label">置顶</div>{{item.title}}</h3>
            <div class="ui center aligned grid">
                <div class="wide column">
                  <div class="ui mini horizontal link list">
                    <div class="item">
                      <div class="content">
                        <router-link to="/about" class="header">
                          <i class="pencil alternate icon"></i>
                          {{item.sysUser.nickName}}
                        </router-link>
                      </div>
                    </div>
                    <div class="item">
                      <div class="content">
                        <i class="clock icon"></i>
                        <span>{{item.createTime}}</span>
                      </div>
                    </div>
                    <div class="item">
                      <div class="content">
                        <i class="eye icon"></i>&nbsp;
                        <span>{{item.views}}</span>
                      </div>
                    </div>
                    <div class="item">
                      <div class="content">
                        <i class="tag icon"></i>&nbsp;
                        <span>{{item.category.name}}</span>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            <div class="ui divider"></div>
            <div class="ui center aligned container">
              <p>{{item.text.slice(0,80)}}</p>
            </div>
          </div>
          <div class="five wide column">
            <img class="ui rounded image" :src="item.img" alt="">
          </div>
        </div>
      </router-link>
    </div>
  </div>
</template>

<script>
  export default {
    name: "index",
    props:{
      blogs: null
    }
  }
</script>

<style scoped>

</style>
