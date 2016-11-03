package com.huawei.wuqf;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.api.java.function.Function2;

import java.util.Arrays;
import java.util.List;

/**
 * Unit test for simple Transform.
 */
public class AppTest 

{
    public static void main(String[] args){
        SparkConf conf = new SparkConf().setAppName("appName").setMaster("spark://wuqf-B85M-D2V:7077");
        JavaSparkContext sc = new JavaSparkContext(conf);
        List<Integer> data = Arrays.asList(1, 2, 3, 4, 5);
        JavaRDD<Integer> distData = sc.parallelize(data);

        JavaRDD<String> lines = sc.textFile("aa");
        JavaRDD<Integer> lineLengths=lines.map(new Function<String, Integer>() {
            @Override
            public Integer call(String s) throws Exception {
                return s.length();
            }
        });
        int totalLength=lineLengths.reduce(new Function2<Integer, Integer, Integer>() {
            @Override
            public Integer call(Integer a, Integer b) throws Exception {
                return a+b;
            }
        });

        System.out.println(totalLength);
    }
}
