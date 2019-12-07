package college.springcloud.elastic;

import org.elasticsearch.action.DocWriteResponse;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.admin.indices.get.GetIndexRequest;
import org.elasticsearch.action.admin.indices.get.GetIndexResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.reindex.BulkByScrollResponse;
import org.elasticsearch.index.reindex.DeleteByQueryRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * User: xuxianbei
 * Date: 2019/11/9
 * Time: 11:18
 * Version:V1.0
 */
@RestController
public class ElasticSearchController {

    @Autowired
    EsSettingsProperties esSettingsProperties;


    @Autowired
    RestHighLevelClient restHighLevelClient;

    //    @Autowired
    RestClient restClient;

    @GetMapping("/settings")
    public String getSetttings() {
        return esSettingsProperties.getIp();
    }

    //删除索引
    @GetMapping("/delete/index")
    public Boolean deleteIndex() throws IOException {
        DeleteIndexRequest deleteIndexRequest = new DeleteIndexRequest("can_delete_index");
        AcknowledgedResponse response = restHighLevelClient.indices().delete(deleteIndexRequest, RequestOptions.DEFAULT);
        return response.isAcknowledged();
    }

    //创建索引
    @GetMapping("/create/index")
    public Boolean createIndex() throws IOException {
        CreateIndexRequest createIndexRequest = new CreateIndexRequest("can_delete_index");
        createIndexRequest.settings(Settings.builder().put("number_of_shards", "1").put("number_of_replicas", "0"));
        createIndexRequest.mapping("doc", "{\n" +
                "\t\"properties\":{\n" +
                "\t\t\"name\":{\n" +
                "\t\t\t\"type\":\"text\",\n" +
                "\t\t\t\"analyzer\":\"ik_max_word\",\n" +
                "\t\t\t\"search_analyzer\":\"ik_smart\"\n" +
                "\t\t},\n" +
                "\t\t\"description\":{\n" +
                "\t\t\t\"type\":\"text\"\n" +
                "\t\t},\n" +
                "\t\t\"studymodel\":{\n" +
                "\t\t\t\"type\":\"keyword\"\n" +
                "\t\t}\n" +
                "\t}\n" +
                "}", XContentType.JSON);
        AcknowledgedResponse response = restHighLevelClient.indices().create(createIndexRequest, RequestOptions.DEFAULT);
        return response.isAcknowledged();
    }

    @GetMapping("/get/index")
    public GetIndexResponse getIndex() throws IOException {
        GetIndexRequest getIndexRequest = new GetIndexRequest();
        getIndexRequest.indices("can_delete_index");
        //好像只是用来判断是否存在的
        GetIndexResponse response = restHighLevelClient.indices().get(getIndexRequest, RequestOptions.DEFAULT);
        return response;
    }

    /**
     * 添加文档
     *
     * @return
     */
    @GetMapping("/add/document")
    public DocWriteResponse.Result addDocument() throws IOException {
        Map<String, Object> map = new HashMap<>();
        map.put("name", "测试1");
        map.put("description", "测试描述");
        map.put("studymodel", "2012132");
        IndexRequest indexRequest = new IndexRequest("can_delete_index", "doc", "2019/11/9test001");
        indexRequest.source(map);
        //有时间再了解吧  RequestOptions 反正也不影响我整体的架构
        IndexResponse indexResponse = restHighLevelClient.index(indexRequest, RequestOptions.DEFAULT);
        DocWriteResponse.Result result = indexResponse.getResult();
        return result;
    }

    /**
     * 更新文档
     *
     * @return
     * @throws IOException
     */
    @GetMapping("/update/document")
    public String updateDoc() throws IOException {
        UpdateRequest updateRequest = new UpdateRequest();
        Map<String, Object> map = new HashMap<>();
        map.put("name", "ceshi 001201");
        updateRequest.doc(map);
        UpdateResponse updateResponse = restHighLevelClient.update(updateRequest, RequestOptions.DEFAULT);
        return updateResponse.status().toString();
    }

    /**
     * 查询一行记录
     *
     * @return
     * @throws IOException
     */
    @GetMapping("/get/Doc")
    public Map<String, Object> getDoc() throws IOException {
        GetRequest getRequest = new GetRequest("can_delete_index", "doc", "2019/11/9test001");
        GetResponse getResponse = restHighLevelClient.get(getRequest, RequestOptions.DEFAULT);
        if (getResponse.isExists()) {
            return getResponse.getSourceAsMap();
        } else {
            return new HashMap<>();
        }
    }

    /**
     * 删除文档
     *
     * @return
     * @throws IOException
     */
    @GetMapping("/delete/doc")
    public DocWriteResponse.Result deleteDoc() throws IOException {
        DeleteRequest deleteRequest = new DeleteRequest("can_delete_index", "doc", "2019/11/9test001");
        DeleteResponse deleteResponse = restHighLevelClient.delete(deleteRequest, RequestOptions.DEFAULT);
        DocWriteResponse.Result result = deleteResponse.getResult();
        return result;
    }

    /**
     * 查询并删除
     * @return
     * @throws IOException
     */
    @GetMapping("/delete/query")
    public Long deleteByquery(String name) throws IOException {
        DeleteByQueryRequest deleteByQueryRequest = new DeleteByQueryRequest();
        deleteByQueryRequest.indices("college_student_test_can_delete");
        deleteByQueryRequest.setQuery(QueryBuilders.matchQuery("name", name));
        BulkByScrollResponse response = restHighLevelClient.deleteByQuery(deleteByQueryRequest, RequestOptions.DEFAULT);
        //删除文档的个数
        return response.getDeleted();
    }

    @GetMapping("/search")
    public Long search() {
        return 0L;
    }
}
