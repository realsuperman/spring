package hello.core.scope;

import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

public class PrototypeTest {
    @Test
    void prototypeBeanFind(){
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(protoTypeBean.class);
        protoTypeBean protoTypeBean1= ac.getBean(protoTypeBean.class);
        protoTypeBean protoTypeBean2= ac.getBean(protoTypeBean.class);
        assertThat(protoTypeBean1).isNotSameAs(protoTypeBean2);
        protoTypeBean1.destroy(); // 스프링 컨테이너 도움을 못받으므로 PreDestory 호출원하면 destory 메소드 호출
        protoTypeBean2.destroy(); // 스프링 컨테이너 도움을 못받으므로 PreDestory 호출원하면 destory 메소드 호출
    }

    @Scope("prototype")
    static class protoTypeBean{
        @PostConstruct
        public void init(){
            System.out.println("init");
        }
        @PreDestroy
        public void destroy(){
            System.out.println("destory");
        }
    }
}

/*
 스코프의 범위를 prototype으로 지정하였으므로 해당 빈을 생성시 스프링 컨테이너에서 관리하지 않고
 한번 생성해주고 끝낸다(그러므로 다른 객체이다)
 */