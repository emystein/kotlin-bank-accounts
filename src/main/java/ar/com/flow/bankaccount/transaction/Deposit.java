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

        public SingleAccountTransaction amount(Money amountToDeposit) {
            return SingleAccountTransaction.builder()
                    .action(Action.Deposit)
                    .account(creditAccount)
                    .amount(amountToDeposit)
                    .algorithm(new DepositAlgorithm())
                    .build();
        }
    }
}
