import java.io.IOException;
import java.text.ParseException;
import java.util.Scanner;

import Controller.Server;
import Menus.MainMenu;
import Menus.Menu;

public class main {

    public static void main(String[] args) throws ParseException, IOException, ClassNotFoundException {

        Server server = new Server();
        Menu.setServer(server);
        Menu.setScanner(new Scanner(System.in));
        MainMenu mainMenu = new MainMenu(null, "Main Menu");
        mainMenu.execute();
    }


}
