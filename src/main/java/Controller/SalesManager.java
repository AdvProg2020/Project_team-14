package Controller;

import Controller.SortFactorEnum.ListSalesSortFactor;
import Model.Off.Sale;
import Model.Request.ChangeSaleRequest;
import Model.Request.Request;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Comparator;

import static java.lang.String.valueOf;

public class SalesManager {
    /*
     * ------------------[this method (all of them) has been copied to SalesmanManager]--------------------------
     */

    //default sort factor is percentage
    public void listSales(String salesmanID ,String sortFactor) {
        StringBuilder result = new StringBuilder("Here are All of your Sales:");
        ArrayList<Sale> sales = new ArrayList<>(Sale.getAllAuthenticSales(salesmanID));
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
}
