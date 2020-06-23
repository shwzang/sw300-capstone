import Vue from "vue";
import Vuex from "vuex";

Vue.use(Vuex);

export default new Vuex.Store({
  state: {
    user: {
      id: 1,
      userId: "shwzang",
      name: "심현우",
      diaries: [
        {
          id: 1,
          title: "6-23",
          date: "2020-06-23",
          content:
            "asdflkjqgqldkgjlkja lakjdf lkqj dlkj qlkjglqkjdglk qjsdlgk jqsdlkjgqlsdkgj",
          diaryGoals: [
            {
              id: 1,
              goal: {
                id: 1,
                name: "20분 운동하기",
                description: "건강을 위해서 하루 20분 씩 운동하기",
                inProgress: true,
              },
              achieved: true,
            },
            {
              id: 3,
              goal: {
                id: 2,
                name: "50분 독서하기",
                description: "하루 50분 씩 독서",
                inProgress: true,
              },
              achieved: false,
            },
          ],
        },
        {
          id: 2,
          title: "6-22",
          date: "2020-06-22",
          content:
            "asdflkjqgqldkgjlkja lakjdf lkqj dlkj qlkjglqkjdglk qjsdlgk jqsdlkjgqlsdkgj",
          diaryGoals: [
            {
              id: 1,
              goal: {
                id: 1,
                name: "20분 운동하기",
                description: "건강을 위해서 하루 20분 씩 운동하기",
                inProgress: true,
              },
              achieved: true,
            },
            {
              id: 3,
              goal: {
                id: 2,
                name: "50분 독서하기",
                description: "하루 50분 씩 독서",
                inProgress: true,
              },
              achieved: false,
            },
          ],
        },
      ],
      goals: [
        {
          id: 1,
          name: "20분 운동하기",
          description: "건강을 위해서 하루 20분 씩 운동하기",
          inProgress: true,
        },
        {
          id: 2,
          name: "50분 독서하기",
          description: "하루 50분 씩 독서",
          inProgress: true,
        },
      ],
    },
  },
  mutations: {},
  actions: {},
  modules: {},
});
