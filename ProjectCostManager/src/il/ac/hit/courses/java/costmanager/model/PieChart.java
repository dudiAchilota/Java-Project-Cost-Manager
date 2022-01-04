package il.ac.hit.courses.java.costmanager.model;

import java.util.List;

/**
 * This class represents data between dates for building a pie chart
 */
public class PieChart {

    private List<Double> listSum;
    private List<String> listCategory;
    private double sumTotal;

    /**
     * Constructs new lists of data for the pie chart
     *
     * @param listSum      represents a list of sums for all expenses in a certain category respectively for listCategory
     *                     for example, listSum[100, 200]; listCategory[food, clothes]
     *                     all the expenses between the dates for food equals 100 and clothes are 200
     * @param listCategory represents a list of categories for all expenses in a certain sum respectively for listSum
     *                     for example, listSum[100, 200]; listCategory[food, clothes]
     *                     all the expenses between the dates for food equals 100 and clothes are 200
     * @param sumTotal     The total expense sum between dates
     */
    public PieChart(List<Double> listSum, List<String> listCategory, double sumTotal) {
        setListSum(listSum);
        setListCategory(listCategory);
        setSumTotal(sumTotal);
    }

    public List<Double> getListSum() {
        return listSum;
    }

    public void setListSum(List<Double> listSum) {
        this.listSum = listSum;
    }

    public List<String> getListCategory() {
        return listCategory;
    }

    public void setListCategory(List<String> listCategory) {
        this.listCategory = listCategory;
    }

    public double getSumTotal() {
        return sumTotal;
    }

    public void setSumTotal(double sumTotal) {
        this.sumTotal = sumTotal;
    }

    @Override
    public String toString() {
        return "PieChart{" +
                "listSum=" + listSum +
                ", listCategory=" + listCategory +
                ", sumTotal=" + sumTotal +
                '}';
    }

}
