<template>
  <div id="app">
    <b-form-group label="가입자 성명" label-for="username">
      <b-form-input
        id="username"
        placeholder="Enter"
        v-model="username"
      ></b-form-input>
    </b-form-group>
    <b-form-group label="아이디" label-for="loginId">
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
    <b-form-group label="거주지" label-for="city">
      <b-form-input id="city" placeholder="Enter" v-model="city"></b-form-input>
    </b-form-group>
    <b-form-group label="도로명" label-for="street">
      <b-form-input
        id="street"
        placeholder="Enter"
        v-model="street"
      ></b-form-input>
    </b-form-group>
    <b-form-group label="우편번호" label-for="zipcode">
      <b-form-input
        id="zipcode"
        placeholder="Enter"
        v-model="zipcode"
      ></b-form-input>
    </b-form-group>
    <b-button variant="dark" @click="addMember()">Submit</b-button>
    <b-button variant="dark" @click="back()">back</b-button>
  </div>
</template>

<script>
export default {
  data() {
    return {
      username: "",
      loginId: "",
      password: "",
      city: "",
      street: "",
      zipcode: "",
    };
  },
  methods: {
    addMember() {
      let member = {
        name: this.username,

        city: this.city,
        street: this.street,
        zipcode: this.zipcode,

        loginId: this.loginId,
        password: this.password,
      };
      this.$store.dispatch("POST_MEMBERS", member);
      this.clearInput();
    },
    clearInput() {
      this.username = "";
      this.loginId = "";
      this.password = "";
      this.city = "";
      this.street = "";
      this.zipcode = "";
    },
    back() {
      this.$router.go(-1);
    },
  },
  components: {},
};
</script>

<style scoped>
button {
  margin-right: 10px;
}
#app {
  text-align: left;
  background-color: #e9ecef;
  padding: 32px 32px;
}
.btn {
  margin-top: 10px;
}
.form-group {
  margin-bottom: 10px;
}
</style>
