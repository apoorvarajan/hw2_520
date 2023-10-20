package model;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class AmountFilter implements TransactionFilter {

    private final double amountToFilter;

    public AmountFilter(double amountToFilter) {
        this.amountToFilter = amountToFilter;
    }

    @Override
    public List<Integer> filter(List<Transaction> transactions) {
        return IntStream.range(0, transactions.size())
                .filter(i -> transactions.get(i).getAmount() == amountToFilter)
                .boxed()
                .collect(Collectors.toList());
    }
}
