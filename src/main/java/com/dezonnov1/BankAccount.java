package com.dezonnov1;

import java.time.LocalDateTime;

public class BankAccount {
    private String owner;

    public String getOwner() {
        return owner;
    }

    protected void setOwner(String owner) {
        this.owner = owner;
    }

    private long balance;

    public long getBalance() {
        return balance;
    }

    protected void setBalance(long balance) {
        this.balance = balance;
    }

    private LocalDateTime openDate;

    public LocalDateTime getOpenDate() {
        return openDate;
    }

    protected void setOpenDate(LocalDateTime openDate) {
        this.openDate = openDate;
    }

    private boolean blocked;

    public boolean isBlocked() {
        return blocked;
    }

    public void setBlocked(boolean blocked) {
        this.blocked = blocked;
    }

    public BankAccount(String owner) {
        this(owner, 0, false);
    }

    public BankAccount(String owner, long balance, boolean blocked) {
        this.owner = owner;
        this.balance = balance;
        this.openDate = LocalDateTime.now();
        this.blocked = blocked;
    }


    /**
     * Пополнение счета.
     *
     * @param amount Пополняемая сумма на счёт.
     * @throws IllegalArgumentException
     *
     */
    public void deposit(long amount) throws IllegalArgumentException {
        if (depositIsPossible(amount)) {
            this.setBalance(this.getBalance() + amount);
        }
    }

    /**
     * Снятие денег.
     *
     * @param amount Снимаемая сумма со счёта.
     * @throws IllegalArgumentException
     *
     */
    public void withdraw(long amount) throws IllegalArgumentException {
        if (withdrawIsPossible(amount)) {
            this.setBalance(this.getBalance() - amount);
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
        if (transferIsPossible(otherAccount,amount)) {
            this.withdraw(amount);
            otherAccount.deposit(amount);
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
     * @param amount Сумма перевода.
     * @return Если перевод возможен.
     * @throws IllegalArgumentException Причина по которой перевод невозможен.
     */
    public boolean transferIsPossible(BankAccount otherAccount,long amount) throws IllegalArgumentException {
        // Возможно снятие с текущего И возможно пополнение на другом
        return withdrawIsPossible(this.getBalance()) && otherAccount.depositIsPossible(amount);
    }
}
