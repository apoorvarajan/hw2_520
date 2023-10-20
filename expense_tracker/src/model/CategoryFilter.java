package model;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class CategoryFilter implements TransactionFilter {

    private final String categoryToFilter;

    public CategoryFilter(String categoryToFilter) {
        this.categoryToFilter = categoryToFilter;
    }

    @Override
    public List<Integer> filter(List<Transaction> transactions) {
        return IntStream.range(0, transactions.size())
                .filter(i -> transactions.get(i).getCategory().equalsIgnoreCase(categoryToFilter))
                .boxed()
                .collect(Collectors.toList());
    }
}
