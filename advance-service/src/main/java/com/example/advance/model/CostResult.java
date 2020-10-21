package com.example.advance.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CostResult {
    private CostDetail data;
    private long updatetime;
    private CostStatus status;
}