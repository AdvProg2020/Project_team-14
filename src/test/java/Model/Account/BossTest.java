package Model.Account;

import org.junit.Assert;
import org.junit.Test;

public class BossTest {

    @Test
    public void customer() {
        Assert.assertNull(Customer.getRandomUsername());
    }


    @Test
    public void isThereBoss1() {
        Assert.assertFalse(Boss.isThereBoss());
    }

    @Test
    public void isThereBoss2() {
        Boss boss = new Boss("username", "password", "manuel",
                "neuer", "h.hafezi2000@gmail.com", "09333805288", "Boss");
        Assert.assertTrue(Boss.isThereBoss());
    }

    @Test
    public void toStringTest() {
        Boss boss = new Boss("username", "password", "manuel",
                "neuer", "h.hafezi2000@gmail.com", "09333805288", "Boss");
        String result = "Username: " + "username" + "\n";
        result += "Password: " + "password" + "\n";
        result += "Name: " + "" + "manuel" + " neuer" + "\n";
        result += "Email: " + "h.hafezi2000@gmail.com" + "\n";
        result += "Telephone: " + "09333805288" + "\n";
        result += "Role: " + "BOSS" + "\n";
        Assert.assertEquals(boss.toString(), result);
    }

}
