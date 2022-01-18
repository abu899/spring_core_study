package spring.autowired;

import hello.purejava.member.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.lang.Nullable;

import java.util.Optional;

public class AutowiredTest {

    @Test
    void autoWiredOption(){
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(TestBean.class);
    }

    static class TestBean {

        @Autowired(required = false)
        public void setNoBeanWithRequiredFalse(Member member) {
            System.out.println("no bean with Required false member = " + member);
        }

        @Autowired
        public void setNoBeanWithNullable(@Nullable Member member) {
            System.out.println("no bean with Nullable member = " + member);
        }

        @Autowired
        public void setNoBeanWithOptional(Optional<Member> member) {
            System.out.println("no bean with Optional member = " + member);
        }
    }
}
