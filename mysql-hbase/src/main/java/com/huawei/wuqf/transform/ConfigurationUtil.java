package com.huawei.wuqf.transform;

import java.io.FileInputStream;
import java.util.Properties;

/**
 * Created by wuqf on 16-10-3.
 */
public class ConfigurationUtil {

    private static Properties props;
    private static String configPath =System.getProperty("user.dir" )+"/mysql-hbase/src/main/resources/config.properties";

    public static Properties getProps() throws Exception{

        props=new Properties();
        if(!props.contains("jdbc.url")){
                props.load(new FileInputStream(configPath));

        }
        return props;
    }
    public static void main(String[] args) throws Exception{
        System.out.println(System.getProperty("user.dir" ));
        getProps();
    }
}
