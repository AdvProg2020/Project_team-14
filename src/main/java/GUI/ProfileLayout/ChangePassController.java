package GUI.ProfileLayout;

import GUI.Media.Audio;
import GUI.MenuHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;

public class ChangePassController {
    public ArrayList<ImageView> imageViews = new ArrayList<>();
    public ArrayList<TextField> textFields = new ArrayList<>();
    public ArrayList<PasswordField> passFields = new ArrayList<>();
    public AnchorPane passPane;
    public Label result;
    public Button doneButton;

    @FXML
    public void initialize() {
        for (Node child : passPane.getChildren()) {
            if (child.getId().startsWith("pass")) passFields.add((PasswordField) child);
            if (child.getId().startsWith("text")) textFields.add((TextField) child);
            if (child instanceof ImageView) imageViews.add((ImageView) child);
        }
    }

    public void changePolicy(MouseEvent mouseEvent) {
        int boxNum = Integer.parseInt(((ImageView) mouseEvent.getSource()).getId().substring(3));

        TextField visibleText = textFields.get(boxNum - 1);
        PasswordField notVisibleText = passFields.get(boxNum - 1);
        ImageView icon = imageViews.get(boxNum - 1);

        if (!visibleText.isVisible()) {
            icon.setImage(new Image("file:src/main/resources/GUI/ProfileLayout/resources/hidePass.png"));
            visibleText.setText(notVisibleText.getText());
            visibleText.setVisible(true);
            notVisibleText.setVisible(false);
        } else {
            icon.setImage(new Image("file:src/main/resources/GUI/ProfileLayout/resources/showPass.png"));
            notVisibleText.setText(visibleText.getText());
            visibleText.setVisible(false);
            notVisibleText.setVisible(true);
        }
    }

    public void checkNewPass(KeyEvent keyEvent) {
        String newPass = textFields.get(1).isVisible() ? textFields.get(1).getText() : passFields.get(1).getText();
        String newPassAgain = ((TextField) keyEvent.getSource()).getText();

        if (!newPassAgain.equals(newPass)) {
            textFields.get(2).setStyle("-fx-border-color: red");
            passFields.get(2).setStyle("-fx-border-color: red");
            doneButton.setDisable(true);
        } else {
            textFields.get(2).setStyle("-fx-border-color: #1aef13");
            passFields.get(2).setStyle("-fx-border-color: #1AEF13");
            doneButton.setDisable(false);
        }
    }

    public void changePass(MouseEvent mouseEvent) throws ParseException, IOException {
        Audio.playClick5();
        StringBuilder toServer = new StringBuilder("change pass+" + MenuHandler.getUsername());
        String oldPass = textFields.get(0).isVisible() ? textFields.get(0).getText() : passFields.get(0).getText();
        String newPass = textFields.get(1).isVisible() ? textFields.get(0).getText() : passFields.get(1).getText();
        toServer.append("+").append(oldPass).append("+").append(newPass);
        MenuHandler.getConnector().clientToServer(toServer.toString());
        String respond = MenuHandler.getConnector().serverToClient();

        if (respond.startsWith("Error:")) {
            System.out.println();
            result.setText(respond.substring(6));
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Your password changed successfully", ButtonType.OK);
        ((Stage) alert.getDialogPane().getScene().getWindow()).setAlwaysOnTop(true);
        ((Stage) alert.getDialogPane().getScene().getWindow()).toFront();
        alert.showAndWait();
        MenuHandler.getConnector().clientToServer("change pass+" + MenuHandler.getUsername() + "+" + oldPass + "+" + newPass);
        passPane.getScene().getWindow().hide();

    }

    public void back(MouseEvent mouseEvent) {
        Audio.playClick5();
        ((Button) mouseEvent.getSource()).getScene().getWindow().hide();
    }
}
