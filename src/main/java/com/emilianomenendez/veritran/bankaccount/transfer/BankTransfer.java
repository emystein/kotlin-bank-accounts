package com.emilianomenendez.veritran.bankaccount.transfer;

import com.emilianomenendez.veritran.bankaccount.BankAccount;
import com.emilianomenendez.veritran.bankaccount.Transaction;
import com.emilianomenendez.veritran.bankaccount.TransactionRecord;
import com.emilianomenendez.veritran.money.Money;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class BankTransfer implements Transaction {
    private final BankAccount debitAccount;
    private final BankAccount creditAccount;
    private final Money amount;

    public static BankTransferBuilder from(BankAccount debitAccount) {
        return new BankTransferBuilder(debitAccount);
    }

    public TransactionRecord execute() {
        assertAccountsAreDifferent();

        debitAccount.withdraw(amount);

        return creditAccount.deposit(amount);
    }

    private void assertAccountsAreDifferent() {
        if (debitAccount.equals(creditAccount)) {
            throw new SameAccountTransferException();
        }
    }
}
