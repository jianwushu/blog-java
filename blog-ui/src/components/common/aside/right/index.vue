<template>
  <div>
    <category-bar/>
    <tag-bar/>
    <blog-bar/>
  </div>
</template>

<script>
  import CategoryBar from "components/common/aside/CategoryBar";
  import TagBar from "components/common/aside/TagBar";
  import BlogBar from "components/common/aside/BlogBar";
  export default {
    name: "index",
    components:{
      CategoryBar,TagBar,BlogBar
    },
  }
</script>

<style scoped>

</style>
