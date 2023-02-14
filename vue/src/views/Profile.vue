<template>
  <div class="main">
    <profile v-bind:profile="profile" v-bind:listOfPosts="listOfPosts" :listOfPhotos="listOfPhotos"/>
  </div>
</template>

<script>
import Profile from "../components/Profile.vue";
import apiService from "../services/APIService.js";
export default {
  components: {
    Profile,
  },
  data() {
    return {

    };
  },

  computed: {
    profile(){
      return this.$store.state.profile;
    },
    listOfPosts(){
      return this.$store.state.allPosts.filter(post => post.username==this.$route.params.username)
    },
    listOfPhotos(){
      return this.$store.state.userPhotos
    }
  },
 created() {
    apiService.displayProfile(this.$route.params.username).then((response) => {
      if(response.status==200){
        this.$store.commit("SET_PROFILE", response.data);
      }
      
    });
    apiService
      .displayPostsByUsername(this.$route.params.username)
      .then((response) => {
        this.$store.commit("SET_ALL_POSTS", response.data)
      });

    apiService.displayPhotosByUser(this.$route.params.username).then((response => {
      if(response.status==200){
        this.$store.commit("SET_USER_PHOTOS", response.data)
      }
    }))
  },
};
</script>

<style>
</style>