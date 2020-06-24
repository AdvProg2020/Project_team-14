package GUI.ProfileLayout;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.util.ArrayList;

public class ChangePassController {
    public ArrayList<ImageView> imageViews = new ArrayList<>();
    public ArrayList<TextField> textFields = new ArrayList<>();
    public ArrayList<PasswordField> passFields = new ArrayList<>();
    public AnchorPane passPane;

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

        System.out.println(textFields);
        System.out.println(passFields);

        TextField visibleText = textFields.get(boxNum - 1);
        PasswordField notVisibleText = passFields.get(boxNum - 1);
        ImageView icon = imageViews.get(boxNum - 1);

        if (!visibleText.isVisible()) {
            icon.setImage(new Image("file:src/ProfileLayout/resources/hidePass.png"));
            visibleText.setText(notVisibleText.getText());
            visibleText.setVisible(true);
            notVisibleText.setVisible(false);
        } else {
            icon.setImage(new Image("file:src/ProfileLayout/resources/showPass.png"));
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
        } else {
            textFields.get(2).setStyle("-fx-border-color: #1aef13");
            passFields.get(2).setStyle("-fx-border-color: #1AEF13");
        }
    }
}
