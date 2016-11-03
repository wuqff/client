package com.huawei.wuqf;
/* 作者：jiaopan
 * 时间：2016.5
 * */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

public abstract class MysqlClient<T> {

    private String url;
    private String userName;
    private String password;

    public MysqlClient(String url, String userName, String password) {
        this.url=url;
        this.userName=userName;
        this.password=password;
    }

    public Connection getConn() throws Exception {

        Class.forName("com.mysql.jdbc.Driver");
        Connection conn = DriverManager.getConnection(url, userName, password);
        return conn;
    }

    public void closeConn(Connection conn) throws Exception {
        if (conn != null) {
            conn.close();
            conn = null;
        }
    }

    public List<T> excuteQuery(Connection conn,String sql) throws Exception {

        PreparedStatement statement = getConn().prepareStatement(sql);
        ResultSet rs = statement.executeQuery();
        List<T> ts=getData(rs);
        rs.close();
        return ts;

    }

    protected abstract List<T> getData(ResultSet rs) throws Exception;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
//	public static void main(String[] args){
//		//MysqlClient dbUtil=new MysqlClient();
//		try {
//			getConn();
//			System.out.println("数据库连接成功");
//		} catch (Exception e) {
//			e.printStackTrace();
//			System.out.println("数据库连接失败!");
//		}
//	}
}
