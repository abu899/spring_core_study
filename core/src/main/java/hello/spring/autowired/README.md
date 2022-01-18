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
   public setMemberRepository(MemberRepository memberRepository){
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
　생성자 주입을 대부분 사용하기 때문에, 대부분 불변이고 따라서 대부분 final을 사용하게 된다.
이를 보다 편리하게 사용하기 위해 Lombok과 함께 사용할 수 있다.
- RequiredArgsConstructor
  - 생성자 주입에서 final이 붙은 필드들에 대해 코드 입력없이 생성자를 만들어준다

　최근에는 생성자를 하나만 두고 Autowired를 생략하는 방법을 많이 사용한다. 또한 거기에 Lombok의 RequiredArgsConstructor를
이용하여 보다 깔끔하게 코드를 정리하는 추세이다.
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

