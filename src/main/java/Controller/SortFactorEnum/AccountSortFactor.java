package Controller.SortFactorEnum;

import Model.Account.Account;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

public enum AccountSortFactor {
    ALPHABETICALLY, CREDIT;

    public static String getValues() {
        return "{Alphabetically, Credit}";
    }

    public static void sort(String sortFactor, String sortType, ArrayList<Account> accounts) {
        //default sort factor is ALPHABETICALLY
        if (sortFactor.equalsIgnoreCase("none") | sortFactor.equals(AccountSortFactor.ALPHABETICALLY.name())) {
            accounts.sort(Comparator.comparing(Account::getUsername));
        } else if (sortFactor.equals(AccountSortFactor.CREDIT.name())) {
            accounts.sort(Comparator.comparing(Account::getCredit));
        }

        //default sort type is ASCENDING
        if (sortType.equalsIgnoreCase("descending")) {
            Collections.reverse(accounts);
        }
    }
}
