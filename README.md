# Spring Boot & JPA

<br>

## 엔티티 설계

- 엔티티는 Setter 사용에 주의한다.

  > 유지 보수에 어려움을 겪을 수 있기 때문

- 연관관계는 지연 로딩으로 설정한다.

  > 즉시로딩('EAGER')은 예측이 힘들고, SQL 실행 시 추적이 어렵다. JPQL 실행 시 N+1 문제 발생
  >
  > 실무에서 모든 연관관계는 지연로딩('LAZY')로 설정
  >
  > @OneToOne, @ManyToOne 관계는 베이스가 즉시이므로 직접 지연로딩 설정이 필요함

- 컬렉션은 필드에서 초기화

  > 생성자에서 초기화하지 않는 이유는 안전함을 위함
  >
  > null 문제를 피할 수 있다.

<br>

## 아키텍처

- Controller : 웹 계층
- Service : 비즈니스 로직, 트랜잭션 처리
- Repository : JPA 사용 계층, 엔티티 매니저 사용
- Domain : 엔티티 계층, 모든 다른 계층에서 사용

<br>

## @Autowired

권장 방식은 생성자 인젝션 주입 방식을 사용한다.

```java
public class MemberService {
    private final MemberRepository memberRepository;

    @Autowired
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }
}
```

> 최근 스프링 버전은 `@Autowired`가 없어도 자동으로 인젝션 주입을 해준다.

final을 통해 생성자 주입의 실수를 방지

<br>

```java
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
}
```

`@RequiredArgsConstructor`를 사용하면 final로 생성된 필드로 생성자를 자동 생성시켜주므로 간편해진다.

<br>

## Test

`테스트`에 대한 설정은 `로컬/운영`과 따로 가져가는게 좋다.

test 내 resource 디렉토리를 생성 후 yml을 따로 관리하여 test DB를 따로 돌릴 수 있다.

<br>

Exception 처리되는 test 메서드 같은 경우 `@Test(expected = IllegalStateException.class)`와 같이 expected로 원하는 exception을 지정해줄 수 있다.

<br>