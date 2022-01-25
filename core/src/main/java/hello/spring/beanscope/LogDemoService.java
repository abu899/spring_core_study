package hello.spring.beanscope;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LogDemoService {

    private final MyLogger myLogger;

    public void logic(String testID) {
        myLogger.log("service ID = " + testID);
    }
}
