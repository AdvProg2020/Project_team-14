package GUI.ProductScene;

import GUI.MenuHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Popup;
import javafx.stage.Stage;

import java.io.IOException;

public class ProductSceneController {

    protected ImageView accountMenuButton;

    public void openPopUp(MouseEvent mouseEvent) throws IOException {
        Popup popup = new Popup();
        Stage stage = (Stage) ((ImageView) mouseEvent.getSource()).getScene().getWindow();
        if (MenuHandler.isIsUserLogin()) {

        } else {
            Parent registerLoginPopUp = FXMLLoader.load(getClass().getResource("/GUI/ProfileLayout//LoginRegisterPopup.fxml"));
            popup.getContent().addAll(registerLoginPopUp);
            popup.show(stage);
        }
    }

    public void initialize() {

    }
}
