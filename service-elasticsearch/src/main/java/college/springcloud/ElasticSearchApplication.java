package college.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * User: xuxianbei
 * Date: 2019/11/6
 * Time: 14:29
 * Version:V1.0
 */
@SpringBootApplication(scanBasePackages = "college.springcloud.elastic")
public class ElasticSearchApplication {
    public static void main(String[] args) {
        SpringApplication.run(ElasticSearchApplication.class, args);
    }
}
