package com.huawei.wuqf;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.client.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wuqf on 10/4/16.
 */
public class HbaseClient<T> implements IHbaseClient {

    private static Configuration configuration;
    private static HBaseAdmin hBaseAdmin;

    static {
        configuration = HBaseConfiguration.create();
        configuration.set("hbase.zookeeper.properity.clientPort", "2181");
        configuration.set("hbase.zookeeper.quorum", "192.168.1.3");
        try {
            hBaseAdmin = new HBaseAdmin(configuration);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void createTable(String tableName, List<String> columnFamilyNames) throws IOException {
        boolean isExsits = hBaseAdmin.tableExists(tableName);
        if (isExsits) {
            return;
        }
        HTableDescriptor descriptor = new HTableDescriptor(tableName);
        for (int i = 0; i < columnFamilyNames.size(); i++) {
            descriptor.addFamily(new HColumnDescriptor(columnFamilyNames.get(i)));
        }
        hBaseAdmin.createTable(descriptor);
    }

    public void bulkPut(String tablename, String columnFamilyName, String rowKey, List<T> data) throws IOException {
        HConnection hconnection = HConnectionManager.createConnection(configuration);
        HTableInterface hTableInterface = hconnection.getTable(tablename);
        List list = new ArrayList();
        for (int i = 0; i < data.size(); i++) {
            Put put = new Put((rowKey + i).getBytes());
            put.addColumn(columnFamilyName.getBytes(), ("column").getBytes(), Utils.bean2ByteArray(data.get(i)));
            list.add(put);
        }
        hTableInterface.put(list);
        hconnection.close();
    }

    public void insertData(String tableName) throws IOException {

    }

    public void dropTable(HBaseAdmin hBaseAdmin, String tableName) throws IOException {

    }

    public void deleteRow(String tablename, String rowkey) throws IOException {

    }

    public void deleteByCondition(String tablename, String rowkey) throws IOException {

    }

    public void queryAll(String tableName) throws IOException {

    }

    public void queryByRowKey(String tableName) throws IOException {

    }

    public void queryByColumnValue(String tableName) throws IOException {

    }

    public void queryByFilters(String tableName) throws IOException {

    }
}
