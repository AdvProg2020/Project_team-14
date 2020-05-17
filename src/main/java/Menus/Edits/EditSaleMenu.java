package Menus.Edits;

import Menus.Menu;

import java.util.HashMap;

public class EditSaleMenu extends EditsMenu {
    public EditSaleMenu(Menu fatherMenu, String menuName, String itemID) {
        super(fatherMenu, menuName, "sale", itemID);
        this.logoutType = false;
        HashMap<Integer, Menu> subMenus = new HashMap<>();
        subMenus.put(1, getChangeSubMenu(this, "Start Date", "(Time Format: mm-dd-yyyy hh-mm-ss)"));
        subMenus.put(2, getChangeSubMenu(this, "End Date", "(Time Format: mm-dd-yyyy hh-mm-ss)"));
        subMenus.put(3, getChangeSubMenu(this, "Percentage", "(a number from 1 to 100)"));
        //subMenus.put(4, getAdditionSubMenu(this, "ProductIDs"));
    }
}
