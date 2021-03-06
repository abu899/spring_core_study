# Spring Core

## 순서
- 핵심 원리
  - [객체 지향 설계](README.md)
  - [Pure-java](core/src/main/java/hello/purejava/README.md)
- 스프링 핵심 기능
  - [Container, Bean](core/src/main/java/hello/spring/bean/README.md)
  - [Singleton](core/src/main/java/hello/spring/singleton/README.md)
  - [ComponentScan](core/src/main/java/hello/spring/componentscan/README.md)
  - [Dependency Injection](core/src/main/java/hello/spring/autowired/README.md)
  - [Bean Lifecycle](core/src/main/java/hello/spring/lifecycle/README.md)
  - [Bean Scope](core/src/main/java/hello/spring/beanscope/README.md)

## Spring Framework

- 핵심 기술 : 스프링 DI 컨테이너, AOP, 이벤트, 기타
- 웹 기술: 스프링 MVC, 스프링 WebFlux
- 데이터 접근 기술: 트랜잭션, JDBC, ORM 지원, XML 지원
- 기술 통합: 캐시, 이메일, 원격접근, 스케쥴링
- 테스트: 스프링 기반 테스트 지원
- 언어: 코틀린, 그루비
- 최근에는 스프링 부트를 통해서 스프링 프레임워크의 기술을 편리하게 사용 한다.

## Spring Boot

- 스프링을 편리하게 사용할 수 있도록 지원, 최근에는 기본으로 사용
- 단독으로 실행할 수 있는 스프링 어플리케이션을 쉽게 생성 가능하다
  - 어플리케이션 세팅에 들어가는 시간을 줄일 수 있다.
- Tomcat 같은 웹 서버를 내장하고 있어서 별도의 웹 서버를 설치하지 않아도 된다
- 손쉬운 빌드 구성을 위한 starter 종속성을 제공한다
- 스프링과 써드파티 라이브러리 자동 구성
  - 외부 라이브러리의 호환성을 신경쓰지 않아도 된다
- 메트릭, 상태 확인, 외부 구성과 같은 프로덕션 준비 기능을 제공
  - 모니터링이 중요하니까
- 관례에 의한 간결한 설정
  - 디폴트 설정을 주로 활용하고 필요할 때 커스텀이 가능하다

## 왜 만들었을까?

### 스프링의 핵심은

- 스프링은 자바 언어 기반의 프레임워크
- 스프링은 객체 지향언어가 가진 특징을 살려내는 프레임워크
- 즉, 좋은 객체 지향 어플리케이션을 개발할 수 있도록 도와주는 프레임워크

### 객체 지향 중 다형성

####장점
- 클라이언트(실제 구현대상)는 대상의 역할(인터페이스)만 알면 된다.
- 클라이언트는 구현 대상의 내부 구조를 몰라도 된다.
- 클라이언트는 구현 대상의 내부 구조가 변경되어도 영향을 받지 않는다.

####자바 언어의 다형성을 활용
- 역할: 인터페이스
- 구현: 인터페이스를 구현한 클래스, 구현 객체
- 객체를 설계할 때 역할과 구현을 명확히 분리
- 객체 설계시 역할을 먼저 부여하고, 그 역할을 수행하는 구현 객체 만들기

####다형성의 본질
- 인터페이스를 구현한 객체 인스턴스를 실행 시점에 유연하게 변경할 수 있다
- **클라이언트를 변경하지않고, 서버의 구현 기능을 유연하게 변경할 수 있다** 
- 즉, 인터페이스를 안정적으로 잘 설계하는 것이 제일 중요하다

####한계
- 인터페이스 자체가 변하면, 글라이언트, 서버 모두에 큰 변경이 발생한다

### 좋은 객체 지향 설계의 5가지 원칙 (클린 코드)

#### Single Responsibility Principle(SRP, 단일 책임 원칙)

