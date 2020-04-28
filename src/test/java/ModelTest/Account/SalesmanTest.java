package ModelTest.Account;

import org.junit.Assert;
import org.junit.Test;

public class SalesmanTest {


    Salesman salesman = new Salesman("username", "password", "firstName",
            "secondName", "h.hafezi2000@gmail.com", "09333805288",
            "SALESMAN", "company", 1000);


    @Test
    public void toStringTest() {
        String result = "Username: " + salesman.getUsername() + "\n";
        result += "Name: " + salesman.getFirstName() + " " + salesman.getSecondName() + "\n";
        result += "Email: " + salesman.getEmail() + "\n";
        result += "Telephone: " + salesman.getTelephone() + "\n";
        result += "Role: " + salesman.getRole() + "\n";
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
