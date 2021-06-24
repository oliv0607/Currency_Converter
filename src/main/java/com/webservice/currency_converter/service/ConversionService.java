package com.webservice.currency_converter.service;

import com.webservice.currency_converter.model.DataAfterConversionDto;
import com.webservice.currency_converter.model.DataToConversionDto;
import com.webservice.currency_converter.webclient.currency.CurrencyClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class ConversionService {

    private final CurrencyClient currencyClient;

    public DataAfterConversionDto getCurrencyConversion(DataToConversionDto dataToConversionDto) {

        return currencyClient.convertCurrency(dataToConversionDto);
    }
}
