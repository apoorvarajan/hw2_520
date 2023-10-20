package controller;

import view.ExpenseTrackerView;

import java.util.List;



import model.ExpenseTrackerModel;
import model.Transaction;
import model.AmountFilter;
import model.CategoryFilter;
import model.TransactionFilter;
import view.ExpenseTrackerView;
public class ExpenseTrackerController {
  
  private ExpenseTrackerModel model;
  private ExpenseTrackerView view;

  public ExpenseTrackerController(ExpenseTrackerModel model, ExpenseTrackerView view) {
    this.model = model;
    this.view = view;

    // Set up view event handlers
  }

  public void refresh() {

    // Get transactions from model
    List<Transaction> transactions = model.getTransactions();

    // Pass to view
    view.refreshTable(transactions);

  }

  public boolean addTransaction(double amount, String category) {
    if (!InputValidation.isValidAmount(amount)) {
      return false;
    }
    if (!InputValidation.isValidCategory(category)) {
      return false;
    }
    
    Transaction t = new Transaction(amount, category);
    model.addTransaction(t);
    view.getTableModel().addRow(new Object[]{t.getAmount(), t.getCategory(), t.getTimestamp()});
    refresh();
    return true;
  }
  
  // Other controller methods

  public void applyFilter(String filterType, String filterValue) {
    List<Integer> filteredTransactions;
    TransactionFilter filter;

    switch (filterType.toLowerCase()) {
        case "amount":
            double amountToFilter = Double.parseDouble(filterValue);
            filter = new AmountFilter(amountToFilter);
            filteredTransactions = filter.filter(model.getTransactions());
            break;

        case "category":
            filter = new CategoryFilter(filterValue);
            filteredTransactions = filter.filter(model.getTransactions());
            break;

        default:
            // Handle unsupported filter type
            return;
    }
    view.setFilteredTransactions(filteredTransactions);
    refresh();
  }
}