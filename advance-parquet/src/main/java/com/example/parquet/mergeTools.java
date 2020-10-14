package com.example.parquet;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.parquet.ParquetReadOptions;
import org.apache.parquet.bytes.ByteBufferInputStream;
import org.apache.parquet.format.Util;
import org.apache.parquet.format.converter.ParquetMetadataConverter;
import org.apache.parquet.hadoop.ParquetFileReader;
import org.apache.parquet.hadoop.ParquetFileWriter;
import org.apache.parquet.hadoop.metadata.FileMetaData;
import org.apache.parquet.hadoop.metadata.ParquetMetadata;
import org.apache.parquet.hadoop.util.HadoopInputFile;
import org.apache.parquet.io.InputFile;
import org.apache.parquet.io.SeekableInputStream;
import org.apache.parquet.schema.MessageType;
import org.apache.parquet.schema.MessageTypeParser;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.apache.parquet.bytes.BytesUtils.readIntLittleEndian;

public class mergeTools {
    private static final int CURRENT_VERSION = 1;
    public static final String MAGIC_STR = "PAR1";
    public static final byte[] MAGIC = MAGIC_STR.getBytes(StandardCharsets.US_ASCII);

    public static void main(String[] args) throws IOException {
        String schemaStr = "message schema {" + "optional int64 log_id;"
                + "optional binary idc_id;" + "optional int64 house_id;"
                + "}";
        MessageType schema = MessageTypeParser.parseMessageType(schemaStr);
        Path path1 = new Path("/par1.parquet");
        Path path2 = new Path("/par2.parquet");
        List<Path> inputFiles = new ArrayList<>();
        inputFiles.add(path1);
        inputFiles.add(path2);
        ParquetMetadata parquetMetadata1 = ParquetFileWriter.mergeMetadataFiles(inputFiles, new Configuration());
        System.out.println("value===" + ParquetMetadata.toPrettyJSON(parquetMetadata1));

//        FileMetaData mergedMeta = mergedMetadata(inputFiles);

//        Path outputFile = new Path("/mergetools.parquet");
//         Merge data
//        ParquetFileWriter writer = new ParquetFileWriter(new Configuration(),
//                mergedMeta.getSchema(), outputFile, ParquetFileWriter.Mode.CREATE);
//        writer.start();
//        boolean tooSmallFilesMerged = false;
//        for (Path input : inputFiles) {

//            writer.appendFile(HadoopInputFile.fromPath(input, new Configuration()));
//        }
//        writer.end(mergedMeta.getKeyValueMetaData());
    }

    private static FileMetaData mergedMetadata(List<Path> inputFiles) throws IOException {
        return ParquetFileWriter.mergeMetadataFiles(inputFiles, new Configuration()).getFileMetaData();
    }


//        HadoopInputFile hadoopInputFile = HadoopInputFile.fromPath(path1, new Configuration());
//        ParquetFileReader parquetFileReader = ParquetFileReader.open(hadoopInputFile, ParquetReadOptions.builder().build());
//        ParquetMetadata metaData = parquetFileReader.getFooter();
//        List<BlockMetaData> blocks = metaData.getBlocks();
//        List<BlockMetaData> allBlocks = new ArrayList<>(blocks);//1block
//
//        HadoopInputFile hadoopInputFile1 = HadoopInputFile.fromPath(path2, new Configuration());
//        ParquetFileReader parquetFileReader2 = ParquetFileReader.open(hadoopInputFile1, ParquetReadOptions.builder().build());
//        ParquetMetadata metaData2 = parquetFileReader2.getFooter();
//        List<BlockMetaData> blocks1 = metaData2.getBlocks();
//        allBlocks.addAll(blocks1);//2block
//
//        ParquetMetadataConverter metadataConverter = new ParquetMetadataConverter();
//
//        Map<String, String> keyValueMetaData = new HashMap<>();
//        keyValueMetaData.put("writer.model.name", "example");
//
//        ParquetMetadata footer = new ParquetMetadata(new FileMetaData(schema, keyValueMetaData, Version.FULL_VERSION), allBlocks);//todo blocks 需要重新组织
//        List<BlockMetaData> blocks2 = footer.getBlocks();
//        for (BlockMetaData blockMetaData : blocks2) {
//            List<ColumnChunkMetaData> columns = blockMetaData.getColumns();
////            for (ColumnChunkMetaData column : columns) {//log_id,idc_id,House_id
//            columns.get(0).setColumnIndexReference(new IndexReference(139, 31));
//            columns.get(0).setOffsetIndexReference(new IndexReference(224, 10));
////            columns.get(0)
//
//            columns.get(1).setColumnIndexReference(new IndexReference(170, 23));
//            columns.get(1).setOffsetIndexReference(new IndexReference(234, 10));
//
//            columns.get(2).setColumnIndexReference(new IndexReference(193, 31));
//            columns.get(2).setOffsetIndexReference(new IndexReference(224, 11));
////            }
//
//        }
//
//        HadoopInputFile hadoopInputFile = HadoopInputFile.fromPath(new Path("/par2.parquet"), new Configuration());
//        ParquetFileReader parquetFileReader = ParquetFileReader.open(hadoopInputFile, ParquetReadOptions.builder().build());
//        ParquetMetadata footer = parquetFileReader.getFooter();
//
//        System.out.println(footer.toString());
//        FileOutputStream o = null;
//        try {
//            ParquetMetadataConverter metadataConverter = new ParquetMetadataConverter();
//
//            o = new FileOutputStream("/meta12.parquet");
//            org.apache.parquet.format.FileMetaData parquetMetadata = metadataConverter.toParquetMetadata(CURRENT_VERSION, footer);
//
//            Util.writeFileMetaData(parquetMetadata, o);
////            BytesUtils.writeIntLittleEndian(o, 475);
//            o.write(MAGIC);
//        } catch (Exception ex) {
//
//        }
//        Our
//        FileMetaData mergedMeta = ParquetFileWriter.mergeMetadataFiles(inputFiles, new Configuration()).getFileMetaData();//合并footer信息，兼容
//
//        ParquetFileWriter writer = new ParquetFileWriter(new Configuration(),
//                mergedMeta.getSchema(), outputFile, ParquetFileWriter.Mode.OVERWRITE);
//
//        writer.start();
//        for (Path input : inputFiles) {
//            writer.appendFile(HadoopInputFile.fromPath(input, new Configuration()));//new n次reader，根据reader里的footer来读取信息，
//            // 然后使用write写入，write维护新的scheme和索引信息
//        }
//
//        writer.end(mergedMeta.getKeyValueMetaData());


