package Model.Off;

import Model.Account.Customer;
import Model.Storage;
import org.junit.Assert;
import org.junit.Test;

public class SpecialOffCodeTest {
    SpecialOffCode specialOffCode = new SpecialOffCode();

    @Test
    public void SpecialOffCodeTesting() throws InterruptedException {
        Customer customer = new Customer("customerUser", "password", "firstname", "secondName",
                "h.hafezi2000@yahoo.com", "09333805288", "CUSTOMER",0);
        specialOffCode.setCeiling(1000);
        specialOffCode.setNumberOfTimesItCanBeUsed(2);
        specialOffCode.deactivate();
        specialOffCode.setTimeInSeconds(10);
        specialOffCode.setOffCodeDurationInHour(48);
        specialOffCode.setPercentage(50);
        Assert.assertFalse(Storage.allSpecialOffCodes.isEmpty());
        specialOffCode.activate();
        Assert.assertTrue(Storage.allOffCodes.size() >= 1);
        specialOffCode.deactivate();
    }

}
