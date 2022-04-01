import axios from "axios";

const api = {
  members: "http://localhost:8080/api/v1/members/",
  items: "http://localhost:8080/api/v1/books/",
  orders: "http://localhost:8080/api/v1/orders/",
  signin: "http://localhost:8080/api/auth/v1/members/signin/",
  signup: "http://localhost:8080/api/auth/v1/members/signup/",
};

function MakeHeader() {
  let headers = {
    "Content-Type": "application/json",
  };
  const accessToken = localStorage.getItem("ACCESS_TOKEN");
  if (accessToken && accessToken != null) {
    headers.Authorization = "Bearer " + accessToken;
  }

  return headers;
}

function fetchMembers() {
  const headers = MakeHeader();
  return axios.get(api.members, { headers });
}

function fetchMember(data) {
  let headers = {
    "Content-Type": "application/json",
  };
  const accessToken = localStorage.getItem("ACCESS_TOKEN");
  if (accessToken && accessToken != null) {
    headers.Authorization = "Bearer " + accessToken;
  }
  const url = `${api.members}${data}`;
  return axios.get(url, { headers });
}

function updateMember(data) {
  let headers = {
    "Content-Type": "application/json",
  };
  const accessToken = localStorage.getItem("ACCESS_TOKEN");
  if (accessToken && accessToken != null) {
    headers.Authorization = "Bearer " + accessToken;
  }
  const url = `${api.members}${"update"}`;
  return axios.put(url, JSON.stringify(data), { headers });
}

function deleteMember(data) {
  let headers = {
    "Content-Type": "application/json",
  };
  const accessToken = localStorage.getItem("ACCESS_TOKEN");
  if (accessToken && accessToken != null) {
    headers.Authorization = "Bearer " + accessToken;
  }
  const url = `${api.members}${"delete/"}${data}`;
  return axios.delete(url, { headers });
}

function fetchItems() {
  let headers = {
    "Content-Type": "application/json",
  };
  const accessToken = localStorage.getItem("ACCESS_TOKEN");
  if (accessToken && accessToken != null) {
    headers.Authorization = "Bearer " + accessToken;
  }
  return axios.get(api.items, { headers });
}

function fetchItem(data) {
  let headers = {
    "Content-Type": "application/json",
  };
  const accessToken = localStorage.getItem("ACCESS_TOKEN");
  if (accessToken && accessToken != null) {
    headers.Authorization = "Bearer " + accessToken;
  }
  const url = `${api.items}${data}`;
  return axios.get(url, { headers });
}

function updateItem(data) {
  let headers = {
    "Content-Type": "application/json",
  };
  const accessToken = localStorage.getItem("ACCESS_TOKEN");
  if (accessToken && accessToken != null) {
    headers.Authorization = "Bearer " + accessToken;
  }
  const url = `${api.items}${"update"}`;
  return axios.put(url, JSON.stringify(data), { headers });
}

function deleteItem(data) {
  let headers = {
    "Content-Type": "application/json",
  };
  const accessToken = localStorage.getItem("ACCESS_TOKEN");
  if (accessToken && accessToken != null) {
    headers.Authorization = "Bearer " + accessToken;
  }
  const url = `${api.items}${"delete/"}${data}`;
  return axios.delete(url, { headers });
}

function fetchOrders() {
  let headers = {
    "Content-Type": "application/json",
  };
  const accessToken = localStorage.getItem("ACCESS_TOKEN");
  if (accessToken && accessToken != null) {
    headers.Authorization = "Bearer " + accessToken;
  }
  return axios.get(api.orders, { headers });
}

function fetchOrder(data) {
  let headers = {
    "Content-Type": "application/json",
  };
  const accessToken = localStorage.getItem("ACCESS_TOKEN");
  if (accessToken && accessToken != null) {
    headers.Authorization = "Bearer " + accessToken;
  }
  const url = `${api.orders}${data}`;
  return axios.get(url, { headers });
}

function deleteOrder(data) {
  let headers = {
    "Content-Type": "application/json",
  };
  const accessToken = localStorage.getItem("ACCESS_TOKEN");
  if (accessToken && accessToken != null) {
    headers.Authorization = "Bearer " + accessToken;
  }
  const url = `${api.orders}${"cancel/"}${data}`;
  return axios.delete(url, { headers });
}

function signin(data) {
  let headers = {
    "Content-Type": "application/json",
  };
  return axios.post(api.signin, JSON.stringify(data), { headers });
}

function postItems(data) {
  let headers = {
    "Content-Type": "application/json",
  };
  const accessToken = localStorage.getItem("ACCESS_TOKEN");
  if (accessToken && accessToken != null) {
    headers.Authorization = "Bearer " + accessToken;
  }
  return axios.post(api.items, JSON.stringify(data), { headers });
}

function postOrders(data) {
  let headers = {
    "Content-Type": "application/json",
  };
  const params = {
    memberId: data.memberId,
    itemId: data.itemId,
    count: data.count,
  };
  const accessToken = localStorage.getItem("ACCESS_TOKEN");
  if (accessToken && accessToken != null) {
    headers.Authorization = "Bearer " + accessToken;
  }
  return axios.post(api.orders, null, { headers, params });
}

function signup(data) {
  let headers = {
    "Content-Type": "application/json",
  };
  return axios.post(api.signup, JSON.stringify(data), { headers });
}

export {
  fetchMembers,
  fetchMember,
  updateMember,
  deleteMember,
  fetchItems,
  fetchItem,
  updateItem,
  deleteItem,
  fetchOrders,
  fetchOrder,
  deleteOrder,
  signin,
  postItems,
  postOrders,
  signup,
};
