<template>
  <v-row align="center" justify="center">
    <v-col cols="5">
      <h1>나의 목표</h1>
    </v-col>
    <v-col cols="5" align="right">
      <v-btn color="success" :to="{ name: 'GoalCreate' }">목표 추가</v-btn>
    </v-col>
    <v-col cols="10" sm="5" v-for="goal in goalsInProgress" :key="goal.id">
      <v-card class="mx-auto">
        <v-card-title class="headline">{{ goal.name }}</v-card-title>
        <v-card-subtitle>{{ goal.description }} </v-card-subtitle>
        <v-card-text>
          {{ countDiaryGoals(diaries, goal.id) }}일 중
          {{ countCompletedDiaryGoals(diaries, goal.id) }}일 달성!!
        </v-card-text>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn color="primary" @click="modifyGoal(goal.id)">수정</v-btn>
          <v-btn color="error" @click="clickDelete(goal.id)">삭제</v-btn>
        </v-card-actions>
      </v-card>
    </v-col>

    <v-col cols="10">
      <v-dialog v-model="dialog" max-width="290">
        <v-card>
          <v-card-title class="headline">
            주의
          </v-card-title>
          <v-card-text>
            해당 목표를 정말로 삭제하시겠습니까?
          </v-card-text>
          <v-card-actions>
            <v-spacer> </v-spacer>
            <v-btn text @click="acceptDelete(clickedId)">
              삭제
            </v-btn>
            <v-btn text @click="cancelDelete">
              취소
            </v-btn>
          </v-card-actions>
        </v-card>
      </v-dialog>
    </v-col>
  </v-row>
</template>

<script>
import { mapState } from "vuex";

export default {
  name: "Goal",
  data() {
    return {
      dialog: false,
      clickedId: ""
    };
  },
  computed: {
    ...mapState(["user", "goals", "diaries"]),
    goalsInProgress() {
      let result = this.$store.state.goals.filter(
        goal => goal.inProgress == true
      );
      return result;
    }
  },
  created() {
    if (JSON.stringify(this.$store.state.user) == "{}") {
      this.$router.push({ name: "Login" });
    } else {
      this.$store
        .dispatch("fetchGoals", this.$store.state.user.id)
        .catch(() => {
          console.log("There was a problem getting Goals");
        });
    }
  },
  methods: {
    modifyGoal(goalId) {
      this.$store
        .dispatch("setModifyingId", goalId)
        .then(() => this.$router.push({ name: "GoalModify" }));
    },
    deleteGoal(goalId) {
      let goal = this.$store.state.goals.find(goal => goal.id == goalId);
      goal.inProgress = false;
      this.$store.dispatch("modifyGoal", { goalId: goalId, goal: goal });
    },
    clickDelete(goalId) {
      this.dialog = true;
      this.clickedId = goalId;
    },
    cancelDelete() {
      this.dialog = false;
      this.clickedId = "";
    },
    acceptDelete(goalId) {
      this.dialog = false;
      this.deleteGoal(goalId);
      this.clickedId = "";
    },
    countDiaryGoals(diaries, goalId) {
      let count = 0;
      for (let diary of diaries) {
        for (let diarygoal of diary.diaryGoals) {
          if (diarygoal.goal.id == goalId) {
            count++;
          }
        }
      }
      return count;
    },
    countCompletedDiaryGoals(diaries, goalId) {
      let count = 0;
      for (let diary of diaries) {
        for (let diarygoal of diary.diaryGoals) {
          if (diarygoal.goal.id == goalId && diarygoal.achieved) {
            count++;
          }
        }
      }
      return count;
    }
  }
};
</script>

<style></style>
