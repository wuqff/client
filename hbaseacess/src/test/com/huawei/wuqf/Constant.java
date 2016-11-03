package com.huawei.wuqf;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by wuqf on 16-9-14.
 */
public interface Constant {
    String tableName = "wuqf";

    String rowKey1 = "rowKey1";
    String rowKey2 = "rowKey2";
    String[] rowKeys = {"rowKey1", "rowKey2", "rowKey3"};

    String columnFamily1 = "columnFamily1";
    String columnFamily2 = "columnFamily2";
    String columnFamily3 = "columnFamily3";

    List<String> columnFamilyNames=new ArrayList<String>(Arrays.asList("columnFamily1","columnFamily1","columnFamily1"));

    String column1 = "column1";
    String column2 = "column2";
    String column3 = "column3";

    String value1 = "value1";
    String value2 = "value2";
    String value3 = "value3";
}
