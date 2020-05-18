package Menus.Filters;

import Menus.LoginOrRegisterMenu;
import Menus.Menu;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;

public class AccountsFilterMenu extends FiltersMenu {
    private void makeFilters() {
        filters.add("role");
        filters.add(new String());
        filters.set(1, null);
        filters.add("minCredit");
        filters.add(new String());
        filters.set(3, null);
        filters.add("maxCredit");
        filters.add(new String());
        filters.set(5, null);
        filters.add("ProductsSalesman:");
        filters.add(new String());
        filters.set(7, null);
    }

    public AccountsFilterMenu(Menu fatherMenu, String menuName) {
        super(fatherMenu, menuName);
        filters = new ArrayList<Object>();
        makeFilters();
        HashMap<Integer, Menu> subMenus = new HashMap<Integer, Menu>();
        subMenus.put(1, getChangeRoleMenu());
        subMenus.put(2, getChangeCreditLimitMenu("min"));
        subMenus.put(3, getChangeCreditLimitMenu("max"));
        subMenus.put(4, new LoginOrRegisterMenu(this, "Login\\Register Menu"));
        this.setSubMenus(subMenus);
    }

    public String getMinLimit() {
        if (filters.get(3) == null) {
            return "none";
        } else {
            return (String) filters.get(3);
        }
    }

    public void setMinCredit(String credit) {
        if (credit.equals("none")) {
            filters.set(3, null);
        } else {
            filters.set(3, credit);
        }
    }

    public String getMaxLimit() {
        if (filters.get(5) == null) {
            return "none";
        } else {
            return (String) filters.get(5);
        }
    }

    public void setMaxCredit(String credit) {
        if (credit.equals("none")) {
            filters.set(5, null);
        } else {
            filters.set(5, credit);
        }
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

    private Menu getChangeCreditLimitMenu(String type) {
        return new Menu(this, "Change " + type + " Credit Limit Menu") {
            @Override
            public void execute() throws ParseException, IOException {
                System.out.println(menuName);
                System.out.println("if you input back we will go back");
                while (true) {
                    if (type.equals("min")) {
                        System.out.println("your current " + type + " Credit Limit is: " + getMinLimit());
                    } else {
                        System.out.println("your current " + type + " Credit Limit is: " + getMaxLimit());
                    }
                    System.out.println("please insert your new " + type + " Credit Limit or clear " +
                            "for clearing current filter");
                    String input = scanner.nextLine();
                    if (input.equals("back")) {
                        fatherMenu.execute();
                    } else if (input.equals("clear")) {
                        if (type.equals("min")) {
                            setMinCredit("clear");
                        } else {
                            setMaxCredit("clear");
                        }
                    } else {
                        if (input.matches("(\\d+)")) {
                            if (input.length() > 8) {
                                System.out.println("out of limit number for credit");
                            } else {
                                if (type.equals("min")) {
                                    setMinCredit(input);
                                } else {
                                    setMaxCredit(input);
                                }
                            }
                        } else {
                            System.out.println("input should be number or clear or back");
                        }
                    }
                }
            }
        };
    }

    private Menu getChangeRoleMenu() {
        return new Menu(this, "Change Role Menu") {
            @Override
            public void execute() throws ParseException, IOException {
                System.out.println("Change Role Menu");
                System.out.println("if you input back we will go back");
                while (true) {
                    System.out.println("your current role(s) is: " + getRole());
                    System.out.println("please insert your new added role or clear for clearing current filter");
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
