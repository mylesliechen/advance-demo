package com.example.parquet;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.parquet.hadoop.util.HadoopStreams;
import org.apache.parquet.io.InputFile;
import org.apache.parquet.io.SeekableInputStream;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class LocalFileInputFile implements InputFile {
    private final FileSystem fs;
    //    private final FileStatus stat;
    private final Configuration conf;
    private String path = "/Users/chenxue198/Downloads/par1.parquet";


    public LocalFileInputFile(String file, Configuration conf) throws IOException {
        Path path = new Path(file);
        this.fs = path.getFileSystem(new Configuration());
//        this.stat = fileStatus;
        this.conf = conf;
        this.path = file;

    }

    @Override
    public long getLength() throws IOException {
        return Files.readAllBytes(new File("/Users/chenxue198/Downloads/par1.parquet").toPath()).length;
    }

    @Override
    public SeekableInputStream newStream() throws IOException {
        return HadoopStreams.wrap(fs.open(new Path(this.path)));
    }
}
