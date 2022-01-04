package il.ac.hit.courses.java.costmanager.view;

import il.ac.hit.courses.java.costmanager.model.*;
import il.ac.hit.courses.java.costmanager.viewModel.IViewModel;

import org.jdatepicker.impl.*;
import org.jfree.chart.*;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.Date;
import java.util.Properties;
import java.util.Vector;


/**
 * Implements the Interface IView, View object is responsible for the UI components
 * Holds a reference to an IViewModel object
 * View object handles events listeners
 */

public class View implements IView {

    private IViewModel vm;
    private ScreenMain screenMain;
    private ScreenReport screenReport = null;
    private ScreenPieChart screenPieChart = null;
    private ScreenAddExpense screenAddExpense = null;
    private ScreenAddCategory screenAddCategory = null;


    /**
     * View constructs the user interface in a different thread using SwingUtilities
     */
    public View() {
        SwingUtilities.invokeLater(new Runnable() {
            /*
             * Runs the main screen of the application
             */
            @Override
            public void run() {
                //Turns on the main screen
                screenMain = new ScreenMain();
                screenMain.start();
            }
        });
    }


    /**
     * Connects this object to the IViewModel interface
     *
     * @param vm Holds a reference to an IViewModel object
     */
    @Override
    public void setViewModel(IViewModel vm) {
        this.vm = vm;
        //displays the date all expenses
        vm.getAllExpenses();
    }

    /**
     * Gets messages from different function windows and shows them to the user
     *
     * @param text       String represents a message to the user
     * @param nameScreen String represents a name of a window that will receive a message
     */
    @Override
    public void showMessage(String text, String nameScreen) {
        /*
         * Send Message to screen the relevant
         */

        switch (nameScreen) {
            case "addExpense":
                screenAddExpense.showMessage(text);
                break;
            case "Report":
                screenReport.showMessage(text);
                break;
            case "PieChart":
                screenPieChart.showMessage(text);
                break;

            case "addCategory":
                screenAddCategory.showMessage(text);
                break;

            default:
            case "main":
                screenMain.showMessage(text);
                break;
        }
    }

