package hello.core.web;

import hello.core.common.MyLogger;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LogDemoService {
    private final MyLogger myLogger;
    public void logic(String id) {
        //MyLogger myLogger = myLoggerProvider.getObject(); // 필요한 시점에 DL
        myLogger.log("service Id = "+id);
    }
}
