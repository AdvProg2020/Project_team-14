package Controller;

import java.util.ArrayList;

public class ProductManager {

    public boolean addProduct(String productID, ArrayList<String> information) {
        return true;
    }

    public boolean editProduct(String productID, String Attribute, String updatedInfo) {
        return true;
    }

    public boolean deleteProduct(String productID, String salesmanUser) {
        return true;
    }

    public ArrayList<String> listProducts(String salesmanUser, String sortFactor) {
        return null;
    }

    public String viewSingleProduct(String productID) {
        return null;
    }

    public ArrayList<String> viewBuyersOfProduct(String productID, String sortFactor) {
        return null;
    }

    public boolean isThereCommentForProduct(String productID){
        return false;
    }

    public boolean isThereIdenticalProduct(ArrayList<String> attributes){
        return false;
    }

    public void addSalesmanToProduct(String productID, String salesmanUser, int price, int remainder){

    }

    public String getIdenticalProductID(ArrayList<String> attribute){
        return null;
    }
}