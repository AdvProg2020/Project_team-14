package GUI.BossProfile.ManagerUsersMenu;

import GUI.MenuHandler;
import javafx.event.ActionEvent;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Menu;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.text.ParseException;

public class ManageUsersLayOut {
    public TableColumn username;
    public TableColumn firstName;
    public TableColumn lastName;
    public TableColumn role;
    public CheckBox bossRole;
    public CheckBox salesmanRole;
    public CheckBox customerRole;
    public TextField minCredit;
    public TextField maxCredit;

    public void initialize() {
        username.getColumns().add("bye");
    }

    private void update() throws ParseException, IOException {
        MenuHandler.getServer().clientToServer("show accounts+" +MenuHandler.getUsername());
        String serverAnswer=MenuHandler.getServer().serverToClient();
        System.out.println(serverAnswer);
    }

    public void makeNewManager(ActionEvent actionEvent) {

    }

    public void filter(ActionEvent actionEvent) {

    }
}
