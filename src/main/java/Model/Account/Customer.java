package Model.Account;

import Model.Cart.Cart;
import Model.Off.Off;
import Model.Off.OffCode;
import Model.Storage;

import java.io.*;
import java.util.HashMap;
import java.util.Random;

import static Model.Storage.*;

public class Customer extends Account implements Serializable {
    private int credit;
    private Cart cart;
    private HashMap<String, Integer> customerOffCodes;

    public Customer(String username, String password, String firstName, String secondName, String Email, String telephone,
                    String role,int credit) {
        super(username, password, firstName, secondName, Email, telephone, role);
        this.credit = credit;
        customerOffCodes = new HashMap<>();
    }

    public Customer(String username, String password, String firstName, String secondName, String Email, String telephone,
                    String role, int credit, HashMap<String, String> productsAlreadyInCart) {
        super(username, password, firstName, secondName, Email, telephone, role);
        this.credit = credit;
        /*cart = new Cart(username);
        if (productsAlreadyInCart != null) {
            for (String productID : productsAlreadyInCart.keySet()) {
                cart.addProductToCart(productID, productsAlreadyInCart.get(productID), cart.getCartID());
            }
        }*/
        customerOffCodes = new HashMap<>();
    }

    public int getCredit() {
        return credit;
    }

    public void setCredit(int credit) {
        this.credit = credit;
    }

    public boolean isCreditEnoughAccordingToCartWithOffCode(String offCode) {
        return (credit > cart.getTotalPrice(offCode));
    }

    public Cart getCart() {
        return cart;
    }

    // public static boolean isCreditEnoughAccordingToCart() {return false;}

    public static boolean isThereCustomerWithUsername(String username) {
        for (Customer customer : getAllCustomers()) {
            if (customer.getUsername().equalsIgnoreCase(username)) {
                return true;
            }
        }
        return false;
    }

    //we get random user for the special off code class

    public static String getRandomUsername() {
        Random random = new Random();
        if (getAllCustomers().size() == 0) {
            return null;
        } else {
            int rand = random.nextInt(getAllCustomers().size());
            return getAllCustomers().get(rand).getUsername();
        }
    }

    /*public boolean isCartEmpty() {
        return cart.isCartEmpty();
    }*/

    public HashMap<String, Integer> getCustomerOffCodes() {
        return customerOffCodes;
    }

    public void useOffCode(String offCodeID) {
        if (offCodeID != null) {
            int count = customerOffCodes.get(offCodeID);
            customerOffCodes.replace(offCodeID, count, count - 1);
            if (customerOffCodes.get(offCodeID) == 0) {
                customerOffCodes.remove(offCodeID);
            }
        }
    }

    public void addOffCode(OffCode offCode) {
        customerOffCodes.put(offCode.getOffCodeID(), offCode.getNumberOfTimesCanBeUsed());
    }

    public String getOffCodeInfo(String offCodeID) {
        /*if (!Storage.isThereOffCodeWithID(offCodeID)) {                           **redundant
            return "ERROR: there isn't exist such a offCode";
        }*/
        OffCode offCode = Storage.getOffCodeById(offCodeID);
        /*if (!offCode.canCustomerUseItWithUsername(this.getUsername())) {          **redundant
            return "ERROR: you don't have access to this offCode";
        }*/
        if (customerOffCodes.containsKey(offCodeID)) {
            return offCode.toString() + "Remaining time you can use it: " + customerOffCodes.get(offCodeID);
        }
        return offCode.toString() + "Remaining time you can use it: " + 0;

    }

    @Override
    public String toString() {
        return super.toString() + "Credit: " + this.getCredit() + "\n";
    }

}
