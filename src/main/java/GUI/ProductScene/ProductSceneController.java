package GUI.ProductScene;

import GUI.MenuHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
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
            Parent registerLoginPopUp = FXMLLoader.load(getClass().getResource("/GUI/ProfileLayout/LoginRegisterPopup.fxml"));
            popup.getContent().addAll(registerLoginPopUp);
            ((Button) ((HBox) ((VBox) registerLoginPopUp).getChildren().get(1)).getChildren().get(0)).setOnAction(event -> {
                Parent root = null;
                try {
                    root = FXMLLoader.load(getClass().getResource("/GUI/Register/Register.fxml"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                stage.setScene(new Scene(root));
                MenuHandler.setLoginBackAddress("/GUI/ProductScene/ProductScene.fxml");
                popup.getScene().getWindow().hide();
            });
            popup.show(stage);
        }
    }

    public void initialize() {

    }
}
