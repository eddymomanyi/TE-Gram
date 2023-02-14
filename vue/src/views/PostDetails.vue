<template>
  <div class="post-details-main-view">
      <post-details :post="post" />
  </div>
</template>

<script>
import PostDetails from '../components/PostDetails.vue'
export default {
    components: {
        PostDetails
    },
    props: ["post"]
}
</script>

<style>

</style>