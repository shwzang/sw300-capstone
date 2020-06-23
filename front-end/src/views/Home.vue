<template>
  <div class="home">
    <log-in></log-in>
  </div>
</template>

<script>
// @ is an alias to /src

export default {
  name: "Home",

  components: {
    LogIn: () => import(/* webpackChunkName: "about" */ '../views/Login.vue')
  },

  data() {
    return {
      links: [
        { label: "Dashboard", url: "/dashboard" },
        { label: "Login", url: "/login" },
        { label: "Register", url: "/register" },
      ],
    };
  },
};
</script>
