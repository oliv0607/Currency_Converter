package com.webservice.currency_converter.webclient.currency;

import com.webservice.currency_converter.error.ApiException;
import com.webservice.currency_converter.model.DataToConversionDto;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CurrencyClientTest {

    private CurrencyClient currencyClient;

    @Before
    public void beforeTest() {

        currencyClient = new CurrencyClient();
    }

    @Test
    void should_convert_Currency() {

        beforeTest();

        assertEquals((currencyClient.convertCurrency(new DataToConversionDto("usd", "pln", 100)).getAmountOutput()), 366.92);
        assertEquals((currencyClient.convertCurrency(new DataToConversionDto("pln", "eur", 100)).getAmountOutput()), 22.31);
        assertEquals((currencyClient.convertCurrency(new DataToConversionDto("GBP", "eur", 100)).getAmountOutput()), 114.6);
    }

    @Test()
    void should_throw_exception() {

        beforeTest();

        assertThrows(ApiException.class, () -> currencyClient.convertCurrency(new DataToConversionDto("usd", "pln", -100)));
        assertThrows(ApiException.class, () -> currencyClient.convertCurrency(new DataToConversionDto("sek", "pln", 100)));
    }
}