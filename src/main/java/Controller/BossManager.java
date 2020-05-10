package Controller;

import Model.Account.Account;
import Model.Account.Boss;
import Model.Category.Category;
import Model.Off.OffCode;
import Model.Product.Product;
import Model.Request.Request;
import Model.Storage;

import java.text.ParseException;
import java.util.ArrayList;

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
        new Boss(information[3], information[4], information[1], information[2], information[6], information[7], information[5]);

    }

    public void showAccounts(String username) {
        ArrayList<Account> accounts = Storage.getAllAccounts();
        StringBuilder answer = new StringBuilder("All Accounts Username:");
        if (accounts.size() == 0) {
            Server.setAnswer("nothing found");
        } else {
            for (Account account : accounts) {
                if (!account.getUsername().equals(username))
                    answer.append("\nUsername: " + account.getUsername());    //just list users IDs, then he will select one
            }
            answer.append("here are what we found");
            String ans = answer.toString();
            Server.setAnswer(ans);
        }
    }

    //can be handel in AccountManager(this class has the same method)
    public void showUsersInfo(String username) {
        Account account = Storage.getAccountWithUsername(username);
        Server.setAnswer(account.toString());
    }

    public void deleteUser(String username) {
        //should we check equality? (users can't remove themselves :|)
        //we can handel error whit exception
        if (Storage.getAccountWithUsername(username) == null) {
            Server.setAnswer("error, there isn't any user with this username, try another username");
        } else {
            Storage.allAccounts.remove(Storage.getAccountWithUsername(username));
            Server.setAnswer("user successfully removed");
        }
    }

    //public void createManager(String[] info)   ---   should we have such a thing??

    //sort is not complete
    public void listAllProducts(String sortFactor) {
        StringBuilder ans = new StringBuilder("Here are All products:");
        for (Product product : Storage.allProducts) {
            ans.append("\nProduct ID: " + product.getProductID() + ", Product Name: " + product.getName());
        }
        Server.setAnswer(ans.toString());
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

    //sort isn't complete
    public void listAllOffCodes(String sortFactor) {
        StringBuilder ans = new StringBuilder("Here are All OffCodes:");
        for (OffCode offCode : Storage.allOffCodes) {
            ans.append("\noffCodeID: " + offCode.getOffCodeID());
        }
        Server.setAnswer(ans.toString());
    }

    public void viewOffCode(String offCodeID) {
        OffCode offCode = OffCode.getOffCodeByID(offCodeID);
        if (offCode == null) {
            Server.setAnswer("error, there isn't exist any offCode with this ID");
        } else {
            Server.setAnswer("OffCode detail: \n" + offCode.toString());
        }
    }

    public void editOffCode(String offCodeID, String attribute, String updatedInfo) throws ParseException {
        OffCode offCode = OffCode.getOffCodeByID(offCodeID);
        if (offCode == null) {
            Server.setAnswer("error, there isn't exist any offCode whit this ID");
        } else {
            if (attribute.equals("end")) {
                offCode.setEnd(updatedInfo);
            } else if (attribute.equals("percentage")) {
                offCode.setPercentage(updatedInfo);
            } else if (attribute.equals("ceiling")) {
                offCode.setCeiling(Integer.parseInt(updatedInfo));
            } else if (attribute.equals("numberOfTimesCanBeUsed")) {
                offCode.setNumberOfTimesCanBeUsed(Integer.parseInt(updatedInfo));
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
        StringBuilder ans = new StringBuilder("Here are All Requests:");
        for (Request request : Request.getCheckingRequests()) {
            ans.append("\nRequest ID: " + request.getRequestID() + ", Request Type: " + request.getRequestType());
        }
        Server.setAnswer(ans.toString());
    }

    public void viewRequest(String requestID) {
        Request request = Request.getRequestByID(requestID);
        if (request == null) {
            Server.setAnswer("request doesn't exist with this ID");
        } else {
            Server.setAnswer(request.toString());
        }
    }

    public void acceptRequest(String requestID) throws ParseException {
        Request request = Request.getRequestByID(requestID);
        if (request == null) {
            Server.setAnswer("request doesn't exist");
        } else {
            request.accept();
            Server.setAnswer("you accepted request with [" + requestID +"] ID, which was a [" + request.getRequestType() + "] request");
        }
    }

    public void declineRequest(String requestID) {
        Request request = Request.getRequestByID(requestID);
        if (request == null) {
            Server.setAnswer("request doesn't exist");
        } else {
            request.decline();
            Server.setAnswer("you just declined request with [" + requestID + "] ID, which was a [" + request.getRequestType() + "] request");
        }
    }

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
            if (attribute.equals("attribute")) {
                category.setAttribute(updatedInfo);
            } else if (attribute.equals("addProduct")) {
                category.addProductToCategory(updatedInfo);
            } else if (attribute.equals("removeProduct")) {
                category.deleteProductFromCategory(updatedInfo);
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