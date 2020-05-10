package Model.Off;

import Model.Confirmation;
import Model.Product.Product;
import Model.RandomString;
import Model.Storage;

import java.io.Serializable;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Objects;

import static Model.Storage.*;

public class Sale extends Off implements Serializable {
    private ArrayList<String> productIDs;
    private String saleID;
    private String salesmanID;
    private Confirmation confirmationState;

    public Sale(String start, String end, int percentage, String salesmanID) throws ParseException {
        super(start, end, percentage);
        this.saleID = RandomString.createID("Sale");
        this.salesmanID = salesmanID;
        productIDs = new ArrayList<>();
        confirmationState = Confirmation.CHECKING;
        allSales.add(this);
    }

    public String getSalesmanID() {
        return salesmanID;
    }

    public static ArrayList<Sale> getAllSaleBySalesmanID(String salesmanID) {
        ArrayList<Sale> salesmanSale = new ArrayList<>();
        for (Sale sale : allSales) {
            if (sale.getSaleID().equals(salesmanID)) salesmanSale.add(sale);
        }
        return salesmanSale;
    }

    public void setConfirmationState(Confirmation confirmationState) {
        this.confirmationState = confirmationState;
    }

    public boolean isConfirmed() {
        return confirmationState.equals(Confirmation.ACCEPTED);
    }

    public boolean isChecking() {
        return confirmationState.equals(Confirmation.CHECKING);
    }

    public boolean isAuthentic() {
        return (isConfirmed() && isAuthenticAccordingToDate());
    }

    public boolean doesContainProduct(String productID) {
        return productIDs.contains(productID);
    }

    public String getSaleID() {
        return saleID;
    }

    public ArrayList<String> listProducts() {
        return productIDs;
    }

    public void addToProducts(String productID) {
        productIDs.add(productID);
    }

    public void removeFromProducts(String productID) {
        productIDs.remove(productID);
    }

    // the method below return the best price of a product with specific salesman
    //after all possible sales

    public static int getPriceAfterSale(String productID, String salesmanID) {
        int price = Objects.requireNonNull(Product.getProductWithID(productID)).getPriceBySalesmanID(salesmanID);
        if (isProductInSaleWithID(productID, salesmanID)) {
            return price - price * getMaxSalePercentage(productID, salesmanID) / 100;
        } else {
            return price;
        }
    }

    public static Sale getSaleByID(String saleID) {
        for (Sale sale : allSales) {
            if (sale.saleID.equals(saleID)) {
                return sale;
            }
        }
        return null;
    }

    //checks for a product with specific salesman whether the product is in sale
    //by the salesman or not

    public static boolean isProductInSaleWithID(String productID, String salesmanID) {
        return getMaxSalePercentage(productID, salesmanID) != -1;
    }

    //it checks all sales for a specific product and salesman and return
    //the highest sale percentage for that

    private static int getMaxSalePercentage(String productID, String salesmanID) {
        int percentage = -1;
        for (Sale sale : allSales) {
            if (sale.isAuthentic() && sale.salesmanID.equals(salesmanID) && sale.productIDs.contains(productID)) {
                if (sale.percentage > percentage) {
                    percentage = sale.percentage;
                }
            }
        }
        return percentage;
    }

    public String toString() {
        StringBuilder result = new StringBuilder(super.toString());
        result.append("Products: " + "\n");
        for (String ID : productIDs) {
            result.append(Product.getNameByID(ID)).append("\n");
        }
        return result.toString();
    }

    public static ArrayList<Sale> getAllAuthenticSales(String salesmanID) {
        ArrayList<Sale> sales = new ArrayList<>();
        for (Sale sale : allSales) {
            if (sale.isAuthentic() & sale.getSalesmanID().equals(salesmanID)) {
                sales.add(sale);
            }
        }
        return sales;
    }
}
