package Model.Product;

import Model.Account.Customer;
import Model.Account.Salesman;
import Model.Confirmation;
import org.junit.Assert;
import org.junit.Test;
import java.util.ArrayList;

public class ProductTest {


    Salesman salesman1 = new Salesman("salesmanUser1", "password", "firstname", "secondName",
            "h.hafezi2000@gmail.com", "09333805288", "SALESMAN", "company", 0);

    Salesman salesman2 = new Salesman("salesmanUser2", "password", "firstname", "secondName",
            "h.hafezi2000@gmail.com", "09333805288", "SALESMAN", "company", 0);

    Salesman salesman3 = new Salesman("salesmanUser3", "password", "firstname", "secondName",
            "h.hafezi2000@gmail.com", "09333805288", "SALESMAN", "company", 0);


    Customer customer = new Customer("customerUser", "password", "firstname", "secondName",
            "h.hafezi2000@yahoo.com", "09333805288", "CUSTOMER", 1000);

    Product product = new Product("name", salesman1.getUsername(), "brand", "description", 10, 10);
    Product product2 = new Product("name2", salesman1.getUsername(), "brand2", "description2", 10, 10);
    Product product3 = new Product("name2", salesman1.getUsername(), "brand2", "description2", 10, 10);

    @Test
    public void seenCount() {
        product.increaseSeenCount();
        product.increaseSeenCount();
        product.increaseSeenCount();
        Assert.assertEquals(product.getSeenCount(), 3);
        Assert.assertNull(product.getCategoryName());
    }

    @Test
    public void brand() {
        Assert.assertEquals(product.getBrand(), "brand");
        product.setBrand("Manuel");
        Assert.assertEquals(product.getBrand(), "Manuel");
    }

    /*@Test
    public void comments() {
        Assert.assertFalse(product.isThereComment());
        Comment comment = new Comment("text", customer.getUsername(), product.getProductID());
        Comment comment2 = new Comment("text", customer.getUsername(), product.getProductID());
        Comment comment3 = new Comment("text", customer.getUsername(), product.getProductID());
        comment.setConfirmationState("ACCEPTED");
        comment2.setConfirmationState("ACCEPTED");
        comment3.setConfirmationState("ACCEPTED");
        Assert.assertTrue(product.isThereComment());
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add(comment.getCommentID());
        arrayList.add(comment2.getCommentID());
        arrayList.add(comment3.getCommentID());
        Assert.assertEquals(arrayList, product.getComments());
    }*/

    @Test
    public void point() {
        Assert.assertFalse(product.isThereAnyPoint());
        Point point = new Point(customer.getUsername(), product.getProductID(), 4);
        Point point2 = new Point(customer.getUsername(), product.getProductID(), 3);
        Point point3 = new Point(customer.getUsername(), product.getProductID(), 4);
        Assert.assertTrue(product.isThereAnyPoint());
        Assert.assertEquals(product.getNumberOfPeopleVoted(), 3);
        Assert.assertEquals(Double.toString(product.getAveragePoint()), Double.toString((double) (11) / 3));
    }

    @Test
    public void getNameByID() {
        Assert.assertEquals(Product.getNameByID(product.getProductID()), "name");
        Assert.assertEquals(Product.getProductWithID(product.getProductID()), product);
        Assert.assertEquals(Product.getProductWithID(product2.getProductID()), product2);
        Assert.assertNull(Product.getProductWithID("fake ID"));
        Assert.assertFalse(Product.isThereProductWithID("fake ID"));

    }

    @Test
    public void addSalesman() {
        product.addSalesman(salesman2.getUsername(), 2, 1000);
        product.addSalesman(salesman3.getUsername(), 5, 100000);
        product.setRemainderForSalesman(0, salesman2.getUsername());
        product.setPriceForSalesman(100, salesman3.getUsername());
        //Assert.assertFalse(product.isAvailableBySalesmanWithUsername(salesman2.getUsername()));
        Assert.assertEquals(product.getPriceBySalesmanID(salesman3.getUsername()), 100);
        Assert.assertFalse(product.doesSalesmanSellProductWithUsername(salesman1.getUsername()));
        product.setConfirmationState(salesman1.getUsername(), Confirmation.ACCEPTED);
        product.setConfirmationState(salesman2.getUsername(), Confirmation.DENIED);
        product.setConfirmationState(salesman3.getUsername(), Confirmation.ACCEPTED);
        product.deleteForSalesman(salesman3.getUsername());
        Assert.assertFalse(product.doesSalesmanSellProductWithUsername(salesman3.getUsername()));
        Assert.assertFalse(product.doesSalesmanSellProductWithUsername(salesman2.getUsername()));
        Assert.assertTrue(product.doesSalesmanSellProductWithUsername(salesman1.getUsername()));
        Assert.assertTrue(product.createID().startsWith("Product---"));
    }

    @Test
    public void toStringForCustomerView() {
        product.addSalesman(salesman2.getUsername(), 10, 100);
        product.addSalesman(salesman3.getUsername(), 10, 100);
        product.deleteForSalesman(salesman3.getUsername());
        product.setConfirmationState(salesman2.getUsername(), Confirmation.ACCEPTED);
        product.setConfirmationState(salesman1.getUsername(), Confirmation.ACCEPTED);
        String result = "Name: name" + "\n";
        result += "Brand: brand" + "\n";
        result += "Description: description" + "\n";
        result += "Sellers: " + "\n";
        result += "Salesman: salesmanUser1";
        result += " Price: 10" + "\n";
        result += "Salesman: salesmanUser2";
        result += " Price: 100" + "\n";
        Assert.assertEquals(product.toStringForCustomerView(), result);
    }

    @Test
    public void toStringForSalesmanView() {
        product.addSalesman(salesman2.getUsername(), 10, 100);
        product.addSalesman(salesman3.getUsername(), 10, 100);
        product.deleteForSalesman(salesman3.getUsername());
        product.setConfirmationState(salesman2.getUsername(), Confirmation.ACCEPTED);
        product.setConfirmationState(salesman1.getUsername(), Confirmation.ACCEPTED);
        String result = "Name: name" + "\n";
        result += "Brand: brand" + "\n";
        result += "Description: description" + "\n";
        result += "Sellers: " + "\n";
        result += "Salesman: salesmanUser1";
        result += " Price: 10" + "\n";
        result += "Salesman: salesmanUser2";
        result += " Price: 100" + "\n";
        result += "Confirmation state for you: ACCEPTED" + "\n";
        result += "Your remainder: 10" + "\n";
        Assert.assertTrue(product.toStringForSalesmanView(salesman1.getUsername()).startsWith(result));
        String s=result+"The product isn't on sale"+"\n";
        Assert.assertEquals(s,product.toStringForSalesmanView(salesman1.getUsername()));
        result += "The product is on sale"+"\n";
        product.setIsOnSale(salesman1.getUsername(),true);
        Assert.assertEquals(result,product.toStringForSalesmanView(salesman1.getUsername()));
    }


}
