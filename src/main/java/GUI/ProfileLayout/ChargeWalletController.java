package GUI.ProfileLayout;

import GUI.Media.Audio;
import GUI.MenuHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.text.ParseException;
import java.util.Random;

public class ChargeWalletController {

    public TextField amount;
    public TextField creditCard;
    public TextField Date;
    public TextField Numbers;
    public Label randomNumber;
    public Button back;
    public Button done;

    public void back(ActionEvent actionEvent) throws IOException {
        Audio.playClick5();
        ((Button) actionEvent.getSource()).getScene().getWindow().hide();
    }

    public void done(ActionEvent actionEvent) throws ParseException, IOException {
        Audio.playClick4();
        Alert alert = new Alert(Alert.AlertType.ERROR, "", ButtonType.OK);
        ((Stage) alert.getDialogPane().getScene().getWindow()).setAlwaysOnTop(true);
        ((Stage) alert.getDialogPane().getScene().getWindow()).toFront();

        if (!amount.getText().matches("\\d+")) {
            alert.setContentText("amount should only be an integer");
            alert.showAndWait();
        } else if (amount.getText().length() > 8) {
            alert.setContentText("your number is too big");
            alert.showAndWait();
        } else if (!creditCard.getText().matches("\\d+")) {
            alert.setContentText("invalid credit card");
            alert.showAndWait();
        } else if (!randomNumber.getText().equals(Numbers.getText())) {
            alert.setContentText("verification didn't happen");
            alert.showAndWait();
        } else if (!Date.getText().matches("^(0[1-9]|[12][0-9]|3[01])[- /.](0[1-9]|1[012])[- /.](19|20)\\d\\d$")) {
            alert.setContentText("invalid date");
            alert.showAndWait();
        } else {
            MenuHandler.getServer().clientToServer("add balance+" + MenuHandler.getUsername() + "+" + amount.getText());
            if(MenuHandler.getServer().serverToClient().startsWith("suc")){
                alert.setContentText("successfully added to your account");
                alert.showAndWait();
            } else {
                alert.setContentText("something went wrong");
                alert.showAndWait();
            }
        }
    }

    public void initialize() {
        Random random = new Random();
        randomNumber.setText(String.valueOf(random.nextInt(10000)));
    }
}
