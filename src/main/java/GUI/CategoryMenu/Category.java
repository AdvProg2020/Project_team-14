package GUI.CategoryMenu;

import Model.Product.Product;
import Model.RandomString;
import Model.Storage;

import java.util.ArrayList;
import java.io.*;

import static Model.Storage.*;

public class Category {

    public String categoryName;
    public String parentCategory;
    public String attribute;

    public Category(String categoryName, String parentCategory, String attribute) {
        this.attribute = attribute;
        this.parentCategory= parentCategory;
        this.categoryName = categoryName;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getParentCategory() {
        return parentCategory;
    }

    public void setParentCategory(String parentCategory) {
        this.parentCategory = parentCategory;
    }

    public String getAttribute() {
        return attribute;
    }

    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }
}
