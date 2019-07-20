package service.fegin;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * User: xuxianbei
 * Date: 2019/7/20
 * Time: 15:19
 * Version:V1.0
 */
@FeignClient(value = "service-web-fegin", path = "/fegin/fallback", fallbackFactory = FallBackFactoryImpl.class)
public interface FallbackFactoryRPC {

    @RequestMapping(value = "/factory/test", method = RequestMethod.GET)
    String test();
}
