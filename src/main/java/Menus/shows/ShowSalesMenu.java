package Menus.shows;

//import Menus.Filters.SalesFilterMenu;
import Menus.LoginOrRegisterMenu;
import Menus.Menu;
import Menus.Sorts.SalesSortMenu;
import Menus.Views.ViewSalesMenu;

import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;

public class ShowSalesMenu extends ShowsMenu {
    public ShowSalesMenu(Menu fatherMenu, String menuName) {
        super(fatherMenu, menuName);
        this.type = "sales";
        HashMap<Integer, Menu> subMenus = new HashMap<>();
        //filter = new SalesFilterMenu(this, "Sales Filter Menu");
        sort = new SalesSortMenu(this, "Sales Sort Menu");
        //subMenus.put(1, filter);
        subMenus.put(2, sort);
        subMenus.put(3, getSelectSale());
        subMenus.put(4, new LoginOrRegisterMenu(this, "Login\\Register Menu"));
        this.setSubMenus(subMenus);
    }

    private Menu getSelectSale() {
        return new Menu(this, "Select Sale Menu") {
            private boolean hasBeenCalled = true;

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

                //get info from user
                System.out.println("Select one of the above SaleID to see detail:");
                String saleID = scanner.nextLine();
                checkBack(saleID);

                //check existence
                String allSale = ((ShowsMenu) fatherMenu).getServerAnswer();
                if (isIdInTheList(allSale, saleID)) {
                    new ViewSalesMenu(this, "View Sale Menu", saleID).execute();
                } else {
                    System.err.println("we couldn't find requested ID in the above list, try another ID");
                    this.execute();
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
