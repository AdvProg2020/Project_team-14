package Menus.Views;

import Menus.*;
import Menus.Edits.EditAccountMenu;
import Menus.shows.ShowRequestsMenu;


import java.util.HashMap;

public class ViewAccountMenu extends Menu {
    private int whereItHasBeenCalled;
    private String username;

    private void PersonalInfoSubMenus(HashMap subMenus) {
        if (whereItHasBeenCalled <= 3) {
            subMenus.put(1, new EditAccountMenu(this, "Edit Personal Info Menu"));
            subMenus.put(2, getDeleteAccountMenu());
            if (whereItHasBeenCalled == 3) {
                subMenus.put(3, new LoginOrRegisterMenu(this, "Login\\Register Menu"));
            } else {
                subMenus.put(3, new LoginOrRegisterMenu(this, "Login\\Register Menu"));
            }
        } else {
            server.clientToServer("see authorization+" + Menu.username + "+" + this.username);
            String serverAnswer = server.serverToClient();
            if (!serverAnswer.equalsIgnoreCase("boss no")) {
                if (serverAnswer.equalsIgnoreCase("boss yes")) {
                    subMenus.put(1, getDeleteAccountMenu());
                    subMenus.put(2, new LoginOrRegisterMenu(this, "Login\\Register Menu"));
                } else {
                    subMenus.put(1, new ShowRequestsMenu(this, "Show Requests Menu"));
                    subMenus.put(2, getDeleteAccountMenu());
                    subMenus.put(3, new LoginOrRegisterMenu(this, "Login\\Register Menu"));
                }
            } else {
                subMenus.put(1, new LoginOrRegisterMenu(this, "Login\\Register Menu"));
            }
        }
    }

    public ViewAccountMenu(Menu fatherMenu, String menuName) {
        super(fatherMenu, menuName);
        this.logoutType = false;
        HashMap<Integer, Menu> subMenus = new HashMap<Integer, Menu>();
        if (fatherMenu instanceof BossMenu) {
            whereItHasBeenCalled = 1;
            PersonalInfoSubMenus(subMenus);
        } else if (fatherMenu instanceof CustomerMenu) {
            whereItHasBeenCalled = 2;
            PersonalInfoSubMenus(subMenus);
        } else if (fatherMenu instanceof SalesmanMenu) {
            whereItHasBeenCalled = 3;
            PersonalInfoSubMenus(subMenus);
        } else {
            whereItHasBeenCalled = 4;
        }
        this.setSubMenus(subMenus);
    }

    private Menu getDeleteAccountMenu() {
        return new Menu(this, "Delete Account Menu") {
            @Override
            public void execute() {
                if (fatherMenu.getWhereItHasBeenCalled() <= 3) {
                    System.out.println("are you sure you want to delete your account?");
                } else {
                    System.out.println("are you sure you want to delete this account?");
                }
                String input = scanner.nextLine();
                if (input.equalsIgnoreCase("yes")) {
                    if (fatherMenu.getWhereItHasBeenCalled() <= 3) {
                        server.clientToServer("delete account+" + Menu.username + "+" + Menu.username);
                    } else {
                        server.clientToServer("delete account+" + Menu.username + "+" +
                                ((ViewAccountMenu) fatherMenu).getUsername());
                    }
                    String serverAnswer = server.serverToClient();
                    System.out.println(serverAnswer);
                    if (serverAnswer.equalsIgnoreCase("deleted successfully")) {
                        if (fatherMenu.getWhereItHasBeenCalled() <= 3) {
                            Menu.isUserLogin = false;
                            Menu.username = null;
                            Menu.userType = null;
                        }
                        fatherMenu.getFatherMenu().getFatherMenu().execute();
                    } else {
                        fatherMenu.execute();
                    }
                } else if (input.equalsIgnoreCase("no")) {
                    fatherMenu.execute();
                } else {
                    System.out.println("error");
                    this.execute();
                }
            }
        };
    }


    public String getUsername() {
        return username;
    }

    public void setAccount(String username) {
        this.username = username;
        HashMap<Integer, Menu> subMenus = new HashMap<Integer, Menu>();
        PersonalInfoSubMenus(subMenus);
        this.setSubMenus(subMenus);
    }

    public int getWhereItHasBeenCalled() {
        return whereItHasBeenCalled;
    }

    private void getPersonalInfo() {
        server.clientToServer("view personal info+" + Menu.username);
        String serverAnswer;
        serverAnswer = server.serverToClient();
        System.out.println(serverAnswer);
    }

    private void getAccountInfo() {
        server.clientToServer("view account info+" + Menu.username + "+" + this.username);
        String serverAnswer;
        serverAnswer = server.serverToClient();
        System.out.println(serverAnswer);
    }

    @Override
    protected void show() {
        super.show();
        if (whereItHasBeenCalled <= 3)
            this.getPersonalInfo();
        else
            this.getAccountInfo();
    }

}
