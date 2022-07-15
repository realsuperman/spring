package hello.core.common;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.UUID;

@Component
//@Scope(value="request") // 동시에 여러 정보가 들어와도 하나의 요청당 생성됨을 보장
@Scope(value="request",proxyMode = ScopedProxyMode.TARGET_CLASS) // 클래스면 TARGET_CLASS로하고 인터페이스면 TARGET_INTERFACE로 함
/*
    프록시 설정시 스프링 컨테이너에 해당 이름으로 프록시 객체가 생성됨 물론 해당 프록시 객체는 필요한 시점에
    진짜 객체를 호출한다
 */
public class MyLogger {
    private String uuid;
    private String requestURL;
    public void setRequestURL(String requestURL){
        this.requestURL=requestURL;
    }
    public void log(String msg){
        System.out.println("[" + uuid + "]" + "[" + requestURL + "] " + msg);
    }
    @PostConstruct // 고객 HTTP 요청 시작시
    public void init(){
        uuid = UUID.randomUUID().toString();
        System.out.println("[" + uuid + "] request scope bean create:" + this);
    }
    @PreDestroy // 리퀘스트 스코프는 호출 됨 (고객 HTTP 요청 종료시)
    public void close(){
        System.out.println("["+uuid+"] request scope bean close:"+this);
    }
}

/*
    Scope = request 사용하면 해당 빈은 HTTP 요청 당 하나씩 생성되고, HTTP 요청이 끝나면 소멸한다
 */