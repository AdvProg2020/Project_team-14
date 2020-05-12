package Menus.Filters;

import Menus.LoginOrRegisterMenu;
import Menus.Menu;
import Menus.shows.ShowRequestsMenu;

import java.util.ArrayList;
import java.util.HashMap;

public class RequestsFilterMenu extends FiltersMenu {
    private void makeFilters() {
        filters.add("requestType");
        filters.add(new String());
        filters.set(1, null);
        filters.add("username");
        filters.add(new String());
        filters.set(3, null);
    }

    public RequestsFilterMenu(Menu fatherMenu, String menuName) {
        super(fatherMenu, menuName);
        filters = new ArrayList<Object>();
        HashMap<Integer, Menu> subMenus = new HashMap<Integer, Menu>();
        makeFilters();
        if (((ShowRequestsMenu) fatherMenu).isCanChangeUsernameFilter()) {
            subMenus.put(1, getChangeRequestTypeMenu());
            subMenus.put(2, getChangeUsernameMenu());
            subMenus.put(3, new LoginOrRegisterMenu(this, "Login\\Register Menu"));
        } else {
            subMenus.put(1, getChangeRequestTypeMenu());
            subMenus.put(2, new LoginOrRegisterMenu(this, "Login\\Register Menu"));
        }
        this.setSubMenus(subMenus);
    }

    public String getUsernameFilter() {
        if (filters.get(3) == null) {
            return "none";
        } else {
            return (String) filters.get(3);
        }
    }

    public void setUsernameFilter(String username) {
        if (username.equals("clear")) {
            filters.set(3, null);
        } else {
            if (this.filters.get(3) == null) {
                filters.set(3, username);
            } else {
                filters.set(3, filters.get(3) + "," + username);
            }
        }
    }

    public String getRequestType() {
        if (filters.get(1) == null) {
            return "none";
        } else {
            return (String) filters.get(1);
        }
    }

    public void setRequestType(String requestType) {
        if (requestType.equals("clear")) {
            filters.set(1, null);
        } else {
            if (this.filters.get(1) == null) {
                filters.set(1, requestType);
            } else {
                filters.set(1, filters.get(1) + "," + requestType);
            }
        }
    }

    private Menu getChangeRequestTypeMenu() {
        return new Menu(this, "Change Request Type Menu") {
            private boolean checkInputValidation(String requestType) {
                if (requestType.equals("clear")) {
                    return true;
                }
                if (requestType.equals("REGISTER_SALESMAN")) {
                    return true;
                } else {
                    return false;
                }
            }

            @Override
            public void execute() {
                System.out.println(this.getMenuName());
                System.out.println("if you input back we will go back");
                while (true) {
                    System.out.println("your current request type(s) is: " + getRequestType());
                    System.out.println("please insert your new added request type or clear for clearing current filter");
                    String input = scanner.nextLine();
                    if (input.equals("back")) {
                        fatherMenu.execute();
                    } else {
                        if (checkInputValidation(input)) {
                            setRequestType(input);
                        } else {
                            System.out.println("not a valid request type");
                        }
                    }
                }
            }
        };
    }

    private Menu getChangeUsernameMenu() {
        return new Menu(this, "Change Username Menu") {
            private boolean checkInputValidation(String username) {
                if (username.equals("clear")) {
                    return true;
                }
                server.clientToServer("is account requestable " + username);
                if (server.serverToClient().equalsIgnoreCase("yes")) {
                    return true;
                } else {
                    return false;
                }
            }

            @Override
            public void execute() {
                System.out.println(this.getMenuName());
                System.out.println("if you input back we will go back");
                while (true) {
                    System.out.println("your current username(s) is: " + getUsernameFilter());
                    System.out.println("please insert your new added username or clear for clearing current filter");
                    String input = scanner.nextLine();
                    if (input.equals("back")) {
                        fatherMenu.execute();
                    } else {
                        if (checkInputValidation(input)) {
                            setUsernameFilter(input);
                        } else {
                            System.out.println("that's not a valid username or it's a username of a boss");
                        }
                    }
                }
            }
        };
    }
}