    /**
     * Shows the current Expense table in the main menu
     *
     * @param data object which holds all the expense item data
     */
    @Override
    public void showAllExpenses(DataAllExpenses data) {
        if (SwingUtilities.isEventDispatchThread()) {
            // Enter data for the table and send to a function that will show it on the screen
            screenMain.allExpenses = data;
            screenMain.showTableMain();
        } else {
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    // Enter data for the table and send to a function that will show it on the screen
                    screenMain.allExpenses = data;
                    screenMain.showTableMain();
                }
            });
        }

    }

    /**
     * Shows a report of expenses in the Report function window
     *
     * @param rb object that holds all the data between dates
     */
    @Override
    public void showReport(ReportBalance rb) {
        // Runs the showReport in class report
        if (SwingUtilities.isEventDispatchThread()) {
            screenReport.showReport(rb);
        } else {
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    // Runs the showReport in class report
                    screenReport.showReport(rb);
                }
            });
        }

    }

    /**
     * Shows a pie chart of expenses in the PieChart function window
     *
     * @param chart object that holds the sum by categories between dates
     */
    @Override
    public void showPieChart(PieChart chart) {
        if (SwingUtilities.isEventDispatchThread()) {
            // Runs the showPieChart in class pie chart
            screenPieChart.showPieChart(chart);
        } else {
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    // Runs the showPieChart in class pie chart
                    screenPieChart.showPieChart(chart);
                }
            });
        }

    }

    /**
     * Adds categories to the screen "AddExpense"
     *
     * @param categories object that holds an array of category
     */
    @Override
    public void showCategories(Category categories[]) {
        if (SwingUtilities.isEventDispatchThread()) {
            // Adds categories to the screen "AddExpense"
            for (Category category : categories)
                screenAddExpense.categoryBox.addItem(category.getCategory());
        } else {
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    // Adds categories to the screen "AddExpense"
                    for (Category category : categories)
                        screenAddExpense.categoryBox.addItem(category.getCategory());
                }
            });
        }

    }

    /**
     * Adds currencies to the screen "AddExpense"
     *
     * @param currency object that holds an array of currency strings
     */
    @Override
    public void showCurrencies(Currency currency[]) {
        if (SwingUtilities.isEventDispatchThread()) {
            // Adds currencies to the screen "AddExpense"
            for (int i = 0; i < currency.length; i++)
                screenAddExpense.currencyBox.addItem(currency[i]);
        } else {
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    // Adds currencies to the screen "AddExpense"
                    for (int i = 0; i < currency.length; i++)
                        screenAddExpense.currencyBox.addItem(currency[i]);
                }
            });
        }

    }

    /**
     * Add category new to the screen "AddExpense"
     *
     * @param category object that holds a string of category
     */
    @Override
    public void showNewCategory(String category) {
        if (SwingUtilities.isEventDispatchThread()) {
            // Add category new to the screen "AddExpense"
            screenAddExpense.categoryBox.addItem(category);
        } else {
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    // Add category new to the screen "AddExpense"
                    screenAddExpense.categoryBox.addItem(category);
                }
            });
        }
    }

    /**
     * Add to table Expense item new to the screen "screenMain" - update
     *
     * @param item ExpenseItem object holds a reference to a new expense
     */
    @Override
    public void showNewExpenseItem(ExpenseItem item) {
        if (SwingUtilities.isEventDispatchThread()) {
            // add to table Expense item new to the screen "screenMain"
            screenMain.allExpenses.addItem(item);
            screenMain.showTableMain();
        } else {
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    // add to table Expense item new to the screen "screenMain"
                    screenMain.allExpenses.addItem(item);
                    screenMain.showTableMain();
                }
            });
        }
    }


    /**
     * This class creates the main window of the application
     */
    public class ScreenMain {
        private JFrame frame;
        private JPanel panelTop;
        private JPanel panelMain;
        private JPanel panelMessage;

        private JButton btAddExpense;
        private JButton btReport;
        private JButton btPieChart;
        private JButton btQuit;

        private JScrollPane scrollPane;
        private JTable table;
        private JLabel lbMessage;
        private JTextField tfMessage;

        private DataAllExpenses allExpenses;


        /**
         * The constructor creates all the user interface components
         */
        public ScreenMain() {
            //creating the window
            frame = new JFrame("Cost Manager");

            //creating the panels
            panelMain = new JPanel();
            panelTop = new JPanel();
            panelMessage = new JPanel();

            //crating the main screen components
            btAddExpense = new JButton("Expense");
            btReport = new JButton("Report");
            btPieChart = new JButton("Pie Chart");
            btQuit = new JButton("Quit");

            //creating the message components
            lbMessage = new JLabel("Message:");
            tfMessage = new JTextField(30);
        }

        /**
         * This method adds all event(buttons, messages and more) components to the panels
         */
        public void start() {
            //setting the window layout manager
            frame.setLayout(new BorderLayout());

            //setting FlowLayout as the LayoutManager for panelTop
            panelTop.setLayout(new FlowLayout());

            //setting GridLayout 1x1 as the LayoutManager for panelBottom
            panelMain.setLayout(new GridLayout(1, 1));

            // setting font to buttons
            Font font = new Font("Dialog", 2, 15);
            btAddExpense.setFont(font);
            btReport.setFont(font);
            btPieChart.setFont(font);
            btQuit.setFont(font);
            font = null;

            //adding the components to the top panel
            panelTop.add(btAddExpense);
            panelTop.add(btReport);
            panelTop.add(btPieChart);
            panelTop.add(btQuit);

            //adding the components to the message panel
            panelMessage.add(lbMessage);
            panelMessage.add(tfMessage);

            //setting a different color for panel message
            panelMessage.setBackground(new Color(198, 208, 156));

            //adding top panel to the window
            frame.add(panelTop, BorderLayout.NORTH);
            //adding the main panel to the window
            frame.add(panelMain, BorderLayout.CENTER);
            //adding the message panel to the window
            frame.add(panelMessage, BorderLayout.SOUTH);


            /**
             * Handling Expense adding button click by adding an event listener to the button
             */
            btAddExpense.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // if null than runs the constructor else turns on screen
                    if (screenAddExpense == null) {
                        screenAddExpense = new ScreenAddExpense();
                        screenAddExpense.start();
                    } else {
                        screenAddExpense.frame.setVisible(true);
                    }
                }
            });

            /**
             * Handling report button click by adding an event listener to the button
             */
            btReport.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // if null than runs the constructor else turns on screen
                    if (screenReport == null) {
                        screenReport = new ScreenReport();
                        screenReport.start();
                    } else {
                        screenReport.frame.setVisible(true);
                    }
                }
            });

            /**
             * Handling pie chart button click by adding an event listener to the button
             */
            btPieChart.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // if null than runs the constructor else turns on screen
                    if (screenPieChart == null) {
                        screenPieChart = new ScreenPieChart();
                        screenPieChart.start();
                    } else {
                        screenPieChart.frame.setVisible(true);
                    }
                }
            });

           // Handling quit button click by adding an event listener to the button
            btQuit.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // close screen and app
                    frame.setVisible(false);
                    System.exit(0);
                }
            });

            //handling window closing
            frame.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    // close screen and app
                    frame.setVisible(false);
                    System.exit(0);
                }
            });


            // constant size
            frame.setResizable(false);
            //displaying the window
            frame.setSize(500, 500);
            frame.setLocation(500, 0);
            frame.setVisible(true);
        }


        /**
         * This method creates all the table components and displays it
         */
        public void showTableMain() {
            // remove Column "id" from table
            allExpenses.getColumnNames().remove("id");

            //Creates new table and displays data
            table = new JTable((Vector) allExpenses.getListData(), (Vector) allExpenses.getColumnNames());
            table.setFillsViewportHeight(true);

            //setting font
            table.setFont(new Font("Dialog", 0, 14));

            //Creates new scroll pane for the table
            scrollPane = new JScrollPane(table);

            //update scroll Pane panelMain
            panelMain.removeAll();
            panelMain.add(scrollPane);
            panelMain.updateUI();
        }

        /**
         * This method shows the user all system messages
         *
         * @param text String represents a message to the user
         */
        public void showMessage(String text) {
            if (SwingUtilities.isEventDispatchThread()) {
                // set text in message
                tfMessage.setText(text);
            } else {
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        // set text in message
                        tfMessage.setText(text);
                    }
                });
            }
        }
    }


    /**
     * This class creates the add expense window of the application
     */
    public class ScreenAddExpense {
        private JFrame frame;
        private JPanel panelTop;

        private JPanel panelMessage;
        private JPanel panelSum;
        private JPanel panelCurrency;

        private JPanel panelDescription;
        private JPanel panelDate;
        private JPanel panelCategory;
        private JPanel panelOk;

        private JTextField tfExpenseDescription;
        private JTextField tfExpenseSum;

        private JButton btAddCategory;
        private JButton btOK;

        private JLabel lbExpenseDescription;
        private JLabel lbExpenseSum;
        private JLabel lbExpenseCurrency;
        private JLabel lbExpenseCategory;
        private JLabel lbExpenseDate;
        private JLabel lbMessage;

        private JTextField tfMessage;
        private JComboBox categoryBox;
        private JComboBox currencyBox;

        private Properties properties;
        private UtilDateModel dateModel;
        private JDatePanelImpl datePanel;
        private JDatePickerImpl datePicker;

        /**
         * The constructor creates all the user interface components
         */
        public ScreenAddExpense() {
            //creating the window
            frame = new JFrame("Add Expense");

            //creating the panels
            panelTop = new JPanel();
            panelMessage = new JPanel();

            panelSum = new JPanel();
            panelCurrency = new JPanel();
            panelDescription = new JPanel();
            panelCategory = new JPanel();
            panelDate = new JPanel();
            panelOk = new JPanel();

            //crating the screen Expense components text
            tfExpenseDescription = new JTextField(12);
            tfExpenseSum = new JTextField(12);

            //creating the add category
            btAddCategory = new JButton("+");
            // ComboBox for categories and currencies
            categoryBox = new JComboBox(new String[0]);
            currencyBox = new JComboBox(new Currency[0]);

            //creating the label
            lbExpenseDescription = new JLabel("Description: ");
            lbExpenseSum = new JLabel("Sum:           ");
            lbExpenseCurrency = new JLabel("Currency:    ");
            lbExpenseCategory = new JLabel("Category:    ");
            lbExpenseDate = new JLabel("Date:          ");
            //creating the button
            btOK = new JButton("Add Expense");

            //setting the date in the main components
            properties = new Properties();
            properties.put("text.day", "Day");
            properties.put("text.month", "Month");
            properties.put("text.year", "Year");
            dateModel = new UtilDateModel();
            datePanel = new JDatePanelImpl(dateModel, properties);
            datePicker = new JDatePickerImpl(datePanel, new DateComponentFormatter());

            //creating the message components
            lbMessage = new JLabel("Message:");
            tfMessage = new JTextField(30);
        }

        /**
         * This method adds all event(buttons, messages and more) components to the panels
         */
        public void start() {
            //Adds the date picker components
            dateModel.setDate(dateModel.getYear(), dateModel.getMonth(), dateModel.getDay());
            dateModel.setSelected(true);

            //Displays categories and currencies to the user
            vm.getCategories();
            vm.getCurrencies();

            //setting the window layout manager
            frame.setLayout(new BorderLayout());

            //setting GridLayout as the LayoutManager for panelTop
            panelTop.setLayout(new GridLayout(6, 1));

            // font new
            Font font = new Font("BOLD", 5, 20);

            //setting FlowLayout as the LayoutManager for panelSum
            panelSum.setLayout(new FlowLayout(5));
            // setting font
            lbExpenseSum.setFont(font);
            tfExpenseSum.setFont(font);
            // adding the panelSum component to the top panel
            panelSum.add(lbExpenseSum);
            panelSum.add(tfExpenseSum);
            panelTop.add(panelSum);


            //setting FlowLayout as the LayoutManager for panelDescription
            panelDescription.setLayout(new FlowLayout(5));
            // setting font
            lbExpenseDescription.setFont(font);
            tfExpenseDescription.setFont(font);
            //adding the panel description component to the top panel
            panelDescription.add(lbExpenseDescription);
            panelDescription.add(tfExpenseDescription);
            panelTop.add(panelDescription);


            //setting FlowLayout as the LayoutManager for panelCurrency
            panelCurrency.setLayout(new FlowLayout(5));
            // setting font
            lbExpenseCurrency.setFont(font);
            currencyBox.setFont(font);
            // adding the panelCurrency component to the top panel
            panelCurrency.add(lbExpenseCurrency);
            panelCurrency.add(currencyBox);
            panelTop.add(panelCurrency);


            //setting FlowLayout as the LayoutManager for panelCategory
            panelCategory.setLayout(new FlowLayout(5));
            // setting font
            lbExpenseCategory.setFont(font);
            categoryBox.setFont(font);
            btAddCategory.setFont(font);
            // adding the panelCategory component and button "add category" to the top panel
            panelCategory.add(lbExpenseCategory);
            panelCategory.add(categoryBox);
            panelCategory.add(btAddCategory);
            panelTop.add(panelCategory);


            //setting FlowLayout as the LayoutManager for panelDate
            panelDate.setLayout(new FlowLayout(5));
            // setting font
            lbExpenseDate.setFont(font);
            datePicker.setFont(font);
            datePicker.setFont(new Font("BOLD", 5, 30));
            //adding the date component to the top panel
            panelDate.add(lbExpenseDate);
            panelDate.add(datePicker);
            panelTop.add(panelDate);


            // setting font
            btOK.setFont(font);
            // adding the ok button to the top panel
            panelOk.add(btOK);
            panelTop.add(panelOk);


            //adding the components to the message panel
            panelMessage.add(lbMessage);
            panelMessage.add(tfMessage);
            //setting a different color for panel message
            panelMessage.setBackground(new Color(198, 208, 156));


            //adding top panel to the window
            frame.add(panelTop, BorderLayout.CENTER);

            //adding the message panel to the window
            frame.add(panelMessage, BorderLayout.SOUTH);


            /*
             * Handling +(add new category) button click by adding an event listener to the button
             */
            btAddCategory.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {

                    // if null than runs the constructor else turns on screen
                    if (screenAddCategory == null) {
                        screenAddCategory = new ScreenAddCategory();
                        screenAddCategory.start();
                    } else {
                        screenAddCategory.frame.setVisible(true);
                    }

                }
            });


            /**
             * Handling ok(adding new expense) button click by adding an event listener to the button
             */
            btOK.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        // Takes the data from the UI
                        String description = tfExpenseDescription.getText();
                        double sum = Double.parseDouble(tfExpenseSum.getText());
                        Currency currency = (Currency) currencyBox.getSelectedItem();
                        Category category = new Category((String) categoryBox.getSelectedItem());
                        Date date = new Date(datePicker.getModel().getYear() - 1900,
                                datePicker.getModel().getMonth(),
                                datePicker.getModel().getDay());

                        // Checks if the number is positive
                        if (sum > 0.0) {
                            ExpenseItem item = new ExpenseItem(description, sum, currency, category, date);
                            vm.addExpenseItem(item);
                        } else {
                            showMessage("Only expenses allowed, no negative number");
                        }

                    } catch (NumberFormatException ex) {
                        showMessage(ex.getMessage() + " illegal input");
                    }

                }
            });

            //handling window closing
            frame.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    //close frame
                    frame.setVisible(false);

                    // restart - delete text field
                    tfExpenseDescription.setText("");
                    tfExpenseSum.setText("");
                    tfMessage.setText("");
                    categoryBox.setSelectedIndex(0);
                    currencyBox.setSelectedIndex(0);
                }
            });

            //displaying the window
            frame.setSize(500, 500);
            frame.setResizable(false);
            frame.setVisible(true);
        }


        /**
         * This method shows the user all system messages
         *
         * @param text String represents a message to the user
         */
        public void showMessage(String text) {
            if (SwingUtilities.isEventDispatchThread()) {
                // set text in message
                tfMessage.setText(text);
            } else {
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        // set text in message
                        tfMessage.setText(text);
                    }
                });
            }
        }

    }


    /**
     * This class creates the add category window of the application
     */
    public class ScreenAddCategory {
        private JFrame frame;
        private JPanel panelTop;
        private JPanel panelMessage;

        private JLabel lbMessage;
        private JLabel lbNewCategory;

        private JTextField tfMessage;
        private JTextField tfNewCategory;
        private JButton btOK;


        /**
         * The constructor creates all the user interface components
         */
        public ScreenAddCategory() {
            //creating the window
            frame = new JFrame("Add Category");

            //creating the panels
            panelTop = new JPanel();
            panelMessage = new JPanel();

            //creating the message components
            lbMessage = new JLabel("Message:");
            tfMessage = new JTextField(20);

            //creating the add category screen components
            lbNewCategory = new JLabel("New Category:");
            tfNewCategory = new JTextField(15);
            btOK = new JButton("OK");
        }

        public void start() {
            //setting the window layout manager
            frame.setLayout(new BorderLayout());

            //adding the components to the message panel
            panelMessage.add(lbMessage);
            panelMessage.add(tfMessage);

            //setting a different color for panel message
            panelMessage.setBackground(new Color(198, 208, 156));

            //adding the components to the top panel
            panelTop.add(lbNewCategory);
            panelTop.add(tfNewCategory);
            panelTop.add(btOK);

            //adding top panel to the window
            frame.add(panelTop, BorderLayout.CENTER);
            frame.add(panelMessage, BorderLayout.SOUTH);

            //handling window closing
            frame.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    // close screen
                    frame.setVisible(false);
                    // delete to text in exit
                    tfNewCategory.setText("");
                    tfMessage.setText("");
                }
            });

            /**
             * Handling ok(adding new category) button click by adding an event listener to the button
             */
            btOK.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        // getting text from UI
                        String newCategory = tfNewCategory.getText();

                        // if not - (empty or contains only white space) add category
                        if (newCategory.isBlank())
                            showMessage("is no string legal");
                        else {
                            vm.addCategory(newCategory);
                        }

                    } catch (NumberFormatException ex) {
                        showMessage(ex.getMessage());
                    }
                }
            });

            //displaying the window
            frame.setSize(400, 100);
            frame.setResizable(false);
            frame.setVisible(true);
        }

        /**
         * This method shows the user all system messages
         *
         * @param text String represents a message to the user
         */
        public void showMessage(String text) {
            if (SwingUtilities.isEventDispatchThread()) {
                // set text in message
                tfMessage.setText(text);
            } else {
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        // set text in message
                        tfMessage.setText(text);
                    }
                });
            }
        }
    }


    /**
     * This class creates the report window of the application
     */
    public class ScreenReport {
        private JFrame frame;
        private JPanel panelTop;
        private JPanel panelMain;
        private JPanel panelMessage;

        private JLabel lbFrom;
        private JLabel lbTo;
        private JLabel lbMessage;

        private JButton btOK;
        private JTextField tfMessage;
        private JScrollPane scrollPane;
        private JTable table = null;

        private Properties propertiesTo;
        private Properties propertiesFrom;
        private UtilDateModel modelTo;
        private UtilDateModel modelFrom;

        private JDatePanelImpl datePanelTo;
        private JDatePanelImpl datePanelFrom;
        private JDatePickerImpl datePickerTo;
        private JDatePickerImpl datePickerFrom;


        /**
         * The constructor creates all the user interface components
         */
        public ScreenReport() {
            //creating the window
            frame = new JFrame("Report");

            //creating the panels
            panelMain = new JPanel();
            panelTop = new JPanel();
            panelMessage = new JPanel();

            //crating the screen report components
            btOK = new JButton("OK");

            //creating the message components
            lbMessage = new JLabel("Message:");
            tfMessage = new JTextField(30);

            //setting the time range in the main components
            lbFrom = new JLabel("From:");
            lbTo = new JLabel("To:");

            //Sets the from date picker
            propertiesTo = new Properties();
            propertiesTo.put("text.day", "Day");
            propertiesTo.put("text.month", "Month");
            propertiesTo.put("text.year", "Year");

            //Sets the To date picker
            propertiesFrom = new Properties();
            propertiesFrom.put("text.day", "Day");
            propertiesFrom.put("text.month", "Month");
            propertiesFrom.put("text.year", "Year");

            //Creates the date picker
            modelTo = new UtilDateModel();
            modelFrom = new UtilDateModel();
            datePanelTo = new JDatePanelImpl(modelTo, propertiesTo);
            datePanelFrom = new JDatePanelImpl(modelFrom, propertiesFrom);
            datePickerTo = new JDatePickerImpl(datePanelTo, new DateComponentFormatter());
            datePickerFrom = new JDatePickerImpl(datePanelFrom, new DateComponentFormatter());
        }

        /**
         * This method adds all event(buttons, messages and more) components to the panels
         */
        public void start() {
            //adding the date picker components and sets default to today's date
            modelTo.setDate(modelFrom.getYear(), modelFrom.getMonth(), modelFrom.getDay());
            modelTo.setSelected(true);
            modelFrom.setDate(modelFrom.getYear(), modelFrom.getMonth(), modelFrom.getDay());
            modelFrom.setSelected(true);

            //setting GridLayout 1x1 as the LayoutManager for panelBottom panelMain
            panelMain.setLayout(new GridLayout(1, 1));

            //setting the window layout manager
            frame.setLayout(new BorderLayout());

            //adding the components to the top panel
            panelTop.add(lbFrom);
            panelTop.add(datePickerTo);
            panelTop.add(lbTo);
            panelTop.add(datePickerFrom);
            panelTop.add(btOK);

            //adding the components to the message panel
            panelMessage.add(lbMessage);
            panelMessage.add(tfMessage);

            //setting a different color for panel message
            panelMessage.setBackground(new Color(198, 208, 156));

            //adding top panel to the window
            frame.add(panelTop, BorderLayout.NORTH);

            //adding the main panel to the window
            frame.add(panelMain, BorderLayout.CENTER);

            //adding the message panel to the window
            frame.add(panelMessage, BorderLayout.SOUTH);

            //handling the ok button
            btOK.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        // get dates from UI
                        Date start = new Date(datePickerTo.getModel().getYear() - 1900,
                                datePickerTo.getModel().getMonth(),
                                datePickerTo.getModel().getDay());

                        Date end = new Date(datePickerFrom.getModel().getYear() - 1900,
                                datePickerFrom.getModel().getMonth(),
                                datePickerFrom.getModel().getDay());

                        // get report
                        vm.getReport(start, end);

                    } catch (NumberFormatException ex) {
                        showMessage(ex.getMessage());
                    }
                }
            });


            //handling window closing
            frame.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    // close frame
                    frame.setVisible(false);
                    // delete message
                    tfMessage.setText("");

                    // delete data on frame
                    if (table != null)
                        panelMain.remove(scrollPane);
                }
            });

            //displaying the window
            frame.setSize(550, 500);
            frame.setResizable(false);
            frame.setLocation(0, 320);
            frame.setVisible(true);
        }


        /**
         * Shows a report of expenses in the Report function window
         *
         * @param rb rb ReportBalance object holds two dimensional Strings
         */
        public void showReport(ReportBalance rb) {
            // if table not null than remove scrollPane
            if (table != null) {
                panelMain.remove(scrollPane);
            }

            // remove Column "id" from table
            rb.getColumnNames().remove("id");
            // put data in table
            table = new JTable((Vector) rb.getListReport(), (Vector<String>) rb.getColumnNames());

            // add color and font to table
            table.setBackground(Color.WHITE);
            table.setForeground(Color.BLUE);
            Font font = new Font("Dialog", 0, 14);
            table.setFont(font);

            // put table in scrollPane
            scrollPane = new JScrollPane(table);
            table.setFillsViewportHeight(true);

            // adding the components to the bottom main
            panelMain.add(scrollPane);

            // update panel main
            panelMain.updateUI();

        }


        /**
         * This method shows the user all system messages
         *
         * @param text String represents a message to the user
         */
        public void showMessage(String text) {
            if (SwingUtilities.isEventDispatchThread()) {
                // set text in message
                tfMessage.setText(text);
            } else {
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        // set text in message
                        tfMessage.setText(text);
                    }
                });
            }
        }

    }


    /**
     * This class creates the pie chart window of the application
     */
    public class ScreenPieChart {
        private JFrame frame;

        private JPanel panelTop;
        private JPanel panelMain;
        private JPanel panelMessage;

        private JLabel lbFrom;
        private JLabel lbTo;
        private JButton btOK;
        private JScrollPane scrollPane = null;

        private JLabel lbMessage;
        private JTextField tfMessage;

        private Properties propertiesTo;
        private Properties propertiesFrom;
        private UtilDateModel modelTo;
        private UtilDateModel modelFrom;
        private JDatePanelImpl datePanelTo;
        private JDatePanelImpl datePanelFrom;

        private JDatePickerImpl datePickerTo;
        private JDatePickerImpl datePickerFrom;
        private PieChart dataPieChart;


        /**
         * The constructor creates all the user interface components
         */
        public ScreenPieChart() {
            //creating the window
            frame = new JFrame("Pie Chart");

            //creating the panels
            panelMain = new JPanel();
            panelTop = new JPanel();
            panelMessage = new JPanel();

            //crating the pie chart screen components
            btOK = new JButton("OK");

            //creating the message components
            lbMessage = new JLabel("Message:");
            tfMessage = new JTextField(35);

            //setting the time range in the main components
            lbFrom = new JLabel("From:");
            lbTo = new JLabel("To:");

            // date start
            propertiesTo = new Properties();
            propertiesTo.put("text.day", "Day");
            propertiesTo.put("text.month", "Month");
            propertiesTo.put("text.year", "Year");

            // date end
            propertiesFrom = new Properties();
            propertiesFrom.put("text.day", "Day");
            propertiesFrom.put("text.month", "Month");
            propertiesFrom.put("text.year", "Year");

            // Creates the date picker
            modelTo = new UtilDateModel();
            modelFrom = new UtilDateModel();
            datePanelTo = new JDatePanelImpl(modelTo, propertiesTo);
            datePanelFrom = new JDatePanelImpl(modelFrom, propertiesFrom);
            datePickerTo = new JDatePickerImpl(datePanelTo, new DateComponentFormatter());
            datePickerFrom = new JDatePickerImpl(datePanelFrom, new DateComponentFormatter());
        }

        /**
         * This method adds all event(buttons, messages and more) components to the panels
         */
        public void start() {
            //adding the date picker components and sets default to today's date
            modelTo.setDate(modelFrom.getYear(), modelFrom.getMonth(), modelFrom.getDay());
            modelTo.setSelected(true);
            modelFrom.setDate(modelFrom.getYear(), modelFrom.getMonth(), modelFrom.getDay());
            modelFrom.setSelected(true);

            //setting GridLayout 1x1 as the LayoutManager for panelMain
            panelMain.setLayout(new GridLayout(1, 1));

            //setting the window layout manager
            frame.setLayout(new BorderLayout());

            //adding the components to the top panel
            panelTop.add(lbFrom);
            panelTop.add(datePickerTo);
            panelTop.add(lbTo);
            panelTop.add(datePickerFrom);
            panelTop.add(btOK);

            //adding the components to the message panel
            panelMessage.add(lbMessage);
            panelMessage.add(tfMessage);

            //setting a different color for panel message
            panelMessage.setBackground(new Color(198, 208, 156));

            //adding top panel to the window
            frame.add(panelTop, BorderLayout.NORTH);

            // adding the main panel to the window
            frame.add(panelMain, BorderLayout.CENTER);

            //adding the message panel to the window
            frame.add(panelMessage, BorderLayout.SOUTH);

            //handling window closing
            frame.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    // close screen
                    frame.setVisible(false);

                    // delete message
                    tfMessage.setText("");

                    // if not null remove scrollPane from panelMain
                    if (scrollPane != null) {
                        panelMain.remove(scrollPane);
                        scrollPane = null;
                    }
                }
            });


            /**
             * Handling ok button click by adding an event listener to the button
             */
            btOK.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        // get dates from UI
                        Date start = new Date(datePickerTo.getModel().getYear() - 1900,
                                datePickerTo.getModel().getMonth(),
                                datePickerTo.getModel().getDay());

                        Date end = new Date(datePickerFrom.getModel().getYear() - 1900,
                                datePickerFrom.getModel().getMonth(),
                                datePickerFrom.getModel().getDay());

                        // get pie chart
                        vm.getPieChart(start, end);

                    } catch (NumberFormatException ex) {
                        showMessage(ex.getMessage());
                    }
                }
            });

            //displaying the window
            frame.setSize(725, 535);
            frame.setResizable(false);
            frame.setLocation(400, 250);
            frame.setVisible(true);
        }


        /**
         * Builds the pie chart
         *
         * @return JPanel that contains the pie chart graphics
         */
        public JPanel createPieChart() {
            //create dataset for pie chart
            DefaultPieDataset dataset = new DefaultPieDataset();
            for (int i = 0; i < dataPieChart.getListCategory().size(); i++) {
                dataset.setValue(dataPieChart.getListCategory().get(i), dataPieChart.getListSum().get(i));
            }

            // create pie chart 3D
            JFreeChart chart = ChartFactory.createPieChart3D(
                    "Expenses by Categories", dataset, true, true, false);

            // create plot
            PiePlot plot = (PiePlot) chart.getPlot();
            plot.setSectionOutlinesVisible(false);
            plot.setNoDataMessage("No data available");

            // update font for pie chart
            Font font = new Font("BOLD", 0, 20);
            plot.setLabelFont(font);
            plot.setNoDataMessageFont(font);
            font = null;

            return new ChartPanel(chart);
        }


        /**
         * Shows a pie chart of expenses in the Pie Chart function window
         *
         * @param pc object that holds the sum by categories between dates
         */
        public void showPieChart(PieChart pc) {
            this.dataPieChart = pc;

            // if null remove scrollPane
            if (scrollPane != null) {
                panelMain.remove(scrollPane);
            }

            // set pie chart in scrollPane - activates construction function
            scrollPane = new JScrollPane(createPieChart());
            //add scrollPane to panel
            panelMain.add(scrollPane);

            // panel update
            panelMain.updateUI();
        }

        /**
         * This method shows the user all system messages
         *
         * @param text String represents a message to the user
         */
        public void showMessage(String text) {
            if (SwingUtilities.isEventDispatchThread()) {
                // set text in message
                screenPieChart.tfMessage.setText(text);
            } else {
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        // set text in message
                        screenPieChart.tfMessage.setText(text);
                    }
                });
            }
        }

    }

}
