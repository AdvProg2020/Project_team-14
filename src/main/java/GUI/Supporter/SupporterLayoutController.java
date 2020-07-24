package GUI.Supporter;

import GUI.Media.Audio;
import GUI.MenuHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class SupporterLayoutController {
    public Pane pane;

    @FXML
    public void initialize() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/GUI/Supporter/SupporterChatLayout.fxml"));
        root.setLayoutX(100);
        root.setLayoutY(100);
        pane.getChildren().add(root);
    }

    public void logOut(MouseEvent mouseEvent) throws IOException {
        Audio.playClick1();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are You Sure You Want To Logout?", ButtonType.YES, ButtonType.NO);
        alert.showAndWait();
        if (alert.getResult().equals(ButtonType.YES)) {
            MenuHandler.getConnector().clientToServer("logout supporter+" + MenuHandler.getUsername());
            MenuHandler.setToken("no token");
            MenuHandler.setUsername(null);
            MenuHandler.setUserType(null);
            MenuHandler.setIsUserLogin(false);
            Parent root = FXMLLoader.load(getClass().getResource("/GUI/ProductScene/ProductScene.fxml"));
            MenuHandler.getStage().setScene(new Scene(root));
        }
    }

}
