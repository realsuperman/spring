package hello.core.web;

import hello.core.common.MyLogger;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequiredArgsConstructor
public class LogDemoController {
    private final LogDemoService logDemoService;
    private final MyLogger myLogger;

    @RequestMapping("log-demo")
    @ResponseBody // 화면없이 문자 반환하고 싶은 경우
    public String logDemo(HttpServletRequest request){ // http 요청 정보 받기 가능
        String url = request.getRequestURL().toString();
        myLogger.setRequestURL(url);
        myLogger.log("controller test");
        logDemoService.logic("testId");
        return "OK";
    }
}
