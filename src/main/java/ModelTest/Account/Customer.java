package ModelTest.Account;

import java.io.*;
import java.util.ArrayList;
import java.util.Random;

import static ModelTest.Storage.*;

public class Customer extends Account implements Serializable {
    private int credit;
    private ArrayList<String> cartItems = new ArrayList<>();    //only contains productIDs

    public Customer(String username, String password, String firstName, String secondName, String Email, String telephone, String role, int credit) {
        super(username, password, firstName, secondName, Email, telephone, role);
        this.credit = credit;
        allAccounts.add(this);
    }

    public int getCredit() {
        return credit;
    }

    public void setCredit(int credit) {
        this.credit = credit;
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
        int rand = random.nextInt(getAllCustomers().size());
        return getAllCustomers().get(rand).getUsername();
    }

    @Override
    public String toString() {
        return super.toString() + "Credit: " + this.getCredit() + "\n";
    }

}
