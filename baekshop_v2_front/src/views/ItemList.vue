<template>
  <div id="app">
    <b-table striped hover :items="items" :fields="fields">
      <template #cell(#)="row">
        <b-button size="sm" @click="update(row.item.id)" class="mr-2">
          edit
        </b-button>
        <b-button size="sm" @click="del(row.item.id)" class="mr-2">
          delete
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
    key: "name",
    label: "이름",
  },
  {
    key: "price",
    label: "가격",
  },
  {
    key: "stockQuantity",
    label: "재고",
  },
  {
    key: "author",
    label: "저자",
  },
  {
    key: "isbn",
    label: "ISBN",
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
    ...mapState(["items"]),
  },
  methods: {
    update(id) {
      console.log(id);
      this.$router.push("/item/list/" + id);
    },
    del(id) {
      console.log(id);
      this.$store.dispatch("DELETE_ITEM", id);
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
