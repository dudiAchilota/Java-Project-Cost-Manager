package il.ac.hit.courses.java.costmanager.model;

import java.sql.*;
import java.sql.Date;
import java.util.*;

/**
 * Implements the IModel interface, DerbyDBModel object is responsible for the database
 * Adds the following tablets to the database: tableCostManager, categories, Id
 * The database is responsible for saving and storing expense items and categories
 *
 * @author Dudi Achilota and Michael Lipshin
 */
public class DerbyDBModel implements IModel {

    private String tableName = "tableCostManager";// name of table CostManager
    private String driver = "org.apache.derby.jdbc.EmbeddedDriver";
    private String protocol = "jdbc:derby:derbyDB;create=true";


    /**
     * constructor DerbyDBModel
     * create tables
     */
    public DerbyDBModel() throws CostManagerException {
        try {
            createTables();

        } catch (CostManagerException e) {
           throw new CostManagerException(e.getMessage());
        }
    }


    /**
     * Checks if there are existing tablets: TABLECOSTMANAGER, CATEGORIES, IDNEW
     * if not existing, creates new tablets with the names above
     * in the installation of CATEGORIES adds default categories
     * in the installation of IDNEW gives the num 1 as an initial id
     *
     * @throws CostManagerException
     */
    private void createTables() throws CostManagerException {
        /*
         * first installation create Tables TABLECOSTMANAGER ,CATEGORIES ,IDNEW if not find in list Tables
         * TABLECOSTMANAGER - create Table
         * CATEGORIES - create Table and add to Table categories default
         * IDNEW - create Table and installation that the value 1
         */
        Connection connection = null;
        Statement statement = null;
        Object props = null;
        ResultSet rs = null;
        List<String> tableNames = null;
        DatabaseMetaData md = null;

        try {
            // Connects to the SQL table
            Class.forName(driver).newInstance();
            connection = DriverManager.getConnection(protocol, (Properties) props);
            statement = connection.createStatement();

            // get tables from DB
            md = connection.getMetaData();
            rs = md.getTables(null, null, "%", null);

            // Adds the table names to the list - tableNames
            tableNames = new LinkedList<String>();
            while (rs.next()) {
                tableNames.add(rs.getString(3));
            }


            // first installation - if the table "TABLECOSTMANAGER" does not exist then create a table
            if (tableNames.contains(tableName.toUpperCase()) == false) {
                // System.out.println("2");
                // add table costs "tableCostManager"
                boolean check = statement.execute("create table " + tableName +
                        " (id int, Description varchar(255),money double,category varchar(255),date Date" +
                        " ,currency varchar(255))");
                // Checking if the operation is performed expects TRUE
                if (check == true)
                    throw new CostManagerException("tableCostManager - No table was built");

            }


            // first installation - if the table "CATEGORIES" does not exist then create a table
            if (tableNames.contains("CATEGORIES") == false) {
                // add table Categories
                boolean check = statement.execute("create table Categories ( category varchar(255) )");
                // Checking if the operation is performed expects TRUE
                if (check == true)
                    throw new CostManagerException("Categories - No table was built");

                // list Categories default
                String Categories[] = {"General", "Food", "Household", "Vacation", "Entertainment",
                        "Automobile", "Furniture", "Clothing", "Cell phones"};

                // add Categories default to DB
                int expected = 0;
                for (String category : Categories) {
                    expected = statement.executeUpdate("insert into Categories values ( '" + category + "' )");

                    // Checks that the operation was performed Expected to 1
                    if (expected != 1)
                        throw new CostManagerException("No action was taken insert into");
                }
            }


            // first installation - if the table "IDNEW" does not exist then create a table
            if (tableNames.contains("IDNEW") == false) {
                // add table IDNEW
                boolean check = statement.execute("create table idNew (id int)");
                // Checking if the operation is performed expects TRUE
                if (check == true)
                    throw new CostManagerException("idNew - No table was built");


                // Adds 1 to the table for a starting number for an ID
                int expected = statement.executeUpdate("insert into idNew values ( 1 )");
                // Checks that the operation was performed Expected to 1
                if (expected != 1)
                    throw new CostManagerException("No action was taken insert into");
            }


        } catch (InstantiationException e) {
            throw new CostManagerException("Instantiation Exception - " + e.getMessage(), e);
        } catch (IllegalAccessException e) {
            throw new CostManagerException("Il legal Access Exception - " + e.getMessage(), e);
        } catch (ClassNotFoundException e) {
            throw new CostManagerException("Class Not Found Exception - " + e.getMessage(), e);
        } catch (SQLException throwables) {
            throw new CostManagerException("SQL Exception -" + throwables.getMessage(), throwables);
        } finally {
            // restart
            tableNames = null;
            md = null;

            // Close connection , statement and rs - ResultSet
            if (statement != null) {
                try {
                    statement.close();
                } catch (Exception var29) {
                }
            }

            if (connection != null) {
                try {
                    connection.close();
                } catch (Exception var28) {
                }
            }
            if (rs != null) {
                try {
                    rs.close();
                } catch (Exception var27) {
                }
            }
        }

    }


