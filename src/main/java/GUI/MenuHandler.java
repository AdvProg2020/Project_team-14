package GUI;

import Controller.Server;

public class MenuHandler {
    static private String username = null;
    static private Server server;
    static private boolean isUserLogin = false;
    static private String userType = null;
    static private String loginBackAddress = null;

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
}