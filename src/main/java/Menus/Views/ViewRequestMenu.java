package Menus.Views;

import Controller.Server;
import Menus.LoginOrRegisterMenu;
import Menus.Menu;

import java.util.HashMap;

public class ViewRequestMenu extends Menu {
    private int whereItHasBeenCalled;
    private String requestID;

    private void RequestSubMenus(HashMap subMenus) {
        server.clientToServer("is request state checking " + this.requestID);
        if (server.serverToClient().equalsIgnoreCase("yes")) {
            subMenus.put(1, getAcceptRequestMenu());
            subMenus.put(2, getDeclineRequestMenu());
            subMenus.put(3, new ViewAccountMenu(this, "View Account Menu"));
            subMenus.put(4, new LoginOrRegisterMenu(this, "Login\\Register Menu"));
        } else {
            subMenus.put(1, new ViewAccountMenu(this, "View Account Menu"));
            subMenus.put(2, getDeleteRequestMenu());
            subMenus.put(3, new LoginOrRegisterMenu(this, "Login\\Register Menu"));
        }
    }

    public ViewRequestMenu(Menu fatherMenu, String menuName, String requestType, String requestID) {
        super(fatherMenu, menuName);
        this.requestID = requestID;
        this.logoutType = false;
        HashMap<Integer, Menu> subMenus = new HashMap<Integer, Menu>();
        if (requestType.equalsIgnoreCase("Register_Salesman")) {
            this.whereItHasBeenCalled = 1;
        }
        RequestSubMenus(subMenus);
        this.setSubMenus(subMenus);
    }

    private Menu getAcceptRequestMenu() {
        return new Menu(this, "Accept Request Menu") {

        };
    }

    private Menu getDeclineRequestMenu() {
        return new Menu(this, "Decline Request Menu") {

        };
    }

    private Menu getDeleteRequestMenu() {
        return new Menu(this, "Delete Request Menu") {

        };
    }

    private void getRequestInfo() {
        server.clientToServer("view request " + Menu.username + " " + requestID);
        String serverAnswer = server.serverToClient();
        System.out.println(serverAnswer);
    }

    @Override
    protected void show() {
        super.show();
        getRequestInfo();
    }
}
