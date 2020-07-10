package Model.Account;

import Model.RandomString;
import org.junit.Assert;
import org.junit.Test;

public class CustomerTest {
    Customer customer = new Customer("username", "password", "firstName"
            , "secondName", "h.hafezi2000@gmail.com", "09333805288", "CUSTOMER", 1000, null);

    @Test
    public void toStringTest() {
        String result = "Username: " + "username" + "\n";
        result += "Password: password\n";
        result += "Name: " + "" + "firstName" + " secondName" + "\n";
        result += "Email: " + "h.hafezi2000@gmail.com" + "\n";
        result += "Telephone: " + "09333805288" + "\n";
        result += "Role: " + "CUSTOMER" + "\n";
        result += "Credit: " + customer.getCredit() + "\n";
        Assert.assertEquals(customer.toString(), result);
    }

    @Test
    public void isOnline() {
        Assert.assertFalse(customer.isOnline());
    }

    @Test
    public void setIsOnline() {
        customer.setOnline(true);
        Assert.assertTrue(customer.isOnline());
    }

    @Test
    public void getPassword() {
        Assert.assertEquals(customer.getPassword(), "password");
    }

    @Test
    public void setPassword() {
        customer.setPassword("8936000230");
        Assert.assertEquals(customer.getPassword(), "8936000230");
    }

    @Test
    public void setFirstName() {
        customer.setFirstName("Manuel");
        Assert.assertEquals(customer.getFirstName(), "Manuel");
    }

    @Test
    public void setSecondName() {
        customer.setSecondName("Neuer");
        Assert.assertEquals(customer.getSecondName(), "Neuer");
    }

    @Test
    public void setUser() {
        customer.setUsername("newUsername");
        Assert.assertEquals(customer.getUsername(), "newUsername");
    }

    @Test
    public void setEmail() {
        customer.setEmail("h.hafezi2000@yahoo.com");
        Assert.assertEquals(customer.getEmail(), "h.hafezi2000@yahoo.com");
    }

    @Test
    public void getRole() {
        Assert.assertEquals(customer.getRole().name(), "CUSTOMER");
    }

    @Test
    public void setTelephone() {
        customer.setTelephone("111111");
        Assert.assertEquals(customer.getTelephone(), "111111");
    }

    @Test
    public void setCredit() {
        customer.setCredit(1111);
        Assert.assertEquals(customer.getCredit(), 1111);
    }

    @Test
    public void isThereCustomerWithUsername1() {
        boolean flg = Customer.isThereCustomerWithUsername(customer.getUsername());
        Assert.assertTrue(flg);
    }

    @Test
    public void isThereCustomerWithUsername2() {
        String random = RandomString.createID("ioi");
        Assert.assertFalse(Customer.isThereCustomerWithUsername(random));
    }


    @Test
    public void getRandomUsername2() {
        Assert.assertEquals(Customer.getRandomUsername(), customer.getUsername());
    }
}