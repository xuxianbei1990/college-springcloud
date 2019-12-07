package design.goodsApi.skuapi;

import com.alibaba.fastjson.JSONObject;
import design.goodsApi.SkuApiService;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * 风控屏障 非线程安全
 * User: xuxianbei
 * Date: 2019/11/11
 * Time: 20:03
 * Version:V1.0
 */
@Component
public class CustomRiskBarrier {

    private static final String RISKBARRIERSKULIST = "risk_barrier:isSkuList";
    private static final String RISKBARRIERSKUSYNCHRONIZE = "risk_barrier:isSkuSynchronize";
    private static final String RISKBARRIERSKUSTORE = "risk_barrier:isSkuStore";
    private static final String RISKBARRIERSKUORDER = "risk_barrier:isSkuOrder";
    private static final String RISKBARRIERTRANSPORTSYNC = "risk_barrier:isTransportSync";

    @Autowired
    private CacheManage cacheManage;
    //风控列表
    private List<RiskBarrier> riskBarriers = new ArrayList<>();

    public List<RiskBarrier> getRiskBarriers() {
        return riskBarriers;
    }

    //非线程安全
    public RiskBarrier getRiskBarrier() {
        riskBarriers.add(new RiskBarrier());
        return riskBarriers.get(riskBarriers.size() - 1);
    }

    public void setBarrierPercent(BigDecimal percent) {
        Assert.isTrue(percent.compareTo(BigDecimal.valueOf(1)) < 0, "必须小于1");
        riskBarriers.stream().forEach(riskBarrier -> riskBarrier.getTimeConditionList().stream().forEach(timeCondition -> {
            timeCondition.setTimes(BigDecimal.valueOf(timeCondition.getTimes()).multiply(percent).intValue());
        }));
    }

    //是否可以sku列表  非线程安全 true 可以
    public boolean isSkuList() {
        return doJudgeRiskBarrier(RISKBARRIERSKULIST, SkuApiService.SKULIST);
    }

    private boolean doJudgeRiskBarrier(String redisRisk, String funName) {
        // 把这个方法放到redis缓存中。记录上次时间，记录限时时间之内的次数。记录总次数。
        String json = cacheManage.getString(redisRisk);
        if (StringUtils.isBlank(json)) {
            Optional<RiskBarrier> riskBarrierOptional = riskBarriers.stream().filter(t -> t.getKey().equals(funName)).findFirst();
            //不存在脏数据
            RiskBarrier riskBarrier = riskBarrierOptional.get();
            List<RiskBarrier.TimeCondition> list = riskBarrier.getTimeConditionList();
            list.stream().forEach(t -> {
                t.setCurrentTimes(0);
                t.setCurrentDate(new Date());
            });
            cacheManage.set(redisRisk, JSONObject.toJSONString(list));
            return true;
        } else {
            List<RiskBarrier.TimeCondition> list = JSONObject.parseArray(json, RiskBarrier.TimeCondition.class);
            Long count = list.stream().filter(timeCondition -> {
                Date value1;
                switch (timeCondition.getTimeUnit()) {
                    case MINUTES:
                        value1 = DateUtils.addMinutes(timeCondition.getCurrentDate(), 1);
                        break;
                    case DAYS:
                        value1 = DateUtils.addDays(timeCondition.getCurrentDate(), 1);
                        break;
                    default:
                        throw new RuntimeException("不支持该定义时间类型");
                }
                Date currentDate = new Date();
                timeCondition.setCurrentDate(currentDate);
                if (value1.compareTo(currentDate) >= 0) {
                    //一分钟记录次数
                    timeCondition.setCurrentTimes(timeCondition.getCurrentTimes() + 1);
                    return timeCondition.getCurrentTimes() >= timeCondition.getTimes();
                } else {
                    timeCondition.setCurrentTimes(0);
                    return false;
                }
            }).count();
            cacheManage.set(redisRisk, JSONObject.toJSONString(list));
            return count == 0;
        }
    }


    public boolean isSkuSynchronization() {
        return doJudgeRiskBarrier(RISKBARRIERSKUSYNCHRONIZE, SkuApiService.SKUSYNCHRONIZATION);
    }

    public boolean isStore() {
        return doJudgeRiskBarrier(RISKBARRIERSKUSTORE, SkuApiService.STORE);
    }

    public boolean isSkuOrder() {
        return doJudgeRiskBarrier(RISKBARRIERSKUORDER, SkuApiService.ORDER);
    }

    public boolean isTransportSync() {
        return doJudgeRiskBarrier(RISKBARRIERTRANSPORTSYNC, SkuApiService.TRANSPORT);
    }
}
