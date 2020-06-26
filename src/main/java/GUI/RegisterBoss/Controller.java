package GUI.RegisterBoss;

import GUI.Media.Audio;
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
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Controller {
    public TextField firstName;
    public TextField lastName;
    public PasswordField password;
    public TextField username;
    public TextField Email;
    public ChoiceBox role;
    public TextField telephone;
    public MediaView mediaView;
    public MediaView film;
    ObservableList<String> rolesIfHasBoss = FXCollections.observableArrayList("Boss");


    public void register(ActionEvent actionEvent) throws ParseException, IOException {
        Audio.playClick4();
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
        } else {
            if (checkUsernameFormat(username.getText())) {
                alert.setContentText("Username Should Consists Of Only Characters And Numbers And _ Without Space");
                alert.showAndWait();
            } else if (checkUsernameFormat(password.getText())) {
                alert.setContentText("Password Should Consists Of Only Characters And Numbers And _ Without Space");
                alert.showAndWait();
            } else if (checkNameFormat(firstName.getText())) {
                alert.setContentText("Name Should Consists Of Only Characters Like [A-Z][a-z]");
                alert.showAndWait();
            } else if (checkNameFormat(lastName.getText())) {
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
                (MenuHandler.getServer()).clientToServer(message);
                (MenuHandler.getServer()).serverToClient();
                alert.setAlertType(Alert.AlertType.CONFIRMATION);
                alert.setContentText("Your Register Was Successful");
                alert.showAndWait();
                Parent root = FXMLLoader.load(getClass().getResource("/GUI/ProductScene/ProductScene.fxml"));
                Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
                stage.setScene(new Scene(root));
            }
        }
    }

    private Matcher getMatcher(String regex, String command) {
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(command);
    }

    private boolean checkNameFormat(String name) {
        return !getMatcher("[a-zA-Z]+", name).matches();
    }

    private boolean checkUsernameFormat(String input) {
        return !getMatcher("(\\w+)", input).matches();
    }

    private boolean checkEmailFormat(String mail) {
        return getMatcher("((\\w||\\.)+)@(\\w+)\\.(com||ir||io||edu)", mail).matches();
    }

    private boolean checkTelephoneFormat(String number) {
        return getMatcher("0(\\d+)", number).matches() && number.length() == 11;
    }

    public void exit(MouseEvent mouseEvent) {
        Audio.playClick1();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are You Sure You Want To Exit", ButtonType.YES, ButtonType.NO);
        alert.showAndWait();
        if (alert.getResult().equals(ButtonType.YES)) {
            System.exit(23);
        }
    }

    public void waterfall() {
        String path = "src/main/java/GUI/Register/resources/mp4.mp4";
        Media media = new Media(new File(path).toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        film.setMediaPlayer(mediaPlayer);
        mediaPlayer.setAutoPlay(true);
        mediaPlayer.setOnEndOfMedia(() -> mediaPlayer.seek(Duration.ZERO));
    }

    public void initialize() {
        role.setItems(rolesIfHasBoss);
        String path = "src/main/java/GUI/Login/resources/mp4.mp4";
        Media media = new Media(new File(path).toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaView.setMediaPlayer(mediaPlayer);
        mediaPlayer.setAutoPlay(true);
        mediaPlayer.setOnEndOfMedia(() -> mediaPlayer.seek(Duration.ZERO));
        waterfall();
    }
}
