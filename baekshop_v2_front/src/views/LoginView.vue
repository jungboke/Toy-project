<template>
  <div id="app">
    <b-form-group label="로그인 ID" label-for="loginId">
      <b-form-input
        id="loginId"
        placeholder="Enter"
        v-model="loginId"
      ></b-form-input>
    </b-form-group>
    <b-form-group label="비밀번호" label-for="password">
      <b-form-input
        id="password"
        placeholder="Enter"
        v-model="password"
      ></b-form-input>
    </b-form-group>
    <div class="row">
      <div class="col">
        <b-button variant="dark" @click="login()">로그인</b-button>
      </div>
      <div class="col">
        <b-button @click="back()" variant="dark">취소</b-button>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      loginId: "",
      password: "",
    };
  },
  methods: {
    login() {
      let login = {
        loginId: this.loginId,
        password: this.password,
      };
      this.$store.dispatch("LOGIN", login);
      this.clearInput();
    },
    back() {
      this.$router.go(-1);
    },
    clearInput() {
      this.loginId = "";
      this.password = "";
    },
  },
};
</script>

<style scoped>
#app {
  text-align: left;
  background-color: #e9ecef;
  padding: 32px 32px;
}
.btn {
  margin-top: 10px;
  margin-right: 10px;
  width: 100% !important;
}
.form-group {
  margin-bottom: 10px;
}
</style>
