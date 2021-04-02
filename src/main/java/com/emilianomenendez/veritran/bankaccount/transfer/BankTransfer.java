package com.emilianomenendez.veritran.bankaccount.transfer;

import com.emilianomenendez.veritran.bankaccount.BankAccount;
import com.emilianomenendez.veritran.money.Money;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class BankTransfer {
    private final BankAccount debitAccount;
    private final BankAccount creditAccount;

    public static BankTransferBuilder from(BankAccount debitAccount) {
        return new BankTransferBuilder(debitAccount);
    }

    public void transfer(Money amountToTransfer) {
        assertAccountsAreDifferent();

        debitAccount.withdraw(amountToTransfer);

        creditAccount.deposit(amountToTransfer);
    }

    private void assertAccountsAreDifferent() {
        if (debitAccount.equals(creditAccount)) {
            throw new SameAccountTransferException();
        }
    }
}
