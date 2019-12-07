package college.springcloud.elastic;

import org.apache.commons.collections4.CollectionUtils;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User: xuxianbei
 * Date: 2019/11/6
 * Time: 14:30
 * Version:V1.0
 */

@Component
public class EsManager {

    public static final String AGGREGATION_KEY_NAME = "key";
    public static final String SUBAGGREGATION_NAME = "subaggregation";

    @Autowired
    EsSettingsProperties properties;

    @Autowired
    RestHighLevelClient client;

    /**
     * 普通查询
     *
     * @param criteria
     * @return
     * @throws Exception
     */
    public Map<String, Object> queryWithBaseInfo(EsCriteria criteria) throws Exception {
        Map<String, Object> resultMap = new HashMap<>();
        SearchResponse sResponse = queryForResponse(criteria);
//        resultMap.put("baseInfoMap", getBaseInfoMap(sResponse, criteria));
//        resultMap.put("resultList", getHitList(sResponse));
        return resultMap;
    }


    /**
     * 通过搜索条件查询搜索引擎
     *
     * @param criteria
     * @return
     * @throws Exception
     */
    private SearchResponse queryForResponse(EsCriteria criteria) throws Exception {
        SearchRequest searchRequest = new SearchRequest();
        searchRequest.indices(properties.getIndex());
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        //搜索条件
        searchSourceBuilder.query(criteria.getFilterBuilder());
        //分页条件
        searchSourceBuilder.from(criteria.getStartIndext()).size(criteria.getPageSize());
        //排序条件
        if (CollectionUtils.isNotEmpty(criteria.getSorts())) {
            List<SortBuilder> sorts = criteria.getSorts();
            for (SortBuilder sortBuilder : sorts) {
                searchSourceBuilder.sort(sortBuilder);
            }
        }
        //自定义包含字段条件
        searchSourceBuilder.fetchSource(criteria.getIncludeFields(), criteria.getExcludeFields());
        //聚合条件
        criteria.getAggBuilders().forEach((Key, value) -> searchSourceBuilder.aggregation(value));
        //高亮条件
        searchSourceBuilder.highlighter(criteria.getHighlightBuilder());
        searchRequest.source(searchSourceBuilder);
        SearchResponse sResponse = client.search(searchRequest, RequestOptions.DEFAULT);
        return sResponse;
    }
}
