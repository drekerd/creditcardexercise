package com.drekerd.testCard.infrastructure.cards;

import com.drekerd.testCard.infrastructure.bank.Bank;
import com.drekerd.testCard.infrastructure.country.Country;
import com.drekerd.testCard.infrastructure.number.Number;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CardResponse {
    private Number number;
    private String scheme;
    private String type;
    private String brand;
    private boolean prepaid;
    private Country country;
    private Bank bank;
}
