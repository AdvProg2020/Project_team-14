package GUI.SalesmanProfile.ManageSale;

import GUI.MenuHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NewSaleController {
    public DatePicker startDate;
    public DatePicker endDate;
    public TextField percentage;
    public ListView<CheckBox> productList;
    public Label errors;
    public Button doneButton;

    @FXML
    public void initialize() throws ParseException, IOException {
        startDate.setPromptText(LocalDate.now().toString());
        endDate.setPromptText("2030-12-30");

        updateProductList();
    }

    private void updateProductList() throws ParseException, IOException {
        String toServer = "show my products" + "+" + MenuHandler.getUsername() + "+" + "filters:" + "+" +  "Confirmation" + "+" + "ACCEPTED";//..
        MenuHandler.getServer().clientToServer(toServer);
        String respond = MenuHandler.getServer().serverToClient();

        if (respond.equals("nothing found")) {
            errors.setText("You don't have any Confirmed product yet!!");
            doneButton.setDisable(true);
            return;
        }

        String[] productInfo = respond.split("\n");
        for (int i = 0; i < productInfo.length - 1; i++) {
            Matcher matcher = getMatcher(productInfo[i], "Name: (\\w+)");
            if (matcher.find()) {
                String productName = matcher.group(1);
                CheckBox item = new CheckBox(productName);
                productList.getItems().add(item);
            }
        }
    }

    private Matcher getMatcher(String command, String regex) {
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(command);
    }

    public void createSale(MouseEvent mouseEvent) throws IOException, ParseException {
        try {
            checkValidation();
        } catch (Exception e) {
            errors.setText(e.getMessage());
            return;
        }

        StringBuilder toServer = new StringBuilder("create new sale");
        toServer.append("+").append(MenuHandler.getUsername());
        toServer.append("+").append(startDate.getValue().toString());
        toServer.append("+").append(endDate.getValue().toString());
        toServer.append("+").append(percentage.getText());
        toServer.append("+Products:").append(chosenProduct());

        MenuHandler.getServer().clientToServer(toServer.toString());
        String respond = MenuHandler.getServer().serverToClient();

        if (respond.startsWith("ERRORS:")) {
            Alert alert = new Alert(Alert.AlertType.ERROR, respond, ButtonType.OK);
            ((Stage) alert.getDialogPane().getScene().getWindow()).setAlwaysOnTop(true);
            ((Stage) alert.getDialogPane().getScene().getWindow()).toFront();
            alert.showAndWait();
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, respond, ButtonType.OK);
        ((Stage) alert.getDialogPane().getScene().getWindow()).setAlwaysOnTop(true);
        ((Stage) alert.getDialogPane().getScene().getWindow()).toFront();
        alert.showAndWait();
        doneButton.getScene().getWindow().hide();
    }

    private String chosenProduct() {
        ArrayList<String> chosenProduct = new ArrayList<>();
        for (CheckBox item : productList.getItems()) {
            if (item.isSelected()) chosenProduct.add(item.getText());
        }
        return chosenProduct.toString();
    }

    private void checkValidation() throws Exception {
        if (startDate.getValue() == null | endDate.getValue() == null | percentage.getText().equals("")) throw new Exception("Please completely fill up the form!!");
        if (startDate.getValue().isAfter(endDate.getValue())) throw new Exception("End date must be after Start Date");
        if (chosenProduct().equals("[]")) throw new Exception("Please select at least one of your products!!");
    }

    public void back(MouseEvent mouseEvent) {
        doneButton.getScene().getWindow().hide();
    }




}
