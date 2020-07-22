package GUI.CategoryMenu;

import GUI.Media.Audio;
import GUI.MenuHandler;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

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

    ObservableList<String> list1 = FXCollections.observableArrayList();
    ObservableList<String> list2 = FXCollections.observableArrayList();

    public void ChangeNameClicked(MouseEvent mouseEvent) throws ParseException, IOException {
        Audio.playClick5();
        TextInputDialog td = new TextInputDialog("enter the new name without space");
        td.setHeaderText("type here ...");
        td.showAndWait();
        String newName = td.getEditor().getText();
        if (!newName.contains(" ")) {
            MenuHandler.getConnector().clientToServer("edit category name+" + MenuHandler.getUsername() + "+" +
                    MenuHandler.getSelectedCategory() + "+" + newName);
            String answer = MenuHandler.getConnector().serverToClient();
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
        MenuHandler.getConnector().clientToServer("view category+" + MenuHandler.getUsername() + "+" + MenuHandler.getSelectedCategory());
        String serverAnswer = MenuHandler.getConnector().serverToClient();
        this.categoryName.setText(categoryName);
        if (serverAnswer.contains("The category doesn't")) {
            parentCategory.setText("None");
        }
        update();
        boolean b = false;
    }

    private void update() throws ParseException, IOException {
        MenuHandler.getConnector().clientToServer("view category+" + MenuHandler.getUsername() + "+" + MenuHandler.getSelectedCategory());
        String serverAnswer = MenuHandler.getConnector().serverToClient();
        boolean b = false;
        for (String s : serverAnswer.split("\n")) {
            if (s.equals("SubCategories: ")) {
                b = true;
                continue;
            }
            if (s.equals("Products: ")) {
                break;
            }
            list1.add(s);
        }
        b = false;
        for (String s : serverAnswer.split("\n")) {
            if (s.equals("Products: ")) {
                b = true;
                continue;
            }
            list2.add(s);
        }
        categoryList.getItems().addAll(list1);
        productList.getItems().addAll(list2);
    }

    public void removeCategory(ActionEvent actionEvent) {

    }

    public void deleteProduct(ActionEvent actionEvent) {

    }

    public void addProduct(ActionEvent actionEvent) {

    }
}
