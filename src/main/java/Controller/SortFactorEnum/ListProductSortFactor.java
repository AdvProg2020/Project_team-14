package Controller.SortFactorEnum;

import Model.Product.Product;

import java.util.ArrayList;
import java.util.Comparator;

import Exception.*;

public enum ListProductSortFactor {
    ALPHABETICALLY, PRICE, SEEN_COUNT, HIGHEST_POINT;

    public static String getValues() {
        return "{ALPHABETICALLY, PRICE, SEEN_COUNT, HIGHEST_POINT}";
    }

    public static void sort(String sortFactor, ArrayList<Product> products) throws SortFactorNotAvailableException {
        if (sortFactor.equalsIgnoreCase(ListProductSortFactor.ALPHABETICALLY.name())) {
            products.sort(Comparator.comparing(Product::getName));
        } else if (sortFactor.equalsIgnoreCase(ListProductSortFactor.PRICE.name())) {
            products.sort(Comparator.comparingInt(Product::getMinimumPrice));
        } else if (sortFactor.equalsIgnoreCase(ListProductSortFactor.SEEN_COUNT.name())) {
            products.sort((Comparator.comparingInt(Product::getSeenCount)));
        } else if (sortFactor.equals("")) {
            products.sort((Comparator.comparingInt(Product::getSeenCount)));
        } else if (sortFactor.equalsIgnoreCase(ListProductSortFactor.HIGHEST_POINT.name())) {
            products.sort((Comparator.comparingDouble(Product::getAveragePoint)));
        } else {
            throw new SortFactorNotAvailableException("the sort factor isn't authentic " + "\n" +
                    "the available sort factors: " + ListProductSortFactor.getValues() + "\n");
        }
    }
}
