package Controller;

//import Controller.SortFactorEnum.ListOffCodesSortFactor;
//import Controller.SortFactorEnum.ListProductSortFactor;
//import Model.Category.Category;
//import Model.Off.OffCode;
//import Model.Product.Product;
//import Model.Request.Request;

import Controller.SortFactorEnum.AccountSortFactor;
import Model.Account.*;
import Model.Request.Request;
import Model.Storage;
import Exception.*;
//import org.graalvm.compiler.nodes.calc.ObjectEqualsNode;

//import java.text.ParseException;
import java.rmi.ServerError;
import java.util.ArrayList;
import java.util.Comparator;

public class BossManager {

    /*
     * this is Account part
     */

    public void register(String[] information) {
        if (information[0].equalsIgnoreCase("register")) {
            if (Storage.isThereAccountWithUsername(information[3])) {
                Server.setAnswer("the username is already taken, try something else");
            }
            Server.setAnswer("register successful");
            Server.setHasBoss(true);
            new Boss(information[3], information[4], information[1], information[2], information[6], information[7],
                    information[5]).setFatherBoss(null);
        } else {
            if (Storage.isThereAccountWithUsername(information[5])) {
                Server.setAnswer("the username is already taken, try something else");
            }
            Server.setAnswer("register successful");
            Server.setHasBoss(true);
            new Boss(information[5], information[6], information[3], information[4], information[7], information[8],
                    "BOSS").setFatherBoss(information[9]);

        }
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
            if ((account).getCredit() >= Integer.parseInt(filter)) {
                return true;
            } else {
                return false;
            }
        } else if (account instanceof Salesman) {
            if ((account).getCredit() >= Integer.parseInt(filter)) {
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
            if (account.getCredit() <= Integer.parseInt(filter)) {
                return true;
            } else {
                return false;
            }
        } else if (account instanceof Salesman) {
            if (account.getCredit() <= Integer.parseInt(filter)) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    public void viewAccount(String bossUsername, String username) {
        Account account = Storage.getAccountWithUsername(username);
        Server.setAnswer(account.toString());
    }

    public void searchAccount(String username) {
        if (Storage.isThereAccountWithUsername(username)) {
            Server.setAnswer("search completed");
        } else {
            Server.setAnswer("no account exist with this username");
        }
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

    public void showAccounts(String username, ArrayList<Object> filters, String sortFactor, String sortType) {
        int count = 0;
        ArrayList<Account> accounts = Storage.getAllAccounts();
        if (!sortFactor.equalsIgnoreCase("none")) {
            AccountSortFactor.sort(sortFactor, accounts);
        }
        StringBuilder answer = new StringBuilder("All Accounts Username:").append("\n");
        if (accounts.size() == 0) {
            Server.setAnswer("no account found with this username");
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

    private int countWordsBySemicolon(String str) {
        int counter = 0;
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == ',') {
                counter++;
            }
        }
        return counter + 1;
    }

    private boolean checkUsernameFilter(Request request, String username) {
        String[] usernames = username.split(",");
        int usernameCount = countWordsBySemicolon(username);
        for (int i = 0; i < usernameCount; i++) {
            if (usernames[i].equals(request.getAccountUsername())) {
                return true;
            }
        }
        return false;
    }

    private boolean checkRequestTypeFilter(Request request, String requestType) {
        if (requestType.contains(request.getRequestType().toString())) {
            return true;
        } else {
            return false;
        }
    }

    private boolean isRequestInFilter(Request request, ArrayList<Object> filters) {
        for (int i = 0; i < filters.size(); i += 2) {
            if (((String) filters.get(i)).equalsIgnoreCase("username")) {
                if (checkUsernameFilter(request, (String) filters.get(i + 1)) == false) {
                    return false;
                }
            }
            if (((String) filters.get(i)).equalsIgnoreCase("requestType")) {
                if (checkRequestTypeFilter(request, (String) filters.get(i + 1)) == false) {
                    return false;
                }
            }
        }
        return true;
    }

    public void showRequests(String username, ArrayList<Object> filters) {
        int count = 0;
        ArrayList<Request> requests = Storage.getAllRequests();
        StringBuilder answer = new StringBuilder("");
        if (requests.size() == 0) {
            Server.setAnswer("nothing found");
        } else {
            for (Request request : requests) {
                if (isRequestInFilter(request, filters)) {
                    answer.append(request.toStringForBoss() + "\n");
                    count++;
                }
            }
            if (count == 0) {
                answer = new StringBuilder("nothing found");
            } else {
                answer.append("here are what we found");
            }
            //if (!account.getUsername().equals(username))
            //answer.append("Username: ").append(account.getUsername());    //just list users IDs, then he will select one
        }
        String ans = answer.toString();
        Server.setAnswer(ans);
    }

    public void viewRequest(String username, String requestID) {
        Request request = Storage.getRequestByID(requestID);
        Server.setAnswer(request.toString());
    }

    public void searchRequest(String requestID) {
        if (Storage.isThereRequestByID(requestID)) {
            Request request = Storage.getRequestByID(requestID);
            Server.setAnswer("search completed " + request.getRequestType());
        } else {
            Server.setAnswer("no request exists with such ID");
        }
    }

    /*public void deleteUser(String username) {
        //should we check equality? (users can't remove themselves :|)
        //we can handel error whit exception
        //I add so users can delete themselves too
        //and we need to check what thing should be deleted when a user get deleted
        //we have it in account menu to
        //and in edit username menu we should edit the username in for example request so i'm gonna fix it too :)
        if (Storage.getAccountWithUsername(username) == null) {
            Server.setAnswer("error, there isn't any user with this username, try another username");
        } else {
            //Storage.allAccounts.remove(Storage.getAccountWithUsername(username));
            Server.setAnswer("user successfully removed");
        }
    }*/

    //public void createManager(String[] info)   ---   should we have such a thing??

    /*public void listAllProducts(String sortFactor) throws SortFactorNotAvailableException {
        ArrayList<Product> products = new ArrayList<>(Storage.allProducts);
        ListProductSortFactor.sort(sortFactor, products);
        StringBuilder ans = new StringBuilder("Here are All products:").append("\n");
        for (Product product : products) {
            ans.append(product.toStringForBossView());
        }
        Server.setAnswer(ans.toString());
    }*/

    //it gets us possible sort factors

    /*public String getSortFactorsForListingAllProducts() {
        return ListProductSortFactor.getValues();
    }*/

    /*public void deleteProduct(String productID) {
        if (Product.getProductWithID(productID) == null) {
            Server.setAnswer("error, there isn't any product whit this ID");
        } else {
            Storage.allProducts.remove(Product.getProductWithID(productID));
            Server.setAnswer("product with id [" + productID + "] removed successfully");
        }
    }*/

    /*
     * this is discount codes part
     */

    //info: 0 -> start, 1 -> end, 2 -> percentage, 3 -> ceiling, 4 -> numberOfTimeCanBeUsed

    /*public void createDiscountCode(String[] info, ArrayList<String> usernameCanUse) throws ParseException {
        new OffCode(info[0], info[1], Integer.parseInt(info[2]), Integer.parseInt(info[3]), Integer.parseInt(info[4]), usernameCanUse);
        Server.setAnswer("offCode created successfully");
    }

    public void listAllOffCodes(String sortFactor) throws SortFactorNotAvailableException {
        ArrayList<OffCode> offCodes = new ArrayList<>(Storage.allOffCodes);
        StringBuilder ans = new StringBuilder("Here are All OffCodes:").append("\n");
        ListOffCodesSortFactor.sort(sortFactor, offCodes);
        for (OffCode offCode : offCodes) {
            ans.append(offCode.toString());
        }
        Server.setAnswer(ans.toString());
    }

    public static String getSortFactorsForListingOffCodes() {
        return ListOffCodesSortFactor.getValues();
    }

    public void viewOffCode(String offCodeID) throws InvalidOffCodeException {
        OffCode offCode = OffCode.getOffCodeByID(offCodeID);
        if (offCode == null) {
            throw new InvalidOffCodeException("off code ID is not authentic");
        } else {
            Server.setAnswer("OffCode detail: " + "\n" + offCode.toString());
        }
    }

    public void editOffCode(String offCodeID, String attribute, String updatedInfo) throws ParseException, InvalidOffCodeException {
        OffCode offCode = OffCode.getOffCodeByID(offCodeID);
        if (offCode == null) {
            throw new InvalidOffCodeException("off code ID is not authentic");
        } else {
            switch (attribute) {
                case "end":
                    offCode.setEnd(updatedInfo);
                    break;
                case "percentage":
                    offCode.setPercentage(updatedInfo);
                    break;
                case "ceiling":
                    offCode.setCeiling(Integer.parseInt(updatedInfo));
                    break;
                case "numberOfTimesCanBeUsed":
                    offCode.setNumberOfTimesCanBeUsed(Integer.parseInt(updatedInfo));
                    break;
            }
            Server.setAnswer("you updated offCode with ID [" + offCodeID + "]");
        }
    }

    public void removeOffCode(String offCodeID) throws InvalidOffCodeException {
        if (OffCode.getOffCodeByID(offCodeID) == null) {
            throw new InvalidOffCodeException("off code ID is not authentic");
        } else {
            Storage.allOffCodes.remove(OffCode.getOffCodeByID(offCodeID));
            Server.setAnswer("offCode with ID [" + offCodeID + "] removed successfully");
        }
    }*/

    /*
     * this is request part
     */

    /*public void listAllRequests() {
        StringBuilder ans = new StringBuilder("Here are All Requests:").append("\n");
        for (Request request : Request.getCheckingRequests()) {
            ans.append("Request ID: ").append(request.getRequestID()).append(", Request Type: ").append(request.getRequestType());
        }
        Server.setAnswer(ans.toString());
    }*/

    /*public void viewRequest(String requestID) throws InvalidRequestIDException {
        Request request = Request.getRequestByID(requestID);
        if (request == null) {
            throw new InvalidRequestIDException("Request ID is not authentic");
        } else {
            Server.setAnswer(request.toString());
        }
    }*/

    /*public void acceptRequest(String requestID) throws ParseException, InvalidRequestIDException {
        Request request = Request.getRequestByID(requestID);
        if (request == null) {
            throw new InvalidRequestIDException("Request ID is not authentic");
        } else {
            request.accept();
            Server.setAnswer("you accepted request with [" + requestID + "] ID, which was a [" + request.getRequestType() + "] request");
        }
    }*/

    /*public void declineRequest(String requestID) throws InvalidRequestIDException {
    public void declineRequest(String requestID) {
        Request request = Request.getRequestByID(requestID);
        if (request == null) {
            throw new InvalidRequestIDException("Request ID is not authentic");
        } else {
            request.decline();
            Server.setAnswer("you just declined request with [" + requestID + "] ID, which was a [" + request.getRequestType() + "] request");
        }
    }*/

    /*
     * this is category part
     */

    //info: 0 -> attribute, 1 -> parent category name, 2 -> category name

    /*public void addCategory(String[] info) {
        new Category(info[0], info[1], info[2]);
    }*/

    /*public void editCategory(String categoryName, String attribute, String updatedInfo) throws InvalidCategoryNameException {
        Category category = Category.getCategoryByName(categoryName);
        if (category == null) {
            throw new InvalidCategoryNameException("there's no category with this name");
        } else {
            switch (attribute) {
                case "attribute":
                    category.setAttribute(updatedInfo);
                    break;
                case "addProduct":
                    category.addProductToCategory(updatedInfo);
                    break;
                case "removeProduct":
                    category.deleteProductFromCategory(updatedInfo);
                    break;
            }
            Server.setAnswer("your changes updated successfully");
        }
    }*/

    /*public void removeCategory(String categoryName) throws InvalidCategoryNameException {
        Category category = Category.getCategoryByName(categoryName);
        if (category == null) {
            throw new InvalidCategoryNameException("there's no category with this name");
        } else {
            Storage.allCategories.remove(category);
            for (String productID : category.getAllProductIDs()) {
                category.deleteProductFromCategory(productID);
            }
            Server.setAnswer("category deleted successfully");
        }
    }*/

}
