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
        Matcher matcher = pattern.matcher(command);
        return matcher;
    }

    public void clientToServer(String command) {
        Matcher matcher;
        if ((matcher = getMatcher("login (\\w+) (\\w+)", command)).find()) {
            this.login(matcher);
        } else if (command.startsWith("register ")) {
            this.register(command);
        }
    }

    private void login(Matcher matcher) {
        accountManager.login(matcher.group(1), matcher.group(2));
    }

    private boolean checkNameFormat(String name) {
        if (getMatcher("[a-zA-Z]+", name).matches()) {
            return true;
        } else {
            return false;
        }
    }

    private boolean checkUsernameFormat(String input) {
        if (getMatcher("(\\w+)", input).matches()) {
            return true;
        } else {
            return false;
        }
    }

    private boolean checkEmailFormat(String mail) {
        if (getMatcher("((\\w||\\.)+)@(\\w+)\\.(com||ir||io||edu)", mail).matches()) {
            return true;
        } else {
            return false;
        }
    }

    private boolean checkTelephoneFormat(String number) {
        if (getMatcher("0(\\d+)", number).matches() && number.length() == 11) {
            return true;
        } else {
            return false;
        }
    }

    private boolean checkRoleFormat(String role) {
        if (role.equals("salesman") || role.equals("boss") || role.equals("customer")) {
            return true;
        } else {
            return false;
        }
    }

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
            if (attributes[5].equals("salesman")) {
                salesmanManager.register(attributes);
            } else if (attributes[5].equals("customer")) {
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
