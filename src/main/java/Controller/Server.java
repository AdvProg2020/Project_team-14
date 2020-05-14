package Controller;

//import Model.Cart.Cart;
//import Model.Category.Category;
//import Model.Storage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import Exception.*;
import Model.Account.Role;
import Model.Confirmation;
import Model.Request.Enum.RequestType;
import Model.Request.Request;
import Model.Storage;

public class Server {
    static private boolean hasBoss;
    private AccountManager accountManager;
    private BossManager bossManager;
    private SalesmanManager salesmanManager;
    private CustomerManager customerManager;

    //first is username, second is a cart
    //private HashMap<String, Cart> abstractCarts;
    static private String answer;

    public Server() {
        answer = "";
        this.accountManager = new AccountManager();
        this.bossManager = new BossManager();
        this.customerManager = new CustomerManager();
        this.salesmanManager = new SalesmanManager();
        //abstractCarts = new HashMap<>();
    }

    public static void setAnswer(String answer) {
        Server.answer = answer;
    }

    public static void setHasBoss(boolean hasBoss) {
        Server.hasBoss = hasBoss;
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
        } else if (command.startsWith("make new boss ")) {
            this.makeNewBoss(command);
        } else if (command.startsWith("show requests ")) {
            this.showRequests(command);
        } else if (command.startsWith("view request ")) {
            this.viewRequest(command);
        } else if (command.startsWith("is request state checking ")) {
            this.isRequestStateChecking(command);
        } else if (command.startsWith("is account requestable ")) {
            this.isAccountRequestable(command);
        } else if (command.startsWith("search request ")) {
            this.searchRequest(command);
        } else if (command.startsWith("accept request ")) {
            this.acceptRequest(command);
        } else if (command.startsWith("decline request ")) {
            this.declineRequest(command);
        } else if (command.startsWith("delete request ")) {
            this.deleteRequest(command);
        } else if (command.startsWith("what is request username ")) {
            this.getRequestAccountUsername(command);
        } else if (command.startsWith("create category ")) {
            this.createCategory(command);
        } else if (command.startsWith("show categories ")) {
            this.showCategories(command);
        } else if (command.startsWith("view category ")) {
            this.viewCategory(command);
        } else if (command.startsWith("search category ")) {
            this.searchCategory(command);
        } else if (command.startsWith("is category exists ")) {
            this.isCategoryExists(command);
        } else if (command.startsWith("edit category name ")) {
            this.editCategoryName(command);
        } else if (command.startsWith("edit father category ")) {
            this.editFatherCategory(command);
        }
    }

    private void editCategoryName(String command) {
        bossManager.editCategoryName(command.split("\\s")[4], command.split("\\s")[5]);
    }

    private void editFatherCategory(String command) {
        bossManager.editFatherCategory(command.split("\\s")[4], command.split("\\s")[5]);
    }

    private void isCategoryExists(String command) {
        if (Storage.isThereCategoryWithName(command.split("\\s")[3])) {
            setAnswer("yes");
        } else {
            setAnswer("no");
        }
    }

    private void searchCategory(String command) {
        bossManager.searchCategory(command.split("\\s")[3]);
    }

    private void viewCategory(String command) {
        String[] input = command.split("\\s");
        bossManager.viewCategory(input[2], input[3]);
    }

    private void showCategories(String command) {
        ArrayList<Object> filters;
        filters = getFilters(command);
        String[] input = command.split("\\s");
        bossManager.showCategories(input[2], filters);
    }

    private void createCategory(String command) {
        System.out.println(command);
        Server.answer = "";
        if (!checkNameFormat(command.split("\\s")[2])) {
            Server.answer += "category name format is invalid";
        }
        if (!checkNameFormat(command.split("\\s")[3])) {
            Server.answer += "father category name format is invalid";
        }
        if (Server.answer.equals("")) {
            bossManager.createCategory(command.split("\\s")[2], command.split("\\s")[3],
                    command.split("\\s")[4]);
        }
    }

    private void getRequestAccountUsername(String command) {
        Server.setAnswer(Storage.getRequestByID(command.split("\\s")[4]).getAccountUsername());
    }

    private void deleteRequest(String command) {
        bossManager.deleteRequest(command.split("\\s")[3]);
    }

    private void declineRequest(String command) {
        Storage.getRequestByID(command.split("\\s")[3]).decline();
        Server.setAnswer("declined successfully");
    }

    private void acceptRequest(String command) {
        Storage.getRequestByID(command.split("\\s")[3]).accept();
        Server.setAnswer("accepted successfully");
    }

    private void searchRequest(String command) {
        bossManager.searchRequest(command.split("\\s")[3]);
    }

    private void isAccountRequestable(String command) {
        if (Storage.isThereAccountWithUsername(command.split("\\s")[3])) {
            if (!Storage.getAccountWithUsername(command.split("\\s")[3]).getRole().equals(Role.BOSS)) {
                Server.setAnswer("yes");
            } else {
                Server.setAnswer("no");
            }
        } else {
            Server.setAnswer("no");
        }
    }

    private void viewRequest(String command) {
        String[] input = command.split("\\s");
        bossManager.viewRequest(input[2], input[3]);
    }

    private void isRequestStateChecking(String command) {
        String[] input = command.split("\\s");
        if (Storage.getRequestByID(input[4]).getConfirmation().equals(Confirmation.CHECKING)) {
            Server.setAnswer("yes");
        } else {
            Server.setAnswer("no");
        }
    }

    private void showRequests(String command) {
        ArrayList<Object> filters;
        filters = getFilters(command);
        String[] input = command.split("\\s");
        bossManager.showRequests(input[2], filters);
    }

    private void makeNewBoss(String input) {
        Server.answer = "";
        String[] attributes = input.split("\\s");
        if (!checkNameFormat(attributes[3])) {
            Server.answer += "first name format is invalid" + "\n";
        }
        if (!checkNameFormat(attributes[4])) {
            Server.answer += "last name format is invalid" + "\n";
        }
        if (!checkUsernameFormat(attributes[5])) {
            Server.answer += "username format is invalid" + "\n";
        }
        if (!checkUsernameFormat(attributes[6])) {
            Server.answer += "password format is invalid" + "\n";
        }
        if (attributes[6].length() < 8) {
            Server.answer += "password length is not enough" + "\n";
        }
        if (!checkEmailFormat(attributes[7])) {
            Server.answer += "Email format is invalid" + "\n";
        }
        if (!checkTelephoneFormat(attributes[8])) {
            Server.answer += "telephone format is invalid" + "\n";
        }
        if (Server.answer.equals("")) {
            bossManager.register(attributes);
        }
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
                if (input[i].equalsIgnoreCase("sort:")) {
                    break;
                }
                filters.add(input[i]);
                filters.add(input[i + 1]);
            }
        }
        return filters;
    }

    private String getSortFactor(String command) {
        String[] input = command.split("\\s");
        if (command.contains("sort:")) {
            for (int i = 0; i < getWordCount(command); i++) {
                if (input[i].equalsIgnoreCase("sort:")) {
                    return input[i + 1];
                }
            }
        }
        return "none";
    }

    private String getSortType(String command) {
        String[] input = command.split("\\s");
        if (command.contains("sort:")) {
            for (int i = 0; i < getWordCount(command); i++) {
                if (input[i].equalsIgnoreCase("sort:")) {
                    return input[i + 2];
                }
            }
        }
        return "none";
    }

    private void showAccounts(String command) {
        ArrayList<Object> filters;
        filters = getFilters(command);
        String[] input = command.split("\\s");
        bossManager.showAccounts(input[2], filters, getSortFactor(command), getSortType(command));
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
    //answer:we can use something else like + for example
    //right now we won't change it but we can use + for separating different attributes of message from client

    private void register(String input) {
        Server.answer = "";
        String[] attributes = input.split("\\s");
        if (!checkNameFormat(attributes[1])) {
            Server.answer += "first name format is invalid" + "\n";
        }
        if (!checkNameFormat(attributes[2])) {
            Server.answer += "last name format is invalid" + "\n";
        }
        if (!checkUsernameFormat(attributes[3])) {
            Server.answer += "username format is invalid" + "\n";
        }
        if (!checkUsernameFormat(attributes[4])) {
            Server.answer += "password format is invalid" + "\n";
        }
        if (attributes[4].length() < 8) {
            Server.answer += "password length is not enough" + "\n";
        }
        if (!checkRoleFormat(attributes[5])) {
            Server.answer += "role is undefined" + "\n";
        }
        if (!checkEmailFormat(attributes[6])) {
            Server.answer += "Email format is invalid" + "\n";
        }
        if (!checkTelephoneFormat(attributes[7])) {
            Server.answer += "telephone format is invalid" + "\n";
        }
        if (Server.answer.equals("")) {
            if (attributes[5].equalsIgnoreCase("salesman")) {
                salesmanManager.register(attributes);
            } else if (attributes[5].equalsIgnoreCase("customer")) {
                customerManager.register(attributes);
            } else {
                if (Server.hasBoss) {
                    Server.setAnswer("a boss has already registered");
                } else {
                    bossManager.register(attributes);
                }
            }
        }
    }

    public String serverToClient() {
        return Server.answer;
    }

    /*
     * ---------[ here are common parts, server handel this by itself, no manager required ]--------
     */

    public void showBalance(String username) {
        //Server.setAnswer("Your Balance is : " + Storage.getAccountWithUsername(username).getCredit());
    }

    public void listCategories(String sortFactor) {
        /*StringBuilder ans = new StringBuilder("Here are all Categories name:");
        for (Category category : Storage.allCategories) {
            ans.append("\n").append(category.getCategoryName());
        }
        Server.setAnswer(ans.toString());*/
    }


}
