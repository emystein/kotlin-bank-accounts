package ar.com.flow.bankaccount.transaction;

import ar.com.flow.bankaccount.BankAccount;
import ar.com.flow.money.Money;

public class Deposit {
    public static DepositBuilder to(BankAccount creditAccount) {
        return new DepositBuilder(creditAccount);
    }

    public static class DepositBuilder {
        private final BankAccount creditAccount;

        public DepositBuilder(BankAccount creditAccount) {
            this.creditAccount = creditAccount;
        }

        public Transaction amount(Money amountToDeposit) {
            return Transaction.builder()
                    .amount(amountToDeposit)
                    .step(new Algorithm(creditAccount, new DepositStamper(creditAccount)))
                    .build();
        }
    }
}
