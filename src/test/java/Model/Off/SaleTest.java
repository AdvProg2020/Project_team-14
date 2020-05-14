package Model.Off;

import Model.Account.Customer;
import Model.Account.Salesman;
import Model.Confirmation;
import Model.Product.Product;
import org.junit.Assert;
import org.junit.Test;

import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class SaleTest {
    Salesman salesman1 = new Salesman("salesmanUser1", "password", "firstname", "secondName",
            "h.hafezi2000@gmail.com", "09333805288", "SALESMAN", "company",1000);

    Customer customer = new Customer("customerUser", "password", "firstname", "secondName",
            "h.hafezi2000@yahoo.com", "09333805288", "CUSTOMER", 1000,null);

    Product product = new Product("name", salesman1.getUsername(), "brand", "description", 10, 10);
    Product product2 = new Product("name2", salesman1.getUsername(), "brand2", "description2", 10, 10);
    ArrayList<String> arrayList = new ArrayList();

    @Test
    public void saleTesting() throws ParseException {
        arrayList.add(product.getProductID());
        arrayList.add(product2.getProductID());
        Date nowDate = new Date();
        Format formatter = new SimpleDateFormat("dd-MM-yyyy HH-mm-ss");
        String today = formatter.format(nowDate);
        Sale sale = new Sale("01-12-2020 20-20-20", "01-12-2020 20-20-20", 20, salesman1.getUsername());
        sale.setConfirmationState(Confirmation.ACCEPTED);
        Assert.assertTrue(sale.isConfirmed());
        Assert.assertFalse(sale.isChecking());
        Assert.assertFalse(sale.isAuthentic());
        sale.setStart(today);
        Assert.assertTrue(sale.isAuthentic());
        sale.addToProducts(product.getProductID());
        sale.addToProducts(product2.getProductID());
        sale.addToProducts("neuer is the best");
        sale.removeFromProducts("neuer is the best");
        Assert.assertTrue(sale.doesContainProduct(product.getProductID()));
        Assert.assertEquals(sale.listProducts(), arrayList);
        Assert.assertEquals(sale, Sale.getSaleByID(sale.getSaleID()));
        Assert.assertNull(Sale.getSaleByID("neuer is the best"));
        Assert.assertEquals(Sale.getPriceAfterSale(product.getProductID(), "salesmanUser1"), 8);
        sale.removeFromProducts(product.getProductID());
        Assert.assertEquals(Sale.getPriceAfterSale(product.getProductID(), "salesmanUser1"), 10);
        Assert.assertFalse(Sale.isProductInSaleWithID(product.getProductID(), "salesmanUser1"));
        Assert.assertTrue(Sale.isProductInSaleWithID(product2.getProductID(), "salesmanUser1"));
    }

    @Test
    public void toStringTest() throws ParseException {
        Sale sale = new Sale("01-12-2020 20-20-20", "01-12-2020 20-20-20", 20, salesman1.getUsername());
        sale.addToProducts(product.getProductID());
        sale.addToProducts(product2.getProductID());
        String result = "";
        result += "Percentage: 20" + "\n";
        result += "Start Date: " + sale.start.toString() + "\n";
        result += "End Date: " + sale.end.toString() + "\n";
        result += "Products: " + "\n";
        result += "name" + "\n";
        result += "name2" + "\n";
        Assert.assertEquals(result, sale.toString());

    }
}
