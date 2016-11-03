package com.huawei.wuqf.transform;

import com.huawei.wuqf.HbaseClient;
import com.huawei.wuqf.User;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

/**
 * Created by wuqf on 10/4/16.
 */
public class Transform {

    private static HbaseClient hbaseClient = new HbaseClient();

    public static void main(String[] args) throws Exception {
        Properties pros = ConfigurationUtil.getProps();
        String url = String.valueOf(pros.get("jdbc.url"));
        String userName = String.valueOf(pros.get("jdbc.username"));
        String password = String.valueOf(pros.get("jdbc.password"));
        UserClient userClient = new UserClient(url, userName, password);

        Connection conn = userClient.getConn();
        String sql = "select * from t_userlist";
        List<User> users = userClient.excuteQuery(conn, sql);
        userClient.closeConn(conn);

        String tableName = "user";
        List<String> columnFamilyNames = new ArrayList(Arrays.asList("cfns"));
        String rowKey = "userRowKey";
        hbaseClient.createTable(tableName, columnFamilyNames);
        hbaseClient.bulkPut(tableName, columnFamilyNames.get(0), rowKey, users);
    }

}
