<template>
  <div class="main-profile">
    <div class="bio-header">
      <div id="profile-pic-div" class="profile-picture-div">
        <img
          id="profile-pic"
          class="profile-picture"
          :src="profile.profile_image"
        />
      </div>
      <div class="name-and-bio">
        <div class="username-and-edit">
          <h2 class="profile-username">{{ this.$route.params.username }}</h2>
          <router-link
            v-if="
              this.$store.state.user.username == this.$route.params.username
            "
            v-bind:to="{
              name: 'edit-profile',
              params: { username: this.$route.params.username },
            }"
            ><button type="button" id="editBtn" class="btn">
              Edit Profile
            </button>
          </router-link>
        </div>

        <h4 id="profile-name">{{ profile.name }}</h4>
        <p id="bio">{{ profile.profile_bio }}</p>
      </div>
    </div>
    <div class="profile-photos-container">
      <div
        class="profile-photos-divs"
        v-for="photo in listOfPhotos"
        v-bind:key="photo.id"
      >
        <img data-toggle="modal"
        :data-target="`#postDetails${getPost(photo).post_id}`" class="profile-posts" :src="photo" />
        <div
          class="modal fade"
          ref="postDetailsModal"
          :id="`postDetails${getPost(photo).post_id}`"
          tabindex="-1"
          aria-labelledby="exampleModalLabel"
          aria-hidden="true"
          v-if="$store.state.token != ''"
        >
          <div
            class="modal-dialog modal-dialog-centered"
            style="max-width: 70%"
          >
            <div class="modal-content" id="details-content">
              <div class="modal-body" id="details-body">
                <post-details :post="getPost(photo)" />
                <!-- <post-details :post="post" :listOfComments="listOfComments" /> -->
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import PostDetails from "../views/PostDetails.vue";
export default {
  props: ["profile", "listOfPosts", "listOfPhotos"],
  data(){
    return {
    }
  },
  components: {
     PostDetails,
  },
  methods: {
    getPost(url) {
      return this.listOfPosts.filter((post) => post.photo_url == url)[0];
    },
  },
};
</script>

<style>
#profile-name {
  /* font-weight: bold; */

  margin: 6% 0% 0% 0%;
}

#bio {
  margin: 0;
}

#profile-pic-div {
  display: flex;
  align-items: center;
  justify-content: center;
  border-radius: 50%;
  width: 185px;
  height: 185px;
  border: 3px rgb(240, 67, 119) solid;
}
#profile-pic {
  border-radius: 50%;
  object-fit: cover;
  object-position: center;

  width: 175px;
  height: 175px;
}
.username-and-edit {
  display: flex;
}
#editBtn {
  margin-left: 30px;
  background-image: linear-gradient(to right, #e2336b, #fcac46);
  width: 150px;
  font-weight: bold;
  color: white;
}

.main-profile {
  display: flex;
  flex-direction: column;
  margin-left: 0;
  margin-right: 0;
  margin-top: 40px;
  width:100%;
  padding: 0;
  font-family: "Mukta", sans-serif;
  font-weight: bold;
}

.bio-header {
  display: flex;
  font-family: "Mukta", sans-serif;
  align-self: center;
}
.profile-username {
  font-weight: bold;
}

.name-and-bio {
  display: flex;
  flex-direction: column;
  align-items: flex-start;
  margin-left: 70px;
  width: 200px;
}

.profile-photos-container {
  display: flex;
  flex-wrap: wrap;
  width:100%;
  margin-top: 50px;
  justify-content: flex-start;
}

.profile-photos-divs {
  width: 450px;
  margin-bottom: 1.66%;
  margin-left:2.5%;
  height: 100%;
}

.profile-posts {
  height: 300px;
  width: 100%;
  object-fit: cover;
}
</style>