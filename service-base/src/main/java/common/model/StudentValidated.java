package common.model;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * User: xuxianbei
 * Date: 2019/7/18
 * Time: 15:13
 * Version:V1.0
 */
@Data
public class StudentValidated implements Serializable {

    private static final long serialVersionUID = 1632705960972023094L;
    @NotNull
    @Min(value = 0, groups = {Update.class})
    private String name;

    @NotNull
    @Min(value = 0, groups = {Update.class})
    private Integer age;

    @NotNull(groups = {Select.class})
    @Min(value = 0, groups = {Select.class})
    private Integer id;

    public interface Update {

    }

    public interface Select {

    }
}
