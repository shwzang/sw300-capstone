<template>
  <v-row align="center" justify="center">
    <v-col cols="10">
      Diary
      <v-btn :to="{ name: 'DiaryCreate' }">일기 작성</v-btn>
    </v-col>
    <v-col cols="10" color="primary" v-for="diary in reversedDiaries" :key="diary.id">
      <v-card>
        <v-card-title class="headline" v-text="diary.title"> </v-card-title>
        <v-card-subtitle>{{ diary.date }} </v-card-subtitle>
        <v-card-text> {{ diary.content }} </v-card-text>

        <v-divider class="mx-4"></v-divider>

        <v-card-title>
          하루 목표
        </v-card-title>
        <v-card-text>
          {{ diary.diaryGoals.length }}개 목표 중
          {{ goalCompleted(diary.diaryGoals) }}개 달성!!
          <v-checkbox
            v-for="diaryGoal in diary.diaryGoals"
            :key="diaryGoal.id"
            v-model="diaryGoal.achieved"
            :label="diaryGoal.goal.name"
            readonly
            hide-details
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
    ...mapState(["user", "diaries"]),
    reversedDiaries() {
      const cloneObj = obj => JSON.parse(JSON.stringify(obj));
      const copied = cloneObj(this.diaries);
      return copied.reverse();
    }
  },
  methods: {
    goalCompleted(goals) {
      let completed = goals.filter(goal => goal.achieved == true);
      return completed.length;
    }
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
