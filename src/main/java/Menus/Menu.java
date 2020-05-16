package Menus;

import Controller.Server;

import java.util.HashMap;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Menu {
    static protected String username;
    static protected boolean backFromAccountMenu = false;
    static protected Server server;
    protected String menuName;
    protected boolean logoutType;
    protected Menu fatherMenu;
    protected HashMap<Integer, Menu> subMenus;
    static protected boolean isUserLogin;
    static protected String userType;
    static protected Scanner scanner;

    public Menu(Menu fatherMenu, String menuName) {
        this.fatherMenu = fatherMenu;
        this.menuName = menuName;
    }

    public int getWhereItHasBeenCalled() {
        return 0;
    }

    public Menu getFatherMenu() {
        return fatherMenu;
    }

    public static int wordCount(String input) {
        Pattern pattern = Pattern.compile("(\\S+)");
        Matcher matcher = pattern.matcher(input);
        int cnt = 0;
        while (matcher.find()) {
            cnt++;
        }
        return cnt;
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

    public void setSubMenus(HashMap subMenus) {
        this.subMenus = subMenus;
    }

    public String getMenuName() {
        return this.menuName;
    }

    public void checkBack(String command) {
        if (command.equalsIgnoreCase("back")) {
            fatherMenu.execute();
        }
    }

    protected void show() {
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
        if (!isUserLogin && !logoutType) {
            if (fatherMenu instanceof AccountMenu) {
                Menu.backFromAccountMenu = true;
            }
            fatherMenu.execute();
        }
        this.show();
        Menu nextMenu = this;
        String str = scanner.nextLine();
        int input = 0;
        if (str.matches("(\\d+)")) {
            input = Integer.parseInt(str);
        } else {
            System.out.println("please input the number of menu");
            this.execute();
        }
        if (input == subMenus.size() + 1) {
            if (this.fatherMenu == null) {
                System.exit(1);
            } else {
                if (fatherMenu instanceof AccountMenu) {
                    Menu.backFromAccountMenu = true;
                }
                nextMenu = this.fatherMenu;
            }
        } else if (input > 0 && input <= subMenus.size()) {
            nextMenu = subMenus.get(input);
        }
        nextMenu.execute();
    }
}