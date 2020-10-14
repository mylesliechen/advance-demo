package com.example.parquet;


import com.alibaba.fastjson.JSON;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.*;
import org.apache.parquet.ParquetReadOptions;
import org.apache.parquet.example.data.Group;
import org.apache.parquet.hadoop.*;
import org.apache.parquet.hadoop.example.ExampleParquetWriter;
import org.apache.parquet.hadoop.example.GroupReadSupport;
import org.apache.parquet.hadoop.metadata.FileMetaData;
import org.apache.parquet.hadoop.metadata.GlobalMetaData;
import org.apache.parquet.hadoop.metadata.ParquetMetadata;
import org.apache.parquet.hadoop.util.HadoopInputFile;
import org.apache.parquet.schema.MessageType;

import java.io.IOException;
import java.util.*;

public class merge {

    private static FileSystem fileSystem;

    static {
//        System.setProperty("HADOOP_USER_NAME", "root");
        try {
//            String s3a = "s3a://bucket/inventory/";
            String pathlocal = "../par2.parquet";
            Path path = new Path(pathlocal);
            fileSystem = path.getFileSystem(getConfiguration());

        } catch (IOException e) {
            System.exit(1);
        }
    }

    public static void main(String[] args) throws Exception {
        mergeParquet("/par2.parquet");
    }


    private static void mergeParquet(String dir) throws Exception {
        MessageType messageType = checkSchemaSame(dir);
        if (messageType == null) {//MessageType不一致
            return;
        }

        List<Path> parquetPaths = getParquetPaths(dir);

        String dest = "/merged12par.parquet";
        Path destPath = new Path(dest);
        ParquetWriter parquetWriter = getParquetWriter(messageType, destPath);
        ParquetReader<Group> parquetReader;
        Group book;


        for (Path path : parquetPaths) {
            parquetReader = getParquetReader(path);
            while ((book = parquetReader.read()) != null) {
                System.out.println(JSON.toJSONString(book));
                parquetWriter.write(book);
            }
        }
        parquetWriter.close();//写meta

//        if (fileSystem.exists(destPath)) {
//            FileStatus fileStatus = fileSystem.getFileStatus(destPath);
//            if (fileStatus.getLen() <= 1024) {
//                System.err.println(dir + "files len to small pleach ack need delete");
//            } else {
//                for (Path path : parquetPaths) {
//                    fileSystem.delete(path, false);
//                }
//            }
//        }
    }

    public static void getFooter(String fileName) throws IOException {
        Path path = new Path(fileName);
        FileStatus fileStatus = fileSystem.getFileStatus(path);

        List<Footer> readSummaryFile = ParquetFileReader.readSummaryFile(getConfiguration(), fileStatus);
        for (Footer footer : readSummaryFile) {
            System.out.println("meta====" + footer.getParquetMetadata().toString());
            System.out.println("footer===" + footer.toString());
        }
    }

    static MessageType mergeInto(MessageType toMerge, MessageType mergedSchema, boolean strict) {
        if (mergedSchema == null) {
            return toMerge;
        }

        return mergedSchema.union(toMerge, strict);
    }

    static GlobalMetaData mergeInto(
            FileMetaData toMerge,
            GlobalMetaData mergedMetadata,
            boolean strict) {
        MessageType schema = null;
        Map<String, Set<String>> newKeyValues = new HashMap<String, Set<String>>();
        Set<String> createdBy = new HashSet<String>();
        if (mergedMetadata != null) {
            schema = mergedMetadata.getSchema();
            newKeyValues.putAll(mergedMetadata.getKeyValueMetaData());
            createdBy.addAll(mergedMetadata.getCreatedBy());
        }
        if ((schema == null && toMerge.getSchema() != null)
                || (schema != null && !schema.equals(toMerge.getSchema()))) {
            schema = mergeInto(toMerge.getSchema(), schema, strict);
        }
        for (Map.Entry<String, String> entry : toMerge.getKeyValueMetaData().entrySet()) {
            Set<String> values = newKeyValues.get(entry.getKey());
            if (values == null) {
                values = new LinkedHashSet<String>();
                newKeyValues.put(entry.getKey(), values);
            }
            values.add(entry.getValue());
        }
        createdBy.add(toMerge.getCreatedBy());
        return new GlobalMetaData(
                schema,
                newKeyValues,
                createdBy);
    }

    public static List<Path> getParquetPaths(String dir) throws Exception {
        Path dirPath = new Path(dir);
//        RemoteIterator<LocatedFileStatus> locatedFileStatusRemoteIterator = fileSystem.listFiles(dirPath, false);
        List<Path> fileList = new ArrayList<Path>();
//        while (locatedFileStatusRemoteIterator.hasNext()) {
//            LocatedFileStatus next = locatedFileStatusRemoteIterator.next();
//            Path path = next.getPath();
//            FileStatus fileStatus = fileSystem.getFileStatus(path);
//
//            if (fileStatus.isFile() && path.getName().endsWith(".parquet")) {//如果是parquet文件
//                fileList.add(path);
//            }
//        }
        fileList.add(new Path("/par1.parquet"));
        fileList.add(new Path("/par2.parquet"));

        return fileList;
    }

    private static MessageType checkSchemaSame(String dir) throws Exception {
        List<MessageType> groupTypes = getMessageType(dir);
        int size = groupTypes.size();
        if (size == 0 || size == 1) {//0个和1个都不处理
            return null;
        }
        MessageType groupType = groupTypes.get(0);
        for (MessageType gt : groupTypes) {
            if (!groupType.equals(gt)) {
                return null;
            }
        }
        return groupType;
    }

    private static List<MessageType> getMessageType(String dir) throws Exception {
        List<Path> parquetPaths = getParquetPaths(dir);
        LinkedList<MessageType> groupTypes = new LinkedList<>();
        for (Path path : parquetPaths) {
            groupTypes.add(getMessageType(path));
        }
        return groupTypes;
    }

    public static MessageType getMessageType(Path path) throws IOException {
        HadoopInputFile hadoopInputFile = HadoopInputFile.fromPath(path, getConfiguration());
        ParquetFileReader parquetFileReader = ParquetFileReader.open(hadoopInputFile, ParquetReadOptions.builder().build());
        ParquetMetadata metaData = parquetFileReader.getFooter();
        MessageType schema = metaData.getFileMetaData().getSchema();
        parquetFileReader.close();
        return schema;
    }

    private static Configuration getConfiguration() {
        Configuration configuration = new Configuration();


        return configuration;
    }

    public static ParquetReader getParquetReader(Path path) throws IOException {
        GroupReadSupport readSupport = new GroupReadSupport();
        ParquetReader.Builder<Group> builder = ParquetReader.builder(readSupport, path);
        builder.withConf(getConfiguration());
        ParquetReader<Group> parquetReader = builder.build();
        return parquetReader;
    }

    public static ParquetWriter getParquetWriter(MessageType schema, Path path) throws IOException {
        ExampleParquetWriter.Builder writebuilder = ExampleParquetWriter.builder(path);
        writebuilder.withWriteMode(ParquetFileWriter.Mode.OVERWRITE);
        writebuilder.withConf(getConfiguration());
        writebuilder.withType(schema);
        return writebuilder.build();
    }
}
