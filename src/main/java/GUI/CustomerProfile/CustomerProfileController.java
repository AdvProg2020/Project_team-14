package GUI.CustomerProfile;

import GUI.Media.Audio;
import GUI.MenuHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class CustomerProfileController {
    public Pane pane;
    public ImageView profileImage;

    @FXML
    public void initialize() throws IOException {
        MenuHandler.setPane(pane);
        pane.getChildren().clear();
        pane.getChildren().add(FXMLLoader.load(getClass().getResource("/GUI/ProfileLayout/PersonalInfoLayout.fxml")));
        setProfileImage();
    }

    private void setProfileImage() throws FileNotFoundException {
        if (!MenuHandler.getUserAvatar().equals("no image found")) {
            String path = "src/main/java/GUI/Register/resources/";
            String avatar = MenuHandler.getUserAvatar() + ".png";
            FileInputStream imageStream = new FileInputStream(path + avatar);
            Image image = new Image(imageStream);
            profileImage.setImage(image);
        }
    }

    public void managePersonalInfo(MouseEvent mouseEvent) throws IOException {
        Audio.playClick6();
        pane.getChildren().clear();
        pane.getChildren().add(FXMLLoader.load(getClass().getResource("/GUI/ProfileLayout/PersonalInfoLayout.fxml")));
    }

    public void manageOffCode(MouseEvent mouseEvent) throws IOException {
        Audio.playClick6();
        pane.getChildren().clear();
        pane.getChildren().add(FXMLLoader.load(getClass().getResource("/GUI/BossProfile/ManageOffCodes/ManageOffCodeLayout.fxml")));
    }

    public void logOut(MouseEvent mouseEvent) throws IOException {
        Audio.playClick6();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are You Sure You Want To Logout?", ButtonType.YES, ButtonType.NO);
        alert.showAndWait();
        if (alert.getResult().equals(ButtonType.YES)) {
            MenuHandler.getConnector().clientToServer("logout+" + MenuHandler.getUsername());
            MenuHandler.setUsername(null);
            MenuHandler.setUserType(null);
            MenuHandler.setIsUserLogin(false);
            Parent root = FXMLLoader.load(getClass().getResource("/GUI/ProductScene/ProductScene.fxml"));
            Stage stage = (Stage) ((Button) mouseEvent.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
        }
    }



    public void logging(ActionEvent actionEvent) throws IOException {
        Audio.playClick3();
        pane.getChildren().clear();
        pane.getChildren().add(FXMLLoader.load(getClass().getResource("/GUI/Log/Log.fxml")));

    }

    public void Banking(ActionEvent actionEvent) throws IOException {
        Audio.playClick2();
        Parent root = FXMLLoader.load(getClass().getResource("/GUI/Bank/LogOrRegister.fxml"));
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
    }

}
