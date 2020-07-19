package GUI.Bank;

import GUI.Media.Audio;
import GUI.MenuHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import javax.sound.sampled.AudioInputStream;
import java.io.IOException;
import java.text.ParseException;

public class LogOrRegister {
    public PasswordField loginPassword;
    public PasswordField createAccountPassword;
    public PasswordField createAccountConfirmation;
    private String username;

    public void login(ActionEvent actionEvent) throws ParseException, IOException {
        Alert alert = new Alert(Alert.AlertType.WARNING, "", ButtonType.OK);

        //there's no user logged

        if (username == null) {
            alert.setContentText("you should login first in the store, go back and login!");
            alert.showAndWait();
            return;
        }
        
        //it's a boss

        if (MenuHandler.getRole().equalsIgnoreCase("boss")) {
            MenuHandler.getServer().clientToServer("bank " + "get token+" + "BOSS" + "+" + loginPassword.getText());
            String token = MenuHandler.getServer().serverToClient();
            System.out.println("this is the token " + token);
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

        if (!createAccountPassword.getText().equals(createAccountConfirmation.getText())) {
            alert.setContentText("the password and confirmation aren't the same");
            alert.showAndWait();
            return;
        }

        if (MenuHandler.getRole().equalsIgnoreCase("boss")) {
            alert.setContentText("as a boss you don't need to register, used the store bank password to login");
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
        } else {
            alert.setContentText("something went wrong, try again");
        }
        alert.showAndWait();
    }

    public void back(ActionEvent actionEvent) throws IOException {
        Audio.playClick1();
        Parent root = FXMLLoader.load(getClass().getResource("/GUI/ProfileLayout/ProfileLayout.fxml"));
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    public void initialize() {
        if (MenuHandler.getRole().equalsIgnoreCase("boss")) {
            username = "BOSS";
        } else {
            username = MenuHandler.getUsername();
        }
    }
}
