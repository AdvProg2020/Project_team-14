package GUI.ProductScene;

import GUI.MenuHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;

public class ProductSceneController {

    public MediaView film;
    protected ImageView accountMenuButton;

    public void openPopUp(MouseEvent mouseEvent) throws IOException {
        Popup popup = new Popup();
        Stage stage = (Stage) ((ImageView) mouseEvent.getSource()).getScene().getWindow();
        MenuHandler.setStage(stage);
        if (MenuHandler.isIsUserLogin()) {
            Parent registerLoginPopUp = FXMLLoader.load(getClass().getResource("/GUI/MainMenuPopups/ProfilePopup.fxml"));
            popup.getContent().addAll(registerLoginPopUp);
            ((Label) ((VBox) registerLoginPopUp).getChildren().get(1)).setText(MenuHandler.getUsername());
            ((Button) ((HBox) ((VBox) registerLoginPopUp).getChildren().get(2)).getChildren().get(0)).setOnAction(event -> {
                Parent root = null;
                try {
                    if (MenuHandler.getUserType().equalsIgnoreCase("boss")) {
                        root = FXMLLoader.load(getClass().getResource("/GUI/ProfileLayout/ProfileLayout.fxml"));
                    } else if (MenuHandler.getUserType().equalsIgnoreCase("salesman")) {
                        root = FXMLLoader.load(getClass().getResource("/GUI/SalesmanProfile/SalesmanProfileLayout.fxml"));
                    } else {

                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

                stage.setScene(new Scene(root));
                popup.getScene().getWindow().hide();
            });
            ((Button) ((HBox) ((VBox) registerLoginPopUp).getChildren().get(2)).getChildren().get(1)).setOnAction(event -> {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are You Sure You Want To Logout", ButtonType.YES, ButtonType.NO);
                popup.getScene().getWindow().hide();
                alert.showAndWait();
                if (alert.getResult().equals(ButtonType.YES)) {
                    try {
                        MenuHandler.getServer().clientToServer("logout+" + MenuHandler.getUsername());
                        String serverAnswer = MenuHandler.getServer().serverToClient();
                        if (serverAnswer.equalsIgnoreCase("logout successful")) {
                            Alert alert1 = new Alert(Alert.AlertType.INFORMATION, "Logout Successful", ButtonType.OK);
                            alert1.showAndWait();
                            MenuHandler.setIsUserLogin(false);
                            MenuHandler.setUsername(null);
                            MenuHandler.setUserType(null);
                        }
                    } catch (ParseException | IOException e) {
                        e.printStackTrace();
                    }
                }
                Parent root = null;
                try {
                    root = FXMLLoader.load(getClass().getResource("/GUI/ProductScene/ProductScene.fxml"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                stage.setScene(new Scene(root));
                MenuHandler.setLoginBackAddress("/GUI/ProductScene/ProductScene.fxml");
                popup.getScene().getWindow().hide();
            });
            popup.show(stage);
        } else {
            Parent registerLoginPopUp = FXMLLoader.load(getClass().getResource("/GUI/MainMenuPopups/LoginRegisterPopup.fxml"));
            popup.getContent().addAll(registerLoginPopUp);
            ((Button) ((HBox) ((VBox) registerLoginPopUp).getChildren().get(1)).getChildren().get(0)).setOnAction(event -> {
                Parent root = null;
                try {
                    root = FXMLLoader.load(getClass().getResource("/GUI/Register/Register.fxml"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                stage.setScene(new Scene(root));
                MenuHandler.setLoginBackAddress("/GUI/ProductScene/ProductScene.fxml");
                popup.getScene().getWindow().hide();
            });
            ((Button) ((HBox) ((VBox) registerLoginPopUp).getChildren().get(1)).getChildren().get(1)).setOnAction(event -> {
                Parent root = null;
                try {
                    root = FXMLLoader.load(getClass().getResource("/GUI/Login/Login.fxml"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                stage.setScene(new Scene(root));
                MenuHandler.setLoginBackAddress("/GUI/ProductScene/ProductScene.fxml");
                popup.getScene().getWindow().hide();
            });
            popup.show(stage);
        }
    }

    public void initialize() {
        String path = "src/main/java/GUI/ProductView/resources/mp4.mp4";
        Media media = new Media(new File(path).toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        film.setMediaPlayer(mediaPlayer);
        mediaPlayer.setAutoPlay(true);
        mediaPlayer.setOnEndOfMedia(() -> mediaPlayer.seek(Duration.ZERO));
    }
}
