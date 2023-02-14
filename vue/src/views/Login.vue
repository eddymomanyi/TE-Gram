<template>
  <div class="main-login">
    <div class="image">
      <img
        id="image-login"
        src="https://res.cloudinary.com/dkq5ktysl/image/upload/v1675876172/momentoApp_upqxds.jpg"
        alt="camera hanging from strap"
      />
    </div>
    <div id="login" class="text-center">
      <form class="form-signin" @submit.prevent="login">
        <h1 class="h3 mb-3 font-weight-normal">Please Sign In</h1>
        <div class="alert alert-danger" role="alert" v-if="invalidCredentials">
          Invalid username and password!
        </div>
        <div
          class="alert alert-success"
          role="alert"
          v-if="this.$route.query.registration"
        >
          Thank you for registering, please sign in.
        </div>
        <!-- <label for="username" class="sr-only">Username</label> -->
        <input
          type="text"
          id="username"
          class="form-control"
          placeholder="Username"
          v-model="user.username"
          required
          autofocus
        />
        <!-- <label for="password" class="sr-only">Password</label> -->
        <input
          type="password"
          id="password"
          class="form-control"
          placeholder="Password"
          v-model="user.password"
          required
        />

        <router-link class="need-account" :to="{ name: 'register' }"
          >Need an account?</router-link
        >
        <button class="sign-in btn btn-lg btn-primary btn-block" type="submit">
          Sign in
        </button>
      </form>
    </div>
  </div>
</template>

<script>
import authService from "../services/AuthService";

export default {
  name: "login",
  components: {},
  data() {
    return {
      user: {
        username: "",
        password: "",
      },
      invalidCredentials: false,
    };
  },
  methods: {
    login() {
      authService
        .login(this.user)
        .then((response) => {
          if (response.status == 200) {
            this.$store.commit("SET_AUTH_TOKEN", response.data.token);
            this.$store.commit("SET_USER", response.data.user);
            this.$router.push("/");
          }
        })
        .catch((error) => {
          const response = error.response;

          if (response.status === 401) {
            this.invalidCredentials = true;
          }
        });
    },
  },
};
</script>

<style>
.main-login {
  height: 100%;
  display:flex;
  justify-content:flex-start;
  align-items: center;
}

#login.text-center {
  background: white;
  height: fit-content;
  padding: 15px;
  border-radius: 10px;
  width: 18%;
}

.form-signin {
  display: flex;
  flex-direction: column;
  align-items:center;
}
.form-control,
.need-account {
  margin: 10px;
  padding: 3px;
  border-width: 1px;
  border-radius: 25px;
}

.sign-in {
  background-image: linear-gradient(to right, #e2336b, #fcac46);
}

#login{
  margin-left:33%;
}

#image-login {
  width: 375px;
  margin-left:90%;
}
</style>
