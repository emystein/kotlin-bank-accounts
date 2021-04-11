package ar.com.flow.bankaccount.transaction.transfer;

import ar.com.flow.bankaccount.BankAccount;
import ar.com.flow.bankaccount.balance.Balance;
import ar.com.flow.bankaccount.transaction.Action;
import ar.com.flow.bankaccount.transaction.Algorithm;
import ar.com.flow.bankaccount.transaction.TransactionRecord;
import ar.com.flow.money.Money;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.Optional;

@RequiredArgsConstructor
@Getter
public class Debit implements Algorithm {
    private final BankAccount account;
    private final BankAccount creditAccount;

    public TransactionRecord execute(Money amount) {
        return new TransactionRecord(LocalDateTime.now(), Action.Transfer, Balance.negative(amount), Optional.of(creditAccount));
    }
}
