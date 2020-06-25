package GUI.CategoryMenu;

import GUI.Media.Audio;
import GUI.MenuHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class Controller {

    public void initialize(){
        //MenuHandler.getServer().clientToServer();
    }


    public void MouseEntered(MouseEvent mouseEvent) {
        Stage stage = (Stage) ((Button) mouseEvent.getSource()).getScene().getWindow();
        stage.getScene().setCursor(Cursor.HAND);
    }

    public void MouseExited(MouseEvent mouseEvent) {
        Stage stage = (Stage) ((Button) mouseEvent.getSource()).getScene().getWindow();
        stage.getScene().setCursor(Cursor.DEFAULT);
    }

    public void NewCategoryClicked(MouseEvent mouseEvent) throws IOException {
        Audio.playClick4();
        Parent root = FXMLLoader.load(getClass().getResource("/GUI/CategoryMenu/NewCategory.fxml"));
        Stage stage = (Stage) ((Button) mouseEvent.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
    }
}
