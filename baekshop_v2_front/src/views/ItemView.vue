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
    <b-button variant="dark" @click="addItem()">Submit</b-button>
    <b-button @click="back()" variant="dark">취소</b-button>
  </div>
</template>

<script>
export default {
  data() {
    return {
      itemName: "",
      price: "",
      stockQuantity: "",
      author: "",
      isbn: "",
    };
  },
  methods: {
    addItem() {
      let item = {
        name: this.itemName,
        price: this.price,
        stockQuantity: this.stockQuantity,
        author: this.author,
        isbn: this.isbn,
      };
      this.$store.dispatch("POST_ITEMS", item);
      this.clearInput();
    },
    clearInput() {
      this.itemName = "";
      this.price = "";
      this.stockQuantity = "";
      this.author = "";
      this.isbn = "";
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
</style>
