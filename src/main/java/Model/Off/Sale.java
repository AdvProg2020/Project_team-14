package Model.Off;

import Model.Confirmation;
import Model.Product.Product;
import Model.RandomString;
import Model.Storage;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;

import static Model.Storage.*;

public class Sale extends Off implements Serializable {
    private ArrayList<String> productIDs = new ArrayList<>();
    private String saleID;
    private String salesmanID;
    private Confirmation confirmationState;
    private static final long serialVersionUID = 4L;

    public Sale(String start, String end, int percentage, String salesmanID, ArrayList<String> productIDs) {
        super(start, end, percentage);
        this.saleID = RandomString.createID("Sale");
        this.salesmanID = salesmanID;
        confirmationState = Confirmation.CHECKING;
        allSales.add(this);
        if (productIDs != null) {
            this.productIDs.addAll(productIDs);
        }
    }

    public String getSalesmanID() {
        return salesmanID;
    }

    public void setSalesmanID(String salesmanID) {
        this.salesmanID = salesmanID;
    }

    public static ArrayList<Sale> getAllSaleBySalesmanID(String salesmanID) {
        ArrayList<Sale> salesmanSale = new ArrayList<>();
        for (Sale sale : allSales) {
            if (sale.getSalesmanID().equals(salesmanID)) salesmanSale.add(sale);
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
        int price = Objects.requireNonNull(Storage.getProductById(productID)).getPriceBySalesmanID(salesmanID);
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
        result.append("Confirmation:").append(confirmationState).append("\n");
        result.append("SalesmanId:").append(salesmanID).append("\n");
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

    public ArrayList<String> getProductIDs() {
        return productIDs;
    }

    public void setProductIDs(ArrayList<String> productIDs) {
        this.productIDs = productIDs;
    }

    public void setSaleID(String saleID) {
        this.saleID = saleID;
    }

    public Confirmation getConfirmationState() {
        return confirmationState;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String toStringForTable() {
//        StringBuilder result = new StringBuilder(super.toString());
        StringBuilder result = new StringBuilder("");
        result.append("StartDate:").append(dateToLocalDate(start)).append(" ");
        result.append("EndDate:").append(dateToLocalDate(end)).append(" ");
        result.append("Percentage:").append(percentage).append(" ");
        result.append("Confirmation:").append(this.confirmationState).append(" ");
        result.append("SaleId:").append(this.getSaleID());
        return result.toString();
    }
}
