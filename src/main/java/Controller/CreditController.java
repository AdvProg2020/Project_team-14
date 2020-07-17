package Controller;

import Model.Account.Account;
import Model.Account.Boss;
import Model.Account.Customer;
import Model.Account.Salesman;
import Model.Storage;

import java.io.IOException;
import java.sql.SQLException;

public class CreditController {
    private int wagePercentage = 5;
    private int minimumCredit = 1000;
    private static CreditController creditController = new CreditController();

    public void setWagePercentage(int wagePercentage) {
        this.wagePercentage = wagePercentage;
    }

    public int getMinimumCredit() {
        return minimumCredit;
    }

    public int getWagePercentage() {
        return wagePercentage;
    }

    public void setMinimumCredit(int minimumCredit) {
        this.minimumCredit = minimumCredit;
    }

    public void addCredit(String username, int amount) {
        Account account = Storage.getAccountWithUsername(username);
        if (account == null || account instanceof Salesman || account instanceof Boss) {
            return;
        }
    }

    public static CreditController getCreditController() {
        return creditController;
    }


}
