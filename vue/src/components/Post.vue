<template>
  <div class="post-container-main home-page">
    <div v-if="this.$route.name == 'home'" class="post-header">
      <div v-if="this.$route.name == 'home'" id="post-details-profile-picture-div">
          <img
            id="post-details-profile-picture"
            :src="profile.profile_image"
            alt="`${profile.username}'s image`"
          />
        </div>
      <h3 v-if="this.$route.name == 'home'" class="username-post">
        <router-link
          class="username-for-post"
          v-bind:to="{ name: 'profile', params: { username: post.username } }"
          ><span class="username-for-post-text">{{
            post.username
          }}</span></router-link
        >
      </h3>
      <div class="top-right-options">
        <i
          v-if="
            !isFavorite &&
            $store.state.token != '' &&
            this.$route.name == 'home'
          "
          v-on:click="addFavorite(post)"
          class="fa-regular fa-square-plus"
        ></i>
        <i
          v-if="
            (isFavorite &&
              $store.state.token != '' &&
              this.$route.name == 'home') ||
            (isFavorite && this.$route.name == 'favorites')
          "
          v-on:click="removeFavorite(post)"
          class="fa-regular fa-square-minus"
        ></i>
        <b-dropdown
          variant="Outline Success"
          no-caret
          text="▼"
          v-if="
            post.username == this.$store.state.user.username &&
            this.$route.name == 'home'
          "
        >
          <b-dropdown-item v-on:click="changeCaptionStatus()"
            >Edit Caption</b-dropdown-item
          >
          <b-dropdown-item v-on:click="removePhoto()"
            >Remove photo</b-dropdown-item
          >
        </b-dropdown>
      </div>
    </div>
    <img
      id="post-img"
      :src="post.photo_url"
      alt="`${post.username}'s image`"
      data-toggle="modal"
      :data-target="`#postDetails${post.post_id}`"
    />
    <div
      class="modal fade"
      ref="postDetailsModal"
      :id="`postDetails${post.post_id}`"
      tabindex="-1"
      aria-labelledby="exampleModalLabel"
      aria-hidden="true"
      v-if="$store.state.token != ''"
    >
      <div class="modal-dialog modal-dialog-centered" style="max-width: 70%">
        <div class="modal-content" id="details-content">
          <div class="modal-body" id="details-body">
            <post-details :post="post" />
            <!-- <post-details :post="post" :listOfComments="listOfComments" /> -->
          </div>
        </div>
      </div>
    </div>
    <div class="post-info">
      <div class="reactions" v-if="this.$route.name == 'home'">
        <div class="likes-ratings">
          <div class="reactions-likes">
            <i
              id="likeIcon"
              v-if="!liked && $store.state.token != ''"
              v-on:click="addLike(post.post_id)"
              class="likeButton fa-regular fa-heart"
            ></i>
            <i
              class="likeButton fa-solid fa-heart"
              v-if="liked && $store.state.token != ''"
              v-on:click="removeLike(post.post_id)"
            ></i>
          </div>
          <div class="reactions-ratings">
            <ratings
              v-bind:ratingsForPost="ratingsForPost"
              v-bind:rateValue="rateValue"
              v-bind:postId="post.post_id"
            />
          </div>
        </div>

        <p id="likes">{{ post.likes }} likes</p>
      </div>

      <p id="caption" v-if="this.$route.name == 'home' && !isEdit">
        <span class="username-post">{{ post.username }}</span
        >&nbsp;{{ caption }}
      </p>
      <div
        v-else-if="
          this.$route.name == 'home' &&
          isEdit &&
          post.username == this.$store.state.user.username
        "
        id="caption2"
      >
        <p>
          <span class="username-post>">{{ post.username }}</span> &nbsp;
          <input
            class="edit-caption"
            v-model="newCaption"
            type="text"
            :placeholder="post.caption"
            v-on:keyup.enter="editCaption()"
          />
        </p>
        <button id="cancel" v-on:click="cancel()">Cancel</button>
      </div>

      <div
        class="comments"
        v-if="this.$route.name == 'home' && hasMoreThan1Comment"
      >
        <p v-for="comm in shortenedCommentsList()" v-bind:key="comm.id">
          <span id="commenter">{{ comm.commenter }}</span
          >&nbsp;{{ comm.comment }}
        </p>
      </div>
      <div
        class="comments"
        v-if="this.$route.name == 'home' && !hasMoreThan1Comment"
      >
        <p v-for="comm in commentsForEachPost" v-bind:key="comm.id">
          <span id="commenter">{{ comm.commenter }}</span
          >&nbsp;{{ comm.comment }}
        </p>
      </div>
      <div class="submit-new-comment">
        <input
          class="comment-input"
          v-if="$store.state.token != '' && this.$route.name == 'home'"
          type="text"
          v-model="filter"
          v-on:keyup.enter="createComment()"
          placeholder="Write a comment..."
        />
      </div>
    </div>
  </div>
