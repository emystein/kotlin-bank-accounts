package ar.com.flow.bankaccount.transaction;

import ar.com.flow.bankaccount.BankAccount;
import ar.com.flow.money.Money;
import lombok.RequiredArgsConstructor;

public class Deposit {
    public static DepositBuilder to(BankAccount creditAccount) {
        return new DepositBuilder(creditAccount);
    }

    @RequiredArgsConstructor
    public static class DepositBuilder {
        private final BankAccount creditAccount;

        public Transaction amount(Money amountToDeposit) {
            return Transaction.builder()
                    .amount(amountToDeposit)
                    .step(new Step(new Credit(creditAccount, Action.Deposit), creditAccount))
                    .build();
        }
    }
}
