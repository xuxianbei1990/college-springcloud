package config.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * User: EDZ
 * Date: 2019/7/11
 * Time: 17:39
 * Version:V1.0
 * 好像springboot已经包含了MVC
 */
@RestController
public class ControllerConfigTest {

    //获取远程配置的参数，只是为了github测试是否可以正确获得远程配置的配置信息
//    @Value("${test.namexxb}")
    @Value("${spring.application.name}")
    private String test = "ddd";

    @GetMapping("/test")
    public String getTest() {
        return test;
    }
}
