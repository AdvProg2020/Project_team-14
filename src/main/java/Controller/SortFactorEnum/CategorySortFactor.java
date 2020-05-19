package Controller.SortFactorEnum;

import Model.Category.Category;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public enum CategorySortFactor {
    ALPHABETICALLY, SEEN_COUNT;

    public static void sort(String sortFactor, String sortType, ArrayList<Category> categories) {
        if (sortFactor.equals("none") | sortFactor.equalsIgnoreCase(CategorySortFactor.SEEN_COUNT.name())) {
            categories.sort(Comparator.comparingInt(Category::getSeenCount));
        } else if (sortFactor.equalsIgnoreCase(CategorySortFactor.ALPHABETICALLY.name())) {
            categories.sort(Comparator.comparing(Category::getCategoryName));
        }

        if (sortType.equalsIgnoreCase("descending")) {
            Collections.reverse(categories);
        }
    }
}
