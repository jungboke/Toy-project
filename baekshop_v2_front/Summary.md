## BaekShopV2-frontend

0. 백엔드, 프론트엔드 통합 리팩토링에 필요한 요소: Id Generator, 쿼리최적화, DTO 작업, final 붙이기, 백엔드 try catch 작성

1. 추가 라이브러리: vuex@3.6.2, axios, vue-router@3.5.3, bootstrap-vue
2. index.html에 fontawesome icon 스크립트 추가
3. google ubuntu font 링크 추가
4. router 작업 시작
5. src 아래 router dir 생성후, index.js를 만들어서 router 모듈 작성하고 main.js에서 import함.
6. router 모듈에서 사용하는 view component들은 src 아래 views dir에서 관리함.
7. vuex 모듈이나 router 모듈에서 `Vue.use(VueRouter)` 처럼 플러그인 사용을 명시해줘야 함.
8. main.js에서 vuex, router 모듈을 Vue 인스턴스에 속성으로 줘야 함.
9. View 작업 시작
10. bootstrap-vue 사용을 위해 main.js에 관련 플러그인 import함.
11. index.html 전체에 적용되어야 할 css 스타일은 App 컴포넌트에 scope없이 작성함.
12. 모든 view에서 공통적으로 들어갈 컴포넌트는 components dir에서 관리함.
13. Body->App->Header 처럼 내려가는 순으로 css 설정
14. a태그 css설정은 App에서 설정하는 것보다 router-link가 더 나중에 설정되서 scope 내에서만 써야할 듯함. !important로 후의 변경을 적용하지 않음.
15. p태그는 기본적으로 margin이 있고, div는 없음. div내에 p포함 가능한데, 역은 안됨.
16. div의 class명이 row면 자동으로 flex box가 됨.
17. api를 사용하는 list page들을 제외하고, 나머지 view 작성완료함.
18. api 데이터 저장을 위한 store,api 작업 시작
19. src 아래 store dir 생성후, index.js를 만들어서 vuex 모듈(store) 작성하고 main.js에서 import함.
20. src 아래 api dir 생성후, index.js를 만들어서 api함수들을 export해서 view 컴포넌트에서 사용함.
21. api데이터를 받아서 저장할 state 데이터 생성
22. api의 index.js에 axios 함수 작성하고, vuex 모듈의 actions에서 해당 함수를 사용
23. list화면에서 actions 함수 사용을 통해 데이터를 받아올 때, CORS문제가 발생함. 따라서 백엔드에서 CORS문제를 해결해야함. WebConfig class를 만들고 addCorsMapping을 통해 해결함.
24. Bootstrap table과 헬퍼함수를 추가적으로 활용하여 list 구현완료
25. 이제 axios.post를 통해 json데이터를 백엔드로 넘겨 회원,물품,주문 정보를 등록해야함.
26. axios.post에는 data를 넘겨줄 수 있으며, headers로 `application/json` 을 줘야 ContentType을 설정할 수 있음.
27. 태그의 id나, 컴포넌트의 data 명으로 name을 사용하면 에러가 발생함.
28. post로 Json data를 넘기고, 백엔드 api에서 받을 때, 프론트엔드의 입력폼에 맞는 DTO 객체를 따로 만들어서 받아줘야 함.
29. b-form-select가 b-form-input과 css스타일이 다른 관계로 select css에 class="form-control" 의 css style에서 appearance만 빼고 복붙함.
30. axios.post로 data를 보낼때, payload로 보내짐. @RequestBody랑 무슨 연관인가?
31. OrderView에서 백엔드로 api를 날릴때, memberId, itemId가 필요함. 따라서 프론트엔드가 받는 데이터에 id가 포함되어 있어야됨. api관련부분에 id추가하는 작업함.
32. axios.post에서 queryParam을 보내고 싶을때, 세번째 파라미터에 추가해서 보내야됨.
33. 프론트엔드 구현 완료했고, 인증 백엔드 통합 작업 시작
34. 백엔드, 프론트엔드 통합 리팩토링은 인증, 검증, 예외처리 설계 전부 끝난 다음에 진행할 예정임.
35. 프론트엔드의 인증처리 과정으로 백엔드에서 보낸 token을 LocalStorage에 넣어야 함. 따라서 login api에서 response의 token값을 다루는 로직이 필요함.
36. api의 index.js에서 login의 url을 /auth로 변경해줌. 또한, 다른 api함수들에서 header에 Authorization 속성으로 Bearer token을 넣어주는 로직을 추가함.
37. HomeView에 v-if로 LoginFlag에 따라 로그인,로그아웃이 바뀌도록 수정함.
38. 각 View에 접근할 때, token이 없으면 라우팅되기 전에 차단되도록 네비게이션 가드를 설정함.
39. 컴포넌트가 아닌 js파일들에서 router나 store를 쓰려면 import해서 직접 router.push 처럼 사용하면 됨. 또한 데이터 전송도 bus로 처리해야 함.
40. 프론트엔드 인증처리 과정 완료했고, 다시 백엔드로 가서 검증작업 시작
41. 프론트엔드의 각 input에서 검증이 이루어져야 하지만, 중점은 백엔드 작업이므로, 백엔드에서 보내주는 error를 catch해서 팝업창만 띄울 수 있도록 설계함.
42. 팝업창은 Modal 컴포넌트를 사용함. components dir 내에 Modal.vue 생성하고, App 컴포넌트에서 import해서 사용함.
43. 화면 새로고침하면 store의 state값 전부 초기화되서 Modal flag를 state로 옮겨도 좋을 듯함.
44. 백엔드 예외처리 작업 시작
45. Spinner 작업 시작
46. Spinner 컴포넌트를 복붙해서 생성하고, App 컴포넌트에서 import해서 사용함. Spinner를 컨트롤하는 loading flag는 App 컴포넌트의 data가 가지고 있고, 해당 flag를 다루는 함수를 제작함.
    utils dir을 만들고, bus.js 파일을 만들어서 해당 bus를 통해 다른 컴포넌트나, JS파일에서 App의 함수를 컨트롤함. bus는 아마 다른 컴포넌트에서 특정 컴포넌트의 함수를 원격으로 실행해주는 역할인듯?
47. router에서 App 컴포넌트의 loading flag를 bus.$emit을 통해 네비게이션 가드에서 돌려주고, ListView들의 distpatch함수도 네비게이션 가드에서 돌려줌. 기존 dispatch방식은 빈 리스트가 먼저 뜨고, dispatch가 늦게 시작됐는데, 네비게이션 가드에서 돌려주면 특정 컴포넌트가 동작하기전에, dispatch가 완료되서 빈 리스트가 안보임. Spinner도 네비게이션 가드에서 돌리고, dispatch가 먼저 된 리스트가 띄워질 때 꺼져서 좋음.
48. Modal도 bus를 사용하도록 수정함.
49. 컴포넌트의 created는 computed보다 빨리 적용되기 때문에, created에서 store 값 사용하려면 mapState 사용불가능함.
50. 모든 View에 뒤로 버튼 구현함.
51. 프론트엔드도 기능 구현 다했고, 백엔드, 프론트엔드 1차 리팩토링 작업 시작
52.
