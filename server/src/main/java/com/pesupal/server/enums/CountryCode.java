package com.pesupal.server.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CountryCode {

    INDIA("+91", 10),
    UNITED_STATES("+1", 10),
    UNITED_KINGDOM("+44", 10),
    FRANCE("+33", 9),
    GERMANY("+49", 11),
    ITALY("+39", 10),
    CANADA("+1", 10),
    AUSTRALIA("+61", 9),
    JAPAN("+81", 10),
    CHINA("+86", 11),
    SOUTH_AFRICA("+27", 9),
    BRAZIL("+55", 11),
    MEXICO("+52", 10),
    RUSSIA("+7", 10),
    SOUTH_KOREA("+82", 10),
    SPAIN("+34", 9);

    private final String code;
    private final Integer digits;
}
