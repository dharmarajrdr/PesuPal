package com.pesupal.server.dto.response.module;

import com.pesupal.server.enums.Currency;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@Builder
public class CurrencyDto {

    private String name;

    private String code;

    private String symbol;

    private double exchangeRate;

    private boolean selected;

    public static CurrencyDto fromCurrency(Currency currency, boolean selected) {

        return CurrencyDto.builder().name(currency.getName()).code(currency.getCode()).symbol(currency.getSymbol()).exchangeRate(currency.getExchangeRate()).selected(selected).build();
    }
}
