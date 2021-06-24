package com.webservice.currency_converter.model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CurrencyDto {

    private String _code;
    private double _bid;
    private double _ask;
}
