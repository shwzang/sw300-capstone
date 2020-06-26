<template>
  <v-row align="stretch" justify="center">
    <v-col cols="10" sm="5"> <h1>Dashboard</h1> </v-col>
    <v-col cols="10" sm="5" align="right"> {{ user.name }}님 환영합니다 </v-col>
    <v-col cols="10" sm="4">
      <v-card min-height="300" class="mx-auto">
        <v-card-title class="headline">나의 목표</v-card-title>

        <v-divider class="mx-4"></v-divider>

        <v-card-text>
          <v-list v-if="goals.length > 0">
            <v-list-item v-for="goal in goals" :key="goal.id">
              <v-list-item-content>
                <v-list-item-title>{{ goal.name }}</v-list-item-title>
                <v-list-item-subtitle>
                  {{ goal.description }}
                </v-list-item-subtitle>
              </v-list-item-content>
            </v-list-item>
          </v-list>
        </v-card-text>

        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn text :to="{ name: 'Goal' }">목표 관리</v-btn>
        </v-card-actions>
      </v-card>
    </v-col>

    <v-col cols="10" sm="6">
      <v-card class="fill-height">
        <v-card-title class="headline">나의 일기</v-card-title>

        <v-divider class="mx-4"></v-divider>

        <v-card-text v-if="todayDiary != null">
          오늘 일기를 작성하였습니다. <br />
          {{ todayDiary.title }}
          <v-img src="@/assets/stamp.png" :aspect-ratio="12/10" contain max-height="150"> </v-img>
        </v-card-text>
        <v-card-text v-else>
          오늘 일기를 작성하지 않았습니다
        </v-card-text>

        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn v-if="todayDiary == null" text :to="{ name: 'DiaryCreate' }">
            일기 작성
          </v-btn>
          <v-btn text :to="{ name: 'Diary' }">일기 관리</v-btn>
        </v-card-actions>
      </v-card>
    </v-col>
    <monthly-statistics> </monthly-statistics>
  </v-row>
</template>

<script>
import { mapState } from "vuex";

export default {
  data: () => ({
    item: ""
  }),
  components: {
    MonthlyStatistics: () => import("@/components/MonthlyStatistics.vue")
  },
  computed: {
    todayDiary() {
      return this.$store.state.diaries.find(diary => {
        let today = new Date();
        let diaryDate = new Date(diary.date);
        let result = diaryDate.getFullYear() == today.getFullYear();
        result &= diaryDate.getMonth() == today.getMonth();
        result &= diaryDate.getDate() == today.getDate();
        return result;
      });
    },
    ...mapState(["user", "diaries", "goals"])
  },
  methods: {
    checkToday(date1, date2) {
      let result = date1.getFullYear() == date2.getFullYear();
      result &= date1.getMonth() == date2.getMonth();
      result &= date1.getDate() == date2.getDate();
      return result;
    }
  },
  created() {
    if (JSON.stringify(this.$store.state.user) != "{}") {
      this.$store.dispatch("fetchGoals", this.$store.state.user.id);
      this.$store.dispatch("fetchDiaries", this.$store.state.user.id);
    } else {
      this.$router.push({ name: "Home" });
    }
  }
};
</script>

<style></style>
