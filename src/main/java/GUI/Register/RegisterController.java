package GUI.Register;

import GUI.MenuHandler;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.text.ParseException;

public class RegisterController {

    ObservableList<String> rolesIfHasNotBoss = FXCollections.observableArrayList("Boss", "Salesman", "Customer");
    ObservableList<String> rolesIfHasBoss = FXCollections.observableArrayList("Salesman", "Customer");

    public TextField firstName;
    public TextField lastName;
    public PasswordField password;
    public TextField username;
    public TextField Email;
    public ChoiceBox role;
    public TextField telephone;
    public TextField company;

    public void initialize() throws ParseException, IOException {
        MenuHandler.getServer().clientToServer("is server has boss");
        if (MenuHandler.getServer().serverToClient().equalsIgnoreCase("yes")) {
            role.setItems(rolesIfHasBoss);
        } else {
            role.setItems(rolesIfHasNotBoss);
        }
    }

    public void openLoginMenu(ActionEvent actionEvent) {

    }

    public void register(ActionEvent actionEvent) {

    }
}
