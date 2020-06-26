<template>
  <v-row align="center" justify="center">
    <v-col cols="10">
      <v-card>
        <v-card-title> 일기 작성 </v-card-title>
        <v-card-subtitle align="right">
          {{ date.toLocaleString() }}
        </v-card-subtitle>
        <v-card-text>
          <v-text-field v-model="diary.title" outlined label="일기 제목" clearable>
          </v-text-field>
          <v-textarea v-model="diary.content" outlined label="일기 내용"> </v-textarea>
        </v-card-text>

        <v-divider> </v-divider>

        <v-card-title>하루 목표</v-card-title>
        <v-card-text>
          <v-checkbox
            v-for="(goal, index) in goalsInProgress"
            :key="goal.id"
            v-model="diaryGoals[index].achieved"
            :label="goal.name"
            hide-details
          >
          </v-checkbox>
        </v-card-text>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn color="primary" @click="summitDiary"> 작성 완료 </v-btn>
          <v-btn color="primary" :to="{ name: 'Diary' }"> 작성 취소 </v-btn>
        </v-card-actions>
      </v-card>
    </v-col>
  </v-row>
</template>

<script>
import { mapState } from "vuex";

export default {
  data() {
    return {
      diary: {
        title: "",
        content: "",
        diaryGoals:[]
      },

      date: new Date()
    };
  },
  computed: {
    diaryGoals() {
      let result = [];
      for (let userGoal of this.$store.state.goals) {
        let newDiaryGoal = { goal: userGoal, achieved: false };
        result.push(newDiaryGoal);
      }
      return result;
    },
    goalsInProgress() {
      let result = this.$store.state.goals.filter(goal => goal.inProgress == true);
      return result;
    },
    ...mapState(["user","goals"])
  },
  methods: {
    summitDiary() {
      if (
        this.diary.title.length > 0 &&
        this.diary.content.length > 0 &&
        this.$store.state.user.id != null
      ) {
        this.diary.diaryGoals = this.diaryGoals
        this.$store
          .dispatch("createDiary", {
            userId: this.$store.state.user.id,
            diary: this.diary
          })
          .then(() => {
            this.$router.push({ name: "Diary" });
          })
          .catch(() => {
            console.log("There was a problem creating your diary");
          });
      }
    }
  }
};
</script>

<style></style>
