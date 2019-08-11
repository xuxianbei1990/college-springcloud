package college.springcloud.model;

import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * User: xuxianbei
 * Date: 2019/8/7
 * Time: 21:20
 * Version:V1.0
 */
@Data
public class ClassValidated implements Serializable {
    private static final long serialVersionUID = 4891785788022146972L;

    private List<ClassInner> classInners;

    @Valid
    @NotNull(groups = add.class)
    private ClassInner classInner;

    public interface add {

    }
}
