import axios from 'axios'

const apiClient = axios.create({
  baseURL: 'http://localhost:8080',
  withCredentials: false, // This is the default
  headers: {
    Accept: 'application/json',
    'Content-Type': 'application/json',
    'Access-Control-Allow-Origin':'*'
  },
})

export default {
  logIn (userId, password) {
    return apiClient.get("/users/" + userId + "/" + password)
  },
  checkDuplicated (userId) {
    return apiClient.get("/users/" + userId + "/duplicated")
  },
  postUser(user) {
    return apiClient.post("/users", user)
  },
  getDiaries(userId) {
    return apiClient.get("/diaries/" + userId)
  },
  getTodayDiary(userId) {
    return apiClient.get("/diaries/today/" + userId)
  },
  postDiary(userId, diary) {
    return apiClient.post("/diaries/" + userId, diary)
  },
  getGoals(userId) {
    return apiClient.get("/goals/" + userId)
  },
  postGoal(userId, goal) {
    return apiClient.post("/goals/" + userId, goal)
  },
  putGoal(goalId, goal) {
    return apiClient.put("/goals/" + goalId, goal)
  }
}
