package Controller.SortFactorEnum;


import Controller.BossManager;
import Model.Off.OffCode;

import java.util.ArrayList;
import java.util.Comparator;

import Exception.*;

public enum ListOffCodesSortFactor {
    PERCENTAGE, END_DATE, NUMBER_OF_TIMES_IT_CAN_BE_STILL_USED, CEILING;

    public static String getValues() {
        return "{PERCENTAGE, END_DATE, NUMBER_OF_TIMES_IT_CAN_BE_STILL_USED, CEILING}";
    }

    public static void sort(String sortFactor, ArrayList<OffCode> offCodes) {
        if (sortFactor.equals(ListOffCodesSortFactor.END_DATE.name())) {
            offCodes.sort(Comparator.comparing(OffCode::getEnd));
        } else if (sortFactor.equals(ListOffCodesSortFactor.NUMBER_OF_TIMES_IT_CAN_BE_STILL_USED.name())) {
            offCodes.sort(Comparator.comparingInt(OffCode::getNumberOfTimesCanBeUsed));
        } else if (sortFactor.equals(ListOffCodesSortFactor.CEILING.name())) {
            offCodes.sort(Comparator.comparingInt(OffCode::getCeiling));
        } else if (sortFactor.equals(ListOffCodesSortFactor.PERCENTAGE.name())) {
            offCodes.sort(Comparator.comparingInt(OffCode::getPercentage));
        }
    }
}
