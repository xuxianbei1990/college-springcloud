package college.springcloud.model;

import lombok.Data;

import javax.validation.Valid;
import java.io.Serializable;
import java.util.List;

/**
 * @author: xuxianbei
 * Date: 2020/4/2
 * Time: 19:42
 * Version:V1.0
 */
@Data
public class ListStudentValidDated implements Serializable {

    private static final long serialVersionUID = 7595407098138041595L;

    @Valid
    private List<StudentValidated> list;
}
