package Menus.Views;

//import Menus.Edits.EditOffCodeMenu;
import Menus.Edits.EditOffCodeMenu;
import Menus.LoginOrRegisterMenu;
import Menus.Menu;

import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;

public class ViewOffCodeMenu extends Menu {
    private String offCodeID;
    private int whereHasBeenCalled;

    public ViewOffCodeMenu(Menu fatherMenu, String menuName, String offCodeID, int whereHasBeenCalled) {
        super(fatherMenu, menuName);
        this.offCodeID = offCodeID;
        this.whereHasBeenCalled = whereHasBeenCalled;
        setSubMenus();
    }

    private void setSubMenus() {
        HashMap<Integer, Menu> subMenus = new HashMap<>();
        if (whereHasBeenCalled == 0) {
            subMenus.put(1, new EditOffCodeMenu(this, "Edit OffCode Menu", offCodeID));
            subMenus.put(2, new LoginOrRegisterMenu(this, "Login\\Register Menu"));
        } else {
            subMenus.put(1, new LoginOrRegisterMenu(this, "Login\\Register Menu"));
        }
        this.setSubMenus(subMenus);
    }

    private void getOffCodeInfo() throws ParseException, IOException {
        server.clientToServer("view offCode" + "+" + Menu.username + "+" + offCodeID);
        String serverAnswer = server.serverToClient();
        System.out.println(serverAnswer);
    }

    @Override
    protected void show() throws ParseException, IOException {
        super.show();
        getOffCodeInfo();
    }
}