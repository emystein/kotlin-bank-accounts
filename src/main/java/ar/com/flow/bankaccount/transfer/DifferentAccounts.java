package ar.com.flow.bankaccount.transfer;

import ar.com.flow.bankaccount.BankAccount;
import ar.com.flow.bankaccount.Preconditions;

public class DifferentAccounts implements Preconditions {
    private final BankAccount debitAccount;
    private final BankAccount creditAccount;

    public DifferentAccounts(BankAccount debitAccount, BankAccount creditAccount) {
        this.debitAccount = debitAccount;
        this.creditAccount = creditAccount;
    }

    public void check() {
        if (debitAccount.equals(creditAccount)) {
            throw new SameAccountTransferException();
        }
    }
}