</template>

<script>
import apiService from "../services/APIService.js";
import PostDetails from "../views/PostDetails.vue";
import Ratings from "./Ratings.vue";
import { BDropdownItem } from "bootstrap-vue";
export default {
  name: "Post",
  props: ["post", "isPhotoFeed"],
  components: {
    PostDetails,
    Ratings,
    BDropdownItem,
  },
  data() {
    return {
      profile: {},
      listOfComments: [],
      liked: false,
      filter: "",
      newComment: {
        post_id: this.post.post_id,
        commenter: this.$store.state.user.username,
        comment: "",
      },
      ratingsForPost: [],
      rateValue: null,
      isEdit: false,
      newCaption: "",
      caption: this.post.caption,
    };
  },
  methods: {
    addLike(id) {
      apiService.like(id).then((response) => {
        if (response.status == 200) {
          this.liked = true;
          this.$store.commit("ADD_LIKE", this.post);
        }
      });
    },
    removeLike(id) {
      apiService.unlike(id).then((response) => {
        if (response.status == 200) {
          this.liked = false;
          this.$store.commit("REMOVE_LIKE", this.post);
        }
      });
    },
    addFavorite(post) {
      const postToAdd = {
        username: this.$store.state.user.username,
        post_id: post.post_id,
      };
      this.$store.commit("ADD_FAV_PHOTO",post.photo_url)
     
      apiService.addFavoritePost(postToAdd).then((response) => {
        if (response.status == 200) {
          this.$store.commit("ADD_FAVORITE_POST", post, postToAdd);
        }
      });
    },
    removeFavorite(post) {
      apiService.deleteFavorite(post.post_id).then((response) => {
        if (response.status == 200) {
          this.$store.commit("REMOVE_FAV_PHOTO", post.photo_url)
          this.$store.commit("REMOVE_FAVORITE_POST", post);
        }
      });
    },
    createComment() {
      if (this.filter != "") {
        this.newComment.comment = this.filter;
        apiService.createNewComment(this.newComment).then((response) => {
          if (response.status == 201) {
            this.$store.commit("ADD_COMMENT", this.newComment);
            // this.listOfComments.push(this.newComment);
            this.filter = "";
            // this.$store.commit("SET_COMMENTS_FOR_POST", this.listOfComments);
            apiService.displayAllComments().then((response) => {
              this.$store.commit("SET_COMMENTS", response.data);
            });
          }
        });
      }
    },
    shortenedCommentsList() {
      const thisPostComments = this.$store.state.allComments.filter(
        (eachComment) => eachComment.post_id == this.post.post_id
      );
      return thisPostComments.slice(this.listOfComments.length - 2);
    },
    changeCaptionStatus() {
      this.isEdit = true;
    },
    editCaption() {
      if (this.newCaption != "") {
        const postToUpdate = {
          post_id: this.post.post_id,
          username: this.post.username,
          photo_url: this.post.photo_url,
          likes: this.post.likes,
          caption: this.newCaption,
          date_time: this.post.date_time,
        };
        apiService.editCaption(postToUpdate).then((response) => {
          if (response.status == 200) {
            this.caption = this.newCaption;
            this.newCaption = "";
            this.isEdit = false;
          }
        });
      }
    },
    removePhoto() {
      if (confirm("Do you want to remove photo?")) {
        apiService.removePost(this.post.post_id).then((response) => {
          if (response.status == 200) {
            if(this.$store.state.userPhotos.length!=0){
              this.$store.commit("REMOVE_PHOTO", this.photoUrl);
            }
            apiService.displayPosts().then((response) => {
              this.$store.commit("SET_ALL_POSTS", response.data);
            });
          }
        });
      }
    },
    cancel() {
      this.isEdit = false;
      this.newCaption = "";
    },
  },
  computed: {
    isFavorite() {
      return this.$store.state.favoriteIds.includes(this.post.post_id);
    },
    hasMoreThan1Comment() {
      return this.commentsForEachPost.length > 1;
    },
    commentsForEachPost() {
      return this.$store.state.allComments.filter(
        (eachComment) => eachComment.post_id == this.post.post_id
      );
    },
      photoUrl(){
        return this.post.photo_url
      }
  },

  created() {
    // apiService.displayCommentsByPost(this.post.post_id).then((response) => {
    //   this.listOfComments = response.data;
    // });
    apiService.getRatingsById(this.post.post_id).then((response) => {
      this.ratingsForPost = response.data;
    });
    apiService
      .getRatingByUser(this.post.post_id, this.$store.state.user.username)
      .then((response) => {
        this.rateValue = response.data;
      });
      apiService.displayProfile(this.post.username).then((response) => {
      if(response.status==200){
        this.profile= response.data;
      }
      
    });
  },
};
</script>

