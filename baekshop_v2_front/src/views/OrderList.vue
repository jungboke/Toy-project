<template>
  <div id="app">
    <b-table striped hover :items="orders" :fields="fields">
      <template #cell(#)="row">
        <b-button size="sm" @click="del(row.item.id)" class="mr-2">
          cancel
        </b-button>
      </template>
    </b-table>
    <b-button class="back" variant="dark" @click="back()">back</b-button>
  </div>
</template>

<script>
import { mapState } from "vuex";
import bus from "../utils/bus.js";

const fields = [
  {
    key: "orderDate",
    label: "일시",
  },
  {
    key: "orderStatus",
    label: "상태",
  },
  "#",
];

export default {
  data() {
    return {
      fields,
    };
  },
  created() {
    bus.$emit("off:progress");
  },
  computed: {
    ...mapState(["orders"]),
  },
  methods: {
    del(id) {
      console.log(id);
      this.$store.dispatch("DELETE_ORDER", id);
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
.back {
  display: block;
  margin: auto;
}
</style>
