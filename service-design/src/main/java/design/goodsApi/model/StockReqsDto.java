package design.goodsApi.model;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import javax.validation.constraints.Size;
import java.util.List;

/**
 * User: xuxianbei
 * Date: 2019/11/15
 * Time: 14:54
 * Version:V1.0
 */
@Data
public class StockReqsDto {
    @Size(min = 1)
    @JSONField(name = "StockReqs")
    private List<String> stockReqs;
}
