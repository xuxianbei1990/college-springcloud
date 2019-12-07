package web.controller;

import college.springcloud.model.Student;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * User: EDZ
 * Date: 2019/7/15
 * Time: 18:13
 * Version:V1.0
 *  配合service-demo 一起使用
 */
@RestController
public class TestFeginController {


    @GetMapping("/getStudents")
    public List<Student> testFegin() {
        List<Student> students = new ArrayList<>();
        Student student = new Student();
        student.setName("001");
        student.setAge(1);
        students.add(student);
        student = new Student();
        student.setName("002");
        student.setAge(1);
        students.add(student);
        student = new Student();
        student.setName("003");
        student.setAge(13);
        students.add(student);
        return students;
    }

    @GetMapping("/getString")
    public String getString(String key) {
        return key;
    }

    @PostMapping(value = "/getStudent")
    public Student getStudent(@RequestBody Student student) {
        return student;
    }

    @PostMapping(value = "/getStudent/Integer")
    public Integer getStudentInt(@RequestBody Student student) {
        return 1;
    }
}
