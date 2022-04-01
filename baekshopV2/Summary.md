## BaekShopV2  
1. BootInit시, 추가한 라이브러리: Lombok, Data JPA, Devtools, Web, Thymeleaf, Validation,
Security, H2, json-simple
2. 직접 의존성 등록한 라이브러리: p6spy, querydsl, postgres, jwt
3. jwt 관련 라이브러리 : `implementation group: 'io.jsonwebtoken', name: 'jjwt', version: '0.9.1'`
4. Setting: annotationProcessing, Test 컴파일러: ItelliJ
5. Docker for Windows를 통한 DB컨테이너 생성
6. Domain 작업: 설계도 대로, Enum Type Class, 값타입 Class 생성후 Entity Class 생성 및 필드 입력
-> 연관관계 세팅 -> fetch,cascade 설정 -> 정적팩토리메서드 구현 -> 연관관계 편의 메서드 구현
7. 왜 Item에서 OrderItem에 대한 OneToMany가 없는가? 추가해줌.
8. Entity의 팩토리메서드를 구현할 때, 연관관계의 주인이 아닌 필드값은
파라미터로 들어가면 안됨.
9. 팩토리메서드 구현시, 연관관계 필드 안넣고, 나중에 필요하다 싶으면 리팩토링함.
10. 후에 인증처리 과정에서 Member Entity 리팩토링 필요함.
11. Entity에 setter 사용을 자제하라는 뜻은 Entity가 만들어진 후, setter를
통해 변경이 많이 이루어지면, 그에 대한 추적이 어려워지기 때문에 자제하라는 말임.
또한 setter가 열려있으면, 다른 개발자가 해당 부분을 마음대로 조작해도 되는지 헷갈릴 수 있음.
따라서 애초에 setter를 막아놓는게 중요함. 팩토리 메서드(생성 메서드)에서도
setter를 사용하는 게 아닌 생성자에 직접 파라미터를 넣거나, 빌더를 사용하는 게 좋음.
Entity를 수정해야 하는 부분도 setter가 아닌, 이름이 있는 비즈니스 로직을 짜서
변경 하는 게 좋음. 하지만 연관관계 편의 메서드와 같이 setter를 사용해야 하는 부분은
어느정도 setter를 허용해 주는 것도 좋음.
12. Repository까지 구현 후, Test 필요함. 이때, Test에 따로 인메모리H2가
사용되어야 편리하므로, 외부 설정파일 따로 작성함.
13. 처음 Entity를 만들었을 때, Test는 너무 뻔하고 비즈니스 로직을 만들었을 때
Test하는게 효율적일 듯 함.
14. Domain 생성 후, querydsl 사용을 위해 compileQueryDsl 해줌.
15. Service 작업: Service를 구현하다가 Entity의 값을 직접 수정해야 하는
로직은 Entity 내에 구현하기
16. Service에 @Transactional 어노테이션을 명시하는 이유?
17. CRUD로직을 우선 만들고, 검증처리는 한번에 하기
18. Order Entity는 Member와 Item과는 다르게 생성시 다른 Entity를 파라미터로
사용해야 함.
19. Order의 팩토리 메서드인 createOrder에서 Item 대신 orderItem을
사용하는 이유는 Item과 Order가 다대다 관계이기 때문에 중간에 OrderItem을
뒀기 때문임. 이때, Order는 OrderItem이 연관관계 주인이지만 팩토리 메서드에
파라미터로 사용했음. 파라미터 사용은 주종관계와 관련 없는 듯함.
20. 편의 메서드의 위치는 연관관계 주인이나 종 아무곳이나 위치 가능한데,
어느 팩토리 메서드에서 상대 Entity를 파라미터로 사용하냐에 따라 결정하는 게 좋을 듯함.
21. 기존에 작성한 팩토리 메서드들에 연관관계 필드를 파라미터로 입력함.
이때, 단순 orderDate나 status와는 다르게 `order.status=OrderStatus.Order` 처럼
필드에 직접 대입이 아닌, 편의메서드를 통해 양쪽 Entity에 적용시켜줘야 함.
22. Service의 update에서 setter를 사용하지 않으려면, Entity의 변경감지 로직을
활용해야 함. 이때, Entity의 필드값을 변경하는 것이라, Entity 내부에
비즈니스 로직을 작성하는 게 좋음. 이런 방식이 Service에서 Entity로 비즈니스
로직이 옮겨지는 과정인 듯함.
23. OrderService의 cancel에서 Order는 status만 바뀌면 되고, 해당 Order Entity와
연관된 OrderItems는 바뀌지 않고, 해당 OrderItems와 연관된 item의
수량만 다시 올라가면 됨. 이 과정에서 각각의 Entity에 비즈니스 로직이 추가됨.
24. Service Test함. 이때, Rollback(false)를 하지 않으면 트렌젝션의 commit이
발생하지 않고 rollback이 일어나므로, 쿼리결과를 확인할 수 없음.
25. 변경감지를 통한 update후, 다시 itemService의 findOne을 통해
Entity를 가져올 때, 이미 영속성 컨텍스트에 해당 Entity가 남아있기 때문에
실제 DB에서 가져오지 않고, 재활용하게 됨. 이러한 문제 때문에 실제DB의
데이터가 변경되었는지 확인을 못함. JPA에 대해 더 공부해야 함.
26. Test코드에서 EntityManager를 주입받아 직접 컨텍스트에 persist할 수 있음.
27. @Rollback(false)와 EntityManager 동시에 사용불가능?
28. Controller 작업: 각 Entity의 DTO를 dto directory에 생성하고,
팩토리 메서드를 생성해줌. 또한 각 API call의 반환값을 ResponseEntity<?>로
고정해서 사용함.
29. SpringSecurity의 AutoConfiguration 기능을 꺼야 default 인증처리를
끌수 있음. `@SpringBootApplication(exclude = SecurityAutoConfiguration.class)`
30. Body에서 작성하지 않은 Json 객체의 속성은 null로 들어감.
31. Member Entity List를 MemberDTO List로 매핑하기 위해 stream()문법을
사용해야 하고, 해당 과정에 MemberDTO 내에 Member를 통한 생성자가 필요함.
32. 후에 리팩토링 과정에서 팩토리 메서드에서 builder를 통한 생성자 생성
구현하기
33. itemService.saveItem(book) 과정에서 반환값으로 Item이 나오는데, Book으로
강제형변환 해줘야함.
34. (중요)토이프로젝트를 하면서 나만의 컨벤션을 짜는게 가장 중요한 듯함.
35. 프로젝트 시작시 더미데이터 입력을 위해 InitDb class 생성
36. Order Entity에 cascade 설정해야함. Order Entity를 등록할 때, member와
item은 이미 등록되어 있는 Entity를 사용하는데, delivery와 orderItem은 order등록
과정에서 생성되는 Entity이므로, order등록시 함께 컨텍스트에 persist되어야 함.
37. API 구현완료 후, 프론트엔드 작업 시작
38. 인증절차 도입을 위해 Member Entity에 password, id 필드 생성함.
39. MemberService에 validateDuplicateMember 함수와 login 함수 추가함.
40. MemberController의 create는 signup의 개념이라 바로 아래에 signin 개념인
authenticate 메서드를 추가함.
41. 후에 validateDuplicateMember
42. 프론트엔드의 LoginView 관련 api 작업함.
43. security dir을 만들고 인증과 인가에 필요한 모든 class를 관리함.
44. security dir에 TokenProvider class 생성함. 해당 class는 Member Entity를
사용해 JWT을 생성하는 역할임. 또한 token을 받아 secret_key를 통해 id값을
뽑아내는 역할도 함.
45. JWT를 생성하는데 String 타입의 Id가 필요하므로, 모든 Entity의 Id Generator
작업을 해야함. 그냥 맨처음부터 하면 좋을 듯함.
46. TokenProvider 생성 끝나면, MemberDTO에 token 필드를 추가함.
47. authenticate(signup)에서 TokenProvider를 통해 token을 생성하고,
MemberDTO에 넣어주는 과정이 필요함. 해당 MemberDTO는 프론트엔드로 넘어가고,
token을 저장함.
48. 이제 Spring Security를 사용해 로그인 서블릿 필터를 구현한 뒤,
해당 token을 사용해 인증받는 절차를 구현해야 함.
49. 기존 서블릿 필터와는 다르게, 필터를 WAS에 등록하는 게 아닌, WebSecurityConfigurerAdapter를
통해 Security의 FilterChainProxy에 등록해줘야 함.
50. JwtAuthenticationFilter에서는 request의 BearerToken을 확인하고,
해당 token을 파싱하여 id를 꺼내고 SecurityContext에 등록해줌.
token을 파싱하는 과정에서 해당 token이 백엔드의 TokenProvider에서 만들어진
token이 아니면 예외를 발생시킴.
51. WebSecurityConfig를 만들고 security관련 설정 및 위에서 만든 필터를 등록해줌.
52. 인증필터를 통과하고, SecurityContext에 등록된 Member Entity의 id는
Controller에서 @AuthenticationPrincipal로 접근하여 사용할 수 있음.
53. 인증 구현 과정에서 초기에 등록했던 password가 단순 String이기 때문에
Spring Security가 제공하는 BcryptPasswordEncoder를 통해 패스워드를
암호화 하는 과정이 필요함.
54. LoginView에서 받은 password를 같은 Encoder로 암호화한후, 데이터베이스
값과 비교하는 게 아니고, Encoder의 matches() 함수를 사용해서 직접 비교해야 함.
BcrpytPasswordEncoder는 같은 값을 인코딩하더라도 매번 값이 달라져서 그럼.
55. MemberService의 login()함수에 파라미터로 PasswordEncoder 추가하고,
로직을 변경함.
56. MemberController에서 password 관련 부분을 Encoder를 사용해서 변경함.
57. 이제 Member Entity의 password는 Encoding된 String이 들어감.
58. 다시 프론트엔드에서 token을 받아 LocalStorage에 넣는 작업을 시작
59. Spring에서 제공하는 message, 국제화 기능은 프론트엔드를 따로 구축했기 때문에
쓸일이 없을 듯함.
60. 검증과정은 1차적으로 프론트엔드에서 발생하고, 2차적으로 백엔드에서 발생해야함.
백엔드에서 검증과정은 Bean Validation으로 처리함. @Validate와 BindingResult를 통해
검증하는데, api에서 타입 관련 바인딩오류가 발생하면 HTTP 메시지 컨버터가
json요청을 특정 객체로 변환하는 과정자체가 실패해서 에러페이지가 발생함.
61. DTO의 int값에 @NotNull을 붙이기 위해서 int를 Integer로 바꿔서 null값이
들어올 수 있도록 바꿔줘야함.
62. @RequestParam의 validation은?
63. RequestParam은 @RequestBody나 @ModelAttribute와는 다르게, 검증실패시,
바로 에러를 던짐. 나머지 둘은 검증 실패해도 BindingResult에 에러를 집어넣고,
함수는 계속 진행이 됨. 따라서 RequestParam에서 BindingResult는 필요없음.
64. LoginView와 SignupView에서 보내지는 데이터는 다르게 validation이
적용되어야함. 따라서 DTO분리가 필수적으로 요구됨.
65. 응답으로 DTO가 나갈때, 에러가 발생했을 경우, 각 예외상황에 맞는
에러처리를 해야함. 해당 프로젝트에서는 response는 ResponseEntity.ok()로
직접 DTO 자체를 body로 보내고, 에러발생시, Response.badRequest를 통해 보내기로 통일함.
66. 백엔드 api에서 검증처리후, 프론트엔드로 에러를 보내는 것밖에 할게 없음.
67. 프론트엔드 검증작업 시작
68. 백엔드의 예외처리를 위해 ExceptionResolver 중에서 @ExceptionHandler를 사용함.
69. Controller에서 프론트엔드로 직접 보내던 에러(ResponseEntity.badRequest)를
예외로 대체하고, ExceptionResolver에서 예외를 잡아 프론트엔드로
json형식으로 바꿔서 보내주는 방식을 사용함.
70. @ExceptionHandler를 Controller내에 작성하지 않고 @RestControllerAdvice와
함께 사용해서 advice 형식으로 작성함.
71. 추가적으로 ErrorResult라는 response body에 들어갈 객체를 생성함.
72. ErrorResult내의 code를 조작하여 api의 각 상황마다 다르게 입력해서
프론트엔드에서 해당 code로 다른 대처를 할수 있을 듯함.
73. 백엔드 타입컨버터 작업 시작
74. json-simple을 사용하여 json 형태의 string data를 쉽게 파싱할 수 있음.
controller의 api에서 파라미터로 Address 같은 직접 만든 객체가 사용될 경우,
포맷터가 요구됨. 해당 포맷터에서 json string을 파싱함.
75. controller에서 json 형 string 데이터가 오면 알아서 객체로 변환해주는 듯함.
근데 ObjectMapper 사용한 파싱은 로직구현에 쓸모있을 것 같으니 잘 알아둬야함.
76. MemberDTO에서 Address 값타입 필드가 검증안되어서 String으로 분리하여 다시 작성함.
77. int, Integer 차이: int는 단순 자료형이고, Integer는 래퍼클래스(객체임).
int는 null 처리불가능하고, 산술연산이 가능한데, Integer는 그 반대임.
78. 백엔드에서 현재 사용할 수 있는 스프링 기능 다 적용한 듯함.
79. 프론트엔드의 기능 마무리 작업 시작
80. Controller에서 update, delete 작업시작함.
81. Entity delete할때, 연관관계 Entity가 있으면, 전부 cascade 설정
해줘야됨. Member지울때, 연관관계인 Order에 cascade 설정해줬음.
82. delete 문제를 해결하기 위해서는 연관관계 필드마다 cascade를 설정해줘야했음.
근데 이렇게 cascade를 함부로 설정하면, 특정 데이터 delete시, 의도치 않은 데이터가
같이 삭제될 수 있음. delete시 영향이 가는 연관관계 파악도 DB설정 초기에 했던
생성시 파악했던 연관관계만큼 중요함. 따라서 delete는 공부를 하고 신중하게 해야됨.
주로 delete시 연관관계 파악은 FK로 함. Item이 지워지면 OrderItem도 지워지는데,
Order도 현재 프로젝트에서는 OrderItem과 사실상 1:1관계이므로, OrderItem에서 Order에
cascade 설정을 해줬음.
83. Order는 Item과 다르게 삭제라는 개념대신 취소로 생각함. Item은 삭제되면,
수량관계를 파악할 필요없지만, Order는 취소되면 다시 Item의 수량을 늘려줘야함.
84. 해당 프로젝트처럼 DB에서 데이터를 지울때, 완전히 지우는 방식과, DB에
데이터는 남겨두지만, status는 CANCEL로 만드는 방법이 있음. status만 바꾸면
완전삭제가 아니라, 덜 위험하지만 데이터가 DB에 계속 쌓이게 됨. 아직 데이터를
주기적으로 삭제하는 방법을 모르므로, Order도 취소가 아닌 완전삭제로 함. 근데
완전삭제를 해도 Item의 수량은 다시 올려줘야 하므로, 기존의 cancel 함수를 재활용함.
85. Optional 타입에서 데이터가 없을 때, get()을 하면 예외 발생함.
86. 백엔드 1차 리팩토링 : DTO 관리(정보유출), 파라미터나 결과값으로 나오는 Entity
validate, isPresent()를 통한 Optional의 data조사, 거의 Service에만 하면 됨,
파라미터에 final 붙이기, 이것도 Service에만, 사용하지 않는 import제거,
dir 아키텍쳐 최적화, 쿼리최적화는 후의 리팩토링에서 진행함.
87. 프론트엔드 1차 리팩토링 작업 시작