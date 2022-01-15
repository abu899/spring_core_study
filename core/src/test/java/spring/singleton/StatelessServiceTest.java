package spring.singleton;

import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

import static org.assertj.core.api.Assertions.assertThat;

class StatelessServiceTest {
    @Test
    void statelessServiceSingleton() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(TestConfig.class);

        StatelessService statefulService1 = ac.getBean(StatelessService.class);
        StatelessService statefulService2 = ac.getBean(StatelessService.class);

        // Thread A
        int userAPrice = statefulService1.order("userA", 10000);
        // Thread B
        int userBPrice = statefulService2.order("userB", 20000);

        // Thread A: 사용자A 주문 금액 조회
        System.out.println("userAPrice = " + userAPrice);
        // Thread B: 사용자B 주문 금액 조회
        System.out.println("userAPrice = " + userBPrice);

        assertThat(userAPrice).isNotSameAs(userBPrice);
    }

    static class TestConfig {
        @Bean
        public StatelessService statelessService() {
            return new StatelessService();
        }
    }
}