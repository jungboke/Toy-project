# Toy-project

## BaekShop
  
0. 요구사항 분석 -> 도메인 모델과 테이블 설계 -> Entity 클래스 설계 순
1. 의존성: lombok, data jpa, devtools, web, thymeleaf
1-2. querydsl과 postgres, p6spy는 BootInit으로 의존성 등록 못해서 따로 등록해줌.
1-3. p6spy: `implementation 'com.github.gavlyukovskiy:p6spy-spring-boot-starter:1.5.6'`
1-4. querydsl
````
//qdsl
buildscript {
    ext {
        queryDslVersion = "5.0.0"
    }
}

plugins {
    id 'org.springframework.boot' version '2.6.3'
    id 'io.spring.dependency-management' version '1.0.11.RELEASE'
    //dqsl
    id "com.ewerk.gradle.plugins.querydsl" version "1.0.10"
    
    id 'java'
}

group = 'me.jungboke'
version = '0.0.1-SNAPSHOT'
sourceCompatibility = '1.8'

configurations {
    compileOnly {
        extendsFrom annotationProcessor
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation 'org.springframework.boot:spring-boot-starter-data-jpa'
    implementation 'org.springframework.boot:spring-boot-starter-thymeleaf'
    implementation 'org.springframework.boot:spring-boot-starter-web'
    implementation group: 'org.postgresql', name: 'postgresql'
    implementation 'com.github.gavlyukovskiy:p6spy-spring-boot-starter:1.5.6'

    //qdsl
    implementation "com.querydsl:querydsl-jpa:${queryDslVersion}"
    implementation "com.querydsl:querydsl-apt:${queryDslVersion}"

    compileOnly 'org.projectlombok:lombok'
    developmentOnly 'org.springframework.boot:spring-boot-devtools'
    annotationProcessor 'org.projectlombok:lombok'
    testImplementation 'org.springframework.boot:spring-boot-starter-test'
}

tasks.named('test') {
    useJUnitPlatform()
}

//qdsl
def querydslDir = "$buildDir/generated/querydsl"
querydsl {
    jpa = true
    querydslSourcesDir = querydslDir
}
sourceSets {
    main.java.srcDir querydslDir
}
compileQuerydsl{
    options.annotationProcessorPath = configurations.querydsl
}
configurations {
    compileOnly {
        extendsFrom annotationProcessor
    }
    querydsl.extendsFrom compileClasspath
}
````
2. Setting: annotationProcessing, Test 컴파일러: ItelliJ
3. Postgres 사용을 위해 의존성에 `implementation group: 'org.postgresql', name: 'postgresql'`추가
4. `docker run -p 5432:5432 -e POSTGRES_PASSWORD=1234 -e POSTGRES_USER=jungboke -e POSTGRES_DB=springboot --name postgres_boot -d postgres`
를 shell에 입력해 docker로 postgres를 실행시킴.
5. DB로그인 정보, DB관련 입출력 정보를 외부 설정파일에 저장함.
````
spring.datasource.url=jdbc:postgresql://localhost:5432/springboot
spring.datasource.username=jungboke
spring.datasource.password=1234

spring.jpa.hibernate.ddl-auto=create
spring.jpa.properties.hibernate.format_sql=true
logging.level.org.hibernate.SQL=debug
logging.level.org.hibernate.type=trace
````
6. Docker Container로써, postgres를 돌리기만 하면 IDE내에서 DB접근 가능함.
7. Entity 설계후, Test로 DB에 정상입력되는지 확인함.
7-1. 각 Entity class를 만들고 필드를 생성
7-2. Entity 연관관계 및 필드속성 입력  
Member -> Order -> OrderItem -> Delivery -> Item -> Category -> Album,Book,Movie  
연관관계 세팅 -> EnumType,Inheritance 세팅 -> 지연로딩,cascade 세팅  
Entity는 프록시 객체를 위한 기본생성자 필요, Order Entity는 Table명 orders로 변경필요  
생성자 대신 정적팩토리 메서드 사용 이때, 생성당시 필요없는 연관관계Entity는 생성자에 안넣어줘야 함.  
비즈니스 로직 추가는 Controller에서 구현체 작성후 Entity위주로 구현하기  
연관관계 메서드는 주인 Entity에 작성  
왜 임베디드 타입에 기본생성자가 필요한지?  
setter 대체 메서드제작은 setter로 일단 다만든후, 최적화할때 하기  
8. Repository 작성(JPA Repository만 생성)
9. Entity별 Test코드 동작 확인  
9-1. 이때, Test의 외부설정파일을 따로 만들어 h2DB를 사용하도록
설정해줌. Postgres는 Test동작시 너무 오래걸리는 듯함. 그리고 devtools로는
h2 의존성 추가가 안됐음. 이번 프로젝트에는 h2,jdbc 의존성을 따로 추가해줌.  
````
implementation group: 'org.springframework', name: 'spring-jdbc', version: '5.3.14'
    testImplementation group: 'com.h2database', name: 'h2', version: '1.4.200'
````
ex) MemberTest
````
@SpringBootTest
@Transactional
@Rollback(value = false)
class MemberTest {

    @Autowired
    MemberRepository memberRepository;

