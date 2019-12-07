package design.goodsApi;


import design.goodsApi.model.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * User: xuxianbei
 * Date: 2019/11/11
 * Time: 20:09
 * Version:V1.0
 */
public interface SkuApiService {

    String SKULIST = "skuList";
    String SKUSYNCHRONIZATION = "skuSynchronization";
    String STORE = "store";
    String ORDER = "order";
    String TRANSPORT = "transport";

    /**
     * sku列表
     *
     * @return
     */
    Integer skuList(Integer pageIndex);

    /**
     * sku同步
     *
     * @return
     */
    Integer skuSynchronization(SkuReqsDto skuReqsDto);

    /**
     * 库存
     *
     * @param stockReqsDto
     * @return
     */
    List<GoodsSkuStoreVo> store(StockReqsDto stockReqsDto);


    /**
     * 订单
     *
     * @return
     */
    WeiNiSkuOrderResponse order(WeiNiOrder weiNiOrder);

    /**
     * 订单通知
     */
    String orderNotify(String WeiNiOrderNotifyStr, HttpServletRequest servletRequest);

    /**
     * 运单同步
     *
     * @param weiNiTransportDto
     * @return
     */
    List<WeiNiTransportInfoResponse> transportSync(WeiNiTransportDto weiNiTransportDto);
}