    private static ParquetMetaWithFooter readFooter(InputFile file, ParquetReadOptions options, SeekableInputStream f, ParquetMetadataConverter converter) throws IOException {
        long fileLen = file.getLength();
//        LOG.debug("File length {}", fileLen);
        int FOOTER_LENGTH_SIZE = 4;
        if (fileLen < MAGIC.length + FOOTER_LENGTH_SIZE + MAGIC.length) { // MAGIC + data + footer + footerIndex + MAGIC
            throw new RuntimeException(file.toString() + " is not a Parquet file (too small length: " + fileLen + ")");
        }
        long footerLengthIndex = fileLen - FOOTER_LENGTH_SIZE - MAGIC.length;

        f.seek(footerLengthIndex);
        int footerLength = readIntLittleEndian(f);
        byte[] magic = new byte[MAGIC.length];
        f.readFully(magic);
        if (!Arrays.equals(MAGIC, magic)) {
            throw new RuntimeException(file.toString() + " is not a Parquet file. expected magic number at tail " + Arrays.toString(MAGIC) + " but found " + Arrays.toString(magic));
        }
        long footerIndex = footerLengthIndex - footerLength;
//        LOG.debug("read footer length: {}, footer index: {}", footerLength, footerIndex);
        if (footerIndex < MAGIC.length || footerIndex >= footerLengthIndex) {
            throw new RuntimeException("corrupted file: the footer index is not within the file: " + footerIndex);
        }
        f.seek(footerIndex);
        // Read all the footer bytes in one time to avoid multiple read operations,
        // since it can be pretty time consuming for a single read operation in HDFS.
        ByteBuffer footerBytesBuffer = ByteBuffer.allocate(footerLength);
        f.readFully(footerBytesBuffer);
//        LOG.debug("Finished to read all footer bytes.");
        footerBytesBuffer.flip();
        InputStream footerBytesStream = ByteBufferInputStream.wrap(footerBytesBuffer);
        return new ParquetMetaWithFooter(converter.readParquetMetadata(footerBytesStream, options.getMetadataFilter()), footerIndex, footerLength);

    }

    @Test
    public void testGetFooterAndData() throws IOException {//拆分
        LocalFileInputFile localFileInputFile = new LocalFileInputFile("/par1.parquet", new Configuration());
        ParquetMetaWithFooter parquetMetaWithFooter = readFooter(localFileInputFile, ParquetReadOptions.builder().build(), localFileInputFile.newStream(), new ParquetMetadataConverter());

    }

    public void splitDataAndMeta() {

    }


