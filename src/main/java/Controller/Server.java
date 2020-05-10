package Controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Server {
    static private boolean hasBoss;
    private AccountManager accountManager;
    private BossManager bossManager;
    private SalesmanManager salesmanManager;
    private CustomerManager customerManager;

    /*still server answer just single customer, we can change a little things to handle more than one customer
     * we change this HashMap to HashMap<String, HashMap>
     */
    private HashMap<String, String> abstractCart;

    static private String answer;

    public Server() {
        answer = "";
        this.accountManager = new AccountManager();
        this.bossManager = new BossManager();
        this.customerManager = new CustomerManager();
        this.salesmanManager = new SalesmanManager();
        abstractCart = new HashMap<>();
    }

    public static void setAnswer(String answer) {
        Server.answer = answer;
    }

    public static void setHasBoss(boolean hasBoss) {
        Server.hasBoss = hasBoss;
    }

    public HashMap<String, String> getAbstractCart() {
        return abstractCart;
    }

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
        } else if (command.startsWith("forgot password ")) {
            this.forgotPassword(command);
        } else if (command.startsWith("view personal info ")) {
            this.viewPersonalInfo(command);
        } else if (command.startsWith("edit personal info ")) {
            this.editPersonalInfo(command);
        } else if (command.startsWith("show accounts ")) {
            this.showAccounts(command);
        } else if (command.startsWith("view account info ")) {
            this.viewAccountInfo(command);
        } else if (command.startsWith("see authorization ")) {
            this.seeAuthorization(command);
        } else if (command.startsWith("delete account ")) {
            this.deleteAccount(command);
        } else if (command.startsWith("search account ")) {
            this.searchAccount(command);
        } else if (command.startsWith("show requests ")) {
            this.showRequests(command);
        }
    }

    private void showRequests(String command) {
        String[] input = command.split("\\s");
        bossManager.showRequest(input[2]);
    }

    private void searchAccount(String command) {
        String[] input = command.split("\\s");
        bossManager.searchAccount(input[3]);
    }

    private void deleteAccount(String command) {
        String[] input = command.split("\\s");
        accountManager.deleteAccount(input[2], input[3]);
    }

    private void seeAuthorization(String command) {
        String[] input = command.split("\\s");
        bossManager.seeAuthorization(input[2], input[3]);
    }

    private void viewAccountInfo(String command) {
        String[] input = command.split("\\s");
        bossManager.viewAccount(input[3], input[4]);
    }

    private int getWordCount(String input) {
        int count = 0;
        Matcher matcher = getMatcher("(\\S+)", input);
        while (matcher.find()) {
            count++;
        }
        return count;
    }

    private ArrayList<Object> getFilters(String command) {
        ArrayList<Object> filters = new ArrayList<Object>();
        String[] input = command.split("\\s");
        if (command.contains("filter")) {
            for (int i = 4; i < getWordCount(command); i += 2) {
                if (input[i].equalsIgnoreCase("sort")) {
                    break;
                }
                filters.add(input[i]);
                filters.add(input[i + 1]);
            }
        }
        return filters;
    }

    private void showAccounts(String command) {
        ArrayList<Object> filters;
        filters = getFilters(command);
        String[] input = command.split("\\s");
        bossManager.showAccounts(input[2], filters);
    }

    private void editPersonalInfo(String command) {
        String[] input = command.split("\\s");
        switch (input[3]) {
            case "firstName":
                if (checkNameFormat(input[4])) {
                    accountManager.editFirstName(input[5], input[4]);
                } else {
                    answer = "invalid first name type";
                }
                break;
            case "lastName":
                if (checkNameFormat(input[4])) {
                    accountManager.editLastName(input[5], input[4]);
                } else {
                    answer = "invalid last name type";
                }
                break;
            case "username":
                if (checkUsernameFormat(input[4])) {
                    accountManager.editUsername(input[5], input[4]);
                } else {
                    answer = "invalid username type";
                }
                break;
            case "Email":
                if (checkEmailFormat(input[4])) {
                    accountManager.editEmail(input[5], input[4]);
                } else {
                    answer = "invalid Email type";
                }
                break;
            case "telephone":
                if (checkTelephoneFormat(input[4])) {
                    accountManager.editTelephone(input[5], input[4]);
                } else {
                    answer = "invalid Telephone type";
                }
                break;
            case "password":
                if (checkUsernameFormat(input[5])) {
                    if (input[6].equals(input[5])) {
                        accountManager.editPassword(input[7], input[4], input[5]);
                    } else {
                        answer = "your new password differs from resubmit of your password";
                    }
                } else {
                    answer = "invalid password type";
                }
                break;
            case "money":
                if (checkMoneyFormat(input[4])) {
                    accountManager.editMoney(input[5], input[4]);
                } else {
                    answer = "invalid money type";
                }
                break;
            case "company":
                accountManager.editCompany(input[5], input[4]);
                break;
        }
    }

    private void viewPersonalInfo(String command) {
        String[] input = command.split("\\s");
        accountManager.viewAccountInformation(input[3]);
    }

    private void forgotPassword(String command) {
        accountManager.forgotPassword(command.split("\\s")[2]);
    }

    private void logout(String command) {
        accountManager.logout(command.split("\\s")[1]);
    }

    private void login(Matcher matcher) {
        accountManager.login(matcher.group(1), matcher.group(2));
    }

    private boolean checkMoneyFormat(String money) {
        return getMatcher("(\\d)+", money).matches();
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
        return role.equalsIgnoreCase("salesman") || role.equalsIgnoreCase("boss") || role.equalsIgnoreCase("customer");
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
