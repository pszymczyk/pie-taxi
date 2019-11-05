package com.pszymczyk.pietaxi.billing.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

/**
 * @author pawel szymczyk
 */
public final class Money {

    private static final Scale SCALE = new Scale(RoundingMode.HALF_UP, 2);
    public static final Money ZERO = Money.of("0");

    private final BigDecimal value;

    public static Money of(String value) {
        if (value == null) {
            throw new NullPointerException("Cannot create Money from null");
        }

        return new Money(value);
    }

    public static Money zero() {
        return new Money(BigDecimal.ZERO);
    }

    private Money(String value) {
        this(new BigDecimal(value));
    }

    private Money(BigDecimal value) {
        this.value = SCALE.apply(value);
    }

    public Money add(Money money) {
        return new Money(this.value.add(money.value));
    }

    public Money times(int times) {
        return new Money(value.multiply(new BigDecimal(times)));
    }

    public Money times(long times) {
        return new Money(value.multiply(new BigDecimal(times)));
    }

    public String asString() {
        return value.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Money money = (Money) o;
        return value.compareTo(money.value) == 0;
    }

    @Override
    public String toString() {
        return value.toString();
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    public boolean isLessThen(Money money) {
        return this.value.compareTo(money.value) < 0;
    }

    public boolean isGreaterOrEqualThen(Money money) {
        return this.value.compareTo(money.value) >= 0;
    }

    public Money minus(Money money) {
        return new Money(value.subtract(money.value));
    }

    public Money multiply(long multiplier) {
        return new Money(value.multiply(new BigDecimal(multiplier)));
    }

    private static final class Scale {
        private final RoundingMode roundingMode;
        private final int scale;

        public Scale(RoundingMode roundingMode, int scale) {
            this.roundingMode = roundingMode;
            this.scale = scale;
        }

        public BigDecimal apply(BigDecimal bigDecimal) {
            return bigDecimal.setScale(scale, roundingMode);
        }
    }
}
