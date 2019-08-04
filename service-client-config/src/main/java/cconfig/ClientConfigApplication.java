package cconfig;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * User: xuxianbei
 * Date: 2019/7/23
 * Time: 9:46
 * Version:V1.0
 */
@SpringBootApplication
@EnableDiscoveryClient
public class ClientConfigApplication {
    public static void main(String[] args) {
        SpringApplication.run(ClientConfigApplication.class, args);
    }
}
