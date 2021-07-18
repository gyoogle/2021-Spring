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

좋은 테스트는 메서드마다 단일 테스트로 하는 것이 좋다.

<br>

## 비즈니스 로직

데이터를 가지고 있는 쪽에서 비즈니스 메서드를 가지고 있는 것이 응집력이 있다.

따라서 Entity 상에서 변경해야 할 일이 있으면 Setter를 사용하는 것이 아니라 Entity 안에 핵심 비즈니스 로직을 만들어 관리해야 한다.

> 다른 공간에서 관리하는 것보다 데이터를 지닌 Entity에서 관리해야 편하다.

<br>

## EntityManager persist & merge

```java
public void save(Item item) {
    if (item.getId() == null) {
        em.persist(item);
    } else {
        em.merge(item); // update와 유사
    }
}
```

- persist : 신규 등록

- merge : 이미 등록된 걸 가져옴 (update와 유사)

<br>

## 도메인 모델 패턴

도메인 엔티티에 핵심 비즈니스 로직을 모두 구현하고, 서비스에서는 호출 형식으로만 구현하는 것

단위테스트로 메서드만 테스트하도록 구현하기 용이하다.