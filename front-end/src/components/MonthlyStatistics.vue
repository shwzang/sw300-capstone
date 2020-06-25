<template>
  <v-col cols="10">
    <v-card min-height="300">
      <v-card-title class="headline">월별 통계</v-card-title>
      <v-row>
        <v-col cols="12" sm="8" align="center" justify="center">
          <v-date-picker
            ref="picker"
            v-model="date"
            :picker-date.sync="pickerDate"
            landscape
            full-width
            show-current
            :events="enableEvents ? functionEvents : null"
          >
          </v-date-picker>
        </v-col>
        <v-col cols="12" sm="4">
          <div class="title">{{ pickerDate }} 통계</div>
          <div>일기 작성 일 : {{ diaryDates(monthDiaries).length }}</div>
          <div>목표 달성 일 : {{ diaryDates(completedDiaries).length }}</div>
        </v-col>
      </v-row>
    </v-card>
  </v-col>
</template>

<script>
import { mapState } from "vuex";

export default {
  data: () => ({
    date: new Date().toISOString().substr(0, 10),
    pickerDate: null,
    enableEvents: true
  }),
  computed: {
    ...mapState(["diaries"]),
    functionEvents() {
      return this.dateFunctionEvents;
    },
    monthDiaries() {
      let result = this.diaries.filter(
        diary =>
          new Date(diary.date).getMonth() ==
          new Date(this.pickerDate).getMonth()
      );
      return result;
    },
    completedDiaries() {
      let result = this.monthDiaries.filter(diary => {
        for (let goal of diary.diaryGoals) {
          if (!goal.achieved) return false;
        }
        return true;
      });
      return result;
    },
    nonCompletedDiaries() {
      let result = this.monthDiaries.filter(diary => {
        for (let goal of diary.diaryGoals) {
          if (goal.achieved) return false;
        }
        return true;
      });
      return result;
    }
  },
  methods: {
    diaryDates(diaries) {
      return diaries.map(diary => new Date(diary.date).getDate());
    },
    dateFunctionEvents(date) {
      const [, , day] = date.split("-");
      if (this.diaryDates(this.completedDiaries).includes(parseInt(day, 10)))
        return "green";
      if (this.diaryDates(this.nonCompletedDiaries).includes(parseInt(day, 10)))
        return "red";
      if (this.diaryDates(this.monthDiaries).includes(parseInt(day, 10)))
        return true;
      return false;
    }
  },
  watch: {
    pickerDate() {}
  }
};
</script>

<style></style>
