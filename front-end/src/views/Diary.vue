<template>
  <v-row>
    <v-col cols="12"> Diary</v-col>
    <v-col cols="12" v-model="item" color="primary">
      <v-card v-for="diary in user.diaries" :key="diary.id">
        <v-card-title class="headline" v-text="diary.title"> </v-card-title>
        <v-card-subtitle>{{ diary.date }} </v-card-subtitle>
        <v-card-text>{{ diary.content }}</v-card-text>

        <v-item-group>
          <v-item v-for="diaryGoal in diary.diaryGoals" :key="diaryGoal.id">
            <v-list-item-content>
              <v-checkbox
                v-model="diaryGoal.achieved"
                :label="diaryGoal.goal.name"
                
              ></v-checkbox>
            </v-list-item-content>
          </v-item>
        </v-item-group>
      </v-card>
    </v-col>
  </v-row>
</template>

<script>
import { mapState } from "vuex";

export default {
  name: "Diary",
  computed: mapState(["user"]),
};
</script>

<style></style>
