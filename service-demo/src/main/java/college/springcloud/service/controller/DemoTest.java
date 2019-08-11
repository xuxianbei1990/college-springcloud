package college.springcloud.service.controller;

import com.alibaba.fastjson.JSONObject;
import college.springcloud.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import college.springcloud.service.fegin.LoadBalanceClient;
import college.springcloud.service.fegin.StoreClient;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.IOException;

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

    //测试所有请求
    //结论下面的写法可以接收所有请求。
    @RequestMapping("/test/quest")
    public String testQuest() {
        return "success";
    }


    @PostMapping("/test/post/form")
    public String testPost(Student student) {
        return student.toString();
    }

    @PostMapping("/test/post/json")
    public String testPostJson(@RequestBody Student student) {
        return student.toString();
    }

    @PostMapping("/test/post/xml")
    public HttpServletResponse testPostXml(HttpServletRequest request, HttpServletResponse response) {

        String inputLine;
        String notityXml = "";
        try {
            while ((inputLine = request.getReader().readLine()) != null) {
                notityXml += inputLine;
            }
            request.getReader().close();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        System.out.println(notityXml);
        try {
            BufferedOutputStream out = new BufferedOutputStream(response.getOutputStream());
            String resXml = "<xml>" + "<return_code><![CDATA[SUCCESS]]></return_code>"
                    + "<return_msg><![CDATA[OK]]></return_msg>" + "</xml> ";
            out.write(resXml.getBytes());
            out.flush();
            out.close();
        } catch (IOException io) {
            io.printStackTrace();
        }
        return response;
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
