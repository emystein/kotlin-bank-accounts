package ar.com.flow.bankaccount.transaction;

import ar.com.flow.bankaccount.BankAccount;
import ar.com.flow.bankaccount.balance.Balance;
import ar.com.flow.money.Money;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Getter
public class DepositAlgorithm implements Algorithm {
    private final BankAccount account;

    public TransactionRecord execute(Money amount) {
        return new TransactionRecord(LocalDateTime.now(), Action.Deposit, Balance.positive(amount));
    }
}