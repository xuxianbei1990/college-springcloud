package design.goodsApi.skuapi;

import com.alibaba.fastjson.JSONObject;
import design.goodsApi.model.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * User: xuxianbei
 * Date: 2019/11/11
 * Time: 15:48
 * Version:V1.0
 * 设计思想，毕竟不是完善风控
 * 如果我是作为开源软件呢？
 */
@Service
@Slf4j
public class WeiNiHaiGouSkuApiImpl extends AbstractCustomPlatformApiTemplate {

    //回调接口名称
    private String NOTIFYINTERFACENAME = "OrderAsynNotify";

    //如果来一个每个月，每年呢？
    private Integer UnitMinutesTimes = 60;

    private Integer UNITMINUTESTIMES_TWOHUNDRED = 200;

    private Integer UnitDayTimes = 5000;

    @Autowired
    private AliOssConfig aliOssConfig;

    @Override
    protected void doRiskTransport(RiskBarrier riskBarrier) {
        riskBarrier.setTimeCondition(TimeUnit.MINUTES, UnitMinutesTimes).setTimeCondition(TimeUnit.DAYS, UnitDayTimes);
    }


    @Override
    protected List<WeiNiTransportInfoResponse> doTransportSync(ThirdApi thirdApi, WeiNiTransportDto weiNiTransportDto) {
        String content = JSONObject.toJSONString(weiNiTransportDto);
        String string = getWeiNiSkuFromHttp(thirdApi, content, thirdApi.getFthirdTransportUrl());
        log.info("doTransportSync-> {}", string);
        return JSONObject.parseArray(string, WeiNiTransportInfoResponse.class);
    }

    @Override
    protected boolean doSkuSynchronization(ThirdApi thirdApi, List<ApiSku> apiSkuList, SkuReqsDto skuReqsDto) {
        String content = JSONObject.toJSONString(skuReqsDto);
        String string = getWeiNiSkuFromHttp(thirdApi, content, thirdApi.getFthirdSkuUrl());
        List<WeiNiHaiGouSku> weiNiHaiGouSkus = JSONObject.parseArray(string, WeiNiHaiGouSku.class);
        log.info("doSkuSynchronization->{}", string);
        if (CollectionUtils.isEmpty(weiNiHaiGouSkus)) {
            return false;
        }
        return true;
    }

    @Override
    protected boolean doSkuList(ThirdApi thirdApi, List<ApiSku> apiSkuList, Integer pageIndex) {
        String content = "{\"PageNo\":" + pageIndex + ",\"PageNum\":100}";
        //从http请求获取维尼的商品列表
        String string = getWeiNiSkuFromHttp(thirdApi, content, thirdApi.getFthirdSkuListUrl());

        List<WeiNiHaiGouSku> weiNiHaiGouSkus = getWeiNiHaiGouSkus(string);
        if (CollectionUtils.isEmpty(weiNiHaiGouSkus)) {
            return false;
        }
        return true;
    }

    private List<WeiNiHaiGouSku> getWeiNiHaiGouSkus(String string) {
        JSONObject jsonObject = JSONObject.parseObject(string);
        JSONObject jsonObjectResult = jsonObject.getJSONObject("result");
        jsonObjectResult.getJSONArray("SkuList");
        return null;
    }

    private Map<String, String> getPicOss(WeiNiHaiGouSku weiNiHaiGouSku) {
        String picMain = weiNiHaiGouSku.getDisplayImgUrls().split(";")[0];
        String[] picDetails = weiNiHaiGouSku.getDetailImgUrls().split(";");
        Map<String, String> picMap = new HashMap<>(picDetails.length + 1);
        picMap.put(MAINPIC, picMain);
        for (String str : picDetails) {
            picMap.put(String.valueOf(picMap.size()), str);
        }
        //还差主图和详细图
        return null;
    }


    private String getWeiNiSkuFromHttp(ThirdApi thirdApi, String content, String url) {
        Integer last = url.lastIndexOf("/");
        String interfacename = url.substring(last + 1).split("\\.")[0];
        Map<String, String> headers = new HashMap<>();
        headers.put("interfacename", interfacename);
        headers.put("content-type", "application/json");
        return "";
    }


    @Override
    protected void doRiskSkuSynchronization(RiskBarrier riskBarrier) {
        riskBarrier.setTimeCondition(TimeUnit.MINUTES, UnitMinutesTimes).setTimeCondition(TimeUnit.DAYS, UnitDayTimes);
    }

    @Override
    protected void doRiskSkuList(RiskBarrier riskBarrier) {
        riskBarrier.setTimeCondition(TimeUnit.MINUTES, UnitMinutesTimes).setTimeCondition(TimeUnit.DAYS, UnitDayTimes);
    }

    /**
     * 对应t_lkb_third_api 的唯妮供应链 id 为1
     *
     * @return
     */
    @Override
    protected Integer getId() {
        return 1;
    }

    @Override
    protected void doSkustore(ThirdApi thirdApi, List<ApiSku> apiSkuList, StockReqsDto stockReqsDto) {
    }

    @Override
    protected void doRiskSkuStore(RiskBarrier riskBarrier) {
        riskBarrier.setTimeCondition(TimeUnit.MINUTES, UnitMinutesTimes).setTimeCondition(TimeUnit.DAYS, UnitDayTimes);
    }

    @Override
    protected WeiNiSkuOrderResponse doSkuOrder(ThirdApi thirdApi, WeiNiOrder weiNiOrder) {
        String content = JSONObject.toJSONString(weiNiOrder);
        return null;
    }

    @Override
    protected void doRiskSkuOrder(RiskBarrier riskBarrier) {
        riskBarrier.setTimeCondition(TimeUnit.MINUTES, UNITMINUTESTIMES_TWOHUNDRED).setTimeCondition(TimeUnit.DAYS, UnitDayTimes);
    }


    @Override
    protected boolean doVerifyNotify(String WeiNiOrderNotifyStr, ThirdApi thirdApi, HttpServletRequest servletRequest) {
        String requesToken = servletRequest.getHeader("token");
        return true;
    }

    @Override
    protected String doOrderNotify(WeiNiOrderNotify weiNiOrderNotify) {
        return "";
    }
}
