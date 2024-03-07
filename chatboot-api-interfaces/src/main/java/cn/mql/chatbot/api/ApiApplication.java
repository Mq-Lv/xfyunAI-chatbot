package cn.mql.chatbot.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * ClassName: ApiApplication
 * Package: cn.mql.chatbot.api
 * Description: 启动类
 *
 * @Author lmq
 * @Create 2024/3/6 20:24
 * @Version 1.0
 */
@SpringBootApplication
public class ApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiApplication.class,args);
    }
}
