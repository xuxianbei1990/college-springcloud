package common.model;

import java.io.Serializable;

/**
 * User: EDZ
 * Date: 2019/7/15
 * Time: 18:25
 * Version:V1.0
 */
public class Student implements Serializable {
    private static final long serialVersionUID = -5713872832171206495L;
    private String name;
    private Integer age;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
