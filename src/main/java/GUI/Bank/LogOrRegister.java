package GUI.Bank;

import GUI.MenuHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.text.ParseException;

public class LogOrRegister {
    public PasswordField loginPassword;
    public PasswordField createAccountPassword;
    private String username = MenuHandler.getUsername();

    public void login(ActionEvent actionEvent) throws ParseException, IOException {
        Alert alert = new Alert(Alert.AlertType.WARNING, "", ButtonType.OK);

        //there's no user logged

        if (username == null) {
            alert.setContentText("you should login first in the store, go back and login!");
            alert.showAndWait();
            return;
        }

        //they don't have an account

        MenuHandler.getServer().clientToServer("bank " + "is there person with username+" + username);
        String answer = MenuHandler.getServer().serverToClient();
        if (answer.equals("false")) {
            alert.setContentText("you don't have an account, create one first!");
            alert.showAndWait();
            return;
        }

        MenuHandler.getServer().clientToServer("bank " + "get token+" + username + "+" + loginPassword.getText());
        String token = MenuHandler.getServer().serverToClient();
        if (!token.equals("fuck off, identification was wrong") && !token.equals("something went wrong")) {
            Bank.setToken(token);
            alert.setContentText("login successful");
            alert.showAndWait();
            Parent root = FXMLLoader.load(getClass().getResource("/GUI/Bank/Bank.fxml"));
            Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
        } else {
            alert.setContentText("wrong identification");
            alert.showAndWait();
        }

    }

    public void createAccount(ActionEvent actionEvent) throws ParseException, IOException {
        Alert alert = new Alert(Alert.AlertType.WARNING, "", ButtonType.OK);

        //there's no user logged

        if (username == null) {
            alert.setContentText("you should login first in the store, go back and login!");
            alert.showAndWait();
            return;
        }

        //they already have an account

        MenuHandler.getServer().clientToServer("bank " + "is there person with username+" + username);
        String answer = MenuHandler.getServer().serverToClient();
        if (answer.equals("true")) {
            alert.setContentText("you already have an account");
            alert.showAndWait();
            return;
        }
        MenuHandler.getServer().clientToServer("bank " + "create account+" + username + "+" + createAccountPassword.getText() + "+first name+second name");
        if (MenuHandler.getServer().serverToClient().equals("created successfully")) {
            alert.setContentText("created successfully");
            alert.showAndWait();
        } else {
            alert.setContentText("something went wrong, try again");
            alert.showAndWait();
        }
    }

    public void back(ActionEvent actionEvent) {

    }
}
