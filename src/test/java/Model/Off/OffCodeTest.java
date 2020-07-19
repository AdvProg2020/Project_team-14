package Model.Off;

import Model.Account.Customer;
import Model.Account.Salesman;
import Model.Product.Product;
import org.junit.Assert;
import org.junit.Test;

import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class OffCodeTest {
    Salesman salesman1 = new Salesman("salesmanUser1", "password", "firstname", "secondName",
            "h.hafezi2000@gmail.com", "09333805288", "SALESMAN", "company", 1000);
    Product product = new Product("name", salesman1.getUsername(), "brand", "description", 10000, 10);

    @Test
    public void setOffCode1() throws ParseException {
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("username1");
        arrayList.add("username2");
        new Customer("username1","costure","costure","costure","costure","costure","costure",1);
        new Customer("username2","costure","costure","costure","costure","costure","costure",1);
        Date nowDate = new Date();
        Format formatter = new SimpleDateFormat("dd-MM-yyyy HH-mm-ss");
        String today = formatter.format(nowDate);
        OffCode offCode1 = new OffCode("01-12-2020 20-20-20", "02-12-2020 20-20-20", 20, 1000, 2, arrayList);
        offCode1.setPercentage("25");
        //Assert.assertFalse(offCode1.isAuthentic());
        offCode1.setStart(today);
        //Assert.assertTrue(offCode1.isAuthentic());
        Assert.assertEquals(offCode1, OffCode.getOffCodeByID(offCode1.getOffCodeID()));
       // Assert.assertTrue(OffCode.isThereOffCodeWithID(offCode1.getOffCodeID()));
        Assert.assertEquals(OffCode.getFinalPrice(10000, offCode1.getOffCodeID()), 9000);
        Assert.assertEquals(OffCode.getFinalPrice(1000, offCode1.getOffCodeID()), 750);
        //Assert.assertTrue(OffCode.isOffCodeAuthenticWithID(offCode1.getOffCodeID()));
        offCode1.reduceNumberOfTimesItCanBeUsed();
        offCode1.reduceNumberOfTimesItCanBeUsed();
        offCode1.reduceNumberOfTimesItCanBeUsed();
        Assert.assertFalse(offCode1.isAuthentic());
        offCode1.setEnd(today);
        Assert.assertFalse(offCode1.isAuthentic());
        Assert.assertFalse(OffCode.isOffCodeAuthenticWithID("Neuer is the best"));
        Assert.assertTrue(offCode1.canCustomerUseItWithUsername("username1"));
        Assert.assertFalse(offCode1.canCustomerUseItWithUsername("username10"));
    }

    @Test
    public void toStringTest() throws ParseException {
        ArrayList<String> arrayList = new ArrayList<>();
        new Customer("username1", "sd", "sd", "SDgds", "dfsaf", "sdf", "customer", 1923);
        new Customer("username2", "sd", "sd", "SDgds", "dfsaf", "sdf", "customer", 1923);
        arrayList.add("username1");
        arrayList.add("username2");
        Assert.assertTrue(Customer.isThereCustomerWithUsername("username1"));
        Assert.assertTrue(Customer.isThereCustomerWithUsername("username2"));
        String result = "";
        OffCode offCode2 = new OffCode("01-12-2020 20-20-20", "02-12-2020 20-20-20", 20, 1000, 2, arrayList);
        System.out.println(offCode2.getOffCodeID());
        result += "Percentage:20" + "\n";
        result += "Start Date:0007-06-09" + "\n";
        result += "End Date:0008-06-08" + "\n";
        Assert.assertEquals(result, offCode2.toString());
    }

}
