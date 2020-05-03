package Controller;

import Model.Account.*;
import Model.Storage;

import java.rmi.ServerError;
import java.util.ArrayList;

public class BossManager {

    public void register(String[] information) {
        if (Storage.isThereAccountWithUsername(information[3])) {
            Server.setAnswer("username has already been taken");
        }
        Server.setAnswer("register successful");
        Server.setHasBoss(true);
        new Boss(information[3], information[4], information[1], information[2], information[6], information[7],
                information[5]).setFatherBoss(null);
    }

    private boolean checkRoleFilter(Account account, String filter) {
        if (filter.contains("boss") && account.getRole().equals(Role.BOSS)) {
            return true;
        } else if (filter.contains("customer") && account.getRole().equals(Role.CUSTOMER)) {
            return true;
        } else if (filter.contains("salesman") && account.getRole().equals(Role.SALESMAN)) {
            return true;
        } else {
            return false;
        }
    }

    private boolean checkMinCreditFilter(Account account, String filter) {
        if (account instanceof Boss) {
            return true;
        } else if (account instanceof Customer) {
            if (((Customer) account).getCredit() >= Integer.parseInt(filter)) {
                return true;
            } else {
                return false;
            }
        } else if (account instanceof Salesman) {
            if (((Salesman) account).getCredit() >= Integer.parseInt(filter)) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    private boolean checkMaxCreditFilter(Account account, String filter) {
        if (account instanceof Boss) {
            return true;
        } else if (account instanceof Customer) {
            if (((Customer) account).getCredit() <= Integer.parseInt(filter)) {
                return true;
            } else {
                return false;
            }
        } else if (account instanceof Salesman) {
            if (((Salesman) account).getCredit() <= Integer.parseInt(filter)) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    public void viewAccount(String bossUsername, String username) {
        Account boss = Storage.getAccountWithUsername(bossUsername);
        Account account = Storage.getAccountWithUsername(username);
        Server.setAnswer(account.toString());
    }

    private boolean isAccountInFilter(Account account, ArrayList<Object> filters) {
        for (int i = 0; i < filters.size(); i += 2) {
            if (((String) filters.get(i)).equalsIgnoreCase("role")) {
                if (checkRoleFilter(account, (String) filters.get(i + 1)) == false) {
                    return false;
                }
            }
            if (((String) filters.get(i)).equalsIgnoreCase("minCredit")) {
                if (checkMinCreditFilter(account, (String) filters.get(i + 1)) == false) {
                    return false;
                }
            }
            if (((String) filters.get(i)).equalsIgnoreCase("maxCredit")) {
                if (checkMaxCreditFilter(account, (String) filters.get(i + 1)) == false) {
                    return false;
                }
            }
        }
        return true;
    }

    public void showAccounts(String username, ArrayList<Object> filters) {
        int count = 0;
        ArrayList<Account> accounts = Storage.getAllAccounts();
        StringBuilder answer = new StringBuilder("");
        if (accounts.size() == 0) {
            Server.setAnswer("nothing found");
        } else {
            for (Account account : accounts) {
                if (!account.getUsername().equals(username) && isAccountInFilter(account, filters)) {
                    answer.append(account.toStringForBoss() + "\n");
                    count++;
                }
            }
            if (count == 0) {
                answer = new StringBuilder("nothing found");
            } else {
                answer.append("here are what we found");
            }
            String ans = answer.toString();
            Server.setAnswer(ans);
        }
    }

    public void seeAuthorization(String bossUsername, String username) {
        Account account = Storage.getAccountWithUsername(username);
        if (account instanceof Salesman) {
            Server.setAnswer("salesman");
        } else if (account instanceof Customer) {
            Server.setAnswer("customer");
        } else {
            while (true) {
                String fatherBoss = ((Boss) account).getFatherBoss();
                if (fatherBoss == null) {
                    Server.setAnswer("boss no");
                    break;
                }
                if (fatherBoss.equals(bossUsername)) {
                    Server.setAnswer("boss yes");
                    break;
                }
                account = Storage.getAccountWithUsername(fatherBoss);
            }
        }
    }

    public static void changeFathers(String bossAccount, String username) {
        for (Account account : Storage.getAllAccounts()) {
            if (((Boss) account).getFatherBoss().equals(username)) {
                ((Boss) account).setFatherBoss(bossAccount);
            }
        }
    }
    /*
    private CategoryManager categoryManager;
    private OffCodeManager offCodeManager;
    private RequestManager requestManager;

    public BossManager() {
        categoryManager = new CategoryManager();
        offCodeManager = new OffCodeManager();
        requestManager = new RequestManager();
    }

     */
}