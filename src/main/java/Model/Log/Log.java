package Model.Log;

import Model.RandomString;
import Model.Storage;

import java.io.Serializable;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;

public abstract class Log implements Serializable {
    private Date date;

    public Log() {
        this.date = new Date();
    }

    public Date getDate() {
        return date;
    }

    public String toString(){
        return "Date: "+getDate().toString();
    };

    public static boolean hasCustomerBoughtProduct(String username,String productID){
        for(BuyLog buyLog: BuyLog.getUserBuyLogs(username)){
            if(buyLog.containProduct(productID)){
                return true;
            }
        }
        return false;
    }
}
