package GUI.BossProfile.ManagerUsersMenu;

import GUI.Media.Audio;
import GUI.MenuHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import javax.sound.sampled.AudioInputStream;
import java.io.IOException;
import java.text.ParseException;

public class MoveInformationPopOut {
    public Label companyField;
    public Label username;
    public Label name;
    public Label Email;
    public Label role;
    public Label telephone;
    public Label company;
    public Button viewRequests;
    public Button viewProducts;
    public Label creditField;
    public Label salesmanStateField;
    public Label credit;
    public Label salesmanState;
    public Button declineRequestButton;
    public Button acceptRequestButton;
    public Button removeButton;
    private String seeingUsername;

    public void initialize() throws ParseException, IOException {
        seeingUsername = MenuHandler.getSeeingUsername();
        MenuHandler.getServer().clientToServer("view account info+" + MenuHandler.getUsername() + "+" + seeingUsername);
        String serverAnswer = MenuHandler.getServer().serverToClient();
        System.out.println(serverAnswer);
        String[] information = serverAnswer.split("\n");
        username.setText(information[0].split("\\s")[1]);
        name.setText(information[2].split("\\s")[1] + " " + information[2].split("\\s")[2]);
        Email.setText(information[3].split("\\s")[1]);
        telephone.setText(information[4].split("\\s")[1]);
        role.setText(information[5].split("\\s")[1]);
        if (role.getText().equalsIgnoreCase("Boss")) {
            companyField.setVisible(false);
            company.setVisible(false);
            viewProducts.setVisible(false);
            credit.setVisible(false);
            creditField.setVisible(false);
            salesmanState.setVisible(false);
            salesmanStateField.setVisible(false);
            viewRequests.setVisible(false);
            acceptRequestButton.setVisible(false);
            declineRequestButton.setVisible(false);
        } else if (role.getText().equalsIgnoreCase("salesman")) {
            company.setText(information[6].split("\\s")[1]);
            credit.setText(information[7].split("\\s")[1]);
            salesmanState.setText(information[8].split("\\s")[2]);
            if (!salesmanState.getText().equals("CHECKING")) {
                acceptRequestButton.setVisible(false);
                declineRequestButton.setVisible(false);
            }
        } else {
            credit.setText(information[6].split("\\s")[1]);
            salesmanState.setVisible(false);
            acceptRequestButton.setVisible(false);
            declineRequestButton.setVisible(false);
            salesmanStateField.setVisible(false);
            viewProducts.setVisible(false);
            company.setVisible(false);
            companyField.setVisible(false);
        }
        MenuHandler.getServer().clientToServer("see authorization+" + MenuHandler.getUsername() + "+" + this.seeingUsername);
        String respond = MenuHandler.getServer().serverToClient();
        if (respond.equalsIgnoreCase("boss no")) {
            removeButton.setVisible(false);
        }
    }

    public void remove(ActionEvent actionEvent) {
        Audio.playClick4();
    }

    public void viewRequests(ActionEvent actionEvent) {
        Audio.playClick7();
    }

    public void ViewProducts(ActionEvent actionEvent) {
        Audio.playClick3();
    }

    public void back(ActionEvent actionEvent) throws IOException {
        Audio.playClick6();
        ((Button) actionEvent.getSource()).getScene().getWindow().hide();
    }

    public void acceptRequest(ActionEvent actionEvent) {
        Audio.playClick5();
    }

    public void declineRequest(ActionEvent actionEvent) {
        Audio.playClick1();
    }
}
