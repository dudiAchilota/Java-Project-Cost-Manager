package il.ac.hit.courses.java.costmanager.viewModel;

import il.ac.hit.courses.java.costmanager.model.*;
import il.ac.hit.courses.java.costmanager.view.IView;

import java.sql.Date;

/**
 * IViewModel interface is the middleman between IModel interface and IView interface.
 */

public interface IViewModel {
    /**
     * Connects the View object to IViewModel object
     *
     * @param view IView object holds reference to a new view object
     */
    public void setView(IView view);

    /**
     * Connects the Model object to IViewModel object
     *
     * @param model IModel object holds reference to a new model object
     */
    public void setModel(IModel model);

    /**
     * Calls the addExpenseItem function in the model, returns an answer and updates the view
     *
     * @param item ExpenseItem object holds a reference to a new expense
     */
    public void addExpenseItem(ExpenseItem item);

    /**
     * Calls the deleteExpenseItem function in the model, returns an answer and updates the view
     *
     * @param item ExpenseItem object holds a reference to a new expense
     */
    public void deleteExpenseItem(ExpenseItem item);

    /**
     * Calls the getReport function in the model, returns an answer and updates the view
     *
     * @param start Holds reference to an sql.date object for the starting date
     * @param end   Holds reference to an sql.date object for the ending date
     */
    public void getReport(Date start, Date end);

    /**
     * Calls the getDataAllExpense function in the model, returns an answer and updates the view
     */
    public void getAllExpenses();

    /**
     * Calls the getPieChart function in the model, returns an answer and updates the view
     *
     * @param start Holds reference to an sql.date object for the starting date
     * @param end   Holds reference to an sql.date object for the ending date
     */
    public void getPieChart(Date start, Date end);

    /**
     * Calls the addCategory function in the model, returns an answer and updates the view
     *
     * @param category Represents the name of the new category
     */
    public void addCategory(String category);

    /**
     * Calls the getCategories function in the model, returns an answer and updates the view
     */
    public void getCategories();

    /**
     * Calls the getArrayCurrencies function in the model and updates the view
     */
    public void getCurrencies();

}
