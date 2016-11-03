package com.huawei.wuqf;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.mapreduce.TableInputFormat;
import org.apache.hadoop.hbase.protobuf.ProtobufUtil;
import org.apache.hadoop.hbase.protobuf.generated.ClientProtos;
import org.apache.hadoop.hbase.util.Base64;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;

/**
 * Hello world!
 *
 */
public class Transform {
    public static void main(String[] args) {
        SparkConf sparkConf=new SparkConf().setAppName("wordCount");

        JavaSparkContext sc = new JavaSparkContext(sparkConf);

        Configuration conf = HBaseConfiguration.create();
        Scan scan = new Scan();
        scan.addFamily(Bytes.toBytes("cfns"));

        try {
            String tableName = "user";
            conf.set(TableInputFormat.INPUT_TABLE, tableName);
            ClientProtos.Scan proto = ProtobufUtil.toScan(scan);
            String ScanToString = Base64.encodeBytes(proto.toByteArray());
            conf.set(TableInputFormat.SCAN, ScanToString);

//            JavaPairRDD<ImmutableBytesWritable, Result> myRDD =
//                    sc.newAPIHadoopRDD(conf, TableInputFormat.class,
//                            ImmutableBytesWritable.class, Region.FlushResult.Result.class);
//            System.out.println(myRDD.count());

        }
            catch(Exception e){
                e.printStackTrace();
            }

    }
}
