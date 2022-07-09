package hello.core.beanFind;

import hello.core.AppConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

class ApplicationContextInfoTest {
    AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);
    @Test
    @DisplayName("모든 빈 출력하기")
    void findAllBean(){
        String[] beanNames = ac.getBeanDefinitionNames(); // 모든 빈의 키를 반환하는 메소드 반환형은 String[]
        for(String str : beanNames){
            Object bean = ac.getBean(str);
            System.out.println(str+" "+bean);
        }
    }

    @Test
    @DisplayName("애플리케이션 빈 출력하기")
    void findApplicationBean(){
        String[] beanNames = ac.getBeanDefinitionNames(); // 모든 빈의 이름 출력 반환형은 String[]
        for(String str : beanNames){
            BeanDefinition bean = ac.getBeanDefinition(str);
            // BeanDefinition.ROLE_APPLICATION는 사용자가 등록한 빈
            // BeanDefinition.ROLE_INFRASTRUCTURE는 스프링 내부에서 필요한 빈
            if(bean.getRole()==BeanDefinition.ROLE_INFRASTRUCTURE){ // 사용자가 app 개발을 위해 등록한 빈만 출력
                System.out.println("name="+ac.getBean(str));
            }
        }
    }
}
