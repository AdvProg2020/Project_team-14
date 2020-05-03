package Menus;

import Menus.Views.ViewAccountMenu;
import Menus.shows.ShowAccountsMenu;

import java.util.HashMap;

public class ManageAccountsMenu extends Menu {
    public ManageAccountsMenu(Menu fatherMenu, String menuName) {
        super(fatherMenu, menuName);
        this.logoutType = false;
        HashMap<Integer, Menu> subMenus = new HashMap<Integer, Menu>();
        subMenus.put(1, new ShowAccountsMenu(this, "Show Accounts Menu"));
        subMenus.put(2, getSearchAccountMenu());
        subMenus.put(3, new LoginOrRegisterMenu(this, "Login\\Register Menu"));
        this.setSubMenus(subMenus);
    }

    private Menu getSearchAccountMenu() {
        return new Menu(this, "Search Account Menu") {
            @Override
            public void execute() {
                System.out.println(menuName);
                System.out.println("if you input back we will go back");
                System.out.println("please input the username");
                String username = scanner.nextLine();
                server.clientToServer("search account " + Menu.username + " " + username);
                String serverAnswer = server.serverToClient();
                System.out.println(serverAnswer);
                if (serverAnswer.equalsIgnoreCase("search completed")) {
                    ViewAccountMenu menu = new ViewAccountMenu(this, "View Account Menu");
                    menu.setAccount(username);
                    menu.execute();
                } else {
                    this.execute();
                }
            }
        };
    }
}
