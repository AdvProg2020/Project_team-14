package Controller.SortFactorEnum;

import Model.Off.Sale;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

import Exception.*;

public enum SalesSortFactor {
    PERCENTAGE, END_DATE, START_DATE;

    public static void sort(String sortFactor, String sortType, ArrayList<Sale> sales) {
        //default sort factor is percentage
        if (sortFactor.equalsIgnoreCase("none") | sortFactor.equalsIgnoreCase(SalesSortFactor.PERCENTAGE.name())) {
            sales.sort(Comparator.comparingInt(Sale::getPercentage));
        } else if (sortFactor.equalsIgnoreCase(SalesSortFactor.END_DATE.name())) {
            sales.sort(Comparator.comparing(Sale::getEnd));
        } else if (sortFactor.equalsIgnoreCase(SalesSortFactor.START_DATE.name())) {
            sales.sort(Comparator.comparing(Sale::getStart));
        }

        //default sort type is ASCENDING
        if (sortType.equalsIgnoreCase("descending")) {
            Collections.reverse(sales);
        }
    }

    public static String getValues() {
        return "{PERCENTAGE, END_DATE}";
    }

}
