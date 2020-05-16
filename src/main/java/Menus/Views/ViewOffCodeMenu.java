package Menus.Views;

import Menus.Edits.EditOffCodeMenu;
import Menus.LoginOrRegisterMenu;
import Menus.Menu;

import java.util.HashMap;

public class ViewOffCodeMenu extends Menu {
    private String offCodeID;

    public ViewOffCodeMenu(Menu fatherMenu, String menuName, String offCodeID) {
        super(fatherMenu, menuName);
        this.offCodeID = offCodeID;
        setSubMenus();
    }

    private void setSubMenus() {
        HashMap<Integer, Menu> subMenus = new HashMap<>();
        subMenus.put(1, new EditOffCodeMenu(this, "Edit OffCode Menu"));
        subMenus.put(2, new LoginOrRegisterMenu(this, "Login\\Register Menu"));
        this.setSubMenus(subMenus);
    }

    private void getOffCodeInfo() {
        server.clientToServer("view offCode" + "+" + Menu.username + "+" + offCodeID);
        String serverAnswer = server.serverToClient();
        System.out.println(serverAnswer);
    }

    @Override
    protected void show() {
        super.show();
        getOffCodeInfo();
    }
}