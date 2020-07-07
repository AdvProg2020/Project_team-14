package Controller;

//import Model.Cart.Cart;
//import Model.Category.Category;
//import Model.Storage;

import java.io.IOException;
import java.net.ServerSocket;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import Controller.DataBase.EndOfProgramme;
import Controller.DataBase.startOfProgramme;
import GUI.Supporter.Controller;
import Menus.shows.ShowRequestsMenu;
import Model.Account.Account;
import Model.Account.Customer;
import Model.Account.Role;
import Model.Account.Salesman;
import Model.Cart.Cart;
import Model.Category.Category;
import Model.Confirmation;
import Model.Log.BuyLog;
import Model.Log.Log;
import Model.Off.OffCode;
import Model.Off.Sale;
import Model.Product.Comment;
import Model.Product.Point;
import Model.Product.Product;
import Model.Request.Request;
import Model.Storage;

import static Controller.Security.Methods.*;

public class Server {
    static private boolean hasBoss;
    private AccountManager accountManager;
    private ProductManager productManager;
    private BossManager bossManager;
    private SalesmanManager salesmanManager;
    private CustomerManager customerManager;
    private EndOfProgramme endOfProgramme = new EndOfProgramme();

    //first is username, second is a cart
    //private HashMap<String, Cart> abstractCarts;
    static private String answer;

