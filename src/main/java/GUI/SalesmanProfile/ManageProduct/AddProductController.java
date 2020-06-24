package GUI.SalesmanProfile.ManageProduct;

import GUI.MenuHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AddProductController {
    public ImageView img;
    public VBox pane;
    public TextField productName;
    public TextField brand;
    public TextArea description;
    public TextField price;
    public Spinner count;
    private String path;

    public void initialize() {
        SpinnerValueFactory<Integer> count = new SpinnerValueFactory.IntegerSpinnerValueFactory
                (0, 100000, 0);
        this.count.setValueFactory(count);
    }

    public void uploadImg(MouseEvent mouseEvent) {
        Stage stage = (Stage) pane.getScene().getWindow();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Upload Product Picture");
        File chosenFile = fileChooser.showOpenDialog(stage);
        if (chosenFile == null) return;
        img.setImage(new Image(String.valueOf(chosenFile.toURI())));
        path = String.valueOf(chosenFile.toURI());
    }

    private void reset(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/GUI/SalesmanProfile/ManageProduct/NewProductLayout.fxml"));
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    public void newProduct(ActionEvent actionEvent) throws IOException, ParseException {
        Alert alert = new Alert(Alert.AlertType.ERROR, "", ButtonType.OK);
        if (productName.getText().equalsIgnoreCase("")) {
            alert.setContentText("You Should Choose A Name For Product");
            alert.showAndWait();
            reset(actionEvent);
        } else if (brand.equals("")) {
            alert.setContentText("You Should Choose A Brand For Product");
            alert.showAndWait();
            reset(actionEvent);
        } else if (description.equals("")) {
            alert.setContentText("You Should Write A Description For Product");
            alert.showAndWait();
            reset(actionEvent);
        } else if (price.equals("")) {
            alert.setContentText("You Should Choose A Price For Product");
            alert.showAndWait();
            reset(actionEvent);
        } else {
            if (!checkProductNameFormat(productName.getText())) {
                alert.setContentText("Product Name Format Is Invalid");
                alert.showAndWait();
                reset(actionEvent);
            }
            if (!checkNameFormat(brand.getText())) {
                alert.setContentText("Product Brand Format Is Invalid");
                alert.showAndWait();
                reset(actionEvent);
            }
            if (!price.getText().matches("\\d+") || ((String) price.getText()).length() > 8) {
                alert.setContentText("You Should Choose A Price");
                alert.showAndWait();
                reset(actionEvent);
            }
            if (!checkDescriptionFormat(description.getText())) {
                alert.setContentText("Description Format Is Invalid");
                alert.showAndWait();
                reset(actionEvent);
            }
            MenuHandler.getServer().clientToServer("create product+" + MenuHandler.getUsername() + "+" +
                    productName.getText() + "+" + brand.getText() + "+" + description.getText() + "+" + price.getText()
                    + "+" + count.getValue());
            String serverAnswer = MenuHandler.getServer().serverToClient();
            System.out.println(serverAnswer);
            if (serverAnswer.equals("product created")) {
                alert.setAlertType(Alert.AlertType.INFORMATION);
                alert.setContentText("Product Created");
            } else {
                alert.setContentText(serverAnswer);
            }
            alert.showAndWait();
            reset(actionEvent);
        }
    }

    public void back(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/GUI/SalesmanProfile/SalesmanProfileLayout.fxml"));
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    private boolean checkProductNameFormat(String input) {
        return getMatcher("([\\d\\w_\\-,\\s])+", input).matches();
    }

    private boolean checkDescriptionFormat(String description) {
        return getMatcher("[\\w\\s\\.]+", description).matches();
    }

    private boolean checkNameFormat(String name) {
        return getMatcher("[a-zA-Z]+", name).matches();
    }

    private Matcher getMatcher(String regex, String command) {
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(command);
    }
}
