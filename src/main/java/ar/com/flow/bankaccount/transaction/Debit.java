package ar.com.flow.bankaccount.transaction;

import ar.com.flow.bankaccount.BankAccount;
import ar.com.flow.money.Money;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class Debit extends Algorithm {
    private final BankAccount account;
    private final Action action;

    public TransactionRecord record(Money amount) {
        return TransactionRecord.debit(account, action, amount);
    }
}
