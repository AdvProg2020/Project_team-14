package GUI.CategoryMenu;

import GUI.Media.Audio;
import GUI.MenuHandler;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
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
        MenuHandler.getServer().clientToServer("show categories+" + MenuHandler.getUsername());
        String serverAnswer = MenuHandler.getServer().serverToClient();
        for (String s : serverAnswer.split("\n")) {
            if (s.startsWith("Category")) {
                filter.getItems().add(s.split("\\s")[2]);
            }
        }
    }

    public void update() throws IOException, ParseException {
        String fatherCategory = "";
        for (Object o : filters.getChildren()) {
            if (o instanceof HBox) {
                Object o1 = ((HBox) o).getChildren().get(0);
                if (o1 instanceof Label) {
                    if (fatherCategory.equals("")) {
                        fatherCategory = ((Label) o1).getText();
                    } else {
                        fatherCategory += "," + ((Label) o1).getText();
                    }
                }
            }
        }
        if (fatherCategory.equals("")) {
            MenuHandler.getServer().clientToServer("show categories+" + MenuHandler.getUsername());
        } else {
            MenuHandler.getServer().clientToServer("show categories+" + MenuHandler.getUsername() + "+filters:" + "+fatherCategory+" + fatherCategory);
        }
        String serverAnswer = MenuHandler.getServer().serverToClient();
        categoryName.setCellValueFactory(new PropertyValueFactory<>("categoryName"));
        this.fatherCategory.setCellValueFactory(new PropertyValueFactory<>("parentCategory"));
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

    public void viewCategory(ActionEvent actionEvent) throws IOException {
        System.out.println("hi");
        if (categories.getSelectionModel().getSelectedItem() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Choose Category First", ButtonType.OK);
            alert.showAndWait();
        } else {
            Category category = categories.getSelectionModel().getSelectedItem();
            MenuHandler.setSelectedCategory(category.getCategoryName());
            MenuHandler.getStage().setScene(new Scene(FXMLLoader.load(getClass().getResource("/GUI/CategoryMenu/SingleCategory.fxml"))));
        }
    }
}
