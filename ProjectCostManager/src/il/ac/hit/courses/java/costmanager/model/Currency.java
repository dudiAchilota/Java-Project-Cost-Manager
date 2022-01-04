package il.ac.hit.courses.java.costmanager.model;

/**
 * This enum represents coin currencies
 */
public enum Currency {
    ILS, USD, EURO, GBP;


    /**
     * Holds an array of all the types of currencies for flexible change
     *
     * @return an array with all the currencies
     */
    public Currency[] getArrayCurrencies() {
        Currency arr[] = {Currency.ILS, Currency.USD, Currency.EURO, Currency.GBP};
        return arr;
    }
}
