package GUI.ProfileLayout;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class ProfileLayoutController {
    public Pane pane;

    public void initialize() throws IOException {
        pane.getChildren().add(FXMLLoader.load(getClass().getResource("/GUI/ProfileLayout/PersonalInfoLayout.fxml")));
    }
}
