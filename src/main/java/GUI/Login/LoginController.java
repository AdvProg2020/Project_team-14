package GUI.Login;

import GUI.Media.Audio;
import GUI.MenuHandler;
import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.text.ParseException;
import java.util.Random;

public class LoginController {

    public TextField username;
    public PasswordField password;
    public AnchorPane loginBoard;
    public Pane photoBoard;
    public Label codeLabel;
    public TextField code;
    private int numberOfTimesTries;
    private int numberOfWrongCode;
    private boolean checkSecurity;

    public void Login(ActionEvent actionEvent) throws ParseException, IOException {
        Audio.playClick7();
        numberOfTimesTries++;
        Alert alert = new Alert(Alert.AlertType.ERROR, "", ButtonType.OK);
        checkSecurity(actionEvent);
        if (checkSecurity) {
            return;
        }
        if (!code.getText().equals(codeLabel.getText())) {
            numberOfWrongCode++;
            alert.setContentText("wrong code");
            alert.showAndWait();
        } else if (username.getText().equals("")) {
            alert.setContentText("Username Field Must Not Be Empty");
            alert.showAndWait();
        } else if (password.getText().equals("")) {
            alert.setContentText("Password Field Must Not Be Empty");
            alert.showAndWait();
        } else {
            MenuHandler.getConnector().clientToServer("login+" + username.getText() + "+" + password.getText());
            String serverAnswer = MenuHandler.getConnector().serverToClient();
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
                setAvatarInClient();
                alert.setAlertType(Alert.AlertType.INFORMATION);
                alert.setContentText("Login Successful, " + "Welcome");
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

    private void setAvatarInClient() throws ParseException, IOException {
        String toServer = "get person image+" + MenuHandler.getUsername();
        MenuHandler.getConnector().clientToServer(toServer);
        String respond = MenuHandler.getConnector().serverToClient();
        MenuHandler.setUserAvatar(respond);
    }

    public void openRegisterMenu(ActionEvent actionEvent) throws IOException {
        Audio.playClick3();
        Parent root = FXMLLoader.load(getClass().getResource("/GUI/Register/Register.fxml"));
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();

        ParallelTransition pt = doTransition("end");
        pt.play();
        pt.setOnFinished(e -> stage.setScene(new Scene(root)));
    }

    private ParallelTransition doTransition(String type) {
        double from, to;
        if (type.equals("start")) {
            from = 0;
            to = 1;
        } else {
            from = 1;
            to = 0;
        }
        FadeTransition ft1 = new FadeTransition(Duration.millis(500), loginBoard);
        ft1.setFromValue(from);
        ft1.setToValue(to);

        FadeTransition ft2 = new FadeTransition(Duration.millis(500), photoBoard);
        ft2.setFromValue(from);
        ft2.setToValue(to);

        return new ParallelTransition(ft1, ft2);
    }

    public void back(MouseEvent mouseEvent) throws IOException {
        Audio.playClick2();
        String path = MenuHandler.getLoginBackAddress();
        Parent root = FXMLLoader.load(getClass().getResource(path));
        Stage stage = (Stage) ((ImageView) mouseEvent.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    public void exit(MouseEvent mouseEvent) {
        Audio.playClick1();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are You Sure You Want To Exit", ButtonType.YES, ButtonType.NO);
        alert.showAndWait();
        if (alert.getResult().equals(ButtonType.YES)) {
            System.exit(1989);
        }
    }

    @FXML
    public void initialize() {
        checkSecurity = false;
        numberOfTimesTries = 0;
        numberOfWrongCode = 0;
        Random random = new Random();
        codeLabel.setText(String.valueOf(random.nextInt(10000)));
        ParallelTransition pt = doTransition("start");
        pt.play();
    }

    public void resetCode(ActionEvent actionEvent) {
        Random random = new Random();
        codeLabel.setText(String.valueOf(random.nextInt(10000)));
    }

    private void checkSecurity(ActionEvent actionEvent) throws IOException {
        if (numberOfTimesTries == 10 || numberOfWrongCode == 3) {
            checkSecurity = true;
            Parent root = FXMLLoader.load(getClass().getResource("/GUI/Login/Lock/Lock.fxml"));
            Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            numberOfWrongCode = 0;
            numberOfTimesTries = 0;
        }
    }

}
