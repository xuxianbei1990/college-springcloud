package service.controller;

import common.model.StudentValidated;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * User: xuxianbei
 * Date: 2019/7/20
 * Time: 13:34
 * Version:V1.0
 */
@RestController
public class DemoTestValidatedController {

    @GetMapping("/demo/valid/select")
    public String demoValidSelect(@Validated(StudentValidated.Select.class) StudentValidated student) {
        return "success";
    }

    @GetMapping("/demo/valid/select/1")
    public String demoValidSelect1(@Valid StudentValidated id) {
        return "success";
    }

    @GetMapping("/demo/valid/select/id")
    public String demoValidSelectBody(@RequestParam() Integer id) {
        return "success";
    }

    @GetMapping("/demo/valid/select/json")
    public String demoValidSelectBody(@RequestBody @Valid StudentValidated student) {
        return "success";
    }

    @GetMapping("/demo/valid/update")
    public String demoValidUpdate(@Validated(StudentValidated.Update.class) @RequestBody StudentValidated student) {
        return "success";
    }
}
