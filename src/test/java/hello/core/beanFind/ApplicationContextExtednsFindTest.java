package hello.core.beanFind;

import hello.core.AppConfig;
import hello.core.discount.DiscountPolicy;
import hello.core.discount.FixDiscountPolicy;
import hello.core.discount.RateDiscountPolicy;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoUniqueBeanDefinitionException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ApplicationContextExtednsFindTest {
    AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(TestConfig.class);

    @Test
    @DisplayName("부모 타입으로 조회시 자식이 둘 이상 있으면 중복오류")
    void findBeanByParentTypeDuplicate(){
        /*
            클래스 구조상 DiscountPolicy를 RateDiscountPolicy와 FixDiscountPolicy가 구현을 하였으므로
            빈도 상속관계로 놓인다 즉, 부모타입인 DiscountPolicy로 조회시 중복 오류가 난다
            참고로 부모 타입으로 조회시 자식 타입도 함께 조회한다
         */
        assertThrows(NoUniqueBeanDefinitionException.class,()->ac.getBean(DiscountPolicy.class));
    }

    @Test
    @DisplayName("부모 타입으로 조회시 자식이 둘 이상 있으면 빈 이름으로 조회하면 된다")
    void findBeanByBeanName(){
        DiscountPolicy rateDiscountPolicy = ac.getBean("rateDiscountPolicy",DiscountPolicy.class);
        assertThat(rateDiscountPolicy).isInstanceOf(RateDiscountPolicy.class);
    }

    @Test
    @DisplayName("부모 타입으로 조회시 자식이 둘 이상 있으면 혹은 특정 자식 타입으로도 가능")
    void findBeanByBeanSubType(){
        DiscountPolicy rateDiscountPolicy = ac.getBean(RateDiscountPolicy.class); // 좋은 방법은 아님
        assertThat(rateDiscountPolicy).isInstanceOf(RateDiscountPolicy.class);
    }

    @Test
    @DisplayName("부모 타입으로 모두 조회하기")
    void findAllBeanByParentType(){
        Map<String,DiscountPolicy> map = ac.getBeansOfType(DiscountPolicy.class);
        assertThat(map.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("모든 클래스는 Object를 상속받으므로 Object로 조회시 모든 빈이 튀어 나온다")
    void findAllBeanByObjectType(){
        Map<String,Object> map = ac.getBeansOfType(Object.class);
        for(String key : map.keySet()){
            System.out.println(key+" "+map.get(key));
        }
    }

    @Configuration
    static class TestConfig{
        @Bean
        public DiscountPolicy rateDiscountPolicy(){
            return new RateDiscountPolicy();
        }
        @Bean
        public DiscountPolicy fixDiscountPolicy(){
            return new FixDiscountPolicy();
        }
    }
}
