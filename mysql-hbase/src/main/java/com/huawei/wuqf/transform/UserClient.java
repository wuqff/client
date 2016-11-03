package com.huawei.wuqf.transform;

import com.huawei.wuqf.MysqlClient;
import com.huawei.wuqf.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wuqf on 16-10-5.
 */
public class UserClient extends MysqlClient {


    public UserClient(String url, String userName, String password) {
        super(url, userName, password);
    }

    @Override
    protected List getData(ResultSet rs) throws Exception{
        List<User> users = new ArrayList();
        while (rs.next()) {
            User u = new User();
            u.setId(rs.getInt("id"));
            u.setUserid(rs.getString("userid"));
            u.setUsername(rs.getString("username"));
            u.setRootuserid(rs.getString("rootuserid"));
            u.setRootusername(rs.getString("rootusername"));
            u.setMutualfollow(rs.getString("mutualfollow"));
            u.setVisited(rs.getInt("visited"));
            u.setInfostored(rs.getInt("infostored"));
            users.add(u);
        }
        return users;
    }

    public List<User> excuteQuery(Connection conn, String sql) throws Exception {

        PreparedStatement statement = getConn().prepareStatement(sql);
        ResultSet rs = statement.executeQuery();
        List<User> ts=getData(rs);
        rs.close();
        return ts;

    }


}
