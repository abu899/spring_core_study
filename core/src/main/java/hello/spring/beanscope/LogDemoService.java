package hello.spring.beanscope;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LogDemoService {

    private final ObjectProvider<MyLogger> myLoggerProvider;

    public void logic(String testID) {
        MyLogger myLogger = myLoggerProvider.getObject();
        myLogger.log("service ID = " + testID);
    }
}
