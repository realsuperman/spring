package hello.core.web;

import hello.core.common.MyLogger;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequiredArgsConstructor
public class LogDemoController {
    private final LogDemoService logDemoService;
    private final MyLogger myLogger; // 프록시 설정했으므로 생성자 주입시점에 가리키는 것은 프록시 객체임
    /*
     빈 스코프 주기가 request인 경우 프로바이더를 사용하여 DL을 적용하지만
     프록시를 사용하여 생성자 주입시점에는 프록시 객체를 가리키게 하고 이게 진짜 필요한 시점 아래의 코드에서
     myLogger.log 부분임 해당 시점에 DL을 한다 그럴라면 해당 MyLogger(빈 스코프 주기가 request인 녀석)의
     프록시 정보 설정 필요(해당 클래스가 클래스면 TARGET_CLASS로 지정하고 인터페이스면 TARGET_INTERFACE)

     사실상 프로바이더든 프록시든 지연로딩이 기본이다
     */

    @RequestMapping("log-demo")
    @ResponseBody // 화면없이 문자 반환하고 싶은 경우
    public String logDemo(HttpServletRequest request){ // http 요청 정보 받기 가능
        //MyLogger myLogger = myLoggerProvider.getObject(); // 필요한 시점에 DL
        String url = request.getRequestURL().toString();
        myLogger.setRequestURL(url);
        myLogger.log("controller test");
        logDemoService.logic("testId");
        return "OK";
    }
}
