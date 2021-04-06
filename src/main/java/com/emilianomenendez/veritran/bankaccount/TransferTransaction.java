package com.emilianomenendez.veritran.bankaccount;

import com.emilianomenendez.veritran.Customer;
import com.emilianomenendez.veritran.money.Dollars;

import java.util.Objects;

public class TransferTransaction implements Transaction {
    private final Dollars debitAmount;
    private final Dollars balanceAfter;
    private final Customer destinationCustomer;

    public TransferTransaction(Dollars amount, Dollars balanceAfter, Customer destinationCustomer) {
        this.debitAmount = amount;
        this.balanceAfter = balanceAfter;
        this.destinationCustomer = destinationCustomer;
    }

    public Dollars getDebitAmount() {
        return debitAmount;
    }

    public Dollars getBalanceAfter() {
        return balanceAfter;
    }

    public Customer getDestinationCustomer() {
        return destinationCustomer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TransferTransaction)) return false;
        TransferTransaction that = (TransferTransaction) o;
        return debitAmount.equals(that.debitAmount) && balanceAfter.equals(that.balanceAfter) && destinationCustomer.equals(that.destinationCustomer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(debitAmount, balanceAfter, destinationCustomer);
    }
}
