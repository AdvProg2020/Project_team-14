package Menus.Manages;

import Menus.LoginOrRegisterMenu;
import Menus.Menu;
import Menus.Views.ViewAccountMenu;
import Menus.shows.ShowAccountsMenu;

import java.text.ParseException;
import java.util.HashMap;

public class ManageAccountsMenu extends Menu {
    public ManageAccountsMenu(Menu fatherMenu, String menuName) {
        super(fatherMenu, menuName);
        this.logoutType = false;
        HashMap<Integer, Menu> subMenus = new HashMap<Integer, Menu>();
        subMenus.put(1, new ShowAccountsMenu(this, "Show Accounts Menu"));
        subMenus.put(2, getSearchAccountMenu());
        subMenus.put(3, getCreateNewBossMenu());
        subMenus.put(4, new LoginOrRegisterMenu(this, "Login\\Register Menu"));
        this.setSubMenus(subMenus);
    }

    private Menu getCreateNewBossMenu() {
        return new Menu(this, "Create Boss Menu") {

            @Override
            public void execute() throws ParseException {
                System.out.println(this.menuName);
                System.out.println("if you input back we will go back");
                String message;
                String firstName, lastName;
                String username, password;
                String Email, telephone;
                String company;
                System.out.println("please enter the boss first and last name in separate lines:");
                firstName = scanner.nextLine();
                checkBack(firstName);
                lastName = scanner.nextLine();
                checkBack(lastName);
                System.out.println("please enter the boss username and password in separate lines:");
                username = scanner.nextLine();
                checkBack(username);
                password = scanner.nextLine();
                checkBack(password);
                System.out.println("please enter the boss email and telephone in separate lines:");
                Email = scanner.nextLine();
                checkBack(Email);
                telephone = scanner.nextLine();
                checkBack(telephone);
                message = "make new boss+" + firstName + "+" + lastName + "+" + username + "+" + password + "+"
                        + Email + "+" + telephone + "+" + Menu.username;
                server.clientToServer(message);
                String serverAnswer;
                serverAnswer = server.serverToClient();
                System.out.println(serverAnswer);
                if (serverAnswer.equals("register successful")) {
                    fatherMenu.execute();
                } else {
                    this.execute();
                }
            }
        };
    }

    private Menu getSearchAccountMenu() {
        return new Menu(this, "Search Account Menu") {
            boolean hasBeenCalled = true;

            @Override
            public void execute() throws ParseException {
                if (hasBeenCalled) {
                    hasBeenCalled = false;
                } else {
                    hasBeenCalled = true;
                    fatherMenu.execute();
                }
                System.out.println(menuName);
                System.out.println("if you input back we will go back");
                System.out.println("please input the username");
                String username = scanner.nextLine();
                server.clientToServer("search account+" + Menu.username + "+" + username);
                String serverAnswer = server.serverToClient();
                System.out.println(serverAnswer);
                if (serverAnswer.equalsIgnoreCase("search completed")) {
                    ViewAccountMenu menu = new ViewAccountMenu(this, "View Account Menu");
                    menu.setAccount(username);
                    menu.execute();
                } else {
                    this.execute();
                }
            }
        };
    }

    @Override
    public void execute() throws ParseException {
        ((ShowAccountsMenu) this.subMenus.get(1)).resetFilters();
        ((ShowAccountsMenu) this.subMenus.get(1)).resetSorts();
        super.execute();
    }
}
