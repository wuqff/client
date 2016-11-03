package com.huawei.wuqf;

import org.apache.hadoop.hbase.client.HBaseAdmin;

import java.io.IOException;

/**
 * Created by wuqf on 16-9-13.
 */
interface IHbaseClient {
    //void createTable(HBaseAdmin hBaseAdmin, String tableName,List<String> columnFamilyNames) throws IOException;

    void insertData(String tableName) throws IOException;

    //void bulkPut(String tablename, String rowkey) throws IOException;

    void dropTable(HBaseAdmin hBaseAdmin, String tableName) throws IOException;

    void deleteRow(String tablename, String rowkey) throws IOException;

    void deleteByCondition(String tablename, String rowkey) throws IOException;

    void queryAll(String tableName) throws IOException;

    void queryByRowKey(String tableName) throws IOException;

    void queryByColumnValue(String tableName) throws IOException;

    void queryByFilters(String tableName) throws IOException;

}
