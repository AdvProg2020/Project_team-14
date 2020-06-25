import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.Scanner;

import Controller.Server;
import GUI.Media.Audio;
import GUI.MenuHandler;
import Menus.MainMenu;
import Menus.Menu;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

public class main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Audio.playBackGroundMusic();
        MenuHandler.setServer(new Server());
        Parent root = FXMLLoader.load(getClass().getResource("GUI/ProductScene/ProductScene.fxml"));
        primaryStage.setScene(new Scene(root));
        primaryStage.show();

    }

    public static void main(String[] args) throws ParseException, IOException, ClassNotFoundException {
        launch(args);
        /*Server server = new Server();
        Menu.setServer(server);
        Menu.setScanner(new Scanner(System.in));
        MainMenu mainMenu = new MainMenu(null, "Main Menu");
        mainMenu.execute();*/
    }


}
