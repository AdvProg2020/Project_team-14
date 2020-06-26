package Controller;


import Controller.SortFactorEnum.SalesSortFactor;
import Model.Account.Salesman;
import Model.Log.SellLog;
import Model.Off.Sale;
import Model.Product.Product;
import Model.Request.ChangeProductRequest;
import Model.Request.ChangeSaleRequest;
import Model.Request.Request;
import Model.Storage;

import java.util.ArrayList;
import java.util.Comparator;

import static java.lang.String.valueOf;

public class SalesmanManager {

    /*
     * this is AccountManager part
     */

    public void register(String[] information) {
        if (Storage.isThereAccountWithUsername(information[3])) {
            Server.setAnswer("username is already taken, try something else");
            return;
        }
        Server.setAnswer("register successful");
        new Salesman(information[3], information[4], information[1], information[2],
                information[6], information[7], information[5], information[8], 0);
    }

    //the order is 2-->name  1-->salesmanID  3-->brand  4-->description  5-->price  6-->remainder

    public void createProduct(String info) {
        String[] information = info.split("\\+");
        Product product = new Product(information[2], information[1], information[3], information[4],
                Integer.parseInt(information[5]), Integer.parseInt(information[6]));
        new Request(information[1], product, "ADD_NEW_PRODUCT");
        Server.setAnswer("product created\n" + product.getProductID());
    }

    public String getSalesHistory(String salesmanID) {
        Salesman salesman = (Salesman) Storage.getAccountWithUsername(salesmanID);
        return SellLog.getSalesmanSellLogs_StringFormatted(salesmanID);
    }

    /*
     * this is SalesManager part
     */

    //default sort factor is percentage

    public void showSales(String salesmanID, ArrayList<Object> filters, String sortFactor, String sortType) {
        StringBuilder result = new StringBuilder("Here are All of your Sales:");
        ArrayList<Sale> sales = Sale.getAllSaleBySalesmanID(salesmanID);
        SalesSortFactor.sort(sortFactor, sortType, sales);
        for (Sale sale : sales) {
            if (saleHasFactor()) {
                result.append("\n").append(sale.toStringForTable()); //assume we list just IDs, then if user chose one, we show detail
            }
        }
        if (result.toString().equals("Here are All of your Sales:")) {
            Server.setAnswer("nothing found");
        } else {
            Server.setAnswer(result.toString());
        }
    }

    private boolean saleHasFactor() {
        return true;
        //has work
    }

    public void viewSale(String saleID) {
        Sale selectedSale = Sale.getSaleByID(saleID);
        String ans;
        if (selectedSale == null) {
            ans = "there isn't exist any sale with this ID, try another ID";
        } else {
            ans = selectedSale.toString();
        }
        Server.setAnswer(ans);
    }

    public void editSale(String saleID, String attribute, String updatedInfo) {
        String ans = "your request to edit " + attribute.toUpperCase() + " has been sent to manager";
        Sale selectedSale = Sale.getSaleByID(saleID);
        if (selectedSale == null) {
            ans = "ERROR: sale doesn't exist.";
        } else {
            new ChangeSaleRequest(selectedSale.getSalesmanID(), selectedSale, attribute, updatedInfo);
        }
        Server.setAnswer(ans);
    }

    public void createSale(String salesmanID, String start, String end, int percentage, ArrayList<String> productID) {
        Sale newSale = new Sale(start, end, percentage, salesmanID, productID);
        new Request(salesmanID, newSale, "ADD_NEW_SALE");
    }

    public void canAddToSale(String salesmanID, String productID) {
        if (!Storage.isThereProductWithID(productID)) {
            Server.setAnswer("no product exist with this ID");
        } else {
            if (!Storage.getProductById(productID).doesSalesmanSellProductWithUsername(salesmanID)) {
                Server.setAnswer("you don't sell this product");
            } else {
                Server.setAnswer("yes");
            }
        }
    }

    public void searchSale(String saleID) {
        for (Sale sale : Storage.allSales) {
            if (sale.getSaleID().equalsIgnoreCase(saleID)) {
                Server.setAnswer("search completed");
            }
        }
        Server.setAnswer("There isn't exist a sale with such a sale");
    }

    /*
     * this is ProductManager part
     */

    public void editProduct(String productID, String salesmanID, String attribute, String updatedInfo) {
        new ChangeProductRequest(salesmanID, Storage.getProductById(productID), attribute, updatedInfo);
    }

    public void deleteProduct(String productID, String salesmanUser) {
        Product product = Storage.getProductById(productID);
        assert product != null;
        new Request(salesmanUser, product, "DELETE_PRODUCT");
    }

}
