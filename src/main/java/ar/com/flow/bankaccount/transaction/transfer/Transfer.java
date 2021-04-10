package ar.com.flow.bankaccount.transaction.transfer;

import ar.com.flow.bankaccount.BankAccount;
import ar.com.flow.bankaccount.transaction.Algorithm;
import ar.com.flow.bankaccount.transaction.CompositeTransaction;
import ar.com.flow.bankaccount.transaction.Transaction;
import ar.com.flow.money.Money;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.Collection;

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
            Collection<Algorithm> steps = new ArrayList<>();
            steps.add(new Debit(debitAccount, creditAccount));
            steps.add(new Credit(creditAccount));

            return CompositeTransaction.builder()
                    .preconditions(new DifferentAccounts(debitAccount, creditAccount))
                    .steps(steps)
                    .amount(amountToTransfer)
                    .build();
        }
    }
}
