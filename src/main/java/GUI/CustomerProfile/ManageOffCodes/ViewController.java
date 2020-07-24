package GUI.CustomerProfile.ManageOffCodes;

import GUI.Media.Audio;
import GUI.MenuHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.text.ParseException;

public class ViewController {
    public Label offCodeId;
    public Label startDate;
    public Label endDate;
    public Label percentage;
    public Label max;
    public Label remaining;

    @FXML
    public void initialize() throws ParseException, IOException {
        String toServer = "view offCode+" + MenuHandler.getUsername() + "+" + MenuHandler.getSeeingOffCode();
        MenuHandler.getConnector().clientToServer(toServer);
        String respond = MenuHandler.getConnector().serverToClient();

        offCodeId.setText(MenuHandler.getSeeingOffCode());

        String[] info = respond.split("\n");
        percentage.setText(drawOutInfoFromLine(info[1]));
        startDate.setText(drawOutInfoFromLine(info[2]));
        endDate.setText(drawOutInfoFromLine(info[3]));
        max.setText(drawOutInfoFromLine(info[4]));
        remaining.setText(drawOutInfoFromLine(info[5]));
    }

    private String drawOutInfoFromLine(String line) {
        int separator = line.indexOf(":");
        return line.substring(separator + 1);
    }

    public void back(MouseEvent mouseEvent) {
        Audio.playClick6();
        ((Button) mouseEvent.getSource()).getScene().getWindow().hide();
    }
}
