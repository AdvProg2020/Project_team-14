package Controller;

import Controller.SortFactorEnum.ViewBuyersOfProductSortFactor;
import Controller.SortFactorEnum.ProductSortFactor;
import Model.Account.Account;
import Model.Account.Role;
import Model.Log.Log;
import Model.Product.Comment;
import Model.Product.Product;
import Model.Request.ChangeProductRequest;
import Model.Request.Request;
import Model.Storage;

import java.util.ArrayList;
import java.util.Comparator;

import static java.lang.String.valueOf;

public class ProductManager {


    private boolean checkCategoryFilter(Product product, String categories) {
        String[] categoriesName = categories.split(",");
        for (String category : categoriesName) {
            if (product.getCategoryName().equals(category)) return true;
        }
        return false;
    }

    private boolean checkSalesmanFilter(Product product, String salesmanIDs) {
        String[] salesmanID = salesmanIDs.split(",");
        for (String salesman : salesmanID) {
            if (product.doesSalesmanSellProductWithUsername(salesman)) return true;
        }
        return false;
    }

    private boolean checkConfirmationFilter(Product product, String s) {
        String[] allState = s.split(",");
        for (String state : allState) {
            if (product.getOverallConfirmation().equals(state)) return true;
        }
        return false;
    }

    private boolean checkRemainderFilter(Product product, String s) {
        String[] remainderState = s.split(",");
        int wantedAmount;
        boolean ans;
        if (remainderState.length == 2) {
            return true;
        }
        if (remainderState[0].equals("not available")) {
            wantedAmount = 0;
            ans = false;
        } else {
            wantedAmount = 1;
            ans = true;
        }
        for (String salesmanID : product.getSalesmanIDs()) {
            if (product.isAvailableBySalesmanWithUsername(salesmanID, wantedAmount)) return ans;
        }
        return ans;
    }


    private boolean isProductInFilter(Product product, ArrayList<Object> filters) {
        for (int i = 0; i < filters.size(); i += 2) {
            if (((String) filters.get(i)).equalsIgnoreCase("salesmanIDs")) {
                if (!checkSalesmanFilter(product, (String) filters.get(i + 1))) {
                    return false;
                }
            }
            if (((String) filters.get(i)).equalsIgnoreCase("categories")) {
                if (!checkCategoryFilter(product, (String) filters.get(i + 1))) {
                    return false;
                }
            }
            if (((String) filters.get(i)).equalsIgnoreCase("remainder")) {
                if (!checkRemainderFilter(product, (String) filters.get(i + 1))) {
                    return false;
                }
            }
            if (((String) filters.get(i)).equalsIgnoreCase("Confirmation")) {
                if (!checkConfirmationFilter(product, (String) filters.get(i + 1))) {
                    return false;
                }
            }
        }
        return true;
    }

    public void showProducts(String username, ArrayList<Object> filters, String sortFactor, String sortType, int type) {
        int count = 0;
        ArrayList<Product> products = Storage.getAllProducts();
        ProductSortFactor.sort(sortFactor, sortType, products);
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


    public void addToProduct(String salesmanUser, String productID, String remainder, String money) {
        Product product = Storage.getProductById(productID);
        assert product != null;
        product.addSalesman(salesmanUser, Integer.parseInt(remainder), Integer.parseInt(money));
        new Request(salesmanUser, product, "ADD_TO_PRODUCT");
    }

    public void searchProduct(String salesmanUser, String productID) {
        if (Storage.isThereProductWithID(productID)) {
            Server.setAnswer("search completed");
        } else {
            Server.setAnswer("no product with this ID");
        }
    }

    public void deleteProduct(String salesmanUser, String productID) {
        Product product = Storage.getProductById(productID);
        assert product != null;
        new Request(salesmanUser, product, "DELETE_PRODUCT");
    }

    public void editProduct(String productID, String salesmanID, String attribute, String updatedInfo) {
        Product p = Storage.getProductById(productID);
        new ChangeProductRequest(salesmanID, Storage.getProductById(productID), attribute, updatedInfo);
    }

    //I didn't actually know for what reason this method was going to be used but I designed this
    //for salesman to view their product with the sort factor they want

    public String viewBuyersOfProduct(String productID, String sortFactor) throws Exception {
        StringBuilder result = new StringBuilder();
        ArrayList<String> buyers = new ArrayList<>(Log.getBuyerOfProduct(productID));
        ViewBuyersOfProductSortFactor.sort(sortFactor, buyers);
        for (String buyer : buyers) {
            result.append(buyer).append("\n");
        }
        return result.toString();
    }

    public static String getPointForProductStringFormatted(String productID) {
        Product product = Storage.getProductById(productID);
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
        Product product = Storage.getProductById(productID);
        assert product != null;
        if (product.isThereComment()) {
            return Comment.getCommentsForProductStringFormatted(productID);
        } else {
            return "No one has commented yet";
        }
    }

    public static boolean isThereCommentForProduct(String productID) {
        Product product = Storage.getProductById(productID);
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
        Product product = Storage.getProductById(productID);
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
        Product product = Storage.getProductById(productID);
        assert product != null;
        return product.doesSalesmanSellProductWithUsername(salesmanUserName);
    }

    public static ArrayList<Product> getArrayListOfProductsFromArrayListOfProductIDs(ArrayList<String> productIDs) {
        ArrayList<Product> arrayList = new ArrayList<>();
        for (String productID : productIDs) {
            arrayList.add(Storage.getProductById(productID));
        }
        return arrayList;
    }

    public boolean canUserCommentForProduct(String username, String productID) {
        return Comment.canUserCommentForProduct(username, productID);
    }

    public void showSalesman(String s) {
        String ans = "here are\n";
        for (Account account : Storage.getAllAccounts()) {
            if (account.getRole().equals(Role.SALESMAN)) {
                ans += account.getUsername() + "\n";
            }
        }
        Server.setAnswer(ans);
    }
}