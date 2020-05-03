package Controller;

import Model.Off.Sale;
import Model.Request.ChangeSaleRequest;
import Model.Request.Request;
import Model.Storage;

import java.text.ParseException;
import java.util.ArrayList;

public class SalesManager {

    public void listSales (String salesmanID, String sortFactor) {
        StringBuilder ans = new StringBuilder();
        ArrayList<Sale> allSales = Sale.getAllSaleBySalesmanID(salesmanID);
        /*
         * what are the sort factor??
         */
        for (Sale sale : allSales) {
            ans.append(sale.toString());
        }
        Server.setAnswer(ans.toString());
    }

    public void viewSingleSale (String saleID) {
        Sale selectedSale = Sale.getSaleByID(saleID);
        String ans;
        if (selectedSale == null) {
            ans = "there isn't exist any sale whit this Id, try another Id";
        } else {
            ans = selectedSale.toString();
        }
        Server.setAnswer(ans);
    }

    public void editSale (String saleID, String attribute, String updatedInfo) {
        String ans = "your request to update Sale info has been sent to manager";
        Sale sale = Sale.getSaleByID(saleID);
        new ChangeSaleRequest(sale.getSalesmanID(), sale, attribute, updatedInfo);
        Server.setAnswer(ans);
    }

    public void addSale (ArrayList<String> info) throws ParseException {
        Sale newSale = new Sale(info.get(0), info.get(1), Integer.parseInt(info.get(2)), info.get(3));
        new Request(info.get(3), newSale, "ADD_NEW_SALE");
    }
}
