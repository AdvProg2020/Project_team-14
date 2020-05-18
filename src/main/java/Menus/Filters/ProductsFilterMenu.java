package Menus.Filters;

import Menus.LoginOrRegisterMenu;
import Menus.Menu;
import Menus.shows.ShowProductsMenu;

import java.util.ArrayList;
import java.util.HashMap;

public class ProductsFilterMenu extends FiltersMenu {
    private void makeFilters() {
        filters.add("salesmanIDs");
        filters.add(new String());
        filters.set(1, null);
        filters.add("categories");
        filters.add(new String());
        filters.set(3, null);
        filters.add("remainder");
        filters.add(new String());
        filters.set(5, null);
        filters.add("Confirmation");
        filters.add(new String());
        filters.set(7, null);
    }

    public ProductsFilterMenu(Menu fatherMenu, String menuName) {
        super(fatherMenu, menuName);
        filters = new ArrayList<Object>();
        makeFilters();
        HashMap<Integer, Menu> subMenus = new HashMap<Integer, Menu>();
        if (((ShowProductsMenu) fatherMenu).isCanChangeSalesmanIDsFilter()) {
            subMenus.put(1, getChangeSalesmanIDsMenu());
            subMenus.put(2, getChangeCategoriesMenu());
            subMenus.put(3, getChangeRemainderMenu());
            subMenus.put(4, getChangeConfirmationMenu());
            subMenus.put(5, new LoginOrRegisterMenu(this, "Login\\Register Menu"));
        } else {
            subMenus.put(1, getChangeCategoriesMenu());
            subMenus.put(2, getChangeRemainderMenu());
            subMenus.put(4, getChangeConfirmationMenu());
            subMenus.put(3, new LoginOrRegisterMenu(this, "Login\\Register Menu"));
        }
        this.setSubMenus(subMenus);
    }

    public String getSalesmanIDs() {
        if (filters.get(1) == null) {
            return "none";
        } else {
            return (String) filters.get(1);
        }
    }

    public void setSalesmanIDs(String salesmanID) {
        if (salesmanID.equals("clear")) {
            filters.set(1, null);
        } else {
            if (this.filters.get(1) == null) {
                filters.set(1, salesmanID);
            } else {
                filters.set(1, filters.get(1) + "," + salesmanID);
            }
        }
    }

    public String getCategories() {
        if (filters.get(3) == null) {
            return "none";
        } else {
            return (String) filters.get(3);
        }
    }

    public void setCategories(String category) {
        if (category.equals("clear")) {
            filters.set(3, null);
        } else {
            if (this.filters.get(3) == null) {
                filters.set(3, category);
            } else {
                filters.set(3, filters.get(3) + "," + category);
            }
        }
    }

    public String getRemainder() {
        if (filters.get(5) == null) {
            return "none";
        } else {
            return (String) filters.get(5);
        }
    }

    public void setRemainder(String available) {
        if (available.equals("none")) {
            filters.set(5, null);
        } else {
            filters.set(5, available);
        }
    }

    public String getConfirmation() {
        if (filters.get(7) == null) {
            return "none";
        } else {
            return (String) filters.get(7);
        }
    }

    public void serConfirmation(String confirmation) {
        if (confirmation.equalsIgnoreCase("none")) {
            filters.set(7, null);
        } else {
            if (this.filters.get(7) == null) {
                filters.set(7, confirmation);
            } else {
                filters.set(7, filters.get(7) + "," + confirmation);
            }
        }
    }


    private Menu getChangeSalesmanIDsMenu() {
        return new Menu(this, "Change Salesman IDs Menu") {

        };
    }

    private Menu getChangeCategoriesMenu() {
        return new Menu(this, "Change Categories Menu") {

        };
    }

    private Menu getChangeRemainderMenu() {
        return new Menu(this, "Change Remainder Menu") {

        };
    }

    private Menu getChangeConfirmationMenu() {
        return new Menu(this, "Change Confirmation Menu") {

        };
    }
}
