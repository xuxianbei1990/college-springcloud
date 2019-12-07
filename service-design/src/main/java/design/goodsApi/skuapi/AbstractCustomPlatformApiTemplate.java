package design.goodsApi.skuapi;

import com.alibaba.fastjson.JSONObject;
import design.goodsApi.*;
import design.goodsApi.model.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * User: xuxianbei
 * Date: 2019/11/11
 * Time: 20:14
 * Version:V1.0
 */
@Slf4j
public abstract class AbstractCustomPlatformApiTemplate implements SkuApiService {

    protected static final String MAINPIC = "main";

    @Autowired
    private CustomRiskBarrier customRiskBarrier;

    @Resource
    private ThirdApiMapper thirdApiMapper;

    private ThirdApi thirdApi;

    @Autowired
    private MQSendClient mqSendClient;

    @Value("${linkiebuy.mq.topic}")
    private String topic;

    @Value("${linkiebuy.mq.notifyItemTag}")
    private String notifyitemtag;

    @Value("${linkiebuy.mq.notifyKey}")
    private String notifyKey;

    protected MQSendResult sendMessage(Object value) {
        MQSendResult mqSendResult = mqSendClient.sendMessage(topic, notifyitemtag, value, notifyKey);
        log.info("订单回调通知: {}, 发送mq消息结果: {}", value, mqSendResult);
        return mqSendResult;
    }


    @PostConstruct
    public void initConfig() {
        //暂时这么写，后期考虑换成Lambda
        doRiskSkuList(customRiskBarrier.getRiskBarrier().setKey(SkuApiService.SKULIST));
        doRiskSkuSynchronization(customRiskBarrier.getRiskBarrier().setKey(SkuApiService.SKUSYNCHRONIZATION));
        doRiskSkuStore(customRiskBarrier.getRiskBarrier().setKey(SkuApiService.STORE));
        doRiskSkuOrder(customRiskBarrier.getRiskBarrier().setKey(SkuApiService.ORDER));
        doRiskTransport(customRiskBarrier.getRiskBarrier().setKey(SkuApiService.TRANSPORT));
        thirdApi = thirdApiMapper.selectByPrimaryKey(getId());
        customRiskBarrier.setBarrierPercent(BigDecimal.valueOf(90));
    }

    protected abstract void doRiskTransport(RiskBarrier setKey);

    /**
     * 业务变更
     *
     * @param pageIndex
     * @return
     */
    @Deprecated
    @Override
    public Integer skuList(Integer pageIndex) {
        if (!customRiskBarrier.isSkuList()) {
            throw new RuntimeException("触发风控");
        }
        List apiSkuList = new ArrayList<>();
        //获取第三方商品数据
        if (!doSkuList(thirdApi, apiSkuList, pageIndex)) {
            return 0;
        }
        if (!CollectionUtils.isEmpty(apiSkuList)) {
            saveApiSkuList(apiSkuList);
        }
        return 1;
    }

    /**
     * 保存列表
     *
     * @param apiSkuList
     */
    private void saveApiSkuList(List apiSkuList) {
        //校验数据正确性，有时间再补上吧
        //商品品牌
        //商品类目
        //原产地
        //发货地
        //待导入 按照品牌 类目过滤
        //填充信息
        //保存数据库
    }


    @Override
    public Integer skuSynchronization(SkuReqsDto skuReqsDto) {
        if (!customRiskBarrier.isSkuSynchronization()) {
            throw new RuntimeException("触发风控");
        }
        List apiSkuList = new ArrayList<>();

        doSkuSynchronization(thirdApi, apiSkuList, skuReqsDto);

        if (!CollectionUtils.isEmpty(apiSkuList)) {
            updateApiSkuList(apiSkuList, 100031);
        }
        return 1;
    }

    @Override
    public WeiNiSkuOrderResponse order(WeiNiOrder weiNiOrder) {
        if (!customRiskBarrier.isSkuOrder()) {
            throw new RuntimeException("触发风控");
        }
        log.info("order->{}", JSONObject.toJSONString(weiNiOrder));

        return doSkuOrder(thirdApi, weiNiOrder);
    }

    @Override
    public String orderNotify(String WeiNiOrderNotifyStr, HttpServletRequest servletRequest) {
        log.info("orderNotify:{}", WeiNiOrderNotifyStr);
        if (!doVerifyNotify(WeiNiOrderNotifyStr, thirdApi, servletRequest)) {
            return "{\n" +
                    "                \"Success\":false\n" +
                    "            }";
        }
        WeiNiOrderNotify weiNiOrderNotify = JSONObject.parseObject(WeiNiOrderNotifyStr, WeiNiOrderNotify.class);
        return doOrderNotify(weiNiOrderNotify);
    }

    @Override
    public List<GoodsSkuStoreVo> store(StockReqsDto stockReqsDto) {
        if (!customRiskBarrier.isStore()) {
            throw new RuntimeException("触发风控");
        }
        List apiSkuList = new ArrayList<>();

        doSkustore(thirdApi, apiSkuList, stockReqsDto);

        if (!CollectionUtils.isEmpty(apiSkuList)) {
            return updateApiSkuStore(apiSkuList, thirdApi.getFsellerId());
        }
        return new ArrayList<>();
    }

    private List<GoodsSkuStoreVo> updateApiSkuStore(List<ApiSku> apiSkuList, Integer fsellerId) {
        if (CollectionUtils.isEmpty(apiSkuList)) {
            return new ArrayList<>();
        }
        Assert.isTrue(apiSkuList.size() == 1, "数据异常");
        return null;
    }

    private void updateApiSkuList(List apiSkuList, Integer sellerId) {
        if (CollectionUtils.isEmpty(apiSkuList)) {
            return;
        }
        //查询全部的 个人觉得还是业务上录入商品标记下是第三方好点，

    }

    @Override
    public List<WeiNiTransportInfoResponse> transportSync(WeiNiTransportDto weiNiTransportDto) {
        if (!customRiskBarrier.isTransportSync()) {
            throw new RuntimeException("触发风控");
        }

        return doTransportSync(thirdApi, weiNiTransportDto);
    }

    //以下代码逻辑都类似，可以优化
    protected abstract List<WeiNiTransportInfoResponse> doTransportSync(ThirdApi thirdApi, WeiNiTransportDto weiNiTransportDto);

    protected abstract boolean doSkuSynchronization(ThirdApi thirdApi, List<ApiSku> apiSkuList, SkuReqsDto skuReqsDto);

    protected abstract void doRiskSkuSynchronization(RiskBarrier riskBarrier);

    protected abstract boolean doSkuList(ThirdApi thirdApi, List<ApiSku> apiSkuList, Integer pageIndex);

    protected abstract void doRiskSkuList(RiskBarrier riskBarrier);

    protected abstract Integer getId();

    protected abstract void doSkustore(ThirdApi thirdApi, List<ApiSku> apiSkuList, StockReqsDto stockReqsDto);

    protected abstract void doRiskSkuStore(RiskBarrier riskBarrier);

    protected abstract WeiNiSkuOrderResponse doSkuOrder(ThirdApi thirdApi, WeiNiOrder weiNiOrder);

    protected abstract void doRiskSkuOrder(RiskBarrier riskBarrier);

    protected abstract String doOrderNotify(WeiNiOrderNotify weiNiOrderNotify);

    //校验回调
    protected abstract boolean doVerifyNotify(String WeiNiOrderNotifyStr, ThirdApi thirdApi, HttpServletRequest servletRequest);
}
