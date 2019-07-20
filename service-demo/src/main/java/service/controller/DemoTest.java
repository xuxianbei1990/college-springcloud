package service.controller;

import com.alibaba.fastjson.JSONObject;
import common.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import service.fegin.LoadBalanceClient;
import service.fegin.StoreClient;

import javax.annotation.Resource;

/**
 * User: EDZ
 * Date: 2019/7/16
 * Time: 9:19
 * Version:V1.0
 */
@RestController
public class DemoTest {

    //测试ribbon
    @Autowired
    LoadBalanceClient loadBalanceClient;

    //测试远程调用
    @Resource
    StoreClient storeClient;

    @GetMapping("/demo/test")
    public String testFegin() {
        return "testFegin";
    }

    //测试远程调用
    @GetMapping("/demo/fegin")
    public String demoFegin() {
        //String类型
        String result = storeClient.getString("sky");
        Student student = new Student();
        student.setName("dsf");
        student.setAge(112);
        //类
        student = storeClient.getStudent(student);
        result = result + "\n student: \n" + JSONObject.toJSONString(student);
        //列表
        result = result + "\n student: \n" + JSONObject.toJSONString(storeClient.getStudents());
        return result;
    }

    //测试负载均衡
    @GetMapping("/demo/loadbalance")
    public String demoLoadbalance(Integer times) {
        for (int i = 0; i < times; i++) {
            loadBalanceClient.testLoadBalance();
        }
        return "succcess";
    }


}
