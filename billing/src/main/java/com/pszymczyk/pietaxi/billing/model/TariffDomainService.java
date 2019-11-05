package com.pszymczyk.pietaxi.billing.model;

import java.math.BigDecimal;

public class TariffDomainService {

    //TODO add more sophisticated policy
    private static final Money pricePerKm = Money.of("1");

    public static Money calculate(BigDecimal distance) {
        return pricePerKm.multiply(distance.longValue());
    }
}
