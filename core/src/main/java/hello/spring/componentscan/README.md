# ComponentScan과 Autowired

　등록해야할 Bean의 갯수가 늘어날 수록 이를 일일히 등록하기 어렵고, 설정정보 또한 커지게 되면
이를 누락할 위험이 있다. 그래서 스프링은 설정정보가 없어도 자동으로 스프링 bean을 등록하는
컴포넌트 스캔(ComponentScan)이라는 기능을 제공한다. 또한 의존관계 자동 주입을 위한 Autowired 기능도 제공한다.

## 사용법
　구성 정보에 **@ComponentScan**를 붙여준다.
ComponentScan은 **@Component** annotation이 붙은 클래스를 스캔해서 Bean으로 등록해준다.
따라서, Bean으로 등록할 클래스들에 @Component를 붙여주자  

　그럼 의존관계 주입은 어떻게 해줘야할까?
@ComponentScan을 선언한 클래스에서 생성자 주입을 해줘야하기 때문에 생성자에 **@Autowired**를 선언해줘서
 자동으로 의존관계가 설정되게 만들어 준다.
 
## @ComponentScan and @Autowired

　ComponentScan은 @Component가 붙은 모든 클래스를 스프링 빈에 등록한다.
스프링 빈의 이름은 클래스명을 따라가고 맨 앞자리만 소문자로 변경된다.  

　Autowired를 지정하면 ComponentScan으로 저장된 스프링 빈을 찾아서 의존성을 주입한다.
기본적으로 같은 타입의 클래스의 빈을 찾아서 주입한다.

## 탐색 위치의 지정

　ComponentScan은 탐색할 위치를 basePackages로 지정할 수 있다. 
- basePackages 
  - 이 패키지를 포함 하위 패키지를 모두 탐색
  - '{ , }'를 통해 여러 시작위치 지정도 가능
- basePackageClasses
  - 지정한 클래스의 package로 하위 패키지를 탐색
- default
  - ComponentScan이 붙은 설정 정보의 클래스의 패키지가 시작위치(basePackageClasses = '현재 위치')

```java
import org.springframework.context.annotation.ComponentScan;
@ComponentScan(
        basePackages = "hello.purejava"
)
public class AppConfig(){
}
```
> 권장하는 방법  
> 　Package위치를 따로 저장하지 않고, 설정정보 클래스의 위치를 프로젝트 최상단에 두고 쓰는 방법

## ComponentScan의 기본대상

　@Component 뿐만아니라 다음 내용도 추가로 포함한다
- @Controller
  - 스프링 MVC controller에서 사용
- @Service
  - 스프링 비지니스 로직에서 사용
- @Repository
  - 스프링 데이터 접근 계층에서 사용
- @Configuration
  - 설정 구성 정보에 사용

## Filter

- includeFilter
  - ComponentScan의 대상을 추가로 지정
- excludeFilter
  - ComponentScan의 대상에서 제외

### Filter Option

- Annotation
  - Default 값
  - Annotation 인식
- ASSIGNABLE_TYPE
  - 지정한 타입과 그 자식타입을 인식
- ASPECTJ
  - AspectJ 패턴을 사용해서 인식
- REGEX
  - 정규 표현식
- CUSTOM
  - TypeFilter라는 인터페이스를 구현해서 처리

## 중복 등록

　만약 ComponentScan시, 이름이 같다면 어떻게 될까? 다음 두 가지 상황이 있을 수 있다.
1. 자동 빈 등록 vs 자동 빈 등록
   - BeanDefinitionStoreException 호출 
2. 수동 빈 등록 vs 자동 빈 등록
   - 된다(?)
   - 이때는 수동으로 등록한 빈이 우선적으로 등록된다(오버라이딩)
   - 하지만 최근 스프링부트에서는 이 경우에도 충돌 오류가 발생하도록 default가 됨