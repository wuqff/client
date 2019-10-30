package com.huawei.wuqf.esacess;


import com.huawei.wuqf.ESUser;
import org.elasticsearch.action.ActionFuture;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class ESClient {

    private TransportClient client;

    public static void main(String[] args) throws IOException {
        ESClient esClient = new ESClient();
        esClient.init("192.168.0.66", 9300);
        long startTime = System.currentTimeMillis();
//        esClient.createIndex("users", "user");
        //esClient.bulkIndex("user", "user");
        esClient.search();
        long endTime = System.currentTimeMillis();
        System.out.println("time:" + String.valueOf(endTime - startTime));
        esClient.close();
    }

    public void init(String ip, int port) throws UnknownHostException {

        Settings settings = Settings.builder().put("cluster.name", "dkes")
                .put("client.transport.sniff", true)  // 开启嗅探，自动搜寻新加入集群IP
                .build();
        client = new PreBuiltTransportClient(settings)
                .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(ip), port));
    }

    public void close() {
        client.close();
    }

    public List<IndexResponse> createIndex(String indexName, String typeName) throws IOException {
        List<IndexResponse> results = new ArrayList();
        for (int i = 0; i < Constant.dataCount; i++) {
            ESUser user = createUser(i, "names for " + i, i % Constant.dataCount, Constant.descriptions[i % 3]);

            XContentBuilder userJson = generateJson(user);
            IndexResponse result = client.prepareIndex(indexName, typeName).setSource(userJson).execute().actionGet();
            results.add(result);
        }
        return results;
    }


    public GetResponse get() {
        GetRequest request = new GetRequest("user", "user", "AW4Wj2g8SE3cxRQqbOEG");
        ActionFuture<GetResponse> response = client.get(request);
        GetResponse getResponse = response.actionGet();
        Map<String, Object> user = getResponse.getSource();
        return getResponse;
    }

    public SearchHit[] search() {
        SearchResponse searchResponse = client.prepareSearch("syslog")
                .setTypes("syslog")
                .setQuery(QueryBuilders.wildcardQuery("guard", "guard*"))
                .setQuery(QueryBuilders.termQuery("guard","guard_00_05_b7_e9_20_7b"))
                .setFrom(0).setSize(60).setExplain(true)
                .get();
        SearchHits hits = searchResponse.getHits();
        System.out.println("hits:" + String.valueOf(hits.getTotalHits()));
        return hits.getHits();
    }

    public BulkResponse bulkIndex(String indexName, String typeName) throws IOException {
        BulkRequestBuilder bulkRequest = client.prepareBulk();
        for (int i = 0; i < Constant.dataCount; i++) {
            ESUser user = createUser(i, "names for " + i, i % Constant.dataCount, Constant.descriptions[i % 3]);
            XContentBuilder userJson = generateJson(user);
            IndexRequestBuilder indexRequestBuilder = client.prepareIndex(indexName, typeName).setSource(userJson);
            bulkRequest.add(indexRequestBuilder);
        }
        BulkResponse bulkResponse = bulkRequest.execute().actionGet();
        return bulkResponse;
    }

    public ActionFuture<UpdateResponse> update(UpdateRequest request) {
        return null;
    }

    public ActionFuture<DeleteResponse> delete(DeleteRequest request) {
        return null;
    }

    private XContentBuilder generateJson(ESUser user) throws IOException {
        XContentBuilder doc = XContentFactory.jsonBuilder().startObject();
        doc.field("id", user.getId());
        doc.field("age", user.getAge());
        doc.field("name", user.getName());
        doc.field("description", user.getDescription());
        doc.endObject();
        return doc;
    }

    private ESUser createUser(int id, String name, int age, String description) {
        ESUser user = new ESUser();
        user.setId(id);
        user.setName(name);
        user.setAge(age);
        user.setDescription(description);
        return user;
    }

}
