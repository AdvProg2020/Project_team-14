package Model.Category;

import Model.Account.Salesman;
import Model.Product.Product;
import Model.Storage;
import org.junit.Assert;
import org.junit.Test;

public class CategoryTest {

    @Test
    public void hasParentCategory() {
        Category category = new Category("attribute", null, "name");
        Assert.assertFalse(category.hasParentCategory());
        Assert.assertNull(Storage.getCategoryByName("sssssssss"));
    }

    @Test
    public void isThereCategoryWithName1() {
        // Category category = new Category("attribute", null, "name");
        Assert.assertFalse(Storage.isThereCategoryWithName("Manuel"));
    }

    @Test
    public void isThereCategoryWithName2() {
        Category category = new Category("name", null, "attribute");
        Assert.assertTrue(Storage.isThereCategoryWithName("name"));
    }


    @Test
    public void addProduct() {
        Salesman salesman1 = new Salesman("salesmanUser1", "password", "firstname", "secondName",
                "h.hafezi2000@gmail.com", "09333805288", "SALESMAN", "company", 0);
        Category category = new Category("attribute", null, "nm");
        Product product = new Product("name", salesman1.getUsername(), "brand", "description", 10, 10);
        Product product2 = new Product("name2", salesman1.getUsername(), "brand2", "description2", 10, 10);
        Product product3 = new Product("name2", salesman1.getUsername(), "brand2", "description2", 10, 10);
        category.addProductToCategory(product.getProductID());
        category.addProductToCategory(product2.getProductID());
        category.addProductToCategory(product3.getProductID());
        Assert.assertNotNull(Storage.getCategoryByName(category.getCategoryName()));
        System.out.println(category.containsProduct(product.getProductID()));
        Assert.assertTrue(category.containsProduct(product.getProductID()));
        Assert.assertTrue(category.containsProduct(product2.getProductID()));
        Assert.assertTrue(category.containsProduct(product3.getProductID()));
    }


    @Test
    public void toStringTest() {
        Salesman salesman1 = new Salesman("salesmanUser1", "password", "firstname", "secondName",
                "h.hafezi2000@gmail.com", "09333805288", "SALESMAN", "company", 0);
        Category category = new Category("attribute", null, "name");
        Product product = new Product("name", salesman1.getUsername(), "brand", "description", 10, 10);
        Product product2 = new Product("name2", salesman1.getUsername(), "brand2", "description2", 10, 10);
        Product product3 = new Product("name2", salesman1.getUsername(), "brand2", "description2", 10, 10);
        category.addProductToCategory(product.getProductID());
        category.addProductToCategory(product2.getProductID());
        category.addProductToCategory(product3.getProductID());
        String result = "Category Name: attribute" + "\n" + "Category Attribute: name" + "\n";
        result += "The category doesn't have parent category" + "\n" + "SubCategories: " + "\n" + "Products: " + "\n";
        result += "name" + "\n" + "name2" + "\n" + "name2" + "\n";
        Assert.assertEquals(result, category.toString());
    }

    @Test
    public void toStringTest2() {
        Salesman salesman1 = new Salesman("salesmanUser1", "password", "firstname", "secondName",
                "h.hafezi2000@gmail.com", "09333805288", "SALESMAN", "company", 0);
        Category category = new Category("n", null, "attribute");
        Category category2 = new Category("n2", "n", "attribute");
        Product product = new Product("name", salesman1.getUsername(), "brand", "description", 10, 10);
        category.addProductToCategory(product.getProductID());
        Assert.assertTrue(category2.hasParentCategory());
        String result = "Category Name: n" + "\n" + "Category Attribute: attribute" + "\n";
        result += "The category doesn't have parent category" + "\n" + "SubCategories: " + "\n" + "n2" + "\n" + "Products: " + "\n";
        result += "name" + "\n";
        Assert.assertEquals(result, category.toString());
        String result2 = "Category Name: n2" + "\n" + "Category Attribute: attribute" + "\n";
        result2 += "Parent Category: n" + "\n" + "SubCategories: " + "\n"  + "Products: " + "\n";
        Assert.assertEquals(result2, category2.toString());

    }

}
