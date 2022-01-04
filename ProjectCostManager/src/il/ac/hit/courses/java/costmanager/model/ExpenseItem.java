package il.ac.hit.courses.java.costmanager.model;

import java.sql.Date;

/**
 * This class represents a person's expense
 */
public class ExpenseItem {
    private int id;
    private Date date;
    private String description;
    private Category category;
    private double sum;
    private Currency currency;

    /**
     * Constructs a new expense
     * The Id is extracted from the database while add item to DB
     *
     * @param description A string described by the user about the expense
     * @param sum         The sum of the expense
     * @param currency    The coin of the expense
     * @param category    To which category the expense belongs to
     * @param date        The time of the expense
     */
    public ExpenseItem(String description, double sum, Currency currency, Category category, Date date) {
        setDescription(description);
        setSum(sum);
        setCurrency(currency);
        setCategory(category);
        setDate(date);
    }


    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public double getSum() {
        return this.sum;
    }

    public void setSum(double sum) {
        if (sum > 0.0D)
            this.sum = sum;
    }


    public Category getCategory() {
        return this.category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }


    public Date getDate() {
        return this.date;
    }

    public void setDate(Date date) {
        this.date = date;
    }


    public Currency getCurrency() {
        return this.currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }


    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    /**
     * Converts expense item to string in order to store in the table database
     *
     * @return A string suitable for the database
     */
    public String toSQL() {
        return id +
                ", '" + description + '\'' +
                ", " + sum +
                ", '" + category + '\'' +
                ", '" + date + '\'' +
                ", '" + currency.name() + '\''
                ;
    }


    @Override
    public String toString() {
        return "ExpenseItem{" +
                "id=" + id +
                ", date=" + date +
                ", description='" + description + '\'' +
                ", category=" + category +
                ", sum=" + sum +
                ", currency=" + currency +
                '}';
    }

}