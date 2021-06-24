package com.webservice.currency_converter.model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class DataAfterConversionDto {

    private double amountOutput;
}