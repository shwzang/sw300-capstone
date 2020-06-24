<template>
  <v-row align="center" justify="center" class="fill-height">
    <v-col>
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
      showPassword: false
    };
  },
  computed: {
    ...mapState(["user"])
  },
  methods: {
    login() {
      this.$store
        .dispatch("fetchUser", { userId: this.userId, password: this.password })
        .then(() => {
          this.$router.push({ name: "Dashboard" });
        })
        .catch(() => {
          console.log("There was a problem logging in");
        });
    }
  }
};
</script>

<style></style>