- 하나의 클래스, 하나의 책임
- 하지만 하나의 책임이라는게 굉장히 모호하다
  - 책임이 클수도 작을 수도 있다
  - 문맥과 상황에 따라 다를 수 있다
- 가장 중요한 판단 기준은 **변경**!
  - 변경이 있을 때 파급 효과가 적으면 SRP를 잘 따른 것이다
- ex)UI 변경, 객체의 생성과 사용을 분리
> 책임의 범위를 잘 조절해야하는게 중요하다  
> 　범위를 너무 크게하면 책임이 너무 많아진다.  
> 　범위를 너무 작게하면 기능이 너무 잘게 쪼개진다.

#### Open Closed Principle(OCP, 개방 폐쇄 원칙) - 중요

- 확장에 Open, 변경에는 Close
- 다형성을 활용해보자
- 인터페이스를 구현한 새로운 클래스를 하나 만들어서 새로운 기능 구현
- 역할과 구현의 분리

##### 문제점

- 구현 객체를 변경하려면 클라이언트 코드를 변경해야 한다
- 분명 다형성을 사용하지만 OCP 원칙을 지킬 수 없다.
- 그렇기에 객체를 생성하고, 연관관계를 맺어주는 별도의 조립, 설정자가 필요하다.
- 이 별도의 무언가가 스프링 컨테이너

#### Liskov Substitution principle(LSP, 리스코프 치환 원칙)

- 다형성을 지원하기 위한 원칙
- 프로그램의 정확성을 깨뜨리지 않고, 하위 타입의 인스턴스로 바꿀 수 있어야한다
- ex) 자동차 인터페이스의 엑셀은 앞으로 가는 기능, 뒤로가게 구현하면 LSP 위반

#### Interface Segregation Principle(ISP, 인터페이스 분리 원칙)

- 특정 클라이언트를 위한 인터페이스 여러 개가 범용 인터페이스 하나보다 낫다
- 자동차 인터페이스
  - 운전 인터페이스
  - 정비 인터페이스
- 사용자 클라이언트
  - 운전자 클라이언트
  - 정비사 클라이언트
- 분리하게 되면 하나의 인터페이스가 변경되다 다른 인터페이스에 영향을 주지 않는다
- 인터페이스가 명확해지고 대체 가능성이 높아진다

#### Dependency Inversion Principle(DIP, 의존관계 역전 원칙) - 중요

- 추상화(인터페이스)에 의존하고 구체화(구현)에 의존하면 안된다
- 클라이언트는 인터페이스를 바라봐야하고, 구현에 의존하면 변경이 어려워진다
- 구현을 모르게 해야한다
```java
class MemberService{
    //...
  MemberRepository m = new MemoryMemberRepository(); // DIP 위반
}
```

### 객체지향과 스프링

스프링은 다음 기술로 다형성 + OCP, DIP를 가능하게 지원해준다.
- Dependency Injection(DI)
  - 의존관계, 의존성 주입
- DI 컨테이너 제공
- 클라이언트 코드의 변경 없이 기능을 확장할 수 있게 도와준다.

### 정리

- 객체 지향의 핵심은 다형성이지만 다형성 만으로는 OCP와 DIP를 지킬 수 없다.
- 뭔가가 더 필요하다(스프링 등장)
- 이상적으로는 모든 설계에 인터페이스를 부여하자

실무에서의 고민
- 인터페이스를 도입하면 추상화라는 비용이 발생한다
  - 인터페이스는 실제 구현이 없기 때문에 실제 내용을 보기 위해선 코드를 찾아 들어가야함
- 따라서 기능을 확장할 가능성이 없다면 구체 클래스를 직접 사용하고
- 향후 꼭 필요할 때 리팩토링을 통해 인터페이스를 도입하는 것도 하나의 방법

---

## 제어의 역전 (Inversion of Control, IoC)

