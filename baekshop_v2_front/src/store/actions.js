import {
  fetchItems,
  fetchItem,
  updateItem,
  deleteItem,
  fetchMembers,
  fetchMember,
  updateMember,
  deleteMember,
  fetchOrders,
  fetchOrder,
  deleteOrder,
  postItems,
  signin,
  postOrders,
  signup,
} from "../api/index.js";
import { router } from "../router/index.js";
import bus from "../utils/bus.js";

export default {
  FETCH_MEMBERS({ commit }) {
    return fetchMembers().then((response) =>
      commit("SET_MEMBERS", response.data)
    );
  },
  FETCH_MEMBER({ commit }, data) {
    return fetchMember(data).then((response) =>
      commit("SET_MEMBERS", response.data)
    );
  },
  UPDATE_MEMBER({ commit }, data) {
    return updateMember(data).then((response) => {
      router.push("/signup/list");
    });
  },
  DELETE_MEMBER({ commit }, data) {
    return deleteMember(data).then((response) => {
      router.go(router.currentRoute);
    });
  },
  FETCH_ITEMS({ commit }) {
    return fetchItems().then((response) => commit("SET_ITEMS", response.data));
  },
  FETCH_ITEM({ commit }, data) {
    return fetchItem(data).then((response) =>
      commit("SET_ITEMS", response.data)
    );
  },
  UPDATE_ITEM({ commit }, data) {
    return updateItem(data).then((response) => {
      router.push("/item/list");
    });
  },
  DELETE_ITEM({ commit }, data) {
    return deleteItem(data).then((response) => {
      router.go(router.currentRoute);
    });
  },
  FETCH_ORDERS({ commit }) {
    return fetchOrders().then((response) =>
      commit("SET_ORDERS", response.data)
    );
  },
  FETCH_ORDER({ commit }, data) {
    return fetchOrder(data).then((response) =>
      commit("SET_ORDERS", response.data)
    );
  },
  DELETE_ORDER({ commit }, data) {
    return deleteOrder(data).then((response) => {
      router.go(router.currentRoute);
    });
  },
  POST_MEMBERS({ commit }, data) {
    return signin(data)
      .then((response) => {
        commit("SET_MEMBERS", response.data);
        router.push("/login");
      })
      .catch((error) => {
        console.log("예외 발생");
        bus.$emit("on:modal");
      });
  },
  POST_ITEMS({ commit }, data) {
    return postItems(data)
      .then((response) => {
        commit("SET_ITEMS", response.data);
        router.push("item/list");
      })
      .catch((error) => {
        console.log("예외 발생");
        bus.$emit("on:modal");
      });
  },
  POST_ORDERS({ commit }, data) {
    return postOrders(data)
      .then((response) => {
        commit("SET_ORDERS", response.data);
        router.push("order/list");
      })
      .catch((error) => {
        console.log("예외 발생");
        bus.$emit("on:modal");
      });
  },
  LOGIN({ commit }, data) {
    return signup(data)
      .then((response) => {
        if (response.data.token) {
          localStorage.setItem("ACCESS_TOKEN", response.data.token);
          router.push("/home");
        }
      })
      .catch((error) => {
        console.log("예외 발생");
        bus.$emit("on:modal");
      });
  },
};
