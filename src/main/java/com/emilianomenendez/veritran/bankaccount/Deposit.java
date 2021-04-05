package com.emilianomenendez.veritran.bankaccount;

import com.emilianomenendez.veritran.money.Money;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import static java.time.LocalDateTime.now;

@RequiredArgsConstructor
@Getter
public class Deposit implements Transaction {
    private final BankAccount creditAccount;
    private final Money amount;

    public static DepositBuilder to(BankAccount bankAccount) {
        return new DepositBuilder(bankAccount);
    }

    @Override
    public TransactionRecord execute() {
        var transactionRecord = new TransactionRecord(now(), amount);

        creditAccount.addTransactionRecord(transactionRecord);

        return transactionRecord;
    }
}
