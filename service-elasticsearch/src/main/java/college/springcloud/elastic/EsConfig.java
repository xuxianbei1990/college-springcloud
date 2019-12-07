package college.springcloud.elastic;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

/**
 * User: xuxianbei
 * Date: 2019/11/6
 * Time: 14:28
 * Version:V1.0
 */
@Configuration
public class EsConfig {

//    @Value("${elasticsearch.hostlist}")
    private String hostlist;

    @Resource
    EsSettingsProperties esSettingsProperties;

    //高版本的RestHighLevelClient
    @Bean
    public RestHighLevelClient restHighLevelClient() {
        RestHighLevelClient client = new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost(esSettingsProperties.getIp(), esSettingsProperties.getPort(), "http")));
        return client;
    }

    //低版本RestClient
//    @Bean
    public RestClient restClient(){
        String[] strings = hostlist.split(",");
        HttpHost[] httpHosts = new HttpHost[strings.length];
        for (int i =0; i< strings.length;i++) {
            String item = strings[i];
            httpHosts[i] = new HttpHost(item.split(":")[0], Integer.parseInt(item.split(":")[1]));
        }
        return RestClient.builder(httpHosts).build();
    }
}
