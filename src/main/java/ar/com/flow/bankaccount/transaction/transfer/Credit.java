package ar.com.flow.bankaccount.transaction.transfer;

import ar.com.flow.bankaccount.BankAccount;
import ar.com.flow.bankaccount.balance.Balance;
import ar.com.flow.bankaccount.transaction.Action;
import ar.com.flow.bankaccount.transaction.Algorithm;
import ar.com.flow.bankaccount.transaction.Deposit;
import ar.com.flow.bankaccount.transaction.TransactionRecord;
import ar.com.flow.money.Money;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class Credit implements Algorithm {
    private final BankAccount account;

    @Override
    public TransactionRecord execute(Money amount) {
        Deposit.to(account)
                .amount(amount)
                .execute();

        return TransactionRecord.now(Action.Transfer, Balance.positive(amount));
    }
}
