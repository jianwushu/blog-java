<template>
  <div>
    <div id="waypoint">
      <div class="ui segment">
        <img class="ui rounded image" :src="blog.img" alt="文章首图">
      </div>
      <div class="ui top attached segment ">
        <h1 class="ui center aligned header">{{blog.title}}</h1>
      </div>
      <div class="ui attached segment">
        <div id="md_haohao" class="entry-content l-h-2x vditor-reset  toc-content m-padded-tiny"></div>
        <!--赞赏-->
        <div v-show="blog.allowFeed" class="ui center aligned basic segment">
          <button id="payButton" class="ui orange basic circular button">赞赏</button>
        </div>
        <div class="ui payQR flowing popup transition hidden">
          <div class="ui orange basic label">
            <div class="ui images" style="font-size: inherit !important;">
              <div class="image">
                <img src="https://ilvyu.cn/images/head.jpg" alt="" class="ui rounded bordered image"
                     style="width: 120px">
                <div>支付宝</div>
              </div>
              <div class="image">
                <img src="https://ilvyu.cn/images/head.jpg" alt="" class="ui rounded bordered image"
                     style="width: 120px">
                <div>微信</div>
              </div>
            </div>
          </div>
        </div>
      </div>
      <!--博客信息-->
      <div class="ui bottom attached segment" v-show="blog.allowShare">
        <div class="ui middle aligned grid">
          <div class="twelve wide column">
            <div class="ui list">
              <div class="item">
                <i class="user icon"></i>
                <div class="content">
                  {{blog.sysUser.nickName}}
                </div>
              </div>
              <div class="item">
                <i class="calendar alternate icon"></i>
                <div class="content">
                  {{blog.createTime}}
                </div>
              </div>
              <div class="item">
                <i class="balance scale icon"></i>
                <div class="content">
                  自由转载-非商用-非衍生-保持署名（创意共享3.0许可证）
                </div>
              </div>
            </div>
          </div>
          <div class="four wide column">
            <img src="https://ilvyu.cn/images/head.jpg" alt="文章二维码" class="ui rounded image"
                 style="width: 10em">
          </div>
        </div>
      </div>
      <!-- 评论-->
      <div id="comment" class="ui segment" v-if="blog.allowComment">
        <comment :blogId="blog.id"/>
      </div>
    </div>
    <tool-bar/>
  </div>

</template>

<script>
  import Comment from "components/common/comment/Comment";
  import ToolBar from "components/common/toolBar";
  import VditorPreview from 'vditor/dist/method.min'
  import {getList,updView} from "@/api/blog";
  export default {
    name: "Article",
    components: {ToolBar, Comment},
    data() {
      return {
        blog: {
          sysUser:{
            nickName: ''
          }
        },
        query: {
          id: 0,
        }
      }
    },
    created() {
      this.query.id = this.$route.params.id
      this.getBlogData(this.query)
      updView(this.query).then(res=>{
      })
    },
    mounted() {
      $('#payButton')
          .popup({
            popup: $('.ui.payQR'),
            on: "click",
            position: 'bottom center',
            delay: {
              show: 300,
              hide: 800
            }
          });
    },
    methods:{
      getBlogData(query){
        getList(query).then(res=>{
          this.blog = res.rows[0]
          this.showData()
        })

      }, async showData() {
        const previewElement = document.getElementById('md_haohao')
        await VditorPreview.preview(
            previewElement,
            this.blog.text,
            {
              hljs: {
                style: 'solarized-light',
                lineNumber: true
              },
              speech: {  // 对选中后的内容进行阅读
                enable: true,
              },
              after() {
                tocbot.init({
                  tocSelector: '.js-toc',
                  contentSelector: '#md_haohao',
                  headingSelector: 'h1, h2, h3,h4',
                  scrollSmooth: true,
                });
              },
            }
        )
      }
    }
  }

</script>

