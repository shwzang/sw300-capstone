<template>
  <v-row>
    <v-col cols="12" sm="6">My Diary</v-col>
    <v-col cols="12" sm="6">{{ user.name }}님 환영합니다</v-col>
    <v-col cols="12">
      <v-card min-height="300" class="mx-auto">
        <v-card-title class="headline">나의 목표</v-card-title>
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
          <v-list-item>
            <v-btn :to="{ name: 'Goal' }">목표 관리</v-btn>
          </v-list-item>
        </v-list>
      </v-card>
    </v-col>
    <v-col>
      <v-card>
        <v-card-title class="headline">나의 일기</v-card-title>
        오늘 일기를 작성하지 않았습니다
        <v-btn :to="{ name: 'Diary' }">일기 작성</v-btn>
        <v-btn :to="{ name: 'Diary' }">일기 관리</v-btn>
      </v-card>
    </v-col>
    <v-col cols="12">
      <v-card min-height="300">
        <v-card-title class="headline">월별 통계</v-card-title>
        <v-row>
          <v-col cols="12" sm="6">
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
    pickerDate: null
  }),
  watch: {
    pickerDate() {},
  },
  computed: {
    ...mapState(["user"]),
  },
};
</script>

<style></style>
