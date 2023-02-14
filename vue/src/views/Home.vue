<template>
  <div class="home d-flex">
    <!-- <div class="title">
      <h1>Home</h1>
    </div> -->

    <div class="posts-container">
      <div class="post-container">
        <post
          class="each-post"
          v-for="post in this.$store.state.allPosts"
          v-bind:key="post.post_id"
          v-bind:post="post"
        />
      </div>
    </div>
    <div class="sort-container d-flex flex-column">
      <div class="sort-div">
        <button
          id="sort-btn"
          class="
            btn btn-lg btn-outline-secondary
            dropdown-toggle
            align-self-start
          "
          type="button"
          data-toggle="dropdown"
          aria-expanded="false"
        >
          <span>Sort By</span>
        </button>
        <div class="dropdown-menu">
          <a
            class="dropdown-item"
            type="button"
            v-on:click="orderByRating('desc')"
            >Highest Rating</a
          >
          <a
            class="dropdown-item"
            type="button"
            v-on:click="orderByRating('asc')"
            >Lowest Rating</a
          >
          <div role="separator" class="dropdown-divider"></div>
          <a
            class="dropdown-item"
            type="button"
            v-on:click="orderByTime('desc')"
            >Newest Posts</a
          >
          <a class="dropdown-item" type="button" v-on:click="orderByTime('asc')"
            >Oldest Posts</a
          >
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import apiService from "../services/APIService.js";
import post from "../components/Post.vue";

export default {
  name: "home",
  components: {
    post,
  },
  data() {
    return {
      listOfPosts: [],
      userCurrent: this.$store.state.user.username,
    };
  },
  created() {
    apiService.displayPosts().then((response) => {
      this.listOfPosts = response.data;
      this.$store.commit("SET_ALL_POSTS", response.data);
    });
    apiService
      .displayFavoritePosts(this.$store.state.user.username)
      .then((response) => {
        this.$store.commit("SET_FAVORITES", response.data);
      });
    apiService.displayFavoritePhotos(this.$store.state.user.username).then(response=>{
      if(response.status==200){
        this.$store.commit("SET_FAV_PHOTOS", response.data)
      }
    });  
    apiService
      .displayFavoriteIds(this.$store.state.user.username)
      .then((response) => {
        this.$store.commit("SET_FAV_IDS", response.data);
      });
  },
  methods: {
    orderByRating(order) {
      apiService.displayPostsByRating(order).then((response) => {
        if (response.status == 200) {
          this.listOfPosts = response.data;
          this.$store.commit("SET_ALL_POSTS", response.data);
        }
      });
    },
    orderByTime(order) {
      apiService.displayPostsByTime(order).then((response) => {
        if (response.status == 200) {
          this.listOfPosts = response.data;
          this.$store.commit("SET_ALL_POSTS", response.data);
        }
      });
    },
  },
};
</script>

<style>
.sort-container {
  width: 100%;
  align-items: center;
}
#sort-btn {
  margin-top: 20%;
  border: none;
  border-bottom: 1px solid rgb(189, 187, 187);
  color: white;
  background-image: linear-gradient(to right, #e2336b, #fcac46);
  width: 170px;
}

#sort-btn span {
  font-weight: bold;
}

#sort-btn:hover {
  background-color: rgba(171, 71, 188, 0.2);
  color: white;
}

#sort-btn:focus {
  background-color: rgba(171, 71, 188, 0.2);
  color: white;

  box-shadow: 0px 0px 2px 2px rgb(204, 204, 204);
}

.posts-container {
  margin-right: auto;
  margin-left: 22.5%;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-items: center;
  /* border:1px black solid; */
}

.dropdown-menu .dropdown-item {
  font-weight: bold;
}

.each-post {
  margin-top: 20px;
  margin-bottom: 40px;
  border-bottom: 1px solid rgb(223, 218, 218);
}
</style>
