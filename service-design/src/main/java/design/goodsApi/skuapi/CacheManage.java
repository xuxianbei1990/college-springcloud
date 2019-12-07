package design.goodsApi.skuapi;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * User: xuxianbei
 * Date: 2019/11/27
 * Time: 16:46
 * Version:V1.0
 */
@Component
public class CacheManage {

    private Map<String, String> map = new HashMap<>();

    public String getString(String redisRisk) {
        return map.get(redisRisk);
    }

    public void set(String redisRisk, String toJSONString) {
        map.put(redisRisk, toJSONString);
    }
}
