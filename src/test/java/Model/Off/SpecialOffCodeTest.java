package Model.Off;

import Model.Account.Customer;
import Model.Storage;
import org.junit.Assert;
import org.junit.Test;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import static Model.Storage.allOffCodes;
import static Model.Storage.allSpecialOffCodes;

public class SpecialOffCodeTest {
    SpecialOffCode specialOffCode = new SpecialOffCode(1,20,1000,2);
    @Test
    public void SpecialOffCodeTesting() throws InterruptedException {
        Customer customer = new Customer("customerUser", "password", "firstname", "secondName",
                "h.hafezi2000@yahoo.com", "09333805288", "CUSTOMER", 0);
        System.out.println(Customer.getRandomUsername());
        specialOffCode.setCeiling(1000);
        specialOffCode.setNumberOfTimesItCanBeUsed(2);
        specialOffCode.deactivate();
        specialOffCode.setTimeInSeconds(1);
        specialOffCode.setOffCodeDurationInHour(48);
        specialOffCode.setPercentage(50);
        Assert.assertFalse(Storage.allSpecialOffCodes.isEmpty());
        specialOffCode.activate();
        Thread.sleep(10000);
        System.out.println(allOffCodes.size());
        Assert.assertTrue(Storage.allOffCodes.size() >= 1);
        specialOffCode.deactivate();
    }

}