    @Test
    @DisplayName("CreateMember")
    void createMember() throws Exception {
        //given
        Member member = new Member();
        member.setName("baek");
        //when
        Member saveMember = memberRepository.save(member);
        Optional<Member> findMember = memberRepository.findById(saveMember.getId());
        //then
        Assertions.assertThat(saveMember).isEqualTo(findMember.get());
    }

}
````
10. Service 작성 및 Entity 내부에 비즈니스 메서드 작성
10-1. 등록,조회등을 제외한 Entity 필드의 값을 수정하는 메서드는 내부에 작성
11. custom한 exception 생성
12. Service Test
12-1. OrderRepositoryImpl에서 JPAQueryFactory를 Autowired했는데,
그러기 위해선 JPAQueryFactory bean을 등록해줬어야 함. 위 문제로 Test도중 오류발생함.  
13. Web계층 작성(Controller -> View)
13-1. Controller 작성시, Slf4j를 사용해서 로그찍으면서 확인하기(Test대용)
13-2. fragments directory를 resource내에 생성하여 header,footer 사용하기
13-3. Bootstrap의 css,js 폴더를 static directory에 받아서 프론트 화면 최적화하기
13-4. jombotron-narrow.css 파일을 css폴더에 추가해줘서 화면비율맞추기
13-5. 입력폼에서 Bean 검증을 사용하기 위해 starter-validation 의존성 추가하기
13-6. 앱 실행후 Controller 동작을 확인하기 위해 InitDb라는 class를 생성하여 더미 데이터 생성하기
14. 필요한 html들 templates directory로 받아오고, 메인페이지 역할을 하는 Home.html 생성
15. Member -> Item -> Order 순으로 Controller 제작
15-1. Form관련해서 Entity를 직접사용하면 부가적인 정보를
받아올수 없으므로 MemberForm같이 class를 직접 제작하여 Form으로 사용
16. resource 폴더에 파일을 복붙해서 가져오면 파일을
인식못하는 오류가 발생하므로 복붙후 Syncronize를 해줘야함.  
17. domain,service,web까지 설계완료하고 추가적인 설계필요함.
18. 메시지 -> 검증 -> 오류 -> 인터셉터(로그, 인증) -> 예외처리 -> 타입컨버터 -> 파일업로드
순으로 진행할 예정임.
19. 메시지 진행시, File encoding 설정에서 전부 UTF-8로 설정해줘야 글씨 안깨짐.
20. 외부설정 파일에 `spring.messages.basename=messages,errors`로 설정
21. messages.properties 만든후, 기존 화면의 메시지들 통합 작성
22. 검증 진행시, bean validation을 진행할 예정이며, view들에 field-error 추가해줌.
23. 오류코드 관련해서, code+object+field를 조합해서 사용가능함.
23-1. bean validation의 오류코드는 NotEmpty,NotNull처럼 입력됨.
24. controller dir내에 form dir 따로 만들어서 validation에 사용할 form들 보관
25. String은 @NotBlank, Integer는 @NotNull
26. 에러코드는 th:errors에서 동작해서 메시지 출력
27. view에 field-error 작성  
````
<style>
    .field-error {
        border-color: #dc3545;
        color: #dc3545;
    }
</style>
````
28. view에서 error아닌 부분을 렌더링하기 위해 form도
ModelAttribute로 같이 전달해줘야함.
29. OrderForm에 bean validation적용하면서 기존에 RequestParam으로
전달되던 값들을 bean으로 묶어주었고, 셀렉트박스에도 bean validation을
추가로 적용하였음.
30. 인터셉터를 구현하기 전에 LoginService(MemberService 내) 및 Member Entity에
password,memberId를 추가해줌. View에도 password관련해서 추가해줌.
31. Home.html에 로그인 버튼이랑 Controller에 로그인,로그아웃 메서드 추가
32. Home화면을 비로그인, 로그인으로 분리해줌.
33. memberController내에 Login기능을 넣을것인가?
34. @NotNull같은 애들은 생성자로 생성할때는 동작안함
35. LoginCOntroller의 login메서드는 @RequestParam을 통해
기존 화면의 주소를 받을 수 있어야 로그인 후 해당 화면으로 바로
보내줄 수 있음.
36. 로그인 실패같이 필드에러가 아니면 ObjectError로 처리해줘야함.
또한 해당 ObjectError는 컨트롤러에 로직을 직접 구현해야 하고, bindingResult에
reject를 통해 직접 에러를 넣어줘야함.
37. request.getSession을 통해 session을 받아와야 하므로,
SessionConst class처럼 전역변수용 class를 생성함.
38. .filter는 Optional에만 동작?
39. typeMistach 에러코드 적어줘야함.
40. HomeController에 ArgumentResolver사용안함.
41. a태그나 redirect는 get방식
42. tracking mode cookie만 설정
43. web dir생성하고 controller dir넣어주고 intercepter dir 생성
44. 인터셉터 구현시작(LoginCheckIntercepter, LogIntercepter)
45. createLoginForm에서 th:action=@{~} 대신 action=~ th:action을 써주니
redirectURL문제가 해결됨. 아마 th:action적용시 redirectURL이 없는
/login으로 가기 때문에 th:action만 적어서 자기자신의 경로를
받게 설정해줘서 해결된듯함.  
46. 예외처리 시작

