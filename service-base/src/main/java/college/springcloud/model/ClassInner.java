package college.springcloud.model;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * User: xuxianbei
 * Date: 2019/8/7
 * Time: 21:25
 * Version:V1.0
 */
@Data
public class ClassInner implements Serializable {

    private static final long serialVersionUID = -8188839412511052852L;

    @NotNull(groups = ClassValidated.add.class)
    private String name;
}
