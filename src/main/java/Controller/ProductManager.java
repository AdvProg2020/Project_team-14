package Controller;

import Controller.SortFactorEnum.ViewBuyersOfProductSortFactor;
import Controller.SortFactorEnum.ListProductSortFactor;
import Exception.*;
import Model.Account.Account;
import Model.Account.Role;
import Model.Log.Log;
import Model.Product.Comment;
import Model.Product.Product;
import Model.Request.ChangeProductRequest;
import Model.Request.Request;
import Model.Storage;

import javax.swing.text.View;
import java.util.ArrayList;
import java.util.Comparator;

import static java.lang.String.valueOf;

public class ProductManager {


    private boolean isProductInFilter(Product product, ArrayList<Object> filters) {
        return true;
    }

    public void showProducts(String username, ArrayList<Object> filters) {
        int count = 0;
        ArrayList<Product> products = Storage.getAllProducts();
        StringBuilder answer = new StringBuilder("");
        if (products.size() == 0) {
            Server.setAnswer("nothing found");
        } else {
            for (Product product : products) {
                if (isProductInFilter(product, filters)) {
                    answer.append(product.toStringForBoss()).append("\n");
                    count++;
                }
            }
            if (count == 0) {
                answer = new StringBuilder("nothing found");
            } else {
                answer.append("here are what we found");
            }
            String ans = answer.toString();
            Server.setAnswer(ans);
        }
    }

    public void viewProduct(String username, String productID) {
        Account account = Storage.getAccountWithUsername(username);
        Product product = Storage.getProductById(productID);
        assert product != null;
        assert account != null;
        if (account.getRole().equals(Role.BOSS)) {
            Server.setAnswer(product.toStringForBossView());
        } else if (account.getRole().equals(Role.SALESMAN)) {
            Server.setAnswer(product.toStringForSalesmanView(username));
        } else {
            Server.setAnswer(product.toStringForCustomerView());
        }
    }


    public void editProduct(String productID, String salesmanID, String attribute, String updatedInfo) {
        new ChangeProductRequest(salesmanID, Product.getProductWithID(productID), attribute, updatedInfo);
    }

    public void deleteProduct(String productID, String salesmanUser) {
        Product product = Product.getProductWithID(productID);
        assert product != null;
        new Request(salesmanUser, product, "DELETE_PRODUCT");
    }

    //I didn't actually know for what reason this method was going to be used but I designed this
    //for salesman to view their product with the sort factor they want

    public String listProductsForSalesman(String salesmanUser, String sortFactor) throws SortFactorNotAvailableException {
        StringBuilder result = new StringBuilder();
        ArrayList<Product> products = new ArrayList<>(getProductsOfSalesman(salesmanUser));
        if (sortFactor.equalsIgnoreCase(valueOf(ListProductSortFactor.ALPHABETICALLY))) {
            products.sort(Comparator.comparing(Product::getName));
        } else if (sortFactor.equalsIgnoreCase(valueOf(ListProductSortFactor.PRICE))) {
            products.sort(Comparator.comparingInt(p -> p.getPriceBySalesmanID(salesmanUser)));
        } else if (sortFactor.equalsIgnoreCase(valueOf(ListProductSortFactor.SEEN_COUNT))) {
            products.sort((Comparator.comparingInt(Product::getSeenCount)));
        } else if (sortFactor.equalsIgnoreCase(valueOf(ListProductSortFactor.HIGHEST_POINT))) {
            products.sort((Comparator.comparingDouble(Product::getAveragePoint)));
        } else if (sortFactor.equals("")) {
            products.sort((Comparator.comparingInt(Product::getSeenCount)));
        } else {
            throw new SortFactorNotAvailableException("the sort factor isn't authentic " + "\n" +
                    "the available sort factors: " + ListProductSortFactor.getValues() + "\n");
        }
        for (Product product : products) {
            result.append(product.toStringForSalesmanView(salesmanUser));
        }
        return result.toString();
    }

