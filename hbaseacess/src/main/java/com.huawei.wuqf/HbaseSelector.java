package com.huawei.wuqf;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.filter.FilterList;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;
import java.util.List;

/**
 * Created by wuqf on 16-9-14.
 */
public class HbaseSelector implements IHbaseSelector {
    private static Configuration configuration;
    private static HBaseAdmin hBaseAdmin;

    static {
        configuration = HBaseConfiguration.create();
        configuration.set("hbase.zookeeper.properity.clientPort", "2181");
        configuration.set("hbase.zookeeper.quorum", "192.168.1.107");
    }

    private static HbaseSelector selector = new HbaseSelector();

    public void selectRowKey(String tableName, String rowKey) throws IOException {

        System.out.println("----------------selectRowKey-----------------");
        HConnection hConnection = HConnectionManager.createConnection(configuration);
        HTableInterface hTableInterface = hConnection.getTable(tableName);

        Get g = new Get(rowKey.getBytes());
        Result rs = hTableInterface.get(g);
        Utils.traversalResult(rs);

        hConnection.close();
    }

    public void selectRowKeyFalimy(String tableName, String rowKey, String family) throws IOException {
        System.out.println("----------------selectRowKeyFalimy-----------------");
        HConnection hConnection = HConnectionManager.createConnection(configuration);
        HTableInterface hTableInterface = hConnection.getTable(tableName);
        Get g = new Get(rowKey.getBytes());
        g.addFamily(Bytes.toBytes(family));
        Result rs = hTableInterface.get(g);
        Utils.traversalResult(rs);

        hConnection.close();
    }

    public void selectRowKeyFalimyColumn(String tableName, String rowKey, String falimy, String column) throws IOException {
        System.out.println("----------------selectRowKeyFalimyColumn-----------------");
        HConnection hConnection = HConnectionManager.createConnection(configuration);
        HTableInterface hTableInterface = hConnection.getTable(tableName);
        Get g = new Get(rowKey.getBytes());
        g.addColumn(falimy.getBytes(), column.getBytes());
        Result rs = hTableInterface.get(g);

        Utils.traversalResult(rs);

        hConnection.close();
    }

    public void selectFilter(String tableName, List<String> arr) throws IOException {
        System.out.println("----------------selectFilter-----------------");
        HConnection hConnection = HConnectionManager.createConnection(configuration);
        HTableInterface hTableInterface = hConnection.getTable(tableName);
        FilterList filterList=new FilterList();

    }
}
