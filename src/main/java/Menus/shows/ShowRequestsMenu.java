package Menus.shows;

import Menus.Filters.RequestsFilterMenu;
import Menus.LoginOrRegisterMenu;
import Menus.Menu;
import Menus.Views.ViewAccountMenu;
import Menus.Views.ViewRequestMenu;

import javax.swing.text.View;
import java.util.HashMap;

public class ShowRequestsMenu extends ShowsMenu {
    //private String serverAnswer;
    private boolean canChangeUsernameFilter = true;

    public ShowRequestsMenu(Menu fatherMenu, String menuName) {
        super(fatherMenu, menuName);
        HashMap<Integer, Menu> subMenus = new HashMap<Integer, Menu>();
        if (fatherMenu instanceof ViewAccountMenu) {
            canChangeUsernameFilter = false;
        }
        filter = new RequestsFilterMenu(this, "Request Filter Menu");
        if (fatherMenu instanceof ViewAccountMenu) {
            ((RequestsFilterMenu) filter).setUsernameFilter(((ViewAccountMenu) fatherMenu).getUsername());
        }
        subMenus.put(1, filter);
        subMenus.put(2, getSelectMenu());
        subMenus.put(3, new LoginOrRegisterMenu(this, "Login\\Register Menu"));
        this.setSubMenus(subMenus);
    }


    private Menu getSelectMenu() {
        return new Menu(this, "Select Menu") {
            private boolean hasBeenCalled = true;

            private String isThereRequestWithIDInList(String list, String requestID) {
                int wordCount = Menu.wordCount(list);
                String[] lists = list.split("\\s");
                for (int i = 0; i < wordCount; i++) {
                    if (lists[i].equals(requestID) && lists[i - 1].equals("ID:")) {
                        return lists[i - 3];
                    }
                }
                return null;
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
                System.out.println("select one of requests above by inserting requestID");
                String requestID = scanner.nextLine();
                if (username.equalsIgnoreCase("back")) {
                    fatherMenu.execute();
                }
                String requests = ((ShowRequestsMenu) fatherMenu).getServerAnswer();
                String requestType = isThereRequestWithIDInList(requests, requestID);
                if (requestType != null) {
                    new ViewRequestMenu(this, "View Request Menu", requestType, requestID).execute();
                } else {
                    System.out.println("this request is not on the list");
                    this.execute();
                }
            }
        };
    }

    public boolean isCanChangeUsernameFilter() {
        return canChangeUsernameFilter;
    }

    /*public String getServerAnswer() {
        return this.serverAnswer;
    }*/

    /*private void getInfo() {
        server.clientToServer("show requests " + username + filter.getFilters());
        this.serverAnswer = server.serverToClient();
        System.out.println(serverAnswer);
    }*/

    /*@Override
    protected void show() {
        super.show();
        getInfo();
    }*/
}
