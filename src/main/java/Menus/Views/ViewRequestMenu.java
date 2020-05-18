package Menus.Views;

import Controller.Server;
import Menus.LoginOrRegisterMenu;
import Menus.Menu;

import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;

public class ViewRequestMenu extends Menu {
    private int whereItHasBeenCalled;
    private String requestID;
    private String objectID;
    private String productID;
    private String accountUsername;

    public void requestSubMenus() throws ParseException, IOException {
        HashMap<Integer, Menu> subMenus = new HashMap<Integer, Menu>();
        if (whereItHasBeenCalled >= 2) {
            server.clientToServer("what is request object ID+" + this.requestID);
            this.objectID = server.serverToClient();
        }
        if (whereItHasBeenCalled == 4) {
            server.clientToServer("what is comment product ID+" + this.objectID);
            this.productID = server.serverToClient();
        }
        server.clientToServer("what is request username+" + this.requestID);
        setAccountUsername(server.serverToClient());
        server.clientToServer("is request state checking+" + this.requestID);
        if (server.serverToClient().equalsIgnoreCase("yes")) {
            if (!this.getAccountUsername().equals("deleted account")) {
                subMenus.put(1, getAcceptRequestMenu());
                subMenus.put(2, getDeclineRequestMenu());
                subMenus.put(3, new ViewAccountMenu(this, "View Account Menu"));
                ((ViewAccountMenu) subMenus.get(3)).setAccount(this.getAccountUsername());
                if (whereItHasBeenCalled == 2) {
                    subMenus.put(4, new ViewProductMenu(this, "View Product Menu", objectID));
                    subMenus.put(5, new LoginOrRegisterMenu(this, "Login\\Register Menu"));
                } else if (whereItHasBeenCalled == 3) {
                    subMenus.put(4, new ViewSalesMenu(this, "View Sales Menu", objectID));
                    subMenus.put(5, new LoginOrRegisterMenu(this, "Login\\Register Menu"));
                } else if (whereItHasBeenCalled == 4) {
                    subMenus.put(4, new ViewCommentMenu(this, "View Comment Menu", objectID));
                    subMenus.put(5, new ViewProductMenu(this, "View Product Menu", productID));
                    subMenus.put(6, new LoginOrRegisterMenu(this, "Login\\Register Menu"));
                } else {
                    subMenus.put(4, new LoginOrRegisterMenu(this, "Login\\Register Menu"));
                }
            } else {
                if (whereItHasBeenCalled == 2) {
                    subMenus.put(1, new ViewProductMenu(this, "View Product Menu", objectID));
                    subMenus.put(2, getDeleteRequestMenu());
                    subMenus.put(3, new LoginOrRegisterMenu(this, "Login\\Register Menu"));
                } else if (whereItHasBeenCalled == 3) {
                    subMenus.put(1, new ViewSalesMenu(this, "View Sales Menu", objectID));
                    subMenus.put(2, getDeleteRequestMenu());
                    subMenus.put(3, new LoginOrRegisterMenu(this, "Login\\Register Menu"));
                } else if (whereItHasBeenCalled == 4) {
                    subMenus.put(1, new ViewCommentMenu(this, "View Comment Menu", objectID));
                    subMenus.put(2, new ViewProductMenu(this, "View Product Menu", productID));
                    subMenus.put(3, getDeleteRequestMenu());
                    subMenus.put(4, new LoginOrRegisterMenu(this, "Login\\Register Menu"));
                } else {
                    subMenus.put(1, getDeleteRequestMenu());
                    subMenus.put(2, new LoginOrRegisterMenu(this, "Login\\Register Menu"));
                }
            }
        } else {
            if (!this.getAccountUsername().equals("deleted account")) {
                subMenus.put(1, new ViewAccountMenu(this, "View Account Menu"));
                ((ViewAccountMenu) subMenus.get(1)).setAccount(this.getAccountUsername());
                if (whereItHasBeenCalled == 2) {
                    subMenus.put(2, new ViewProductMenu(this, "View Product Menu", objectID));
                    subMenus.put(3, getDeleteRequestMenu());
                    subMenus.put(4, new LoginOrRegisterMenu(this, "Login\\Register Menu"));
                } else if (whereItHasBeenCalled == 3) {
                    subMenus.put(2, new ViewSalesMenu(this, "View Sales Menu", objectID));
                    subMenus.put(3, getDeleteRequestMenu());
                    subMenus.put(4, new LoginOrRegisterMenu(this, "Login\\Register Menu"));
                } else if (whereItHasBeenCalled == 4) {
                    subMenus.put(2, new ViewCommentMenu(this, "View Comment Menu", objectID));
                    subMenus.put(3, new ViewProductMenu(this, "View Product Menu", productID));
                    subMenus.put(4, getDeleteRequestMenu());
                    subMenus.put(5, new LoginOrRegisterMenu(this, "Login\\Register Menu"));
                } else {
                    subMenus.put(2, getDeleteRequestMenu());
                    subMenus.put(3, new LoginOrRegisterMenu(this, "Login\\Register Menu"));
                }
            } else {
                if (whereItHasBeenCalled == 2) {
                    subMenus.put(1, new ViewProductMenu(this, "View Product Menu", objectID));
                    subMenus.put(2, getDeleteRequestMenu());
                    subMenus.put(3, new LoginOrRegisterMenu(this, "Login\\Register Menu"));
                } else if (whereItHasBeenCalled == 3) {
                    subMenus.put(1, new ViewSalesMenu(this, "View Sales Menu", objectID));
                    subMenus.put(2, getDeleteRequestMenu());
                    subMenus.put(3, new LoginOrRegisterMenu(this, "Login\\Register Menu"));
                } else if (whereItHasBeenCalled == 4) {
                    subMenus.put(1, new ViewCommentMenu(this, "View Comment Menu", objectID));
                    subMenus.put(2, new ViewProductMenu(this, "View Product Menu", productID));
                    subMenus.put(3, getDeleteRequestMenu());
                    subMenus.put(4, new LoginOrRegisterMenu(this, "Login\\Register Menu"));
                } else {
                    subMenus.put(1, getDeleteRequestMenu());
                    subMenus.put(2, new LoginOrRegisterMenu(this, "Login\\Register Menu"));
                }
            }
        }
        this.setSubMenus(subMenus);
    }

