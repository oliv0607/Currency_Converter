package com.webservice.currency_converter.webclient.currency;

import com.webservice.currency_converter.error.ApiException;
import com.webservice.currency_converter.error.ErrorMessageEnteredData;
import com.webservice.currency_converter.model.CurrencyDto;
import com.webservice.currency_converter.model.DataAfterConversionDto;
import com.webservice.currency_converter.model.DataToConversionDto;
import com.webservice.currency_converter.webclient.currency.dto.OpenCurrencyConversionDto;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import java.util.Locale;

@Component
public class CurrencyClient {

    private static final String CURRENCY_URL = "http://api.nbp.pl/api/exchangerates/rates/c/{code}/last/1/?format=json";
    private final RestTemplate REST_TEMPLATE = new RestTemplate();
    private CurrencyDto currencyDto;
    private DataAfterConversionDto dataAfterConversionDto;

    public DataAfterConversionDto convertCurrency(DataToConversionDto dataToConversionDto) {

        if(dataToConversionDto.getAmountInput() <= 0) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "amountInput", ErrorMessageEnteredData.INCORRECT_INPUT_AMOUNT);
        }

        String currencyCodeIn = dataToConversionDto.getCodeInput().toLowerCase(Locale.ROOT);
        String currencyCodeOut = dataToConversionDto.getCodeOutput().toLowerCase(Locale.ROOT);

        if(currencyCodeIn.equals("pln") && (currencyCodeOut.equals("usd") || currencyCodeOut.equals("eur") || currencyCodeOut.equals("gbp"))) {

            OpenCurrencyConversionDto openCurrencyConversionDto = REST_TEMPLATE.getForObject(CURRENCY_URL, OpenCurrencyConversionDto.class, currencyCodeOut);

            currencyDto = CurrencyDto.builder()
                    ._bid(openCurrencyConversionDto.getRates()[0].getBid())
                    ._ask(openCurrencyConversionDto.getRates()[0].getAsk())
                    .build();

            sell(dataToConversionDto);
        } else if ((currencyCodeIn.equals("usd") || currencyCodeIn.equals("eur") || currencyCodeIn.equals("gbp"))
                && (currencyCodeOut.equals("pln") || currencyCodeOut.equals("usd") || currencyCodeOut.equals("eur") || currencyCodeOut.equals("gbp"))) {

            if (currencyCodeOut.equals("pln")) {

                OpenCurrencyConversionDto openCurrencyConversionDto = REST_TEMPLATE.getForObject(CURRENCY_URL, OpenCurrencyConversionDto.class, currencyCodeIn);

                currencyDto = CurrencyDto.builder()
                        ._bid(openCurrencyConversionDto.getRates()[0].getBid())
                        ._ask(openCurrencyConversionDto.getRates()[0].getAsk())
                        .build();

                buy(dataToConversionDto);
            } else {

                OpenCurrencyConversionDto openCurrencyConversionDto = REST_TEMPLATE.getForObject(CURRENCY_URL, OpenCurrencyConversionDto.class, currencyCodeIn);

                currencyDto = CurrencyDto.builder()
                        ._bid(openCurrencyConversionDto.getRates()[0].getBid())
                        ._ask(openCurrencyConversionDto.getRates()[0].getAsk())
                        .build();

                buy(dataToConversionDto);

                openCurrencyConversionDto = REST_TEMPLATE.getForObject(CURRENCY_URL, OpenCurrencyConversionDto.class, currencyCodeOut);

                currencyDto = CurrencyDto.builder()
                        ._bid(openCurrencyConversionDto.getRates()[0].getBid())
                        ._ask(openCurrencyConversionDto.getRates()[0].getAsk())
                        .build();

                dataToConversionDto = DataToConversionDto.builder()
                        .codeInput("pln")
                        .codeOutput(currencyCodeOut)
                        .amountInput(dataAfterConversionDto.getAmountOutput())
                        .build();

                sell(dataToConversionDto);
            }
        } else {

            throw new ApiException(HttpStatus.BAD_REQUEST, "codeInput", ErrorMessageEnteredData.INCORRECT_INPUT_CODE);
        }

        return dataAfterConversionDto;
    }

    private DataAfterConversionDto buy(DataToConversionDto dataToConversionDto) {

        double amountBuy = dataToConversionDto.getAmountInput() * currencyDto.get_bid();
        dataAfterConversionDto = DataAfterConversionDto.builder()
                .amountOutput((Math.round((amountBuy - (amountBuy * 0.02)) * 100.00) / 100.00))
                .build();

        return dataAfterConversionDto;
    }

    private DataAfterConversionDto sell(DataToConversionDto dataToConversionDto) {

        double amountSell = dataToConversionDto.getAmountInput() / currencyDto.get_ask();
        dataAfterConversionDto = DataAfterConversionDto.builder()
                .amountOutput(Math.round((amountSell + (amountSell * 0.02)) * 100.00) / 100.00)
                .build();

        return dataAfterConversionDto;
    }
}