package GUI.Login;

import GUI.MenuHandler;
import Menus.Menu;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.text.ParseException;

public class LoginController {

    public TextField username;
    public PasswordField password;

    public void Login(ActionEvent actionEvent) throws ParseException, IOException {
        Alert alert = new Alert(Alert.AlertType.ERROR, "", ButtonType.OK);
        if (username.getText().equals("")) {
            alert.setContentText("Username Field Must Not Be Empty");
            alert.showAndWait();
        } else if (password.getText().equals("")) {
            alert.setContentText("Password Field Must Not Be Empty");
            alert.showAndWait();
        } else {
            MenuHandler.getServer().clientToServer("login+" + username.getText() + "+" + password.getText());
            String serverAnswer = MenuHandler.getServer().serverToClient();
            if (serverAnswer.startsWith("login successful ")) {
                MenuHandler.setIsUserLogin(true);
                if (serverAnswer.contains("BOSS")) {
                    MenuHandler.setUserType("BOSS");
                } else if (serverAnswer.contains("CUSTOMER")) {
                    MenuHandler.setUserType("CUSTOMER");
                } else {
                    MenuHandler.setUserType("SALESMAN");
                }
                MenuHandler.setUsername(serverAnswer.split("\\s")[4]);
                alert.setAlertType(Alert.AlertType.INFORMATION);
                alert.setContentText("Login Successful" + "\n" + "Welcome");
                alert.showAndWait();
                Parent root = FXMLLoader.load(getClass().getResource(MenuHandler.getLoginBackAddress()));
                Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
                stage.setScene(new Scene(root));
            } else {
                alert.setContentText(serverAnswer);
                alert.showAndWait();
            }
        }
    }

    public void openRegisterMenu(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/GUI/Register/Register.fxml"));
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    public void back(MouseEvent mouseEvent) throws IOException {
        String path = MenuHandler.getLoginBackAddress();
        Parent root = FXMLLoader.load(getClass().getResource(path));
        Stage stage = (Stage) ((ImageView) mouseEvent.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    public void exit(MouseEvent mouseEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are You Sure You Want To Exit", ButtonType.YES, ButtonType.NO);
        alert.showAndWait();
        if (alert.getResult().equals(ButtonType.YES)) {
            System.exit(1989);
        }
    }
}
