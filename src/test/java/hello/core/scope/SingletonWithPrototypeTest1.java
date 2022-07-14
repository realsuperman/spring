package hello.core.scope;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Provider;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

public class SingletonWithPrototypeTest1 {
    @Test
    void prototypeFind(){
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(PrototypeBean.class);
        PrototypeBean prototypeBean1 = ac.getBean(PrototypeBean.class);
        prototypeBean1.addCount();
        assertThat(prototypeBean1.getCount()).isEqualTo(1);

        PrototypeBean prototypeBean2 = ac.getBean(PrototypeBean.class);
        prototypeBean2.addCount();
        assertThat(prototypeBean2.getCount()).isEqualTo(1);
    }
    @Test
    void singletonClientUsePrototype(){
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(PrototypeBean.class,ClientBean.class);
        ClientBean clientBean1 = ac.getBean(ClientBean.class);
        int cnt1 = clientBean1.logic();
        assertThat(cnt1).isEqualTo(1);
        ClientBean clientBean2 = ac.getBean(ClientBean.class);
        int cnt2 = clientBean2.logic();
        assertThat(cnt2).isEqualTo(1);
    }

    static class ClientBean{
        //private final PrototypeBean prototypeBean; // 생성시점에 주입되버림

        @Autowired
        private Provider<PrototypeBean> prototypeBeansProvider; // 스프링 컨테이너에서 빈을 찾아주는 기능만 전문으로 함(스프링 의존)

        /*public ClientBean(PrototypeBean prototypeBean) {
            this.prototypeBean = prototypeBean;
        }*/
        public int logic(){
            PrototypeBean prototypeBean = prototypeBeansProvider.get();
            prototypeBean.addCount();
            return prototypeBean.getCount();
        }
    }

    @Scope("prototype")
    static class PrototypeBean{
        private int count = 0;
        public void addCount(){
            count++;
        }
        public int getCount(){
            return count;
        }
        @PostConstruct
        public void init(){
            System.out.println("PrototypeBean.init = "+this);
        }
        @PreDestroy
        public void destroy(){
            System.out.println("PrototypeBean.destory");
        }
    }
}

/*
    만약 싱글톤 빈에서 프로토타입 빈을 사용하는 경우가 있다고 가정을 하자 이랬을 때 사용자가 의도한 것은
    싱글톤 빈이라도 프로토타입을 이용하므로 빈이 필요할 때마다 프로토타입 빈을 생성해서 하고자 함이였다
    그러나 싱글톤 빈을 사용하고 의존관계를 주입받으면 싱글톤 빈 안에서 프로토타입 빈을 생각처럼 사용할 수 없다
    왜냐하면 주입받는 시점에 프로토타입 빈을 만들어서 넣어줬고 그 이후에 변경되는 경우가 없으므로

    따라서 이걸 의도한 것 처럼 하려면 Provider로 문제를 해결하면 된다(Provider는 스프링 컨테이너에서 빈을 찾는 역할을 전문적으로 수행한다)
    -> 참고로 이렇게 컨테이너에서 찾는걸 DL(의존성 찾기)라고 한다
    ObjectProvider<PrototypeBean(찾는 빈 이름)>을 선언을 하고 추후에 getObject()를 통해 꺼내면 위의 문제를 해결 가능
    PrototypeBean의 경우 스프링에 의존적이므로 자바 표준인 import javax.inject.Provider를 사용하자 (단 그래들 설정 필요)
    스프링 표준 : ObjectProvider -> 어지간하면 이걸 쓰자
    자바 표준 : Provider(javax.inject.Provider) -> 스프링 외의 컨테이너 조회시는 필수

    -> 스프링 표준 메소드와 자바 표준 메소드 둘 다 사용 가능하면 지금은 스프링 표준 메소드를 사용하자
 */