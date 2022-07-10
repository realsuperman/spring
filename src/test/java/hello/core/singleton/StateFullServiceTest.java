package hello.core.singleton;

import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class StateFullServiceTest {
    @Test
    void stateFullServiceSingleton(){
        ApplicationContext ac = new AnnotationConfigApplicationContext(TestConfig.class);
        StateFullService stateFullService1 = ac.getBean(StateFullService.class);
        StateFullService stateFullService2 = ac.getBean(StateFullService.class);

        //Thread A에서 10000원 주문
        stateFullService1.order("userA",10000);
        //Thread B에서 20000원 주문
        stateFullService1.order("userB",20000);
        //Thread A : 사용자A 주문 금액 조회
        int price =    stateFullService1.getPrice();
        System.out.println(price);
        assertThat(stateFullService1.getPrice()).isEqualTo(20000);
    }

    static class TestConfig{
        @Bean
        public StateFullService stateFulService(){
            return new StateFullService();
        }
    }
}