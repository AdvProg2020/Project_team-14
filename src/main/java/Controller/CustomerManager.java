package Controller;

import Model.Account.Customer;
import Model.Cart.Cart;
import Model.Log.BuyLog;
import Model.Log.SellLog;
import Model.Off.Off;
import Model.Off.OffCode;
import Model.Product.Point;
import Model.Storage;

import java.util.ArrayList;

public class CustomerManager {

    public void register(String[] information) {
        if (Storage.isThereAccountWithUsername(information[3])) {
            Server.setAnswer("username has already been taken");
        }
        Server.setAnswer("register successful");
        new Customer(information[3], information[4], information[1], information[2],
                information[6], information[7], information[5], 0, null);
    }

    public void viewDiscountCode (String username, String sortFactor) {
        ArrayList<OffCode> userOffCodes = OffCode.getAllCustomerOffCodesByUsername(username);
        /*
         * sort ArrayList
         */
        StringBuilder ans = new StringBuilder("Here are all of your Discount code:");
        for (OffCode offCode : userOffCodes) {
            ans.append("\nOffCodeID: " + offCode.getOffCodeID() + ", Percentage: " + offCode.getPercentage());
        }
        Server.setAnswer(ans.toString());
    }

    public void showAllProductsInCart (String username, String sortFactor) {
        Server.setAnswer(((Customer)Storage.getAccountWithUsername(username)).getCart().toString());
    }

    public void addOrRemoveProductToCart (String type, String username, String productID, String salesmanID) {
        //assume productID ans salesmanID are valid
        StringBuilder ans = new StringBuilder("your product ");
        Cart customerCart = ((Customer)Storage.getAccountWithUsername(username)).getCart();
        if (type.equals("add")) {
            ans.append("added to cart successfully");
            customerCart.addProductToCart(productID, salesmanID, customerCart.getCartID());
        } else if (type.equals("remove")) {
            ans.append("removed from cart successfully");
            customerCart.removeProductFromCart(productID);
        }
        Server.setAnswer(ans.toString());
    }

    public void showTotalPrice (String username) {
        Server.setAnswer("Total Price:\n" + ((Customer) Storage.getAccountWithUsername(username)).getCart().getTotalPrice(null));
    }

    //order part, still is here, we can make a new class for it [not recommended (:]

    public void showAllOrders(String username, String sortFactor) {
        ArrayList<BuyLog> customerBuyLog = BuyLog.getUserBuyLogs(username);
        StringBuilder ans = new StringBuilder("Here are All of your orders:");
        for (BuyLog buyLog : customerBuyLog) {
            ans.append("\n" + buyLog.getBuyLogID());
        }
        Server.setAnswer(ans.toString());
    }

    public void showSingleOrder(String buyLogID) {
        BuyLog buyLog = BuyLog.getBuyLogByID(buyLogID);
        Server.setAnswer("Your BuyLog detail:\n" + buyLog.toString());
    }

    public void rateProduct(String username, String productID, int point) {
        new Point(username, productID, point);
        Server.setAnswer("successful, your point added");
    }

    //purchase part, still is here, we can make a new class for it :)

    //public void createLog();          ->      doesn't match this logic
    //public void setInfoOfLog();       ->      doesn't match this logic

    public void setOffCode(String username, String offCodeID) {
        StringBuilder ans = new StringBuilder("");
        OffCode offCode = OffCode.getOffCodeByID(offCodeID);
        if (offCodeID != null) {
            if (offCode == null) {
                Server.setAnswer("error, offCode doesn't exist.");
                return;
            } else if (!offCode.canCustomerUseItWithUsername(username)){
                Server.setAnswer("error, you do not have permission to use this offCode.");
                return;
            }
        }
        Customer customer = (Customer) Storage.getAccountWithUsername(username);
        Server.setAnswer("Final Price:\n" + customer.getCart().getTotalPrice(offCodeID));
    }

    public void buy(String username, String offCodeID) {
        Customer customer = (Customer) Storage.getAccountWithUsername(username);
        if (customer.getCart().buy(offCodeID)) {
            Server.setAnswer("successful, your purchase completed");
            BuyLog buyLog = new BuyLog(customer.getCart(), offCodeID);
            for (String productID : customer.getCart().getProductIDs().keySet()) {
                new SellLog(buyLog, productID, customer.getCart().getProductIDs().get(productID));
            }
        } else {
            Server.setAnswer("error, you don't have enough credit to purchase");
        }
    }
}