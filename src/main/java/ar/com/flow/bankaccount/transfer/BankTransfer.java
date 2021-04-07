package ar.com.flow.bankaccount.transfer;

import ar.com.flow.bankaccount.BankAccount;
import ar.com.flow.bankaccount.Transaction;
import ar.com.flow.bankaccount.TransactionRecord;
import ar.com.flow.money.Money;
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
