package GUI.CategoryMenu;

import GUI.Media.Audio;
import GUI.MenuHandler;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.text.ParseException;

public class Controller {

    public TableView<Category> categories;
    public TableColumn<Category, String> categoryName;
    public TableColumn<Category, String> categoryAttribute;
    public TableColumn<Category, String> fatherCategory;
    public ComboBox filter;
    public FlowPane filters;

    ObservableList<Category> list = FXCollections.observableArrayList();
    ObservableList<String> categoryList = FXCollections.observableArrayList();

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
        categoryList = FXCollections.observableArrayList();
        for (String s : serverAnswer.split("\n")) {
            if (s.startsWith("Category")) {
                list.add(new Category(s.split("\\s")[2], s.split("\\s")[8], s.split("\\s")[5]));
                categoryList.add("Parent Category:" + s.split("\\s")[2]);
            }
        }
        filter.setItems(categoryList);
        categories.setItems(list);
    }

    public void NewCategoryClicked(MouseEvent mouseEvent) throws IOException {
        Audio.playClick4();
        Parent root = FXMLLoader.load(getClass().getResource("/GUI/CategoryMenu/NewCategory.fxml"));
        Stage stage = (Stage) ((Button) mouseEvent.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    public void newCategory(ActionEvent actionEvent) throws IOException {
        Audio.playClick4();
        Parent root = FXMLLoader.load(getClass().getResource("/GUI/CategoryMenu/NewCategory.fxml"));
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    public void updateFilter(ActionEvent actionEvent) throws IOException, ParseException {
        String s = (String) filter.getValue();
        try {
            checkExistenceOfFilter(s);
        } catch (Exception e) {
            return;
        }
        for (Object object : filters.getChildren()) {
            if (object instanceof HBox) {
                Object object1 = ((HBox) object).getChildren().get(0);
                if (object1 instanceof Label) {
                    if (((Label) object1).getText().equals(s)) return;
                }
            }
        }
        Parent item = FXMLLoader.load(getClass().getResource("/GUI/MainTheme/chosenItemLayout.fxml"));
        Label label = (Label) ((HBox) item).getChildren().get(0);
        label.setText(s);
        Button button = (Button) ((HBox) item).getChildren().get(1);
        button.setOnAction(actionEvent2 -> {
            filters.getChildren().remove(item);
            try {
                update();
            } catch (ParseException | IOException e) {
                e.printStackTrace();
            }
        });
        filters.getChildren().add(item);
        update();
    }

    private void checkExistenceOfFilter(String filterFactor) throws Exception {
        for (Node child : filters.getChildren()) {
            Label prevFactor = (Label) ((HBox) child).getChildren().get(0);
            if (prevFactor.getText().equals(filterFactor)) throw new Exception("filter exist");
        }
    }

    public void MouseEntered(MouseEvent mouseEvent) {

    }

    public void MouseExited(MouseEvent mouseEvent) {

    }
}
