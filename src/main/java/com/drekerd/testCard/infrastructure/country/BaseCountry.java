package com.drekerd.testCard.infrastructure.country;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BaseCountry {
    private int numeric;
    private String alpha2;
    private String name;
    private String emoji;
    private String currency;
    private int latitude;
    private int longitude;
}
