package ar.com.flow.bankaccount.transaction.transfer;

import ar.com.flow.bankaccount.BankAccount;
import ar.com.flow.bankaccount.transaction.Action;
import ar.com.flow.bankaccount.transaction.Algorithm;
import ar.com.flow.bankaccount.transaction.TransactionRecord;
import ar.com.flow.money.Money;

public class Debit extends Algorithm {
    private final BankAccount creditAccount;

    public Debit(BankAccount account, BankAccount creditAccount) {
        super(account);
        this.creditAccount = creditAccount;
    }

    protected TransactionRecord record(Money amount) {
        return TransactionRecord.debit(creditAccount, Action.Transfer, amount);
    }
}
