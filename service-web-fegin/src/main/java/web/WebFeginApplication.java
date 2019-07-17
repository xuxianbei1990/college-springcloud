package web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * User: EDZ
 * Date: 2019/7/15
 * Time: 18:12
 * Version:V1.0
 */
@SpringBootApplication
@EnableDiscoveryClient
//@EnableFeignClients
public class WebFeginApplication {
    public static void main(String[] args) {
        SpringApplication.run(WebFeginApplication.class, args);
    }
}
