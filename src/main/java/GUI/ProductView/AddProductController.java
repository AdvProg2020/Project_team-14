package GUI.ProductView;

import GUI.Media.Audio;
import GUI.MenuHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Popup;

import java.io.IOException;
import java.text.ParseException;

public class AddProductController {
    public Button sendButton;
    public CheckBox accept;
    public TextField count;
    public TextField price;
    public Label text;

    public void initialize() {
        text.setVisible(false);
        sendButton.setDisable(true);
    }

    public void sendRequest(ActionEvent actionEvent) throws IOException, ParseException {
        Audio.playClick4();
        if (price.getText().matches("\\d+") && price.getText().length() <= 8) {
            if (count.getText().matches("\\d+") && count.getText().length() <= 5) {
                MenuHandler.getServer().clientToServer("add product+" + MenuHandler.getUsername() + "+" +
                        MenuHandler.getProductID() + "+" + count.getText() + "+" + price.getText());
                Popup popup = (Popup) ((Button) actionEvent.getSource()).getScene().getWindow();
                popup.hide();
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Your Request Has Submitted", ButtonType.OK);
                alert.showAndWait();
                Parent root = FXMLLoader.load(getClass().getResource("/GUI/ProductView/ProductViewLayout.fxml"));
                MenuHandler.getStage().setScene(new Scene(root));
            } else {
                text.setVisible(true);
                text.setText("Count Is Wrong!!");
            }
        } else {
            text.setVisible(true);
            text.setText("Price Is Wrong!!");
        }
    }

    public void exitPopup(ActionEvent actionEvent) throws IOException {
        Audio.playClick6();
        Popup popup = (Popup) ((Button) actionEvent.getSource()).getScene().getWindow();
        popup.hide();
        Parent root = FXMLLoader.load(getClass().getResource("/GUI/ProductView/ProductViewLayout.fxml"));
        MenuHandler.getStage().setScene(new Scene(root));
    }

    public void buttonDrop(ActionEvent actionEvent) throws IOException {
        if (accept.isSelected()) {
            sendButton.setDisable(false);
        } else {
            sendButton.setDisable(true);
        }
    }
}
