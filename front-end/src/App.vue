<template>
  <v-app>
    <v-app-bar app color="primary" dark>
      <v-row col="12" justify="space-between">
        <v-col align="left">
          <v-btn
            v-for="link in links"
            :key="`${link.label}-header-link`"
            text
            rounded
            :to="link.url"
          >
            {{ link.label }}
          </v-btn>
        </v-col>
        <v-col align="right">
          <v-btn v-if="isLoggedin" text rounded @click="logOut">Log out</v-btn>
          <v-btn v-if="!isLoggedin" text rounded :to="{ name: 'Login' }"
            >Log in</v-btn
          >
          <v-btn v-if="!isLoggedin" text rounded :to="{ name: 'Register' }"
            >Register</v-btn
          >
        </v-col>
      </v-row>
    </v-app-bar>
    <v-main>
      <router-view></router-view>
    </v-main>
    <v-footer color="primary lighten-1" padless>
      <v-layout justify-center wrap>
        <span v-if="isLoggedin">
          <v-btn
            v-for="link in links"
            :key="`${link.label}-footer-link`"
            color="white"
            text
            rounded
            class="my-2"
            :to="link.url"
          >
            {{ link.label }}
          </v-btn>
        </span>

        <v-flex primary lighten-2 py-4 text-center white--text xs12>
          {{ new Date().getFullYear() }} —
          <strong>My Diary, hw.sim@naviworks.com</strong>
        </v-flex>
      </v-layout>
    </v-footer>
  </v-app>
</template>

<script>
import { mapState } from "vuex";

export default {
  name: "App",

  data() {
    return {
      links: [
        { label: "Home", url: "/" },
        { label: "Diary", url: "/diary" },
        { label: "Goal", url: "/goal" }
      ]
    };
  },
  computed: {
    ...mapState(["user"]),
    isLoggedin() {
      return JSON.stringify(this.$store.state.user) != "{}";
    }
  },
  methods: {
    logOut() {
      this.$store
        .dispatch("logOut")
        .then(() => {
          alert("정상적으로 로그아웃 되었습니다");
          this.$router.push({ name: "Home" });
        })
        .catch(() => {
          console.log("There was a problem logging out");
        });
    }
  }
};
</script>
