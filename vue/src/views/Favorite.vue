<template>
  <div class="main-favorites">
    <h3 class="favorite-title">Favorites</h3>
    <div class="favorite-posts-container">
      <div
        class="favorite-post"
        v-for="eachPhoto in this.$store.state.favoritePhotos"
        v-bind:key="eachPhoto.post_id"
      ><img id="favorite-image" :src="eachPhoto"/></div>
    </div>
  </div>
</template>

<script>
// import Post from "../components/Post.vue";
//import apiService from '../services/APIService'

export default {
  name: "favorite",
  components: {
    // Post,
  },
  data() {
    return {
      listOfIds: [],
    };
  },
  created() {},
};
</script>

<style>
h3.favorite-title {
  text-align: center;
  font-size: 3rem;
  font-weight: bolder;
  color: rgb(188, 141, 221);
  color: linear-gradient(
    -90deg,
    rgba(188, 141, 221, 1) 25%,
    rgba(138, 193, 255, 1) 50%,
    rgba(241, 134, 134, 1) 100%
  );
  margin: 10px;
}
div.main-favorites {
  display: flex;
  flex-direction: column;
  align-items: center;
}

div.favorite-posts-container {
  align-self: center;
  justify-content:flex-start;
  display: flex;
  margin-top:50px;
  width:100%;
  flex-wrap: wrap;
 
}

.favorite-post {
  width: 450px;
  margin-bottom: 1.66%;
  margin-left:2.5%;
  height: fit-content;
  padding:0;
}

#favorite-image{
  width:100%;
  height:300px;
  object-fit: cover;
  
}
</style>