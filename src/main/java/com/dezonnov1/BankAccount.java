package com.dezonnov1;

import java.time.LocalDateTime;
import java.util.Random;

public class BankAccount {

    private static final int NUMBER_LONG = 8;
    private static final String NUMBER_STRING_FORMAT = "\\d{%d}".formatted(NUMBER_LONG);

    private static final long DEFAULT_BLOCK_BALANCE = 0;
    private static final boolean DEFAULT_BLOCK_STATE = false;

    private String owner;

    private long balance;

    private LocalDateTime openDate;

    private boolean blocked;

    private String number;


    public String getOwner() {
        return owner;
    }

    protected void setOwner(String owner) {
        this.owner = owner;
    }

    public long getBalance() {
        return balance;
    }

    protected void setBalance(long balance) {
        this.balance = balance;
    }

    public LocalDateTime getOpenDate() {
        return openDate;
    }

    protected void setOpenDate(LocalDateTime openDate) {
        this.openDate = openDate;
    }

    public boolean isBlocked() {
        return blocked;
    }

    public void setBlocked(boolean blocked) {
        this.blocked = blocked;
    }

    public String getNumber() {
        return number;
    }

    /**
     *
     * @param number строка из цифр (0-9) длинной в 8 символов
     * @throws IllegalArgumentException Если не подходит под условие.
     */
    protected void setNumber(String number) throws IllegalArgumentException {
        try {
            if (isValidNumber(number)) {
                this.number = number;
            }
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("number is not valid");
        }
    }

    public boolean isValidNumber(String number) {
        return number.matches(NUMBER_STRING_FORMAT);
    }

    public BankAccount(String owner) {
        this(owner, DEFAULT_BLOCK_BALANCE, DEFAULT_BLOCK_STATE);
    }

    public BankAccount(String owner, long balance, boolean blocked) {
        this.owner = owner;
        this.balance = balance;
        this.openDate = LocalDateTime.now();
        this.blocked = blocked;
        this.number = generateNumber();
    }


    /**
     * Пополнение счета.
     *
     * @param amount Пополняемая сумма на счёт.
     * @throws IllegalArgumentException Если невозможно пополнить счёт на сумму.
     *
     */
    public void deposit(long amount) throws IllegalArgumentException {
        try {
            if (depositIsPossible(amount)) {
                this.setBalance(this.getBalance() + amount);
            }
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(e);
        }

    }

    /**
     * Снятие денег.
     *
     * @param amount Снимаемая сумма со счёта.
     * @throws IllegalArgumentException Если невозможно снять сумму со счета.
     *
     */
    public void withdraw(long amount) throws IllegalArgumentException {
        try {
            if (withdrawIsPossible(amount)) {
                this.setBalance(this.getBalance() - amount);
            }
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(e);
        }
    }

    /**
     * Перевод денег на другой счет.
     *
     * @param otherAccount Аккаунт, на который переводятся деньги.
     * @param amount       Переводимая сумма с ЭТОГО аккаунта на otherAccount
     * @throws IllegalArgumentException Причина по которой перевод невозможен.
     *
     */
    public void transfer(BankAccount otherAccount, long amount) throws IllegalArgumentException {
        try {
            if (transferIsPossible(otherAccount, amount)) {
                this.withdraw(amount);
                otherAccount.deposit(amount);
            }
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(e);
        }
    }

    /**
     * Возможность пополнение суммы с аккаунта.
     *
     * @param amount Сумма пополнения.
     * @return Если пополнение возможно.
     * @throws IllegalArgumentException Причина по которой пополнение невозможно.
     */
    public boolean depositIsPossible(long amount) throws IllegalArgumentException {
        if (this.isBlocked()) {
            throw new IllegalArgumentException("Account is blocked");
        }
        if (amount < 0) {
            throw new IllegalArgumentException("amount is less than 0");
        }
        return true;
    }

    /**
     * Возможность снятия суммы с аккаунта.
     *
     * @param amount Сумма снятия (положительная).
     * @return Если снятие возможно.
     * @throws IllegalArgumentException Причина по которой снятие невозможно.
     */
    public boolean withdrawIsPossible(long amount) throws IllegalArgumentException {
        if (this.isBlocked()) {
            throw new IllegalArgumentException("Account is blocked");
        }
        if (amount < 0) {
            throw new IllegalArgumentException("amount is less than 0");
        }
        if (amount > this.getBalance()) {
            throw new IllegalArgumentException("amount is more than balance");
        }

        return true;
    }

    /**
     * Возможность перевода суммы с аккаунта на otherAccount.
     *
     * @param otherAccount Аккаунт на который проверяется перевод.
     * @param amount       Сумма перевода.
     * @return Если перевод возможен.
     * @throws IllegalArgumentException Причина по которой перевод невозможен.
     */
    public boolean transferIsPossible(BankAccount otherAccount, long amount) throws IllegalArgumentException {
        // Возможно снятие с текущего И возможно пополнение на другом
        try {
            return withdrawIsPossible(this.getBalance()) && otherAccount.depositIsPossible(amount);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Генерирует номер счета
     *
     * @return Строка с номером счета
     */
    public String generateNumber() {
        Random random = new Random();
        StringBuilder sb = new StringBuilder(NUMBER_LONG);
        for (int i = 0; i < NUMBER_LONG; i++) {
            sb.append(random.nextInt(10));
        }
        String number = sb.toString();
        if (!isValidNumber(number)) {
            throw new RuntimeException("number from generator is not valid");
        }
        return number;
    }

    @Override
    public String toString() {
        return "BankAccount{" +
                "account number=" + getNumber() +
                ", owner='" + getOwner() + "'" +
                ", balance=" + getBalance() +
                ", openDate=" + getOpenDate().toString() +
                ", isBlocked=" + isBlocked() +
                "}"
                ;
    }

    @Override
    public boolean equals(Object obj) {

        return super.equals(obj);
    }

    @Override
    public int hashCode() {

        return super.hashCode();
    }
}
