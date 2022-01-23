package spring.lifecycle;

import hello.spring.lifecycle.NetworkClientWithAnnotationInitialize;
import hello.spring.lifecycle.NetworkClientWithBeanInitialize;
import hello.spring.lifecycle.NetworkClientWithInterfaceInitialize;
import org.junit.jupiter.api.Test;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

public class BeanLifeCycleTest {

    @Test
    void lifeCycleTest(){
        ConfigurableApplicationContext ac = new AnnotationConfigApplicationContext(LifeCycleConfig.class);
        NetworkClientWithInterfaceInitialize client = ac.getBean(NetworkClientWithInterfaceInitialize.class);
        NetworkClientWithBeanInitialize clientWithBeanInitialize = ac.getBean(NetworkClientWithBeanInitialize.class);
        NetworkClientWithAnnotationInitialize clientWithAnnotationInitialize = ac.getBean(NetworkClientWithAnnotationInitialize.class);
        ac.close();

    }

    @Configuration
    static class LifeCycleConfig {
        @Bean
        public NetworkClientWithInterfaceInitialize networkClientWithInterfaceInitialize(){
            NetworkClientWithInterfaceInitialize networkClient = new NetworkClientWithInterfaceInitialize();
            networkClient.setUrl("http://hello-world.dev");
            return networkClient;
        }

        @Bean(initMethod = "init", destroyMethod = "close")
        public NetworkClientWithBeanInitialize networkClientWithBeanInitialize(){
            NetworkClientWithBeanInitialize networkClient = new NetworkClientWithBeanInitialize();
            networkClient.setUrl("http://hello-world.dev");
            return networkClient;
        }

        @Bean
        public NetworkClientWithAnnotationInitialize networkClientWithAnnotationInitialize(){
            NetworkClientWithAnnotationInitialize networkClient = new NetworkClientWithAnnotationInitialize();
            networkClient.setUrl("http://hello-world.dev");
            return networkClient;
        }
    }
}
