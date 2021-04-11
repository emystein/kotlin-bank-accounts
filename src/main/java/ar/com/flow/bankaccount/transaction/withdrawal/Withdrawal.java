package ar.com.flow.bankaccount.transaction.withdrawal;

import ar.com.flow.bankaccount.BankAccount;
import ar.com.flow.bankaccount.transaction.Action;
import ar.com.flow.bankaccount.transaction.Debit;
import ar.com.flow.bankaccount.transaction.Step;
import ar.com.flow.bankaccount.transaction.Transaction;
import ar.com.flow.money.Money;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;

public class Withdrawal {
    public static WithdrawalBuilder from(BankAccount debitAccount) {
        return new WithdrawalBuilder(debitAccount);
    }

    @RequiredArgsConstructor
    public static class WithdrawalBuilder {
        private final BankAccount debitAccount;
        private Money amountToWithdraw;

        public WithdrawalBuilder amount(Money amountToWithdraw) {
            this.amountToWithdraw = amountToWithdraw;
            return this;
        }

        public Transaction build() {
            var steps = new ArrayList<Step>();
            steps.add(new Step(new Debit(debitAccount, Action.Withdrawal), debitAccount));

            return Transaction.builder()
                    .amount(amountToWithdraw)
                    .preconditions(new SufficientFunds(debitAccount, amountToWithdraw))
                    .steps(steps)
                    .build();
        }
    }
}
