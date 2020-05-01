package Controller;

import Model.Off.Sale;
import Model.Storage;

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
        // need request class be implemented
    }

    public void addSale (ArrayList<String> info) {
        //
    }
}
