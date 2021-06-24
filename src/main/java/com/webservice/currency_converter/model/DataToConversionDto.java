package com.webservice.currency_converter.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class DataToConversionDto {

    @JsonProperty("input")
    private String codeInput;
    @JsonProperty("output")
    private String codeOutput;
    @JsonProperty("amount")
    private double amountInput;
}