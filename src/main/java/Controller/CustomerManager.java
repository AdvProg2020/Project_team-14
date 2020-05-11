package Controller;

import Controller.SortFactorEnum.ListOffCodesSortFactor;
import Model.Account.Customer;
import Model.Cart.Cart;
import Model.Log.BuyLog;
import Model.Log.SellLog;
import Exception.*;
import Model.Off.OffCode;
import Model.Product.Point;
import Model.Storage;

import java.util.ArrayList;

public class CustomerManager {

    public void register(String[] information) throws UsernameAlreadyExistException {
        if (Storage.isThereAccountWithUsername(information[3])) {
            throw new UsernameAlreadyExistException("the username already exists, try something else");
        }
        Server.setAnswer("register successful");
        new Customer(information[3], information[4], information[1], information[2],
                information[6], information[7], information[5], 0, null);
    }

    public void viewOffCode(String username, String sortFactor) throws SortFactorNotAvailableException {
        ArrayList<OffCode> userOffCodes = OffCode.getAllCustomerOffCodesByUsername(username);
        ListOffCodesSortFactor.sort(sortFactor, userOffCodes);
        StringBuilder ans = new StringBuilder("Here are all of your Discount code:").append("\n");
        for (OffCode offCode : userOffCodes) {
            ans.append("OffCodeID: ").append(offCode.getOffCodeID()).append(", Percentage: ").append(offCode.getPercentage()).append("\n");
        }
        Server.setAnswer(ans.toString());
    }

    public void showAllProductsInCart(String username, String sortFactor) {
        Customer customer = (Customer) Storage.getAccountWithUsername(username);
        assert customer != null;
        Server.setAnswer(customer.getCart().toString());
    }

    public void addOrRemoveProductToCart(String type, String username, String productID, String salesmanID) {
        //assume productID ans salesmanID are valid
        StringBuilder ans = new StringBuilder("your product ");
        Customer customer = (Customer) Storage.getAccountWithUsername(username);
        assert customer != null;
        Cart customerCart = (customer.getCart());
        if (type.equalsIgnoreCase("add")) {
            ans.append("added to cart successfully");
            customerCart.addProductToCart(productID, salesmanID, customerCart.getCartID());
        } else if (type.equalsIgnoreCase("remove")) {
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

    //order part, still is here, we can make a new class for it [not recommended (:]

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

    //purchase part, still is here, we can make a new class for it :)

    //public void createLog();          ->      doesn't match this logic
    //public void setInfoOfLog();       ->      doesn't match this logic

    public void setOffCode(String username, String offCodeID) throws InvalidOffCodeException, CannotUseOffCodeException {
        StringBuilder ans = new StringBuilder("");
        OffCode offCode = OffCode.getOffCodeByID(offCodeID);
        if (offCodeID != null) {
            if (offCode == null) {
                throw new InvalidOffCodeException("off code isn't valid");
            } else if (!offCode.canCustomerUseItWithUsername(username)) {
                throw new CannotUseOffCodeException("error, you do not have permission to use this offCode.");
            }
        }
        Customer customer = (Customer) Storage.getAccountWithUsername(username);
        assert customer != null;
        Server.setAnswer("Final Price:" + "\n" + customer.getCart().getTotalPrice(offCodeID));
    }

    public void buy(String username, String offCodeID) throws NotEnoughCreditException {
        Customer customer = (Customer) Storage.getAccountWithUsername(username);
        assert customer != null;
        if (customer.getCart().buy(offCodeID)) {
            Server.setAnswer("successful, your purchase completed");
            BuyLog buyLog = new BuyLog(customer.getCart(), offCodeID);
            for (String productID : customer.getCart().getProductIDs().keySet()) {
                new SellLog(buyLog, productID, customer.getCart().getProductIDs().get(productID));
            }
        } else {
            throw new NotEnoughCreditException("you haven't got enough credit");
        }
    }
}