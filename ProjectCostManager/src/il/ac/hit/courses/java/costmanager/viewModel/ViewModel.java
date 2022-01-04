package il.ac.hit.courses.java.costmanager.viewModel;

import il.ac.hit.courses.java.costmanager.model.*;
import il.ac.hit.courses.java.costmanager.view.IView;

import java.sql.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Implements the IViewModel Interface as the last object in the MVVM structure
 * Acts as the middleman between the View and the Model
 * Holds a reference to an IModel object and an IView object
 * Creates new threads for each functionality in the application inorder to maintain a flexible environment:
 * for example sometime in the future the IModel might be represented by a different object(different database etc')
 * it will be easier to implement it by using this object
 * for example by clicking a button in the View object an appropriate method immediately runs in the IViewModel object,
 * then IViewModel object runs an appropriate method in IModel object using a different thread
 */

public class ViewModel implements IViewModel {

    private IModel model;
    private IView view;
    private ExecutorService pool;

    /**
     * Constructs ViewModel which creates 10 new fixed threads in a pool
     */
    public ViewModel() {
        pool = Executors.newFixedThreadPool(10);
    }

    /**
     * Connects the View object to IViewModel object
     *
     * @param view IView object holds reference to a new view object
     */
    @Override
    public void setView(IView view) {
        this.view = view;
    }

    /**
     * Connects the Model object to IViewModel object
     *
     * @param model IModel object holds reference to a new model object
     */
    @Override
    public void setModel(IModel model) {
        this.model = model;
    }

    /**
     * Calls the addExpenseItem function in the model, returns an answer and updates the view
     *
     * @param item ExpenseItem object holds a reference to a new expense
     */
    @Override
    public void addExpenseItem(ExpenseItem item) {
        pool.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    // add expense to DB and send message appropriate
                    model.addExpenseItem(item);
                    view.showMessage("Expense added successfully", "addExpense");
                    // add expense to screen main
                    view.showNewExpenseItem(item);

                } catch (CostManagerException e) {
                    view.showMessage(e.getMessage(), "addExpense");
                }
            }
        });
    }

    /**
     * Calls the deleteExpenseItem function in the model, returns an answer and updates the view
     *
     * @param item ExpenseItem object holds a reference to a new expense
     */
    @Override
    public void deleteExpenseItem(ExpenseItem item) {
        pool.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    // delete expense from DB and send message appropriate
                    model.deleteExpenseItem(item);
                    view.showMessage("Expense deleted successfully", "main");

                } catch (CostManagerException e) {
                    view.showMessage(e.getMessage(), "main");
                }
            }
        });
    }

    /**
     * Calls the getReport function in the model, returns an answer and updates the view
     *
     * @param start Holds reference to an sql.date object for the starting date
     * @param end   Holds reference to an sql.date object for the ending date
     */
    @Override
    public void getReport(Date start, Date end) {
        pool.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    // gets object ReportBalance from DB
                    ReportBalance rb = model.getReport(start, end);
                    // show report to view
                    view.showReport(rb);
                    // check if data ListReport empty and send message appropriate
                    if (rb.getListReport().isEmpty()) {
                        view.showMessage("no data between the dates", "Report");
                    } else {
                        view.showMessage("Displaying list of reports , sum total =" + rb.getSumTotal()
                                , "Report");
                    }

                } catch (CostManagerException e) {
                    view.showMessage(e.getMessage(), "Report");
                }
            }
        });
    }

    /**
     * Calls the getDataAllExpense function in the model, returns an answer and updates the view
     */
    @Override
    public void getAllExpenses() {
        pool.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    // gets object DataAllExpenses from DB
                    DataAllExpenses data = model.getDataAllExpenses();
                    // show table to view
                    view.showAllExpenses(data);
                    // send message appropriate
                    view.showMessage("All the expenses shown above", "main");

                } catch (CostManagerException e) {
                    view.showMessage(e.getMessage(), "main");
                }
            }
        });
    }

    /**
     * Calls the getPieChart function in the model, returns an answer and updates the view
     *
     * @param start Holds reference to an sql.date object for the starting date
     * @param end   Holds reference to an sql.date object for the ending date
     */
    @Override
    public void getPieChart(Date start, Date end) {
        pool.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    // gets object PieChart from DB
                    PieChart pc = model.getPieChart(start, end);
                    // show pie chart to view
                    view.showPieChart(pc);
                    // check if data empty and send message appropriate
                    if (pc.getListSum().isEmpty())
                        view.showMessage("no data between the dates", "PieChart");
                    else
                        view.showMessage("Displaying a pie chart based on categories ,sum total=" + pc.getSumTotal()
                                , "PieChart");

                } catch (CostManagerException e) {
                    view.showMessage(e.getMessage(), "PieChart");
                }
            }
        });
    }

    /**
     * Calls the addCategory function in the model, returns an answer and updates the view
     *
     * @param category Represents the name of the new category
     */
    @Override
    public void addCategory(String category) {
        pool.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    // add category new to DB and send message appropriate
                    model.addCategory(category);
                    view.showMessage("Category Added successfully", "addCategory");
                    // add category new to screen appropriate
                    view.showNewCategory(category);

                } catch (CostManagerException e) {
                    view.showMessage(e.getMessage(), "addCategory");
                }
            }
        });
    }

    /**
     * Calls the getCategories function in the model, returns an answer and updates the view
     */
    @Override
    public void getCategories() {
        pool.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    // gets array categories from DB
                    Category[] categories = model.getCategories();
                    // adds categories to view
                    view.showCategories(categories);

                } catch (CostManagerException e) {
                    view.showMessage(e.getMessage(), "main");
                }
            }
        });
    }

    /**
     * Calls the getArrayCurrencies function in the model and updates the view
     */
    @Override
    public void getCurrencies() {
        pool.submit(new Runnable() {
            @Override
            public void run() {
                // gets array categories from model
                Currency[] currencies = model.getArrayCurrencies();
                // adds currencies new to view
                view.showCurrencies(currencies);
            }
        });
    }

}


