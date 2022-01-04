package il.ac.hit.courses.java.costmanager.view;

import il.ac.hit.courses.java.costmanager.model.*;
import il.ac.hit.courses.java.costmanager.viewModel.IViewModel;

/**
 * IView Interface represents the user interface
 */

public interface IView {

    /**
     * Connects this object to the IViewModel interface
     *
     * @param vm Holds a reference to an IViewModel object
     */
    public void setViewModel(IViewModel vm);

    /**
     * Gets messages from different function windows and shows them to the user
     *
     * @param text       String represents a message to the user
     * @param nameScreen String represents a name of a window that will receive a message
     */
    public void showMessage(String text, String nameScreen);

    /**
     * Shows the current Expense table in the main menu
     *
     * @param data object which holds all the expense item data
     */
    public void showAllExpenses(DataAllExpenses data);

    /**
     * Shows a report of expenses in the Report function window
     *
     * @param rb object that holds all the data between dates
     */
    public void showReport(ReportBalance rb);

    /**
     * Shows a pie chart of expenses in the PieChart function window
     *
     * @param chart object that holds the sum by categories between dates
     */
    public void showPieChart(PieChart chart);

    /**
     * Adds categories to the screen "AddExpense"
     *
     * @param categories object that holds an array of category
     */
    public void showCategories(Category[] categories);

    /**
     * Adds currencies to the screen "AddExpense"
     *
     * @param currency object that holds an array of currency
     */
    public void showCurrencies(Currency[] currency);


    /**
     * Add category new to the screen "AddExpense"
     *
     * @param category object that holds a string of category
     */
    public void showNewCategory(String category);

    /**
     * Add to table Expense item new to the screen "screenMain"
     *
     * @param item ExpenseItem object holds a reference to a new expense
     */
    public void showNewExpenseItem(ExpenseItem item);

}
