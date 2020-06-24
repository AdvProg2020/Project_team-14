package GUI.ProfileLayout;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
}
