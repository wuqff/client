package com.huawei.wuqf;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.filter.CompareFilter;
import org.apache.hadoop.hbase.filter.Filter;
import org.apache.hadoop.hbase.filter.FilterList;
import org.apache.hadoop.hbase.filter.SingleColumnValueFilter;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wuqf on 16-9-13.
 */
public class HbaseClientTest implements IHbaseClient {

    public static Configuration configuration;

    static {
        configuration = HBaseConfiguration.create();
        configuration.set("hbase.zookeeper.properity.clientPort", "2181");
        configuration.set("hbase.zookeeper.quorum", "localhost");
    }

    private static HbaseClientTest client = new HbaseClientTest();

    public static void main(String[] args) throws IOException {
        HBaseAdmin hBaseAdmin = new HBaseAdmin(configuration);
        client.dropTable(hBaseAdmin, Constant.tableName);
        client.createTable(hBaseAdmin, Constant.tableName,Constant.columnFamilyNames);
        //client.insertData(Constant.tableName);
        client.bulkPut(Constant.tableName,Constant.rowKey1);
        client.queryAll(Constant.tableName);
        client.queryByRowKey(Constant.tableName);
        client.queryByColumnValue(Constant.tableName);
        client.queryByFilters(Constant.tableName);
        //client.deleteByCondition(Constant.tableName, Constant.rowKey1);
        //client.deleteRow(Constant.tableName, Constant.rowKey2);
        //client.dropTable(hBaseAdmin,tableName);
    }

    public void createTable(HBaseAdmin hBaseAdmin, final String tableName,List<String> columnFamilyNames) throws IOException {


        boolean isExsits = hBaseAdmin.tableExists(tableName);
        if (isExsits) {
            hBaseAdmin.disableTable(tableName);
            hBaseAdmin.deleteTable(tableName);
        }


        HTableDescriptor descriptor = new HTableDescriptor(tableName);
        descriptor.addFamily(new HColumnDescriptor(Constant.columnFamily1));
        descriptor.addFamily(new HColumnDescriptor(Constant.columnFamily2));
        descriptor.addFamily(new HColumnDescriptor(Constant.columnFamily3));
        hBaseAdmin.createTable(descriptor);
    }

    public void insertData(String tableName) throws IOException {
        HConnection hconnection = HConnectionManager.createConnection(configuration);
        HTableInterface hTableInterface = hconnection.getTable(tableName);

        for (String rowKey : Constant.rowKeys) {
            Put put = new Put(rowKey.getBytes());
            put.addColumn(Constant.columnFamily1.getBytes(), Constant.column1.getBytes(), Constant.value1.getBytes());
            put.addColumn(Constant.columnFamily2.getBytes(), Constant.column2.getBytes(), Constant.value2.getBytes());
            put.addColumn(Constant.columnFamily3.getBytes(), Constant.column3.getBytes(), Constant.value3.getBytes());
            hTableInterface.put(put);
        }
        hconnection.close();
    }

    public void dropTable(HBaseAdmin hBaseAdmin, String tableName) throws IOException {
        boolean isExsits = hBaseAdmin.tableExists(tableName);
        if (isExsits) {
            hBaseAdmin.disableTable(tableName);
            hBaseAdmin.deleteTable(tableName);
        }
    }

    public void deleteRow(String tablename, String rowkey) throws IOException {
        HConnection hconnection = HConnectionManager.createConnection(configuration);
        HTableInterface hTableInterface = hconnection.getTable(Constant.tableName);
        List list = new ArrayList();
        Delete d1 = new Delete(Constant.rowKey1.getBytes());
        list.add(d1);
        hTableInterface.delete(list);
        hconnection.close();
    }

    public void bulkPut(String tablename, String rowkey) throws IOException {
        HConnection hconnection = HConnectionManager.createConnection(configuration);
        HTableInterface hTableInterface = hconnection.getTable(Constant.tableName);
        List list = new ArrayList();
        for (int i = 0; i < 1000; i++) {
            Put put = new Put(Constant.rowKey1.getBytes());
            put.addColumn(Constant.columnFamily1.getBytes(), ("column"+i).getBytes(), ("value"+i).getBytes());
            list.add(put);
        }
        hTableInterface.put(list);
        hconnection.close();
    }


    public void deleteByCondition(String tablename, String rowkey) throws IOException {

    }

    public void queryAll(String tableName) throws IOException {
        HConnection hConnection = HConnectionManager.createConnection(configuration);
        HTableInterface hTableInterface = hConnection.getTable(tableName);
        ResultScanner resultScanner = hTableInterface.getScanner(new Scan());
        Utils.traversalResultScanner(resultScanner);
        resultScanner.close();
    }

    public void queryByRowKey(String tableName) throws IOException {
        System.out.println("----------------queryByRowKey------------------");
        HConnection hConnection = HConnectionManager.createConnection(configuration);
        HTableInterface hTableInterface = hConnection.getTable(tableName);
        Get get = new Get(Constant.rowKey1.getBytes());
        Result result = hTableInterface.get(get);
        System.out.println();
        Utils.traversalResult(result);
    }

    public void queryByColumnValue(String tableName) throws IOException {
        System.out.println("----------------queryByColumnValue------------------");
        HConnection hConnection = HConnectionManager.createConnection(configuration);
        HTableInterface hTableInterface = hConnection.getTable(tableName);
        Filter filter = new SingleColumnValueFilter(Bytes.toBytes(Constant.columnFamily1), null, CompareFilter.CompareOp.EQUAL, Bytes.toBytes(Constant.value1));
        Scan scan = new Scan();
        scan.setFilter(filter);
        ResultScanner resultScanner = hTableInterface.getScanner(scan);

        Utils.traversalResultScanner(resultScanner);
        resultScanner.close();
    }

    public void queryByFilters(String tableName) throws IOException {
        System.out.println("----------------queryByFilters------------------");
        HConnection hConnection = HConnectionManager.createConnection(configuration);
        HTableInterface hTableInterface = hConnection.getTable(tableName);
        FilterList filterList = createFilterList();

        Scan scan = new Scan();
        scan.setFilter(filterList);
        ResultScanner resultScanner = hTableInterface.getScanner(scan);
        Utils.traversalResultScanner(resultScanner);
        resultScanner.close();
    }

    private FilterList createFilterList() {
        List<Filter> filters = new ArrayList();

        Filter filter1 = new SingleColumnValueFilter(Bytes
                .toBytes(Constant.columnFamily1), null, CompareFilter.CompareOp.EQUAL, Bytes
                .toBytes(Constant.value1));
        filters.add(filter1);

        Filter filter2 = new SingleColumnValueFilter(Bytes
                .toBytes(Constant.columnFamily2), null, CompareFilter.CompareOp.EQUAL, Bytes
                .toBytes(Constant.value2));
        filters.add(filter2);

        Filter filter3 = new SingleColumnValueFilter(Bytes
                .toBytes(Constant.columnFamily3), null, CompareFilter.CompareOp.EQUAL, Bytes
                .toBytes(Constant.value3));
        boolean add;
        if (filters.add(filter3)) add = true;
        else add = false;

        FilterList filterList1 = new FilterList(filters);
        return filterList1;
    }
}
