package ar.com.flow.bankaccount;

import ar.com.flow.bankaccount.balance.Balance;
import ar.com.flow.bankaccount.transaction.TransactionRecord;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

public class InMemoryStatement implements Statement {
    private List<TransactionRecord> history = new ArrayList<>();

    public int total() {
        return history.size();
    }

    public Optional<TransactionRecord> first() {
        return history.stream().findFirst();
    }

    public Optional<TransactionRecord> last() {
        return history.stream().reduce((first, second) -> second);
    }

    public void add(TransactionRecord record) {
        history.add(record);
    }

    public boolean contains(TransactionRecord record) {
        return history.contains(record);
    }

    public boolean containsInOrder(TransactionRecord... records) {
        List<TransactionRecord> indexedMovements = Arrays.asList(records);

        return history.stream()
                .filter(indexedMovements::contains)
                .collect(toList())
                .equals(indexedMovements);
    }

    public Optional<Balance> getCurrentBalance() {
        return sum(total());
    }

    public Optional<Balance> getPreviousBalance() {
        return sum(total() - 1);
    }

    public Optional<Balance> getInitialBalance() {
        return first().map(TransactionRecord::getBalance);
    }

    private Optional<Balance> sum(int numberOfTransactions) {
        return entries(numberOfTransactions)
                .map(TransactionRecord::getBalance)
                .reduce(Balance::plus);
    }

    private Stream<TransactionRecord> entries(int numberOfEntries) {
        if (!history.isEmpty()) {
            return history.stream().limit(numberOfEntries);
        } else {
            return Stream.empty();
        }
    }
}
