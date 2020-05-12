package Controller.SortFactorEnum;

import Model.Account.Account;

import java.util.ArrayList;
import java.util.Comparator;

public enum AccountSortFactor {
    USERNAME, CREDIT;

    public static String getValues() {
        return "{Username,Credit}";
    }

    public static void sort(String sortFactor, ArrayList<Account> accounts) {
        if (sortFactor.equals(AccountSortFactor.USERNAME.name())) {
            accounts.sort(Comparator.comparing(Account::getUsername));
        } else if (sortFactor.equals(AccountSortFactor.CREDIT.name())) {
            accounts.sort(Comparator.comparing(Account::getCredit));
        }
    }
}
