package Model.Product;

import Model.Account.Customer;
import Model.Account.Salesman;
import org.junit.Assert;
import org.junit.Test;

public class PointTest {
    Salesman salesman = new Salesman("salesmanUser", "password", "firstname", "secondName",
            "h.hafezi2000@gmail.com", "09333805288", "SALESMAN", "company", 0);

    Customer customer = new Customer("customerUser", "password", "firstname", "secondName",
            "h.hafezi2000@yahoo.com", "09333805288", "CUSTOMER", 1000);

    Product product = new Product("name", salesman.getUsername(), "brand", "description", 10, 10);

    Product product2 = new Product("name2", salesman.getUsername(), "brand2", "description2", 10, 10);

    Point point = new Point(customer.getUsername(), product.getProductID(), 4);
    Point point2 = new Point(customer.getUsername(), product.getProductID(), 3);
    Point point3 = new Point(customer.getUsername(), product.getProductID(), 4);


    @Test
    public void isTherePointWithID1() {
        Assert.assertTrue(Point.isTherePointWithID(point.getPointID()));
        Assert.assertFalse(Point.isTherePointWithID("ABC"));
    }

    @Test
    public void isTherePointForProduct() {
        Assert.assertFalse(Point.isTherePointForProduct(product2.getProductID()));
        Assert.assertTrue(Point.isTherePointForProduct(product.getProductID()));
    }

    @Test
    public void getAveragePointForProduct() {
        double x = (double) (3 + 4 + 4) / 3;
        Assert.assertEquals(Double.toString(x), Double.toString(Point.getAveragePointForProduct(product.getProductID())));
    }

    @Test
    public void createID() {
        Assert.assertTrue(point.createID().startsWith("Point---"));
    }

}
