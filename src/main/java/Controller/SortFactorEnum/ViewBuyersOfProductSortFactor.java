package Controller.SortFactorEnum;

import java.util.ArrayList;

public enum  ViewBuyersOfProductSortFactor {
    ALPHABETICALLY;

    public static String getValues() {
        return "{ALPHABETICALLY}";
    }

    public static void sort(String sortFactor, ArrayList<String> buyers) throws Exception {
        if (sortFactor.equalsIgnoreCase(String.valueOf(ViewBuyersOfProductSortFactor.ALPHABETICALLY))) {
            buyers.sort(String::compareTo);
        } else {
            throw new Exception("the sort factor isn't authentic " + "\n" +
                    "the available sort factors: " + ViewBuyersOfProductSortFactor.getValues() + "\n");

        }
    }
}
