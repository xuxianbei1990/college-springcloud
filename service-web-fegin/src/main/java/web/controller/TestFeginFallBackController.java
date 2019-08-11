package web.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * User: xuxianbei
 * Date: 2019/7/20
 * Time: 14:11
 * Version:V1.0
 */
@RestController
@RequestMapping("/fallback")
public class TestFeginFallBackController {

    @GetMapping("/test/college.springcloud.exception")
    public String feginFallBackException() {
        throw new RuntimeException("error");
    }

    @GetMapping("/test/success")
    public String feginFallBack() {
        return "success";
    }

    @GetMapping("/factory/test")
    public String feginFallBackFactory() {
        return "success";
    }
}
