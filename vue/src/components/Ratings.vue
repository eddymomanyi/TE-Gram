<template>
  <div class="ratings-container">
      <p>Avg Rating: {{ avgRating }}</p>
    <b-form-rating v-model="rate" 
    variant="warning"
    v-on:change="addOrUpdate()"
    v-if="$store.state.token != ''"></b-form-rating>
  </div>
</template>

<script>
import { BFormRating } from "bootstrap-vue";
import apiService from "../services/APIService";

export default {
  name: "Ratings",
  components: {
    BFormRating,
  },
  data() {
    return {
      rate: 0,
      avgRating: null,
      ratingValue: this.rate,
    };
  },
  props: ["ratingsForPost", "rateValue", "postId"],
//   computed: {
//       avgRating() {
          
//       }
//   },
  methods: {
    addOrUpdate() {
      const rating = {
        post_id: this.postId,
        rating: this.rate,
        rater: this.$store.state.user.username,
      };
      if (this.ratingValue != -1 && this.rate > 0) {
        apiService.updateRating(rating).then((response) => {
          if (response.status == 200) {
            apiService
              .getRatingByUser(this.postId, this.$store.state.user.username)
              .then((response) => {
                this.ratingValue = response.data;
              });
               apiService.getAvgRating(this.postId).then((response) => {
            this.avgRating = response.data
        })
          }
        });
      } else if(this.rate > 0 && this.rateValue == -1) {
        apiService.addRating(rating).then((response) => {
          if (response.status == 200) {
            apiService
              .getRatingByUser(this.postId, this.$store.state.user.username)
              .then((response) => {
                this.ratingValue = response.data;
              });
               apiService.getAvgRating(this.postId).then((response) => {
            this.avgRating = response.data
        })
          }
        });
      }
    },
  },
  created() {
      apiService
              .getRatingByUser(this.postId, this.$store.state.user.username)
              .then((response) => {
                this.rate = response.data;
                this.ratingValue = response.data;
              });
        apiService.getAvgRating(this.postId).then((response) => {
            this.avgRating = response.data
        })
  }
};
</script>

<style>
.ratings-container {
    display: flex;
}

.ratings-container p{
    display: flex;
    white-space: nowrap;
    align-items: flex-end;
}
</style>