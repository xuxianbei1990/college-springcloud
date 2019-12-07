package design.goodsApi.skuapi;

import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * 风控屏障
 * User: xuxianbei
 * Date: 2019/11/11
 * Time: 19:59
 * Version:V1.0
 */
public class RiskBarrier {

    private String key;

    private List<TimeCondition> timeConditionList = new ArrayList<>();

    //这个只是初始化设置，目前不提供更新功能
    RiskBarrier setTimeCondition(TimeUnit timeUnit, Integer times) {
        TimeCondition timeCondition = new TimeCondition();
        timeCondition.setTimes(times);
        timeCondition.setTimeUnit(timeUnit);
        if (!timeConditionList.contains(timeCondition)) {
            timeConditionList.add(timeCondition);
        } else {
            throw new RuntimeException(timeUnit.toString() + ":已经设置过了");
        }
        return this;
    }

    public List<TimeCondition> getTimeConditionList() {
        return timeConditionList;
    }

    public RiskBarrier setKey(String key) {
        this.key = key;
        return this;
    }

    public String getKey() {
        return key;
    }

    @Data
    static public class TimeCondition {
        private TimeUnit timeUnit;
        private Integer times;
        private Date currentDate;
        private Integer currentTimes;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            TimeCondition that = (TimeCondition) o;
            return timeUnit == that.timeUnit;
        }

        @Override
        public int hashCode() {
            return Objects.hash(timeUnit);
        }
    }
}
