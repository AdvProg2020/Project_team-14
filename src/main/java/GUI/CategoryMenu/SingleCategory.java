package GUI.CategoryMenu;

import GUI.MenuHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;

public class SingleCategory {
    public Label parentCategory;
    public Label categoryName;
    public Label attribute;

    public void ChangeNameClicked(MouseEvent mouseEvent) {

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
        Parent root = FXMLLoader.load(getClass().getResource("/GUI/CategoryMenu/CategoryMenu.fxml"));
        Stage stage = (Stage) ((Button) mouseEvent.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    public void initialize(){
        parentCategory.setText(Category.selected_category.getParentCategoryName());
        categoryName.setText(Category.selected_category.getCategoryName());
        attribute.setText(Category.selected_category.getAttribute());
    }
}
