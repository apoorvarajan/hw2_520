package view;

import javax.swing.*;
import javax.swing.JFormattedTextField.AbstractFormatterFactory;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.DefaultTableCellRenderer;

import controller.InputValidation;
import controller.ExpenseTrackerController;

import java.awt.*;
import java.text.NumberFormat;

import model.Transaction;
import java.util.List;

public class ExpenseTrackerView extends JFrame {

  private JTable transactionsTable;
  private JButton addTransactionBtn;
  private JFormattedTextField amountField;
  private JTextField categoryField;
  private DefaultTableModel model;
  private JButton applyFilterBtn;
  private JComboBox<String> filterTypeComboBox;
  private JTextField filterValueField;
  private ExpenseTrackerController controller;
  private List<Integer> filteredTransactions;
 
  

  public List<Integer> getFilteredTransactions() {
	return filteredTransactions;
}

public void setFilteredTransactions(List<Integer> filteredTransactions) {
	this.filteredTransactions = filteredTransactions;
}

public ExpenseTrackerView() {
    setTitle("Expense Tracker"); // Set title
    setSize(600, 400); // Make GUI larger

    String[] columnNames = {"serial", "Amount", "Category", "Date"};
    this.model = new DefaultTableModel(columnNames, 0);

    addTransactionBtn = new JButton("Add Transaction");
    applyFilterBtn = new JButton("Apply Filter");
    String[] filterTypes = {"Amount", "Category"};
    filterTypeComboBox = new JComboBox<>(filterTypes);
    filterValueField = new JTextField(10);

    // Create UI components
    JLabel amountLabel = new JLabel("Amount:");
    NumberFormat format = NumberFormat.getNumberInstance();

    amountField = new JFormattedTextField(format);
    amountField.setColumns(10);

    
    JLabel categoryLabel = new JLabel("Category:");
    categoryField = new JTextField(10);

    // Create table
    transactionsTable = new JTable(model);
    
    transactionsTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            if(getFilteredTransactions() != null) {
	            boolean isFiltered = getFilteredTransactions().contains(row);
	            System.out.println("render "+ isFiltered);
	            if (isFiltered) {
	                c.setBackground(new Color(173, 255, 168)); // Light green
	            } else {
	                c.setBackground(table.getBackground());
	            }
            }

            return c;
        }
    });

  
    // Layout components
    JPanel inputPanel = new JPanel();
    inputPanel.add(amountLabel);
    inputPanel.add(amountField);
    inputPanel.add(categoryLabel); 
    inputPanel.add(categoryField);
    inputPanel.add(addTransactionBtn);
  
    JPanel buttonPanel = new JPanel();
    buttonPanel.add(addTransactionBtn);

    // JPanel buttonPanel = new JPanel();
    buttonPanel.add(new JLabel("Filter Type:"));
    buttonPanel.add(filterTypeComboBox);
    buttonPanel.add(new JLabel("Filter Value:"));
    buttonPanel.add(filterValueField);
    buttonPanel.add(applyFilterBtn);

    applyFilterBtn.addActionListener(e -> applyFilter());
  
    // Add panels to frame
    add(inputPanel, BorderLayout.NORTH);
    add(new JScrollPane(transactionsTable), BorderLayout.CENTER); 
    add(buttonPanel, BorderLayout.SOUTH);
  
    // Set frame properties
    setSize(700, 500);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setVisible(true);
  
  }

  public void refreshTable(List<Transaction> transactions) {
      // Clear existing rows
      model.setRowCount(0);
      // Get row count
      int rowNum = model.getRowCount();
      double totalCost=0;
      // Calculate total cost
      for(Transaction t : transactions) {
        totalCost+=t.getAmount();
      }
      // Add rows from transactions list
      for(Transaction t : transactions) {
        model.addRow(new Object[]{rowNum+=1,t.getAmount(), t.getCategory(), t.getTimestamp()}); 
      }
        // Add total row
        Object[] totalRow = {"Total", null, null, totalCost};
        model.addRow(totalRow);
  
      // Fire table update
      transactionsTable.updateUI();
  
    }  
  

  
  
  public JButton getAddTransactionBtn() {
    return addTransactionBtn;
  }
  private void applyFilter() {
    String filterType = (String) filterTypeComboBox.getSelectedItem();
    String filterValue = filterValueField.getText();
    controller.applyFilter(filterType, filterValue);
}
  public DefaultTableModel getTableModel() {
    return model;
  }
  // Other view methods
    public JTable getTransactionsTable() {
    return transactionsTable;
  }

  public double getAmountField() {
    if(amountField.getText().isEmpty()) {
      return 0;
    }else {
    double amount = Double.parseDouble(amountField.getText());
    return amount;
    }
  }

  public void setAmountField(JFormattedTextField amountField) {
    this.amountField = amountField;
  }

  
  public String getCategoryField() {
    return categoryField.getText();
  }

  public void setCategoryField(JTextField categoryField) {
    this.categoryField = categoryField;
  }

private Transaction getTransactionAtRow(int row) {
  Object amountObj = model.getValueAt(row, 1);
    Object categoryObj = model.getValueAt(row, 2);

    // Check for null values
    if (amountObj == null || categoryObj == null) {
        return null;  // Return null if any required values are null
    }

    // Convert values to appropriate types
    double amount = (double) amountObj;
    String category = (String) categoryObj;

  return new Transaction(amount, category);
}
public void setController(ExpenseTrackerController controller){
  this.controller=controller;
}
private boolean isTransactionInList(Transaction transaction, List<Transaction> transactionList) {
  // Iterate through the list and check for a match
  for (Transaction t : transactionList) {
      if (areTransactionsEqual(transaction, t)) {
          return true; // Found a match
      }
  }
  return false; // No match found
}
private boolean areTransactionsEqual(Transaction t1, Transaction t2) {
  // Implement your custom logic to compare transactions for equality
  return Double.compare(t1.getAmount(), t2.getAmount()) == 0 &&
         t1.getCategory().equals(t2.getCategory());
}
}
