package com.drekerd.testCard.Infrastructure.cards;

import com.drekerd.testCard.Infrastructure.bank.Bank;
import com.drekerd.testCard.Infrastructure.country.Country;
import com.drekerd.testCard.Infrastructure.number.Number;
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