    public String listProductsForCustomer(String sortFactor, ArrayList<String> productIDs) throws SortFactorNotAvailableException {
        StringBuilder result = new StringBuilder();
        ArrayList<Product> products = new ArrayList<>(getArrayListOfProductsFromArrayListOfProductIDs(productIDs));
        if (sortFactor.equalsIgnoreCase(valueOf(ListProductSortFactor.ALPHABETICALLY))) {
            products.sort(Comparator.comparing(Product::getName));
        } else if (sortFactor.equalsIgnoreCase(valueOf(ListProductSortFactor.PRICE))) {
            products.sort(Comparator.comparingInt(Product::getMinimumPrice));
        } else if (sortFactor.equalsIgnoreCase(valueOf(ListProductSortFactor.SEEN_COUNT))) {
            products.sort((Comparator.comparingInt(Product::getSeenCount)));
        } else if (sortFactor.equals("")) {
            products.sort((Comparator.comparingInt(Product::getSeenCount)));
        } else if (sortFactor.equalsIgnoreCase(valueOf(ListProductSortFactor.HIGHEST_POINT))) {
            products.sort((Comparator.comparingDouble(Product::getAveragePoint)));
        } else {
            throw new SortFactorNotAvailableException("the sort factor isn't authentic " + "\n" +
                    "the available sort factors: " + ListProductSortFactor.getValues() + "\n");
        }
        for (Product product : products) {
            result.append(product.getName()).append("\n");
        }
        return result.toString();
    }


    public String viewSingleProductDigest(String productID) {
        Product product = Product.getProductWithID(productID);
        assert product != null;
        return product.toStringForCustomerView();
    }


    public String viewBuyersOfProduct(String productID, String sortFactor) throws SortFactorNotAvailableException {
        StringBuilder result = new StringBuilder();
        ArrayList<String> buyers = new ArrayList<>(Log.getBuyerOfProduct(productID));
        ViewBuyersOfProductSortFactor.sort(sortFactor, buyers);
        for (String buyer : buyers) {
            result.append(buyer).append("\n");
        }
        return result.toString();
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
            return Comment.getCommentsForProductStringFormatted(productID);
        } else {
            return "No one has commented yet";
        }
    }

    public static boolean isThereCommentForProduct(String productID) {
        Product product = Product.getProductWithID(productID);
        assert product != null;
        return product.isThereComment();
    }

    public static Product getProductWithName(String name) {
        for (Product product : Storage.getAllProducts()) {
            if (product.getName().equals(name)) {
                return product;
            }
        }
        return null;
    }

    public static boolean isThereProductWithName(String name) {
        return getProductWithName(name) != null;
    }

    public static void addSalesmanToProduct(String productID, String salesmanUserName, int price, int remainder) {
        Product product = Product.getProductWithID(productID);
        assert product != null;
        product.addSalesman(salesmanUserName, remainder, price);
    }

    public static ArrayList<Product> getProductsOfSalesman(String salesmanUser) {
        ArrayList<Product> arrayList = new ArrayList<>();
        for (Product product : Storage.getAllProducts()) {
            if (product.doesSalesmanSellProductWithUsername(salesmanUser)) {
                arrayList.add(product);
            }
        }
        return arrayList;
    }

    public boolean doesSalesmanSellProduct(String salesmanUserName, String productID) {
        Product product = Product.getProductWithID(productID);
        assert product != null;
        return product.doesSalesmanSellProductWithUsername(salesmanUserName);
    }

    public static ArrayList<Product> getArrayListOfProductsFromArrayListOfProductIDs(ArrayList<String> productIDs) {
        ArrayList<Product> arrayList = new ArrayList<>();
        for (String productID : productIDs) {
            arrayList.add(Product.getProductWithID(productID));
        }
        return arrayList;
    }

    public boolean canUserCommentForProduct(String username, String productID) {
        return Comment.canUserCommentForProduct(username, productID);
    }

}