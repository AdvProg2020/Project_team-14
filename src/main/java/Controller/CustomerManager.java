package Controller;

import Model.Account.Customer;
import Model.Cart.Cart;
import Model.Off.OffCode;
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
        StringBuilder ans = new StringBuilder("");
        for (OffCode offCode : userOffCodes) {
            ans.append(offCode.toString());
        }
        Server.setAnswer(ans.toString());
    }

    public void showAllProductsInCart (String username, String sortFactor) {
        Server.setAnswer(((Customer)Storage.getAccountWithUsername(username)).getCart().toString());
    }

    public void showSingleProductInCart(String username, String productID, String salesmanID) {
        //
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
        Server.setAnswer("" + ((Customer) Storage.getAccountWithUsername(username)).getCart().getTotalPrice(null));
    }

}