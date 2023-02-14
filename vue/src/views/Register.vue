<template>
  <div id="register" class="text-center">
    <div class="image">
      <img
        id="image-register"
        src="https://res.cloudinary.com/dkq5ktysl/image/upload/v1675876172/momentoApp_upqxds.jpg"
        alt="camera hanging from strap"
      />
    </div>
    <div class="register-container">
      <form class="form-register" @submit.prevent="register">
        <h1 class="h3 mb-3 font-weight-normal">Create Account</h1>
        <div class="alert alert-danger" role="alert" v-if="registrationErrors">
          {{ registrationErrorMsg }}
        </div>
        <label for="username" class="sr-only">Username</label>
        <input
          type="text"
          id="username"
          class="form-control"
          placeholder="Username"
          v-model="user.username"
          required
          autofocus
        />
        <label for="password" class="sr-only">Password</label>
        <input
          type="password"
          id="password"
          class="form-control"
          placeholder="Password"
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
        <router-link class="have-account" :to="{ name: 'login' }">Have an account?</router-link>
        <button class="sign-in btn btn-lg btn-primary btn-block" type="submit">
          Create Account
        </button>
      </form>
    </div>
  </div>
</template>

<script>
import authService from "../services/AuthService";

export default {
  name: "register",
  data() {
    return {
      user: {
        username: "",
        password: "",
        confirmPassword: "",
        role: "user",
      },
      registrationErrors: false,
      registrationErrorMsg: "There were problems registering this user.",
    };
  },
  methods: {
    register() {
      if (this.user.password != this.user.confirmPassword) {
        this.registrationErrors = true;
        this.registrationErrorMsg = "Password & Confirm Password do not match.";
      } else {
        authService
          .register(this.user)
          .then((response) => {
            if (response.status == 201) {
              this.$router.push({
                path: "/login",
                query: { registration: "success" },
              });
            }
          })
          .catch((error) => {
            const response = error.response;
            this.registrationErrors = true;
            if (response.status === 400) {
              this.registrationErrorMsg = "Bad Request: Validation Errors";
            }
          });
      }
    },
    clearErrors() {
      this.registrationErrors = false;
      this.registrationErrorMsg = "There were problems registering this user.";
    },
  },
};
</script>

<style>
#register.text-center {
  height: 100%;
}

.form-control,
.have-account {
  margin: 10px;
  padding: 3px;
  border-width: 1px;
  border-radius: 25px;
}

.register-container {
  background: white;
  height: fit-content;
  padding: 15px;
  border-radius: 10px;
  width: 18%;
  position: absolute;
  top: 250px;
  right: 300px;
}

.form-register {
  display: flex;
  flex-direction: column;
  align-items: center;
}

#image-register {
  width: 475px;
  padding: 50px;
  margin-top: 40px;
  margin-left: -50rem;
  margin-left: -600px
}
</style>
