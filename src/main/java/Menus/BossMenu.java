package Menus;

import Menus.Manages.*;
import Menus.Views.ViewAccountMenu;

import java.text.ParseException;
import java.util.HashMap;

public class BossMenu extends Menu {
    public BossMenu(Menu fatherMenu, String menuName) throws ParseException {
        super(fatherMenu, menuName);
        this.logoutType = false;
        HashMap<Integer, Menu> subMenus = new HashMap<Integer, Menu>();
        subMenus.put(1, new ViewAccountMenu(this, "View Personal Info Menu"));
        subMenus.put(2, new ManageAccountsMenu(this, "Manage Accounts Menu"));
        subMenus.put(3, new ManageRequestsMenu(this, "Manage Requests Menu"));
        subMenus.put(4, new ManageProductsMenu(this, "Manage Products Menu"));
        subMenus.put(5, new ManageOffCodesMenu(this, "Manage OffCodes Menu"));
        subMenus.put(6, new ManageCategoriesMenu(this, "Manage Categories Menu"));
        subMenus.put(7, new ManageLogsMenu(this, "Manage Logs Menu"));
        subMenus.put(8, new ManageSalesMenu(this, "Manage Sales Menu"));
        subMenus.put(9, new LoginOrRegisterMenu(this, "Login\\Register Menu"));
        this.setSubMenus(subMenus);
    }
}
