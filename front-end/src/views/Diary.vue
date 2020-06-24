<template>
  <v-row align="center" justify="center">
    <v-col cols="10">
      Diary
      <v-btn :to="{ name: 'DiaryCreate' }">일기 작성</v-btn>
    </v-col>
    <v-col cols="10" color="primary" v-for="diary in diaries" :key="diary.id">
      <v-card>
        <v-card-title class="headline" v-text="diary.title"> </v-card-title>
        <v-card-subtitle>{{ diary.date }} </v-card-subtitle>
        <v-card-text> {{ diary.content }} </v-card-text>

        <v-divider class="mx-4"></v-divider>

        <v-card-title>하루 목표</v-card-title>
        <v-card-text>
          <v-checkbox
            v-for="diaryGoal in diary.diaryGoals"
            :key="diaryGoal.id"
            v-model="diaryGoal.achieved"
            :label="diaryGoal.goal.name"
            readonly
          >
          </v-checkbox>
        </v-card-text>
      </v-card>
    </v-col>
  </v-row>
</template>

<script>
import { mapState } from "vuex";

export default {
  name: "Diary",

  computed: {
    diaries() {
      return this.$store.state.diaries;
    },
    ...mapState(["user"])
  },
  created() {
    if (JSON.stringify(this.$store.state.user) == "{}") {
      this.$router.push({ name: "Login" });
    } else {
      this.$store
        .dispatch("fetchDiaries", this.$store.state.user.id)
        .catch(() => {
          console.log("There was a problem getting Diaries");
        });
    }
  }
};
</script>

<style></style>
