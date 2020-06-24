<template>
  <v-row align="center" justify="center">
    <v-col cols="10">
      <v-card>
        <v-card-title>
          목표 작성
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
          <v-btn @click="summitGoal"> 작성 완료 </v-btn>
          <v-btn :to="{ name: 'Goal' }"> 작성 취소 </v-btn>
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
        description: ""
      }
    };
  },
  computed: {
    ...mapState(["user"])
  },
  methods: {
    summitGoal() {
      if (this.goal.name.length >= 3 && this.$store.state.user.id != null) {
        this.$store
          .dispatch("createGoal", {
            userId: this.$store.state.user.id,
            goal: this.goal
          })
          .then(() => {
            this.$router.push({ name: "Goal" });
          })
          .catch(() => {
            console.log("There was a problem creating your goal");
          });
      }
    }
  }
};
</script>

<style></style>
