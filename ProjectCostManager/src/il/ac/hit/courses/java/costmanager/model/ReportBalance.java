package il.ac.hit.courses.java.costmanager.model;

import java.util.List;

/**
 * This class represents a detailed report of expenses between certain dates
 */
public class ReportBalance {

    // vector of it contains list vectors of all vector it contains data on expense
    private List<List<String>> listReport;
    private List<String> columnNames;
    private double sumTotal;

    /**
     * Constructs a new detailed report
     *
     * @param listReport  represents a list of expenses, each expense represented by a list of all data about the expense
     * @param columnNames represents the list of the types data in an expense
     * @param sumTotal    The total expense sum between dates
     */
    public ReportBalance(List<List<String>> listReport, List<String> columnNames, double sumTotal) {
        setListReport(listReport);
        setColumnNames(columnNames);
        setSumTotal(sumTotal);
    }


    public List<List<String>> getListReport() {
        return listReport;
    }

    public void setListReport(List<List<String>> listReport) {
        this.listReport = listReport;
    }

    public List<String> getColumnNames() {
        return columnNames;
    }

    public void setColumnNames(List<String> columnNames) {
        this.columnNames = columnNames;
    }

    public double getSumTotal() {
        return sumTotal;
    }

    public void setSumTotal(double sumTotal) {
        this.sumTotal = sumTotal;
    }


    @Override
    public String toString() {
        return "ReportBalance{" +
                "listReport=" + listReport +
                ", columnNames=" + columnNames +
                ", sumTotal=" + sumTotal +
                '}';
    }

}
