package ar.com.flow.bankaccount.transaction.withdrawal;

import ar.com.flow.bankaccount.BankAccount;
import ar.com.flow.bankaccount.transaction.Action;
import ar.com.flow.bankaccount.transaction.Algorithm;
import ar.com.flow.bankaccount.transaction.DebitStamper;
import ar.com.flow.bankaccount.transaction.Transaction;
import ar.com.flow.money.Money;

public class Withdrawal {
    public static WithdrawalBuilder from(BankAccount debitAccount) {
        return new WithdrawalBuilder(debitAccount);
    }

    public static class WithdrawalBuilder {
        private final BankAccount debitAccount;

        public WithdrawalBuilder(BankAccount debitAccount) {
            this.debitAccount = debitAccount;
        }

        public Transaction amount(Money amountToWithdraw) {
            return Transaction.builder()
                    .amount(amountToWithdraw)
                    .precondition(new SufficientFunds(debitAccount, amountToWithdraw))
                    .step(new Algorithm(debitAccount, new WithdrawalStamper(debitAccount)))
                    .build();
        }
    }
}
