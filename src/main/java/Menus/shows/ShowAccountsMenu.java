package Menus.shows;

import Menus.Filters.AccountsFilterMenu;
import Menus.LoginOrRegisterMenu;
import Menus.Menu;
import Menus.Sorts.AccountsSortMenu;
import Menus.Views.ViewAccountMenu;

import java.util.HashMap;

public class ShowAccountsMenu extends ShowsMenu {
    //private String serverAnswer;

    public ShowAccountsMenu(Menu fatherMenu, String menuName) {
        super(fatherMenu, menuName);
        this.type = "accounts";
        HashMap<Integer, Menu> subMenus = new HashMap<Integer, Menu>();
        filter = new AccountsFilterMenu(this, "Account Filter Menu");
        sort = new AccountsSortMenu(this, "Account Sorts Menu");
        subMenus.put(1, filter);
        subMenus.put(2, sort);
        subMenus.put(3, getSelectMenu());
        subMenus.put(4, new LoginOrRegisterMenu(this, "Login\\Register Menu"));
        this.setSubMenus(subMenus);
    }

    private Menu getSelectMenu() {
        return new Menu(this, "Select Menu") {
            private boolean hasBeenCalled = true;

            private boolean isThereAccountWithUsernameInList(String list, String username) {
                int wordCount = Menu.wordCount(list);
                String[] lists = list.split("\\s");
                for (int i = 0; i < wordCount; i++) {
                    if (lists[i].equals(username) && lists[i - 1].equals("Username:")) {
                        return true;
                    }
                }
                return false;
            }

            @Override
            public void execute() {
                if (hasBeenCalled) {
                    hasBeenCalled = false;
                } else {
                    hasBeenCalled = true;
                    fatherMenu.execute();
                }
                System.out.println(menuName);
                System.out.println("if you input back we will go back");
                System.out.println("select one of accounts above by inserting username");
                String username = scanner.nextLine();
                if (username.equalsIgnoreCase("back")) {
                    fatherMenu.execute();
                }
                String accounts = ((ShowAccountsMenu) fatherMenu).getServerAnswer();
                if (isThereAccountWithUsernameInList(accounts, username)) {
                    ViewAccountMenu menu = new ViewAccountMenu(this, "View Account Menu");
                    menu.setAccount(username);
                    menu.execute();
                } else {
                    System.out.println("this account is not on the list");
                    this.execute();
                }
            }
        };
    }

    /*public String getServerAnswer() {
        return this.serverAnswer;
    }*/

    /*private void getInfo() {
        server.clientToServer("show accounts " + username + filter.getFilters() + " " + sort.getSort());
        this.serverAnswer = server.serverToClient();
        System.out.println(serverAnswer);
    }*/

    /*@Override
    protected void show() {
        super.show();
        getInfo();
    }*/
}
