package Controller;

import Model.Account.Account;
import Model.Account.Customer;
import Model.Product.Product;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Server {
    static private boolean hasBoss;
    private AccountManager accountManager;
    private BossManager bossManager;
    private SalesmanManager salesmanManager;
    private CustomerManager customerManager;

    static private String answer;

    public Server() {
        answer = "";
        this.accountManager = new AccountManager();
        this.bossManager = new BossManager();
        this.customerManager = new CustomerManager();
        this.salesmanManager = new SalesmanManager();
    }

    public static void setAnswer(String answer) {
        Server.answer = answer;
    }

    public static void setHasBoss(boolean hasBoss) {
        Server.hasBoss = hasBoss;
    }

    /*
            private ArrayList<Account> loginUsers;
            private LoginManager loginManager;
            private PageManager pageManager;
            private ProductPageManager productPageManager;
            private ArrayList<Product> abstractCart;

            public Server() {
                loginUsers = new ArrayList<>();
                loginManager = new LoginManager();
                bossManager = new BossManager();
                customerManager = new CustomerManager();
                salesmanManager = new SalesmanManager();
                pageManager = new PageManager();
                productPageManager = new ProductPageManager();
                abstractCart = new ArrayList<>();
            }

             */
    private Matcher getMatcher(String regex, String command) {
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(command);
    }

    public void clientToServer(String command) {
        Matcher matcher;
        if ((matcher = getMatcher("login (\\w+) (\\w+)", command)).find()) {
            this.login(matcher);
        } else if (command.startsWith("register ")) {
            this.register(command);
        } else if (command.startsWith("logout ")) {
            this.logout(command);
        }
    }

    private void logout(String command) {
        accountManager.logout(command.split("\\s")[1]);
    }

    private void login(Matcher matcher) {
        accountManager.login(matcher.group(1), matcher.group(2));
    }

    private boolean checkNameFormat(String name) {
        return getMatcher("[a-zA-Z]+", name).matches();
    }

    private boolean checkUsernameFormat(String input) {
        return getMatcher("(\\w+)", input).matches();
    }

    private boolean checkEmailFormat(String mail) {
        return getMatcher("((\\w||\\.)+)@(\\w+)\\.(com||ir||io||edu)", mail).matches();
    }

    private boolean checkTelephoneFormat(String number) {
        return getMatcher("0(\\d+)", number).matches() && number.length() == 11;
    }

    private boolean checkRoleFormat(String role) {
        return role.equals("salesman") || role.equals("boss") || role.equals("customer");
    }

    //we should make sure that each word doesn't contain any space otherwise
    //we will reach trouble spiting it with "\\s"

    private void register(String input) {
        Server.answer = "";
        String[] attributes = input.split("\\s");
        if (!checkNameFormat(attributes[1])) {
            Server.answer += "first name format is invalid\n";
        }
        if (!checkNameFormat(attributes[2])) {
            Server.answer += "last name format is invalid\n";
        }
        if (!checkUsernameFormat(attributes[3])) {
            Server.answer += "username format is invalid\n";
        }
        if (!checkUsernameFormat(attributes[4])) {
            Server.answer += "password format is invalid\n";
        }
        if (attributes[4].length() < 8) {
            Server.answer += "password length is not enough\n";
        }
        if (!checkRoleFormat(attributes[5])) {
            Server.answer += "role is undefined\n";
        }
        if (!checkEmailFormat(attributes[6])) {
            Server.answer += "Email format is invalid\n";
        }
        if (!checkTelephoneFormat(attributes[7])) {
            Server.answer += "telephone format is invalid\n";
        }
        if (Server.answer.equals("")) {
            if (attributes[5].equalsIgnoreCase("salesman")) {
                salesmanManager.register(attributes);
            } else if (attributes[5].equalsIgnoreCase("customer")) {
                customerManager.register(attributes);
            } else {
                if (Server.hasBoss) {
                    Server.answer = "a boss has already been registered";
                } else {
                    bossManager.register(attributes);
                }
            }
        }
    }

    public String serverToClient() {
        return Server.answer;
    }
}
