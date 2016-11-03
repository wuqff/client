package com.huawei.wuqf;

import java.io.IOException;
import java.util.List;

/**
 * Created by wuqf on 16-9-14.
 */
public interface IHbaseSelector {
    void selectRowKey(String tableName, String rowKey) throws IOException;

    void selectRowKeyFalimy(String tableName, String rowKey, String family) throws IOException;

    void selectRowKeyFalimyColumn(String tableName, String rowKey, String falimy, String column) throws IOException;

    void selectFilter(String tableName, List<String> arr) throws IOException;

}
