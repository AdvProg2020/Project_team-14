package Model.Category;

import Model.Product.Product;
import Model.RandomString;
import Model.Storage;

import java.util.ArrayList;
import java.io.*;

import static Model.Storage.*;

public class Category implements Serializable {

    //note that the name of each category must be unique so we can get the name
    //we should check to ensure that there's no previous category with this name
    //OK
    //let's delete sub category and all products because
    //we have parent category and for products we can have category
    private String categoryName;
    private ArrayList<String> allProductIDs = new ArrayList<>();
    private String parentCategoryName;
    private String attribute;

    public Category(String categoryName, String parentCategoryName, String attribute) {
        this.attribute = attribute;
        this.parentCategoryName = parentCategoryName;
        this.categoryName = categoryName;
        Storage.getAllCategories().add(this);
    }

    public ArrayList<Category> getSubCategories() {
        ArrayList<Category> categories = new ArrayList<>();
        for (Category category : Storage.getAllCategories()) {
            if (category.getParentCategoryName() == null) {
                continue;
            } else if (category.getParentCategoryName().equals(this.getCategoryName())) {
                categories.add(category);
            }
        }
        return categories;
    }

    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }

    public String getAttribute() {
        return attribute;
    }

    public int getSeenCount() {
        int seenCount = 0;
        for (String productID : allProductIDs) {
            seenCount += Storage.getProductById(productID).getSeenCount();
        }
        return seenCount;
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

    public ArrayList<String> getAllProductIDs() {
        return allProductIDs;
    }

    public void addProductToCategory(String productID) {
        this.allProductIDs.add(productID);
        Product.getProductWithID(productID).setCategoryName(this.categoryName);
    }

    public void deleteProductFromCategory(String productID) {
        this.allProductIDs.remove(productID);
        Product.getProductWithID(productID).setCategoryName("");
    }

    public boolean containsProduct(String productID) {
        return allProductIDs.contains(productID);
    }

    private StringBuilder toStringProducts() {
        StringBuilder result = new StringBuilder();
        result.append("Products: " + "\n");
        for (String productID : allProductIDs) {
            result.append(Product.getNameByID(productID)).append("\n");
        }
        return result;
    }

    private StringBuilder toStringSubCategories() {
        StringBuilder result = new StringBuilder();
        result.append("SubCategories: ").append("\n");
        for (Category category : this.getSubCategories()) {
            result.append(category.getCategoryName()).append("\n");
        }
        return result;
    }

    private StringBuilder toStringParentCategory() {
        StringBuilder result = new StringBuilder();
        if (this.parentCategoryName == null) {
            result.append("The category doesn't have parent category" + "\n");
        } else {
            result.append("Parent Category: ").append(parentCategoryName).append("\n");
        }
        return result;
    }

    public String toStringForBoss() {
        return "Category Name: " + this.categoryName + " " +
                "Category Attribute: " + this.attribute;
    }

    public String toString() {
        return "Category Name: " + this.categoryName + "\n" +
                "Category Attribute: " + this.attribute + "\n" +
                toStringParentCategory() + toStringSubCategories() + toStringProducts();
    }

}
