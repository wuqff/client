package com.huawei.wuqf;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by wuqf on 10/4/16.
 */
public class HbaseClient implements IHbaseClient {

    private  Configuration configuration;
    private  Admin hBaseAdmin;

    private Connection connection ;

    public HbaseClient(String zookeeperIp,String zookeeperPort,int coonnectionPoolSize){

        ExecutorService executor = Executors.newFixedThreadPool(coonnectionPoolSize);
        configuration = HBaseConfiguration.create();
        configuration.set("hbase.zookeeper.properity.clientPort", zookeeperPort);
        configuration.set("hbase.zookeeper.quorum", zookeeperIp);
        try {
            connection= ConnectionFactory.createConnection(configuration, executor);
            hBaseAdmin = connection.getAdmin();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public IHbaseClient createTable(String tableName, List<String> columnFamilyNames) throws IOException {
        TableName tn=TableName.valueOf(tableName);
        boolean isExsits = connection.getAdmin().tableExists(tn);
        if (isExsits) {
            return this;
        }
        HTableDescriptor descriptor = new HTableDescriptor(tn);
        for (int i = 0; i < columnFamilyNames.size(); i++) {
            descriptor.addFamily(new HColumnDescriptor(columnFamilyNames.get(i)));
        }
        hBaseAdmin.createTable(descriptor);
        return this;
    }

    public IHbaseClient bulkPut(String tableName, String family,String qualifier, String rowKey, List datas) throws IOException {
        TableName tn=TableName.valueOf(tableName);
        Table table = connection.getTable(tn);
        List list = new ArrayList();
        for (int i = 0; i < datas.size(); i++) {
            Put put = new Put((rowKey + i).getBytes());
            put.addColumn(family.getBytes(), qualifier.getBytes(), Utils.bean2ByteArray(datas.get(i)));
            list.add(put);
        }
        table.put(list);
        return this;
    }

    public IHbaseClient put(String tableName, String family,String qualifier, String rowKey,Object data) throws IOException {
        TableName tn=TableName.valueOf(tableName);
        Table table = connection.getTable(tn);
        Put put = new Put((rowKey ).getBytes());
        put.addColumn(family.getBytes(), qualifier.getBytes(), Utils.bean2ByteArray(data));
        table.put(put);
        return this;
    }

    public IHbaseClient dropTable(String tableName) throws IOException {
        TableName tn=TableName.valueOf(tableName);
        hBaseAdmin.deleteTable(tn);
        return this;
    }

    public IHbaseClient deleteRow(String tableName, String row) throws IOException {
        TableName tn=TableName.valueOf(tableName);
        Table table = connection.getTable(tn);
        Delete delete=new Delete(row.getBytes());
        table.delete(delete);
        return this;
    }

    public IHbaseClient deleteByCondition(String tablename, String rowkey) throws IOException {
        return null;
    }

    public IHbaseClient queryAll(String tableName) throws IOException {
        return null;
    }

    public IHbaseClient queryByRowKey(String tableName) throws IOException {
        return null;
    }

    public IHbaseClient queryByColumnValue(String tableName) throws IOException {
        return null;
    }

    public IHbaseClient queryByFilters(String tableName) throws IOException {
        return null;
    }
}
