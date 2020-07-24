package GUI.CategoryMenu;

import GUI.Media.Audio;
import GUI.MenuHandler;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.text.ParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NewCategory {
    public TextField name;
    public TextField attribute;
    public ComboBox categories;
    public FlowPane attributes;

    ObservableList<String> category = FXCollections.observableArrayList();

    public void initialize() throws ParseException, IOException {
        MenuHandler.getConnector().clientToServer("show categories+" + MenuHandler.getUsername());
        String serverAnswer = MenuHandler.getConnector().serverToClient();
        for (String s : serverAnswer.split("\n")) {
            if (s.startsWith("Category")) {
                String category = s.split("\\s")[2];
                this.category.add(category);
            }
        }
        categories.getItems().addAll(category);
    }

    public void closeNewCategory(MouseEvent mouseEvent) {
        Audio.playClick1();
        ((Button) mouseEvent.getSource()).getScene().getWindow().hide();
    }

    public void createNewCategory(ActionEvent mouseEvent) throws ParseException, IOException {
        Audio.playClick5();
        Alert alert = new Alert(Alert.AlertType.ERROR, "", ButtonType.OK);
        ((Stage) alert.getDialogPane().getScene().getWindow()).setAlwaysOnTop(true);
        ((Stage) alert.getDialogPane().getScene().getWindow()).toFront();
        String parent_category = (String) categories.getValue();
        String category_name = name.getText();
        String category_attribute = "";
        for (Object object : attributes.getChildren()) {
            if (object instanceof HBox) {
                Object object1 = ((HBox) object).getChildren().get(0);
                if (object1 instanceof Label) {
                    if (category_attribute.equals("")) {
                        category_attribute = ((Label) object1).getText();
                    } else {
                        category_attribute += "," + ((Label) object1).getText();
                    }
                }
            }
        }
        if (category_attribute.equals("")) {
            category_attribute = "none";
        }
        if (parent_category == null) {
            parent_category = "rootCategory";
        }
        if (parent_category.equals("")) {
            parent_category = "none";
        }
        if (!checkNameFormat(category_name)) {
            alert.setContentText("Invalid Category Name");
        } else {
            MenuHandler.getConnector().clientToServer("create category+" + category_name + "+" + parent_category + "+" + category_attribute);
            String answer = MenuHandler.getConnector().serverToClient();
            if (answer.equals("a category with this name has already exists")) {
                alert.setContentText("A Category With This Name Has Already Exists");
            } else {
                alert.setAlertType(Alert.AlertType.INFORMATION);
                alert.setContentText("Category Created Successfully");
            }
        }
        alert.showAndWait();
    }

    private boolean checkNameFormat(String name) {
        return getMatcher("[a-zA-Z]+", name).matches();
    }

    private Matcher getMatcher(String regex, String command) {
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(command);
    }

    public void addAttribute(ActionEvent actionEvent) throws IOException {
        Alert alert = new Alert(Alert.AlertType.ERROR, null, ButtonType.OK);
        ((Stage) alert.getDialogPane().getScene().getWindow()).setAlwaysOnTop(true);
        ((Stage) alert.getDialogPane().getScene().getWindow()).toFront();
        if (attribute.getText().equals("")) {
            alert.setContentText("You Should Write Something In Attribute Section");
            alert.showAndWait();
        } else if (!checkNameFormat(attribute.getText())) {
            alert.setContentText("Wrong Attribute Format");
            alert.showAndWait();
        } else {
            String s = attribute.getText();
            try {
                checkExistenceOfFilter(s);
            } catch (Exception e) {
                return;
            }
            for (Object object : attributes.getChildren()) {
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
                attributes.getChildren().remove(item);
            });
            attributes.getChildren().add(item);
        }
    }

    private void checkExistenceOfFilter(String s) throws Exception {
        for (Node child : attributes.getChildren()) {
            Label prevFactor = (Label) ((HBox) child).getChildren().get(0);
            if (prevFactor.getText().equals(attributes)) throw new Exception("filter exist");
        }
    }
}
