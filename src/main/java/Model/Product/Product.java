package Model.Product;

import Model.Category.Category;
import Model.Confirmation;
import Model.RandomString;
import Model.Storage;

import java.io.Serializable;
import java.util.*;

import static Model.Storage.*;

public class Product implements Serializable {
    private ArrayList<String> salesmanIDs = new ArrayList<>();
    private String productID;
    private String name;
    private String brand;
    private String description;
    private int seenCount;
    private String categoryName;

    //the first argument is salesmanID and the second one is whether is's on sale by that salesman
    private HashMap<String, Boolean> isOnSale = new HashMap<>();
    //the first argument is salesmanID and the second onw is whether it has been deleted by the salesman
    private HashMap<String, Boolean> hasBeenDeleted = new HashMap<>();
    //the first argument is salesmanID and the second onw is whether it has been confirmed by the boss for the salesman
    private HashMap<String, Confirmation> confirmationState = new HashMap<>();
    //the first argument is salesmanID and the second onw is the remainder number of product by the salesman
    private HashMap<String, Integer> remainder = new HashMap<>();
    //the first argument is salesmanID and the second onw is the price by salesman
    private HashMap<String, Integer> price = new HashMap<>();


    public Product(String name, String salesmanID, String brand, String description, int price, int remainder) {
        this.name = name;
        this.brand = brand;
        this.description = description;
        this.productID = createID();
        this.seenCount = 0;
        this.categoryName = null;
        this.remainder.put(salesmanID, remainder);
        this.confirmationState.put(salesmanID, Confirmation.CHECKING);
        this.hasBeenDeleted.put(salesmanID, false);
        this.isOnSale.put(salesmanID, false);
        this.salesmanIDs.add(salesmanID);
        this.price.put(salesmanID, price);
        Storage.getAllProducts().add(this);
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public int getSeenCount() {
        return seenCount;
    }

    public void increaseSeenCount() {
        seenCount++;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setRemainderForSalesman(int remainder, String salesmanUser) {
        this.remainder.put(salesmanUser, remainder);
    }

    public void setPriceForSalesman(int price, String salesmanUser) {
        this.price.put(salesmanUser, price);
    }

    public void deleteForSalesman(String salesmanUser) {
        hasBeenDeleted.put(salesmanUser, true);
    }

    public String getBrand() {
        return brand;
    }

    public void setIsOnSale(String salesmanID, boolean isOnSale) {
        this.isOnSale.put(salesmanID, isOnSale);
    }

    public String getName() {
        return name;
    }

    public int getPriceBySalesmanID(String salesmanID) {
        return price.get(salesmanID);
    }

    public int getMinimumPrice() {
        int result = 1000000000;
        for (String salesmanID : salesmanIDs) {
            if (result > getPriceBySalesmanID(salesmanID)) {
                result = getPriceBySalesmanID(salesmanID);
            }
        }
        return result;
    }

    public static String getNameByID(String productID) {
        return Objects.requireNonNull(Product.getProductWithID(productID)).getName();
    }

    public String getProductID() {
        return productID;
    }

    public double getAveragePoint() {
        return Point.getAveragePointForProduct(this.productID);
    }

    public int getNumberOfPeopleVoted() {
        return Point.getNumberOfPeopleVotedForProduct(this.productID);
    }

    public boolean isThereAnyPoint() {
        return Point.isTherePointForProduct(this.productID);
    }

    public boolean isThereComment() {
        return Comment.isThereCommentForProduct(this.productID);
    }

    public ArrayList<String> getComments() {
        return Comment.getCommentsForProductWithID(this.productID);
    }

    public void addSalesman(String salesmanID, int remainder, int price) {
        Set<String> set = new HashSet<>(salesmanIDs);
        set.add(salesmanID);
        this.salesmanIDs.clear();
        this.salesmanIDs.addAll(set);
        salesmanIDs.sort(String::compareTo);
        this.remainder.put(salesmanID, remainder);
        this.confirmationState.put(salesmanID, Confirmation.CHECKING);
        this.hasBeenDeleted.put(salesmanID, false);
        this.isOnSale.put(salesmanID, false);
        this.price.put(salesmanID, price);
    }

    private boolean isConfirmedForSalesmanWithUsername(String username) {
        return this.confirmationState.get(username).equals(Confirmation.ACCEPTED);
    }

    // it checks that the salesman is among seller of product and
    //the salesman is confirmed by manager and also
    //checks that the salesman hasn't deleted the product
    public boolean doesSalesmanSellProductWithUsername(String username) {
        return (salesmanIDs.contains(username) && !hasBeenDeleted.get(username) && isConfirmedForSalesmanWithUsername(username));
    }

    //we should first check the method doesSalesmanSellProductWithUsername() and then
    //call method below to make sure that the salesman sell it first
    //then checking whether they have remainder or not

    public boolean isAvailableBySalesmanWithUsername(String username, int wantedAmount) {
        return remainder.get(username) >= 1 & remainder.get(username) >= wantedAmount;
    }

    public ArrayList<String> getSeller() {
        ArrayList<String> sellers = new ArrayList<>();
        for (String salesmanID : confirmationState.keySet()) {
            if (confirmationState.get(salesmanID).equals(Confirmation.ACCEPTED)) sellers.add(salesmanID);
        }
        return sellers;
    }

    public void setConfirmationState(String salesmanID, Confirmation confirmationState) {
        this.confirmationState.put(salesmanID, confirmationState);
    }

    public void decreaseProductRemaining(String salesmanID, int wantedAmount) {
        remainder.replace(salesmanID, remainder.get(salesmanID) - wantedAmount);
    }

    public static boolean isThereProductWithID(String productID) {
        return getProductWithID(productID) != null;
    }

    public static Product getProductWithID(String productID) {
        for (Product product : Storage.getAllProducts()) {
            if (product.getProductID().equals(productID)) {
                return product;
            }
        }
        return null;
    }

    private Confirmation getOverallConfirmation() {
        for (String salesmanID : this.salesmanIDs) {
            if (confirmationState.get(salesmanID).equals(Confirmation.ACCEPTED)) {
                return Confirmation.ACCEPTED;
            }
        }
        for (String salesmanID : this.salesmanIDs) {
            if (confirmationState.get(salesmanID).equals(Confirmation.CHECKING)) {
                return Confirmation.CHECKING;
            }
        }
        return Confirmation.DENIED;
    }

    public String toStringForBoss() {
        return "Product ID: " + this.productID + "\n" +
                "Name: " + this.name + "\n" +
                "Price: " + this.getMinimumPrice() + "\n" +
                "Average Point: " + this.getAveragePoint() + "\n" +
                "Confirmation State: " + this.getOverallConfirmation();
    }

    public String toStringForBossView() {
        return "Name: " + this.name + "\n" +
                "Brand: " + this.brand + "\n" +
                "Description: " + this.description + "\n" +
                "Seen Count: " + this.seenCount + "\n" +
                "Price: " + this.getMinimumPrice() + "\n" +
                "Average Point: " + this.getAveragePoint() + "\n";
    }

    public String toStringForCustomerView() {
        StringBuilder result = new StringBuilder();
        result.append("Name: ").append(this.name).append("\n");
        result.append("Brand: ").append(this.brand).append("\n");
        result.append("Description: ").append(this.description).append("\n");
        result.append("Sellers: " + "\n");
        for (String salesmanID : salesmanIDs) {
            if (!doesSalesmanSellProductWithUsername(salesmanID) || !isAvailableBySalesmanWithUsername(salesmanID, 1)) {
                continue;
            }
            result.append("Salesman: ").append(salesmanID);
            result.append(" Price: ").append(price.get(salesmanID)).append("\n");
        }
        return result.toString();
    }

    public String toStringForSalesmanView(String salesmanUser) {
        StringBuilder result = new StringBuilder();
        result.append("Name: ").append(this.name).append("\n");
        result.append("Brand: ").append(this.brand).append("\n");
        result.append("Description: ").append(this.description).append("\n");
        result.append("Sellers: " + "\n");
        for (String salesmanID : salesmanIDs) {
            if (!doesSalesmanSellProductWithUsername(salesmanID) || !isAvailableBySalesmanWithUsername(salesmanID, 1)) {
                continue;
            }
            result.append("Salesman: ").append(salesmanID);
            result.append(" Price: ").append(price.get(salesmanID)).append("\n");
        }
        result.append("Confirmation state for you: ").append(confirmationState.get(salesmanUser)).append("\n");
        result.append("Your remainder: ").append(remainder.get(salesmanUser)).append("\n");
        if (isOnSale.get(salesmanUser)) {
            result.append("The product is on sale").append("\n");
        } else {
            result.append("The product isn't on sale").append("\n");
        }
        return result.toString();
    }

    public String createID() {
        return RandomString.createID("Product");
    }
}
