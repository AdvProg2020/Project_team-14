package Controller;

import Model.Off.Sale;
import Model.Storage;

import java.text.ParseException;
import java.util.ArrayList;

public class SalesManager {

    public void listSales (String sortFactor) {
        StringBuilder ans = new StringBuilder();
        ArrayList<Sale> allSales = Storage.getAllSales();
        /*
         * what are the sort factor??
         */
        for (Sale sale : allSales) {
            ans.append(sale.toString());
        }
        Server.setAnswer(ans.toString());
    }

    public void viewSingleSale (String saleID) {
        Sale selectedSale = Storage.getSaleByID(saleID);
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
        Sale selectedSale = Storage.getSaleByID(saleID);
        if (attribute.equals("startDate")) {
            new Request("editSale", saleID, attribute, updatedInfo);
        } else if (attribute.equals("endDate")) {
            new Request("editSale", saleID, attribute,updatedInfo);
        } else if (attribute.equals("addProduct")) {
            new Request("editSale", saleID, attribute, updatedInfo);
        } else if (attribute.equals("removeProduct")) {
            new Request("editSale", saleID, attribute, updatedInfo);
        } else if (attribute.equals("percentage")) {
            new Request("editSale", saleID, attribute, updatedInfo);
        } else {
            ans = "sales do not have property like want you want";
        }
        Server.setAnswer(ans);
    }

    public void addSale (ArrayList<String> info) throws ParseException {
        Sale newSale = new Sale(info.get(0), info.get(1), Integer.parseInt(info.get(2)), info.get(3));
        new Request("addSale", newSale.getSaleID());
    }
}
