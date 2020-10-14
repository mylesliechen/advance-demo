package com.example.parquet;

import org.apache.parquet.hadoop.metadata.ParquetMetadata;

public class ParquetMetaWithFooter {
    ParquetMetadata parquetMetadata;
    long footerIndex;
    int footerLength;

    public ParquetMetaWithFooter(ParquetMetadata parquetMetadata, long footerIndex, int footerLength) {
        this.parquetMetadata = parquetMetadata;
        this.footerIndex = footerIndex;
        this.footerLength = footerLength;
    }

    public ParquetMetadata getParquetMetadata() {
        return parquetMetadata;
    }

    public void setParquetMetadata(ParquetMetadata parquetMetadata) {
        this.parquetMetadata = parquetMetadata;
    }

    public long getFooterIndex() {
        return footerIndex;
    }

    public void setFooterIndex(int footerIndex) {
        this.footerIndex = footerIndex;
    }

    public int getFooterLength() {
        return footerLength;
    }

    public void setFooterLength(int footerLength) {
        this.footerLength = footerLength;
    }
}
