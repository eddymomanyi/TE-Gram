<template>
  <div id="app">
    <div id="nav">
      <div id="upper-nav">
        <i
          ><router-link class="link-text" v-bind:to="{ name: 'home' }">
            <img
              class="home-image"
              src="https://res.cloudinary.com/dzt9ane7n/image/upload/v1675996605/Momento_cbwk2o.jpg"
              alt="Hand-drawn camera polaroid" /></router-link
        ></i>
        <i>&nbsp;</i>
        <i class="nav-items fa-solid fa-house">
          <router-link class="link-text" v-bind:to="{ name: 'home' }">
            Home
          </router-link>
        </i>
        <i
          v-if="$store.state.token == ''"
          class="nav-items fa-solid fa-arrow-right-to-bracket"
          >&nbsp;&nbsp;<router-link
            class="link-text"
            v-bind:to="{ name: 'login' }"
            >Login</router-link
          ></i
        >
        <i v-if="$store.state.token != ''" class="nav-items fa-solid fa-heart"
          >&nbsp;&nbsp;<router-link
            class="link-text"
            v-bind:to="{ name: 'favorites' }"
            >Favorites</router-link
          >
        </i>
        <i v-if="$store.state.token != ''" class="nav-items fa-solid fa-user"
          >&nbsp;&nbsp;<router-link
            class="link-text"
            v-bind:to="{
              name: 'profile',
              params: { username: this.$store.state.user.username },
            }"
            >Profile</router-link
          >
        </i>
        <i
          v-if="$store.state.token != ''"
          class="nav-items fa-solid fa-arrow-right-to-bracket"
          >&nbsp;&nbsp;<router-link
            class="link-text"
            v-bind:to="{ name: 'logout' }"
            >Logout</router-link
          >
        </i>
        <i
          v-if="$store.state.token != ''"
          class="nav-items fa-regular fa-square-plus"
          data-toggle="modal"
          data-target="#createModal"
          >&nbsp;
          <span id="create-modal-link">Create</span>
        </i>
        <div class="modal fade" id="createModal" tabindex="-1">
          <div class="modal-dialog modal-dialog-centered">
            <div class="modal-content">
              <div class="modal-header">
                <h5 class="modal-title" id="exampleModalLabel">
                  Create a Post
                </h5>
                <button
                  type="button"
                  class="close"
                  data-dismiss="modal"
                  aria-label="Close"
                >
                  <span aria-hidden="true">&times;</span>
                </button>
              </div>
              <div class="modal-body">
                <create-post />
              </div>
              <div class="modal-footer"></div>
            </div>
          </div>
        </div>
      </div>
      <div id="footer">
        <div class="contact">
          <p class="contact-us">CONTACT US</p>
          <div class="contact-items">
            <a href="http://linkedin.com/in/chelsea-mauldin" target="blank"
              ><i class="fa-brands fa-linkedin"></i
            ></a>
            <a href="https://www.linkedin.com/in/edward-momanyi/" target="blank"
              ><i class="fa-brands fa-linkedin"></i
            ></a>
            <a href="https://www.linkedin.com/in/heewwoo/" target="blank"
              ><i class="fa-brands fa-linkedin"></i
            ></a>
            <a href="https://www.linkedin.com/in/vanessa-j-reed/" target="blank"
              ><i class="fa-brands fa-linkedin"></i
            ></a>
          </div>
        </div>
        <div id="cpr">
          <i class="fa-regular fa-copyright"></i>&nbsp;
          <span class="copyright">2023 Momento</span>
        </div>
      </div>
    </div>
    <div id="page d-flex flex-column">
      <div class="title">
        <h1 class="momento-title">Momento</h1>
        <div class="search-bar">
          <svg class="search-icon" aria-hidden="true" viewBox="0 0 24 24">
            <g>
              <path
                d="M21.53 20.47l-3.66-3.66C19.195 15.24 20 13.214 20 11c0-4.97-4.03-9-9-9s-9 4.03-9 9 4.03 9 9 9c2.215 0 4.24-.804 5.808-2.13l3.66 3.66c.147.146.34.22.53.22s.385-.073.53-.22c.295-.293.295-.767.002-1.06zM3.5 11c0-4.135 3.365-7.5 7.5-7.5s7.5 3.365 7.5 7.5-3.365 7.5-7.5 7.5-7.5-3.365-7.5-7.5z"
              ></path>
            </g>
          </svg>
          <input
            type="search"
            id="username-search"
            v-model="filter.username"
            placeholder="Search"
            v-on:keyup.enter="getSearchResult()"
          />
        </div>
      </div>
      <router-view id="pages-view" />
    </div>
  </div>
