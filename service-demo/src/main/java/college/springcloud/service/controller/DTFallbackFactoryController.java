package college.springcloud.service.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import college.springcloud.service.fegin.FallbackFactoryRPC;

import javax.annotation.Resource;

/**
 * User: xuxianbei
 * Date: 2019/7/20
 * Time: 15:17
 * Version:V1.0
 */
@RestController
@RequestMapping("/demo/fallback/factory")
public class DTFallbackFactoryController {

    @Resource
    FallbackFactoryRPC fallbackFactoryRPC;

    @GetMapping("/test")
    public String test() {
        return fallbackFactoryRPC.test();
    }
}
