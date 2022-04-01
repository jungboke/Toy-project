<template>
  <div id="app">
    <b-form-group label="도서명" label-for="name">
      <b-form-input
        id="name"
        placeholder="Enter"
        v-model="itemName"
      ></b-form-input>
    </b-form-group>
    <b-form-group label="가격" label-for="price">
      <b-form-input
        id="price"
        placeholder="Enter"
        v-model="price"
      ></b-form-input>
    </b-form-group>
    <b-form-group label="재고수량" label-for="stockQuantity">
      <b-form-input
        id="stockQuantity"
        placeholder="Enter"
        v-model="stockQuantity"
      ></b-form-input>
    </b-form-group>
    <b-form-group label="저자" label-for="author">
      <b-form-input
        id="author"
        placeholder="Enter"
        v-model="author"
      ></b-form-input>
    </b-form-group>
    <b-form-group label="ISBN" label-for="isbn">
      <b-form-input id="isbn" placeholder="Enter" v-model="isbn"></b-form-input>
    </b-form-group>
    <b-button variant="dark" @click="updateItem()">Edit</b-button>
  </div>
</template>

<script>
import bus from "../utils/bus";
export default {
  data() {
    return {
      id: "",
      itemName: "",
      price: "",
      stockQuantity: "",
      author: "",
      isbn: "",
    };
  },
  created() {
    bus.$emit("off:progress");
    this.id = this.$route.params.id;
    this.itemName = this.$store.state.items.name;
    this.price = this.$store.state.items.price;
    this.stockQuantity = this.$store.state.items.stockQuantity;
    this.author = this.$store.state.items.author;
    this.isbn = this.$store.state.items.isbn;
  },
  methods: {
    updateItem() {
      let item = {
        id: this.id,
        name: this.itemName,
        price: this.price,
        stockQuantity: this.stockQuantity,
        author: this.author,
        isbn: this.isbn,
      };
      this.$store.dispatch("UPDATE_ITEM", item);
      this.clearInput();
    },
    clearInput() {
      this.itemName = "";
      this.price = "";
      this.stockQuantity = "";
      this.author = "";
      this.isbn = "";
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
}
.form-group {
  margin-bottom: 10px;
}
</style>
