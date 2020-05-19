package Menus.shows;

import Menus.BossMenu;
import Menus.Filters.ProductsFilterMenu;
import Menus.LoginOrRegisterMenu;
import Menus.Manages.ManageAccountsMenu;
import Menus.Manages.ManageProductsMenu;
import Menus.Menu;
import Menus.Sorts.ProductsSortMenu;
import Menus.Views.ViewAccountMenu;
import Menus.Views.ViewCategoryMenu;
import Menus.Views.ViewProductMenu;

import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;

public class ShowProductsMenu extends ShowsMenu {
    private boolean canChangeSalesmanIDsFilter;
    private boolean canChangeCategoryFilter;

    public ShowProductsMenu(Menu fatherMenu, String menuName) {
        super(fatherMenu, menuName);
        this.type = "products";
        if (menuName.equalsIgnoreCase("Show My Products Menu")) {
            this.type = "my products";
        }
        HashMap<Integer, Menu> subMenus = new HashMap<Integer, Menu>();
        if (fatherMenu instanceof ViewCategoryMenu) {
            canChangeCategoryFilter = false;
        } else {
            canChangeCategoryFilter = true;
        }
        if (fatherMenu instanceof ViewAccountMenu) {
            canChangeSalesmanIDsFilter = false;
        } else {
            canChangeSalesmanIDsFilter = true;
        }
        if (fatherMenu instanceof ManageAccountsMenu) {
            canChangeSalesmanIDsFilter = false;
            if (fatherMenu.getFatherMenu() instanceof BossMenu) {
                canChangeSalesmanIDsFilter = true;
            }
        }
        filter = new ProductsFilterMenu(this, "Product Filter Menu");
        sort = new ProductsSortMenu(this, "Product Sort Menu");
        if (fatherMenu instanceof ViewAccountMenu) {
            ((ProductsFilterMenu) filter).setSalesmanIDs(((ViewAccountMenu) fatherMenu).getUsername());
        }
        if(fatherMenu instanceof ViewCategoryMenu){
            ((ProductsFilterMenu) filter).setCategories(((ViewCategoryMenu) fatherMenu).getCategoryName());
        }
        subMenus.put(1, filter);
        subMenus.put(2, sort);
        subMenus.put(3, getSelectMenu());
        subMenus.put(4, new LoginOrRegisterMenu(this, "Login\\Register Menu"));
        this.setSubMenus(subMenus);
    }

    private Menu getSelectMenu() {
        return new Menu(this, "Select Menu") {
            private boolean hasBeenCalled = true;

            private boolean isThereProductWithIDInList(String list, String productID) {
                int wordCount = Menu.wordCount(list);
                String[] lists = list.split("\\s");
                for (int i = 0; i < wordCount; i++) {
                    if (lists[i].equals(productID) && lists[i - 1].equals("ID:") && lists[i - 2].equals("Product")) {
                        return true;
                    }
                }
                return false;
            }

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
                System.out.println("select one of products above by inserting product ID");
                String productID = scanner.nextLine();
                if (productID.equalsIgnoreCase("back")) {
                    fatherMenu.execute();
                }
                String products = ((ShowProductsMenu) fatherMenu).getServerAnswer();
                if (isThereProductWithIDInList(products, productID)) {
                    ViewProductMenu menu = new ViewProductMenu(this, "View Product Menu", productID);
                    menu.execute();
                } else {
                    System.out.println("this account is not on the list");
                    this.execute();
                }
            }
        };
    }

    public boolean isCanChangeCategoryFilter() {
        return canChangeCategoryFilter;
    }

    public boolean isCanChangeSalesmanIDsFilter() {
        return canChangeSalesmanIDsFilter;
    }
}
