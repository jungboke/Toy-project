<template>
  <div id="app">
    <b-table striped hover :items="members" :fields="fields">
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
    key: "city",
    label: "도시",
  },
  {
    key: "street",
    label: "주소",
  },
  {
    key: "zipcode",
    label: "우편번호",
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
    ...mapState(["members"]),
  },
  methods: {
    update(id) {
      console.log(id);
      this.$router.push("/signup/list/" + id);
    },
    del(id) {
      console.log(id);
      this.$store.dispatch("DELETE_MEMBER", id);
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
