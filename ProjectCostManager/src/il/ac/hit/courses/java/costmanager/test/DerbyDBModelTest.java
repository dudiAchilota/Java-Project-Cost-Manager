package il.ac.hit.courses.java.costmanager.test;

import il.ac.hit.courses.java.costmanager.model.*;

import java.sql.*;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Vector;

import static org.junit.jupiter.api.Assertions.*;

class DerbyDBModelTest {
    private String driver = "org.apache.derby.jdbc.EmbeddedDriver";
    private String protocol = "jdbc:derby:derbyDB;create=true";
    private String tableName = "TableCostManager";

    private Date[] dates;
    private ExpenseItem[] items;

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        dates = new Date[]{
                new Date(2019 - (1900), 6 - 1, 1),
                new Date(2019 - (1900), 6 - 1, 1),
                new Date(2020 - (1900), 4 - 1, 1),
                new Date(2020 - (1900), 3 - 1, 1)
        };
        items = new ExpenseItem[]{
                new ExpenseItem("a", 150, Currency.EURO, new Category("new"), dates[0]),
                new ExpenseItem("b", 200, Currency.USD, new Category("Game"), dates[1]),
                new ExpenseItem("c", 300, Currency.ILS, new Category("food"), dates[2]),
                new ExpenseItem("d", 400, Currency.GBP, new Category("new"), dates[3]),
        };
    }

    @org.junit.jupiter.api.AfterEach
    void tearDown() {
        // delete dates
        for (Date date : dates)
            date = null;
        dates = null;

        // delete items
        for (ExpenseItem item : items)
            item = null;
        items = null;
    }


    @org.junit.jupiter.api.Test
    void addExpenseItem() throws CostManagerException {
        /*
         * add Expense item and check of save in DB per id
         * finally delete Expense item
         */

        IModel db = new DerbyDBModel();
        ExpenseItem item = items[0];

        Statement statement = null;
        Connection connection = null;
        ResultSet rs = null;
        Object props = null;

        try {

            // add ExpenseItem to DB
            db.addExpenseItem(item);

            // Connects to the SQL table
            connection = DriverManager.getConnection(protocol, (Properties) props);
            statement = connection.createStatement();


            // Checks if id of "item" find in DB
            rs = statement.executeQuery("SELECT id from " + tableName + " WHERE id=" + item.getId());
            rs.next();
            assertEquals(rs.getInt("id"), item.getId());


            // Reset the DB delete items[0]
            int expected = statement.executeUpdate("delete from " + tableName + " where id=" + item.getId());
            // Checks that the operation was performed Expected to false
            if (expected == 0) {
                throw new CostManagerException("No action was taken insert into");
            }

        } catch (SQLException throwables) {
            throw new CostManagerException("SQL Exception -" + throwables.getMessage(), throwables);

        } finally {
            //Reset the objects
            db = null;
            item = null;
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

    @org.junit.jupiter.api.Test
    void deleteExpenseItem() throws CostManagerException {
        /*
         * add Expense item and later delete Expense item
         * check if delete by that id not find in DB
         */
        Statement statement = null;
        Connection connection = null;
        ResultSet rs = null;
        Object props = null;

        IModel db = new DerbyDBModel();
        List listId = new ArrayList();

        try {
            // Connects to the SQL table
            connection = DriverManager.getConnection(protocol, (Properties) props);
            statement = connection.createStatement();

            // add ExpenseItem from DB
            db.addExpenseItem(items[1]);
            // delete ExpenseItem from DB
            db.deleteExpenseItem(items[1]);

            // add all id to listId
            rs = statement.executeQuery("SELECT id from " + tableName);
            while (rs.next()) {
                listId.add(rs.getInt("id"));
            }

            // Checks if id no find in DB
            assertFalse(listId.contains(items[1].getId()));

        } catch (SQLException throwables) {
            throw new CostManagerException("SQL Exception -" + throwables.getMessage(), throwables);
        } catch (CostManagerException e) {
            e.printStackTrace();
        } finally {
            // Reset the objects
            listId = null;
            db = null;
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

    @org.junit.jupiter.api.Test
    void getReportBalance() throws CostManagerException {
        /*
         * add 4 ExpenseItem to DB and Activates a function getReport between dates
         * check of that 2 item that between dates find in list report per id
         * and check of that 2 item that not between dates  not find in list report per id.
         * finally delete all Expense item.
         */
        IModel db = new DerbyDBModel();
        Date start = new Date(2019 - (1900), 1 - 1, 1);
        Date end = new Date(2019 - (1900), 12 - 1, 31);

        try {
            // add all ExpenseItem to DB
            for (ExpenseItem item : items) {
                db.addExpenseItem(item);
            }

            // Activates a function getReport
            ReportBalance rb = db.getReport(start, end);

            // Teaks list report
            List<List<String>> vectorReport = rb.getListReport();

            List<Integer> ids = new Vector<>();
            List<String> vector;

            // Gets the location of "id"
            int index = rb.getColumnNames().indexOf("id");


            for (int i = 0; i < vectorReport.size(); i++) {
                // Teaks all id from list
                vector = vectorReport.get(i);

                ids.add(Integer.parseInt(vector.get(index)));
            }


            // Checks if id find in list
            assertTrue(ids.contains(items[0].getId()));
            assertTrue(ids.contains(items[1].getId()));
            // Checks if id no find in list
            assertFalse(ids.contains(items[2].getId()));
            assertFalse(ids.contains(items[3].getId()));


            // Reset the objects
            rb = null;
            ids = null;
            vectorReport = null;
            vector = null;

        } catch (CostManagerException e) {
            e.printStackTrace();
        } catch (NumberFormatException e) {
            throw new CostManagerException(e.getMessage());

        } finally {
            // delete all ExpenseItem from DB
            for (ExpenseItem item : items) {
                db.deleteExpenseItem(item);
            }

            // Reset the objects
            db = null;
            start = null;
            end = null;
        }
    }

    @org.junit.jupiter.api.Test
    void getPieChart() throws CostManagerException {
        /*
         * add 4 ExpenseItem to DB and Activates a function getPieChart between dates
         * Checks if sum of category "new" equals 150 and sum of category "Game" equals 200
         * finally delete all Expense item
         */
        IModel db = new DerbyDBModel();
        Date start = new Date(2019 - (1900), 1 - 1, 1);
        Date end = new Date(2019 - (1900), 12 - 1, 31);

        try {
            // add all ExpenseItem to DB
            for (ExpenseItem item : items) {
                db.addExpenseItem(item);
            }

            // get pie chart from DB
            PieChart pc = db.getPieChart(start, end);

            int index;
            // get list sum,category from pie chart
            List<Double> vectorSum = pc.getListSum();
            List<String> vectorCategory = pc.getListCategory();

            // Checks if sum of category "new" equals 150
            index = vectorCategory.indexOf("new");
            assertEquals(vectorSum.get(index), 150);

            // Checks if sum of category "Game" equals 200
            index = vectorCategory.indexOf("Game");
            assertEquals(vectorSum.get(index), 200);


            // Reset the objects
            pc = null;
            vectorCategory = null;
            vectorSum = null;

        } catch (CostManagerException e) {
            e.printStackTrace();
        } catch (NumberFormatException e) {
            throw new CostManagerException(e.getMessage());
        } finally {
            // delete all ExpenseItem from DB
            for (ExpenseItem item : items) {
                db.deleteExpenseItem(item);
            }

            // Reset the objects
            db = null;
            start = null;
            end = null;
        }

    }

    @org.junit.jupiter.api.Test
    void addCategory() throws CostManagerException {
        /*
         *  add category new in name "new"
         *  Checks if category "new" find in DB
         *  finally delete category "new"
         */
        Statement statement = null;
        Connection connection = null;
        ResultSet rs = null;
        Object props = null;

        IModel db = new DerbyDBModel();
        try {
            // add Category new in name "new"
            db.addCategory("new");

            // Connects to the SQL table
            connection = DriverManager.getConnection(protocol, (Properties) props);
            statement = connection.createStatement();

            // Checks if category "new" find in DB
            rs = statement.executeQuery("SELECT category from categories WHERE category= 'new' ");
            rs.next();
            assertEquals("new", rs.getString("category"));

            // delete category
            int expected = statement.executeUpdate("delete from categories where category= 'new' ");
            // Checks that the operation was performed Expected to 1
            if (expected != 1) {
                throw new CostManagerException("No action was taken insert into");
            }

        } catch (CostManagerException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throw new CostManagerException("SQL Exception -" + throwables.getMessage(), throwables);
        } finally {
            // Reset the objects
            db = null;
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

    @org.junit.jupiter.api.Test
    void getDataAllExpense() throws CostManagerException {
        /*
         * add 4 ExpenseItem to DB and Activates a function getDataAllExpense
         * check of that all items finds in list data per id
         * finally delete all Expense item.
         */
        IModel db = new DerbyDBModel();

        try {
            // add all ExpenseItem to DB
            for (ExpenseItem item : items) {
                db.addExpenseItem(item);
            }

            // Activates a function getDataAllExpenses
            DataAllExpenses allExpenses = db.getDataAllExpenses();

            // Teaks list data
            List<List<String>> listData = allExpenses.getListData();

            List<Integer> ids = new Vector<>();
            List<String> vector;

            // Gets the location of "id"
            int index = allExpenses.getColumnNames().indexOf("id");


            for (int i = 0; i < listData.size(); i++) {
                // Teaks all id from list
                vector = listData.get(i);

                ids.add(Integer.parseInt(vector.get(index)));
            }


            // Checks if id find in list
            assertTrue(ids.contains(items[0].getId()));
            assertTrue(ids.contains(items[1].getId()));
            assertTrue(ids.contains(items[2].getId()));
            assertTrue(ids.contains(items[3].getId()));


            // Reset the objects
            allExpenses = null;
            ids = null;
            listData = null;
            vector = null;

        } catch (CostManagerException e) {
            e.printStackTrace();
        } catch (NumberFormatException e) {
            throw new CostManagerException(e.getMessage());

        } finally {
            // delete all ExpenseItem from DB
            for (ExpenseItem item : items) {
                db.deleteExpenseItem(item);
            }

            // Reset the object
            db = null;
        }
    }

}