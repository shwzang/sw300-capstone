<template>
  <v-row align="center" justify="center">
    <v-col cols="10">
      <v-btn text disable>나의 목표</v-btn>
      <v-btn :to="{ name: 'GoalCreate' }">목표 추가</v-btn>
    </v-col>
    <v-col cols="10" v-for="goal in goals" :key="goal.id">
      <v-card class="mx-auto">
        <v-card-title class="headline">{{ goal.name }}</v-card-title>
        <v-card-subtitle>{{ goal.description }} </v-card-subtitle>
        <v-card-actions>
          <v-btn @click="modifyGoal(goal.id)">수정</v-btn>
          <v-btn>삭제</v-btn>
        </v-card-actions>
      </v-card>
    </v-col>
  </v-row>
</template>

<script>
import { mapState } from "vuex";

export default {
  name: "Goal",

  computed: {
    goals() {
      return this.$store.state.goals;
    },
    ...mapState(["user"])
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
    modifyGoal(paramGoalId) {
      this.$store.dispatch("setModifyingId", paramGoalId);
      this.$router.push({ name: "GoalModify" });
    }
  }
};
</script>

<style></style>
