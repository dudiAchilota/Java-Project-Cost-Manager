package il.ac.hit.courses.java.costmanager.model;

/**
 * This class represents a category for an expense
 */
public class Category {

    private String category;

    public Category(String category) {
        setCategory(category);
    }

    public String getCategory() {
        return this.category;
    }

    public void setCategory(String category) {
        this.category = category;
    }


    @Override
    public String toString() {
        return this.category;
    }

}
