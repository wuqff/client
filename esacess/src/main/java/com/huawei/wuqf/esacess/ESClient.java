package com.huawei.wuqf.esacess;


import com.huawei.wuqf.ESUser;
import org.elasticsearch.action.ActionFuture;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.count.CountRequest;
import org.elasticsearch.action.count.CountResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;


public class ESClient implements IESclient {

    private TransportClient client;

    public static void main(String[] args) throws IOException {
        ESClient esClient = new ESClient();
        esClient.init("127.0.0.1", 9300);
        long startTime = System.currentTimeMillis();
//        esClient.createIndex("users", "user");
        esClient.bulkIndex("users", "user");
        long endTime = System.currentTimeMillis();
        System.out.println(endTime - startTime);
        esClient.close();
    }

    public void init(String ip, int port) throws UnknownHostException {

        client = TransportClient.builder().build()
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

    public ActionFuture<CountResponse> count(CountRequest request) {

        ActionFuture<CountResponse> response = client.count(request);
        return response;
    }

    public ActionFuture<GetResponse> get(GetRequest request) {
        return null;
    }

    public ActionFuture<SearchResponse> search(SearchRequest request) {
        return null;
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
