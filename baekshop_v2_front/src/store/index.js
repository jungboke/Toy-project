import Vue from "vue";
import Vuex from "vuex";
import mutations from "./mutations.js";
import actions from "./actions.js";
import getters from "./getters.js";

Vue.use(Vuex);

export default new Vuex.Store({
  state: {
    members: [],
    items: [],
    orders: [],
  },
  getters,
  mutations,
  actions,
});