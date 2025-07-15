package com.pesupal.server.dto.request;

import com.pesupal.server.enums.Currency;
import com.pesupal.server.enums.Interval;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PaymentDto {

    private String name;

    private String description;

    private Currency currency;

    private Long amount;

    private Interval interval;
}
