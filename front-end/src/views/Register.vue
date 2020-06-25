<template>
  <v-row align="center" justify="center" class="fill-height">
    <v-col cols="10">
      <v-card>
        <v-card-title> <h1>Register</h1> </v-card-title>
        <v-card-text>
          <v-text-field
            v-model="user.userId"
            label="ID"
            :rules="userIdRule"
            clearable
          ></v-text-field>

          <v-text-field
            v-model="user.password"
            type="password"
            label="비밀번호"
            :rules="passwordRule"
            clearable
          ></v-text-field>

          <v-text-field
            v-model="passwordConfirm"
            type="password"
            label="비밀번호 재입력"
            :rules="passwordConfirmRule"
            clearable
          ></v-text-field>

          <v-text-field
            v-model="user.name"
            label="이름"
            :rules="nameRule"
            clearable
          ></v-text-field>
        </v-card-text>
        
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn color="success" @click="summitRegister"> 회원 가입 </v-btn>
          <v-btn color="info" :to="{ name: 'Login' }"> 취소 </v-btn>
        </v-card-actions>
      </v-card>

      <v-dialog v-model="dialog" max-width="290">
        <v-card>
          <v-card-title class="headline"> 환영합니다! </v-card-title>

          <v-card-text>
            회원가입 처리가 완료되었습니다.
          </v-card-text>

          <v-card-actions>
            <v-spacer> </v-spacer>

            <v-btn color="green darken-1" text @click="finishRegister">
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
  data() {
    return {
      user: {
        userId: "",
        password: "",
        name: ""
      },
      passwordConfirm: "",
      dialog: false,
      userIdRule: [
        value =>
          value.length == 0 ||
          (value && value.length >= 3) ||
          "최소 네 글자 이상 입력하세요"
      ],
      passwordRule: [
        value =>
          value.length == 0 ||
          (value && value.length >= 8) ||
          "최소 여덟 글자 이상 입력하세요"
      ],
      passwordConfirmRule: [
        value =>
          value.length == 0 ||
          this.passwordConfirm == this.user.password ||
          "입력한 비밀번호와 일치하지 않습니다"
      ],
      nameRule: [
        value =>
          value.length == 0 ||
          (value && value.length >= 2) ||
          "최소 두 글자 이상 입력하세요"
      ]
    };
  },
  computed: {
    ...mapState(["user"])
  },
  methods: {
    summitRegister() {
      if (
        this.user.userId.length >= 4 &&
        this.user.name.length >= 2 &&
        this.user.password.length >= 8 &&
        this.user.password == this.user.passwordConfirm
      ) {
        this.$store
          .dispatch("createUser", this.user)
          .then(() => {
            this.dialog = true;
          })
          .catch(() => {
            console.log("There was a problem creating user");
          });
      }
    },
    finishRegister() {
      this.dialog = false;
      this.$router.push({ name: "Login" });
    }
  }
};
</script>

<style></style>
