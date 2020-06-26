package GUI.ProfileLayout;

import GUI.Media.Audio;
import GUI.MenuHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.ParseException;

public class ProfileLayoutController {
    public Pane pane;
    public ImageView imageView;
    public ImageView profileImage;
    Image image;

    public void initialize() throws IOException, ParseException {
        pane.getChildren().add(FXMLLoader.load(getClass().getResource("/GUI/ProfileLayout/PersonalInfoLayout.fxml")));
        image = profileImage.getImage();
    }

    public void setProfileImage() throws ParseException, IOException {
        if (MenuHandler.getServer().serverToClient() == null) {
            return;
        }
        MenuHandler.getServer().clientToServer("get person image+" + MenuHandler.getUsername());
        if (!MenuHandler.getServer().serverToClient().startsWith("no image found")) {
            Image image = new Image(new FileInputStream(MenuHandler.getServer().serverToClient().substring(6)));
            imageView.setImage(image);
        } else {
            File file = new File("src/main/java/GUI/ProductScene/resources/user.png");
            Image image = new Image(file.toURI().toString());
            imageView.setImage(image);

        }
    }

    public void managePersonalInfo(ActionEvent actionEvent) throws IOException {
        Audio.playClick5();
        pane.getChildren().remove(pane.getChildren().get(0));
        pane.getChildren().add(FXMLLoader.load(getClass().getResource("/GUI/ProfileLayout/PersonalInfoLayout.fxml")));
    }

    public void manageRequests(ActionEvent actionEvent) throws IOException {
        Audio.playClick4();
        pane.getChildren().remove(pane.getChildren().get(0));
        pane.getChildren().add(FXMLLoader.load(getClass().getResource("/GUI/BossProfile/ManageRequests/ManageRequestLayout.fxml")));
    }

    public void logout(ActionEvent actionEvent) throws IOException {
        Audio.playClick3();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are You Sure You Want To Logout?", ButtonType.YES, ButtonType.NO);
        alert.showAndWait();
        if (alert.getResult().equals(ButtonType.YES)) {
            MenuHandler.setUsername(null);
            MenuHandler.setUserType(null);
            MenuHandler.setIsUserLogin(false);
            Parent root = FXMLLoader.load(getClass().getResource("/GUI/ProductScene/ProductScene.fxml"));
            Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
        }
    }

    public void manageAccounts(ActionEvent actionEvent) throws IOException {
        Audio.playClick2();
        pane.getChildren().remove(pane.getChildren().get(0));
        pane.getChildren().add(FXMLLoader.load(getClass().getResource("/GUI/BossProfile/ManagerUsersMenu/ManageUsersLayOut.fxml")));
    }

    public void manageOffCodes(MouseEvent mouseEvent) throws IOException {
        Audio.playClick1();
        pane.getChildren().remove(pane.getChildren().get(0));
        pane.getChildren().add(FXMLLoader.load(getClass().getResource("/GUI/BossProfile/ManageOffCodes/ManageOffCodeLayout.fxml")));
    }

    public void categoryMenu(ActionEvent actionEvent) throws IOException {
        Audio.playClick7();
        pane.getChildren().remove(pane.getChildren().get(0));
        pane.getChildren().add(FXMLLoader.load(getClass().getResource("/GUI/CategoryMenu/CategoryMenu.fxml")));
    }

    public void categoryClicked(MouseEvent mouseEvent) throws IOException {
        Audio.playClick7();
        pane.getChildren().remove(pane.getChildren().get(0));
        pane.getChildren().add(FXMLLoader.load(getClass().getResource("/GUI/CategoryMenu/CategoryMenu.fxml")));
    }
}
