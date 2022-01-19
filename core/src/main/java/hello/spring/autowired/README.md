# AutoWired

## 의존관계 주입 방법

1. 생성자 주입
    - 생성자를 통해 의존관계를 주입 받는 방법
    - 기존 예제들이 생성자를 통해 의존관계를 주입했던 방법이다
    - 생성자에 @Autowired가 되어있어 호출시점에 1번만 호출되는걸 보장한다
    - **불변하고 필수적인 의존관계**에 사용된다.
    - 참고로 생성자가 하나만 존재한다면 자동으로 Autowired가 된다

```java

@Component
public class MemberServiceImpl implements MemberService {
    private final MemberRepository memberRepository;

    @Autowired
    public MemberServiceImpl(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }
}
```

2. 수정자 주입 (setter 주입)
    - setter라는 필드의 값을 변경할 수 있는 메소드가 존재
    - 스프링이 빈을 생성하는 단계와 의존관계를 주입하는 단계가 나눠져있는데, 의존관계를 주입하는 단계에 동작한다
    - **선택적이고 변경 가능성이 있는 의존관계**에 사용된다.
    - 기본적으로 주입할 대상이 빈에 없다면 오류가 뜨지만, @Autowired(required = false)를 지정하면 추후에 주입 가능하다.

```java

@Component
public class MemberServiceImpl implements MemberService {
    private MemberRepository memberRepository;

    @Autowired
    public setMemberRepository(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }
}
```

3. 필드 주입
    - 필드에 바로 주입하는 방법
    - 외부에서 변경이 불가능하기에 테스트하기 어렵다는 단점이 존재...
    - 사용하지 말자. 단, 비지니스 로직이 아닌 테스트코드나 @Configuration 정도에서는 사용 가능.

```java

@Component
public class MemberServiceImpl implements MemberService {
    @Autowired
    private MemberRepository memberRepository;
}
```

4. 일반 메서드 주입
    - 일반 메서드를 통해서 주입받는 방법(setter가 아닌)
    - 한번에 여러개의 필드를 주입받을 수 있다.

```java

@Component
public class MemberServiceImpl implements MemberService {
    private MemberRepository memberRepository;
    private DiscountPolicy discountPolicy;

    @Autowired

    public void init(MemberRepository memberRepository, DiscountPolicy discountPolicy) {
        this.memberRepository = memberRepository;
        this.discountPolicy = discountPolicy;
    }
}
```

## 옵션 처리

만약 주입할 스프링 빈이 없는데 동작시켜야 된다면?  
따라서 자동 주입 대상을 옵션으로 처리하는 방법이 존재한다.

- @Autowired(required = false)
    - 자동 주입할 대상이 없으면 호출 자체가 안된다
- org.springframework.lang.@Nullable
    - 자동 주입할 대상이 없으면 null이 입력된다
- Optional<>
    - 자동 주입할 대상이 없으면 Optional.empty가 입력된다

## 생성자 주입을 선택하자!

- 현재 스프링 포함 DI framework들은 생성자 주입을 권장한다.
- 불변성
    - 대부분 의존관계 주입은 한번 일어나면 종료까지 변경할 일이 거의 없음
    - 또한 setter 주입을 사용하게되면, setter가 public으로 노출되기 때문에 의도치 않는 변경의 위험이 존재한다
- 누락의 위험성이 줄어듬
    - setter 주입을 사용하게되면, 누락되는 의존성이 존재할 수 있다.
    - 또한 생성자 주입은 final로 선언되었기에, 생성자에서 무조건 의존성을 주입해줘야 컴파일이 되기에 누락의 위험이 없다.
    - 생성자 주입 이외에 주입방법은 final을 사용할 수 없다
- 기본으로 생성자 주입을 사용하고, 필수가 아닌 값에 대해서만 수정자 주입을 사용하면 둘다 사용가능하다
- 필드 주입은 사용하지 말자!

### Lombok

생성자 주입을 대부분 사용하기 때문에, 대부분 불변이고 따라서 대부분 final을 사용하게 된다. 이를 보다 편리하게 사용하기 위해 Lombok과 함께 사용할 수 있다.

- RequiredArgsConstructor
    - 생성자 주입에서 final이 붙은 필드들에 대해 코드 입력없이 생성자를 만들어준다

최근에는 생성자를 하나만 두고 Autowired를 생략하는 방법을 많이 사용한다. 또한 거기에 Lombok의 RequiredArgsConstructor를 이용하여 보다 깔끔하게 코드를 정리하는 추세이다.

```java

@Component
public class OrderServiceImpl implements OrderService { // 클라이언트 객체 ( 실행만 담당)
    private final MemberRepository memberRepository;
    private final DiscountPolicy discountPolicy;

    @Autowired
    public OrderServiceImpl(MemberRepository memberRepository, DiscountPolicy discountPolicy) {
        this.memberRepository = memberRepository;
        this.discountPolicy = discountPolicy;
    }
}
```

```java

@Component
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService { // 클라이언트 객체 ( 실행만 담당)
    private final MemberRepository memberRepository;
    private final DiscountPolicy discountPolicy;
}
```

## 조회 빈이 2개 이상인 경우

Autowired는 기본적으로 type으로 조회하게된다.  
따라서 purejava package의 DiscountPolicy의 경우 ac.getBean(DiscountPolicy.class)처럼 동작하게 된다. 그런데 만약 FixDiscountPolicy와
RateDiscountPolicy 모두 Component로 지정된다면 어떻게 될까?

- NoUniqueBeanDefinitionException 발생!
- 이런 경우 하위타입 지정이 가능하지만, 구현을 지정하는 것은 유연성이 떨어진다 의존 관계 주입에서는 어떻게 이 문제를 해결할 수 있을까?

## 여러 Bean이 조회되었을 때

