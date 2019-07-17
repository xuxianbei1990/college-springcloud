package service.controller;

import com.alibaba.fastjson.JSONObject;
import common.model.Student;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
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

    @Resource
    StoreClient storeClient;

    @GetMapping("/demo/test")
    public String testFegin() {
        return "testFegin";
    }

    @GetMapping("/demo/fegin")
    public String demoFegin() {
        String result = storeClient.getString("sky");
        Student student = new Student();
        student.setName("dsf");
        student.setAge(112);
        student = storeClient.getStudent(student);
        result = result + "\n student: \n" + JSONObject.toJSONString(student);
        result = result + "\n student: \n" + JSONObject.toJSONString(storeClient.getStudents());
        return result;
    }
}
