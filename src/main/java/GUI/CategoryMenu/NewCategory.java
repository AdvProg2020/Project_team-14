package GUI.CategoryMenu;

import GUI.MenuHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.text.ParseException;

public class NewCategory {
    public TextField parentCategory;
    public TextField name;
    public TextField attribute;

    public void closeNewCategory(MouseEvent mouseEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/GUI/CategoryMenu/CategoryMenu.fxml"));
        Stage stage = (Stage) ((Button) mouseEvent.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    public void createNewCategory(MouseEvent mouseEvent) throws ParseException, IOException {
        Alert alert = new Alert(Alert.AlertType.ERROR, "", ButtonType.OK);
        String parent_category = parentCategory.getText();
        String category_name = name.getText();
        String category_attribute = attribute.getText();
        if (category_name.contains("\\s+") || category_name.equals("")) {
            alert.setContentText("invalid category name");
            alert.showAndWait();
        } else if (parent_category.contains("\\s+") || parent_category.equals("")) {
            alert.setContentText("invalid parent category name");
            alert.showAndWait();
        } else {
            MenuHandler.getServer().clientToServer("create category+" + category_name + "+" + parent_category + "+" + category_attribute);
            String answer = MenuHandler.getServer().serverToClient();
            if (answer.equals("category name format is invalid")) {
                alert.setContentText("category name format is invalid");
                alert.showAndWait();
            } else if (answer.equals("father category name format is invalid")) {
                alert.setContentText("father category name format is invalid");
                alert.showAndWait();
            } else {
                alert.setContentText("category created successfully");
                alert.showAndWait();
            }
        }
    }

}
