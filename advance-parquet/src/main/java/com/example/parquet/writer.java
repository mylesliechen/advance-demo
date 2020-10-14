package com.example.parquet;

import com.alibaba.fastjson.JSON;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.LocatedFileStatus;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.fs.RemoteIterator;
import org.apache.parquet.column.ParquetProperties;
import org.apache.parquet.example.data.Group;
import org.apache.parquet.example.data.simple.SimpleGroupFactory;
import org.apache.parquet.hadoop.ParquetFileWriter;
import org.apache.parquet.hadoop.ParquetWriter;
import org.apache.parquet.hadoop.example.ExampleParquetWriter;
import org.apache.parquet.hadoop.example.GroupWriteSupport;
import org.apache.parquet.schema.MessageType;
import org.apache.parquet.schema.MessageTypeParser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class writer {

    private static String schemaStr = "message schema {" + "optional int64 log_id;"
            + "optional binary idc_id;" + "optional int64 house_id;"
            + "}";
    static MessageType schema = MessageTypeParser.parseMessageType(schemaStr);

    private static void testParquetWriter() throws IOException {

        Configuration configuration = new Configuration();
//        String endPoint = "";
//        String ak = "";//线上
//        String sk = "";

//        configuration.set("fs.s3a.endpoint", endPoint);
//        configuration.set("fs.s3a.access.key", ak);
//        configuration.set("fs.s3a.secret.key", sk);
//        String f1 = "s3a://bucket/inventory/par1.parquet";//bucket
        String f1 = "../test.parquet";//本地文件路径

        Path file = new Path(f1);

        ExampleParquetWriter.Builder builder = ExampleParquetWriter
                .builder(file).withWriteMode(ParquetFileWriter.Mode.OVERWRITE)
                .withWriterVersion(ParquetProperties.WriterVersion.PARQUET_1_0)
                .withConf(configuration)
                .withType(schema);
        GroupWriteSupport.setSchema(schema, configuration);

        ParquetWriter<Group> writer = builder.build();

        Group group = new SimpleGroupFactory(schema).newGroup();
        String[] access_log = { "111111", "22222", "33333", "44444", "55555",
                "666666", "777777", "888888", "999999", "101010" };
        group.append("log_id", Long.parseLong(access_log[0]))
                .append("idc_id", access_log[1])
                .append("house_id", Long.parseLong(access_log[2]));
        writer.write(group);
        Group group1 = new SimpleGroupFactory(schema).newGroup();
        group1.append("log_id", Long.parseLong(access_log[7]))
                .append("idc_id", access_log[8])
                .append("house_id", Long.parseLong(access_log[9]));

        System.out.println(JSON.toJSONString(group));
        writer.write(group1);
        writer.close();
    }


    public static List<Path> getPaths(Configuration conf, String src) throws Exception {
        FileSystem fs = FileSystem.get(conf);
        RemoteIterator<LocatedFileStatus> locatedFileStatusRemoteIterator = fs.listFiles(new Path(src), true);
        List<Path> fileList = new ArrayList<Path>();
        while (locatedFileStatusRemoteIterator.hasNext()) {
            LocatedFileStatus next = locatedFileStatusRemoteIterator.next();
            Path path = next.getPath();
            fileList.add(path);
        }

        return fileList;
    }


    public static void main(String[] args) throws Exception {
        testParquetWriter();
    }

}
