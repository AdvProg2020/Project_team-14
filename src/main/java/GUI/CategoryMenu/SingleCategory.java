package GUI.CategoryMenu;

import GUI.Media.Audio;
import GUI.MenuHandler;
import Menus.Menu;
import Menus.Views.ViewCategoryMenu;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import javax.sound.sampled.AudioInputStream;
import java.io.IOException;
import java.text.ParseException;

public class SingleCategory {
    public Label parentCategory;
    public Label categoryName;
    public Label attribute;

    public void ChangeNameClicked(MouseEvent mouseEvent) throws ParseException, IOException {
        Audio.playClick5();
        TextInputDialog td = new TextInputDialog("enter the new name without space");
        td.setHeaderText("type here ...");
        td.showAndWait();
        String newName = td.getEditor().getText();
        if (!newName.contains(" ")) {
            MenuHandler.getServer().clientToServer("edit category name+" + MenuHandler.getUsername() + "+" +
                    MenuHandler.getSelectedCategory() + "+" + newName);
            String answer = MenuHandler.getServer().serverToClient();
            if (answer.equals("edit successful")) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Successful", ButtonType.OK);
                alert.showAndWait();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Unsuccessful", ButtonType.OK);
                alert.showAndWait();
            }
        }

    }

    public void MouseEntered(MouseEvent mouseEvent) {
        Stage stage = (Stage) ((Button) mouseEvent.getSource()).getScene().getWindow();
        stage.getScene().setCursor(Cursor.HAND);
    }

    public void MouseExited(MouseEvent mouseEvent) {
        Stage stage = (Stage) ((Button) mouseEvent.getSource()).getScene().getWindow();
        stage.getScene().setCursor(Cursor.DEFAULT);
    }

    public void BackClicked(MouseEvent mouseEvent) throws IOException {
        Audio.playClick4();
        Parent root = FXMLLoader.load(getClass().getResource("/GUI/CategoryMenu/CategoryMenu.fxml"));
        Stage stage = (Stage) ((Button) mouseEvent.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    public void initialize() {

    }
}
