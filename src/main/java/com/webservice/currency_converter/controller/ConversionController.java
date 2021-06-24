package com.webservice.currency_converter.controller;

import com.webservice.currency_converter.error.ApiException;
import com.webservice.currency_converter.model.DataAfterConversionDto;
import com.webservice.currency_converter.model.DataToConversionDto;
import com.webservice.currency_converter.service.ConversionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ConversionController {

    private final ConversionService conversionService;

    @PostMapping("/convert")
    public DataAfterConversionDto getData(@RequestBody DataToConversionDto dataToConversionDto) throws ApiException {
        return conversionService.getCurrencyConversion(dataToConversionDto);
    }
}
