<template>
  <div class="home">
    <v-row align="center" justify="center" class="fill-height">
      <v-col cols="10">
        <v-img src="https://picsum.photos/800/600">
          <v-row align="center" class="lightbox white--text pa-2 fill-height">
            <v-col>
              <div align="center"><h1>My Diary</h1></div>
              <div align="center"><h2>hw.sim@naviworks.com</h2></div>
            </v-col>
          </v-row>
        </v-img>
      </v-col>
    </v-row>
  </div>
</template>

<script>
// @ is an alias to /src

export default {
  name: "Home",

  data() {
    return {
      links: [
        { label: "Home", url: "/" },
        { label: "Dashboard", url: "/dashboard" },
        { label: "Login", url: "/login" },
        { label: "Register", url: "/register" }
      ]
    };
  },
  created() {
    if (JSON.stringify(this.$store.state.user) != "{}") {
      this.$router.push({ name: "Dashboard" });
    }
  }
};
</script>