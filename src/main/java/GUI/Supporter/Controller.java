package GUI.Supporter;

import GUI.Media.Audio;
import GUI.MenuHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.text.ParseException;

public class Controller {
    public TextField createUsernameField;
    public TextField createPasswordField;
    public TextField deleteUsernameField;

    public void create(ActionEvent actionEvent) throws ParseException, IOException {
        Audio.playClick5();
        Alert alert = new Alert(Alert.AlertType.ERROR, "", ButtonType.OK);

        if (!createUsernameField.getText().matches("\\w+")) {
            alert.setContentText("Username Should Consists Of Only Characters And Numbers And _ Without Space");
            alert.showAndWait();
            return;
        } else if (!createPasswordField.getText().matches("\\w+")) {
            alert.setContentText("Password Should Consists Of Only Characters And Numbers And _ Without Space");
            alert.showAndWait();
            return;
        }
        MenuHandler.getConnector().clientToServer("make new supporter+" + createUsernameField.getText() + "+" + createPasswordField.getText());
    }

    public void delete(ActionEvent actionEvent) {

    }

    public void back(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/GUI/ProfileLayout/ProfileLayout.fxml"));
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
    }

}
