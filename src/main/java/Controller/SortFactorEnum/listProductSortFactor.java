package Controller.SortFactorEnum;

public enum listProductSortFactor {
    ALPHABETICALLY, PRICE, SEEN_COUNT,HIGHEST_POINT;

    public static String getValues() {
        return "{ALPHABETICALLY, PRICE, SEEN_COUNT, HIGHEST_POINT}";
    }
}