    public Server() throws IOException, ClassNotFoundException {
        answer = "";
        this.accountManager = new AccountManager();
        this.bossManager = new BossManager();
        this.customerManager = new CustomerManager();
        this.salesmanManager = new SalesmanManager();
        this.productManager = new ProductManager();
        startOfProgramme startOfProgramme = new startOfProgramme();
        startOfProgramme.startProgramme();
        hasBoss = (Storage.getAllBosses().size() != 0);
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

    public void clientToServer(String command) throws ParseException {

        //running security checks

        if (checkStringLength(command) || mayContainScript(command)) {
            return;
        }
        
        takeAction(command);
    }

    public void takeAction(String command) throws ParseException {
        Matcher matcher;
        if ((matcher = getMatcher("login\\+(\\w+)\\+(\\w+)", command)).find()) {
            this.login(matcher);
        } else if (command.startsWith("register+")) {
            this.register(command);
        } else if (command.startsWith("logout+")) {
            this.logout(command);
        } else if (command.startsWith("forgot password+")) {
            this.forgotPassword(command);
        } else if (command.startsWith("what is account role+")) {
            this.getAccountRole(command);
        } else if (command.startsWith("view personal info+")) {
            this.viewPersonalInfo(command);
        } else if (command.startsWith("edit personal info+")) {
            this.editPersonalInfo(command);
        } else if (command.startsWith("change pass")) {
            this.changePass(command);
        } else if (command.startsWith("show accounts+")) {
            this.showAccounts(command);
        } else if (command.startsWith("view account info+")) {
            this.viewAccountInfo(command);
        } else if (command.startsWith("see authorization+")) {
            this.seeAuthorization(command);
        } else if (command.startsWith("delete account+")) {
            this.deleteAccount(command);
        } else if (command.startsWith("search account+")) {
            this.searchAccount(command);
        } else if (command.startsWith("make new boss+")) {
            this.makeNewBoss(command);
        } else if (command.startsWith("show requests+")) {
            this.showRequests(command);
        } else if (command.startsWith("request username show+")) {
            this.requestUsername(command);
        } else if (command.startsWith("view request+")) {
            this.viewRequest(command);
        } else if (command.startsWith("is request state checking+")) {
            this.isRequestStateChecking(command);
        } else if (command.startsWith("is account requestable+")) {
            this.isAccountRequestable(command);
        } else if (command.startsWith("search request+")) {
            this.searchRequest(command);
        } else if (command.startsWith("accept request+")) {
            this.acceptRequest(command);
        } else if (command.startsWith("decline request+")) {
            this.declineRequest(command);
        } else if (command.startsWith("delete request+")) {
            this.deleteRequest(command);
        } else if (command.startsWith("what is request username+")) {
            this.getRequestAccountUsername(command);
        } else if (command.startsWith("what is request object ID+")) {
            this.getRequestObjectID(command);
        } else if (command.startsWith("create category+")) {
            this.createCategory(command);
        } else if (command.startsWith("show categories+")) {
            this.showCategories(command);
        } else if (command.startsWith("view category+")) {
            this.viewCategory(command);
        } else if (command.startsWith("search category+")) {
            this.searchCategory(command);
        } else if (command.startsWith("is category exists+")) {
            this.isCategoryExists(command);
        } else if (command.startsWith("edit category name+")) {
            this.editCategoryName(command);
        } else if (command.startsWith("edit father category+")) {
            this.editFatherCategory(command);
        } else if (command.startsWith("what is category attribute+")) {
            this.whatIsCategoryAttribute(command);
        } else if (command.startsWith("add category attribute+")) {
            this.addCategoryAttribute(command);
        } else if (command.startsWith("product picture path+")) {
            this.addProductPicturePath(command);
        } else if (command.startsWith("get product picture path+")) {
            this.getProfilePicturePath(command);
        } else if (command.startsWith("delete category attribute+")) {
            this.deleteCategoryAttribute(command);
        } else if (command.startsWith("edit category attribute+")) {
            this.editCategoryAttribute(command);
        } else if (command.startsWith("delete category+")) {
            this.deleteCategory(command);
        } else if (command.startsWith("create product+")) {
            this.createProduct(command);
        } else if (command.startsWith("show products+")) {
            this.showProducts(command);
        } else if (command.startsWith("show my products+")) {
            this.showMyProducts(command);
        } else if (command.startsWith("view product+")) {
            this.viewProduct(command);
        } else if (command.startsWith("add product+")) {
            this.addProduct(command);
        } else if (command.startsWith("delete product+")) {
            this.deleteProduct(command);
        } else if (command.startsWith("search product+")) {
            this.searchProduct(command);
        } else if (command.startsWith("edit product price+")) {
            this.editProductPrice(command);
        } else if (command.startsWith("add product remainder+")) {
            this.addProductRemainder(command);
        } else if (command.startsWith("decrease product remainder")) {
            this.decreaseProductRemainder(command);
        } else if (command.startsWith("edit product+")) {
            this.editProduct(command);
        } else if (command.startsWith("what is product category+")) {
            this.whatIsProductCategory(command);
        } else if (command.startsWith("add product view+")) {
            this.addProductView(command);
        } else if (command.startsWith("delete product category+")) {
            this.deleteProductCategory(command);
        } else if (command.startsWith("add product category+")) {
            this.addProductCategory(command);
        } else if (command.startsWith("delete product salesman+")) {
            this.deleteProductSalesman(command);
        } else if (command.startsWith("search offCod")) {
            this.searchOffCode(command);
        } else if (command.startsWith("create new normal offCode")) {
            this.createNormalOffCode(command);
        } else if (command.startsWith("create new special offCode")) {
            this.createSpecialOffCode(command);
        } else if (command.startsWith("can add to offCode")) {
            this.canAddUserToOffCode(command);
        } else if (command.startsWith("get all offCodeAble user")) {
            this.getAllOffCodeAbleUser(command);
        } else if (command.startsWith("view offCode")) {
            this.viewOffCode(command);
        } else if (command.startsWith("edit offCode")) {
            this.editOffCode(command);
        } else if (command.startsWith("show offCodes")) {
            this.showOffCodes(command);
        } else if (command.startsWith("create new sale")) {
            this.createSale(command);
        } else if (command.startsWith("can add product to sale")) {
            this.canAddProductToSale(command);
        } else if (command.startsWith("search sale")) {
            this.searchSale(command);
        } else if (command.startsWith("view sale")) {
            this.viewSale(command);
        } else if (command.startsWith("show sales")) {
            this.showSales(command);
        } else if (command.startsWith("edit sale")) {
            this.editSale(command);
        } else if (command.startsWith("what is comment product ID+")) {
            this.getCommentProductID(command);
        } else if (command.startsWith("comment product+")) {
            this.commentProduct(command);
        } else if (command.startsWith("point product+")) {
            this.pointProduct(command);
        } else if (command.startsWith("what is point product+")) {
            this.getProductPoint(command);
        } else if (command.startsWith("is server has boss")) {
            this.isServerHasBoss();
        } else if (command.startsWith("get product min price+")) {
            this.getMinPrice(command);
        } else if (command.startsWith("get product sellers+")) {
            this.getProductSellers(command);
        } else if (command.startsWith("show the salesman+")) {
            this.showSalesman(command);
        } else if (command.startsWith("get product video+")) {
            this.getProductVideo(command);
        } else if (command.startsWith("set product video+")) {
            this.setProductVideo(command);
        } else if (command.startsWith("set person image+")) {
            this.setPersonImage(command);
        } else if (command.startsWith("get person image+")) {
            this.getPersonImage(command);
        } else if (command.startsWith("delete person image")) {
            this.deletePersonImage(command);
        } else if (command.startsWith("what is product name+")) {
            this.getProductName(command);
        } else if (command.startsWith("is product on sale by+")) {
            this.getProductOnSale(command);
        } else if (command.startsWith("get product price by salesman+")) {
            this.getProductPrice(command);
        } else if (command.startsWith("is product finished+")) {
            isFinished(command);
        } else if (command.startsWith("is product on sale+")) {
            isOnSale(command);
        } else if (command.startsWith("product min price with name+")) {
            getMinPriceWithName(command);
        } else if (command.startsWith("is there product name+")) {
            isThereProductName(command);
        } else if (command.startsWith("similar product+")) {
            similarProduct(command);
        } else if (command.startsWith("commercial+")) {
            commercial(command);
        } else if (command.startsWith("get all commercial")) {
            getAllCommercials();
        } else if (command.startsWith("get product sale")) {
            getProductSale(command);
        }
        //end parts
        else if (command.startsWith("show balance")) {
            this.showBalance(command);
        } else if (command.startsWith("add balance+")) {
            this.addBalance(command);
        } else if (command.startsWith("Add To Cart+")) {
            this.addToCart(command);
        } else if (command.startsWith("get money+")) {
            this.getMoney(command);
        } else if (command.startsWith("buy+")) {
            this.buy(command);
        } else if (command.startsWith("can use offCode+")) {
            this.canUserOffCode(command);
        }

    }

    private void canUserOffCode(String command) {
        String offCodeId = command.split("\\+")[2];
        String customer = command.split("\\+")[1];
        if (Storage.isThereOffCodeWithID(offCodeId)) {
            OffCode offCode = Storage.getOffCodeById(offCodeId);
            if (offCode.canCustomerUseItWithUsername(customer)) {
                setAnswer("Use It");
            } else {
                setAnswer("You Cant Use This Off Code");
            }
        } else {
            setAnswer("No Off Code With This Id");
        }
    }

    private void buy(String command) {
        for (String s : command.split("\n")) {
            if (s.startsWith("buy")) continue;
            Product product = Storage.getProductById(s.split("\\+")[1]);
            product.setRemainderForSalesman(product.getRemainderForSalesman(s.split("\\+")[0]) - Integer.parseInt(s.split("\\+")[2]), s.split("\\+")[0]);
            Account account = Storage.getAccountWithUsername(command.split("\\+")[1]);
            Account account1 = Storage.getAccountWithUsername(s.split("\\+")[0]);
            ((Salesman) account1).setCredit(account.getCredit() + product.getPriceBySalesmanID(s.split("\\+")[0]) * Integer.parseInt(s.split("\\+")[2]));
            ((Customer) account).setCredit(account.getCredit() - product.getPriceBySalesmanID(s.split("\\+")[0]) * Integer.parseInt(s.split("\\+")[2]));
        }
    }

    private void getMoney(String command) {
        Account account = Storage.getAccountWithUsername(command.split("\\+")[1]);
        Server.setAnswer(String.valueOf(account.getCredit()));
    }

    private void addToCart(String command) {
        /*for (Cart cart : Storage.allCarts) {
            if (cart.getUsername().equals(command.split("\\+")[1])) {
                for (Triplet<String, String, Integer> item : cart.getAllItems()) {
                    if (item.getValue0().equals(command.split("\\+")[2])) {
                        if (item.getValue1().equals(command.split("\\+")[3])) {
                            int counter = item.getValue2();
                            if (counter + (Integer) count.getValue() > (Integer.parseInt(remainder.getText()))) {
                                Alert alert = new Alert(Alert.AlertType.ERROR, "The Current Number Of Items + The Items You Already Added To Your Cart Is More Than Remainder", ButtonType.OK);
                                alert.showAndWait();
                            } else {
                                MenuHandler.getServer().clientToServer("Add To Cart+" + MenuHandler.getUsername() + "+" + (String) chooseSeller.getValue() + "+" + MenuHandler.getProductID() + "+" + (Integer) count.getValue());
                                Triplet addedItem = new Triplet<>((String) chooseSeller.getValue(), MenuHandler.getProductID(), (Integer) count.getValue() + counter);
                                MenuHandler.getCart().remove(item);
                                MenuHandler.getCart().add(addedItem);
                                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Added To The Cart", ButtonType.OK);
                                alert.showAndWait();
                            }
                            return;
                        }
                    }
                }
                Triplet addedItem = new Triplet<>((String) chooseSeller.getValue(), MenuHandler.getProductID(), (Integer) count.getValue());
                MenuHandler.getCart().add(addedItem);
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Added To The Cart", ButtonType.OK);
                alert.showAndWait();
                return;
            }
        }*/
    }

    private void getProductSale(String command) {
        String productId = command.split("\\+")[1];
        String salesmanId = command.split("\\+")[2];
        Product product = Storage.getProductById(productId);
        assert product != null;
        Sale maxSale = product.getMaxSale(salesmanId);
        Server.setAnswer(maxSale.getSaleID());
    }

    private void getAllCommercials() {
        StringBuilder string = new StringBuilder();
        for (String id : Salesman.getAllCommercials()) {
            string.append(id).append(",");
        }
        if (string.length() != 0) {
            Server.setAnswer(string.substring(0, string.length() - 1));
        } else {
            Server.setAnswer("no commercial");
        }
    }

    private void commercial(String command) {
        String productID = command.split("\\+")[2];
        String username = command.split("\\+")[1];
        Salesman salesman = (Salesman) Storage.getAccountWithUsername(username);
        if (Storage.getProductById(productID) == null) {
            Server.setAnswer("invalid product");
        } else if (salesman.isProductOnCommercial(productID)) {
            Server.setAnswer("it's already on commercial");
        } else if (Storage.getProductById(productID).getSalesmanIDs().contains(username)) {
            if (salesman.getCredit() < 1000) {
                Server.setAnswer("not enough money");
            } else {
                salesman.setCredit(salesman.getCredit() - 1000);
                salesman.getCommercials().add(productID);
                Server.setAnswer("added successfully");
            }
        } else {
            Server.setAnswer("not your product");
        }
    }

    private void isThereProductName(String command) {
        for (Product product : Storage.getAllProducts()) {
            if (product.getName().equals(command.split("\\+")[1])) {
                Server.setAnswer("true");
                return;
            }
        }
        Server.setAnswer("false");
    }

    public void similarProduct(String command) {
        String productID = command.split("\\+")[1];
        while (true) {
            if (Storage.getAllProducts().size() == 1 || Storage.getAllProducts().size() == 0) {
                Server.setAnswer("nothing");
            } else {
                Random rand = new Random();
                int i = rand.nextInt(Storage.getAllProducts().size());
                if (Storage.getAllProducts().get(i).getProductID().equals(productID)) {
                    continue;
                }
                Server.setAnswer(Storage.getAllProducts().get(i).getProductID());
            }
            break;
        }
    }

    private void isFinished(String command) {
        Server.setAnswer(Boolean.toString(Storage.getProductById(command.split("\\+")[1]).isFinished()));
    }

    private void isOnSale(String command) {
        Server.setAnswer(Boolean.toString(Storage.getProductById(command.split("\\+")[1]).is_on_sale()));
    }

    private void addBalance(String command) {
        Account account = Storage.getAccountWithUsername(command.split("\\+")[1]);
        if (account instanceof Customer) {
            ((Customer) account).setCredit(account.getCredit() + Integer.parseInt(command.split("\\+")[2]));
        } else if (account instanceof Salesman) {
            ((Salesman) account).setCredit(account.getCredit() + Integer.parseInt(command.split("\\+")[2]));
        }
        Server.setAnswer("successful");
    }

    private void getProductPrice(String command) {
        Product product = Storage.getProductById(command.split("\\+")[2]);
        setAnswer(String.valueOf(product.getPriceBySalesmanID(command.split("\\+")[1])));
    }

    private void getProductOnSale(String command) {
        //complete needed
        /*setAnswer("none");
        for (Product product : Storage.getAllProducts()) {
            if (product.getProductID().equals(command.split("\\+")[2])) {
                if (product.getIsOnSale().get(command.split("\\+")[1])) {
                    for (Sale sale : Storage.allSales) {
                        if (sale.getSalesmanID().equals(command.split("\\+")[1])) {
                        }
                    }
                }
            }
        }*/
        setAnswer("none");
    }

    private void getProductName(String command) {
        answer = Storage.getProductById(command.split("\\+")[1]).getName();
    }

    private void pointProduct(String command) {
        for (Point point : Storage.allPoints) {
            if (point.getProductID().equals(command.split("\\+")[2])) {
                if (point.getUsername().equals(command.split("\\+")[1])) {
                    point.setPoint(Integer.parseInt(command.split("\\+")[3]));
                    return;
                }
            }
        }
        new Point(command.split("\\+")[1], command.split("\\+")[2], (Integer.parseInt(command.split("\\+")[3])));
    }

    private void getProductPoint(String command) {
        int[] a = new int[5];
        a[0] = a[1] = a[2] = a[3] = a[4] = 0;
        for (Point point : Storage.allPoints) {
            if (point.getProductID().equals(command.split("\\+")[1])) {
                a[point.getPoint() - 1]++;
            }
        }
        Server.setAnswer(a[0] + "+" + a[1] + "+" + a[2] + "+" + a[3] + "+" + a[4]);
    }

    private void getCommentProductID(String command) {
        StringBuilder ans = new StringBuilder();
        for (Comment comment : Storage.allComments) {
            if (comment.getProductID().equals(command.split("\\+")[1])) {
                ans.append(comment.toStringForProductView()).append("\n");
            }
        }
        if (ans.toString().equals("")) {
            Server.setAnswer("none");
            return;
        }
        Server.setAnswer(ans.toString());
    }

    private void commentProduct(String command) {
        Comment comment = new Comment(command.split("\\+")[3], command.split("\\+")[4], command.split("\\+")[1],
                command.split("\\+")[2]);
        new Request(comment).setAccountUsername(command.split("\\+")[1]);
    }

    private void deletePersonImage(String command) {
        String username = command.split("\\+")[1];
        for (Account account : Storage.getAllAccounts()) {
            if (account.getUsername().equals(username)) {
                account.setImgPath(null);
                Server.setAnswer("successful");
                return;
            }
        }
    }

    private void setPersonImage(String command) {
        String username = command.split("\\+")[1];
        for (Account account : Storage.getAllAccounts()) {
            if (account.getUsername().equals(username)) {
                account.setAvatar(command.split("\\+")[2]);
                Server.setAnswer("successful");
                return;
            }
        }
    }

    private void getPersonImage(String command) {
        String username = command.split("\\+")[1];
        for (Account account : Storage.getAllAccounts()) {
            if (account.getUsername().equals(username)) {
                if (account.getAvatar() != null) {
                    Server.setAnswer(account.getAvatar());
                    return;
                } else {
                    Server.setAnswer("no image found");
                }
            }
        }
    }


    private void setProductVideo(String command) {
        String productId = command.split("\\+")[1];
        for (Product product : Storage.getAllProducts()) {
            if (product.getProductID().equals(productId)) {
                product.setVideoPath(command.split("\\+")[2]);
                Server.setAnswer("successful");
                return;
            }
        }
    }

    private void getProductVideo(String command) {
        String productId = command.split("\\+")[1];
        for (Product product : Storage.getAllProducts()) {
            if (product.getProductID().equals(productId)) {
                if (product.getVideoPath() != null) {
                    Server.setAnswer(product.getVideoPath());
                } else {
                    Server.setAnswer("no video found");
                }
            }
        }
    }

    private void showSalesman(String command) {
        productManager.showSalesman(command.split("\\+")[1]);
    }

    private void getProductSellers(String command) {
        for (Product product : Storage.getAllProducts()) {
            if (product.getProductID().equals(command.split("\\+")[1])) {
                Server.setAnswer(product.getSalesmanIDs().toString());
            }
        }
    }

    private void getMinPrice(String command) {
        String productId = command.split("\\+")[1];
        for (Product product : Storage.getAllProducts()) {
            if (product.getProductID().equals(productId)) {
                Server.setAnswer(String.valueOf(product.getMinimumPrice()));
            }
        }
    }

    private void getMinPriceWithName(String command) {
        String name = command.split("\\+")[1];
        for (Product product : Storage.getAllProducts()) {
            if (product.getName().equals(name)) {
                Server.setAnswer(String.valueOf(product.getMinimumPrice()));
            }
        }
    }


    private void getProfilePicturePath(String command) {
        for (Product product : Storage.getAllProducts()) {
            if (product.getProductID().equals(command.split("\\+")[1])) {
                Server.setAnswer(product.getPicPath());
            }
        }
    }

    private void addProductPicturePath(String command) {
        String path = command.split("\\+")[2];
        for (Product product : Storage.getAllProducts()) {
            if (product.getProductID().equals(command.split("\\+")[1])) {
                product.setPicPath(path);
            }
        }
    }

    private void changePass(String command) {
        String[] info = command.split("\\+");
        accountManager.editPassword(info[1], info[2], info[3]);
    }

    private void requestUsername(String command) {
        String username = command.split("\\+")[1];
        bossManager.requestUsername(username);
    }

    private void isServerHasBoss() {
        if (Server.hasBoss) {
            Server.setAnswer("yes");
        } else {
            Server.setAnswer("no");
        }
    }

    private void editSale(String command) {
        String[] input = command.split("\\+");

        //check errors
        StringBuilder error = new StringBuilder("ERRORS:");
        if (input[2].equals("percentage")) {
            error.append(isPercentageValid(input[3]));
        } else if (input[2].contains("Date")) {
            error.append(isDateValid(input[3], input[2]));
        }

        //set answer
        if (error.toString().equals("ERRORS:")) {
            salesmanManager.editSale(input[1], input[2], input[3]);
        } else {
            Server.setAnswer(error.toString());
        }
    }

    private void showSales(String command) {
        ArrayList<Object> filters;
        filters = getFilters(command);
        String[] input = command.split("\\+");
        salesmanManager.showSales(input[1], filters, getSortFactor(command), getSortType(command));
    }

    private void viewSale(String command) {
        String[] input = command.split("\\+");
        salesmanManager.viewSale(input[2]);
    }

    private void searchSale(String command) {
        String[] input = command.split("\\+");
        salesmanManager.searchSale(input[2]);
    }

    private void canAddProductToSale(String command) {
        String[] input = command.split("\\+");//1-->salesmanID    2-->productID
        salesmanManager.canAddToSale(input[1], input[2]);
    }

    private void createSale(String command) {
        String[] info = command.split("\\+");//1-->salesmanID    2--> start     3-->end     4-->percentage     5-->productIDs
        if (isSaleInfoValid(info[2], info[3], info[4])) {
            Server.setAnswer("creation of sale successful");
            String productsID = info[5].substring(info[5].indexOf(":") + 2, info[5].length() - 1);//info[5]-->Products:[...]
            salesmanManager.createSale(info[1], info[2], info[3], Integer.parseInt(info[4]), convertStringToArray(productsID));
        }
    }

    private String isPercentageValid(String percentage) {
        try {
            int interest = Integer.parseInt(percentage);
            if (interest <= 0 | 100 < interest) {
                return "\nPERCENTAGE: must be from 1 to 100";
            }
        } catch (Exception e) {
            return "\nPERCENTAGE: invalid format";
        }
        return "";
    }

    private String isDateValid(String date, String type) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            dateFormat.parse(date);
        } catch (ParseException e) {
            return "\n" + type.toUpperCase() + ": invalid format";
        }
        return "";
    }

    private String isNumericalValueValid(String value, String type) {
        try {
            Integer.parseInt(value);
        } catch (Exception e) {
            return "\n" + type.toUpperCase() + ": format is invalid";
        }
        return "";
    }

    private boolean isSaleInfoValid(String start, String end, String percentage) {
        StringBuilder checkResult = new StringBuilder("ERRORS:");

        //check errors
        checkResult.append(isDateValid(start, "start"));
        checkResult.append(isDateValid(end, "end"));
        checkResult.append(isPercentageValid(percentage));

        //set answer
        if (checkResult.toString().equals("ERRORS:")) {
            return true;
        } else {
            setAnswer(checkResult.toString());
            return false;
        }
    }

    /*
     * this is offCode part
     */

    private void showOffCodes(String command) {
        ArrayList<Object> filters;
        filters = getFilters(command);
        String[] input = command.split("\\+");
        bossManager.showOffCodes(input[1], filters, getSortFactor(command), getSortType(command));
    }

    private void editOffCode(String command) {
        String[] input = command.split("\\+");
        StringBuilder error = new StringBuilder("ERRORS:");
        //check errors
        if (input[2].equals("percentage")) {
            error.append(isPercentageValid(input[3]));
        } else if (input[2].contains("date")) {
            error.append(isDateValid(input[3], input[2]));
        } else if (input[2].equals("ceiling")) {
            error.append(isNumericalValueValid(input[3], input[2]));
        }
        //set info
        if (error.toString().equals("ERRORS:")) {
            bossManager.editOffCode(input[1], input[2], input[3]);
        } else {
            Server.setAnswer(error.toString());
        }
    }

    private void viewOffCode(String command) {
        bossManager.viewOffCode(command.split("\\+")[1], command.split("\\+")[2]);
    }

    private void searchOffCode(String command) {
        bossManager.searchOffCode(command.split("\\+")[2]);
    }

    private void getAllOffCodeAbleUser(String command) {
        StringBuilder ans = new StringBuilder("users:");
        for (Account account : Storage.getAllAccounts()) {
            if (account.getRole().equals(Role.CUSTOMER)) ans.append("+").append(account.getUsername());
        }
        if (ans.toString().equals("users:")) setAnswer("nothing found");
        else setAnswer(ans.toString());
    }

    private void createSpecialOffCode(String command) {
        String[] info = command.split("\\+"); //info: 1-->percentage 2-->ceiling 3-->frequency 4-->period
        if (checkOffCodeInfoCorrectness(info)) {
            bossManager.createSpecialOffCode(Integer.parseInt(info[4]), Integer.parseInt(info[1]), Integer.parseInt(info[2]), Integer.parseInt(info[3]));
            setAnswer("creation of offCode successful");
        }
    }

    private void createNormalOffCode(String command) {
        String[] info = command.split("\\+");   //info: 1-->percentage 2-->ceiling 3-->frequency 4-->start 5-->end
        if (checkOffCodeInfoCorrectness(info)) {
            String usernames = info[6].substring(info[6].indexOf(":") + 2, info[6].length() - 1); //info[6] = User:[....]
            ArrayList<String> allUsersCanUse = convertStringToArray(usernames);
            bossManager.createNormalOffCode(info[4], info[5], Integer.parseInt(info[1]), Integer.parseInt(info[2]),
                    Integer.parseInt(info[3]), allUsersCanUse);
            setAnswer("creation of offCode successful");
        }
    }

    private ArrayList<String> convertStringToArray(String s) {
        ArrayList<String> ans = new ArrayList<>();
        if (s.equals("")) return ans;
        return new ArrayList<String>(Arrays.asList(s.split(", ")));
    }

    //info: create new normal offCode+percentage+ceiling+frequency+start+end+User:[...]
    //info: create new special offCode+percentage+ceiling+frequency+period

    private boolean checkOffCodeInfoCorrectness(String[] info) {
        StringBuilder error = new StringBuilder("ERRORS:");

        //check different part

        if (info[0].equals("create new normal offCode")) {
            error.append(isDateValid(info[4], "START TIME"));
            error.append(isDateValid(info[5], "END TIME"));
        } else {
            error.append(isNumericalValueValid(info[4], "PERIOD"));
        }

        //check same parts
        error.append(isPercentageValid(info[1]));
        error.append(isNumericalValueValid(info[2], "CEILING"));
        error.append(isNumericalValueValid(info[3], "FREQUENCY"));

        //set answer
        if (!error.toString().equals("ERRORS:")) {
            setAnswer(error.toString());
            return false;
        }
        return true;
    }

    public void canAddUserToOffCode(String command) {
        String username = command.split("\\+")[2];
        if (!Storage.isThereAccountWithUsername(username)) {
            setAnswer("ERROR: there isn't exist any user with this username");
        } else if (!Storage.getAccountWithUsername(username).getRole().equals(Role.CUSTOMER)) {
            setAnswer("ERROR: this user is not a CUSTOMER, you must add customers to offCode");
        } else {
            setAnswer("yes");
        }

    }

    /*
     * this is Product Part
     */

    private void addProductCategory(String command) {
        Product product = Storage.getProductById(command.split("\\+")[2]);
        if (Storage.isThereCategoryWithName(command.split("\\+")[3])) {
            setAnswer("added successfully");
            product.setCategoryName(command.split("\\+")[3]);
        } else {
            setAnswer("there isn't category with this name");
        }
    }

    private void deleteProductSalesman(String command) {
        Product product = Storage.getProductById(command.split("\\+")[2]);
        if (Storage.isThereAccountWithUsername(command.split("\\+")[3])) {
            Account account = Storage.getAccountWithUsername(command.split("\\+")[3]);
            if (account.getRole().equals(Role.SALESMAN)) {
                if (product.doesSalesmanSellProductWithUsername(account.getUsername())) {
                    product.deleteForSalesman(account.getUsername());
                    setAnswer("deleted successfully");
                } else {
                    setAnswer("this salesman does'nt sell this product");
                }
            } else {
                setAnswer("this username isn't a salesman username");
            }
        } else {
            setAnswer("no account with this username");
        }
    }

    private void deleteProductCategory(String command) {
        Product product = Storage.getProductById(command.split("\\+")[2]);
        product.setCategoryName(null);
        Server.setAnswer("deleted successfully");
    }

    private void addProductView(String command) {
        Product product = Storage.getProductById(command.split("\\+")[1]);
        product.setSeenCount(product.getSeenCount() + 1);
    }

    private void whatIsProductCategory(String command) {
        Product product = Storage.getProductById(command.split("\\+")[1]);
        if (product.getCategoryName() == null) {
            setAnswer("it doesn't have a category");
        } else {
            setAnswer(product.getCategoryName());
        }
    }

    private void editProduct(String command) {
        productManager.editProduct(command.split("\\+")[3], command.split("\\+")[2],
                command.split("\\+")[1], command.split("\\+")[4]);
        setAnswer("request submitted");
    }

    private void addProductRemainder(String command) {
        String[] input = command.split("\\+");
        Product product = Storage.getProductById(input[2]);
        product.setRemainderForSalesman(product.getRemainderForSalesman(input[1]) +
                Integer.parseInt(input[3]), input[1]);
        setAnswer("edit successful");
    }

    private void decreaseProductRemainder(String command) {
        String[] input = command.split("\\+");
        Product product = Storage.getProductById(input[1]);
        assert product != null;
        if (Integer.parseInt(input[3]) > product.getRemainderForSalesman(input[1])) {
            setAnswer("not enough remainder");
            return;
        }
        product.setRemainderForSalesman(product.getRemainderForSalesman(input[1]) -
                Integer.parseInt(input[3]), input[1]);
        setAnswer("edit successful");
    }

    private void editProductPrice(String command) {
        Product product = Storage.getProductById(command.split("\\+")[2]);
        assert product != null;
        product.setPriceForSalesman(Integer.parseInt(command.split("\\+")[3]), command.split("\\+")[1]);
        setAnswer("edit successful");
    }

    private void addProduct(String command) {
        productManager.addToProduct(command.split("\\+")[1], command.split("\\+")[2],
                command.split("\\+")[3], command.split("\\+")[4]);
    }

    private void deleteProduct(String command) {
        productManager.deleteProduct(command.split("\\+")[1], command.split("\\+")[2]);
    }

    private void searchProduct(String command) {
        productManager.searchProduct(command.split("\\+")[1], command.split("\\+")[2]);
    }

    private void viewProduct(String command) {
        productManager.viewProduct(command.split("\\+")[1], command.split("\\+")[2]);
    }

    private void showMyProducts(String command) {
        ArrayList<Object> filters;
        filters = getFilters(command);
        String[] input = command.split("\\+");
        productManager.showProducts(input[1], filters, getSortFactor(command), getSortType(command), 1);
    }

    private void showProducts(String command) {
        ArrayList<Object> filters;
        filters = getFilters(command);
        String[] input = command.split("\\+");
        productManager.showProducts(input[1], filters, getSortFactor(command), getSortType(command), 0);
    }

    private boolean checkProductNameFormat(String input) {
        return getMatcher("([\\d\\w_\\-,\\s])+", input).matches();
    }

    private boolean checkDescriptionFormat(String description) {
        return getMatcher("[\\w\\s\\.]+", description).matches();
    }

    private void createProduct(String command) {
        Server.answer = "";
        if (!checkProductNameFormat(command.split("\\+")[2])) {
            Server.answer += "product name format is invalid";
        }
        if (!checkNameFormat(command.split("\\+")[3])) {
            Server.answer += "brand format is invalid";
        }
        if (!checkDescriptionFormat(command.split("\\+")[4])) {
            Server.answer += "description format is invalid";
        }
        if (!checkMoneyFormat(command.split("\\+")[5])) {
            Server.answer += "money format is invalid";
        }
        if (command.split("\\+")[5].length() > 8) {
            Server.answer += "money is too much!";
        }
        if (!checkMoneyFormat(command.split("\\+")[6])) {
            Server.answer += "remainder format is invalid";
        }
        if (command.split("\\+")[6].length() > 5) {
            Server.answer += "remainder is too much!";
        }
        if (Server.answer.equals("")) {
            salesmanManager.createProduct(command);
        }
    }

    private void deleteCategory(String command) {
        bossManager.deleteCategory(command.split("\\+")[1]);
    }

    private void editCategoryAttribute(String command) {
        bossManager.editCategoryAttribute(command.split("\\+")[2], command.split("\\+")[3]);
    }

    private void deleteCategoryAttribute(String command) {
        bossManager.deleteCategoryAttribute(command.split("\\+")[2], command.split("\\+")[4]);
    }

    private void addCategoryAttribute(String command) {
        bossManager.addCategoryAttribute(command.split("\\+")[2], command.split("\\+")[3]);
    }

    private void whatIsCategoryAttribute(String command) {
        setAnswer(Storage.getCategoryByName(command.split("\\+")[1]).getAttribute());
    }

    private void editCategoryName(String command) {
        bossManager.editCategoryName(command.split("\\+")[2], command.split("\\+")[3]);
    }

    private void editFatherCategory(String command) {
        bossManager.editFatherCategory(command.split("\\+")[2], command.split("\\+")[3]);
    }

    private void isCategoryExists(String command) {
        if (Storage.isThereCategoryWithName(command.split("\\+")[1])) {
            setAnswer("yes");
        } else {
            setAnswer("no");
        }
    }

    private void searchCategory(String command) {
        bossManager.searchCategory(command.split("\\+")[2]);
    }

    private void viewCategory(String command) {
        String[] input = command.split("\\+");
        bossManager.viewCategory(input[1], input[2]);
    }

    private void showCategories(String command) {
        ArrayList<Object> filters;
        filters = getFilters(command);
        String[] input = command.split("\\+");
        bossManager.showCategories(input[1], filters, getSortFactor(command), getSortType(command));
    }

    private void createCategory(String command) {
        Server.answer = "";
        if (!checkNameFormat(command.split("\\+")[1])) {
            Server.answer += "category name format is invalid";
        }
        if (!checkNameFormat(command.split("\\+")[2])) {
            Server.answer += "father category name format is invalid";
        }
        if (Server.answer.equals("")) {
            bossManager.createCategory(command.split("\\+")[1], command.split("\\+")[2],
                    command.split("\\+")[3]);
        }
    }

    private void getRequestObjectID(String command) {
        Request request = Storage.getRequestByID(command.split("\\+")[1]);
        assert request != null;
        Server.setAnswer(request.getObjectID());
    }

    private void getRequestAccountUsername(String command) {
        Request request = Storage.getRequestByID(command.split("\\+")[1]);
        assert request != null;
        Server.setAnswer(request.getAccountUsername());
    }

    private void deleteRequest(String command) {
        bossManager.deleteRequest(command.split("\\+")[2]);
    }

    private void declineRequest(String command) {
        Request request = Storage.getRequestByID(command.split("\\+")[2]);
        assert request != null;
        request.decline();
        Server.setAnswer("declined successfully");
    }

    private void acceptRequest(String command) throws ParseException {
        Request request = Storage.getRequestByID(command.split("\\+")[2]);
        assert request != null;
        request.accept();
        Server.setAnswer("accepted successfully");
    }

    private void searchRequest(String command) {
        bossManager.searchRequest(command.split("\\+")[2]);
    }

    private void isAccountRequestable(String command) {
        if (Storage.isThereAccountWithUsername(command.split("\\+")[1])) {
            if (!Storage.getAccountWithUsername(command.split("\\+")[1]).getRole().equals(Role.BOSS)) {
                Server.setAnswer("yes");
            } else {
                Server.setAnswer("no");
            }
        } else {
            Server.setAnswer("no");
        }
    }

    private void viewRequest(String command) {
        String[] input = command.split("\\+");
        bossManager.viewRequest(input[1], input[2]);
    }

    private void isRequestStateChecking(String command) {
        String[] input = command.split("\\+");
        Request request = Storage.getRequestByID(input[1]);
        assert request != null;
        if (request.getConfirmation().equals(Confirmation.CHECKING)) {
            Server.setAnswer("yes");
        } else {
            Server.setAnswer("no");
        }
    }

    private void showRequests(String command) {
        ArrayList<Object> filters;
        filters = getFilters(command);
        String[] input = command.split("\\+");
        bossManager.showRequests(input[1], filters);
    }

    private void makeNewBoss(String input) {
        Server.answer = "";
        String[] attributes = input.split("\\+");
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
        if (!checkEmailFormat(attributes[5])) {
            Server.answer += "Email format is invalid" + "\n";
        }
        if (!checkTelephoneFormat(attributes[6])) {
            Server.answer += "telephone format is invalid" + "\n";
        }
        if (Server.answer.equals("")) {
            bossManager.register(attributes);
        }
    }

    private void searchAccount(String command) {
        String[] input = command.split("\\+");
        bossManager.searchAccount(input[2]);
    }

    private void deleteAccount(String command) {
        String[] input = command.split("\\+");
        accountManager.deleteAccount(input[1], input[2]);
    }

    private void seeAuthorization(String command) {
        String[] input = command.split("\\+");
        bossManager.seeAuthorization(input[1], input[2]);
    }

    private void viewAccountInfo(String command) {
        String[] input = command.split("\\+");
        bossManager.viewAccount(input[1], input[2]);
    }

    private int getWordCount(String input) {
        int count = 0;
        Matcher matcher = getMatcher("\\+", input);
        while (matcher.find()) {
            count++;
        }
        return count + 1;
    }

    private ArrayList<Object> getFilters(String command) {
        ArrayList<Object> filters = new ArrayList<Object>();
        String[] input = command.split("\\+");
        if (command.contains("filters:")) {
            for (int i = 3; i < input.length; i += 2) {
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
        String[] input = command.split("\\+");
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
        String[] input = command.split("\\+");
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
        String[] input = command.split("\\+");
        bossManager.showAccounts(input[1], filters, getSortFactor(command), getSortType(command));
    }

    private void editPersonalInfo(String command) {
        String[] input = command.split("\\+");
        switch (input[1]) {
            case "firstName":
                if (checkNameFormat(input[2])) {
                    accountManager.editFirstName(input[3], input[2]);
                } else {
                    answer = "invalid first name type";
                }
                break;
            case "lastName":
                if (checkNameFormat(input[2])) {
                    accountManager.editLastName(input[3], input[2]);
                } else {
                    answer = "invalid last name type";
                }
                break;
            case "username":
                if (checkUsernameFormat(input[2])) {
                    accountManager.editUsername(input[3], input[2]);
                } else {
                    answer = "invalid username type";
                }
                break;
            case "Email":
                if (checkEmailFormat(input[2])) {
                    accountManager.editEmail(input[3], input[2]);
                } else {
                    answer = "invalid Email type";
                }
                break;
            case "telephone":
                if (checkTelephoneFormat(input[2])) {
                    accountManager.editTelephone(input[3], input[2]);
                } else {
                    answer = "invalid Telephone type";
                }
                break;
            case "password":
                if (checkUsernameFormat(input[3])) {
                    if (input[4].equals(input[3])) {
                        accountManager.editPassword(input[5], input[2], input[3]);
                    } else {
                        answer = "your new password differs from resubmit of your password";
                    }
                } else {
                    answer = "invalid password type";
                }
                break;
            case "money":
                if (checkMoneyFormat(input[2])) {
                    accountManager.editMoney(input[3], input[2]);
                } else {
                    answer = "invalid money type";
                }
                break;
            case "company":
                accountManager.editCompany(input[3], input[2]);
                break;
        }
    }

    private void viewPersonalInfo(String command) {
        String[] input = command.split("\\+");
        accountManager.viewAccountInformation(input[1]);
    }

    private void forgotPassword(String command) {
        accountManager.forgotPassword(command.split("\\+")[1]);
    }

    private void getAccountRole(String command) {
        setAnswer(Storage.getAccountWithUsername(command.split("\\+")[1]).getRole().name());
    }

    private void logout(String command) {
        accountManager.logout(command.split("\\+")[1]);
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
        hasBoss = (Storage.getAllBosses().size() != 0);
        Server.answer = "";
        String[] attributes = input.split("\\+");
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

    public String serverToClient() throws IOException {
        endOfProgramme.updateFiles();
        return Server.answer;
    }

    /*
     * ---------[ here are common parts, server handel this by itself, no manager required ]--------
     */

    public void showBalance(String command) {
        String username = command.split("\\+")[1];
        Server.setAnswer("Your Balance is : " + Storage.getAccountWithUsername(username).getCredit());
    }

    public void listCategories(String sortFactor) {
        StringBuilder ans = new StringBuilder("Here are all Categories name:");
        for (Category category : Storage.getAllCategories()) {
            ans.append("\n").append(category.getCategoryName());
        }
        Server.setAnswer(ans.toString());
    }
}
