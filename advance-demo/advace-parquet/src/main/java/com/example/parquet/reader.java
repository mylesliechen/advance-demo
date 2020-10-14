package com.example.parquet;


import org.apache.hadoop.fs.Path;
import org.apache.parquet.example.data.Group;
import org.apache.parquet.hadoop.ParquetReader;
import org.apache.parquet.hadoop.example.GroupReadSupport;
import org.apache.parquet.schema.*;

import java.io.IOException;

public class reader {

    private static void readParquetFromDisk(String fileName) {

        //1、声明readSupport
        GroupReadSupport groupReadSupport = new GroupReadSupport();
        Path path = new Path(fileName);

        //2、通过parquetReader读文件
        ParquetReader<Group> reader = null;
        String all = "";
        try {
            reader = ParquetReader.builder(groupReadSupport, path).build();

            Group group = null;
            while ((group = reader.read()) != null) {
//                System.out.println(group);

                all += group;
            }
            System.out.println("parquetValue=====" + all);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } finally {
            //                reader.close();
        }
    }


    public static void main(String[] args) {
        readParquetFromDisk("/Users/chenxue198/Downloads/par2col.parquet");
    }
}