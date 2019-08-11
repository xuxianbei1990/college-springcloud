package mvc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * User: xuxianbei
 * Date: 2019/8/6
 * Time: 19:45
 * Version:V1.0
 */
@SpringBootApplication
@EnableDiscoveryClient
public class MVCApplication {
    public static void main(String[] args) {
        SpringApplication.run(MVCApplication.class, args);
    }
}
