<template>
  <div>
    <header-bar/>

    <div class="m-container m-padded-tb ">
      <div class="ui container">
        <div class="ui stackable grid">
          <!--左半部分-->
          <div class="eleven wide column">
<!--            <keep-alive>-->
              <router-view/>
<!--            </keep-alive>-->
          </div>
          <!--右半部分-->
          <div class="five wide column">
            <right-bar/>
          </div>
        </div>
      </div>
    </div>

    <footer-bar/>
  </div>
</template>

<script>
  import FooterBar from "components/common/footer/FooterBar";
  import HeaderBar from "components/common/header/HeaderBar";
  import RightBar from "components/common/aside/right";
  export default {
    name: "Layout",
    components: {
      FooterBar,HeaderBar,RightBar
    }
  }
</script>

<style scoped>

</style>
