package Controller;

import Model.Account.Customer;
import Model.Cart.Cart;
import Model.Log.BuyLog;
import Model.Log.SellLog;
import Model.Off.Off;
import Model.Off.OffCode;
import Model.Product.Point;
import Model.Product.Product;
import Model.Storage;

import java.util.ArrayList;
import java.util.HashMap;

public class CustomerManager {
    /*
     * this is account apart
     */

    public void register(String[] information) {
        if (Storage.isThereAccountWithUsername(information[3])) {
            Server.setAnswer("username has already been taken");
        }
        Server.setAnswer("register successful");
        new Customer(information[3], information[4], information[1], information[2],
                information[6], information[7], information[5], 0, null);
    }

    public void viewDiscountCodes (String username, String sortFactor) {
        Customer customer = (Customer)Storage.getAccountWithUsername(username);
        HashMap<String, Integer> userOffCodes = customer.getOffCodesUsage();
        /*
         * sort ArrayList
         */
        StringBuilder ans = new StringBuilder("Here are all of your Discount code:");
        for (String offCodeID : userOffCodes.keySet()) {
            ans.append("\nOffCode ID: " + offCodeID + ", Percentage: " + OffCode.getOffCodeByID(offCodeID).getPercentage() +
                    ", Remaining time of use: " + userOffCodes.get(offCodeID));
        }
        Server.setAnswer(ans.toString());
    }

    /*
     * this is cart part
     */

    public void showAllProductsInCart(String username, String sortFactor) {
        Customer customer = (Customer) Storage.getAccountWithUsername(username);
        assert customer != null;
        Server.setAnswer(customer.getCart().toString());
    }

    public void addOrRemoveProductToCart(String type, String username, String productID, String salesmanID) {
        //assume productID ans salesmanID are valid
        StringBuilder ans = new StringBuilder("your product ");
        Cart customerCart = Cart.getCartBasedOnUsername(username);
        if (type.equals("add")) {
            if (customerCart.addProductToCart(productID, salesmanID, customerCart.getCartID())) {
                ans.append("added to cart successfully");
            } else {
                ans.append("couldn't added to cart, because no product remains");
            }
        } else if (type.equals("remove")) {
            ans.append("removed from cart successfully");
            customerCart.removeProductFromCart(productID);
        }
        Server.setAnswer(ans.toString());
    }

    public void showTotalPrice(String username) {
        Customer customer = (Customer) Storage.getAccountWithUsername(username);
        assert customer != null;
        Server.setAnswer("Total Price:" + "\n" + customer.getCart().getTotalPrice(null));
    }

    /*
     * this is order part
     */

    public void showAllBuyLogs(String username, String sortFactor) {
        ArrayList<BuyLog> customerBuyLog = BuyLog.getUserBuyLogs(username);
        StringBuilder ans = new StringBuilder("Here are All of your orders:");
        for (BuyLog buyLog : customerBuyLog) {
            ans.append("\n").append(buyLog.getBuyLogID());
        }
        Server.setAnswer(ans.toString());
    }

    public void showSingleOrder(String buyLogID) {
        BuyLog buyLog = BuyLog.getBuyLogByID(buyLogID);
        assert buyLog != null;
        Server.setAnswer("Your BuyLog detail:" + "\n" + buyLog.toString());
    }

    public void rateProduct(String username, String productID, int point) {
        new Point(username, productID, point);
        Server.setAnswer("successful, your point added");
    }

    /*
     * this is purchase part
     */

    //public void createLog();          ->      doesn't match this logic
    //public void setInfoOfLog();       ->      doesn't match this logic

    public void canUserContinuePurchase(String username) {
        HashMap<String, String> errorProductIDs = new HashMap<>();
        Customer customer = (Customer) Storage.getAccountWithUsername(username);
        //find errors
        for (String productID : customer.getCart().getProductIDs().keySet()) {
            String salesmanID = customer.getCart().getProductIDs().get(productID);
            if (!Product.getProductWithID(productID).isAvailableBySalesmanWithUsername(salesmanID)) {
                errorProductIDs.put(productID, salesmanID);
            }
        }
        //set answer to server
        if (errorProductIDs.size() == 0) {
            Server.setAnswer("you can continue your purchase");
        } else {
            StringBuilder error = new StringBuilder("error, some products are not available, remove them from your cart and then continue");
            for (String productID : errorProductIDs.keySet()) {
                error.append("\n product [" + productID + "] is not available by [" + errorProductIDs.get(productID) + "]");
            }
        }
    }

    public void setOffCode(String username, String offCodeID) {
        StringBuilder ans = new StringBuilder("");
        OffCode offCode = OffCode.getOffCodeByID(offCodeID);
        if (offCodeID != null) {
            if (offCode == null) {
                Server.setAnswer("error, offCode doesn't exist.");
                return;
            } else if (!offCode.canCustomerUseItWithUsername(username)) {
                Server.setAnswer("error, you do not have permission to use this offCode.");
                return;
            }
        }
        Customer customer = (Customer) Storage.getAccountWithUsername(username);
        assert customer != null;
        Server.setAnswer("Final Price:" + "\n" + customer.getCart().getTotalPrice(offCodeID));
    }

    public void buy(String username, String offCodeID) {
        Customer customer = (Customer) Storage.getAccountWithUsername(username);
        assert customer != null;
        if (customer.getCart().buy(offCodeID)) {
            Server.setAnswer("successful, your purchase completed");
        } else {
            Server.setAnswer("error, you don't have enough credit to purchase");
        }
    }
}