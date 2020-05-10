package Controller.SortFactorEnum;


public enum ListOffCodesSortFactor {
    PERCENTAGE, END_DATE, NUMBER_OF_TIMES_IT_CAN_BE_STILL_USED, CEILING;

    public static String getValues() {
        return "{PERCENTAGE, END_DATE, NUMBER_OF_TIMES_IT_CAN_BE_STILL_USED, CEILING}";
    }
}
