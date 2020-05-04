package Model.Log;

import Model.RandomString;
import Model.Storage;

import java.awt.image.AreaAveragingScaleFilter;
import java.io.Serializable;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public abstract class Log implements Serializable {
    private Date date;

    public Log() {
        this.date = new Date();
    }

    public Date getDate() {
        return date;
    }

    public String toString() {
        return "Date: " + getDate().toString();
    }

    ;

    public static boolean hasCustomerBoughtProduct(String username, String productID) {
        for (BuyLog buyLog : BuyLog.getUserBuyLogs(username)) {
            if (buyLog.containProduct(productID)) {
                return true;
            }
        }
        return false;
    }

    public static ArrayList<String> getBuyerOfProduct(String productID) {
        Set<String> customerUserNames = new HashSet<>();
        for (BuyLog buyLog : Storage.allBuyLogs) {
            if (buyLog.containProduct(productID)) {
                customerUserNames.add(buyLog.getCustomerUsername());

            }
        }
        return new ArrayList<>(customerUserNames);
    }
}
