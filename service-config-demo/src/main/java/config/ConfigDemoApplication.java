package config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * User: EDZ
 * Date: 2019/7/11
 * Time: 17:36
 * Version:V1.0
 */
@SpringBootApplication
@EnableDiscoveryClient
public class ConfigDemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(ConfigDemoApplication.class, args);
    }
}
