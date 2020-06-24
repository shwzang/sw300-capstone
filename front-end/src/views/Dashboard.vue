<template>
  <v-row align="center" justify="center">
    <v-col cols="10" sm="5"> <h2>My Diary</h2> </v-col>
    <v-col cols="10" sm="5"> {{ user.name }}님 환영합니다 </v-col>
    <v-col cols="10">
      <v-card min-height="300" class="mx-auto">
        <v-card-title class="headline">나의 목표</v-card-title>
        <v-card-text>
          <v-list>
            <v-list-item-group v-model="item" color="primary">
              <v-list-item v-for="goal in user.goals" :key="goal.id">
                <v-list-item-content>
                  <v-list-item-title>{{ goal.name }}</v-list-item-title>
                  <v-list-item-subtitle>{{
                    goal.description
                  }}</v-list-item-subtitle>
                </v-list-item-content>
              </v-list-item>
            </v-list-item-group>
          </v-list>
        </v-card-text>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn text :to="{ name: 'Goal' }">목표 관리</v-btn>
        </v-card-actions>
      </v-card>
    </v-col>
    <v-col cols="10">
      <v-card>
        <v-card-title class="headline">나의 일기</v-card-title>
        <v-card-text>
          오늘 일기를 작성하지 않았습니다
        </v-card-text>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn text :to="{ name: 'DiaryCreate' }">일기 작성</v-btn>
          <v-btn text :to="{ name: 'Diary' }">일기 관리</v-btn>
        </v-card-actions>
      </v-card>
    </v-col>
    <v-col cols="10">
      <v-card min-height="300">
        <v-card-title class="headline">월별 통계</v-card-title>
        <v-row>
          <v-col cols="12" sm="6" align="center" justify="center">
            <v-date-picker
              ref="picker"
              v-model="date"
              :picker-date.sync="pickerDate"
            >
            </v-date-picker>
          </v-col>
          <v-col cols="12" sm="6">
            <div class="title">{{ pickerDate }} 통계</div>
            <div>
              일기 작성 일 : n
            </div>
            <div>
              목표 달성 일 : n
            </div>
          </v-col>
        </v-row>
      </v-card>
    </v-col>
  </v-row>
</template>

<script>
import { mapState } from "vuex";
export default {
  data: () => ({
    date: new Date().toISOString().substr(0, 10),
    pickerDate: null,
    item: {}
  }),
  watch: {
    pickerDate() {}
  },
  created() {
    this.$store.dispatch("fetchGoals", this.$store.state.user.id);
    this.$store.dispatch("fetchDiaries", this.$store.state.user.id);
  },
  computed: {
    ...mapState(["user", "diaries", "goals"])
  }
};
</script>

<style></style>
