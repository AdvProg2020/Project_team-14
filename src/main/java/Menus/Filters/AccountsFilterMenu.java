package Menus.Filters;

import Menus.LoginOrRegisterMenu;
import Menus.Menu;

import java.util.ArrayList;
import java.util.HashMap;

public class AccountsFilterMenu extends FiltersMenu {
    private void makeFilters() {
        filters.add("role");
        filters.add(new String());
        filters.set(1, null);
    }

    public AccountsFilterMenu(Menu fatherMenu, String menuName) {
        super(fatherMenu, menuName);
        filters = new ArrayList<Object>();
        makeFilters();
        HashMap<Integer, Menu> subMenus = new HashMap<Integer, Menu>();
        subMenus.put(1, getChangeRoleMenu());
        subMenus.put(2, new LoginOrRegisterMenu(this, "Login\\Register Menu"));
        this.setSubMenus(subMenus);
    }

    public String getRole() {
        if (filters.get(1) != null) {
            return (String) filters.get(1);
        } else {
            return "none";
        }
    }

    public void setRole(String role) {
        if (role.equals("clear")) {
            filters.set(1, null);
        } else {
            if (this.filters.get(1) == null) {
                filters.set(1, role);
            } else {
                filters.set(1, filters.get(1) + "," + role);
            }
        }
    }

    private Menu getChangeRoleMenu() {
        return new Menu(this, "Change Role Menu") {
            @Override
            public void execute() {
                System.out.println("Change Role Menu");
                System.out.println("if you input back we will go back");
                while (true) {
                    System.out.println("your current role(s) is: " + getRole());
                    System.out.println("please insert you new added role or clear for clearing current filter");
                    String input = scanner.nextLine();
                    if (input.equals("back")) {
                        fatherMenu.execute();
                    } else {
                        if (input.equalsIgnoreCase("boss") || input.equalsIgnoreCase("customer")
                                || input.equalsIgnoreCase("salesman") || input.equalsIgnoreCase
                                ("clear")) {
                            setRole(input);
                        } else {
                            System.out.println("not a valid role");
                        }
                    }
                }
            }
        };
    }
}
