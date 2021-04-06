package com.emilianomenendez.veritran.bankaccount;

import com.emilianomenendez.veritran.money.Dollars;

import java.util.Objects;

public class WithdrawalTransaction implements Transaction {
    private final Dollars amount;
    private final Dollars balanceAfter;

    public WithdrawalTransaction(Dollars amount, Dollars balanceAfter) {
        this.amount = amount;
        this.balanceAfter = balanceAfter;
    }

    public Dollars getAmount() {
        return amount;
    }

    public Dollars getBalanceAfter() {
        return balanceAfter;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof WithdrawalTransaction)) return false;
        WithdrawalTransaction that = (WithdrawalTransaction) o;
        return amount.equals(that.amount) && balanceAfter.equals(that.balanceAfter);
    }

    @Override
    public int hashCode() {
        return Objects.hash(amount, balanceAfter);
    }
}