<style>
.post-info {
  padding-left: 1.5%;
  padding-right: 1.5%;
  padding-bottom: 1.5%;
}
.submit-new-comment {
  justify-content: flex-start;
  display: flex;
}

.post-container-main {
  display: flex;
  flex-direction: column;
  width: 80vh;
  text-align: center;
  background-color: rgba(255, 255, 255, 0.726);
  border-radius: 10px;
  object-fit: cover;
  object-position: center;
  box-shadow: 2px 4px 4px rgb(215, 186, 243);
  font-family: "Mukta", sans-serif;
  font-weight: bold;
}

.comment-input:focus {
  outline: none;
}
#details-content{
  margin:0;
  padding:0;
 
}
#details-body {
  margin: 0;
  padding: 0;
}
.modal.fade{
   padding:0;
   margin:0;
}

.comment-input {
  border-style: none;
  width: 100%;
  border-radius: 10px;
  background-color: rgba(255, 255, 255, 0.7);
}

#commenter,
.username-post,
#caption2 span {
  font-weight: bold;
  color: rgb(123, 12, 167);
}

#likes {
  color: red;
}

.comments,
#likes,
#caption,
#caption2,
.username-post {
  text-align: left;
}

#caption{
  text-align: left;
}

.username-for-post {
  text-decoration: none;
}

.username-for-post:hover {
  text-decoration: none;
  /* text-decoration-color: rgb(243, 170, 250); */
}

.username-for-post-text {
  text-decoration: none;
  color: rgb(123, 12, 167);
  margin-left:1%;
}

.reactions {
  display: flex;
  flex-direction: column;
  align-items: flex-start;
}

div.likes-ratings {
  display: flex;
  width: 100%;
  justify-content: flex-start;
  /* border-bottom: 1px solid rgba(0, 0, 0, 0.082);*/
}

.reactions-likes,
.reactions-ratings {
  flex: 1;
  display: flex;
  justify-content: flex-start;
  align-self: center;
}

.reactions-likes {
  height: 100%;
}

.reactions-likes i.likeButton {
  padding: 5px;
  font-size: 30px;
  color: red;
}

.reactions-ratings {
  justify-content: flex-end;
}

.fa-heart {
  color: red;
}

.likeButton:hover {
  cursor: pointer;
}

/* #post-img {
  border-radius: 10px;
} */

.post-header {
  display: flex;
  align-items: center;
  padding: 3px 8px;
}

.post-header i {
  flex: 1;
  display: flex;
  justify-content: flex-end;
  width: 100%;
}

.post-header h3.username-post {
  flex: 1;
}



.top-right-options {
  display: flex;
  align-items: center;
}

#caption2 {
  display: flex;
}

div#caption2 button {
  height: fit-content;
}

.edit-caption {
  border: none;
  border-bottom: 1px solid black;
}

.edit-caption:focus {
  outline: none;
  border-bottom: 1px solid black;
}

#cancel {
  border-radius: 15px;
  margin-left: 5px;
  background: rgba(119, 114, 199, 0.349);
}

.top-right-options button {
  padding-top: 8px;
}

.top-right-options i:hover {
  cursor: pointer;
}
</style>