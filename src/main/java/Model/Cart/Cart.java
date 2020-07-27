package Model.Cart;

import Controller.CreditController;
import Model.Account.Customer;
import Model.Account.Salesman;
import Model.Log.BuyLog;
import Model.Off.OffCode;
import Model.Off.Sale;
import Model.Product.Product;
import Model.RandomString;
import Model.Storage;
import org.javatuples.Triplet;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class Cart implements Serializable {

    //    private HashMap<String, String> productIDs = new HashMap<>();//when logs updated with Triplet, we delete this
    private String username;
    private String cartID;

    private ArrayList<Triplet<String, String, Integer>> allItems = new ArrayList<>();

    public Cart(String username) {
        this.username = username;
        Customer customer = (Customer) Storage.getAccountWithUsername(username);
        customer.setCart(this);
        cartID = RandomString.createID("cartID");
        Storage.allCarts.add(this);
    }

    //if it return true it means that the credit was enough and the purchase process is done
    //otherwise it return false which means credit isn't enough
    //the buy log will be created and the money is received from the customer and the money is given to salesman
    //in sell log which is call in constructor of buy log
    //Buy --> creating BuyLog --> creating SellLog --> giving the salesman their money

    //updated with Triplet(nothing to do)  ******** note: assume that if we don't use offCode, String offCode will be null not "" !!!!! ******
    public boolean buy(String offCode) {
        Customer customer = (Customer) Storage.getAccountWithUsername(username);
        assert customer != null;
        if (customer.isCreditEnoughAccordingToCartWithOffCode(offCode)) {
            new BuyLog(this, offCode);
            customer.setCredit(customer.getCredit() - getTotalPrice(offCode));
            customer.useOffCode(offCode);
            doBuyProductThings();
            sendMoneyToStore(offCode);
            clearCart();
            return true;
        } else {
            return false;
        }
    }

    private void doBuyProductThings() {
        int wagePercentage = CreditController.creditController.getWagePercentage();
        for (Triplet<String, String, Integer> item : allItems) {
            Product product = Storage.getProductById(item.getValue0());
            product.decreaseProductRemaining(item.getValue1(), item.getValue2());
            int productCost = Sale.getPriceAfterSale(item.getValue0(), item.getValue1());
            Salesman salesman = (Salesman) Storage.getAccountWithUsername(item.getValue1());
            salesman.setCredit(salesman.getCredit() + productCost * wagePercentage / 100);
        }
    }

    private void sendMoneyToStore(String offCodeID) {
        int totalPrice = getTotalPrice(offCodeID);
        //...
    }

    //updated with Triplet
    public void clearCart() {
        for (Triplet<String, String, Integer> item : allItems) {
            Storage.getProductById(item.getValue0()).decreaseProductRemaining(item.getValue1(), item.getValue2());
        }
        allItems.clear();
    }


    public static Cart getCartWithID(String cartID) {
        for (Cart cart : Storage.allCarts) {
            if (cart.getCartID().equals(cartID)) {
                return cart;
            }
        }
        return null;
    }

    public static Cart getCartBasedOnUsername(String username) {
        assert username != null;
        Customer customer = (Customer) Storage.getAccountWithUsername(username);
        assert customer != null;
        return customer.getCart();
    }


    //updated with Triplet
    public boolean addProductToCart(String productID, String salesmanID, int count) {
        Triplet<String, String, Integer> item = getItem(productID, salesmanID);
        if (item != null) {
            if (!Storage.getProductById(productID).isAvailableBySalesmanWithUsername(salesmanID, getItemCount(productID, salesmanID) + count)) {
                return false;
            }
            allItems.remove(item);
            allItems.add(item.setAt2(item.getValue2() + 1));//Triplet is immutable :|
            return true;
        }
        if (Storage.getProductById(productID).isAvailableBySalesmanWithUsername(salesmanID, count)) {
            allItems.add(new Triplet<>(productID, salesmanID, 1));
            return true;
        }
        return false;
    }

    //updated with Triplet
    public boolean removeProductFromCart(String productID, String salesmanID) {
        Triplet<String, String, Integer> item = getItem(productID, salesmanID);
        if (item == null) {
            return false;
        }
        allItems.remove(item);
        if (item.getValue2() - 1 != 0) allItems.add(item.setAt2(item.getValue2() - 1));
        return true;
    }

    //add for Triplet
    private Triplet<String, String, Integer> getItem(String productID, String salesmanID) {
        for (Triplet<String, String, Integer> item : allItems) {
            if (item.getValue0().equals(productID) & item.getValue1().equals(salesmanID)) return item;
        }
        return null;
    }

    //add for Triplet
    private int getItemCount(String productID, String salesmanID) {
        return getItem(productID, salesmanID).getValue2();
    }

    //add for Triplet
    public ArrayList<Triplet<String, String, Integer>> getAllItems() {
        return allItems;
    }

    //updated with Triplet
    public boolean isCartEmpty() {
        return allItems.isEmpty();
    }

//    public HashMap<String, String> getProductIDs() {
//        return productIDs;
//    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String newUsername) {
        this.username = newUsername;
    }

    public String getCartID() {
        return cartID;
    }

    //----[ new ]---updated with Triplet
    public HashMap<String, Integer> getPrices() {
        HashMap<String, Integer> prices = new HashMap<>();
        for (Triplet<String, String, Integer> item : allItems) {
            Product product = Storage.getProductById(item.getValue0());
            assert product != null;
            prices.put(item.getValue0(), product.getPriceBySalesmanID(item.getValue1()));
        }
        return prices;
    }

    //----[ new ]-----updated with Triplet
    public HashMap<String, Integer> getPricesAfterSale() {
        HashMap<String, Integer> prices = new HashMap<>();
        for (Triplet<String, String, Integer> item : allItems) {
            Product product = Storage.getProductById(item.getValue0());
            assert product != null;
            prices.put(item.getValue0(), Sale.getPriceAfterSale(item.getValue0(), item.getValue1()));
        }
        return prices;
    }

    //updated with Triplet(nothing to do)
    public int getTotalPrice(String offCodeID) {
        if (offCodeID == null) {
            return getTotalPrice();
        } else {
            return OffCode.getFinalPrice(getTotalPrice(), offCodeID);
        }
    }

    //updated with Triplet
    private int getTotalPrice() {
        int result = 0;
        for (Triplet<String, String, Integer> item : allItems) {
            result += Sale.getPriceAfterSale(item.getValue0(), item.getValue1()) * item.getValue2();
        }
        return result;
    }

    //updated with Triplet
    private String toStringSingleItem(Triplet<String, String, Integer> item) {
        Product product = Storage.getProductById(item.getValue0());
        assert product != null;
        String result = "| Product: " + product.getName() + " |---| ";
        result += "Salesman: " + item.getValue1() + " |---| ";
        result += "Count: " + item.getValue2() + " |---| ";
        result += "Price Per Unit: " + product.getPriceBySalesmanID(item.getValue1()) + " |---| ";
        result += "Price after sale Per Unit: " + Sale.getPriceAfterSale(item.getValue0(), item.getValue1()) + " |";
        return result;
    }

    //updated with Triplet
    public String toString() {
        StringBuilder result = new StringBuilder("Here are all of your products in cart:");
        for (Triplet<String, String, Integer> item : allItems) {
            result.append("\n").append(toStringSingleItem(item)).append("\n-----------------------------------------");
        }
        result.append("Total price WithOut Using OffCode: ").append(this.getTotalPrice());
        return result.toString();
    }
}

