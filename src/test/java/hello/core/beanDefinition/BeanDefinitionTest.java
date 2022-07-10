package hello.core.beanDefinition;

import hello.core.AppConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class BeanDefinitionTest {
    AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);

    @Test
    @DisplayName("빈 설정 메타정보 확인")
    void findApplicationBean(){
        String[] str= ac.getBeanDefinitionNames();
        for(String bean : str){
            BeanDefinition beanDefinition = ac.getBeanDefinition(bean);
            if(beanDefinition.getRole() == BeanDefinition.ROLE_APPLICATION){
                System.out.println("name = "+bean+ " bean = "+beanDefinition);
            }
        }
    }
}