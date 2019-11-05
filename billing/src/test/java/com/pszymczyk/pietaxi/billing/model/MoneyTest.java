package com.pszymczyk.pietaxi.billing.model;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import com.pszymczyk.pietaxi.billing.model.Money;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

class MoneyTest {

    @ParameterizedTest
    @MethodSource("shouldIgnoreIrrelevantZerosArguments")
    void Should_ignore_irrelevant_zeros(String x, String y) {
        //expect
        assertThat(Money.of(x).equals(Money.of(y))).isTrue();
    }

    private static Stream<Arguments> shouldIgnoreIrrelevantZerosArguments() {
        return Stream.of(
                Arguments.of("0.00", "0"),
                Arguments.of("8.00", "8"),
                Arguments.of("8.00000", "8")
        );
    }

    @ParameterizedTest
    @MethodSource("shouldRoundToExpectedScaleArguments")
    void Should_round_to_expected_scale(String x, String y) {
        //expect
        assertThat(Money.of(x)).isEqualTo(Money.of(y));
    }

    private static Stream<Arguments> shouldRoundToExpectedScaleArguments() {
        return Stream.of(
                Arguments.of("0.129", "0.13"),
                Arguments.of("0.125", "0.13"),
                Arguments.of("0.124", "0.12"),
                Arguments.of("0.9999", "1")
        );
    }

    @ParameterizedTest
    @MethodSource("shouldAddMoneyToMoneyArguments")
    void Should_add_money_to_money(String x, String y, String z) {
        //expect
        assertThat(Money.of(x).add(Money.of(y))).isEqualTo(Money.of(z));
    }

    private static Stream<Arguments> shouldAddMoneyToMoneyArguments() {
        return Stream.of(
                Arguments.of("2", "0", "2"),
                Arguments.of("2", "3", "5")
        );
    }

    @ParameterizedTest
    @ValueSource(strings = { "", "one thousand", "XXI", "3.3.3", "3,2", "3 200 000", "3_200_000" })
    void Should_throw_exception_when_try_to_create_money_from_incorrect_input(String x) {
        //expect
        assertThatExceptionOfType(NumberFormatException.class).isThrownBy(() -> Money.of(x));
    }

    @Test
    void Should_throw_NullPointerException_when_try_to_create_money_from_null() {
        //expect
        assertThatExceptionOfType(NullPointerException.class).isThrownBy(() -> Money.of(null));
    }

    @Test
    void Should_be_indiscernible() {
        //when
        Set<Money> moneySet = new HashSet<>();
        moneySet.add(Money.of("100"));
        moneySet.add(Money.of("100"));
        moneySet.add(Money.of("100"));

        //expect
        assertThat(moneySet).hasSize(1);
    }
}
