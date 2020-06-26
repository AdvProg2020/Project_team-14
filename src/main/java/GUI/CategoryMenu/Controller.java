package GUI.CategoryMenu;

import GUI.Media.Audio;
import GUI.MenuHandler;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.text.ParseException;

public class Controller {

    public TableView<Category> categories;
    public TableColumn<Category, String> categoryName;
    public TableColumn<Category, String> categoryAttribute;
    public TableColumn<Category, String> fatherCategory;

    ObservableList<Category> list = FXCollections.observableArrayList();

    public void initialize() throws ParseException, IOException {
        update();
    }

    public void update() throws IOException, ParseException {
        MenuHandler.getServer().clientToServer("show categories+" + MenuHandler.getUsername());
        String serverAnswer = MenuHandler.getServer().serverToClient();
        System.out.println(serverAnswer);
        categoryName.setCellValueFactory(new PropertyValueFactory<>("categoryName"));
        fatherCategory.setCellValueFactory(new PropertyValueFactory<>("parentCategory"));
        categoryAttribute.setCellValueFactory(new PropertyValueFactory<>("attribute"));
        list = FXCollections.observableArrayList();
        for (String s : serverAnswer.split("\n")) {
            if (s.startsWith("Category")) {
                list.add(new Category(s.split("\\s")[2], s.split("\\s")[8], s.split("\\s")[5]));
            }
        }
        categories.setItems(list);
    }

    public void NewCategoryClicked(MouseEvent mouseEvent) throws IOException {
        Audio.playClick4();
        Parent root = FXMLLoader.load(getClass().getResource("/GUI/CategoryMenu/NewCategory.fxml"));
        Stage stage = (Stage) ((Button) mouseEvent.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    public void newCategory(ActionEvent actionEvent) {

    }

    public void updateFilter(ActionEvent actionEvent) {

    }
}
