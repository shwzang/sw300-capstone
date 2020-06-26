<template>
  <v-col cols="10">
    <v-card min-height="300">
      <v-row>
        <v-col cols="12" sm="8" align="center" justify="center">
          <v-card-text>
            <v-date-picker
              ref="picker"
              v-model="date"
              :picker-date.sync="pickerDate"
              full-width
              show-current
              :events="functionEvents"
            >
            </v-date-picker>
          </v-card-text>
        </v-col>
        
        <v-col cols="12" sm="4">
          <v-card-title>{{ pickerDate }} 통계</v-card-title>
          <v-card-text>
            <div>일기 작성 일 : {{ diaryDates(monthDiaries).length }}</div>
            <div>
              목표 100% 달성 일 : {{ diaryDates(completedDiaries).length }}
            </div>
          </v-card-text>

          <v-container v-if="dateDiary(selectedDate) != null">
            <v-divider></v-divider>
            <v-card-title>
              {{ selectedDate.toISOString().substr(0, 10) }} 일기
            </v-card-title>
            {{ dateDiary(selectedDate).title }}
            <v-card-text v-if="dateDiary(selectedDate).diaryGoals.length > 0">
              <br />
              {{ dateDiary(selectedDate).diaryGoals.length }} 개 목표 중
              {{ goalCompleted(dateDiary(selectedDate).diaryGoals) }} 개 달성!
            </v-card-text>
          </v-container>
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
    pickerDate: null
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
    },
    selectedDate() {
      return new Date(this.date);
    }
  },
  methods: {
    diaryDates(diaries) {
      return diaries.map(diary => new Date(diary.date).getDate());
    },
    dateFunctionEvents(date) {
      const [, , day] = date.split("-");
      if (this.diaryDates(this.nonCompletedDiaries).includes(parseInt(day, 10)))
        return "red";
      if (this.diaryDates(this.completedDiaries).includes(parseInt(day, 10)))
        return "green";
      if (this.diaryDates(this.monthDiaries).includes(parseInt(day, 10)))
        return true;
      return false;
    },
    dateDiary(date) {
      return this.$store.state.diaries.find(diary => {
        let diaryDate = new Date(diary.date);
        let result = diaryDate.getFullYear() == date.getFullYear();
        result &= diaryDate.getMonth() == date.getMonth();
        result &= diaryDate.getDate() == date.getDate();
        return result;
      });
    },
    goalCompleted(goals) {
      let completed = goals.filter(goal => goal.achieved == true);
      return completed.length;
    }
  }
};
</script>

<style></style>
