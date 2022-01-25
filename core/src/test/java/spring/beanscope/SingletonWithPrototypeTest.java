package spring.beanscope;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Provider;

import static org.assertj.core.api.Assertions.*;

public class SingletonWithPrototypeTest {

    @Test
    void prototypeFind(){
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(PrototypeBean.class);

        PrototypeBean prototypeBean1 = ac.getBean(PrototypeBean.class);
        prototypeBean1.addCount();
        assertThat(prototypeBean1.getCount()).isEqualTo(1);
        PrototypeBean prototypeBean2 = ac.getBean(PrototypeBean.class);
        prototypeBean2.addCount();
        assertThat(prototypeBean2.getCount()).isEqualTo(1);
    }

    @Test
    void singletonClientUseProtoType() {
        AnnotationConfigApplicationContext ac =
                new AnnotationConfigApplicationContext(ClientSingletonBean.class, PrototypeBean.class);

        ClientSingletonBean clientSingletonBean1 = ac.getBean(ClientSingletonBean.class);
        int count1 = clientSingletonBean1.logic();
        assertThat(count1).isEqualTo(1);
        System.out.println("clientSingletonBean1.getPrototypeBean() = " + clientSingletonBean1.getPrototypeBean());
        ClientSingletonBean clientSingletonBean2 = ac.getBean(ClientSingletonBean.class);
        int count2 = clientSingletonBean2.logic();
        assertThat(count2).isEqualTo(2);
        System.out.println("clientSingletonBean2.getPrototypeBean() = " + clientSingletonBean2.getPrototypeBean());

        assertThat(clientSingletonBean1.getPrototypeBean()).isSameAs(clientSingletonBean2.getPrototypeBean());
    }

    @Test
    void twoSingletonClientHasDifferentPrototype(){
        AnnotationConfigApplicationContext ac =
                new AnnotationConfigApplicationContext(ClientSingletonBean.class, ClientSingletonBean2.class, PrototypeBean.class);

        ClientSingletonBean clientSingletonBean_a = ac.getBean(ClientSingletonBean.class);
        int count1 = clientSingletonBean_a.logic();
        assertThat(count1).isEqualTo(1);
        System.out.println("clientSingletonBean_a.getPrototypeBean() = " + clientSingletonBean_a.getPrototypeBean());
        ClientSingletonBean2 clientSingletonBean_b = ac.getBean(ClientSingletonBean2.class);
        int count2 = clientSingletonBean_b.logic();
        assertThat(count2).isEqualTo(1);
        System.out.println("clientSingletonBean_b.getPrototypeBean() = " + clientSingletonBean_b.getPrototypeBean());

        assertThat(clientSingletonBean_a.getPrototypeBean()).isNotSameAs(clientSingletonBean_b.getPrototypeBean());
    }

    @Test
    void providerTest(){
        AnnotationConfigApplicationContext ac =
                new AnnotationConfigApplicationContext(ClientSingletonBeanWithProvider.class, PrototypeBean.class);

        ClientSingletonBeanWithProvider clientSingletonBean_a = ac.getBean(ClientSingletonBeanWithProvider.class);
        int count1 = clientSingletonBean_a.logic();
        assertThat(count1).isEqualTo(1);
        ClientSingletonBeanWithProvider clientSingletonBean_b = ac.getBean(ClientSingletonBeanWithProvider.class);
        int count2 = clientSingletonBean_b.logic();
        assertThat(count2).isEqualTo(1);
    }

    @Test
    void javaxProviderTest(){
        AnnotationConfigApplicationContext ac =
                new AnnotationConfigApplicationContext(ClientSingletonBeanWithJavaxProvider.class, PrototypeBean.class);

        ClientSingletonBeanWithJavaxProvider clientSingletonBean_a = ac.getBean(ClientSingletonBeanWithJavaxProvider.class);
        int count1 = clientSingletonBean_a.logic();
        assertThat(count1).isEqualTo(1);
        ClientSingletonBeanWithJavaxProvider clientSingletonBean_b = ac.getBean(ClientSingletonBeanWithJavaxProvider.class);
        int count2 = clientSingletonBean_b.logic();
        assertThat(count2).isEqualTo(1);
    }

    @Scope("singleton")
    static class ClientSingletonBean{
        private final PrototypeBean prototypeBean; //생성시점에 주입되어 계속 같은걸 사용하게된다

        @Autowired
        public ClientSingletonBean(PrototypeBean prototypeBean){
            this.prototypeBean = prototypeBean;
        }

        public int logic(){
            prototypeBean.addCount();
            return prototypeBean.getCount();
        }

        //for test
        public PrototypeBean getPrototypeBean() {
            return prototypeBean;
        }
    }

    @Scope("singleton")
    static class ClientSingletonBean2{
        private final PrototypeBean prototypeBean; //생성시점에 주입되어 계속 같은걸 사용하게된다

        @Autowired
        public ClientSingletonBean2(PrototypeBean prototypeBean){
            this.prototypeBean = prototypeBean;
        }

        public int logic(){
            prototypeBean.addCount();
            return prototypeBean.getCount();
        }

        //for test
        public PrototypeBean getPrototypeBean() {
            return prototypeBean;
        }
    }

    @Scope("singleton")
    static class ClientSingletonBeanWithProvider{
        @Autowired
        ObjectProvider<PrototypeBean> prototypeBeanProvider;

        public int logic(){
            PrototypeBean prototypeBean = prototypeBeanProvider.getObject();
            prototypeBean.addCount();
            return prototypeBean.getCount();
        }
    }


    @Scope("singleton")
    static class ClientSingletonBeanWithJavaxProvider{
        @Autowired
        Provider<PrototypeBean> prototypeBeanProvider;

        public int logic(){
            PrototypeBean prototypeBean = prototypeBeanProvider.get();
            prototypeBean.addCount();
            return prototypeBean.getCount();
        }
    }
    @Scope("prototype")
    static class PrototypeBean {
        private int count = 0;

        public void addCount() {
            count++;
        }

        public int getCount() {
            return count;
        }

        @PostConstruct
        public void init(){
            System.out.println("PrototypeBean.init " + this);
        }

        @PreDestroy
        public void destroy(){
            System.out.println("PrototypeBean.destroy");
        }
    }



}