    /**
     * Extracts an id from the database and updates the upcoming next id
     *
     * @return a new id for expense item
     * @throws CostManagerException
     */
    private int getId() throws CostManagerException {
        /*
         * Takes the ID from the table "idnew" and return it
         * and update id to id next.
         */
        Connection connection = null;
        Statement statement = null;
        Object props = null;
        ResultSet rs = null;
        int getId;
        int updateId;
        int expected;

        try {
            // Connects to the SQL table
            connection = DriverManager.getConnection(protocol, (Properties) props);
            statement = connection.createStatement();

            // Takes the ID in the table to be used for the ID new  save to "getId"
            rs = statement.executeQuery("select id from idNew");
            rs.next();
            getId = rs.getInt("id");

            // Updates the ID to the next ID
            updateId = getId + 1;
            expected = statement.executeUpdate("UPDATE idNew SET id =" + updateId + " WHERE id =" + getId);

            // Checks that the operation was performed Expected to 1
            if (expected == 0)
                throw new CostManagerException("No action was taken insert into");


        } catch (SQLException throwables) {
            throw new CostManagerException("SQL Exception -" + throwables.getMessage(), throwables);
        } finally {
            // Close connection , statement and rs - ResultSet
            if (statement != null) {
                try {
                    statement.close();
                } catch (Exception var29) {
                }
            }

            if (connection != null) {
                try {
                    connection.close();
                } catch (Exception var28) {
                }
            }
            if (rs != null) {
                try {
                    rs.close();
                } catch (Exception var27) {
                }
            }
        }

        return getId;
    }


