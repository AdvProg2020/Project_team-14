import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.text.ParseException;
import java.util.Scanner;

import Controller.DataBase.DataBase;
import Controller.DataBase.EndOfProgramme;
import Controller.DataBase.startOfProgramme;
import Controller.Server;
import Menus.MainMenu;
import Menus.Menu;
import Model.Product.Point;

import static Model.Storage.allPoints;

public class main {

    public static void main(String[] args) throws ParseException, IOException, ClassNotFoundException {

        /*startOfProgramme s=new startOfProgramme();
        s.startProgramme();
        System.out.println(allPoints.size());
        System.out.println(DataBase.listAllFileNamesInDirectory("src\\main\\resources\\DataBase\\Points"));
        System.out.println(allPoints.get(0).getPointID());*/
        Server server = new Server();
        Menu.setServer(server);
        Menu.setScanner(new Scanner(System.in));
        MainMenu mainMenu = new MainMenu(null, "Main Menu");
        mainMenu.execute();
    }


}
