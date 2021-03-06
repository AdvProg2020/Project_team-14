package GUI.Bank;

import GUI.MenuHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.text.ParseException;

public class Bank {
    public Pane pane;
    private static String token;
    private static String password;
    public Label creditLabel;
    public static Bank bank;
    private String username = MenuHandler.getRole().equalsIgnoreCase("boss") ? "BOSS" : MenuHandler.getUsername();

    public void back(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/GUI/Bank/LogOrRegister.fxml"));
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    private void checkExpired(ActionEvent actionEvent) throws IOException {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "", ButtonType.OK);
        MenuHandler.getConnector().clientToServer("bank " + "get all receipts by me+" + Bank.getToken() + "+" + username);
        String answer = MenuHandler.getConnector().serverToClient();
        if (answer.equals("token isn't authentic") || answer.equals("something went wrong") || answer.contains("expired")) {
            alert.setContentText("you token is expired, you may wanna login again");
            alert.showAndWait();
            Parent root = FXMLLoader.load(getClass().getResource("/GUI/Bank/LogOrRegister.fxml"));
            Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
        }
    }

    public void manageReceipt(ActionEvent actionEvent) throws IOException, ParseException {
        checkExpired(actionEvent);
        try {
            pane.getChildren().remove(pane.getChildren().get(0));
            pane.getChildren().add(FXMLLoader.load(getClass().getResource("/GUI/Bank/Pane/ManageReceipt.fxml")));
        } catch (Exception ignored) { }
    }

    public void newReceipt(ActionEvent actionEvent) throws IOException, ParseException {
        checkExpired(actionEvent);
        try {
            pane.getChildren().remove(pane.getChildren().get(0));
            pane.getChildren().add(FXMLLoader.load(getClass().getResource("/GUI/Bank/Pane/NewReceipt.fxml")));
        } catch (Exception ignored) {
        }
    }

    public static String getToken() {
        return token;
    }

    public static void setToken(String token) {
        Bank.token = token;
    }

    public static void setPassword(String password) {
        Bank.password = password;
    }

    public static String getPassword() {
        return password;
    }

    public void initialize() throws IOException, ParseException {
        pane.getChildren().add(FXMLLoader.load(getClass().getResource("/GUI/Bank/Pane/NewReceipt.fxml")));
        updateCredit();
        bank = this;
    }

    public void updateCredit() throws IOException, ParseException {
        if (MenuHandler.getRole().equalsIgnoreCase("boss")) {
            MenuHandler.getConnector().clientToServer("bank " + "get balance+" + Bank.getToken() + "+" + "BOSS");
        } else {
            MenuHandler.getConnector().clientToServer("bank " + "get balance+" + Bank.getToken() + "+" + username);
        }
        String credit = MenuHandler.getConnector().serverToClient();
        if (credit.equals("token has expired")) {
            return;
        }
        if (credit.matches("\\d+")) {
            creditLabel.setText(" your credit: " + credit);
            return;
        }
        creditLabel.setText("oops .... ");
    }

    public static boolean isPossibleToDepositForSalesman(long amount) throws IOException {
        if (!MenuHandler.getRole().equalsIgnoreCase("salesman")) {
            return true;
        }
        long credit = MenuHandler.getCredit();
        return credit - amount >= MenuHandler.getMinCredit();
    }

}
