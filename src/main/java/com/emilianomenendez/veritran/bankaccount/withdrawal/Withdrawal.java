package com.emilianomenendez.veritran.bankaccount.withdrawal;

import com.emilianomenendez.veritran.bankaccount.Balance;
import com.emilianomenendez.veritran.bankaccount.BankAccount;
import com.emilianomenendez.veritran.bankaccount.Transaction;
import com.emilianomenendez.veritran.bankaccount.TransactionRecord;
import com.emilianomenendez.veritran.money.InsufficientFundsException;
import com.emilianomenendez.veritran.money.Money;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import static java.time.LocalDateTime.now;

@RequiredArgsConstructor
@Getter
public class Withdrawal implements Transaction {
    private final BankAccount debitAccount;
    private final Money amount;

    public static WithdrawalBuilder from(BankAccount debitAccount) {
        return new WithdrawalBuilder(debitAccount);
    }

    public Balance previewBalanceAfter() {
        return debitAccount.getBalance()
                .minus(amount);
    }

    @Override
    public TransactionRecord execute() {
        if (!debitAccount.withdrawalLimitAccepts(this)) {
            throw new InsufficientFundsException();
        }

        var transactionRecord = new TransactionRecord(now(), Balance.negative(amount));

        debitAccount.getTransactionHistory().add(transactionRecord);

        return transactionRecord;
    }
}
