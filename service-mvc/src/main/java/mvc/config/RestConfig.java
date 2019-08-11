package mvc.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * User: xuxianbei
 * Date: 2019/8/6
 * Time: 20:07
 * Version:V1.0
 */
@Configuration
public class RestConfig {

    @Bean
    public RestTemplate init() {
        return new RestTemplate();
    }
}