    public ViewRequestMenu(Menu fatherMenu, String menuName, String requestType, String requestID) throws ParseException, IOException {
        super(fatherMenu, menuName);
        this.requestID = requestID;
        this.logoutType = false;
        if (requestType.equalsIgnoreCase("Register_Salesman")) {
            this.whereItHasBeenCalled = 1;
        } else if (requestType.equalsIgnoreCase("ADD_NEW_PRODUCT")) {
            this.whereItHasBeenCalled = 2;
        } else if (requestType.equalsIgnoreCase("DELETE_NEW PRODUCT")) {
            this.whereItHasBeenCalled = 2;
        } else if (requestType.equalsIgnoreCase("CHANGE_PRODUCT")) {
            this.whereItHasBeenCalled = 2;
        } else if (requestType.equalsIgnoreCase("ADD_NEW_SALE")) {
            this.whereItHasBeenCalled = 3;
        } else if (requestType.equalsIgnoreCase("CHANGE_SALE")) {
            this.whereItHasBeenCalled = 3;
        } else if (requestType.equalsIgnoreCase("DELETE_SALE")) {
            this.whereItHasBeenCalled = 3;
        } else if (requestType.equalsIgnoreCase("ADD_TO_PRODUCT")) {
            this.whereItHasBeenCalled = 2;
        } else if (requestType.equalsIgnoreCase("COMMENT_CONFIRMATION")) {
            this.whereItHasBeenCalled = 4;
        }
        requestSubMenus();
    }

    private Menu getAcceptRequestMenu() {
        return new Menu(this, "Accept Request Menu") {
            @Override
            public void execute() throws ParseException, IOException {
                System.out.println("Are you sure you want to accept this request");
                String input = scanner.nextLine();
                if (input.equalsIgnoreCase("yes")) {
                    server.clientToServer("accept request+" + Menu.username + "+" +
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
            public void execute() throws ParseException, IOException {
                System.out.println("Are you sure you want to decline this request");
                String input = scanner.nextLine();
                if (input.equalsIgnoreCase("yes")) {
                    server.clientToServer("decline request+" + Menu.username + "+" +
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
            public void execute() throws ParseException, IOException {
                System.out.println("are you sure you want to delete this request");
                String input = scanner.nextLine();
                if (input.equalsIgnoreCase("yes")) {
                    server.clientToServer("delete request+" + Menu.username + "+" + ((ViewRequestMenu)
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

    private void getRequestInfo() throws ParseException, IOException {
        server.clientToServer("view request+" + Menu.username + "+" + requestID);
        String serverAnswer = server.serverToClient();
        System.out.println(serverAnswer);
    }

    @Override
    protected void show() throws ParseException, IOException {
        super.show();
        getRequestInfo();
    }
}
