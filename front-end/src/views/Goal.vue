<template>
  <v-row>
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
                <v-list-text>
                  <v-btn>목표 수정</v-btn>
                  <v-btn>목표 삭제</v-btn>
                </v-list-text>
              </v-list-item-content>
            </v-list-item>
          </v-list-item-group>
          <v-list-item>
            <v-btn>목표 추가</v-btn>
          </v-list-item>
        </v-list>
      </v-card>
    </v-col>
  </v-row>
</template>

<script>
import { mapState } from "vuex";

export default {
  name: "Goal",
  data() {
    return {};
  },
  computed: {
    ...mapState(["user"]),
  },
};
</script>

<style></style>
