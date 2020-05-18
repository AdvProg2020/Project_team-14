package Menus.Manages;

import Controller.Server;
import Menus.LoginOrRegisterMenu;
import Menus.Menu;
import Menus.Views.ViewAccountMenu;
import Menus.Views.ViewRequestMenu;
import Menus.shows.ShowRequestsMenu;

import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;

public class ManageRequestsMenu extends Menu {
    public ManageRequestsMenu(Menu fatherMenu, String menuName) {
        super(fatherMenu, menuName);
        this.logoutType = false;
        HashMap<Integer, Menu> subMenus = new HashMap<Integer, Menu>();
        subMenus.put(1, new ShowRequestsMenu(this, "Show Requests Menu"));
        subMenus.put(2, getSearchRequestMenu());
        subMenus.put(3, new LoginOrRegisterMenu(this, "Login\\Register Menu"));
        this.setSubMenus(subMenus);
    }

    private Menu getSearchRequestMenu() {
        return new Menu(this, "Search Request Menu") {
            boolean hasBeenCalled = true;

            @Override
            public void execute() throws ParseException, IOException {
                if (hasBeenCalled) {
                    hasBeenCalled = false;
                } else {
                    hasBeenCalled = true;
                    fatherMenu.execute();
                }
                System.out.println(menuName);
                System.out.println("if you input back we will go back");
                System.out.println("please input the requestID");
                String requestID = scanner.nextLine();
                server.clientToServer("search request+" + Menu.username + "+" + requestID);
                String serverAnswer = server.serverToClient();
                if (serverAnswer.startsWith("search completed")) {
                    System.out.println("search completed");
                    ViewRequestMenu menu = new ViewRequestMenu(this, "View Request Menu",
                            serverAnswer.split("\\s")[2], requestID);
                    menu.execute();
                } else {
                    System.out.println(serverAnswer);
                    this.execute();
                }
            }
        };
    }
}
