package GUI.CategoryMenu;

import Model.Product.Product;
import Model.RandomString;
import Model.Storage;

import java.util.ArrayList;
import java.io.*;

import static Model.Storage.*;

public class Category {

    private String categoryName;
    private ArrayList<String> allProductIDs = new ArrayList<>();
    private String parentCategoryName;
    private String attribute;
    public static ArrayList<Category> categories = new ArrayList<>();
    public static Category selected_category;

    public Category(String categoryName, String parentCategoryName, String attribute) {
        this.attribute = attribute;
        this.parentCategoryName = parentCategoryName;
        this.categoryName = categoryName;
        categories.add(this);
    }

    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }

    public String getAttribute() {
        return attribute;
    }

    public boolean hasParentCategory() {
        return (this.parentCategoryName != null);
    }

    public String getParentCategoryName() {
        return parentCategoryName;
    }

    public void setParentCategoryName(String parentCategoryName) {
        this.parentCategoryName = parentCategoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryName() {
        return categoryName;
    }
}
