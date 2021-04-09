package ar.com.flow.bankaccount.transaction.transfer;

import ar.com.flow.bankaccount.BankAccount;
import ar.com.flow.bankaccount.balance.Balance;
import ar.com.flow.bankaccount.transaction.Transaction;
import ar.com.flow.bankaccount.transaction.Action;
import ar.com.flow.money.Money;
import lombok.RequiredArgsConstructor;

public class Transfer {
    public static BankTransferBuilder from(BankAccount debitAccount) {
        return new BankTransferBuilder(debitAccount);
    }

    @RequiredArgsConstructor
    public static class BankTransferBuilder {
        private final BankAccount debitAccount;
        private BankAccount creditAccount;

        public BankTransferBuilder to(BankAccount creditAccount) {
            this.creditAccount = creditAccount;
            return this;
        }

        public Transaction amount(Money amountToTransfer) {
            return Transaction.builder()
                    .action(Action.Transfer)
                    .account(creditAccount)
                    .amount(Balance.positive(amountToTransfer))
                    .preconditions(new DifferentAccounts(debitAccount, creditAccount))
                    .algorithm(new TransferFunds(debitAccount, creditAccount, amountToTransfer))
                    .build();
        }
    }
}
