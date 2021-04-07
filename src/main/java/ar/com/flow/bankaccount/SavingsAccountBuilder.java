package ar.com.flow.bankaccount;

import ar.com.flow.Customer;
import ar.com.flow.bankaccount.withdrawal.CurrentFundsLimit;
import ar.com.flow.bankaccount.withdrawal.WithdrawalLimit;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SavingsAccountBuilder {
    private final Customer accountOwner;
    private String currency = "USD";
    private WithdrawalLimit withdrawalLimit = new CurrentFundsLimit();

    public SavingsAccountBuilder currency(String currency) {
        this.currency = currency;

        return this;
    }

    public SavingsAccountBuilder withdrawalLimit(WithdrawalLimit withdrawalLimit) {
        this.withdrawalLimit = withdrawalLimit;

        return this;
    }

    public SavingsAccount build() {
        var accountHistory = new InMemoryTransactionHistory();

        return new SavingsAccount(accountOwner, currency, withdrawalLimit, accountHistory);
    }
}
