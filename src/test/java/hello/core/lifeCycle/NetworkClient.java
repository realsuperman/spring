package hello.core.lifeCycle;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

public class NetworkClient {//implements InitializingBean, DisposableBean {
    private String url;
    public NetworkClient(){
        System.out.println("생성자 호출, url = "+url);
    }
    public void setUrl(String url){
        this.url=url;
    }

    //서비스 시작시 호출
    public void connect(){
        System.out.println("connect : "+url);
    }

    public void call(String msg){
        System.out.println("call : "+url+"massage = "+msg);
    }

    //서비스 종료시 호출
    public void disconnect(){
        System.out.println("close "+url);
    }

    @PostConstruct // 스프링에서 그냥 이방법을 권장함(javax안에 있음 이건 자바 표준임)
    // 근데 만약에 외부 라이브러리의 경우 코드를 작성할 수 없으므로 그때는 빈으로 등록 후 bean안에 init하자
    public void init(){
        connect();
        call("초기화 연결 메시지");
    }

    @PreDestroy // 스프링에서 그냥 이방법을 권장함(javax안에 있음 이건 자바 표준임)
    // 근데 만약에 외부 라이브러리의 경우 코드를 작성할 수 없으므로 그때는 빈으로 등록 후 bean안에 init하자
    public void close(){
        disconnect();
    }

    /*@Override
    public void afterPropertiesSet() throws Exception { // 의존관계 주입이 끝나면 호출되는 메소드이다
        connect();
        call("초기화 연결 메시지");
    }

    @Override
    public void destroy() throws Exception {
        disconnect();
    }*/
}

/*
 InitializingBean, DisposableBean를 구현하면 의존관계 주입이 끝난 후와 스프링 빈이 종료되는 시점에 메소드를 호출 할 수 있다
 이건 근데 스프링 전용 인터페이스이다 그리고 메소드 이름 변경 불가능하다 또한 외부 라이브러리 코드의 경우 이런걸 달 수 없다
 */