package com.example.advance.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Data
@Builder
public class CostDetail {
    private Map<String, String> storage;
    private Map<String, String> storageresource;
    @JsonProperty(value = "jd_oss")
    private Map<String, String> jdOss;

    private CostSubUser costSubUser;

    //private Map<String, Map<String, String>> reource;
}