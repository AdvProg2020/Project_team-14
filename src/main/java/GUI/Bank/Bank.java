package GUI.Bank;

import GUI.MenuHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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

    public void back(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/GUI/Bank/LogOrRegister.fxml"));
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    public void manageReciept(ActionEvent actionEvent) throws IOException {
        pane.getChildren().remove(pane.getChildren().get(0));
        pane.getChildren().add(FXMLLoader.load(getClass().getResource("/GUI/Bank/Pane/ManageReciept.fxml")));
    }

    public void newReciept(ActionEvent actionEvent) throws IOException {
        pane.getChildren().remove(pane.getChildren().get(0));
        pane.getChildren().add(FXMLLoader.load(getClass().getResource("/GUI/Bank/Pane/NewReciept.fxml")));
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
        try {
            pane.getChildren().remove(pane.getChildren().get(0));
        } catch (Exception ignored) {
        }
        pane.getChildren().add(FXMLLoader.load(getClass().getResource("/GUI/Bank/Pane/NewReciept.fxml")));

        if (MenuHandler.getRole().equalsIgnoreCase("boss")) {
            MenuHandler.getServer().clientToServer("bank " + "get balance+" + Bank.getToken() + "+" + "BOSS");
        } else {
            MenuHandler.getServer().clientToServer("bank " + "get balance+" + Bank.getToken() + "+" + MenuHandler.getUsername());
        }

        String credit = MenuHandler.getServer().serverToClient();

        if (credit.equals("token has expired")) {
            //going back
            return;
        }
        if (credit.matches("\\d+")) {
            creditLabel.setText(" your credit: " + credit);
        } else {
            creditLabel.setText("oops .... ");
        }
    }

}
