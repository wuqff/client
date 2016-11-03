package com.huawei.wuqf;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wuqf on 16-9-14.
 */
public class HbaseSelectorTest {
    private static Configuration configuration;
    private static HBaseAdmin hBaseAdmin;

    static {
        configuration = HBaseConfiguration.create();
        configuration.set("hbase.zookeeper.properity.clientPort", "2181");
        configuration.set("hbase.zookeeper.quorum", "192.168.1.107");
    }

    private static HbaseSelector selector = new HbaseSelector();

    public static void main(String[] args) throws IOException {
        selector.selectRowKey(Constant.tableName, Constant.rowKey2);
        selector.selectRowKeyFalimy(Constant.tableName, Constant.rowKey2, Constant.columnFamily1);
        selector.selectRowKeyFalimyColumn(Constant.tableName, Constant.rowKey2, Constant.columnFamily1, Constant.column1);

        List<String> arr = new ArrayList();
        selector.selectFilter(Constant.tableName, arr);

    }
}
