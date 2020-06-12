import java.io.IOException;
import java.text.ParseException;
import java.util.Scanner;

import Controller.Server;
import Menus.MainMenu;
import Menus.Menu;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class main extends Application {


    public static void main(String[] args) throws ParseException, IOException, ClassNotFoundException {
        Server server = new Server();
        Menu.setServer(server);
        //Menu.setScanner(new Scanner(System.in));
        //MainMenu mainMenu = new MainMenu(null, "Main Menu");
        launch(args);
        //mainMenu.execute();
    }


    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("GUI/RegisterBoss/RegisterBoss.fxml"));
        stage.setScene(new Scene(root, 1920, 1080));
        stage.setTitle("RegisterForBossMenu");
        stage.setResizable(false);
        //stage.getIcons().add(new Image(getClass().getResourceAsStream("Pictures/logo.jpg")));
        stage.initStyle(StageStyle.UNDECORATED);
        stage.show();
    }
}
