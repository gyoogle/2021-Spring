# Spring 학습

<br>

### 스프링 동작 환경

<img src="https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FkyRmR%2Fbtq0Pb0TT5T%2FeHQeLobMIiT2rk3kIfMg30%2Fimg.png">

1. 웹 브라우저에서 url(localhost:8080/hello)을 보낸다.

2. url을 받은 내장 톰켓 서버는 맵핑되는 컨트롤러에게 넘겨준다 (→ helloController)

3. 맵핑된 컨트롤러에서 model에 데이터를 담아 return해준다.

4. 컨트롤러가 return으로 반환해주는 model을 viewResolver가 템플릿을 찾아서 뿌려준다.

5. 변환된 html을 다시 웹 브라우저에게 제공해준다.

<br>

<br>

#### 테스트 기본 패턴

```java
class MemberServiceTest {

    MemberService memberService;
    MemoryMemberRepository memberRepository;

    @BeforeEach
    public void beforeEach() {
        memberRepository = new MemoryMemberRepository();
        memberService = new MemberService(memberRepository);
    }

    @AfterEach
    public void afterEach() {
        memberRepository.clearStore();
    }

    @Test
    void join() {
        // given
        Member member = new Member();
        member.setName("spring");

        // when
        Long saveId = memberService.join(member);

        // then
        Member findMember = memberService.findOne(saveId).get();
        assertThat(member.getName()).isEqualTo(findMember.getName());
    }

    @Test
    void duplicate_user() {
        //given
        Member member1 = new Member();
        member1.setName("spring");

        Member member2 = new Member();
        member2.setName("spring");

        //when
        memberService.join(member1);
        IllegalStateException e = assertThrows(IllegalStateException.class, () -> memberService.join(member2));

        //then
        assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
    }
}
```

<br>

<br>

#### 스프링 정형화된 패턴

컨트롤러를 통해 외부 요청 받고, 서비스에서 비즈니스 로직을 만들고, 레포지토리에서 데이터를 저장한다.

<br>

#### 스프링 빈 등록 방법

1. ##### 컴포넌트 스캔과 자동 의존관계 설정 (@Service, @Controller, @Repository)

   어노테이션이 있으면, 자동으로 스프링 빈으로 등록된다.

<br>

`@SpringBootApplication` 파일의 하위 패키지를 돌면서 스프링 빈으로 등록한다. 따라서 여기에 포함되지 않은 파일들은 따로 설정하지 않는 이상 등록되지 않는다.

> 설정 방법 : @ComponentScan

<br>

스프링은 스프링 컨테이너에서 스프링 빈을 등록할 때, **싱글톤**(하나만 등록하고 공유)으로 등록한다.

> 같은 스프링 빈이면, 모두 같은 인스턴스를 사용한다.

<br>

2. ##### 자바 코드로 직접 스프링 빈 등록

   서비스와 레포지토리에서 @Service, @Repository, @Autowired 대신 직접 코드로 빈을 등록할 수도 있음

   ```java
   // 스프링 빈 직접 등록 방법
   @Configuration
   public class SpringConfig {
       
       @Bean
       public MemberService memberService() {
           return new MemberService(memberRepository());
       }
       
       @Bean
       public MemberRepository memberRepository() {
           return new MemoryMemberRepository();
       }
   }
   ```

   Controller에서 Service를 찾을 때, Config에서 등록된 빈을 통해 Repository까지 연결이 가능하다.

<br>

<br>

#### DI 종류

- 생성자 주입

  ```java
  @Autowired
  public MemberController(MemberService memberService) {
      this.memberService = memberService;
  }
  ```

  > 요즘 많이 추구하는 생성자로 주입하는 타입
  >
  > 의존관계 실행 중에는 동적으로 변하는 경우(서버가 Run 중인데 중간중간 바뀌는 것)는 거의 없기 때문

- 필드 주입

  ```java
  @Autowired private final MemberService memberService;
  ```

  > 중간에 바꿀 수 있는 방법이 없으므로 별로 좋지 않음

- Setter 주입

  ```java
  @Autowired
  public void setMemberService(MemberService memberService) {
      this.memberService = memberService;
  }
  ```

  > 누군가가 member 컨트롤러에 들어왔을 때 public으로 호출되므로 잘못 바뀔 때 문제가 생길 수 있다.

<br>

실무에서는 주로 정형화된 컨트롤러, 서비스, 레포지토리 같은 코드는 컴포넌트 스캔 사용함

만약 정형화되지 않거나, **상황에 따라 구현 클래스를 변경**해야 할 때는 스프링 빈으로 등록

(ex. MemoryMemberRepository를 나중에 DB를 연결해서 변경해야 할 때, 다른 파일은 손대지 않고 스프링 빈을 통해 변경이 가능하다.)

```java
// SpringConfig.java
@Bean
public MemberRepository memberRepository() {
    return new DBMemberRepository(); // 변경
}
```

<br>

`@Autowired`를 통한 DI를 진행했을 때는 스프링이 관리하는 객체에서만 동작함 (helloController, MemberService 등)

> 스프링 빈으로 등록하지 않고, 내가 직접 생성한 객체에선 동작 X

