package Controller;


import Model.Account.Salesman;
import Model.Log.SellLog;
import Model.Storage;
import Exception.*;

public class SalesmanManager {
    public void register(String[] information) throws UsernameAlreadyExistException {
        if (Storage.isThereAccountWithUsername(information[3])) {
            throw new UsernameAlreadyExistException("user name is already taken, try something else");
        }
        Server.setAnswer("register successful");
        new Salesman(information[3], information[4], information[1], information[2],
                information[6], information[7], information[5], information[8]);
    }

    public void showCompanyInfo(String salesmanID) {
        Salesman salesman = (Salesman) Storage.getAccountWithUsername(salesmanID);
        assert salesman != null;
        Server.setAnswer("Salesman Company Info : " + salesman.getCompany());
    }

    public String getSalesHistory(String salesmanID) {
        Salesman salesman = (Salesman) Storage.getAccountWithUsername(salesmanID);
        return SellLog.getSalesmanSellLogs_StringFormatted(salesmanID);
    }
}
