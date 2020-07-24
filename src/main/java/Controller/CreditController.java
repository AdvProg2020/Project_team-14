package Controller;

import Model.Account.Account;
import Model.Account.Boss;
import Model.Account.Customer;
import Model.Account.Salesman;
import Model.Storage;

import java.io.IOException;
import java.io.Serializable;
import java.sql.SQLException;

public class CreditController implements Serializable {
    private int wagePercentage;
    private int minimumCredit;
    public static CreditController creditController = new CreditController(5, 1000);

    public CreditController(int wagePercentage, int minimumCredit) {
        this.wagePercentage = wagePercentage;
        this.minimumCredit = minimumCredit;
    }

    public void setWagePercentage(int wagePercentage) {
        creditController.wagePercentage = wagePercentage;
    }

    public int getMinimumCredit() {
        return creditController.minimumCredit;
    }

    public int getWagePercentage() {
        return creditController.wagePercentage;
    }

    public void setMinimumCredit(int minimumCredit) {
        creditController.minimumCredit = minimumCredit;
    }

    public static CreditController getCreditController() {
        return creditController;
    }

    public static void setCreditController(CreditController creditController) {
        CreditController.creditController = creditController;
    }

}
