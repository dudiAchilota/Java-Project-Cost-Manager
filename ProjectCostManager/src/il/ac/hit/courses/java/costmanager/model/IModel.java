package il.ac.hit.courses.java.costmanager.model;

import java.sql.Date;

/**
 * IModel handles all the data in the database
 *
 * @author Dudi Achilota and Michael Lipshin
 */
public interface IModel {

    /**
     * Adds a new expense to the database
     *
     * @param item ExpenseItem object holds a reference to a new expense
     * @throws CostManagerException
     */
    public void addExpenseItem(ExpenseItem item) throws CostManagerException;


    /**
     * Deletes an expense by id
     *
     * @param item ExpenseItem object holds a reference to a new expense
     * @throws CostManagerException
     */
    public void deleteExpenseItem(ExpenseItem item) throws CostManagerException;

    /**
     * Builds a ReportBalance object that holds all the expense items in the database between start and end dates
     *
     * @param start Holds reference to an sql.date object for the starting date
     * @param end   Holds reference to an sql.date object for the ending date
     * @return ReportBalance object that holds all the data between the dates start and end
     * @throws CostManagerException
     */
    public ReportBalance getReport(Date start, Date end) throws CostManagerException;

    /**
     * Builds a PieChart object that holds two lists, one for categories and one for sum respectively
     *
     * @param start Holds reference to an sql.date object for the starting date
     * @param end   Holds reference to an sql.date object for the ending date
     * @return PieChart object that holds the sum by categories between the dates start and end
     * @throws CostManagerException
     */
    public PieChart getPieChart(Date start, Date end) throws CostManagerException;


    /**
     * Adds a new category to the table categories in the database
     *
     * @param category Represents the name of the new category
     * @throws CostManagerException
     */
    public void addCategory(String category) throws CostManagerException;

    /**
     * Extracts all the categories from the database table: categories
     *
     * @return Array of categories
     * @throws CostManagerException
     */
    public Category[] getCategories() throws CostManagerException;

    /**
     * Extracts all the currencies from the enum Currency
     *
     * @return Array of currencies
     */
    public Currency[] getArrayCurrencies();


    /**
     * Extracts all the data from the database on expense item
     *
     * @return DataAllExpenses object which holds all the expense item data
     * @throws CostManagerException
     */
    public DataAllExpenses getDataAllExpenses() throws CostManagerException;


}