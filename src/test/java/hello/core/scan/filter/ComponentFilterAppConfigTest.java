package hello.core.scan.filter;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.stereotype.Component;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class ComponentFilterAppConfigTest {
    @Test
    void filterScan(){
        ApplicationContext ac = new AnnotationConfigApplicationContext(ComponentFilterAppConfig.class);
        BeanA beanA = ac.getBean("beanA",BeanA.class);
        assertThat(beanA).isNotNull();
        Assertions.assertThrows(NoSuchBeanDefinitionException.class,()->ac.getBean("beanB",BeanB.class));
    }

    @Configuration
    @ComponentScan( // 스캔대상에서 제외 및 추가 할 수 있다
            includeFilters = @ComponentScan.Filter(classes = MyIncludeComponent.class),
            excludeFilters = @ComponentScan.Filter(type=FilterType.ANNOTATION,classes = MyExcludeComponent.class)
    )
    static class ComponentFilterAppConfig{

    }
}


/*
    필터 타입 type=FilterType.ANNOTATION은 기본값이라 생략 가능
    type=FilterType.ASSIGNABLE_TYPE은 클래스를 직접 지정하는 것이다
    type=FilterType.ASPECTJ는 AspectJ 패턴 사용
    type=FilterType.REGEX는 정규식 패턴 사용 가능
    type=FilterType.CUSTOM은 타입 필터를 구현해서 조건을 직접 프로그래밍화 하는 것
 */