package Menus.Manages;

import Menus.LoginOrRegisterMenu;
import Menus.Menu;
import Menus.Views.ViewSalesMenu;
import Menus.shows.ShowSalesMenu;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;

public class ManageSalesMenu extends Menu {
    public ManageSalesMenu(Menu fatherMenu, String menuName) {
        super(fatherMenu, menuName);
        HashMap<Integer, Menu> subMenus = new HashMap<>();
        subMenus.put(1, new ShowSalesMenu(this, "Show Sales Menu"));
        subMenus.put(2, getSearchSale());
        subMenus.put(3, getCreateSale());
        subMenus.put(4, new LoginOrRegisterMenu(this, "Login\\Register Menu"));
        this.setSubMenus(subMenus);
    }

    private Menu getSearchSale() {
        return new Menu(this, "Search Sale Menu") {
            @Override
            public void execute() throws ParseException, IOException {
                System.out.println(menuName);
                System.out.println("if you input back we will go back");

                //get info from user
                System.out.println("Enter salesID to see details");
                String saleID = scanner.nextLine();
                checkBack(saleID);
                server.clientToServer("search sale" + "+" + Menu.username + "+" + saleID);
                System.out.println(server.serverToClient());
                if (server.serverToClient().equalsIgnoreCase("search completed")) {
                    new ViewSalesMenu(this, "View Sale Menu", saleID).execute();
                } else {
                    fatherMenu.execute();
                }
            }
        };
    }

    private Menu getCreateSale() {
        return new Menu(this, "Create New Sale Menu") {

            @Override
            public void execute() throws ParseException, IOException {
                System.out.println(menuName);
                System.out.println("if you input back we will go back");

                //get info from user
                System.out.println("When do you want this Sale Start? (insert date in this format: mm-dd-yyyy hh-mm-ss)");
                String start = scanner.nextLine();
                checkBack(start);
                System.out.println("When do you want this Sale End? (insert date in this format: mm-dd-yyyy hh-mm-ss)");
                String end = scanner.nextLine();
                checkBack(end);
                System.out.println("How much do you want to off? (insert a number from 1 to 100)");
                String percentage = scanner.nextLine();
                checkBack(percentage);
                ArrayList<String> productInSale = getProductIDAndAddThemToSale();

                //send info to server
                String message = "create new sale" + "+" + start + "+" + end + "+" + percentage + "+" + "Products:" + productInSale.toString();
                server.clientToServer(message);
                System.out.println(server.serverToClient());
                if (server.serverToClient().equalsIgnoreCase("creation of sale successful")) {
                    fatherMenu.execute();
                } else {
                    this.execute();
                }
            }

            private ArrayList<String> getProductIDAndAddThemToSale() throws ParseException, IOException {
                System.out.println("Here use this format to add product to Sale: (-add [ProductID]\n-remove [ProductID]\n-DONE)");
                ArrayList<String> ans = new ArrayList<>();
                String command;
                while (!(command = scanner.nextLine()).equalsIgnoreCase("done")) {
                    if (command.split("\\s").length != 2) {
                        System.err.println("wrong input");
                    } else if (command.split("\\s")[0].equalsIgnoreCase("add")) {
                        addProductToSale(command.split("\\s")[1], ans);
                    } else if (command.split("\\s")[1].equalsIgnoreCase("remove")) {
                        removeProductFromSale(command.split("\\s")[1], ans);
                    } else {
                        System.err.println("wrong input");
                    }
                }
                return ans;
            }

            private void addProductToSale(String productID, ArrayList<String> array) throws ParseException, IOException {
                server.clientToServer("can add product to sale" + "+" + Menu.username + "+" + productID);
                if (server.serverToClient().equalsIgnoreCase("yes")) {
                    array.add(productID);
                } else {
                    System.err.println(server.serverToClient());
                }
            }

            private void removeProductFromSale(String productID, ArrayList<String> array) {
                if (array.contains(productID)) {
                    array.remove(productID);
                } else {
                    System.err.println("you haven't add this to Sale before");
                }
            }
        };
    }
}
