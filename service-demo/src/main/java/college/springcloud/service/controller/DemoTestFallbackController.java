package college.springcloud.service.controller;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * User: xuxianbei
 * Date: 2019/7/20
 * Time: 13:37
 * Version:V1.0
 * 怎么说不知道是自己用错了，还是其他情况;为什么降级还需要   @RequestMapping("/fallbackPpcimpl")
 */
@RestController
@RequestMapping("/demo/fallback")
public class DemoTestFallbackController {

    @Resource
    FallBackRPC fallBackRPC;

    @GetMapping("/test")
    public String testFallback() {
        return fallBackRPC.fallbackTest("success");
    }

    @GetMapping("/test/college.springcloud.exception")
    public String testFallbackExcption() {
        return fallBackRPC.feginFallBackException("success");
    }

    @FeignClient(value = "college.springcloud.service-web-fegin", path = "/fegin/fallback", fallback = FallBackRPCImpl.class)
    protected interface FallBackRPC {

        @RequestMapping(value = "/test/college.springcloud.exception", method = RequestMethod.GET)
        String feginFallBackException(@RequestParam("key") String key);

        @RequestMapping(value = "/test/success", method = RequestMethod.GET)
        String fallbackTest(@RequestParam("key") String key);
    }

    @Component
//    @RequestMapping("/fallbackPpcimpl")  加了path 居然成功，不是特别明白， 去掉path，也成功
    static class FallBackRPCImpl implements FallBackRPC {
        @Override
        public String feginFallBackException(String key) {
            return "I am sorry you be falure, fall back";
        }

        @Override
        public String fallbackTest(String key) {
            return "fallbackTest";
        }
    }
}
