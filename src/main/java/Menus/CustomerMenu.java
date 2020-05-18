package Menus;

import Menus.Views.ViewAccountMenu;
import Menus.shows.ShowOffCodesMenu;

import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;

public class CustomerMenu extends Menu {
    public CustomerMenu(Menu fatherMenu, String menuName) throws ParseException, IOException {
        super(fatherMenu, menuName);
        this.logoutType = false;
        HashMap<Integer, Menu> subMenus = new HashMap<Integer, Menu>();
        subMenus.put(1, new ViewAccountMenu(this, "View Personal Info Menu"));
        subMenus.put(2, new ShowOffCodesMenu(this, "Show OffCodes Menu", 1));
        subMenus.put(3, new LoginOrRegisterMenu(this, "Login\\Register Menu"));
        this.setSubMenus(subMenus);
    }
}
