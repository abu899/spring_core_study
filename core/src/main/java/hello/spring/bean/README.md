# Spring Container and Bean

## Spring Container

**ApplicationContext**를 스프링 컨테이너라고 하며 인터페이스 형태를 취하고 있다.
우리가 MemberApp에서 사용해본 **AnnotationConfigApplicationContext**는 Annotation기반의 자바 설정 클래스로 만든 것이다.
ApplicationContext에는 Annotation, XML 등의 설정 클래스등 여러가지가 존재한다.

> 스프링 컨테이는 BeanFactory와 ApplicationContext로 구분할 수 있지만,
> BeanFactory를 직접 사용하는 경우는 거의 없기에 일반적으로 ApplicationContext를 스프링 컨테이너라고 한다.

## Spring container and bean

1. Spring container 생성
   - new AnnotationConfigApplicationContext(AppConfig.class)
   - AnnotationContext를 만들어주고 구성 정보를 전달해 준다.
2. Spring bean 등록
   - Spring container 파라미터로 넘어온 class 정보를 이용해 내부의 bean을 등록한다.
   - Bean의 이름은 메서드 이름과 같으며, 중복되지 않게 부여해야한다(물론 직접 이름을 부여할 수도 있음)
3. Spring bean 의존관계 준비 및 설정
   - 등록된 bean 정보를 토대로 의존관계를 주입(DI)한다.
    
> 스프링은 bean을 생성하고 의존관계를 주입하는 단계가 나눠져 있다.  
> 우리가 만든 AppConfig의 경우 자바 코드로 호출하는 형태로 빈을 등록하기 위해
> 함수를 실행하면, 엮여있는 다른 함수 또한 수행하게 되며 의존관계 또한 한번에 주입된다.

## BeanFactory and ApplicationContext

- BeanFactory
  - 스프링 컨테이너의 최상위 인터페이스!
  - Bean을 관리하고 조회하는 역할(getBean 등)
- ApplicationContext
  - BeanFactory의 기능을 상속받아 만들어진 인터페이스!
  - Bean을 관리하고 조회하는 기능이외에 많은 부가기능이 추가되어 있다.
  - BeanFactory 뿐만아니라 여러가지 인터페이스를 상속받아 만들어져있다.
    - Environment
      - 개발환경, 로컬환경 등으로 분리해서 처리 
    - MessageSource
      - Localization 기능 제공
    - EventPublisher
    - ResourceLoader
      - 리소스 조회를 편리하게 해주는 기능

## Bean Definition, 스프링 빈 설정 메타 정보
　스프링은 어떻게 XML이나 자바코드 등의 다양한 구성정보 설정 방식을 지원할까?
이것 역시 **역할과 구현**을 개념적으로 나눠서 적용한 것이다.
즉, XML이든 자바코드든 이를 읽어서 **BeanDefinition**이라는 형태로 만들고,
스프링 컨테이너는 **설정 방식에 관계없이** BeanDefinition을 사용하는 방식이다.

　BeanDefinition을 설정 메타정보라고 하며, @Bean annotation 하나당 하나의 메타정보가 생성된다.
스프링 컨테이너는 이 메타정보를 기반으로 스프링 빈을 생성하는 구조이다.
- AnnotationConfigApplicationContext 내부를 살펴보면 **AnnotatedBeanDefinitionReader**라는게 존재한다
- 다른 ApplicationContext에도 reader가 존재하고, reader를 통해 BeanDefiniton을 생성한다.

