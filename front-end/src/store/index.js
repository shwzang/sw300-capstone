import Vue from "vue";
import Vuex from "vuex";
import DiaryService from '@/services/DiaryService.js'

Vue.use(Vuex);

export default new Vuex.Store({
  state: {
    user:{},
    diaries:[],
    goals:[],
    modifyingId:"",
  },
  mutations: {
    DO_NOTHING(state) {
      let user = state.user
      state.user = user
    },
    SET_USER (state, user) {
      state.user = user
    },
    SET_DIARIES (state, diaries) {
      state.diaries = diaries
    },
    ADD_DIARY (state, goal) {
      state.goals.push(goal)
    },
    SET_GOALS (state, goals) {
      state.goals = goals
    },
    ADD_GOAL (state, goal) {
      state.goals.push(goal)
    },
    MODIFY_GOAL(state, goalId, goal) {
      let index = state.goals.findIndex(goal => goal.id === goalId);
      state.goals.splice(index, 0, goal)
    },
    SET_MODIFYING_ID (state, modifyingId) {
      state.modifyingId = modifyingId
    },
  },
  actions: {
    logIn ({ commit }, {userId, password}) {
      return DiaryService.logIn(userId, password)
      .then(response => {
        commit('SET_USER', response.data)
      })
      .catch(error => {
        console.log('There was an error:', error.response)
        alert("입력하신 아이디 및 비밀번호가 일치하지 않습니다")
      })
    },
    createUser({ commit }, newUser ) {
      return DiaryService.postUser(newUser).then(() => {
        commit('SET_USER', {})
      })
    },


    fetchDiaries ({ commit }, userId) {
      DiaryService.getDiaries(userId)
      .then(response => {
        commit('SET_DIARIES', response.data)
      })
      .catch(error => {
        console.log('There was an error:', error.response)
      })
    },
    createDiary({ commit }, { userId, diary }) {
      return DiaryService.postDiary(userId, diary).then(() => {
        commit('ADD_DIARY', diary)
      })
    },

    fetchGoals ({ commit }, userId) {
      DiaryService.getGoals(userId)
      .then(response => {
        commit('SET_GOALS', response.data)
      })
      .catch(error => {
        console.log('There was an error:', error.response)
      })
    },
    createGoal({ commit }, { userId, goal }) {
      return DiaryService.postGoal(userId, goal).then(() => {
        commit('ADD_GOAL', goal)
      })
    },
    modifyGoal({commit}, {goalId, goal}) {
      return DiaryService.putGoal(goalId, goal).then(() => {
        commit('MODIFY_GOAL', goalId, goal)
      })
    },
    
    setModifyingId({commit}, modifyingId) {
      commit('SET_MODIFYING_ID', modifyingId)
    },
    logOut({ commit }) {
      commit('SET_USER', {})
      commit('SET_DIAIRES', [])
      commit('SET_GOALS',[])
    }
  },
  modules: {}
});
