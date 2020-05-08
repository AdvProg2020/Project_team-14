package Controller;

import Controller.SortFactorEnum.ListSalesSortFactor;
import Controller.SortFactorEnum.listProductSortFactor;
import Model.Off.Sale;
import Model.Product.Product;
import Model.Request.ChangeSaleRequest;
import Model.Request.Request;
import Model.Storage;

import java.awt.image.AreaAveragingScaleFilter;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Comparator;

import static java.lang.String.valueOf;

public class SalesManager {

    //default sort factor is percentage

    public void listSales(String sortFactor) {
        StringBuilder result = new StringBuilder();
        ArrayList<Sale> sales = new ArrayList<>(Sale.getAllAuthenticSales());
        if (sortFactor.equalsIgnoreCase(valueOf(ListSalesSortFactor.PERCENTAGE))) {
            sales.sort(Comparator.comparingInt(Sale::getPercentage));
        }  else if (sortFactor.equalsIgnoreCase(valueOf(ListSalesSortFactor.END_DATE))) {
            sales.sort(Comparator.comparing(Sale::getEnd));
        } else if (sortFactor.equals("")) {
            sales.sort(Comparator.comparingInt(Sale::getPercentage));
        }
        for (Sale sale : sales) {
            result.append(sale.toString());
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
