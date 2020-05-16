package Menus.shows;

import Menus.LoginOrRegisterMenu;
import Menus.Menu;
import Menus.Sorts.OffCodesSortMenu;
import Menus.Views.ViewOffCodeMenu;

import java.util.HashMap;

public class ShowOffCodesMenu extends ShowsMenu {
    private int whereHasBeenCalled;

    public ShowOffCodesMenu(Menu fatherMenu, String menuName, int whereHasBeenCalled) {
        super(fatherMenu, menuName);
        this.whereHasBeenCalled = whereHasBeenCalled;
        HashMap<Integer, Menu> subMenus = new HashMap<>();
        //subMenus.put(1, new OffCodesFilterMenu(this, "OffCode Filter Menu"));
        subMenus.put(2, new OffCodesSortMenu(this, "OffCode Sort Menu"));
        if (whereHasBeenCalled == 0) {
            subMenus.put(3, getSelectMenu());
            subMenus.put(4, new LoginOrRegisterMenu(this, "Login\\Register Menu"));
        } else {
            subMenus.put(3, new LoginOrRegisterMenu(this, "Login\\Register Menu"));
        }

        this.setSubMenus(subMenus);
    }

    private Menu getSelectMenu() {
        return new Menu(this, "Select Menu") {

            @Override
            public void execute() {
                System.out.println(menuName);
                System.out.println("if you input back we will go back");

                //get info from user
                System.out.println("select one of the above offCodeID to see detail:");
                String offCodeID = scanner.nextLine();
                checkBack(offCodeID);
                String allOffCodes = ((ShowsMenu) fatherMenu).getServerAnswer();
                if (isIdInTheList(allOffCodes, offCodeID)) {
                    new ViewOffCodeMenu(this, "View OffCode Menu", offCodeID).execute();
                } else {
                    System.out.println("we couldn't found this id the above list, try another one");
                    fatherMenu.execute();
                }
            }

            public boolean isIdInTheList(String list, String id) {
                String[] items = list.split("\n");
                for (String item : items) {
                    if (item.equalsIgnoreCase(id)) return true;
                }
                return false;
            }
        };
    }
}
