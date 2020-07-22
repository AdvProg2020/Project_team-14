package GUI.ProfileLayout.Financial;

import GUI.Media.Audio;
import GUI.MenuHandler;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.text.ParseException;

public class Financial {
    public TextField wage;
    public TextField min;
    private int minAmount;
    private int wageAmount;

    public void reset(ActionEvent actionEvent) throws IOException, ParseException {
        initialize();
        wage.setText(Integer.toString(wageAmount));
        min.setText(Integer.toString(minAmount));
    }

    public void save(ActionEvent actionEvent) throws ParseException {
        Audio.playClick5();
        Alert alert = new Alert(Alert.AlertType.ERROR, "", ButtonType.OK);
        try {
            if (!min.getText().matches("\\d+")) {
                alert.setContentText("minimum credit should be an int");
                alert.showAndWait();
                return;
            } else {
                if (Integer.parseInt(min.getText()) >100000) {
                    alert.setContentText("minimum credit shouldn't be higher than 100000");
                    alert.showAndWait();
                    return;
                }
            }
            if (!wage.getText().matches("\\d+")) {
                alert.setContentText("wage percentage should be integer");
                alert.showAndWait();
                return;
            } else {
                if (Integer.parseInt(wage.getText()) >= 100) {
                    alert.setContentText("the wage should be an integer less than 100");
                    alert.showAndWait();
                    return;
                }
            }
            if (minAmount != Integer.parseInt(min.getText())) {
                MenuHandler.getConnector().clientToServer("set min credit+" + min.getText());
            }
            if (wageAmount != Integer.parseInt(wage.getText())) {
                MenuHandler.getConnector().clientToServer("set wage+" + wage.getText());
            }
        } catch (Exception e) {
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    public void refresh(ActionEvent actionEvent) {
        try {
            initialize();
        } catch (Exception ignored) { }
    }

    public void initialize() throws ParseException, IOException {
        MenuHandler.getConnector().clientToServer("get wage+");
        wageAmount = Integer.parseInt(MenuHandler.getConnector().serverToClient());
        MenuHandler.getConnector().clientToServer("get min credit+");
        minAmount = Integer.parseInt(MenuHandler.getConnector().serverToClient());
        wage.setText(Integer.toString(wageAmount));
        min.setText(Integer.toString(minAmount));
    }
}
