<template>
  <div class="edit-main">
    <div class="edit-form d-flex flex-column">
      <div class="form-group">
        <label for="name-input">Name</label>
        <input
          v-model="newProfile.name"
          type="text"
          class="form-control"
          id="name-input"
          placeholder="Enter new display name"
        />
      </div>
      <div class="form-group">
        <label for="bio-input">Biography</label>
        <textarea
          v-model="newProfile.profile_bio"
          class="form-control"
          id="bio-input"
          rows="3"
        ></textarea>
      </div>
      <div class="form-group">
        <label for="email-input">Email Address</label>
        <input
          v-model="newProfile.email"
          type="email"
          class="form-control"
          id="email-input"
          placeholder="Enter new email"
        />
      </div>
      <div class="form-group">
        <label for="password" class="sr-only">New Password</label>
      <input
        type="password"
        id="password"
        class="form-control"
        placeholder="New Password"
        v-model="user.password"
        required
      />
      <input
        type="password"
        id="confirmPassword"
        class="form-control"
        placeholder="Confirm Password"
        v-model="user.confirmPassword"
        required
      />
      </div>
      <cloudinary-comp />
      <button type="submit" v-on:click="updateProfile" class="btn btn-primary">
        Submit
      </button>
    </div>
  </div>
</template>

<script>
import authService from '../services/AuthService';
import apiService from "../services/APIService.js";
import CloudinaryComp from "../components/CloudinaryComp.vue";
export default {
  data() {
    return {
      newProfile: {},
      user: {
        username: this.$route.params.username,
        password: '',
        confirmPassword: ''
      },

    };
  },
  computed: {
    passwordMatch() {
      return this.user.password === this.user.confirmPassword}
  },
  components: {
    CloudinaryComp,
  },

  created() {
    apiService.displayProfile(this.$route.params.username).then((response) => {
      this.newProfile = response.data;
    });
  },
  methods: {
    updateProfile() {
      if (this.$store.state.profileImageUrl != "") {
        this.newProfile.profile_image = this.$store.state.profileImageUrl;
      }
      if (!this.passwordMatch) {
        alert('Password & Confirm Password do not match.');
      } else {
      authService.updatePassword(this.user).then(response => {
        if(response.status!=200){
          alert("Issue updating password. Please try again")
        }
      })
      }
      apiService.updateProfile(this.newProfile).then((response) => {
        if (response.status == 200 && this.passwordMatch) {
          this.$router.push(`/profile/${this.$route.params.username}`);
        }
      });
      
    },
  },
};
</script>

<style>
.edit-main {
  width: 30%;
  margin-top: 30px;
  margin-left: auto;
  margin-right: auto;
}
</style>