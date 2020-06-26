package Model.Product;

import Model.Confirmation;
import Model.RandomString;
import Model.Storage;
import org.omg.Messaging.SYNC_WITH_TRANSPORT;

import java.io.Serializable;
import java.util.*;

public class Product implements Serializable {
    private ArrayList<String> salesmanIDs = new ArrayList<>();
    private String productID;
    private String name;
    private String brand;
    private String description;
    private int seenCount;
    private String picPath;
    private String categoryName;
    private String videoPath;

    public String getPicPath() {
        return picPath;
    }

    public void setPicPath(String picPath) {
        this.picPath = picPath;
    }

    public String getVideoPath() {
        return videoPath;
    }

    public void setVideoPath(String videoPath) {
        this.videoPath = videoPath;
    }

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

    public boolean is_on_sale() {
        for (String username : isOnSale.keySet()) {
            if (isOnSale.get(username) && !hasBeenDeleted.get(username) && remainder.get(username) > 0) {
                return true;
            }
        }
        return false;
    }

    public boolean isFinished() {
        for (String username : isOnSale.keySet()) {
            if (!hasBeenDeleted.get(username) && remainder.get(username) > 0) {
                return false;
            }
        }
        return true;
    }


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
        this.picPath = "none";
        Storage.getAllProducts().add(this);
    }

    public void updateUserNames(String oldUsername, String newUsername) {
        ListIterator<String> salesmanIDIterator = salesmanIDs.listIterator();
        while (salesmanIDIterator.hasNext()) {
            if (salesmanIDIterator.next().equals(oldUsername)) {
                salesmanIDIterator.remove();
                salesmanIDIterator.add(newUsername);
            }
        }

        updateUsernameInHashMap(oldUsername, newUsername, isOnSale);
        updateUsernameInHashMap(oldUsername, newUsername, hasBeenDeleted);
        updateUsernameInHashMap(oldUsername, newUsername, confirmationState);
        updateUsernameInHashMap(oldUsername, newUsername, remainder);
        updateUsernameInHashMap(oldUsername, newUsername, price);
    }

    private void updateUsernameInHashMap(String oldUsername, String newUsername, HashMap hashMap) {
        if (hashMap.containsKey(oldUsername)) {
            Object value = hashMap.remove(oldUsername);
            hashMap.put(newUsername, value);
        }
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

    public ArrayList<String> getSalesmanIDs() {
        return salesmanIDs;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setRemainderForSalesman(int remainder, String salesmanUser) {
        this.remainder.remove(salesmanUser);
        this.remainder.put(salesmanUser, remainder);
    }

    public int getRemainderForSalesman(String salesmanUser) {
        return this.remainder.get(salesmanUser);
    }

    public void setPriceForSalesman(int price, String salesmanUser) {
        this.price.remove(salesmanUser);
        this.price.put(salesmanUser, price);
    }

    public void setSeenCount(int seenCount) {
        this.seenCount = seenCount;
    }

    public void deleteForSalesman(String salesmanUser) {
        hasBeenDeleted.remove(salesmanUser);
        hasBeenDeleted.put(salesmanUser, true);
    }

    public String getBrand() {
        return brand;
    }

    public void setIsOnSale(String salesmanID, boolean isOnSale) {
        this.isOnSale.remove(salesmanID);
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
        return Objects.requireNonNull(Storage.getProductById(productID)).getName();
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
        isConfirmedForSalesmanWithUsername(username);
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
        this.confirmationState.remove(salesmanID);
        this.confirmationState.put(salesmanID, confirmationState);
    }

    public void decreaseProductRemaining(String salesmanID, int wantedAmount) {
        remainder.replace(salesmanID, remainder.get(salesmanID) - wantedAmount);
    }

    public Confirmation getOverallConfirmation() {
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

    public String toStringForAddToProduct(String username) {
        return "Product ID: " + this.productID + " " +
                "Name: " + this.name + " " +
                "Offered Price: " + this.price.get(username) + " " +
                "Average Point: " + this.getAveragePoint() + " " +
                "Confirmation State: " + this.confirmationState.get(username);
    }

    public String toStringForBoss() {
        return "Product ID: " + this.productID + " " +
                "Name: " + this.name + " " +
                "Price: " + this.getMinimumPrice() + " " +
                "Average Point: " + this.getAveragePoint() + " " +
                "Confirmation State: " + this.getOverallConfirmation();
    }

    public String toStringForBossView() {
        StringBuilder result = new StringBuilder();
        result.append("Product ID: ").append(this.productID).append("\n");
        result.append("Name: ").append(this.name).append("\n");
        result.append("Brand: ").append(this.brand).append("\n");
        result.append("Description: ").append(this.description).append("\n");
        /*result.append("Sellers: " + "\n");
        for (String salesmanID : salesmanIDs) {
            if (!doesSalesmanSellProductWithUsername(salesmanID) || !isAvailableBySalesmanWithUsername(salesmanID, 1)) {
                continue;
            }
            result.append("Salesman: ").append(salesmanID);
            result.append(" Price: ").append(price.get(salesmanID)).append(" Remainder: ").
                    append(remainder.get(salesmanID)).append("\n");
        }
        result.append("Seen Count: ").append(this.seenCount).append("\n");
        result.append("Average Point").append(this.getAveragePoint()).append("\n");*/
        return result.toString();
    }

    public String toStringForCustomerView() {
        return "Product ID: " + this.productID + "\n" +
                "Name: " + this.name + "\n" +
                "Brand: " + this.brand + "\n" +
                "Description: " + this.description + "\n" +
                "Price: " + getMinimumPrice() + "\n";
    }

    public String toStringForSalesmanView(String salesmanUser) {
        StringBuilder result = new StringBuilder();
        result.append("Product ID: ").append(this.productID).append("\n");
        result.append("Name: ").append(this.name).append("\n");
        result.append("Brand: ").append(this.brand).append("\n");
        result.append("Description: ").append(this.description).append("\n");
        if (salesmanIDs.contains(salesmanUser)) {
            if (!hasBeenDeleted.get(salesmanUser)) {
                result.append("Confirmation state for you: ").append(this.confirmationState.get(salesmanUser)).append("\n");
                result.append("Your Price: ").append(price.get(salesmanUser)).append("\n");
                result.append("Your remainder: ").append(remainder.get(salesmanUser)).append("\n");
                if (isOnSale.get(salesmanUser)) {
                    result.append("The product is on sale").append("\n");
                } else {
                    result.append("The product isn't on sale").append("\n");
                }
            } else {
                result.append("you have been deleted from selling this product\n");
                result.append("Price: ").append(getMinimumPrice()).append("\n");
            }
        } else {
            result.append("Price: ").append(getMinimumPrice()).append("\n");
        }
        return result.toString();
    }

    public String createID() {
        return RandomString.createID("Product");
    }
}