    @Test
    public void readFooterAndWriteBack() throws IOException {
        Path path = new Path("/par1.parquet");
        HadoopInputFile hadoopInputFile = HadoopInputFile.fromPath(path, new Configuration());
        ParquetFileReader parquetFileReader = ParquetFileReader.open(hadoopInputFile, ParquetReadOptions.builder().build());
        ParquetMetadata metaData = parquetFileReader.getFooter();
        ParquetMetadataConverter metadataConverter = new ParquetMetadataConverter();

        //meta信息
        org.apache.parquet.format.FileMetaData parquetMetadata = metadataConverter.toParquetMetadata(CURRENT_VERSION, metaData);//有colunmIndexRefernce

//        parquetMetadata.get

        FileOutputStream fos = new FileOutputStream("/par1meta.parquet");
        Util.writeFileMetaData(parquetMetadata, fos);
        //最后8字节信息，长度可以根据meta长度拼接
//        BytesUtils.writeIntLittleEndian(fos, (int) (fos() - footerIndex));
//        fos.write(MAGIC);
        fos.close();
    }

    @Test
    public void readByte() throws IOException {
        File file = new File("/par1.parquet");
        FileInputStream fis = new FileInputStream(file);
        DataInputStream dis = new DataInputStream(fis);
        byte[] by = new byte[1000];
        dis.read(by);
        String str = new String(by);
        System.out.println(str);


//
////        byte[] reversal = reversal(readAllBytes);
//        int length = readAllBytes.length;
//        System.out.println("total - >" + length);
//
//        byte[] lastbytes = new byte[9];
//        int j = 0;
//        //0 -30 1 0
//        for (int i = length - 8 - 1; i < length; i++) {
//            lastbytes[j] = readAllBytes[i];
//            j++;
//        }
//        System.out.println(new String(lastbytes, StandardCharsets.US_ASCII));

    }

    public static byte[] reversal(byte[] bytes) {
        for (int i = 0; i < bytes.length / 2; i++) {
            byte temp = bytes[i];
            bytes[i] = bytes[bytes.length - 1 - i];
            bytes[bytes.length - 1 - i] = temp;
        }
        return bytes;
    }

    @Test
    public void testFooterDefined() throws IOException {
        int fileLen = 710;
        int footerLength = 465;
        int footerIndex = 237;
        int footerLengthIndex = 702;
        byte[] readAllBytes = Files.readAllBytes(new File("/par1.parquet").toPath());
        System.out.println(readAllBytes.length);
//
        byte[] data = new byte[237];//data长度
//        byte[] meta = new byte[486];//meta长度
        byte[] magicAndFooterLength = new byte[8];//最后8byte

        int j = 0;
        int f = 0;
        int m = 0;
        for (int i = 0; i < readAllBytes.length; i++) {
            if (i < footerIndex) {
                data[j] = readAllBytes[i];
                j++;
            }
//            if (i >= footerIndex && i < footerLengthIndex) {
//                meta[f] = readAllBytes[i];
//                f++;
//            }
            if (i >= footerLengthIndex) {
                magicAndFooterLength[m] = readAllBytes[i];
                m++;
            }
        }
        System.out.println("data:" + data.length);//data加上第一个par1
//        System.out.println("meta:" + meta.length);
        System.out.println(magicAndFooterLength.length);
        FileOutputStream fos = new FileOutputStream("/par1data.parquet");
        fos.write(data);
        fos.close();
//
//        FileOutputStream fosmeta = new FileOutputStream("/meta.parquet");
//        fosmeta.write(meta);//
//        fosmeta.close();
//
        FileOutputStream fosFooter = new FileOutputStream("/par1footer.parquet");
        fosFooter.write(magicAndFooterLength);
        fosFooter.close();
    }

    @Test
    public void testFooterDefined2() throws IOException {
        int fileLen = 682;
        int footerLength = 482;
        int footerIndex = 225;
        int footerLengthIndex = 674;
        byte[] readAllBytes = Files.readAllBytes(new File("/par2.parquet").toPath());
        System.out.println(readAllBytes.length);

        byte[] data = new byte[225];//data长度
        byte[] meta = new byte[449];//meta长度
        byte[] magicAndFooterLength = new byte[8];//最后8byte

        int j = 0;
        int f = 0;
        int m = 0;
        for (int i = 0; i < readAllBytes.length; i++) {
            if (i < footerIndex) {
                data[j] = readAllBytes[i];
                j++;
            }
            if (i >= footerIndex && i < footerLengthIndex) {
                meta[f] = readAllBytes[i];
                f++;
            }
            if (i >= footerLengthIndex) {
                magicAndFooterLength[m] = readAllBytes[i];
                m++;
            }
        }
        System.out.println("data:" + data.length);//data加上第一个par1
        System.out.println("meta:" + meta.length);
        System.out.println(magicAndFooterLength.length);
        FileOutputStream fos = new FileOutputStream("/data2.parquet");
        fos.write(data);
        fos.close();

        FileOutputStream fosmeta = new FileOutputStream("/meta2.parquet");
        fosmeta.write(meta);//
        fosmeta.close();

        FileOutputStream fosFooter = new FileOutputStream("/footer2.parquet");
        fosFooter.write(magicAndFooterLength);
        fosFooter.close();
    }


}
