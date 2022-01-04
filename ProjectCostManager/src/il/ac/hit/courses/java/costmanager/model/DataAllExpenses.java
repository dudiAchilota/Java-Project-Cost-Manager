package il.ac.hit.courses.java.costmanager.model;

import java.util.List;
import java.util.Vector;

/**
 * This class represents all the data on expense item
 */
public class DataAllExpenses {

    private List<List<String>> listData;
    private List<String> columnNames;

    /**
     * Constructs a new list of all data
     *
     * @param listData    represents a list of expenses, each expense represented by a list of all data about the expense
     * @param columnNames represents the list of the types data in an expense
     */
    public DataAllExpenses(List<List<String>> listData, List<String> columnNames) {
        setListData(listData);
        setColumnNames(columnNames);
    }

    public List<List<String>> getListData() {
        return listData;
    }

    public void setListData(List<List<String>> listData) {
        this.listData = listData;
    }

    public List<String> getColumnNames() {
        return columnNames;
    }

    public void setColumnNames(List<String> columnNames) {
        this.columnNames = columnNames;
    }


    /**
     * Adds a new expense to the list
     *
     * @param item ExpenseItem object holds a reference to a new expense
     */
    public void addItem(ExpenseItem item) {
        List<String> vector = new Vector<String>();
        // add data of item to vector
        vector.add(String.valueOf(item.getSum()));
        vector.add(String.valueOf(item.getCategory()));
        vector.add(String.valueOf(item.getCurrency()));
        vector.add(String.valueOf(item.getDate()));
        vector.add(item.getDescription());

        vector.add(String.valueOf(item.getId()));

        // add vector to first listData
        listData.add(0, vector);
    }


    @Override
    public String toString() {
        return "DataAllExpenses{" +
                "listData=" + listData +
                ", columnNames=" + columnNames +
                '}';
    }

}