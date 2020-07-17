package GUI.Bank.Pane;

import GUI.Bank.Bank;
import GUI.MenuHandler;
import Menus.Menu;
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

public class NewReciept {
    public TextField depositAmount;
    public TextField withdrawAmount;
    public TextField transferAmount;
    public TextField transferUsername;
    public TextField depositDescription;
    public TextField withdrawDescription;
    public TextField transferDescription;
    public Button deposit;
    public Button withdraw;
    private String username;


    public void depositDone(ActionEvent actionEvent) throws ParseException, IOException {
        long amount;
        String description;

        try {
            amount = Long.parseLong(depositAmount.getText());
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "", ButtonType.OK);
            alert.setContentText("invalid amount");
            alert.showAndWait();
            return;
        }

        if (depositDescription.getText() == null || depositDescription.getText().equals("")) {
            description = " ";
        } else {
            description = depositDescription.getText();
        }

        MenuHandler.getServer().clientToServer("create deposit receipt+" + Bank.getToken() + "+" + username + "+" + amount + "+" + description);
        String result = MenuHandler.getServer().serverToClient();

        if (result.equals("successful")) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "created successfully", ButtonType.OK);
            alert.showAndWait();
        } else if (result.equals("token has expired")) {

            Alert alert = new Alert(Alert.AlertType.WARNING, "you token is expired, you may wanna login again", ButtonType.OK);
            alert.showAndWait();

            //going back to register login page

            Parent root = FXMLLoader.load(getClass().getResource("/GUI/Bank/LogOrRegister.fxml"));
            Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            Bank.setToken(null);
            Bank.setPassword(null);
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "something has gone wrong, try again", ButtonType.OK);
            alert.showAndWait();
        }

    }

    public void withdrawDone(ActionEvent actionEvent) throws ParseException, IOException {
        long amount;
        String description;

        try {
            amount = Long.parseLong(withdrawAmount.getText());
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "", ButtonType.OK);
            alert.setContentText("invalid amount");
            alert.showAndWait();
            return;
        }

        if (withdrawDescription.getText() == null || withdrawDescription.getText().equals("")) {
            description = " ";
        } else {
            description = withdrawDescription.getText();
        }

        MenuHandler.getServer().clientToServer("create withdraw receipt+" + Bank.getToken() + "+" + username + "+" + amount + "+" + description);
        String result = MenuHandler.getServer().serverToClient();

        if (result.equals("successful")) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "created successfully", ButtonType.OK);
            alert.showAndWait();
        } else if (result.equals("token has expired")) {

            Alert alert = new Alert(Alert.AlertType.WARNING, "you token is expired, you may wanna login again", ButtonType.OK);
            alert.showAndWait();

            //going back to register login page

            Parent root = FXMLLoader.load(getClass().getResource("/GUI/Bank/LogOrRegister.fxml"));
            Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            Bank.setToken(null);
            Bank.setPassword(null);

        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "something has gone wrong, try again", ButtonType.OK);
            alert.showAndWait();
        }

    }

    public void transferDone(ActionEvent actionEvent) throws ParseException, IOException {
        String otherUser = transferUsername.getText();
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "", ButtonType.OK);
        long amount;
        String description;

        try {
            amount = Long.parseLong(transferAmount.getText());
        } catch (Exception e) {
            alert.setContentText("invalid amount");
            alert.showAndWait();
            return;
        }

        if (otherUser == null || otherUser.equals("")) {
            alert.setContentText("the username field cannot be empty");
            alert.showAndWait();
            return;
        }

        if (otherUser.equals(username)) {
            alert.setContentText("you cannot transfer money to your own account");
            alert.showAndWait();
            return;
        }

        MenuHandler.getServer().clientToServer("bank " + "is there person with username+" + otherUser);
        String answer = MenuHandler.getServer().serverToClient();
        System.out.println("there it is: " + answer);
        if (answer.equals("false")) {
            alert.setContentText("the username entered isn't valid, try something valid");
            alert.showAndWait();
            return;
        }

        if (transferDescription.getText() == null || transferDescription.getText().equals("")) {
            description = " ";
        } else {
            description = transferDescription.getText();
        }
        MenuHandler.getServer().clientToServer("create transfer receipt+" + Bank.getToken() + "+" + username + "+" + otherUser + "+" + amount + "+" + description);

        String result = MenuHandler.getServer().serverToClient();

        if (result.equals("successful")) {
            alert.setContentText("created successfully");
            alert.showAndWait();
        } else if (result.equals("token has expired")) {

            alert.setContentText("you token is expired, you may wanna login again");
            alert.showAndWait();

            //going back to register login page

            Parent root = FXMLLoader.load(getClass().getResource("/GUI/Bank/LogOrRegister.fxml"));
            Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            Bank.setToken(null);
            Bank.setPassword(null);

        } else {
            alert.setContentText("something has gone wrong, try again");
            alert.showAndWait();
        }
    }

    public void initialize() {

        if (MenuHandler.getRole().equalsIgnoreCase("boss")) {
            username = "BOSS";
        } else {
            username = MenuHandler.getUsername();
        }

        if (MenuHandler.getRole().equalsIgnoreCase("customer")) {
            deposit.setDisable(true);
        } else if (MenuHandler.getRole().equalsIgnoreCase("boss")) {
            withdraw.setDisable(true);
            deposit.setDisable(true);
        }
    }

}
