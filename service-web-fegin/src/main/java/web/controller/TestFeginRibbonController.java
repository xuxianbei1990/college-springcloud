package web.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * User: xuxianbei
 * Date: 2019/7/18
 * Time: 11:10
 * Version:V1.0
 * 测试方法，修改不同的端口
 */
@RestController
@RequestMapping("/ribbon")
public class TestFeginRibbonController {

    @Value("${server.port}")
    private String serverPort;

    AtomicInteger times = new AtomicInteger();

    //测试负载均衡
    @GetMapping("/test/loadbalance")
    public String testLoadBalance() {
        System.out.println(serverPort + "调用次数" + times.incrementAndGet());
        return "success";
    }
}
