package com.huawei.wuqf.esacess;


import org.elasticsearch.action.ActionFuture;
import org.elasticsearch.action.count.CountRequest;
import org.elasticsearch.action.count.CountResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;

import java.io.IOException;
import java.util.List;

public interface IESclient
{
    public List<IndexResponse> createIndex(String indexName, String typeName) throws IOException;
    
    public ActionFuture<CountResponse> count(CountRequest request);
    
    public ActionFuture<GetResponse> get(GetRequest request);
    
    public ActionFuture<SearchResponse> search(SearchRequest request);
    
    public ActionFuture<UpdateResponse> update(UpdateRequest request);
    
    public ActionFuture<DeleteResponse> delete(DeleteRequest request);
}
