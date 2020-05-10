package Model.Account;

import org.junit.Assert;
import org.junit.Test;

public class SalesmanTest {


    Salesman salesman = new Salesman("username", "password", "firstName",
            "secondName", "h.hafezi2000@gmail.com", "09333805288",
            "SALESMAN", "company");


    @Test
    public void toStringTest() {
        String result = "Username: " + "username" + "\n";
        result += "Name: " + "" + "firstName" + " secondName" + "\n";
        result += "Email: " + "h.hafezi2000@gmail.com" + "\n";
        result += "Telephone: " + "09333805288" + "\n";
        result += "Role: " + "SALESMAN" + "\n";
        result += "Company: " + salesman.getCompany() + "\n";
        result += "Credit: " + salesman.getCredit() + "\n";
        result += "Confirmation State: " + "CHECKING" + "\n";
        Assert.assertEquals(salesman.toString(), result);
    }

    @Test
    public void setCompany() {
        salesman.setCompany("co...");
        Assert.assertEquals(salesman.getCompany(), "co...");
    }

    @Test
    public void setCredit() {
        salesman.setCredit(8585);
        Assert.assertEquals(salesman.getCredit(), 8585);
    }

    @Test
    public void isConfirmed() {
        Assert.assertFalse(salesman.isConfirmed());
    }
}
