package ar.com.flow.bankaccount;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class BalanceTimeline {
    private final String currency;
    private final TransactionHistory transactionHistory;

    public boolean isEmpty() {
        return transactionHistory.isEmpty();
    }

    public Balance currentSnapshot() {
        return transactionHistory.sum()
                .orElse(Balance.zero(currency));
    }

    public Balance previousSnapshot() {
        return transactionHistory.sumBeforeLast()
                .orElse(Balance.zero(currency));
    }

    public Balance initialSnapshot() {
        return transactionHistory.first()
                .map(TransactionRecord::getBalance)
                .orElse(Balance.zero(currency));
    }
}
