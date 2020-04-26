package Menus;

import Controller.Server;

import java.util.HashMap;
import java.util.Scanner;

public class Menu {
    static protected Server server;
    private String menuName;
    protected boolean logoutType;
    protected Menu fatherMenu;
    private HashMap<Integer, Menu> subMenus;
    static protected boolean isUserLogin;
    static protected String userType;
    static protected Scanner scanner;

    public Menu(Menu fatherMenu, String menuName) {
        this.fatherMenu = fatherMenu;
        this.menuName = menuName;
    }

    public static void setIsUserLogin(boolean isUserLogin) {
        Menu.isUserLogin = isUserLogin;
    }

    public static void setUserType(String userType) {
        Menu.userType = userType;
    }

    public static void setScanner(Scanner scanner) {
        Menu.scanner = scanner;
    }

    public static void setServer(Server server) {
        Menu.server = server;
    }

    public void setSubMenus(HashMap<Integer, Menu> subMenus) {
        this.subMenus = subMenus;
    }

    public String getMenuName() {
        return this.menuName;
    }

    private void show() {
        System.out.println(this.menuName + ":");
        for (Integer menuNum : subMenus.keySet()) {
            System.out.println(menuNum + ". " + subMenus.get(menuNum).getMenuName());
        }
        if (this.fatherMenu != null)
            System.out.println((subMenus.size() + 1) + ". Back");
        else
            System.out.println((subMenus.size() + 1) + ". Exit");
    }


    public void execute() {
        if (isUserLogin == false && logoutType == false) {
            fatherMenu.execute();
        }
        this.show();
        Menu nextMenu = null;
        int input = Integer.parseInt(scanner.nextLine());
        if (input == subMenus.size() + 1) {
            if (this.fatherMenu == null) {
                System.exit(1);
            } else {
                nextMenu = this.fatherMenu;
            }
        } else if (input > 0 && input <= subMenus.size()) {
            nextMenu = subMenus.get(input);
        }
        nextMenu.execute();
    }
}