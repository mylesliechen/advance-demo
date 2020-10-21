package com.example.advance.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CostStatus {
    private String code;
    private String msg;
}