<template>
  <v-row align="center" justify="center" class="fill-height">
    <v-col cols="10">
      <v-card width="400" class="mx-auto mt-5">
        <v-card-title class="pb-0">
          <h1>Login</h1>
        </v-card-title>
        <v-card-text>
          <v-form>
            <v-text-field
              v-model="userId"
              label="ID"
              prepend-icon="mdi-account-circle"
            />
            <v-text-field
              v-model="password"
              :type="showPassword ? 'text' : 'password'"
              label="Password"
              prepend-icon="mdi-lock"
              :append-icon="showPassword ? 'mdi-eye' : 'mdi-eye-off'"
              @click:append="showPassword = !showPassword"
            />
          </v-form>
        </v-card-text>
        <v-divider></v-divider>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn color="info" @click="login">Login</v-btn>
          <v-btn color="success" :to="{ name: 'Register' }">Register</v-btn>
        </v-card-actions>
      </v-card>
    </v-col>

    <v-col cols="10">
      <v-dialog v-model="isLogined" max-width="290">
        <v-card>
          <v-card-title class="headline"> {{this.$store.state.user.name}}님 환영합니다! </v-card-title>

          <v-card-actions>
            <v-spacer> </v-spacer>

            <v-btn color="green darken-1" text @click="finishLogin">
              닫기
            </v-btn>
          </v-card-actions>
        </v-card>
      </v-dialog>
    </v-col>
  </v-row>
</template>

<script>
import { mapState } from "vuex";
export default {
  name: "LoginPage",
  data() {
    return {
      userId: "",
      password: "",
      showPassword: false,
      dialog: false
    };
  },
  computed: {
    isLogined() {
      return JSON.stringify(this.$store.state.user) != "{}";
    },
    ...mapState(["user"])
  },
  methods: {
    login() {
      this.$store
        .dispatch("logIn", { userId: this.userId, password: this.password })
        .then(response => {
          console.log(response)
          this.dialog = true;
        })
        .catch(() => {
          console.log("There was a problem logging in");
        });
    },
    finishLogin() {
      this.dialog = false;

      this.$router.push({ name: "Dashboard" });
    }
  }
};
</script>

<style></style>
