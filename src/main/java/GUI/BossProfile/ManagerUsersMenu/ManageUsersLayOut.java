package GUI.BossProfile.ManagerUsersMenu;

import GUI.Media.Audio;
import GUI.MenuHandler;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Popup;

import java.io.IOException;
import java.text.ParseException;

public class ManageUsersLayOut {
    public TableColumn<Account, String> username;
    public TableColumn<Account, String> firstName;
    public TableColumn<Account, String> lastName;
    public TableColumn<Account, String> role;
    public CheckBox bossRole;
    public CheckBox salesmanRole;
    public CheckBox customerRole;
    public TextField minCredit;
    public TextField maxCredit;
    public TableView<Account> accounts;

    public ObservableList<Account> observableList = FXCollections.observableArrayList();


    public void initialize() throws IOException, ParseException {
        update();
    }

    private void update() throws ParseException, IOException {
        String command = "show accounts+" + MenuHandler.getUsername();
        String minCredit = "", maxCredit = "", role = "";
        if (!this.minCredit.getText().equals("")) {
            minCredit = this.minCredit.getText();
        }
        if (!this.maxCredit.getText().equals("")) {
            maxCredit = this.maxCredit.getText();
        }
        if (this.bossRole.isSelected()) {
            role = bossRole.getText();
        }
        if (this.customerRole.isSelected()) {
            if (role.equals("")) {
                role = customerRole.getText();
            } else {
                role += "," + customerRole.getText();
            }
        }
        if (this.salesmanRole.isSelected()) {
            if (role.equals("")) {
                role = salesmanRole.getText();
            } else {
                role += "," + salesmanRole.getText();
            }
        }
        if (!(role.equals("") && minCredit.equals("") && maxCredit.equals(""))) {
            command += "+filters:";
            if (!minCredit.equals("")) {
                command += "+minCredit+" + minCredit;
            }
            if (!maxCredit.equals("")) {
                command += "+maxCredit+" + maxCredit;
            }
            if (!role.equals("")) {
                command += "+role+" + role;
            }
        }
        username.setCellValueFactory(new PropertyValueFactory<>("username"));
        firstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        this.role.setCellValueFactory(new PropertyValueFactory<>("role"));
        MenuHandler.getConnector().clientToServer(command);
        String serverAnswer = MenuHandler.getConnector().serverToClient();
        observableList = FXCollections.observableArrayList();
        for (String s : serverAnswer.split("\n")) {
            if (s.startsWith("here") || s.startsWith("All") || s.startsWith("nothing")) continue;
            Account account = new Account(s.split("\\s")[1], s.split("\\s")[5], s.split("\\s")[6], s.split("\\s")[3]);
            observableList.add(account);
        }
        accounts.setItems(observableList);
    }

    public void makeNewManager(ActionEvent actionEvent) throws IOException {
        Audio.playClick4();
        Parent root = FXMLLoader.load(getClass().getResource("/GUI/BossProfile/ManagerUsersMenu/NewManagerPopOut.fxml"));
        Popup popup = new Popup();
        popup.getContent().add(root);
        popup.show(((Button) actionEvent.getSource()).getScene().getWindow());
        popup.setOnHiding(e -> {
            try {
                update();
            } catch (ParseException | IOException ex) {
                ex.printStackTrace();
            }
        });
    }

    public void filter(ActionEvent actionEvent) throws IOException, ParseException {
        Audio.playClick7();
        update();
    }

    public void viewAccount(ActionEvent actionEvent) throws IOException {
        Audio.playClick4();
        if (accounts.getSelectionModel().getSelectedItem() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "You Must Choose From Accounts First", ButtonType.OK);
            alert.showAndWait();
            return;
        }
        Account account = accounts.getSelectionModel().getSelectedItem();
        MenuHandler.setSeeingUsername(account.getUsername());
        Popup popup = new Popup();
        popup.getContent().add(FXMLLoader.load(getClass().getResource("/GUI/BossProfile/ManagerUsersMenu/MoveInformationPopOut.fxml")));
        popup.show(((Button) actionEvent.getSource()).getScene().getWindow());
        popup.setOnHiding(e -> {
            try {
                update();
            } catch (ParseException | IOException ex) {
                ex.printStackTrace();
            }
        });
    }
}
