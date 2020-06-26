<template>
  <v-row align="center" justify="center">
    <v-col cols="10">
      <v-card>
        <v-card-title>
          목표 수정
        </v-card-title>
        <v-card-title>
          <v-text-field
            v-model="goal.name"
            label="하루 목표"
            :rules="rules"
            hide-details="auto"
            clearable
          >
          </v-text-field>
        </v-card-title>
        <v-card-text>
          <v-text-field
            v-model="goal.description"
            label="목표 상세 내용"
            clearable
          >
          </v-text-field>
        </v-card-text>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn color="primary" @click="modifyGoal"> 작성 완료 </v-btn>
          <v-btn color = "error" :to="{ name: 'Goal' }"> 작성 취소 </v-btn>
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
      rules: [
        value => !!value || "필수 사항입니다",
        value => (value && value.length >= 3) || "최소 세 글자 이상 입력하세요"
      ],
      goal: {
        name: "",
        description: "",
        inProgress:"",
        id:"",
      }
    };
  },
  computed: {
    ...mapState(["goals", "modifyingId"])
  },
  created() {
    let oldGoal = this.goals.find(goal => goal.id == this.modifyingId);
    for(let prop in oldGoal) {
      this.goal[prop] = oldGoal[prop];
    }
  },
  methods: {
    modifyGoal() {
      this.$store
        .dispatch("modifyGoal", { goalId: this.modifyingId, goal: this.goal })
        .then(() => {
          this.$router.push({ name: "Goal" });
          this.$store.dispatch("setModifyingId", "");
        })
        .catch(() => {
          console.log("There was a problem modifying your goal");
        });
    }
  }
};
</script>

<style></style>
