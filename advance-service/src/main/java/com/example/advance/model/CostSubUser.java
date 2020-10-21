package com.example.advance.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CostSubUser {
    @JsonProperty(value = "subPin")
    private String pin;
}
