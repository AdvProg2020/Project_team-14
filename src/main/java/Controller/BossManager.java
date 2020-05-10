package Controller;

import Controller.SortFactorEnum.ListOffCodesSortFactor;
import Controller.SortFactorEnum.listProductSortFactor;
import Model.Account.Account;
import Model.Account.Boss;
import Model.Category.Category;
import Model.Off.OffCode;
import Model.Product.Product;
import Model.Request.Request;
import Model.Account.*;
import Model.Request.Request;
import Model.Storage;
import Exception.*;

import java.text.ParseException;
import java.rmi.ServerError;
import java.util.ArrayList;
import java.util.Comparator;

public class BossManager {

    /*
     * this is Account part
     */

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

    public void showAccounts(String username, ArrayList<Object> filters) {
        int count = 0;
        ArrayList<Account> accounts = Storage.getAllAccounts();
        StringBuilder answer = new StringBuilder("All Accounts Username:").append("\n");
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

    public void showRequest(String username) {
        int count = 0;
        ArrayList<Request> requests = Storage.getAllRequests();
        StringBuilder answer = new StringBuilder("");
        if (requests.size() == 0) {
            Server.setAnswer("nothing found");
        } else {
            for (Request request : requests) {
                //answer.append(request.toStringForBoss() + "\n");
                count++;
            }
            if (count == 0) {
                answer = new StringBuilder("nothing found");
            } else {
                answer.append("here are what we found");
            }
            //if (!account.getUsername().equals(username))
                //answer.append("Username: ").append(account.getUsername());    //just list users IDs, then he will select one
        }
        answer.append("here are what we found").append("\n");
        String ans = answer.toString();
        Server.setAnswer(ans);
    }

    //can be handel in AccountManager(this class has the same method)
    public void showUsersInfo(String username) {
        Account account = Storage.getAccountWithUsername(username);
        assert account != null;
        Server.setAnswer(account.toString());
    }

    public void deleteUser(String username) {
        //should we check equality? (users can't remove themselves :|)
        //we can handel error whit exception
        if (Storage.getAccountWithUsername(username) == null) {
            Server.setAnswer("error, there isn't any user with this username, try another username");
        } else {
            //Storage.allAccounts.remove(Storage.getAccountWithUsername(username));
            Server.setAnswer("user successfully removed");
        }
    }

    //public void createManager(String[] info)   ---   should we have such a thing??

    public void listAllProducts(String sortFactor) throws SortFactorNotAvailableException {
        ArrayList<Product> products = new ArrayList<>(Storage.allProducts);
        if (sortFactor.equals(listProductSortFactor.ALPHABETICALLY.name())) {
            products.sort(Comparator.comparing(Product::getName));
        } else if (sortFactor.equals(listProductSortFactor.HIGHEST_POINT.name())) {
            products.sort(Comparator.comparingDouble(Product::getAveragePoint));
        } else if (sortFactor.equals(listProductSortFactor.PRICE.name())) {
            products.sort(Comparator.comparingInt(Product::getMinimumPrice));
        } else if (sortFactor.equals(listProductSortFactor.SEEN_COUNT.name())) {
            products.sort(Comparator.comparingInt(Product::getSeenCount));
        } else if (!sortFactor.isEmpty()) {
            throw new SortFactorNotAvailableException("the sort factor isn't authentic " + "\n" +
                    "the available sort factors: " + getSortFactorsForListingAllProducts() + "\n");
        }
        StringBuilder ans = new StringBuilder("Here are All products:").append("\n");
        for (Product product : products) {
            ans.append(product.toStringForBossView());
        }
        Server.setAnswer(ans.toString());
    }

    //it gets us possible sort factors

    public String getSortFactorsForListingAllProducts() {
        return listProductSortFactor.getValues();
    }

    public void deleteProduct(String productID) {
        if (Product.getProductWithID(productID) == null) {
            Server.setAnswer("error, there isn't any product whit this ID");
        } else {
            Storage.allProducts.remove(Product.getProductWithID(productID));
            Server.setAnswer("product with id [" + productID + "] removed successfully");
        }
    }

    /*
     * this is discount codes part
     */

    //info: 0 -> start, 1 -> end, 2 -> percentage, 3 -> ceiling, 4 -> numberOfTimeCanBeUsed


    public void createDiscountCode(String[] info, ArrayList<String> usernameCanUse) throws ParseException {
        new OffCode(info[0], info[1], Integer.parseInt(info[2]), Integer.parseInt(info[3]), Integer.parseInt(info[4]), usernameCanUse);
        Server.setAnswer("offCode created successfully");
    }

    public void listAllOffCodes(String sortFactor) throws SortFactorNotAvailableException {
        ArrayList<OffCode> offCodes = new ArrayList<>(Storage.allOffCodes);
        if (sortFactor.equals(ListOffCodesSortFactor.END_DATE.name())) {
            offCodes.sort(Comparator.comparing(OffCode::getEnd));
        } else if (sortFactor.equals(ListOffCodesSortFactor.NUMBER_OF_TIMES_IT_CAN_BE_STILL_USED.name())) {
            offCodes.sort(Comparator.comparingInt(OffCode::getNumberOfTimesCanBeUsed));
        } else if (sortFactor.equals(ListOffCodesSortFactor.CEILING.name())) {
            offCodes.sort(Comparator.comparingInt(OffCode::getCeiling));
        } else if (sortFactor.equals(ListOffCodesSortFactor.PERCENTAGE.name())) {
            offCodes.sort(Comparator.comparingInt(OffCode::getPercentage));
        } else if (!sortFactor.isEmpty()) {
            throw new SortFactorNotAvailableException("the sort factor isn't authentic " + "\n" +
                    "the available sort factors: " + getSortFactorsForListingOffCodes());
        }
        StringBuilder ans = new StringBuilder("Here are All OffCodes:").append("\n");
        for (OffCode offCode : offCodes) {
            ans.append(offCode.toString());
        }
        Server.setAnswer(ans.toString());
    }

    public String getSortFactorsForListingOffCodes() {
        return ListOffCodesSortFactor.getValues();
    }

    public void viewOffCode(String offCodeID) {
        OffCode offCode = OffCode.getOffCodeByID(offCodeID);
        if (offCode == null) {
            Server.setAnswer("error, there isn't exist any offCode with this ID" + "\n");
        } else {
            Server.setAnswer("OffCode detail: " + "\n" + offCode.toString());
        }
    }

    public void editOffCode(String offCodeID, String attribute, String updatedInfo) throws ParseException {
        OffCode offCode = OffCode.getOffCodeByID(offCodeID);
        if (offCode == null) {
            Server.setAnswer("error, there isn't exist any offCode whit this ID");
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

    public void removeOffCode(String offCodeID) {
        if (OffCode.getOffCodeByID(offCodeID) == null) {
            Server.setAnswer("error, offCode doesn't exist");
        } else {
            Storage.allOffCodes.remove(OffCode.getOffCodeByID(offCodeID));
            Server.setAnswer("offCode with ID [" + offCodeID + "] removed successfully");
        }
    }

    /*
     * this is request part
     */

    //sort isn't complete
    public void listAllRequests(String sortFactor) {
        StringBuilder ans = new StringBuilder("Here are All Requests:").append("\n");
        for (Request request : Request.getCheckingRequests()) {
            ans.append("Request ID: ").append(request.getRequestID()).append(", Request Type: ").append(request.getRequestType());
        }
        Server.setAnswer(ans.toString());
    }

    /*public void viewRequest(String requestID) {
        Request request = Request.getRequestByID(requestID);
        if (request == null) {
            Server.setAnswer("request doesn't exist with this ID");
        } else {
            Server.setAnswer(request.toString());
        }
    }*/

    /*public void acceptRequest(String requestID) throws ParseException {
        Request request = Request.getRequestByID(requestID);
        if (request == null) {
            Server.setAnswer("request doesn't exist");
        } else {
            request.accept();
            Server.setAnswer("you accepted request with [" + requestID + "] ID, which was a [" + request.getRequestType() + "] request");
        }
    }*/

    /*public void declineRequest(String requestID) {
        Request request = Request.getRequestByID(requestID);
        if (request == null) {
            Server.setAnswer("request doesn't exist");
        } else {
            request.decline();
            Server.setAnswer("you just declined request with [" + requestID + "] ID, which was a [" + request.getRequestType() + "] request");
        }
    }*/

    /*
     * this is category part
     */

    //info: 0 -> attribute, 1 -> parent category name, 2 -> category name
    public void addCategory(String[] info) {
        new Category(info[0], info[1], info[2]);
    }

    public void editCategory(String categoryName, String attribute, String updatedInfo) {
        Category category = Category.getCategoryByName(categoryName);
        if (category == null) {
            Server.setAnswer("error, there isn't exist any category with this name");
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
    }

    public void removeCategory(String categoryName) {
        Category category = Category.getCategoryByName(categoryName);
        if (category == null) {
            Server.setAnswer("there isn't any category with this name exist");
        } else {
            Storage.allCategories.remove(category);
            for (String productID : category.getAllProductIDs()) {
                category.deleteProductFromCategory(productID);
            }
            Server.setAnswer("category deleted successfully");
        }
    }

}