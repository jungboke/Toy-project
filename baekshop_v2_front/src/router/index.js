import Vue from "vue";
import VueRouter from "vue-router";
import HomeView from "../views/HomeView.vue";
import SignupView from "../views/SignupView.vue";
import SignupList from "../views/SignupList.vue";
import ItemView from "../views/ItemView.vue";
import ItemList from "../views/ItemList.vue";
import OrderView from "../views/OrderView.vue";
import OrderList from "../views/OrderList.vue";
import LoginView from "../views/LoginView.vue";
import SignupUpdate from "../views/SignupUpdate.vue";
import ItemUpdate from "../views/ItemUpdate.vue";
import bus from "../utils/bus.js";
import store from "../store/index.js";

Vue.use(VueRouter);

export const router = new VueRouter({
  mode: "history",
  routes: [
    {
      path: "/",
      redirect: "/home",
    },
    {
      path: "/home",
      component: HomeView,
    },
    {
      path: "/login",
      component: LoginView,
    },
    {
      path: "/signup",
      component: SignupView,
    },
    {
      path: "/signup/list",
      component: SignupList,
      beforeEnter(routeTo, routeFrom, next) {
        if (localStorage.getItem("ACCESS_TOKEN") == null) {
          router.push("/login");
        } else {
          bus.$emit("on:progress");
          store.dispatch("FETCH_MEMBERS").then(() => next());
        }
      },
    },
    {
      path: "/signup/list/:id",
      component: SignupUpdate,
      beforeEnter(routeTo, routeFrom, next) {
        bus.$emit("on:progress");
        const MemberId = routeTo.params.id;
        store.dispatch("FETCH_MEMBER", MemberId).then(() => next());
      },
    },
    {
      path: "/item",
      component: ItemView,
      beforeEnter(routeTo, routeFrom, next) {
        if (localStorage.getItem("ACCESS_TOKEN") == null) {
          router.push("/login");
        } else {
          next();
        }
      },
    },
    {
      path: "/item/list",
      component: ItemList,
      beforeEnter(routeTo, routeFrom, next) {
        if (localStorage.getItem("ACCESS_TOKEN") == null) {
          router.push("/login");
        } else {
          bus.$emit("on:progress");
          store.dispatch("FETCH_ITEMS").then(() => next());
        }
      },
    },
    {
      path: "/item/list/:id",
      component: ItemUpdate,
      beforeEnter(routeTo, routeFrom, next) {
        bus.$emit("on:progress");
        const ItemId = routeTo.params.id;
        store.dispatch("FETCH_ITEM", ItemId).then(() => next());
      },
    },
    {
      path: "/order",
      component: OrderView,
      beforeEnter(routeTo, routeFrom, next) {
        if (localStorage.getItem("ACCESS_TOKEN") == null) {
          router.push("/login");
        } else {
          next();
        }
      },
    },
    {
      path: "/order/list",
      component: OrderList,
      beforeEnter(routeTo, routeFrom, next) {
        if (localStorage.getItem("ACCESS_TOKEN") == null) {
          router.push("/login");
        } else {
          bus.$emit("on:progress");
          store.dispatch("FETCH_ORDERS").then(() => next());
        }
      },
    },
  ],
});
