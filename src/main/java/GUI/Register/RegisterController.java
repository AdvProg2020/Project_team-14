package GUI.Register;

import GUI.Media.Audio;
import GUI.MenuHandler;
import javafx.animation.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.text.ParseException;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterController {

    public Label companyLabel;
    public MediaView mediaView;
    public TextField indegtification;
    public Label identificationLabel;
    private int numberOfTimesTried;
    private int numberOfBadTries;
    private boolean checkSecurity;
    private Random random = new Random();
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
    public Pane avatarPane;
    public Pane avatarBoard;
    public Pane registerBoard;

    @FXML
    public void initialize() throws ParseException, IOException {
        checkSecurity = false;
        numberOfBadTries = 0;
        numberOfTimesTried = 0;
        identificationLabel.setText(String.valueOf(random.nextInt(10000)));
        MenuHandler.getConnector().clientToServer("is server has boss");
        if (MenuHandler.getConnector().serverToClient().equalsIgnoreCase("yes")) {
            role.setItems(rolesIfHasBoss);
        } else {
            role.setItems(rolesIfHasNotBoss);
        }

        avatarPane.getChildren().add(FXMLLoader.load(getClass().getResource("/GUI/Register/Avatar.fxml")));
        ParallelTransition pt = doTransition("start");
        pt.play();
    }

    public void openLoginMenu(ActionEvent actionEvent) throws IOException {
        Audio.playClick2();
        Parent root = FXMLLoader.load(getClass().getResource("/GUI/Login/Login.fxml"));
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();

        ParallelTransition pt = doTransition("end");
        pt.play();
        pt.setOnFinished(e -> stage.setScene(new Scene(root)));
    }

    private ParallelTransition doTransition(String type) {
        double from, to;
        if (type.equals("start")) {
            from = 0;
            to = 1;
        } else {
            from = 1;
            to = 0;
        }
        FadeTransition ft1 = new FadeTransition(Duration.millis(500), registerBoard);
        ft1.setFromValue(from);
        ft1.setToValue(to);

        FadeTransition ft2 = new FadeTransition(Duration.millis(500), avatarBoard);
        ft2.setFromValue(from);
        ft2.setToValue(to);

        return new ParallelTransition(ft1, ft2);
    }

    public void register(ActionEvent actionEvent) throws ParseException, IOException {
        Audio.playClick5();
        checkSecurity(actionEvent);
        numberOfTimesTried++;
        numberOfBadTries++;
        if (checkSecurity) {
            return;
        }
        Alert alert = new Alert(Alert.AlertType.ERROR, "", ButtonType.OK);
        if (!indegtification.getText().equals(identificationLabel.getText())) {
            alert.setContentText("the code is wrong try again");
            alert.showAndWait();
        } else if (username.getText().equals("")) {
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
        } else if(username.getText().equalsIgnoreCase("boss")){
            alert.setContentText("the username cannot be case insensitive equal to boss");
            alert.showAndWait();
        } else if (((String) role.getValue()).equalsIgnoreCase("salesman") && company.getText().equals("")) {
            alert.setContentText("Company Field Must Not Be Empty");
            alert.showAndWait();
        } else if (((Label) (((AnchorPane) avatarPane.getChildren().get(0)).getChildren().get(12))).getText().equals("")) {
            alert.setContentText("please chose your avatar");
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
                (MenuHandler.getConnector()).clientToServer(message);
                String serverAnswer;
                serverAnswer = (MenuHandler.getConnector()).serverToClient();
                if (serverAnswer.equalsIgnoreCase("the username is already taken, try something else")) {
                    alert.setContentText("This Username Has Been Taken Please Try Something Else");
                } else {
                    numberOfTimesTried--;
                    alert.setAlertType(Alert.AlertType.CONFIRMATION);
                    alert.setContentText("Your Register Was Successful");
                    setAvatarInServer(username.getText());
                }
                alert.showAndWait();
                Parent root = FXMLLoader.load(getClass().getResource("/GUI/Register/Register.fxml"));
                Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
                stage.setScene(new Scene(root));
            }
        }
    }

    private void setAvatarInServer(String username) throws ParseException, IOException {
        String avatar = ((Label) (((AnchorPane) avatarPane.getChildren().get(0)).getChildren().get(12))).getText();
        String toServer = "set person image+" + username + "+" + avatar;
        MenuHandler.getConnector().clientToServer(toServer);
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

    public void back(MouseEvent mouseEvent) throws IOException, InterruptedException {
        Audio.playClick7();
        String path = MenuHandler.getLoginBackAddress();

        Parent root = FXMLLoader.load(getClass().getResource(path));
        Stage stage = (Stage) ((ImageView) mouseEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
    }

    public void exit(MouseEvent mouseEvent) {
        Audio.playClick3();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are You Sure You Want To Exit", ButtonType.YES, ButtonType.NO);
        alert.showAndWait();
        if (alert.getResult().equals(ButtonType.YES)) {
            System.exit(1989);
        }
    }

    public void reset(ActionEvent actionEvent) {
        identificationLabel.setText(String.valueOf(random.nextInt(10000)));
    }

    public void checkSecurity(ActionEvent actionEvent) throws IOException {
        if (numberOfTimesTried == 3 || numberOfBadTries == 5) {
            checkSecurity = true;
            Parent root = FXMLLoader.load(getClass().getResource("/GUI/Register/Lock/Lock.fxml"));
            Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            numberOfBadTries = 0;
            numberOfTimesTried = 0;
        }
    }

}
