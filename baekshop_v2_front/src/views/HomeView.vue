<template>
  <div id="app">
    <h1>BAEK SHOP</h1>
    <p>로그인 기능</p>
    <p>
      <b-button v-if="loginFlag" @click="login()" size="lg" variant="dark"
        >로그인</b-button
      >
      <b-button v-else @click="logout()" size="lg" variant="dark"
        >로그아웃</b-button
      >
    </p>
    <p>회원 기능</p>
    <p>
      <b-button @click="signup()" size="lg" variant="secondary"
        >회원 가입</b-button
      >
      <b-button @click="signup_list()" size="lg" variant="secondary"
        >회원 목록</b-button
      >
    </p>
    <p>상품 기능</p>
    <p>
      <b-button @click="item()" size="lg" variant="dark">상품 등록</b-button>
      <b-button @click="item_list()" size="lg" variant="dark"
        >상품 목록</b-button
      >
    </p>
    <p>주문 기능</p>
    <p>
      <b-button @click="order()" size="lg" variant="info">상품 주문</b-button>
      <b-button @click="order_list()" size="lg" variant="info"
        >주문 내역</b-button
      >
    </p>
  </div>
</template>

<script>
export default {
  data() {
    return {
      loginFlag: "",
    };
  },
  created() {
    if (localStorage.getItem("ACCESS_TOKEN") != null) {
      this.loginFlag = false;
    } else this.loginFlag = true;
  },
  methods: {
    login() {
      this.$router.push("/login");
    },
    logout() {
      localStorage.removeItem("ACCESS_TOKEN");
      this.loginFlag = true;
    },
    signup() {
      this.$router.push("/signup");
    },
    signup_list() {
      this.$router.push("/signup/list");
    },
    item() {
      this.$router.push("/item");
    },
    item_list() {
      this.$router.push("/item/list");
    },
    order() {
      this.$router.push("/order");
    },
    order_list() {
      this.$router.push("/order/list");
    },
  },
};
</script>

<style scoped>
p {
  font-size: 1.25rem;
}
h1 {
  font-size: 2.5rem;
  margin-bottom: 40px;
}
.btn {
  margin: 0 5px;
  opacity: 0.8;
}
#app {
  text-align: center;
  background-color: #e9ecef;
  padding: 64px 32px;
}
</style>