    /**
     * Adds a new expense to the database
     *
     * @param item ExpenseItem object holds a reference to a new expense
     * @throws CostManagerException
     */
    @Override
    public void addExpenseItem(ExpenseItem item) throws CostManagerException {
        Connection connection = null;
        Statement statement = null;
        Object props = null;


        try {
            // set id
            int id = getId();
            item.setId(id);
        } catch (CostManagerException e) {
            throw new CostManagerException(e.getMessage());
        }

        try {
            // Connects to the SQL table
            connection = DriverManager.getConnection(protocol, (Properties) props);
            statement = connection.createStatement();


            // add expense item
            int expected = statement.executeUpdate("insert into " + tableName + " values (" + item.toSQL() + ")");
            // Checks that the operation was performed Expected to 1
            if (expected != 1) {
                throw new CostManagerException("No action was taken insert into");
            }

        } catch (SQLException throwables) {
            throw new CostManagerException("SQL Exception -" + throwables.getMessage(), throwables);
        } finally {
            // Close connection and statement
            if (statement != null) {
                try {
                    statement.close();
                } catch (Exception var29) {
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (Exception var28) {
                }
            }
        }
    }


    /**
     * Deletes an expense by id
     *
     * @param item ExpenseItem object holds a reference to a new expense
     * @throws CostManagerException
     */
    @Override
    public void deleteExpenseItem(ExpenseItem item) throws CostManagerException {
        Connection connection = null;
        Statement statement = null;
        Object props = null;

        try {
            // Connects to the SQL table
            connection = DriverManager.getConnection(protocol, (Properties) props);
            statement = connection.createStatement();

            // delete expense Item
            int expected = statement.executeUpdate("delete from " + tableName + " where id=" + item.getId());
            // Checks that the operation was performed Expected to 1 or not 0
            if (expected == 0) {
                throw new CostManagerException("No action was taken insert into");
            }


        } catch (SQLException throwables) {
            throw new CostManagerException("SQL Exception -" + throwables.getMessage(), throwables);
        } finally {
            // Close connection and statement
            if (statement != null) {
                try {
                    statement.close();
                } catch (Exception var29) {
                }
            }

            if (connection != null) {
                try {
                    connection.close();
                } catch (Exception var28) {
                }
            }

        }
    }


    /**
     * Builds a ReportBalance object that holds all the expense items in the database between start and end dates
     *
     * @param start Holds reference to an sql.date object for the starting date
     * @param end   Holds reference to an sql.date object for the ending date
     * @return ReportBalance object that holds all the data between the dates start and end
     * @throws CostManagerException
     */
    @Override
    public ReportBalance getReport(Date start, Date end) throws CostManagerException {
        /*
         * Takes information about each items between dates and puts them in a list data - "vectorItem"
         * and on a list data -"vectorItem" on a specific item put a general list - "vectors Data"
         */
        Connection connection = null;
        Statement statement = null;
        Object props = null;
        ResultSet rs = null;
        ReportBalance report = null;

        try {
            // Connects to the SQL table
            connection = DriverManager.getConnection(protocol, (Properties) props);
            statement = connection.createStatement();

            // vector of it contains list vectors of all vector it contains data on expense specific
            List<List<String>> vectorsData = new Vector<>();
            double sumTotal = 0;


            // select of all the item between date start to date end
            rs = statement.executeQuery("select * from " + tableName +
                    " where date between '" + start + "' and '" + end + "' ORDER BY date");

            // Insert to vectorItem data on expense specific and vectorItem Insert to vectorsData
            while (rs.next()) {
                List<String> vectorItem = new Vector<String>();

                vectorItem.add(rs.getString("money"));
                vectorItem.add(rs.getString("category"));
                vectorItem.add(rs.getString("currency"));
                vectorItem.add(rs.getString("date"));
                vectorItem.add(rs.getString("description"));
                vectorItem.add(rs.getString("id"));
                // add vectorItem to vectorsData
                vectorsData.add(vectorItem);
                // sum Total of all costs between dates
                sumTotal += rs.getDouble("money");
            }

            // add column Names of kind data on expense
            List<String> columnNames = new Vector<String>();
            columnNames.add("Sum");
            columnNames.add("Category");
            columnNames.add("Currency");
            columnNames.add("Date");
            columnNames.add("Description");
            columnNames.add("id");

            // Operator builder ReportBalance
            report = new ReportBalance(vectorsData, columnNames, sumTotal);


        } catch (SQLException throwables) {
            throw new CostManagerException("SQL Exception -" + throwables.getMessage(), throwables);
        } finally {
            // Close connection , statement and rs - ResultSet
            if (statement != null) {
                try {
                    statement.close();
                } catch (Exception var29) {
                }
            }

            if (connection != null) {
                try {
                    connection.close();
                } catch (Exception var28) {
                }
            }

            if (rs != null) {
                try {
                    rs.close();
                } catch (Exception var27) {
                }
            }
        }

        return report;
    }


    /**
     * Builds a PieChart object that holds two lists, one for categories and one for sum respectively
     *
     * @param start Holds reference to an sql.date object for the starting date
     * @param end   Holds reference to an sql.date object for the ending date
     * @return PieChart object that holds the sum by categories between the dates start and end
     * @throws CostManagerException
     */
    @Override
    public PieChart getPieChart(Date start, Date end) throws CostManagerException {
        Connection connection = null;
        Statement statement = null;
        Object props = null;
        ResultSet rs = null;
        PieChart pc;

        try {
            // Connects to the SQL table
            connection = DriverManager.getConnection(protocol, (Properties) props);
            statement = connection.createStatement();

            // A list in which we will enter a category
            // and the total amount of the category will be in the same index in the sum list
            List<Double> vectorSum = new Vector<Double>();
            List<String> vectorCategory = new Vector<String>();
            // sum total - sum of all expenses between dates
            double sumTotal = 0.0;


            // Select the amount of expenses by category between dates
            rs = statement.executeQuery("select sum(money) as sumMoney ,category from " + tableName +
                    " where date between '" + start + "' and '" + end + "' group by category");

            // add category , sum to lists and concludes sum total
            while (rs.next()) {
                vectorSum.add(rs.getDouble("sumMoney"));
                vectorCategory.add(rs.getString("category"));

                sumTotal += rs.getDouble("sumMoney");
            }
            // Operator builder PieChart
            pc = new PieChart(vectorSum, vectorCategory, sumTotal);


        } catch (SQLException throwables) {
            throw new CostManagerException("SQL Exception -" + throwables.getMessage(), throwables);
        } finally {
            // Close connection , statement and rs - ResultSet
            if (statement != null) {
                try {
                    statement.close();
                } catch (Exception var29) {
                }
            }

            if (connection != null) {
                try {
                    connection.close();
                } catch (Exception var28) {
                }
            }

            if (rs != null) {
                try {
                    rs.close();
                } catch (Exception var27) {
                }
            }

        }

        return pc;
    }


    /**
     * Adds a new category to the table categories in the database
     *
     * @param category Represents the name of the new category
     * @throws CostManagerException
     */
    @Override
    public void addCategory(String category) throws CostManagerException {
        Connection connection = null;
        Statement statement = null;
        Object props = null;

        try {
            // Connects to the SQL table
            connection = DriverManager.getConnection(protocol, (Properties) props);
            statement = connection.createStatement();

            // add Category to DB to table Categories
            int expected = statement.executeUpdate("insert into Categories values ( '" + category + "' )");

            // Checks that the operation was performed Expected to 1
            if (expected != 1) {
                throw new CostManagerException("No action was taken insert into");
            }


        } catch (SQLException throwables) {
            throw new CostManagerException("SQL Exception -" + throwables.getMessage(), throwables);
        } finally {
            // Close connection and statement
            if (statement != null) {
                try {
                    statement.close();
                } catch (Exception var29) {
                }
            }

            if (connection != null) {
                try {
                    connection.close();
                } catch (Exception var28) {
                }
            }

        }
    }


    /**
     * Extracts all the categories from the database table: categories
     *
     * @return Array of categories
     * @throws CostManagerException
     */
    @Override
    public Category[] getCategories() throws CostManagerException {
        Connection connection = null;
        Statement statement = null;
        Object props = null;
        ResultSet rs = null;

        List<Category> listCategories = new LinkedList<Category>();

        try {
            // Connects to the SQL table
            connection = DriverManager.getConnection(protocol, (Properties) props);
            statement = connection.createStatement();


            // select all category from DB and add categories to list
            rs = statement.executeQuery("select category from Categories");
            while (rs.next()) {
                listCategories.add(new Category(rs.getString("category")));
            }


        } catch (SQLException throwables) {
            throw new CostManagerException("SQL Exception -" + throwables.getMessage(), throwables);
        } finally {
            // Close connection , statement and rs - ResultSet
            if (statement != null) {
                try {
                    statement.close();
                } catch (Exception var29) {
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (Exception var28) {
                }
            }
            if (rs != null) {
                try {
                    rs.close();
                } catch (Exception var27) {
                }
            }
        }

        // send array instead of list
        return listCategories.toArray(new Category[0]);
    }


    /**
     * Extracts all the currencies from the enum Currency
     *
     * @return Array of currencies
     */
    @Override
    public Currency[] getArrayCurrencies() {
        return Currency.ILS.getArrayCurrencies();
    }


    /**
     * Extracts all the data from the database on expense item
     *
     * @return DataAllExpenses object which holds all the expense item data
     * @throws CostManagerException
     */
    @Override
    public DataAllExpenses getDataAllExpenses() throws CostManagerException {
        /*
         * Takes information about each items and puts them in a list "vector Item"
         * and on a "vector Item" on a specific item put a general list - "vectors data"
         */
        Connection connection = null;
        Statement statement = null;
        Object props = null;
        ResultSet rs = null;
        DataAllExpenses dataAllExpenses = null;

        try {
            // Connects to the SQL table
            connection = DriverManager.getConnection(protocol, (Properties) props);
            statement = connection.createStatement();

            // vectorsData it contains list vectors
            // and all vector it contains data on expense specific - id,sum,category,currency,date,description
            List<List<String>> vectorsData = new Vector<>();


            // select of all the expense By ID in descending order
            rs = statement.executeQuery("select * from " + tableName + " ORDER BY id DESC");


            // Insert to vectorItem data on expense specific and vectorItem Insert to vectorsData
            while (rs.next()) {
                List<String> vectorItem = new Vector<String>();

                vectorItem.add(rs.getString("money"));
                vectorItem.add(rs.getString("category"));
                vectorItem.add(rs.getString("currency"));
                vectorItem.add(rs.getString("date"));
                vectorItem.add(rs.getString("description"));
                vectorItem.add(rs.getString("id"));
                // add vectorItem to vectorsData
                vectorsData.add(vectorItem);
            }

            // add column Names of kind data on expense
            List<String> columnNames = new Vector<String>();
            columnNames.add("Sum");
            columnNames.add("Category");
            columnNames.add("Currency");
            columnNames.add("Date");
            columnNames.add("Description");
            columnNames.add("id");

            // Operator builder DataAllExpenses
            dataAllExpenses = new DataAllExpenses(vectorsData, columnNames);


        } catch (SQLException throwables) {
            throw new CostManagerException("SQL Exception -" + throwables.getMessage(), throwables);
        } finally {
            // Close connection , statement and rs - ResultSet
            if (statement != null) {
                try {
                    statement.close();
                } catch (Exception var29) {
                }
            }

            if (connection != null) {
                try {
                    connection.close();
                } catch (Exception var28) {
                }
            }

            if (rs != null) {
                try {
                    rs.close();
                } catch (Exception var27) {
                }
            }
        }

        return dataAllExpenses;
    }

}
