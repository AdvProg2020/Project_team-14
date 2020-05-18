package Menus.Edits;

import Controller.Server;
import Menus.LoginOrRegisterMenu;
import Menus.Menu;

import java.util.HashMap;

public class EditOffCodeMenu extends EditsMenu {

    public EditOffCodeMenu(Menu fatherMenu, String menuName, String itemID) {
        super(fatherMenu, menuName, "offCode", itemID);
        this.logoutType = false;
        HashMap<Integer, Menu> subMenus = new HashMap<>();
        subMenus.put(1, getChangeSubMenu(this, "start time", "(Format: mm-dd-yyyy hh-mm-ss)"));
        subMenus.put(2, getChangeSubMenu(this, "end time", "(Format: mm-dd-yyyy hh-mm-ss)"));
        subMenus.put(3, getChangeSubMenu(this, "percentage", "(insert a number from 1 to 100)"));
        subMenus.put(4, getChangeSubMenu(this, "ceiling", "(insert a number)"));
        /*subMenus.put(1, getEditTimes());
        subMenus.put(2, getEditNumericalAttribute("percentage"));
        subMenus.put(3, getEditNumericalAttribute("ceiling"));
        subMenus.put(4, getEditUsers());*/
        this.setSubMenus(subMenus);
    }

    /*private Menu getEditTimes() {
        return new Menu(this, "Edit Start/End time") {

            @Override
            public void execute() {
                System.out.println(this.getMenuName());
                System.out.println("if you input back we will go back");

                //get info from user
                System.out.println("Do you want to update Start time OR End time:(type Start or End");
                String dateType = scanner.nextLine();
                checkBack(dateType);
                System.out.println("please insert updated time below, using this formats:(mm-dd-yyyy hh-mm-ss)");
                String updatedDate = scanner.nextLine();
                checkBack(updatedDate);

                //send info to server
                String message = "edit offCode" + "+" + "time" + "+" + dateType + "+" + updatedDate;
                server.clientToServer(message);
                System.out.println(server.serverToClient());
                if (server.serverToClient().equals("edit offCode successful")) {
                    fatherMenu.execute();
                } else {
                    this.execute();
                }
            }
        };
    }*/

    /*private Menu getEditNumericalAttribute(String type) {
        return null;
    }*/

    /*private Menu getEditUsers() {
        return null;
    }*/
}
