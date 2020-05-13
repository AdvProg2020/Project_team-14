package Menus.Views;

import Controller.Server;
import Menus.LoginOrRegisterMenu;
import Menus.Menu;

import java.util.HashMap;

public class ViewRequestMenu extends Menu {
    private int whereItHasBeenCalled;
    private String requestID;
    private String accountUsername;

    public void requestSubMenus() {
        HashMap<Integer, Menu> subMenus = new HashMap<Integer, Menu>();
        server.clientToServer("what is request username " + this.requestID);
        setAccountUsername(server.serverToClient());
        server.clientToServer("is request state checking " + this.requestID);
        if (server.serverToClient().equalsIgnoreCase("yes")) {
            if (!this.getAccountUsername().equals("deleted account")) {
                subMenus.put(1, getAcceptRequestMenu());
                subMenus.put(2, getDeclineRequestMenu());
                subMenus.put(3, new ViewAccountMenu(this, "View Account Menu"));
                ((ViewAccountMenu) subMenus.get(3)).setAccount(this.getAccountUsername());
                subMenus.put(4, new LoginOrRegisterMenu(this, "Login\\Register Menu"));
            } else {
                subMenus.put(1, getDeleteRequestMenu());
                subMenus.put(2, new LoginOrRegisterMenu(this, "Login\\Register Menu"));
            }
        } else {
            if (!this.getAccountUsername().equals("deleted account")) {
                subMenus.put(1, new ViewAccountMenu(this, "View Account Menu"));
                ((ViewAccountMenu) subMenus.get(1)).setAccount(this.getAccountUsername());
                subMenus.put(2, getDeleteRequestMenu());
                subMenus.put(3, new LoginOrRegisterMenu(this, "Login\\Register Menu"));
            } else {
                subMenus.put(1, getDeleteRequestMenu());
                subMenus.put(2, new LoginOrRegisterMenu(this, "Login\\Register Menu"));
            }
        }
        this.setSubMenus(subMenus);
    }

    public ViewRequestMenu(Menu fatherMenu, String menuName, String requestType, String requestID) {
        super(fatherMenu, menuName);
        this.requestID = requestID;
        this.logoutType = false;
        HashMap<Integer, Menu> subMenus = new HashMap<Integer, Menu>();
        if (requestType.equalsIgnoreCase("Register_Salesman")) {
            this.whereItHasBeenCalled = 1;
        }
        requestSubMenus();
    }

    private Menu getAcceptRequestMenu() {
        return new Menu(this, "Accept Request Menu") {
            @Override
            public void execute() {
                System.out.println("Are you sure you want to accept this request");
                String input = scanner.nextLine();
                if (input.equalsIgnoreCase("yes")) {
                    server.clientToServer("accept request " + Menu.username + " " +
                            ((ViewRequestMenu) fatherMenu).getRequestID());
                    String serverAnswer = server.serverToClient();
                    System.out.println(serverAnswer);
                    if (serverAnswer.equalsIgnoreCase("accepted successfully")) {
                        ((ViewRequestMenu) fatherMenu).requestSubMenus();
                        fatherMenu.execute();
                    } else {
                        //no idea right now
                    }
                }
            }
        };
    }

    private Menu getDeclineRequestMenu() {
        return new Menu(this, "Decline Request Menu") {
            @Override
            public void execute() {
                System.out.println("Are you sure you want to decline this request");
                String input = scanner.nextLine();
                if (input.equalsIgnoreCase("yes")) {
                    server.clientToServer("decline request " + Menu.username + " " +
                            ((ViewRequestMenu) fatherMenu).getRequestID());
                    String serverAnswer = server.serverToClient();
                    System.out.println(serverAnswer);
                    if (serverAnswer.equalsIgnoreCase("declined successfully")) {
                        ((ViewRequestMenu) fatherMenu).requestSubMenus();
                        fatherMenu.execute();
                    } else {
                        //no idea right now
                    }
                }
            }
        };
    }

    private Menu getDeleteRequestMenu() {
        return new Menu(this, "Delete Request Menu") {
            @Override
            public void execute() {
                System.out.println("are you sure you want to delete this request");
                String input = scanner.nextLine();
                if (input.equalsIgnoreCase("yes")) {
                    server.clientToServer("delete request " + Menu.username + " " + ((ViewRequestMenu)
                            this.getFatherMenu()).getRequestID());
                    String serverAnswer = server.serverToClient();
                    System.out.println(serverAnswer);
                    if (serverAnswer.equalsIgnoreCase("deleted successfully")) {
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

    public String getRequestID() {
        return requestID;
    }

    public String getAccountUsername() {
        return accountUsername;
    }

    public void setAccountUsername(String accountUsername) {
        this.accountUsername = accountUsername;
    }

    private void getRequestInfo() {
        server.clientToServer("view request " + Menu.username + " " + requestID);
        String serverAnswer = server.serverToClient();
        System.out.println(serverAnswer);
    }

    @Override
    protected void show() {
        super.show();
        getRequestInfo();
    }
}
