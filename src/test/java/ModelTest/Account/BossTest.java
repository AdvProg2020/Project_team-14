package ModelTest.Account;

import org.junit.Assert;
import org.junit.Test;

public class BossTest {

    @Test
    public void isThereBoss1(){
        Assert.assertFalse(Boss.isThereBoss());
    }

    @Test
    public void isThereBoss2(){
        Boss boss=new Boss("username","password","manuel",
                "neuer","h.hafezi2000@gmail.com","09333805288","Boss");
        Assert.assertTrue(Boss.isThereBoss());
    }

    @Test
    public void toStringTest() {
        Boss boss=new Boss("username","password","manuel",
                "neuer","h.hafezi2000@gmail.com","09333805288","Boss");
        String result = "Username: " + boss.getUsername() + "\n";
        result += "Name: " + boss.getFirstName() + " " + boss.getSecondName() + "\n";
        result += "Email: " + boss.getEmail() + "\n";
        result += "Telephone: " + boss.getTelephone() + "\n";
        result += "Role: " + boss.getRole() + "\n";
        Assert.assertEquals(boss.toString(), result);
    }

}
