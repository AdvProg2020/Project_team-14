package GUI.Bank.Pane;

import GUI.Bank.Bank;
import GUI.MenuHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;
import java.text.ParseException;

public class ManageReceipt {
    public VBox box1;
    public VBox box2;
    public TextField receiptID;
    private String username;

    public void initialize() throws IOException, ParseException {
        username = MenuHandler.getRole().equalsIgnoreCase("boss") ? "BOSS" : MenuHandler.getUsername();
        updateBoxOne();
        updateBoxTwo();
    }

    private void updateBoxOne() throws ParseException, IOException {
        box1.getChildren().clear();
        MenuHandler.getConnector().clientToServer("bank " + "get all receipts by me+" + Bank.getToken() + "+" + MenuHandler.getUsername());
        String answer = MenuHandler.getConnector().serverToClient();
        if (answer.equals("token isn't authentic") || answer.equals("something went wrong")) {
            //logout
            return;
        }
        if (answer.equals("token has expired")) {
            //logout
            return;
        }
        if (answer.equals("")) {
            Label label = new Label("no receipt in this part");
            label.setFont(new Font("Arial", 15));
            box1.getChildren().add(label);
            return;
        }
        for (String string : answer.split("\n")) {
            Label label = new Label(string);
            label.setFont(new Font("Arial", 15));
            box1.getChildren().add(label);
        }
    }

    private void updateBoxTwo() throws ParseException, IOException {
        box2.getChildren().clear();
        MenuHandler.getConnector().clientToServer("bank " + "get all receipts involving me+" + Bank.getToken() + "+" + MenuHandler.getUsername());
        String answer = MenuHandler.getConnector().serverToClient();
        if (answer.equals("token isn't authentic") || answer.equals("something went wrong")) {
            //logout
            return;
        }
        if (answer.equals("token has expired")) {
            //logout
            return;
        }
        if (answer.equals("")) {
            Label label = new Label("no receipt in this part");
            label.setFont(new Font("Arial", 15));
            box2.getChildren().add(label);
            return;
        }
        for (String string : answer.split("\n")) {
            Label label = new Label(string);
            label.setFont(new Font("Arial", 15));
            box2.getChildren().add(label);
        }
    }

    public void done(ActionEvent actionEvent) throws ParseException, IOException {
        MenuHandler.getConnector().clientToServer("bank " + "pay transaction with id+" + Bank.getToken() + "+" + receiptID.getText() + "+" + username);
        String answer = MenuHandler.getConnector().serverToClient();
        if (receiptID.getText().equals("") || receiptID.getText() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "the receipt ID cannot be empty", ButtonType.OK);
            alert.showAndWait();
            return;
        }
        if (!receiptID.getText().matches("\\d+")) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "the receipt ID should only contain digits", ButtonType.OK);
            alert.showAndWait();
            return;
        }
        if (answer.equals("token isn't authentic") || answer.equals("something went wrong")) {
            Parent root = FXMLLoader.load(getClass().getResource("/GUI/Bank/LogOrRegister.fxml"));
            Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            return;
        }
        if (answer.equals("token has expired")) {
            Parent root = FXMLLoader.load(getClass().getResource("/GUI/Bank/LogOrRegister.fxml"));
            Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            return;
        }
        if (answer.equals("transaction ID is not authentic") || answer.equals("that's not your receipt to pay") || answer.equals("it's already done")) {
            Alert alert = new Alert(Alert.AlertType.ERROR, answer, ButtonType.OK);
            alert.showAndWait();
            return;
        }

        try {
            MenuHandler.getConnector().clientToServer("bank " + "get amount of transaction+" + Bank.getToken() + "+" + receiptID.getText());
            long ID = Long.parseLong(receiptID.getText());
            long amount = Long.parseLong(MenuHandler.getConnector().serverToClient());
            if (200000 > ID && ID >= 100000 && !Bank.isPossibleToDepositForSalesman(amount)) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "you should keep the min credit", ButtonType.OK);
                alert.showAndWait();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (answer.equals("successful")) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, answer, ButtonType.OK);
            alert.showAndWait();
            manageCredit(receiptID.getText());
            receiptID.setText("");
            Bank.bank.updateCredit();
            updateBoxOne();
            updateBoxTwo();
            return;
        }
        Alert alert = new Alert(Alert.AlertType.ERROR, answer, ButtonType.OK);
        alert.showAndWait();
    }

    private void manageCredit(String string) {
        try {
            MenuHandler.getConnector().clientToServer("bank " + "get amount of transaction+" + Bank.getToken() + "+" + string);
            long ID = Long.parseLong(string);
            long amount = Long.parseLong(MenuHandler.getConnector().serverToClient());
            if (200000 > ID && ID >= 100000 && MenuHandler.getRole().equalsIgnoreCase("salesman")) {
                amount = -amount;
                MenuHandler.getConnector().clientToServer("add balance+" + MenuHandler.getUsername() + "+" + amount);
            }
            if (300000 > ID && ID >= 200000 && MenuHandler.getRole().equalsIgnoreCase("customer")) {
                MenuHandler.getConnector().clientToServer("add balance+" + MenuHandler.getUsername() + "+" + amount);
                System.out.println("we're here");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
