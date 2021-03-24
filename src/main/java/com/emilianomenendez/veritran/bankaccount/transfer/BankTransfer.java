package com.emilianomenendez.veritran.bankaccount.transfer;

import com.emilianomenendez.veritran.Dollars;
import com.emilianomenendez.veritran.bankaccount.BankAccount;
import com.emilianomenendez.veritran.bankaccount.InsufficientFundsException;

public class BankTransfer {
    private BankAccount debitAccount;
    private BankAccount creditAccount;

    public static BankTransferBuilder from(BankAccount debitAccount) {
        return new BankTransferBuilder(debitAccount);
    }

    public BankTransfer(BankAccount debitAccount, BankAccount creditAccount) {
        this.debitAccount = debitAccount;
        this.creditAccount = creditAccount;
    }

    public void transfer(Dollars amountToTransfer) throws InsufficientFundsException {
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
