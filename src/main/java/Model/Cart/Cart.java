package Model.Cart;

import Model.Account.Customer;
import Model.Log.BuyLog;
import Model.Off.OffCode;
import Model.Off.Sale;
import Model.Product.Product;
import Model.RandomString;
import Model.Storage;

import java.io.Serializable;
import java.util.HashMap;

public class Cart implements Serializable {

    private HashMap<String, String> productIDs = new HashMap<>();
    private String username;
    private String cartID;

    public Cart(String username) {
        this.username = username;
        cartID = RandomString.createID("cartID");
        Storage.allCarts.add(this);
    }

    //if it return true it means that the credit was enough and the purchase process is done
    //otherwise it return false which means credit isn't enough
    //the buy log will be created and the money is received from the customer and the money is given to salesman
    //in sell log which is call in constructor of buy log
    //Buy --> creating BuyLog --> creating SellLog --> giving the salesman their money

    public boolean buy(String offCode) {
        Customer customer = (Customer) Storage.getAccountWithUsername(username);
        assert customer != null;
        if(customer.isCreditEnoughAccordingToCartWithOffCode(offCode)) {
            new BuyLog(this, offCode);
            customer.setCredit(customer.getCredit() - getTotalPrice(offCode));
            customer.useOffCode(offCode);
            clearCart();
            return true;
        } else {
            return false;
        }
    }

    public boolean isAlreadyInCart(String productID) {
        return this.productIDs.containsKey(productID);
    }

    public HashMap<String, String> getProductIDs() {
        return productIDs;
    }

    public String getUsername() {
        return username;
    }

    public String getCartID() {
        return cartID;
    }

    public void clearCart() {
        for (String productID : productIDs.keySet()) {
            Product.getProductWithID(productID).decreaseProductRemaining(productIDs.get(productID));
        }
        productIDs.clear();
    }

    public HashMap<String, Integer> getPrices() {
        HashMap<String, Integer> prices = new HashMap<>();
        for (String productID : productIDs.keySet()) {
            Product product = Product.getProductWithID(productID);
            assert product != null;
            prices.put(productID, product.getPriceBySalesmanID(productIDs.get(productID)));
        }
        return prices;
    }

    public HashMap<String, Integer> getPricesAfterSale() {
        HashMap<String, Integer> prices = new HashMap<>();
        for (String productID : productIDs.keySet()) {
            Product product = Product.getProductWithID(productID);
            assert product != null;
            prices.put(productID, Sale.getPriceAfterSale(productID, productIDs.get(productID)));
        }
        return prices;
    }

    private int getTotalPrice() {
        int result = 0;
        for (String productID : productIDs.keySet()) {
            result += Sale.getPriceAfterSale(productID, productIDs.get(productID));
        }
        return result;
    }

    public int getTotalPrice(String offCodeID) {
        if (offCodeID == null) {
            return getTotalPrice();
        } else {
            return OffCode.getFinalPrice(getTotalPrice(), offCodeID);
        }
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

    public boolean addProductToCart(String productID, String salesmanID, String cartID) {
        Cart cart = getCartWithID(cartID);
        assert cart != null;
        if (Product.getProductWithID(productID).isAvailableBySalesmanWithUsername(salesmanID)) {
            cart.addProductToCart(productID, salesmanID);
            return true;
        }
        return false;
    }

    private void addProductToCart(String productID, String salesmanID) {
        productIDs.put(productID, salesmanID);
    }

    public void removeProductFromCart(String productID) {
        productIDs.remove(productID);
    }

    private String toStringSingleProduct(String productID, String salesmanID) {
        Product product = Product.getProductWithID(productID);
        assert product != null;
        String result = "Product: " + product.getName() + "\n";
        result += "Salesman: " + salesmanID + "\n";
        result += "Price: " + product.getPriceBySalesmanID(salesmanID) + "\n";
        result += "Price after sale: " + Sale.getPriceAfterSale(productID, salesmanID) + "\n";
        return result;
    }

    public String toString() {
        StringBuilder result = new StringBuilder();
        for (String productID : productIDs.keySet()) {
            result.append(toStringSingleProduct(productID, productIDs.get(productID)));
        }
        result.append("---------------------------------");
        result.append("Total price WithOut Using OffCode: ").append(this.getTotalPrice());
        return result.toString();
    }
}

