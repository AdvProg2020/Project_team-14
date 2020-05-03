package Model.Log;

import Model.Account.Salesman;
import Model.Product.Product;
import Model.Storage;

import java.util.ArrayList;

public class SellLog {
    private BuyLog buyLog;
    private String productID;
    private String salesmanID;
    private String customerID;
    private boolean wasOnSale;
    private int priceBeforeSale;
    private int priceAfterSale;

    public SellLog(BuyLog buyLog, String productID, String salesmanID) {
        this.buyLog = buyLog;
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
    }

    public String getDeliveryState() {
        return buyLog.getDeliveryState().name();
    }

    public ArrayList<SellLog> getSalesmanSellLogs(String salesmanID) {
        ArrayList<SellLog> arrayList = new ArrayList<>();
        for (SellLog sellLog : Storage.allSellLogs) {
            if (sellLog.salesmanID.equals(salesmanID)) {
                arrayList.add(sellLog);
            }
        }
        return arrayList;
    }

    private String toStringSingleSellLogs() {
        String result = "";
        result += "Product: " + Product.getNameByID(productID) + "\n";
        result += "Customer Username: " + customerID + "\n";
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

    public String getSalesmanSellLogs_StringFormatted(String salesmanID) {
        StringBuilder result = new StringBuilder("Sell logs of salesman: " + salesmanID + "\n");
        for (SellLog sellLog : getSalesmanSellLogs(salesmanID)) {
            result.append(sellLog.toStringSingleSellLogs());
        }
        return result.toString();
    }


}