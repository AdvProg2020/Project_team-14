package GUI.ProfileLayout.Financial;

import GUI.MenuHandler;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Menu;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.text.ParseException;

public class Financial {
    public TextField wage;
    public TextField min;
    public TextField balance;
    private int minAmount;
    private int wageAmount;
    private int balanceAmount;

    public void reset(ActionEvent actionEvent) {
        wage.setText(Integer.toString(wageAmount));
        min.setText(Integer.toString(minAmount));
        balance.setText(Integer.toString(balanceAmount));
    }

    public void save(ActionEvent actionEvent) throws ParseException {
        Alert alert = new Alert(Alert.AlertType.ERROR, "", ButtonType.OK);
        if (!min.getText().matches("\\d+")) {
            alert.setContentText("minimum credit should be an int");
            return;
        } else {
            if (Integer.parseInt(min.getText()) < 100000) {
                alert.setContentText("minimum credit shouldn't be higher than 100000");
                return;
            }
        }
        if (!wage.getText().matches("\\d+")) {
            alert.setContentText("wage percentage should be integer");
            return;
        } else {
            if (Integer.parseInt(min.getText()) >= 100) {
                alert.setContentText("the wage should be an integer less than 100");
                return;
            }
        }
        if (minAmount != Integer.parseInt(min.getText())) {
            MenuHandler.getServer().clientToServer("set wage+" + min.getText());
        }
        if (wageAmount != Integer.parseInt(wage.getText())){
            MenuHandler.getServer().clientToServer("set min credit+" + min.getText());
        }
    }

    public void refresh(ActionEvent actionEvent) {
        try {
            initialize();
        } catch (Exception ignored) {
        }
    }

    public void initialize() throws ParseException, IOException {
        MenuHandler.getServer().clientToServer("get wage+");
        wageAmount = Integer.parseInt(MenuHandler.getServer().serverToClient());
        MenuHandler.getServer().clientToServer("get min credit+");
        minAmount = Integer.parseInt(MenuHandler.getServer().serverToClient());
        MenuHandler.getServer().clientToServer("showBalance+BOSS");
        balanceAmount = Integer.parseInt(MenuHandler.getServer().serverToClient().substring("Your Balance is : ".length()));
        wage.setText(Integer.toString(wageAmount));
        min.setText(Integer.toString(minAmount));
        balance.setText(Integer.toString(balanceAmount));
    }
}
