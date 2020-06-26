package GUI.BossProfile.ManageOffCodes;

import GUI.Media.Audio;
import GUI.MenuHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

import javax.sound.sampled.AudioInputStream;
import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.ArrayList;

public class NewOffCodeController {
    public DatePicker startDate;
    public DatePicker endDate;
    public TextField percentage;
    public TextField max;
    public TextField frequency;
    public Label warning;
    public TextField wantedUsername;
    public ListView<String> allUsers;
    public ListView<String> addedUsers;
    public Label additionResult;

    @FXML
    public void initialize() throws IOException, ParseException {
        startDate.setPromptText(LocalDate.now().toString());
        endDate.setPromptText(LocalDate.parse("2025-12-30").toString());
        allUsers.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        addedUsers.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        updateAllUsers();
    }

    private void updateAllUsers() throws ParseException, IOException {
        String toServer = "get all offCodeAble user";
        MenuHandler.getServer().clientToServer(toServer);
        String respond = MenuHandler.getServer().serverToClient();
        String[] usernames = respond.split("\\+");
        for (int i = 1; i < usernames.length; i++) {
            allUsers.getItems().add(usernames[i]);
        }
    }

    public void doneCreating(MouseEvent mouseEvent) throws IOException, ParseException {
        Audio.playClick7();
        try {
            checkValidation();
        } catch (Exception e) {
            warning.setText(e.getMessage());
            return;
        }

        StringBuilder toServer = new StringBuilder("create new normal offCode");
        toServer.append("+").append(percentage.getText());
        toServer.append("+").append(max.getText());
        toServer.append("+").append(frequency.getText());
        toServer.append("+").append(startDate.getValue().toString());
        toServer.append("+").append(endDate.getValue().toString());
        toServer.append("+Users:").append(usernameCanUserIt());

        MenuHandler.getServer().clientToServer(toServer.toString());
        String respond = MenuHandler.getServer().serverToClient();

        if (respond.startsWith("ERROR")) {
            Alert alert = new Alert(Alert.AlertType.ERROR, respond, ButtonType.OK);
            ((Stage) alert.getDialogPane().getScene().getWindow()).setAlwaysOnTop(true);
            ((Stage) alert.getDialogPane().getScene().getWindow()).toFront();
            alert.showAndWait();
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, respond, ButtonType.OK);
        ((Stage) alert.getDialogPane().getScene().getWindow()).setAlwaysOnTop(true);
        ((Stage) alert.getDialogPane().getScene().getWindow()).toFront();
        alert.showAndWait();
        ((Button) mouseEvent.getSource()).getScene().getWindow().hide();
    }

    private String usernameCanUserIt() {
        ArrayList<String> usernames = new ArrayList<>();
        for (Object item : addedUsers.getItems()) {
            usernames.add((String) item);
        }
        return usernames.toString();
    }

    public void cancel(MouseEvent mouseEvent) {
        Audio.playClick6();
        ((Button) mouseEvent.getSource()).getScene().getWindow().hide();
    }

    private void checkValidation() throws Exception {
        if (startDate.getValue() == null | endDate.getValue() == null | percentage.getText().equals("") |
                max.getText().equals("") | frequency.getText().equals(""))
            throw new Exception("Error: Fill form completely");
        if (endDate.getValue().isBefore(startDate.getValue()))
            throw new Exception("Error: End Date must be after Start Date!!");
    }

    public void addToList(MouseEvent mouseEvent) {
        Audio.playClick6();
        String username = wantedUsername.getText();
        try {
            checkUsernameValidation(username, "add");
        } catch (Exception e) {
            additionResult.setText(e.getMessage());
            additionResult.setTextFill(Paint.valueOf("red"));
            return;
        }

        addedUsers.getItems().add(username);
        additionResult.setText("user added");
        additionResult.setTextFill(Paint.valueOf("green"));
    }

    public void deleteFromList(MouseEvent mouseEvent) {
        Audio.playClick3();
        String username = wantedUsername.getText();
        try {
            checkUsernameValidation(username, "delete");
        } catch (Exception e) {
            additionResult.setText(e.getMessage());
            additionResult.setTextFill(Paint.valueOf("red"));
            return;
        }

        addedUsers.getItems().remove(username);
        additionResult.setText("user deleted");
        additionResult.setTextFill(Paint.valueOf("green"));
    }

    public void setWantedUsername(MouseEvent mouseEvent) {
        ListView<String> listView = (ListView<String>) mouseEvent.getSource();
        String username = listView.getId().equalsIgnoreCase("allUsers") ? allUsers.getSelectionModel().getSelectedItem() :
                addedUsers.getSelectionModel().getSelectedItem();
        wantedUsername.setText(username);
    }

    private void checkUsernameValidation(String username, String type) throws Exception {
        if (username.equals("")) throw new Exception("you must chose a username!!");
        if (type.equals("add") & addedUsers.getItems().contains(username))
            throw new Exception("you've already added this user");
        if (type.equals("delete") & !addedUsers.getItems().contains(username))
            throw new Exception("this user is not in your list");
    }
}
