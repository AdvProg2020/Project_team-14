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
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;

public class ViewOffCodeController {
    public DatePicker startDate;
    public DatePicker endDate;
    public TextField percentage;
    public TextField max;
    public TextField frequency;
    public ListView<String> usersList;
    public Label changeResult;
    //initial variable
    public String initStartDate;
    public String initEndDate;
    public String initPercentage;
    public String initMax;

    @FXML
    public void initialize() throws IOException, ParseException {
        String toServer = "view offCode" + "+" + MenuHandler.getUsername() + "+" + MenuHandler.getSeeingOffCode();
        MenuHandler.getServer().clientToServer(toServer);
        String respond = MenuHandler.getServer().serverToClient();

        setInitialValue(respond.split("\n"));
    }

    private void setInitialValue(String[] infoLines) {
        startDate.setValue(LocalDate.parse(getInfoFromLine(infoLines[1])));
        endDate.setValue(LocalDate.parse(getInfoFromLine(infoLines[2])));
        percentage.setText(getInfoFromLine(infoLines[0]));
        max.setText(getInfoFromLine(infoLines[3]));
        frequency.setText(getInfoFromLine(infoLines[4]));
        for (int i = 6; i < infoLines.length; i++) {
            usersList.getItems().add(infoLines[i]);
        }

        initStartDate = startDate.getValue().toString();
        initEndDate = endDate.getValue().toString();
        initPercentage = percentage.getText();
        initMax = max.getText();
    }

    private String getInfoFromLine(String line) {
        int separator = line.indexOf(":");
        return line.substring(separator + 1);
    }

    public void back(MouseEvent mouseEvent) {
        usersList.getScene().getWindow().hide();
        Audio.playClick1();
    }

    public void saveChanges(MouseEvent mouseEvent) throws ParseException, IOException {
        Audio.playClick6();
        try {
            isChangeValid();
        } catch (Exception e) {
            changeResult.setText(e.getMessage());
            changeResult.setTextFill(Paint.valueOf("red"));
            return;
        }

        StringBuilder respond = new StringBuilder();
        if (!startDate.getValue().toString().equals(initStartDate)) {
            String toServer = "edit offCode" + "+" + MenuHandler.getSeeingOffCode() + "+" + "start date" + "+" + startDate.getValue().toString();
            MenuHandler.getServer().clientToServer(toServer);
            respond.append(MenuHandler.getServer().serverToClient()).append("\n");
        }
        if (!endDate.getValue().toString().equals(initEndDate)) {
            String toServer = "edit offCode" + "+" + MenuHandler.getSeeingOffCode() + "+" + "end date" + "+" + endDate.getValue().toString();
            MenuHandler.getServer().clientToServer(toServer);
            respond.append(MenuHandler.getServer().serverToClient()).append("\n");
        }
        if (!percentage.getText().equals(initPercentage)) {
            String toServer = "edit offCode" + "+" + MenuHandler.getSeeingOffCode() + "+" + "percentage" + "+" + percentage.getText();
            MenuHandler.getServer().clientToServer(toServer);
            respond.append(MenuHandler.getServer().serverToClient()).append("\n");
        }
        if (!max.getText().equals(initMax)) {
            String toServer = "edit offCode" + "+" + MenuHandler.getSeeingOffCode() + "+" + "ceiling" + "+" + max.getText();
            MenuHandler.getServer().clientToServer(toServer);
            respond.append(MenuHandler.getServer().serverToClient()).append("\n");
        }

        if (respond.toString().startsWith("ERRORS")) {
            Alert alert = new Alert(Alert.AlertType.ERROR, respond.toString(), ButtonType.OK);
            alert.showAndWait();
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, respond.toString(), ButtonType.OK);
        ((Stage) alert.getDialogPane().getScene().getWindow()).setAlwaysOnTop(true);
        ((Stage) alert.getDialogPane().getScene().getWindow()).toFront();
        alert.showAndWait();
        usersList.getScene().getWindow().hide();
    }

    private void isChangeValid() throws Exception {
        if (startDate.getValue().toString().equals(initStartDate) & endDate.getValue().toString().equals(initEndDate) &
                percentage.getText().equals(initPercentage) & max.getText().equals(initMax)) throw new Exception("You haven't changed anything");
        if (endDate.getValue().isBefore(startDate.getValue())) throw new Exception("end date must be AFTER start date");
    }
}
