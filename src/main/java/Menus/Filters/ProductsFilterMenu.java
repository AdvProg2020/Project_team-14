package Menus.Filters;

import Menus.LoginOrRegisterMenu;
import Menus.Menu;
import Menus.shows.ShowProductsMenu;

import java.io.IOException;
import java.text.ParseException;
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

    public void setConfirmation(String confirmation) {
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
        return new Menu(this, "Change Seller IDs Menu") {
            private boolean checkInputValidation(String username) throws ParseException, IOException {
                if (username.equals("clear")) {
                    return true;
                }
                server.clientToServer("what is accounts role+" + username);
                return server.serverToClient().equalsIgnoreCase("salesman");
            }

            @Override
            public void execute() throws ParseException, IOException {
                System.out.println(this.getMenuName());
                System.out.println("if you input back we will go back");
                while (true) {
                    System.out.println("your current sellers(s) is: " + getSalesmanIDs());
                    System.out.println("please insert your new added salesman Username or clear for clearing current filter");
                    String input = scanner.nextLine();
                    if (input.equals("back")) {
                        fatherMenu.execute();
                    } else {
                        if (checkInputValidation(input)) {
                            setSalesmanIDs(input);
                        } else {
                            System.out.println("that's not a valid username for a salesman");
                        }
                    }
                }
            }
        };
    }

    private Menu getChangeCategoriesMenu() {
        return new Menu(this, "Change Categories Menu") {
            private boolean checkInputValidation(String category) throws ParseException, IOException {
                if (username.equals("clear")) {
                    return true;
                }
                server.clientToServer("is category exists+" + category);
                return server.serverToClient().equalsIgnoreCase("salesman");
            }

            @Override
            public void execute() throws ParseException, IOException {
                System.out.println(this.getMenuName());
                System.out.println("if you input back we will go back");
                while (true) {
                    System.out.println("your current category(ies) is: " + getCategories());
                    System.out.println("please insert your new added category or clear for clearing current filter");
                    String input = scanner.nextLine();
                    if (input.equals("back")) {
                        fatherMenu.execute();
                    } else {
                        if (checkInputValidation(input)) {
                            setCategories(input);
                        } else {
                            System.out.println("that's not a valid category name");
                        }
                    }
                }
            }
        };
    }

    private Menu getChangeRemainderMenu() {
        return new Menu(this, "Change Remainder Menu") {
            private boolean checkInputValidation(String available) throws ParseException, IOException {
                if (username.equals("clear")) {
                    return true;
                }
                if (available.equalsIgnoreCase("available") || available.equalsIgnoreCase
                        ("not available")) {
                    return true;
                }
                return false;
            }

            @Override
            public void execute() throws ParseException, IOException {
                System.out.println(this.getMenuName());
                System.out.println("if you input back we will go back");
                while (true) {
                    System.out.println("your current availability is: " + getRemainder());
                    System.out.println("please insert available for seeing available ones or not available to see not" +
                            " available ones or clear for clearing filter");
                    String input = scanner.nextLine();
                    if (input.equals("back")) {
                        fatherMenu.execute();
                    } else {
                        if (checkInputValidation(input)) {
                            setRemainder(input);
                        } else {
                            System.out.println("that's not a valid input");
                        }
                    }
                }
            }
        };
    }

    private Menu getChangeConfirmationMenu() {
        return new Menu(this, "Change Confirmation Menu") {
            private boolean checkInputValidation(String confirmation) throws ParseException, IOException {
                if (username.equals("clear")) {
                    return true;
                }
                if (confirmation.equalsIgnoreCase("ACCEPTED") || confirmation.equalsIgnoreCase
                        ("DENIED") || confirmation.equalsIgnoreCase("CHECKING")) {
                    return true;
                }
                return false;
            }

            @Override
            public void execute() throws ParseException, IOException {
                System.out.println(this.getMenuName());
                System.out.println("if you input back we will go back");
                while (true) {
                    System.out.println("your confirmation state(s) is: " + getConfirmation());
                    System.out.println("please insert your new confirmation or clear for clearing this filter");
                    String input = scanner.nextLine();
                    if (input.equals("back")) {
                        fatherMenu.execute();
                    } else {
                        if (checkInputValidation(input)) {
                            setConfirmation(input);
                        } else {
                            System.out.println("that's not a valid category name");
                        }
                    }
                }
            }
        };
    }
}
