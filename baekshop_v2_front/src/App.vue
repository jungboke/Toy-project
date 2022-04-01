<template>
  <div id="app">
    <spinner :loading="loading"></spinner>
    <Header></Header>
    <router-view></router-view>
    <Footer></Footer>
    <Modal v-if="showModal" @close="showModal = false">
      <h3 slot="header">
        경고
        <i
          class="closeModalBtn fa fa-times"
          aria-hidden="true"
          @click="offModal()"
        >
        </i>
      </h3>
      <p slot="body">다시 입력해주세요.</p>
    </Modal>
  </div>
</template>

<script>
import Header from "./components/Header.vue";
import Footer from "./components/Footer.vue";
import Modal from "./components/Modal.vue";
import Spinner from "./components/Spinner.vue";
import { mapState, mapMutations } from "vuex";
import bus from "./utils/bus.js";

export default {
  components: {
    Header,
    Footer,
    Modal,
    Spinner,
  },
  data() {
    return {
      loading: false,
      showModal: false,
    };
  },
  methods: {
    onModal() {
      this.showModal = true;
    },
    offModal() {
      this.showModal = false;
    },
    onProgress() {
      this.loading = true;
    },
    offProgress() {
      this.loading = false;
    },
  },
  created() {
    bus.$on("on:progress", this.onProgress);
    bus.$on("off:progress", this.offProgress);
    bus.$on("on:modal", this.onModal);
    bus.$on("off:modal", this.offModal);
  },
};
</script>

<style>
body {
  padding: 20px 100px;
  display: block;
  font-size: 15px !important;
  font-weight: 100 !important;
}
a {
  text-decoration: none !important;
}
</style>
