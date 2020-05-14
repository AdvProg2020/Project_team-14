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
    private String categoryName;
    private ArrayList<String> allProductIDs = new ArrayList<>();
    private ArrayList<String> subCategoryNames = new ArrayList<>();
    private String parentCategoryName;
    private String attribute;

    public Category(String categoryName, String parentCategoryName, String attribute) {
        this.attribute = attribute;
        this.parentCategoryName = parentCategoryName;
        this.categoryName = categoryName;
        Storage.getAllCategories().add(this);
    }

    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }

    public boolean hasParentCategory() {
        return (this.parentCategoryName != null);
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
        Product.getProductWithID(productID).setCategoryName(null);
    }

    public void addSubCategory(String categoryName) {
        this.subCategoryNames.add(categoryName);
    }

    public boolean containsProduct(String productID) {
        return allProductIDs.contains(productID);
    }

    private StringBuilder toStringSubCategory() {
        StringBuilder result = new StringBuilder();
        result.append("SubCategories: " + "\n");
        for (String subCategory : subCategoryNames) {
            result.append(subCategory).append("\n");
        }
        return result;
    }

    private StringBuilder toStringProducts() {
        StringBuilder result = new StringBuilder();
        result.append("Products: " + "\n");
        for (String productID : allProductIDs) {
            result.append(Product.getNameByID(productID)).append("\n");
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

    public String toString() {
        return "Category Name: " + this.categoryName + "\n" +
                "Category Attribute: " + this.attribute + "\n" +
                toStringParentCategory() +
                toStringSubCategory() +
                toStringProducts();
    }

}
