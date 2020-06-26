package GUI;

import Controller.Server;
import GUI.CategoryMenu.Category;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.javatuples.Triplet;

import java.util.ArrayList;

public class MenuHandler {
    static private String username = null;
    static private Server server;
    static private String productID = null;
    static private String requestID = null;
    static private String seeingUsername = null;
    static private String seeingOffCode = null;
    static private String seeingSale = null;
    static private boolean isUserLogin = false;
    static private String userType = null;
    static private Stage stage;
    static private String loginBackAddress = null;
    static private Pane pane;
    static private String backProduct = null;
    static private ArrayList<Triplet<String, String, Integer>> cart = new ArrayList<>();
    public static String selectedCategory;

    public static String getRequestID() {
        return requestID;
    }

    public static void setRequestID(String requestID) {
        MenuHandler.requestID = requestID;
    }

    public static String getUsername() {
        return username;
    }

    public static void setUsername(String username) {
        MenuHandler.username = username;
    }

    public static Server getServer() {
        return server;
    }

    public static void setServer(Server server) {
        MenuHandler.server = server;
    }

    public static boolean isIsUserLogin() {
        return isUserLogin;
    }

    public static void setIsUserLogin(boolean isUserLogin) {
        MenuHandler.isUserLogin = isUserLogin;
    }

    public static String getUserType() {
        return userType;
    }

    public static void setUserType(String userType) {
        MenuHandler.userType = userType;
    }

    public static String getLoginBackAddress() {
        return loginBackAddress;
    }

    public static void setLoginBackAddress(String loginBackAddress) {
        MenuHandler.loginBackAddress = loginBackAddress;
    }

    public static String getSeeingUsername() {
        return seeingUsername;
    }

    public static void setSeeingUsername(String seeingUsername) {
        MenuHandler.seeingUsername = seeingUsername;
    }

    public static String getSeeingOffCode() {
        return seeingOffCode;
    }

    public static void setSeeingOffCode(String seeingOffCode) {
        MenuHandler.seeingOffCode = seeingOffCode;
    }

    public static String getProductID() {
        return productID;
    }

    public static void setProductID(String productID) {
        MenuHandler.productID = productID;
    }

    public static Pane getPane() {
        return pane;
    }

    public static void setPane(Pane pane) {
        MenuHandler.pane = pane;
    }

    public static String getBackProduct() {
        return backProduct;
    }

    public static void setBackProduct(String backProduct) {
        MenuHandler.backProduct = backProduct;
    }

    public static Stage getStage() {
        return stage;
    }

    public static void setStage(Stage stage) {
        MenuHandler.stage = stage;
    }

    public static String getSelectedCategory() {
        return selectedCategory;
    }

    public static void setSelectedCategory(String selectedCategory) {
        MenuHandler.selectedCategory = selectedCategory;
    }

    public static ArrayList<Triplet<String, String, Integer>> getCart() {
        return cart;
    }

    public static void setCart(ArrayList<Triplet<String, String, Integer>> cart) {
        MenuHandler.cart = cart;
    }

    public static String getSeeingSale() {
        return seeingSale;
    }

    public static void setSeeingSale(String seeingSale) {
        MenuHandler.seeingSale = seeingSale;
    }
}
