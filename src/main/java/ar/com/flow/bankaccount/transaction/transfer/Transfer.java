package ar.com.flow.bankaccount.transaction.transfer;

import ar.com.flow.bankaccount.BankAccount;
import ar.com.flow.bankaccount.transaction.*;
import ar.com.flow.bankaccount.transaction.withdrawal.SufficientFunds;
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
            var preconditions = new CompositePreconditions();
            preconditions.add(new SufficientFunds(debitAccount, amountToTransfer));
            preconditions.add(new DifferentAccounts(debitAccount, creditAccount));

            Collection<Step> steps = new ArrayList<>();
            steps.add(new Step(new Debit(debitAccount, creditAccount), debitAccount));
            steps.add(new Step(new Credit(creditAccount, Action.Transfer), creditAccount));

            return Transaction.builder()
                    .preconditions(preconditions)
                    .steps(steps)
                    .amount(amountToTransfer)
                    .build();
        }
    }
}
