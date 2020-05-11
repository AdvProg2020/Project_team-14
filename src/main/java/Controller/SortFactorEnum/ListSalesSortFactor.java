package Controller.SortFactorEnum;

import Model.Off.Sale;

import java.util.ArrayList;
import java.util.Comparator;

import Exception.*;

public enum ListSalesSortFactor {
    PERCENTAGE, END_DATE;

    public static void sort(String sortFactor, ArrayList<Sale> sales) throws SortFactorNotAvailableException {
        if (sortFactor.equalsIgnoreCase(ListSalesSortFactor.PERCENTAGE.name())) {
            sales.sort(Comparator.comparingInt(Sale::getPercentage));
        } else if (sortFactor.equalsIgnoreCase(ListSalesSortFactor.END_DATE.name())) {
            sales.sort(Comparator.comparing(Sale::getEnd));
        } else if (sortFactor.equals("")) {
            sales.sort(Comparator.comparingInt(Sale::getPercentage));
        }
    }

    public static String getValues() {
        return "{PERCENTAGE, END_DATE}";
    }

}
