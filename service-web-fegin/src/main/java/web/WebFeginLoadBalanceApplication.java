package web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * User: xuxianbei
 * Date: 2019/7/18
 * Time: 11:26
 * Version:V1.0
 */
@SpringBootApplication
@EnableDiscoveryClient
public class WebFeginLoadBalanceApplication {
    public static void main(String[] args) {
        SpringApplication.run(WebFeginApplication.class, args);
    }
}
