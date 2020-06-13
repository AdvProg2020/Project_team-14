package GUI.Register;

import GUI.MenuHandler;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.text.ParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterController {

    public Label companyLabel;
    ObservableList<String> rolesIfHasNotBoss = FXCollections.observableArrayList("Boss", "Salesman", "Customer");
    ObservableList<String> rolesIfHasBoss = FXCollections.observableArrayList("Customer", "Salesman");

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

    public void openLoginMenu(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/GUI/Login/Login.fxml"));
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    public void register(ActionEvent actionEvent) throws ParseException, IOException {
        Alert alert = new Alert(Alert.AlertType.ERROR, "", ButtonType.OK);
        if (username.getText().equals("")) {
            alert.setContentText("Username Field Must Not Be Empty");
            alert.showAndWait();
        } else if (password.getText().equals("")) {
            alert.setContentText("Password Field Must Not Be Empty");
            alert.showAndWait();
        } else if (firstName.getText().equals("")) {
            alert.setContentText("First Name Field Must Not Be Empty");
            alert.showAndWait();
        } else if (lastName.getText().equals("")) {
            alert.setContentText("Last Name Field Must Not Be Empty");
            alert.showAndWait();
        } else if (role.getValue() == null) {
            alert.setContentText("You Should Choose A Role");
            alert.showAndWait();
        } else if (Email.getText().equals("")) {
            alert.setContentText("Please Enter Your Email");
            alert.showAndWait();
        } else if (telephone.getText().equals("")) {
            alert.setContentText("Telephone Field Must Not Be Empty");
            alert.showAndWait();
        } else if (((String) role.getValue()).equalsIgnoreCase("salesman") && company.getText().equals("")) {
            alert.setContentText("Company Field Must Not Be Empty");
            alert.showAndWait();
        } else {
            if (!checkUsernameFormat(username.getText())) {
                alert.setContentText("Username Should Consists Of Only Characters And Numbers And _ Without Space");
                alert.showAndWait();
            } else if (!checkUsernameFormat(password.getText())) {
                alert.setContentText("Password Should Consists Of Only Characters And Numbers And _ Without Space");
                alert.showAndWait();
            } else if (!checkNameFormat(firstName.getText())) {
                alert.setContentText("Name Should Consists Of Only Characters Like [A-Z][a-z]");
                alert.showAndWait();
            } else if (!checkNameFormat(lastName.getText())) {
                alert.setContentText("Name Should Consists Of Only Characters Like [A-Z][a-z]");
                alert.showAndWait();
            } else if (!checkEmailFormat(Email.getText())) {
                alert.setContentText("It's Not A Valid Email Form");
                alert.showAndWait();
            } else if (!checkTelephoneFormat(telephone.getText())) {
                alert.setContentText("Telephone Should Only Consists Of Numbers And Should Be 11 Digits");
                alert.showAndWait();
            } else {
                String message = "register+" + firstName.getText() + "+" + lastName.getText() + "+" + username.getText()
                        + "+" + password.getText() + "+" + ((String) role.getValue()) + "+" + Email.getText()
                        + "+" + telephone.getText();
                if (((String) role.getValue()).equalsIgnoreCase("salesman")) {
                    message += "+" + company.getText();
                }
                (MenuHandler.getServer()).clientToServer(message);
                String serverAnswer;
                serverAnswer = (MenuHandler.getServer()).serverToClient();
                if (serverAnswer.equalsIgnoreCase("the username is already taken, try something else")) {
                    alert.setContentText("This Username Has Been Taken Please Try Something Else");
                } else {
                    alert.setAlertType(Alert.AlertType.CONFIRMATION);
                    alert.setContentText("Your Register Was Successful");
                }
                alert.showAndWait();
                Parent root = FXMLLoader.load(getClass().getResource("/GUI/Register/Register.fxml"));
                Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
                stage.setScene(new Scene(root));
            }
        }
    }

    public void checkSalesmanRole() {
        if (role.getValue() == null) return;
        if (((String) role.getValue()).equalsIgnoreCase("salesman")) {
            companyLabel.setVisible(true);
            company.setVisible(true);
        } else {
            company.setVisible(false);
            companyLabel.setVisible(false);
        }
    }

    private Matcher getMatcher(String regex, String command) {
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(command);
    }

    private boolean checkNameFormat(String name) {
        return getMatcher("[a-zA-Z]+", name).matches();
    }

    private boolean checkUsernameFormat(String input) {
        return getMatcher("(\\w+)", input).matches();
    }

    private boolean checkEmailFormat(String mail) {
        return getMatcher("((\\w||\\.)+)@(\\w+)\\.(com||ir||io||edu)", mail).matches();
    }

    private boolean checkTelephoneFormat(String number) {
        return getMatcher("0(\\d+)", number).matches() && number.length() == 11;
    }

    public void back(MouseEvent mouseEvent) throws IOException {
        String path = MenuHandler.getLoginBackAddress();
        Parent root = FXMLLoader.load(getClass().getResource(path));
        Stage stage = (Stage) ((ImageView) mouseEvent.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
    }
}
