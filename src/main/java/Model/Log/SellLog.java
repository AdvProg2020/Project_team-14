package Model.Log;

import Model.Account.Salesman;
import Model.Product.Product;
import Model.RandomString;
import Model.Storage;

import javax.xml.transform.sax.SAXResult;
import java.io.Serializable;
import java.util.ArrayList;

public class SellLog implements Serializable {
    private String sellLogID;
    private BuyLog buyLog;
    private String productID;
    private String salesmanID;
    private int productCount;
    private String customerID;
    private boolean wasOnSale;
    private int priceBeforeSale;
    private int priceAfterSale;

    public SellLog(BuyLog buyLog, String productID, String salesmanID) {
        this.buyLog = buyLog;
        this.productCount = buyLog.getProductCountByID(productID);
        this.priceAfterSale = buyLog.getPricesAfterSale().get(productID);
        this.priceBeforeSale = buyLog.getPrices().get(productID);
        this.wasOnSale = !(this.priceAfterSale == this.priceBeforeSale);
        this.customerID = buyLog.getCustomerUsername();
        this.salesmanID = salesmanID;
        this.productID = productID;
        Storage.allSellLogs.add(this);
        Salesman salesman = (Salesman) Storage.getAccountWithUsername(salesmanID);
        assert salesman != null;
        salesman.setCredit(salesman.getCredit() + priceAfterSale);
        sellLogID = RandomString.createID("SellLog");
    }

    public String getSellLogID() {
        return sellLogID;
    }

    public String getDeliveryState() {
        return buyLog.getDeliveryState().name();
    }

    public void setSalesmanID(String salesmanID) {
        this.salesmanID = salesmanID;
    }

    public String getSalesmanID() {
        return salesmanID;
    }

    public static ArrayList<SellLog> getSalesmanSellLogs(String salesmanID) {
        ArrayList<SellLog> arrayList = new ArrayList<>();
        for (SellLog sellLog : Storage.allSellLogs) {
            if (sellLog.salesmanID.equals(salesmanID)) {
                arrayList.add(sellLog);
            }
        }
        return arrayList;
    }

    private void updateSellLogsWithNewUsername(String oldUsername, String newUsername) {
        if (this.customerID.equals(oldUsername)) {
            customerID = newUsername;
        } else if (this.salesmanID.equals(oldUsername)) {
            salesmanID = newUsername;
        }
    }

    public static void updateAllSellLogsWithNewUsername(String oldUsername,String newUsername){
        for (SellLog sellLog:Storage.allSellLogs){
            sellLog.updateSellLogsWithNewUsername(oldUsername,newUsername);
        }
    }

    public static boolean hasSalesmanAnySellLog(String salesmanID) {
        return getSalesmanSellLogs(salesmanID).size() != 0;
    }

    private String toStringSingleSellLogs() {
        String result = "";
        result += "Product: " + Product.getNameByID(productID) + "\n";
        result += "Customer Username: " + customerID + "\n";
        result += "Count: " + productCount + "\n";
        if (wasOnSale) {
            result += "the product was sold on sale" + "\n";
            result += "price before sale: " + priceBeforeSale + "\n";
            result += "price after sale: " + priceAfterSale + "\n";
        } else {
            result += "the product wasn't on sale" + "\n";
            result += "price: " + priceBeforeSale + "\n";
        }
        result += "Delivery state: " + getDeliveryState();
        return result;
    }

    public static String getSalesmanSellLogs_StringFormatted(String salesmanID) {
        if (!hasSalesmanAnySellLog(salesmanID)) {
            return "no sell log yet!";
        }
        StringBuilder result = new StringBuilder("Sell logs of salesman: " + salesmanID + "\n");
        for (SellLog sellLog : getSalesmanSellLogs(salesmanID)) {
            result.append(sellLog.toStringSingleSellLogs());
        }
        return result.toString();
    }
}
