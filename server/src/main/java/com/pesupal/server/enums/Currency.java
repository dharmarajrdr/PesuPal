package com.pesupal.server.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Currency {

    USD("United States Dollar", "USD", "$", 1),
    INR("Indian Rupee", "INR", "₹", 80),
    EUR("Euro", "EUR", "€", 0.85),
    GBP("British Pound", "GBP", "£", 0.75),
    JPY("Japanese Yen", "JPY", "¥", 110),
    CNY("Chinese Yuan", "CNY", "¥", 6.5),
    KES("Kenyan Shilling", "KES", "KSh", 110),
    TZS("Tanzanian Shilling", "TZS", "TSh", 2300),
    UGX("Ugandan Shilling", "UGX", "USh", 3600),
    RWF("Rwandan Franc", "RWF", "RF", 1000),
    ZAR("South African Rand", "ZAR", "R", 15),
    NGN("Nigerian Naira", "NGN", "₦", 410),
    GHS("Ghanaian Cedi", "GHS", "GH₵", 6.0),
    XAF("Central African CFA Franc", "XAF", "FCFA", 600),
    XOF("West African CFA Franc", "XOF", "CFA", 600),
    XPF("CFP Franc", "XPF", "CFPF", 105),
    AED("United Arab Emirates Dirham", "AED", "د.إ", 3.67),
    CAD("Canadian Dollar", "CAD", "$", 1.25),
    AUD("Australian Dollar", "AUD", "$", 1.35),
    NZD("New Zealand Dollar", "NZD", "$", 1.4),
    CHF("Swiss Franc", "CHF", "CHF", 0.92),
    SEK("Swedish Krona", "SEK", "kr", 8.5),
    NOK("Norwegian Krone", "NOK", "kr", 8.7),
    DKK("Danish Krone", "DKK", "kr", 6.5),
    PLN("Polish Zloty", "PLN", "zł", 4.0),
    HUF("Hungarian Forint", "HUF", "Ft", 300),
    CZK("Czech Koruna", "CZK", "Kč", 22),
    ILS("Israeli New Shekel", "ILS", "₪", 3.3),
    TRY("Turkish Lira", "TRY", "₺", 8.5),
    BRL("Brazilian Real", "BRL", "R$", 5.2),
    ARS("Argentine Peso", "ARS", "$", 95.0),
    CLP("Chilean Peso", "CLP", "$", 720),
    COP("Colombian Peso", "COP", "$", 3800),
    PEN("Peruvian Sol", "PEN", "S/.", 3.8);

    private final String name;
    private final String code;
    private final String symbol;
    private final double exchangeRate;
}
