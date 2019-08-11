package college.springcloud.service.fegin;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * User: xuxianbei
 * Date: 2019/7/18
 * Time: 11:18
 * Version:V1.0
 */
@FeignClient(value = "college.springcloud.service-web-fegin")
@RequestMapping("/ribbon")
public interface LoadBalanceClient {

    /**
     * 需要配合service-web-fegin 一起使用
     * @return
     */
    @RequestMapping(value = "/test/loadbalance", method = RequestMethod.GET)
    String testLoadBalance();
}