</template>
<script>
import createPost from "./views/CreatePost.vue";
import apiService from "./services/APIService";
export default {
  data() {
    return {
      profile: {},
      filter: {
        username: "",
      },
    };
  },
  methods: {
    getSearchResult() {
      apiService.displayProfile(this.filter.username).then((response) => {
        if (response.status == 200) {
          this.$store.commit("SET_PROFILE", response.data);
        }
      });

      apiService
        .displayPostsByUsername(this.filter.username)
        .then((response) => {
          this.$store.commit("SET_ALL_POSTS", response.data);
        });
      this.$router.push(`/profile/${this.filter.username}`);
      apiService
        .displayPostsByUsername(this.$route.params.username)
        .then((response) => {
          this.$store.commit("SET_ALL_POSTS", response.data);
        });

      apiService
        .displayPhotosByUser(this.$route.params.username)
        .then((response) => {
          if (response.status == 200) {
            this.$store.commit("SET_USER_PHOTOS", response.data);
          }
        });
    },
  },
  components: {
    createPost,
  },
  mounted() {
    apiService
      .displayFavoritePosts(this.$store.state.user.username)
      .then((response) => {
        this.$store.commit("SET_FAVORITES", response.data);
      });
    apiService
      .displayFavoriteIds(this.$store.state.user.username)
      .then((response) => {
        this.$store.commit("SET_FAV_IDS", response.data);
      });
    apiService.displayAllComments().then((response) => {
      this.$store.commit("SET_COMMENTS", response.data);
    });
  },
};
</script>

<style>
.home-text {
  text-decoration: none;
  font-family: "Mukta", sans-serif;
  font-size: 30px;
  color: black;
}

.home-image {
  margin-top: 0px;
  width: 175px;
  margin-bottom: 15px;
  padding-top: 0px;
}

.home-image:hover {
  box-shadow: 0 0 2px 1px rgba(0, 140, 186, 0.5);
}

.nav-items {
  padding-bottom: 15px;
  font-size: 30px;
  border-bottom: solid rgba(255, 255, 255, 0.459) 1px;
  margin-bottom: 50px;
  margin-right: 4%;
  margin-left: 4%;
}

.momento-title {
  margin-left: 5%;
  font-family: "Caveat", cursive;
  font-size: 65px;
}

#app {
  display: grid;
  grid-template-columns: 1fr 4fr;
  grid-template-areas: "nav link";
  height: 100vh;
  width: 100vw;
  background: rgb(238, 174, 202);
  background: linear-gradient(
    90deg,
    rgba(238, 174, 202, 0.753) 0%,
    rgba(202, 148, 233, 0.671) 100%
  );
  font-family: "Mukta", sans-serif;
  font-weight: bolder;
  overflow-y: hidden;
}

#upper-nav {
  display: flex;
  flex-direction: column;
  widows: 100%;
}

#footer .contact {
  display: flex;
  flex-direction: column;
  align-items: center;
  text-justify: center;
  height: 100%;
  width: 100%;
  font-family: "Mukta", sans-serif;
}

p.contact-us {
  align-items: center;
  font-size: 1.4rem;
  font-weight: bold;
  width: 100%;
}
.contact div.contact-items {
  display: flex;
  margin: 0px 5px;
}

.contact-items i {
  font-size: 30px;
  color: rgb(63, 54, 54);
  margin: 0 10px;
}

#footer .cpr {
  display: flex;
  flex-direction: row;
  height: 100%;
  font-family: "Mukta", sans-serif;
}

#pages-view {
  background: rgba(255, 255, 255, 0.562);
  margin-top: 0px;
  padding: 10px;
  padding-left: 0;
  padding-right: 0;
  overflow-y: scroll;
  height: 95vh;
}

#nav {
  padding-top: 40px;
  grid-area: nav;
  display: flex;
  flex-direction: column;
  text-align: center;
  border: rgba(255, 255, 255, 0.8) 1px solid;
  height: 100%;
  font-family: "Mukta", sans-serif;
}

#nav i .link-text {
  text-decoration: none;
  font-family: "Mukta", sans-serif;
  font-size: 30px;
  color: black;
  padding-bottom: 100px;
}

.search-bar {
  display: flex;
  line-height: 28px;
  align-items: center;
  position: relative;
  max-width: 190px;
}

.search-icon {
  position: absolute;
  left: 1rem;
  fill: rgba(73, 55, 55, 0.733);
  width: 1rem;
  height: 1rem;
}

#username-search {
  font-family: "Mukta", sans-serif;
  font-weight: bold;
  width: 100%;
  height: 35px;
  padding: 0 10px;
  padding-left: 40px;
  border-radius: 10px;
  background-color: rgba(255, 255, 255, 0.904);
  color: #0d0c22;
  margin-right: 10px;
  border: 2px solid rgba(202, 148, 233, 0.651);
  outline: none;
  transition: 0.25s ease;
}

#username-search::placeholder {
  color: rgba(73, 55, 55, 0.733);
}

#username-search:focus,
#username-search:hover {
  outline: none;
  border-color: rgba(86, 0, 247, 0.541);
  background-color: rgba(255, 255, 255, 0.904);
  box-shadow: 0 0 0 3px rgba(249, 239, 255, 0.849);
}

#page {
  grid-area: link;
  width: 100%;
  height: 100%;
}
#create-modal-link {
  font-weight: bold;
  font-family: "Mukta", sans-serif;
  font-size: 30px;
  color: black;
}
#create-modal-link:hover {
  cursor: pointer;
}

.title {
  /*  background-color: rgba(255, 51, 102, 0.2);*/
  height: 75px;
  width: 100%;
  display: flex;
  text-align: center;
  border-right: white 1px solid;
  border-top: white 1px solid;
  border-bottom: white 1px solid;
}

h1,
.search-bar {
  flex: 1;
}

.fa-solid.fa-heart {
  color: black;
}
</style>