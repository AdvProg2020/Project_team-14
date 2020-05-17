package Controller.SortFactorEnum;


import Model.Off.OffCode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

public enum OffCodesSortFactor {
    PERCENTAGE, END_DATE, START_DATE;

    public static String getValues() {
        return "{PERCENTAGE, END_DATE, NUMBER_OF_TIMES_IT_CAN_BE_STILL_USED, CEILING}";
    }

    public static void sort(String sortFactor, String sortType, ArrayList<OffCode> offCodes) {
        //default sort factor is End Time
        if (sortFactor.equals("none")) {
            offCodes.sort(Comparator.comparing(OffCode::getEnd));
        } else if (sortFactor.equals(OffCodesSortFactor.END_DATE.name())) {
            offCodes.sort(Comparator.comparing(OffCode::getEnd));
        } else if (sortFactor.equals(OffCodesSortFactor.START_DATE.name())) {
            offCodes.sort(Comparator.comparing(OffCode::getStart));
        } else if (sortFactor.equals(OffCodesSortFactor.PERCENTAGE.name())) {
            offCodes.sort(Comparator.comparingInt(OffCode::getPercentage));
        }

        //default sort type is ASCENDING
        if (sortType.equalsIgnoreCase("descending")) {
            Collections.reverse(offCodes);
        }
    }
}