- 기존 프로그램은 클라이언트 구현 객체가 프로그램의 제어 흐름을 스스로 조종. 즉, 생성, 연결, 실행을 모두 담당.
- AppConfig 등장 이후, 구현 객체는 제어권을 가지지 않고 실행만 담당하면 되게 바뀜.
- 프로그램에 대한 제어 흐름 권한은 AppConfig가 생성하고 조종한다.
- 이렇듯 **제어 흐름을 직접 제어하는 것이 아닌, 외부에서 관리**하는 것이 제어의 역전

> 프레임워크 vs 라이브러리
> 내가 작성한 코드가
> 프레임워크: 외부에서 제어되고 대신 실행하는 것 (JUnit)
> 라이브러리: 직접 제어의 흐름을 담당하는 것

## 의존 관계 주입(Dependency Injection, DI)

　OrderServiceImpl은 DiscountPolicy 인터페이스에 의존하지만, 실제로 어떤 discount policy가 실행될지는 모른다.
의존관계는 분리해서 생각해야 한다
- 정적인 클래스 의존관계
  - import 만으로도 의존관계를 파악할 수 있다. 즉, 실행하지 않고도 파악할 수 있다.
  - 그렇지만 OrderServiceImpl에 어떤 구현이 올지는 알 수 없다.
  - 역할에만 의존하고 있기 때문에
- 실행 시점에 결정되는 동적인 객체(인스턴스) 의존관계
  - 실행 시점에 실제 생성된 객체 인스턴스의 참조가 연결된 의존관계

　정적인 클래스 의존관계까 아닌 **실행 시점**에서 실제 의존관계가 연결되는 것을 의존관계 주입이라 한다.
객체 인스턴스를 생성하고 참조값을 전달해 연결하며, **정적인 클래스 의존관계의 변경없이** 동적인 객체 인스턴스 의존관계를 변경할 수 있다.

## IoC 컨테이너, DI 컨테이너

　AppConfig처럼 객체를 생성하고 관리하며 의존관계를 연결해주는 것을 IoC컨테이너 또는 DI컨테이너라 한다.
스프링이 DI 컨테이너 역할을 해준다.

## 느낀점 및 정리

　IoC와 DI를 통해 DIP와 OCP를 지킬 수 있는 방법을 진행해봤다.
- 클라이언트 객체에는 실행만을 담당시키고 AppConfig와 같은 구성객체를 만들어 실제 구현객체의 생성과 연결을 담당시켜야한다
- 실행객체는 변화시키지 않고 인터페이스만을 활용한 의존관계를 수립시켜 정적인 클래스 의존관계는 변화시키지 않도록 하자.
- 실제 변화는 구성객체 내에서 인스턴스의 참조를 통해 진행해보자
- OCP는 모든 코드에 대한 수정이 닫힌 것이라고 볼 수는 없을것 같고 실행 객체에 한정되서 생각해봐야할 것 같다.
- 즉, 구성객체는 외부 요인으로 설정하고, 구성객체 수정을 통한 인스턴스 참조의 수정을 제외하고 
내부 실행 코드라 생각되는 실행 객체에 대한 수정에 대해 닫히게 해야될 것 같다.


---

## 스프링 컨테이너

　ApplicationContext를 스프링 컨테이너라고 한다. 이전에는 AppConfig를 직접 직접 인스턴스화하고 DI를 했지만,
이제는 스프링 컨테이너로 그 작업을 대체할 수 있다.
- @Configuration annotation이 붙으면 하위 @Bean annotation이 붙은 모든 메서드들을 호출 후, 스프링 컨테이너에
key, value 형식을 가진 map 형태로 등록한다.
- 스프링 컨테이너에 등록된 객체를 스프링 빈(Spring bean)이라고 한다.
- 스프링 컨테이너는 Bean이 붙은 method 이름을 스프링 빈 이름으로 사용(default)
- getBean method를 사용해 찾을 수 있음.