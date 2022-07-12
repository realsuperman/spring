package hello.core.autowired;

import hello.core.member.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.lang.Nullable;

import java.util.Optional;

public class AutowiredTest {
    @Test
    void AutowiredOption(){
        ApplicationContext ac = new AnnotationConfigApplicationContext(TestBean.class);
    }

    static class TestBean{
        @Autowired(required = false)
        public void setNoBean1(Member member){
            System.out.println(member);
        }

        @Autowired
        public void setNoBean2(@Nullable Member member){
            System.out.println(member);
        }

        @Autowired
        public void setNoBean3(Optional<Member> member){
            System.out.println(member);
        }
    }
}

/*
    required=false를 하면 인젝션할 대상이 없으면 메소드 자체가 호출이 안됨
    나머지 케이스의 경우 호출은 되지만 값이 없는 경우 null로 들어감
 */
