package GUI;

import Controller.Server;
import GUI.Media.Audio;
import Model.Supporter.Chat;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Popup;
import javafx.stage.Stage;
import org.javatuples.Triplet;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.ParseException;
import java.util.ArrayList;

public class MenuHandler extends Application {
    static private String currentAuction;
    static private final int PORT_NUMBER = 8080;
    static private String username = null;
    static private String userAvatar = null;
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
    static private String cartBack = null;
    static private ArrayList<Triplet<String, String, Integer>> cart = new ArrayList<>();
    public static String selectedCategory;
    static private Connector connector;
    static private Popup supporterPopup;
    static private Object lock = new Object();
    static private Object newMessageLock = new Object();
    static private ArrayList<Chat> myChats = new ArrayList<>();
    static private String token = "no token";
    static private P2PHandler p2PHandler;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        Audio.playBackGroundMusic();
        Connector connector = new Connector("localhost", PORT_NUMBER);
        p2PHandler = new P2PHandler();
        p2PHandler.run();
        MenuHandler.setConnector(connector);
        try {
            connector.run();
            connector.clientToServer("is server has boss");
            String response = connector.serverToClient();
            Parent root;
            if (response.equalsIgnoreCase("yes")) {
                root = FXMLLoader.load(getClass().getResource("/GUI/ProductScene/ProductScene.fxml"));
            } else {
                root = FXMLLoader.load(getClass().getResource("/GUI/RegisterBoss/RegisterBoss.fxml"));
            }
            primaryStage.setScene(new Scene(root));
            MenuHandler.setStage(primaryStage);
            createSupporterPopup();
            primaryStage.show();

        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "your connection to server failed ): try again later", ButtonType.OK);
            alert.showAndWait();
        }
    }

    public static Connector getConnector() {
        return connector;
    }

    public static void setConnector(Connector connector) {
        MenuHandler.connector = connector;
    }

    private static void createSupporterPopup() throws IOException {
        Popup popup = new Popup();
        HBox root = FXMLLoader.load(MenuHandler.class.getResource("/GUI/Supporter/SupporterPopUp.fxml"));
        popup.getContent().add(root);
        supporterPopup = popup;
    }

    public static void showSupporterPopup() {
        double x = stage.getX() + stage.getWidth() - 150 - 15;
        double y = stage.getY() + stage.getHeight() - 50 - 15;
        supporterPopup.setAnchorX(x);
        supporterPopup.setAnchorY(y);
        supporterPopup.show(stage);
    }

    public static void hideSupporterPopup() {
        supporterPopup.hide();
    }

    public static Object getLock() {
        return lock;
    }

    public static Object getNewMessageLock() {
        return newMessageLock;
    }

    public static void addMessage(String sender, String receiver, String content) {
        if (receiver.equalsIgnoreCase(MenuHandler.getUsername()) | sender.equalsIgnoreCase(MenuHandler.getUsername())) {
            Chat.processMessage(sender, receiver, content, myChats);
        }
    }

    public static ArrayList<Chat> getMyChats() {
        return myChats;
    }

    public static String getRole() {
        try {
            getConnector().clientToServer("what is account role+" + username);
            return getConnector().serverToClient();
        } catch (Exception e) {
            return "none";
        }
    }

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

    /*public static Server getServer() {
        return server;
    }*/

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

    public static String getCartBack() {
        return cartBack;
    }

    public static void setCartBack(String cartBack) {
        MenuHandler.cartBack = cartBack;
    }

    public static String getSeeingSale() {
        return seeingSale;
    }

    public static void setSeeingSale(String seeingSale) {
        MenuHandler.seeingSale = seeingSale;
    }

    public static String getUserAvatar() {
        return userAvatar;
    }

    public static void setUserAvatar(String userAvatar) {
        MenuHandler.userAvatar = userAvatar;
    }

    public static long getCredit() throws IOException {
        MenuHandler.getConnector().clientToServer("show balance+" + username);
        return Long.parseLong(MenuHandler.getConnector().serverToClient().substring("Your Balance is : ".length()));
    }

    public static int getWagePercentage() throws IOException {
        MenuHandler.getConnector().clientToServer("get wage+");
        return Integer.parseInt(MenuHandler.getConnector().serverToClient());
    }

    public static long getMinCredit() throws IOException {
        MenuHandler.getConnector().clientToServer("get min credit+");
        return Long.parseLong(MenuHandler.getConnector().serverToClient());
    }

    public static void setToken(String s) {
        token = s;
    }

    public static String getToken() {
        return token;
    }

    public static P2PHandler getP2PHandler() {
        return p2PHandler;
    }

    public static void sendFile(String seller, String fileName, String host, String port) {
        if (seller.equalsIgnoreCase(MenuHandler.getUsername())) {
            p2PHandler.send(fileName, host, Integer.parseInt(port));
        }
    }

    public static String getCurrentAuction() {
        return currentAuction;
    }

    public static void setCurrentAuction(String currentAuction) {
        MenuHandler.currentAuction = currentAuction;
    }

    public static int getPortNumber() {
        return PORT_NUMBER;
    }

    public static Server getServer() {
        return server;
    }

    public static void setLock(Object lock) {
        MenuHandler.lock = lock;
    }

    public static void setNewMessageLock(Object newMessageLock) {
        MenuHandler.newMessageLock = newMessageLock;
    }

    public static void goHome() throws IOException {
        Parent root = FXMLLoader.load(MenuHandler.class.getResource("/GUI/ProductScene/ProductScene.fxml"));
        MenuHandler.getStage().setScene(new Scene(root));
    }

    /*public static void setP2PLock(Object p2PLock) {
        P2PLock = p2PLock;
    }

    public static void setMyChats(ArrayList<Chat> myChats) {
        MenuHandler.myChats = myChats;
    }

    public static ServerSocket getServerSocket() {
        return serverSocket;
    }

    public static void setServerSocket(ServerSocket serverSocket) {
        MenuHandler.serverSocket = serverSocket;
    }*/
}
