package GUI.ProfileLayout;

import GUI.Media.Audio;
import GUI.MenuHandler;
import javafx.embed.swing.SwingFXUtils;
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

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.ParseException;

public class ProfileLayoutController {
    public Pane pane;
    public ImageView profileImage;


    public void initialize() throws IOException, ParseException {
        MenuHandler.setBackProduct("GUI/ProfileLayout/ProfileLayout.fxml");
        pane.getChildren().add(FXMLLoader.load(getClass().getResource("/GUI/ProfileLayout/PersonalInfoLayout.fxml")));
    }

    public void setProfileImage() throws ParseException, IOException {
        if (!MenuHandler.getUserAvatar().equals("no image found")) {
            String path = "src/main/java/GUI/Register/resources/";
            String avatar = MenuHandler.getUserAvatar() + ".png";
            System.out.println(avatar);
            FileInputStream imageStream = new FileInputStream(path + avatar);
            Image image = new Image(imageStream);
            profileImage.setImage(image);
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
            MenuHandler.getConnector().clientToServer("logout+" + MenuHandler.getUsername());
            MenuHandler.setToken("no token");
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
        if (pane.getChildren().size() == 0) return;
        pane.getChildren().remove(pane.getChildren().get(0));
        pane.getChildren().add(FXMLLoader.load(getClass().getResource("/GUI/BossProfile/ManagerUsersMenu/ManageUsersLayOut.fxml")));
    }

    public void manageOffCodes(MouseEvent mouseEvent) throws IOException {
        Audio.playClick1();
        if (pane.getChildren().size() == 0) return;
        pane.getChildren().remove(pane.getChildren().get(0));
        pane.getChildren().add(FXMLLoader.load(getClass().getResource("/GUI/BossProfile/ManageOffCodes/ManageOffCodeLayout.fxml")));
    }

    public void categoryMenu(ActionEvent actionEvent) throws IOException {
        Audio.playClick7();
        if (pane.getChildren().size() == 0) return;
        pane.getChildren().remove(pane.getChildren().get(0));
        pane.getChildren().add(FXMLLoader.load(getClass().getResource("/GUI/CategoryMenu/CategoryMenu.fxml")));
    }

    public void categoryClicked(MouseEvent mouseEvent) throws IOException {
        Audio.playClick7();
        if (pane.getChildren().size() == 0) return;
        pane.getChildren().remove(pane.getChildren().get(0));
        pane.getChildren().add(FXMLLoader.load(getClass().getResource("/GUI/CategoryMenu/CategoryMenu.fxml")));
    }

    public void productShow(ActionEvent actionEvent) throws IOException {
        Audio.playClick3();
        if (pane.getChildren().size() == 0) return;
        pane.getChildren().remove(pane.getChildren().get(0));
        pane.getChildren().add(FXMLLoader.load(getClass().getResource("/GUI/SalesmanProfile/ManageProduct/ManageProductsLayout.fxml")));
    }

    public void home(MouseEvent mouseEvent) throws IOException {
        Audio.playClick5();
        MenuHandler.getStage().setScene(new Scene(FXMLLoader.load(getClass().getResource("/GUI/ProductScene/ProductScene.fxml"))));
        pane.getChildren().add(FXMLLoader.load(getClass().getResource("/GUI/SalesmanProfile/ManageProduct/ManageProductsLayout.fxml")));
    }

    public void manageSales(ActionEvent actionEvent) throws IOException {
        Audio.playClick1();
        pane.getChildren().remove(pane.getChildren().get(0));
        pane.getChildren().add(FXMLLoader.load(getClass().getResource("/GUI/SalesmanProfile/ManageSale/ManageSalesLayout.fxml")));
    }

    public void manageSupporter(ActionEvent actionEvent) throws IOException {
        Audio.playClick1();
        Parent root = FXMLLoader.load(getClass().getResource("/GUI/Supporter/Supporter.fxml"));
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    public void Bank(ActionEvent actionEvent) throws IOException {
        Audio.playClick7();
        Parent root = FXMLLoader.load(getClass().getResource("/GUI/Bank/LogOrRegister.fxml"));
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    public void Financial(ActionEvent actionEvent) throws IOException {
        pane.getChildren().remove(pane.getChildren().get(0));
        pane.getChildren().add(FXMLLoader.load(getClass().getResource("/GUI/ProfileLayout/Financial/Financial.fxml")));
    }

    public void goHome(MouseEvent mouseEvent) throws IOException {
        Audio.playClick5();
        MenuHandler.goHome();
    }

    public void exit(MouseEvent mouseEvent) throws InterruptedException {
        MenuHandler.exit();
    }
}
