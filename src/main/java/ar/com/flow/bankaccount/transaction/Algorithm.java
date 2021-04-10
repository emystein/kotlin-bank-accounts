package ar.com.flow.bankaccount.transaction;

import ar.com.flow.bankaccount.BankAccount;
import ar.com.flow.money.Money;

public interface Algorithm {
    // TODO replace by moving account as field of TransactionRecord
    BankAccount getAccount();

    TransactionRecord execute(Money amount);
}
