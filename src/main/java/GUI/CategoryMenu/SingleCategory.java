package GUI.CategoryMenu;

import GUI.Media.Audio;
import GUI.MenuHandler;
import Menus.Menu;
import Menus.Views.ViewCategoryMenu;
import javafx.event.ActionEvent;
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
    public TableView productList;
    public TableColumn product;
    public TableView categoryList;
    public TableColumn category;
    public Button addProduct;
    public Button deleteProduct;
    public Button removeCategory;
    public TableView attributeList;
    public TableColumn attribute;

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


    public void BackClicked(MouseEvent mouseEvent) throws IOException {
        Audio.playClick4();
        Parent root = FXMLLoader.load(getClass().getResource("/GUI/CategoryMenu/CategoryMenu.fxml"));
        Stage stage = (Stage) ((Button) mouseEvent.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    public void initialize() throws ParseException, IOException {
        String categoryName = MenuHandler.getSelectedCategory();
        MenuHandler.getServer().clientToServer("view category+" + MenuHandler.getUsername() + "+" + MenuHandler.getSelectedCategory());
        String serverAnswer = MenuHandler.getServer().serverToClient();
        System.out.println(serverAnswer);
        this.categoryName.setText(categoryName);
    }

    public void removeCategory(ActionEvent actionEvent) {
    }

    public void deleteProduct(ActionEvent actionEvent) {
    }

    public void addProduct(ActionEvent actionEvent) {
    }
}
