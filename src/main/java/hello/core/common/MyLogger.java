package hello.core.common;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.UUID;

@Component
@Scope(value="request")
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
        String uuid = UUID.randomUUID().toString();
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