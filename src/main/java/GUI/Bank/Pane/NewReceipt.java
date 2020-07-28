package GUI.Bank.Pane;

import GUI.Bank.Bank;
import GUI.MenuHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.text.ParseException;

public class NewReceipt {
    public TextField depositAmount;
    public TextField withdrawAmount;
    public TextField transferAmount;
    public TextField transferUsername;
    public TextField depositDescription;
    public TextField withdrawDescription;
    public TextField transferDescription;
    public Button deposit;
    public Button withdraw;
    public Label depositLabel;
    public Label withdrawLabel;
    private String username;

    private void resetTextFields() {
        depositAmount.setText("");
        withdrawAmount.setText("");
        transferAmount.setText("");
        transferUsername.setText("");
        depositDescription.setText("");
        withdrawDescription.setText("");
        transferDescription.setText("");
    }

    public void depositDone(ActionEvent actionEvent) throws ParseException, IOException {
        long amount;
        String description;

        try {
            amount = Long.parseLong(depositAmount.getText());
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "invalid amount", ButtonType.OK);
            alert.showAndWait();
            return;
        }

        description = (depositDescription.getText() == null || depositDescription.getText().equals("")) ? "empty" : depositDescription.getText();

        MenuHandler.getConnector().clientToServer("bank " + "create deposit receipt+" + Bank.getToken() + "+" + username + "+" + amount + "+" + description);

        String result = MenuHandler.getConnector().serverToClient();

        if (result.equals("successful")) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "created successfully", ButtonType.OK);
            alert.showAndWait();
            resetTextFields();
            return;
        }

        if (result.equals("token has expired")) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "you token is expired, you may wanna login again", ButtonType.OK);
            alert.showAndWait();

            //going back to register login page

            Parent root = FXMLLoader.load(getClass().getResource("/GUI/Bank/LogOrRegister.fxml"));
            Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            Bank.setToken(null);
            Bank.setPassword(null);
            return;
        }

        Alert alert = new Alert(Alert.AlertType.ERROR, "something has gone wrong, try again", ButtonType.OK);
        alert.showAndWait();

    }

    public void withdrawDone(ActionEvent actionEvent) throws ParseException, IOException {
        long amount;
        String description;

        try {
            amount = Long.parseLong(withdrawAmount.getText());
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "invalid amount", ButtonType.OK);
            alert.showAndWait();
            return;
        }

        description = (withdrawDescription.getText() == null || withdrawDescription.getText().equals("")) ? "empty" : withdrawDescription.getText();

        MenuHandler.getConnector().clientToServer("bank " + "create withdraw receipt+" + Bank.getToken() + "+" + username + "+" + amount + "+" + description);

        String result = MenuHandler.getConnector().serverToClient();

        if (result.equals("successful")) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "created successfully", ButtonType.OK);
            alert.showAndWait();
            resetTextFields();
            return;
        }
        if (result.equals("token has expired")) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "you token is expired, you may wanna login again", ButtonType.OK);
            alert.showAndWait();
            Parent root = FXMLLoader.load(getClass().getResource("/GUI/Bank/LogOrRegister.fxml"));
            Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            Bank.setToken(null);
            Bank.setPassword(null);
            return;
        }
        Alert alert = new Alert(Alert.AlertType.ERROR, "something has gone wrong, try again", ButtonType.OK);
        alert.showAndWait();
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
        MenuHandler.getConnector().clientToServer("bank " + "is there person with username+" + otherUser);
        String answer = MenuHandler.getConnector().serverToClient();
        if (answer.equals("false")) {
            alert.setContentText("the username entered isn't valid, try something valid");
            alert.showAndWait();
            return;
        }

        description = (transferDescription.getText() == null || transferDescription.getText().equals("")) ? "empty" : transferDescription.getText();

        MenuHandler.getConnector().clientToServer("bank " + "create transfer receipt+" + Bank.getToken() + "+" + username + "+" + otherUser + "+" + amount + "+" + description);

        String result = MenuHandler.getConnector().serverToClient();

        if (result.equals("successful")) {
            alert.setContentText("created successfully");
            alert.showAndWait();
            transferUsername.setText("");
            transferAmount.setText("");
            transferDescription.setText("");
            return;
        }

        if (result.equals("token has expired")) {
            alert.setContentText("you token is expired, you may wanna login again");
            alert.showAndWait();
            Parent root = FXMLLoader.load(getClass().getResource("/GUI/Bank/LogOrRegister.fxml"));
            Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            Bank.setToken(null);
            Bank.setPassword(null);
            return;
        }

        alert.setContentText("something has gone wrong, try again");
        alert.showAndWait();
    }

    public void initialize() {
        username = MenuHandler.getRole().equalsIgnoreCase("boss") ? "BOSS" : MenuHandler.getUsername();
        String role = MenuHandler.getRole();
        if (role.equalsIgnoreCase("boss")) {
            withdraw.setDisable(true);
            deposit.setDisable(true);
        } else if (role.equalsIgnoreCase("customer")) {
            depositLabel.setText("deposit (add money to your account)");
            withdrawLabel.setText("withdraw (add credit to your account)");
        } else if (role.equalsIgnoreCase("salesman")) {
            withdraw.setDisable(true);
            depositLabel.setText("deposit (add to you bank account from credits)");
        }
    }

}
