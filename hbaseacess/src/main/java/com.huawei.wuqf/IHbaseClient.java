package com.huawei.wuqf;

import org.apache.hadoop.hbase.client.HBaseAdmin;

import java.io.IOException;
import java.util.List;

/**
 * Created by wuqf on 16-9-13.
 */
interface IHbaseClient {
    /**
     * 创建表，如果表已经存在，直接返回
     * @param tableName
     * @param columnFamilyNames
     * @return
     * @throws IOException
     */
    IHbaseClient createTable( String tableName,List<String> columnFamilyNames) throws IOException;

    /**
     * 向hbase提交数据
     * @param tableName
     * @param family
     * @param qualifier
     * @param rowKey
     * @param data
     * @return
     * @throws IOException
     */
    IHbaseClient put(String tableName, String family,String qualifier, String rowKey,Object data) throws IOException;

    /**
     * 向hbase批量提交数据
     * @param tableName
     * @param family
     * @param qualifier
     * @param rowKey
     * @param data
     * @return
     * @throws IOException
     */
    IHbaseClient bulkPut(String tableName, String family,String qualifier, String rowKey,List data) throws IOException;

    /**
     * 删除表
     * @param tableName
     * @return
     * @throws IOException
     */
    IHbaseClient dropTable( String tableName) throws IOException;

    /**
     * 删除一行
     * @param tablename
     * @param rowkey
     * @return
     * @throws IOException
     */
    IHbaseClient deleteRow(String tablename, String rowkey) throws IOException;

    IHbaseClient deleteByCondition(String tablename, String rowkey) throws IOException;

    IHbaseClient queryAll(String tableName) throws IOException;

    IHbaseClient queryByRowKey(String tableName) throws IOException;

    IHbaseClient queryByColumnValue(String tableName) throws IOException;

    IHbaseClient queryByFilters(String tableName) throws IOException;

}
