package Model.Log;

import Model.Cart.Cart;
import Model.Off.OffCode;
import Model.Product.Product;
import Model.RandomString;
import Model.Storage;

import java.util.ArrayList;
import java.util.HashMap;

public class BuyLog extends Log {

    //the first argument is productID and the second one is salesmanID
    private HashMap<String, String> products;

    //the first argument is productID and the second one is the prices with consideration of possible sales
    private HashMap<String, Integer> prices;

    //the first argument is productID and the second one is the prices with consideration of possible sales
    private HashMap<String, Integer> pricesAfterSale = new HashMap<>();

    private String customerUsername;
    private String buyLogID;
    private String offCodeID;
    private int totalAmountWithOutOffCode;
    private int totalAmountWithOffCode;
    private Delivery deliveryState;
    private boolean wasOffCodeUsed;

    //mark we should make sure that the offCode is authentic before passing it to BuyLog

    public BuyLog(Cart cart, String offCodeID) {
        super();
        this.products = cart.getProductIDs();
        this.prices = cart.getPricesAfterSale();
        this.pricesAfterSale = cart.getPricesAfterSale();
        this.customerUsername = cart.getUsername();
        this.totalAmountWithOutOffCode = cart.getTotalPrice(null);
        this.totalAmountWithOffCode = cart.getTotalPrice(offCodeID);
        this.offCodeID = offCodeID;
        if (offCodeID.isEmpty()) {
            wasOffCodeUsed = false;
        } else {
            this.offCodeID = offCodeID;
            wasOffCodeUsed = true;
            OffCode offCode = OffCode.getOffCodeByID(offCodeID);
            assert offCode != null;
        }
        deliveryState = Delivery.PROCESSING;
        this.buyLogID = createID();
        Storage.allBuyLogs.add(this);
        for (String productID : products.keySet()) {
            new SellLog(this, productID, products.get(productID));
        }
    }

    public HashMap<String, Integer> getPrices() {
        return prices;
    }

    public Delivery getDeliveryState() {
        return deliveryState;
    }

    public HashMap<String, Integer> getPricesAfterSale() {
        return pricesAfterSale;
    }

    public HashMap<String, String> getProducts() {
        return products;
    }

    public String getCustomerUsername() {
        return customerUsername;
    }

    public String createID() {
        return RandomString.createID("BuyLog");
    }

    public String getBuyLogID() {
        return buyLogID;
    }

    public boolean containProduct(String productID) {
        return products.containsKey(productID);
    }

    public static ArrayList<BuyLog> getUserBuyLogs(String customerUsername) {
        ArrayList<BuyLog> arrayList = new ArrayList<>();
        for (BuyLog buyLog : Storage.allBuyLogs) {
            if (buyLog.customerUsername.equals(customerUsername)) {
                arrayList.add(buyLog);
            }
        }
        return arrayList;
    }

    public static BuyLog getBuyLogByID(String buyLogID) {
        for (BuyLog buyLog : Storage.allBuyLogs) {
            if (buyLog.getBuyLogID().equals(buyLogID)) return buyLog;
        }
        return null;
    }

    private StringBuilder toStringSingleProduct(String productID) {
        StringBuilder result = new StringBuilder();
        result.append("Product Name:").append(Product.getNameByID(productID)).append("\n");
        result.append("Salesman: ").append(products.get(productID)).append("\n");
        result.append("Price: ").append(prices.get(productID)).append("\n");
        if (pricesAfterSale.get(productID).equals(prices.get(productID))) {
            result.append("The product wasn't on sale." + "\n");
        } else {
            result.append("The price after sale: ").append(pricesAfterSale.get(productID)).append("\n");
        }
        result.append("------------------------------------------").append("\n");
        return result;
    }

    private String toStringProducts() {
        StringBuilder result = new StringBuilder();
        result.append("Products: " + "\n");
        for (String productID : products.keySet()) {
            result.append(toStringSingleProduct(productID));
        }
        return result.toString();
    }

    private String toStringOffCodeUsage() {
        if (wasOffCodeUsed) {
            return "No offCode was used." + "\n";
        } else {
            return "Price after using OffCode: " + totalAmountWithOffCode + "\n";
        }
    }

    @Override
    public String toString() {
        return "Customer: " + customerUsername + "\n" +
                "Amount without OffCode: " + totalAmountWithOutOffCode + "\n" +
                "Delivery State: " + deliveryState + "\n" +
                toStringOffCodeUsage() + toStringProducts() +
                super.toString();
    }
}
