package Model.Cart;

import Model.Account.Customer;
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

    public static void updateUsernameCart(String customerUser, String cartID) {
        Cart fromCart = getCartWithID(cartID);
        Cart toCart = getCartBasedOnUsername(customerUser);
        toCart.clearCart();
        assert fromCart != null;
        toCart.productIDs = fromCart.productIDs;
        Storage.allCarts.remove(fromCart);
    }

    public String getCartID() {
        return cartID;
    }

    public void clearCart() {
        productIDs.clear();
    }

    public void addProductToCart(String productID, String salesmanID, String cartID) {
        Cart cart = getCartWithID(cartID);
        assert cart != null;
        cart.addProductToCart(productID, salesmanID);
    }

    private void addProductToCart(String productID, String salesmanID) {
        productIDs.put(productID, salesmanID);
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
        result.append("Total price WithOut: ").append(this.getTotalPrice());
        return result.toString();
    }
}

