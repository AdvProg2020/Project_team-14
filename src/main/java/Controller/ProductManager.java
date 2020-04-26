package Controller;

import Model.Product.Product;

import java.util.ArrayList;

public class ProductManager {

    public boolean addProduct(String productID, ArrayList<String> information) {
        return true;
    }

    public boolean editProduct(String productID, String Attribute, String updatedInfo) {
        return true;
    }

    public void deleteProduct(String productID, String salesmanUser) {
        Product product = Product.getProductWithID(productID);
        assert product != null;
        product.deleteForSalesman(salesmanUser);
    }

    public ArrayList<String> listProducts(String salesmanUser, String sortFactor) {
        return null;
    }

    public String viewSingleProduct(String productID) {
        Product product = Product.getProductWithID(productID);
        assert product != null;
        return product.toStringForCustomerView();
    }

    public ArrayList<String> viewBuyersOfProduct(String productID, String sortFactor) {
        return null;
    }

    public String getPointForProductStringFormatted(String productID) {
        Product product = Product.getProductWithID(productID);
        assert product != null;
        if (product.isThereAnyPoint()) {
            String result = "";
            result += product.getAveragePoint() + " out of " + product.getNumberOfPeopleVoted();
            return result;
        } else {
            return "No one has voted yet";
        }
    }

    public String getCommentsForProductStringFormatted(String productID){
        Product product = Product.getProductWithID(productID);
        assert product != null;
        if (product.isThereComment()) {
            String result = "";
            result += product.getComments();
            return null;
        } else {
            return "No one has voted yet";
        }
    }

    public boolean isThereCommentForProduct(String productID) {
        Product product = Product.getProductWithID(productID);
        assert product != null;
        return product.isThereComment();
    }

    public boolean isThereIdenticalProduct(ArrayList<String> attributes) {
        return false;
    }

    public void addSalesmanToProduct(String productID, String salesmanUserName, int price, int remainder) {
        Product product = Product.getProductWithID(productID);
        assert product != null;
        product.addSalesman(salesmanUserName, remainder, price);
    }


    public String getIdenticalProductID(ArrayList<String> attribute) {
        return null;
    }

    public boolean doesSalesmanSellProduct(String salesmanUserName, String productID) {
        Product product = Product.getProductWithID(productID);
        assert product != null;
        product.doesSalesmanSellProductWithUsername(salesmanUserName);

    }
}