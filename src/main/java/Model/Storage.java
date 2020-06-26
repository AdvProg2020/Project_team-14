package Model;

import Model.Account.*;
import Model.Cart.Cart;
import Model.Category.Category;
import Model.Log.BuyLog;
import Model.Log.SellLog;
import Model.Off.OffCode;
import Model.Off.Sale;
import Model.Off.SpecialOffCode;
import Model.Product.Comment;
import Model.Product.Point;
import Model.Product.Product;
import Model.Request.Request;

import java.util.ArrayList;

public class Storage {
    public static ArrayList<Comment> allComments = new ArrayList<>();
    public static ArrayList<Point> allPoints = new ArrayList<>();
    private static ArrayList<Product> allProducts = new ArrayList<>();
    public static ArrayList<OffCode> allOffCodes = new ArrayList<>();
    public static ArrayList<Sale> allSales = new ArrayList<>();
    public static ArrayList<BuyLog> allBuyLogs = new ArrayList<>();
    private static ArrayList<Category> allCategories = new ArrayList<>();
    private static ArrayList<Account> allAccounts = new ArrayList<>();
    public static ArrayList<SpecialOffCode> allSpecialOffCodes = new ArrayList<>();
    public static ArrayList<Cart> allCarts = new ArrayList<>();
    private static ArrayList<Request> allRequests = new ArrayList<>();
    public static ArrayList<SellLog> allSellLogs = new ArrayList<>();

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

    public static ArrayList<Product> getAllProducts() {
        return allProducts;
    }

    public static ArrayList<Request> getAllRequests() {
        return allRequests;
    }

    public static ArrayList<Category> getAllCategories() {
        return allCategories;
    }

    public static boolean isThereRequestByID(String requestID) {
        return getRequestByID(requestID) != null;
    }

    public static Request getRequestByID(String requestID) {
        for (Request request : Storage.allRequests) {
            if (request.getRequestID().equals(requestID)) return request;
        }
        return null;
    }

    public static boolean isThereCategoryWithName(String categoryName) {
        return getCategoryByName(categoryName) != null;
    }

    public static Category getCategoryByName(String categoryName) {
        for (Category category : allCategories) {
            if (category.getCategoryName().equals(categoryName)) {
                return category;
            }
        }
        return null;
    }

    public static boolean isThereOffCodeWithID(String offCodeID) {
        return getOffCodeById(offCodeID) != null;
    }

    public static OffCode getOffCodeById (String offCodeID) {
        for (OffCode offCode : allOffCodes) {
            if (offCode.getOffCodeID().equals(offCodeID)) return offCode;
        }
        return null;
    }

    public static boolean isThereProductWithID(String productID) {
        return getProductById(productID) != null;
    }

    public static Product getProductById(String productID) {
        for (Product product : allProducts) {
            if (product.getProductID().equalsIgnoreCase(productID)) return product;
        }
        return null;
    }

    public static Sale getSaleById(String saleID) {
        for (Sale sale : allSales) {
            if (sale.getSaleID().equals(saleID)) return sale;
        }
        return null;
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

    public static ArrayList<Product> getAllProductOfSeller(String salesmanID, int type) {
        if (salesmanID == null) return allProducts;
        if (salesmanID.equals("")) return allProducts;
        ArrayList<Product> ans = new ArrayList<>();
        boolean wantAll = type != 1;
        for (Product product : allProducts) {
            if (product.doesSalesmanSellProductWithUsername(salesmanID) | wantAll) ans.add(product);
        }
        return ans;
    }

    public static ArrayList<Sale> getAllSalesByUsername(String username) {
        Account account = getAccountWithUsername(username);
        if (account.getRole().equals(Role.BOSS)) {
            return allSales;
        } else {
            ArrayList<Sale> wantedSale = new ArrayList<>();
            for (Sale sale : allSales) {
                if (sale.getSalesmanID().equals(username)) wantedSale.add(sale);
            }
            return wantedSale;
        }
    }

}
