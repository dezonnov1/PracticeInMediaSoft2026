package com.dezonnov1;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

class BankAccountTest {

    static Stream<Arguments> provideDepositSuccessData() {
        return Stream.of(
                // с нуля
                Arguments.of(new BankAccount("Ivan", 0, false), 1000, 1000),
                // пополнение положительного баланса на 200
                Arguments.of(new BankAccount("Oleg", 200, false), 200, 400),
                // пополнение отрицательного баланса на 200
                Arguments.of(new BankAccount("Semen", -100, false), 1000, 900),
                // пополнение отрицательного баланса до 0
                Arguments.of(new BankAccount("Valera", -999, false), 999, 0),
                // пополнение на максимальное значение
                Arguments.of(new BankAccount("Sergey", 0, false), Long.MAX_VALUE, Long.MAX_VALUE),
                // пополнение из крайне отрицательного баланса до 0
                Arguments.of(new BankAccount("Sergey", Long.MIN_VALUE + 1, false), Long.MAX_VALUE, 0)
        );
    }

    @ParameterizedTest
    @MethodSource("provideDepositSuccessData")
    void depositSuccessTest(BankAccount account, long amount, long result) {
        account.deposit(amount);
        assertEquals(account.getBalance(), result);
    }

    static Stream<Arguments> provideDepositErrorData() {
        return Stream.of(
                // Пополнение заблокированного аккаунта
                Arguments.of(new BankAccount("Ivan", 0, true), 100, "Account is blocked"),
                // Пополнение на отрицательное число
                Arguments.of(new BankAccount("Oleg", 200, false), -100, "amount is less than 0"),
                // Пополнение заблокированного аккаунта на отрицательное число
                Arguments.of(new BankAccount("Semen", -100, true), -100, "Account is blocked")
        );
    }

    @ParameterizedTest
    @MethodSource("provideDepositErrorData")
    void depositErrorTest(BankAccount account, long amount, String message) {
        long initBalance = account.getBalance();
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> account.deposit(amount));
        assertEquals(ex.getMessage(), message);
        assertEquals(initBalance, account.getBalance()); // баланс не должен измениться
    }

    static Stream<Arguments> provideWithdrawSuccessData() {
        return Stream.of(
                // до нуля
                Arguments.of(new BankAccount("Ivan", 1000, false), 1000, 0),
                // снятие с положительного баланса на 200
                Arguments.of(new BankAccount("Oleg", 600, false), 400, 200)
        );
    }

    @ParameterizedTest
    @MethodSource("provideWithdrawSuccessData")
    void withdrawSuccessTest(BankAccount account, long amount, long result) {
        account.withdraw(amount);
        assertEquals(account.getBalance(), result);
    }

    static Stream<Arguments> provideWithdrawErrorData() {
        return Stream.of(
                // Снятие с заблокированного аккаунта
                Arguments.of(new BankAccount("Ivan", 0, true), 100, "Account is blocked"),
                // Снятие с отрицательного баланса
                Arguments.of(new BankAccount("Oleg", -200, false), 100, "amount is more than balance"),
                // Снятие с заблокированного аккаунта
                Arguments.of(new BankAccount("Semen", 100, true), 100, "Account is blocked")

        );
    }

    @ParameterizedTest
    @MethodSource("provideWithdrawErrorData")
    void withdrawErrorTest(BankAccount account, long amount, String message) {
        long initBalance = account.getBalance();
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> account.withdraw(amount));
        assertEquals(ex.getMessage(), message);
        assertEquals(initBalance, account.getBalance()); // баланс не должен измениться
    }


    static Stream<Arguments> provideTransferSuccessData() {
        return Stream.of(
                // account1 , account2, transfer, resultBalance1, resultBalance2
                // Перевод на 100
                Arguments.of(new BankAccount("Ivan", 1000, false),
                        new BankAccount("Oleg", 0, false),
                        100,
                        900, 100),
                // перевод на 10_000
                Arguments.of(new BankAccount("Ivan", 40_000, false),
                        new BankAccount("Oleg", 10_000, false),
                        10_000,
                        30_000, 20_000)
        );
    }

    @ParameterizedTest
    @MethodSource("provideTransferSuccessData")
    void transferSuccessTest(BankAccount account1, BankAccount account2, long amount, long resultBalance1, long resultBalance2) {
        account1.transfer(account2, amount);
        assertEquals(account1.getBalance(), resultBalance1);
        assertEquals(account2.getBalance(), resultBalance2);
    }

    static Stream<Arguments> provideTransferErrorData() {
        return Stream.of(
                // account1 , account2, transfer, massage
                Arguments.of(new BankAccount("Ivan", 1000, true),
                        new BankAccount("Oleg", 1000, false),
                        100,
                        "Account is blocked"),
                Arguments.of(new BankAccount("Ivan", 1000, false),
                        new BankAccount("Oleg", 1000, true),
                        100,
                        "Account is blocked"),
                Arguments.of(new BankAccount("Ivan", 40_000, false),
                        new BankAccount("Oleg", -10_000, false),
                        -10_000,
                        "amount is less than 0"),
                Arguments.of(new BankAccount("Ivan", 10_000, false),
                        new BankAccount("Oleg", 10_000, false),
                        50_000,
                        "amount is more than balance")
        );
    }

    @ParameterizedTest
    @MethodSource("provideTransferErrorData")
    void transferErrorTest(BankAccount account1, BankAccount account2, long amount, String message) {
        long initBalance1 = account1.getBalance();
        long initBalance2 = account2.getBalance();

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> account1.transfer(account2, amount));

        assertEquals(ex.getMessage(), message);

        assertEquals(initBalance1, account1.getBalance()); // баланс не должен измениться
        assertEquals(initBalance2, account2.getBalance()); // баланс не должен измениться
    }
}