1. @Autowired에 필드명 매칭
    - Autowired는 처음에는 타입 매칭으로 진행된다.
    - 하지만 여러 개의 빈이 조회되면 **필드 이름, 파라미터 이름을 이용해 추가 매칭**을 진행한다

```text
@Autowired
DiscountPolicy discountPolicy;
// Fixed와 Rate 두개의 빈이 조회된다 
```

```text
@Autowired
DiscountPolicy rateDiscountPolicy;
// 두 개의 빈이 조회되며, rateDiscountPolicy라는 이름으로 추가 매칭 진행
```

2. @Qualifier끼리 매칭
    - 추가 구분자
    - 같은 Qualifier끼리 매칭
    - 빈 이름을 변경하는 것은 아니고, **구분할수 있는 추가적인 방법**을 제공하는 것
    - 만약, Qualifier로 내가 지정한 qualifier를 찾지못한다면, qualifier로 입력받은 값 자체를 스프링 빈에서 찾아본다
    - Qualifier를 주입받는 곳에도 적어줘야하기에 가독성이 떨어짐

```java

@Component
@Qualifier("mainDiscountPolicy")
public class RateDiscountPolicy implements DiscountPolicy {
}
```

```java

@Component
@Qualifier("fixDiscountPolicy")
public class FixDiscountPolicy implements DiscountPolicy {
}
```

```java
public class OrderServiceImpl implements OrderService {
    public OrderServiceImpl(MemberRepository memberRepository,
                            @Qualifier("mainDiscountPolicy") DiscountPolicy discountPolicy) {
        //..
    }
}
```

3. @Primary 사용
    - 우선순위를 정하는 것!
    - 여러가지가 매칭이 되면 Primary annotation이 붙은 빈이 우선권을 가진다!

```java
@Component
@Primary
public class RateDiscountPolicy implements DiscountPolicy {
}
```

> Primary와 Qualifier의 우선순위  
> Qualifier는 수동, Primary는 자동인데 보통 스프링에서는 수동, 즉 Qualifier가 우선순위를 가진다

## Custom Annotation
　우리가 앞서 Qualifier를 이용해 다수의 빈 중 원하는 빈을 주입하는 방법을 봤는데,
Qualifier("mainDiscountPolicy") 처럼 안에 string 문자가 입력되는 경우 컴파일 타임에 타입을 체크할 수 없다.
 따라서, 이런 경우 직접 annotation을 만들어 문제를 해결할 수 있다.
```java

@Component
@Qualifier("mainDiscountPolicy")
public class RateDiscountPolicy implements DiscountPolicy {
}
```
```java
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
@Qualifier("mainDiscountPolicy")
public @interface MainDiscountPolicy {
}
```
```java
@Component
@MainDiscountPolicy
public class RateDiscountPolicy implements DiscountPolicy{
}
```

```java
public class OrderServiceImpl implements OrderService {
    public OrderServiceImpl(MemberRepository memberRepository,
                            @MainDiscountPolicy DiscountPolicy discountPolicy) {
        //..
    }
}
```
　참고로 Annotation은 상속의 개념이 없고, 이렇게 여러개의 annotation을 상속처럼 사용하는 것은 스프링이 지원해주는 것이다.
따라서 다른 annotation들도 조합해서 사용가능하지만, 목적성을 확실히하고 사용해야한다.

## 조회한 빈이 모두 필요할 때는?
　경우에 따라서는 조회된 빈들이 모두 필요한 경우가 있다.
이럴땐 List와 Map을 이용하여 빈들을 받아들이고, 원하는 빈을 선택하여 전략적으로 적용이 가능하다. (AllBeanTest.java)

## 자동과 수동주입 실무 기준

### 편리한 자동 주입 기능을 기본적으로
　요즘 추세는 점점 자동을 선호하는 추세이다. 실제 스프링과 스프링부트에서는 계층에 맞춰 어플리케이션 로직을 자동으로
 스캔할 수 있게 지원하고 있다. 일단 빈을 등록할 때 ComponentScan 하나면 간편한 점도 있고 또한 객체를 생성하고 주입대상을 일일히 적어주는 것은 상당히 번거롭고, 관리할 대상이
 많아지면 실수할 여지가 있다. 
 
### 그렇다면 언제 수동 빈 등록을 사용할까?
- 업무 로직 Bean
  - 컨트롤러, 핵심 비지니스 로직의 서비스, 데이터 계층을 처리하는 repository
  - 비지니스 요구사항을 개발할 때 추가되거나 변경되는 곳
  - 빈의 숫자가 굉장히 많고 유사한 패턴이 존재한다
  - **자동기능**을 적극 사용 권장
- 기술 지원 Bean
  - 기술적인 문제나 공통 관심사(AOP) 처리시 사용되는 곳
  - 업무로직을 지원하기 위한 하부 기술
  - 빈의 숫자가 상대적으로 적고 어플리케이션 전반에 영향을 미친다
  - 문제가 발생했을시 파악하기 어렵기 때문에 **수동 빈 등록**을 통해 명확하게 하는게 좋다!
  - 즉 어플리케이션에 광범위하게 영향을 미치는 기술은 수동 빈 등록!

　단 비지니스 로직 중에 **다형성**을 활용해야하는 경우에는 수동 빈 등록이 좋다!
DiscountPolicy같이 다형성을 사용해야하는 경우 Config를 통해 묶어두거나 한 package에 넣어둠으로써 한 눈에 파악하기 좋게하자!

```java
@Configuration
public class DiscountPolicyConfig {
    @Bean
    public DiscountPolicy rateDiscountPolicy() {
        return new RateDiscountPolicy();
    }

    @Bean
    public DiscountPolicy fixDiscountPolicy() {
        return new FixDiscountPolicy();
    }
}
```