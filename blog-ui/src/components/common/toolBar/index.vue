<template>
  <div>
    <div id="toolbar" class="m-padded m-fixed m-right-bottom" style="display: none">
      <div class="ui vertical icon buttons">
        <button type="button" class="ui toc teal button" >目录</button>
        <a href="#comment" class="ui teal button" >留言</a>
        <button class="ui wechat icon button"><i class="weixin icon"></i></button>
        <div id="toTop-button" class="ui icon button" ><i class="chevron up icon"></i></div>
      </div>
    </div>

    <div class="ui toc-container flowing popup transition hidden" style="height:250px;width: 250px!important;overflow-y: auto">
      <ol class="js-toc">

      </ol>
    </div>

    <div id="qrcode" class="ui wechat-qr flowing popup transition hidden "style="width: 130px !important;">
    </div>
  </div>
</template>

<script>
  export default {
    name: "ToolBar",
    mounted() {
      $('.toc.button').popup({
        popup : $('.toc-container.popup'),
        on : 'click',
        position: 'left center',
      });

      $('.wechat').popup({
        popup : $('.wechat-qr'),
        position: 'left center'
      });

      var qrcode = new QRCode("qrcode", {
        text: "http://jindo.dev.naver.com/collie",
        width: 110,
        height: 110,
        colorDark : "#000000",
        colorLight : "#ffffff",
        correctLevel : QRCode.CorrectLevel.H
      });

      $('#toTop-button').click(function () {
        $(window).scrollTo(0,500);
      })

      var waypoint = new Waypoint({
        element: document.getElementById('waypoint'),
        handler: function(direction) {
          if (direction == 'down') {
            $('#toolbar').show(100);
          } else {
            $('#toolbar').hide(500);
          }
        }
      })
    },
    methods:{
    }
  }

</script>

<style scoped>

</style>
