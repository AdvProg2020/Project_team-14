package Menus.Filters;

import Menus.Menu;

import java.util.HashMap;

public class AccountsFilterMenu extends Menu {
    String role;

    public AccountsFilterMenu(Menu fatherMenu, String menuName) {
        super(fatherMenu, menuName);
        this.logoutType = false;
        this.role = null;
        HashMap<Integer, Menu> subMenus = new HashMap<Integer, Menu>();
        subMenus.put(1, getChangeRoleMenu());
    }

    public String getRole() {
        if (this.role != null) {
            return this.role;
        } else {
            return "none";
        }
    }

    private Menu getChangeRoleMenu() {
        return new Menu(this, "Change Role Menu") {
            @Override
            public void execute() {
                System.out.println("Change Role Menu");
                System.out.println("if you input back we will go back");
                System.out.println("your current role is: " + getRole());
            }
        };
    }
}
