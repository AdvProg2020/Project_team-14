package Controller;

import Model.Product.Product;
import Model.Storage;

import java.util.ArrayList;
import java.util.Comparator;

public class ProductManager {

    //the order is 0-->name  1-->salesmanID  2-->brand  3-->description  4-->price  5-->remainder
    public boolean addNewProduct(String productID, ArrayList<String> information) {
        new Product(information.get(0), information.get(1), information.get(2), information.get(3),
                Integer.parseInt(information.get(4)), Integer.parseInt(information.get(5)));
        return true;
    }

    public void editProduct(String productID, String salesmanID, String attribute, String updatedInfo) {
        attribute = attribute.trim();
        if (attribute.equalsIgnoreCase("brand")) {
            editProductBrand(productID, updatedInfo);
        } else if (attribute.equalsIgnoreCase("price")) {
            editProductPrice(productID, salesmanID, updatedInfo);
        } else if (attribute.equalsIgnoreCase("remainder")) {
            editProductRemainder(productID, salesmanID, updatedInfo);
        }
    }

    private void editProductBrand(String productID, String updatedInfo) {
        Product product = Product.getProductWithID(productID);
        assert product != null;
        product.setBrand(updatedInfo);
    }

    private void editProductPrice(String productID, String salesmanUser, String updatedInfo) {
        Product product = Product.getProductWithID(productID);
        assert product != null;
        product.setPriceForSalesman(Integer.parseInt(updatedInfo), salesmanUser);
    }

    private void editProductRemainder(String productID, String salesmanUser, String updatedInfo) {
        Product product = Product.getProductWithID(productID);
        assert product != null;
        product.setRemainderForSalesman(Integer.parseInt(updatedInfo), salesmanUser);
    }

    public void deleteProduct(String productID, String salesmanUser) {
        Product product = Product.getProductWithID(productID);
        assert product != null;
        product.deleteForSalesman(salesmanUser);
    }

    public static ArrayList<String> listProducts(String salesmanUser, String sortFactor) {
        ArrayList<Product> products = new ArrayList<>(getProductsOfSalesman(salesmanUser));
        if (sortFactor.equalsIgnoreCase("alphabetically")){
            //products.sort((Comparator.comparing()));
        } else if (sortFactor.equalsIgnoreCase("price")){
            //sorting
        } else if (sortFactor.equalsIgnoreCase("seen count" ) || sortFactor.equalsIgnoreCase("seencount")){
            //sorting
        }

        ArrayList<String> strings = new ArrayList<>();
        for(Product product :products){
          //  strings.add(product.to)
        }
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

    public static String getPointForProductStringFormatted(String productID) {
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

    public static String getCommentsForProductStringFormatted(String productID) {
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

    public static boolean isThereCommentForProduct(String productID) {
        Product product = Product.getProductWithID(productID);
        assert product != null;
        return product.isThereComment();
    }

    public static boolean isThereIdenticalProduct(ArrayList<String> attributes) {
        return false;
    }

    public static void addSalesmanToProduct(String productID, String salesmanUserName, int price, int remainder) {
        Product product = Product.getProductWithID(productID);
        assert product != null;
        product.addSalesman(salesmanUserName, remainder, price);
    }


    public static String getIdenticalProductID(ArrayList<String> attribute) {
        return null;
    }

    public static ArrayList<Product> getProductsOfSalesman(String salesmanUser) {
        ArrayList<Product> arrayList = new ArrayList<>();
        for (Product product : Storage.allProducts) {
            if (product.doesSalesmanSellProductWithUsername(salesmanUser)) {
                arrayList.add(product);
            }
        }
        return arrayList;
    }

    /*public boolean doesSalesmanSellProduct(String salesmanUserName, String productID) {
        Product product = Product.getProductWithID(productID);
        assert product != null;
        product.doesSalesmanSellProductWithUsername(salesmanUserName);

    }*/
}