package Controller;


import Controller.SortFactorEnum.ListSalesSortFactor;
import Controller.SortFactorEnum.ViewBuyersOfProductSortFactor;
import Model.Account.Salesman;
import Model.Log.Log;
import Model.Log.SellLog;
import Model.Off.Sale;
import Model.Product.Product;
import Model.Request.ChangeProductRequest;
import Model.Request.ChangeSaleRequest;
import Model.Request.Request;
import Model.Storage;
import Exception.*;

import java.text.ParseException;
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
        }
        Server.setAnswer("register successful");
        new Salesman(information[3], information[4], information[1], information[2],
                information[6], information[7], information[5], information[8],0);
    }

    public void showCompanyInfo(String salesmanID) {
        Salesman salesman = (Salesman) Storage.getAccountWithUsername(salesmanID);
        assert salesman != null;
        Server.setAnswer("Salesman Company Info : " + salesman.getCompany());
    }

    public String getSalesHistory(String salesmanID) {
        Salesman salesman = (Salesman) Storage.getAccountWithUsername(salesmanID);
        return SellLog.getSalesmanSellLogs_StringFormatted(salesmanID);
    }

    /*
     * this is SalesManager part
     */

    //default sort factor is percentage
    public void listSales(String salesmanID, String sortFactor) {
        StringBuilder result = new StringBuilder("Here are All of your Sales:");
        ArrayList<Sale> sales = new ArrayList<>(Sale.getAllAuthenticSales(salesmanID));
        if (sortFactor.equalsIgnoreCase(valueOf(ListSalesSortFactor.PERCENTAGE))) {
            sales.sort(Comparator.comparingInt(Sale::getPercentage));
        } else if (sortFactor.equalsIgnoreCase(valueOf(ListSalesSortFactor.END_DATE))) {
            sales.sort(Comparator.comparing(Sale::getEnd));
        } else if (sortFactor.equals("")) {
            sales.sort(Comparator.comparingInt(Sale::getPercentage));
        }
        for (Sale sale : sales) {
            result.append("\n").append(sale.getSaleID()); //assume we list just IDs, then if user chose one, we show detail
        }
        Server.setAnswer(result.toString());
    }

    public void viewSingleSale(String saleID) {
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
        String ans = "your request to update Sale info has been sent to manager";
        Sale selectedSale = Sale.getSaleByID(saleID);
        if (selectedSale == null) {
            ans = "error, sale doesn't exist.";
        } else {
            new ChangeSaleRequest(selectedSale.getSalesmanID(), selectedSale, attribute, updatedInfo);
        }
        Server.setAnswer(ans);
    }

    //order of info : 0 -> [start time] , 1 -> [end time] , 2 -> [percentage] , 3 -> [salesmanID]
    public void addSale(ArrayList<String> info) throws ParseException {
        Sale newSale = new Sale(info.get(0), info.get(1), Integer.parseInt(info.get(2)), info.get(3));
        new Request(info.get(3), newSale, "ADD_NEW_SALE");
    }

    /*
     * this is ProductManager part
     */

    //the order is 0-->name  1-->salesmanID  2-->brand  3-->description  4-->price  5-->remainder
    public void addNewProduct(String productID, ArrayList<String> information) {
        Product p = new Product(information.get(0), information.get(1), information.get(2), information.get(3),
                Integer.parseInt(information.get(4)), Integer.parseInt(information.get(5)));
        new Request(information.get(1), p, "ADD_NEW_PRODUCT");
    }

    public void editProduct(String productID, String salesmanID, String attribute, String updatedInfo) {
        new ChangeProductRequest(salesmanID, Product.getProductWithID(productID), attribute, updatedInfo);
    }

    public void deleteProduct(String productID, String salesmanUser) {
        Product product = Product.getProductWithID(productID);
        assert product != null;
        new Request(salesmanUser, product, "DELETE_PRODUCT");
    }

}
