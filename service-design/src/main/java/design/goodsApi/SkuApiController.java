package design.goodsApi;


import design.goodsApi.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Min;
import java.util.List;

/**
 * 第三方平台skuAPI
 * User: xuxianbei
 * Date: 2019/11/12
 * Time: 16:30
 * Version:V1.0
 */
@RestController
@RequestMapping("/sku")
@Validated
public class SkuApiController {

    @Autowired
    SkuApiService skuApiService;

    /**
     * 列表
     *
     * @return 0:代表第三方没有数据；1:代表成功
     */
    @Deprecated
    @GetMapping("/list")
    public Integer list(@RequestParam @Min(1) Integer pageIndex) {
        return skuApiService.skuList(pageIndex);
    }

    /**
     * 同步
     *
     * @return 1: 成功
     */
    @PostMapping("/synchronization")
    public Integer synchronization(@Validated @RequestBody SkuReqsDto skuReqsDto) {
        return skuApiService.skuSynchronization(skuReqsDto);
    }

    /**
     * 更新库存并返回
     *
     * @return 1: 成功
     */
    @PostMapping("/store")
    public List<GoodsSkuStoreVo> store(@Validated @RequestBody StockReqsDto stockReqsDto) {
        List<GoodsSkuStoreVo> store = skuApiService.store(stockReqsDto);
        return store;
    }

    /**
     * 订单
     *
     * @param weiNiOrder
     * @return
     */
    @PostMapping("/order")
    public WeiNiSkuOrderResponse order(@Validated @RequestBody WeiNiOrder weiNiOrder) {
        return skuApiService.order(weiNiOrder);
    }

    /**
     * 订单通知
     *
     * @param WeiNiOrderNotifyStr
     * @param servletRequest
     * @return
     */
    @PostMapping("/OrderAsynNotify.shtml")
    public String orderNotify(@RequestBody String WeiNiOrderNotifyStr, HttpServletRequest servletRequest) {
        return skuApiService.orderNotify(WeiNiOrderNotifyStr, servletRequest);
    }

    /**
     * 运单同步
     *
     * @return
     */
    @PostMapping("/transport/sync")
    public List<WeiNiTransportInfoResponse> transportSync(@RequestBody WeiNiTransportDto weiNiTransportDto) {
        return skuApiService.transportSync(weiNiTransportDto);
    }
}
