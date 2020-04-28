package ModelTest;

import ModelTest.Account.Account;
import ModelTest.Account.Boss;
import ModelTest.Account.Customer;
import ModelTest.Account.Salesman;
import ModelTest.Category.Category;
import ModelTest.Log.BuyLog;
import ModelTest.Off.OffCode;
import ModelTest.Off.Sale;
import ModelTest.Off.SpecialOffCode;
import ModelTest.Product.Comment;
import ModelTest.Product.Point;
import ModelTest.Product.Product;

import java.util.ArrayList;

public class Storage {
    public static ArrayList<Comment> allComments = new ArrayList<>();
    public static ArrayList<Point> allPoints = new ArrayList<>();
    public static ArrayList<Product> allProducts = new ArrayList<>();
    public static ArrayList<OffCode> allOffCodes = new ArrayList<>();
    public static ArrayList<Sale> allSales = new ArrayList<>();
    public static ArrayList<BuyLog> allBuyLogs = new ArrayList<>();
    public static ArrayList<Category> allCategories = new ArrayList<>();
    public static ArrayList<Account> allAccounts = new ArrayList<>();
    public static ArrayList<SpecialOffCode> allSpecialOffCodes = new ArrayList<>();

    public static boolean isThereAccountWithUsername(String username) {
        return getAccountWithUsername(username) != null;
    }

    public static Account getAccountWithUsername(String username) {
        for (Account account : allAccounts) {
            if (account.getUsername().equalsIgnoreCase(username)) {
                return account;
            }
        }
        return null;
    }

    public static ArrayList<Account> getAllAccounts() {
        return allAccounts;
    }

    public static ArrayList<Customer> getAllCustomers() {
        ArrayList<Customer> arrayList = new ArrayList<>();
        for (Account account : allAccounts) {
            if (account instanceof Customer) {
                arrayList.add((Customer) account);
            }
        }
        return arrayList;
    }

    public static ArrayList<Boss> getAllBosses() {
        ArrayList<Boss> arrayList = new ArrayList<>();
        for (Account account : allAccounts) {
            if (account instanceof Boss) {
                arrayList.add((Boss) account);
            }
        }
        return arrayList;
    }

    public static ArrayList<Salesman> getAllSalesmen() {
        ArrayList<Salesman> arrayList = new ArrayList<>();
        for (Account account : allAccounts) {
            if (account instanceof Salesman) {
                arrayList.add((Salesman) account);
            }
        }
        return arrayList;
    }
}
