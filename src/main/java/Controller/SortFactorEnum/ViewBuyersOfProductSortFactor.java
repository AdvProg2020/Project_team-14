package Controller.SortFactorEnum;

import Controller.BossManager;
import Model.Off.OffCode;

import java.util.ArrayList;
import java.util.Comparator;

import Exception.*;
import Model.Product.Product;

public enum  ViewBuyersOfProductSortFactor {
    ALPHABETICALLY;

    public static String getValues() {
        return "{ALPHABETICALLY}";
    }

    public static void sort(String sortFactor, ArrayList<String> buyers) throws SortFactorNotAvailableException {
        if (sortFactor.equalsIgnoreCase(String.valueOf(ViewBuyersOfProductSortFactor.ALPHABETICALLY))) {
            buyers.sort(String::compareTo);
        } else {
            throw new SortFactorNotAvailableException("the sort factor isn't authentic " + "\n" +
                    "the available sort factors: " + ViewBuyersOfProductSortFactor.getValues() + "\n");

        }
    }


}
