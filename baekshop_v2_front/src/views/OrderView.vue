<template>
  <div id="app">
    <b-form-group label="주문회원" label-for="members">
      <b-form-select
        id="members"
        v-model="form.member"
        :options="members"
        value-field="id"
        text-field="name"
        required
      ></b-form-select>
    </b-form-group>
    <b-form-group label="도서명" label-for="items">
      <b-form-select
        id="items"
        v-model="form.item"
        :options="items"
        value-field="id"
        text-field="name"
        required
      ></b-form-select>
    </b-form-group>
    <b-form-group label="수량" label-for="count">
      <b-form-input
        id="count"
        placeholder="Enter"
        v-model="count"
      ></b-form-input>
    </b-form-group>
    <b-button variant="dark" @click="addOrder()">Submit</b-button>
    <b-button @click="back()" variant="dark">취소</b-button>
  </div>
</template>

<script>
import { mapState } from "vuex";

export default {
  data() {
    return {
      form: {
        member: null,
        item: null,
      },
      count: "",
    };
  },
  created() {
    this.$store.dispatch("FETCH_MEMBERS");
    this.$store.dispatch("FETCH_ITEMS");
  },
  computed: {
    ...mapState(["members", "items"]),
  },
  methods: {
    addOrder() {
      let data = {
        memberId: this.form.member,
        itemId: this.form.item,
        count: this.count,
      };
      this.$store.dispatch("POST_ORDERS", data);
      this.clearInput();
    },
    clearInput() {
      this.form.member = null;
      this.form.item = null;
      this.count = "";
    },
    back() {
      this.$router.go(-1);
    },
  },
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
select {
  display: block;
  width: 100%;
  padding: 0.375rem 0.75rem;
  font-size: 1rem;
  font-weight: 400;
  line-height: 1.5;
  color: #212529;
  background-color: #fff;
  background-clip: padding-box;
  border: 1px solid #ced4da;
  border-radius: 0.25rem;
  transition: border-color 0.15s ease-in-out, box-shadow 0.15s ease-in-out;
}
</style>
