package college.springcloud.elastic;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * User: xuxianbei
 * Date: 2019/11/6
 * Time: 14:39
 * Version:V1.0
 */

@ConfigurationProperties(prefix = "elastic")
@Data
@Component
public class EsSettingsProperties {
    private String ip;

    private int port;

    private String cluster;

    private String index;

    private String type;

    private int pageSize;

}
