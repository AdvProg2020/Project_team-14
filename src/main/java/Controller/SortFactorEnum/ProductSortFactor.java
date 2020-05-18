package Controller.SortFactorEnum;

import Model.Product.Product;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import Exception.*;

public enum ProductSortFactor {
    ALPHABETICALLY, PRICE, SEEN_COUNT, HIGHEST_POINT;

    public static String getValues() {
        return "{ALPHABETICALLY, PRICE, SEEN_COUNT, HIGHEST_POINT}";
    }

    public static void sort(String sortFactor, String sortType, ArrayList<Product> products) {
        //default sort factor is price
        if (sortFactor.equalsIgnoreCase(ProductSortFactor.ALPHABETICALLY.name())) {
            products.sort(Comparator.comparing(Product::getName));
        } else if (sortFactor.equalsIgnoreCase(ProductSortFactor.PRICE.name())) {
            products.sort(Comparator.comparingInt(Product::getMinimumPrice));
        } else if (sortFactor.equalsIgnoreCase(ProductSortFactor.SEEN_COUNT.name())) {
            products.sort((Comparator.comparingInt(Product::getSeenCount)));
        } else if (sortFactor.equals("")) {
            products.sort((Comparator.comparingInt(Product::getSeenCount)));
        } else if (sortFactor.equalsIgnoreCase(ProductSortFactor.HIGHEST_POINT.name())) {
            products.sort((Comparator.comparingDouble(Product::getAveragePoint)));
        } else {/*
            throw new SortFactorNotAvailableException("the sort factor isn't authentic " + "\n" +
                    "the available sort factors: " + ProductSortFactor.getValues() + "\n");*/
        }

        //default sort factor is ASCENDING
        if (sortType.equalsIgnoreCase("descending")) {
            Collections.reverse(products);
        }
    }
}
