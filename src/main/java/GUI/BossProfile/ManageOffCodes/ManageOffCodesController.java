package GUI.BossProfile.ManageOffCodes;

import GUI.MenuHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Popup;
import javafx.stage.Stage;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;

public class ManageOffCodesController {
    public ComboBox<String> sortList;
    public ComboBox<String> filterList;
    public FlowPane chosenFilterItem;
    public VBox offCodeList;
    private ArrayList<String> chosenFilter = new ArrayList<>();
    private String sortType = "ASCENDING";



    @FXML
    public void initialize() throws IOException, ParseException {
        sortList.getItems().addAll("Default", "PERCENTAGE", "END_DATE", "START_DATE");
        updateContent();
    }

    private void updateContent() throws ParseException, IOException {
        StringBuilder toServer = new StringBuilder("show offCodes").append("+").append(MenuHandler.getUsername());
        setFilterFactor(toServer);
        setSortFactor(toServer);

        MenuHandler.getServer().clientToServer(toServer.toString());
        String respond = MenuHandler.getServer().serverToClient();

        offCodeList.getChildren().clear();
        makeOffCodeList(respond);
    }

    private void setFilterFactor(StringBuilder toServer) {

    }

    private void setSortFactor(StringBuilder toServer) {
        if (sortList.getValue() == null) return;
        if (!sortList.getValue().equals("default")) {
            toServer.append("+sort:+").append(sortList.getValue()).append("+").append(sortType);
        }
    }

    private void makeOffCodeList(String ans) throws IOException {
        if (ans.equals("nothing found")) {
            Image img = new Image("file:src/main/java/GUI/BossProfile/ManageOffCodes/resources/notFound.png");
            ImageView imageView = new ImageView(img);
            imageView.setFitWidth(250.0);
            imageView.setFitHeight(200.0);
            offCodeList.getChildren().add(imageView);
            offCodeList.setAlignment(Pos.CENTER);
            return;
        }
        offCodeList.setAlignment(Pos.TOP_CENTER);
        String[] ansLines = ans.split("\n");
        for (int i = 1; i < ansLines.length; i++) {
            Parent root = FXMLLoader.load(getClass().getResource("OffCodeItem.fxml"));
            Label offCodeId = (Label) ((HBox) root).getChildren().get(1);
            Label percentage = (Label) ((HBox) root).getChildren().get(3);

            offCodeId.setText(ansLines[i].split("\\+")[0]);
            percentage.setText(ansLines[i].split("\\+")[1]);
            offCodeList.getChildren().add(root);

            root.setOnMouseClicked(this::viewOffCode);
        }
    }

    private void viewOffCode(MouseEvent mouseEvent) {
        HBox hBox = (HBox) mouseEvent.getSource();
        MenuHandler.setSeeingOffCode(((Label) hBox.getChildren().get(1)).getText());
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/GUI/BossProfile/ManageOffCodes/ViewOffCodePopup.fxml"));
            Popup popup = new Popup();
            popup.getContent().add(root);
            popup.setOnHiding(e -> {
                try {
                    updateContent();
                } catch (ParseException ex) {
                    ex.printStackTrace();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            });
            popup.show((Stage) offCodeList.getScene().getWindow());
        } catch (IOException e) {
            System.err.println("exception in loading ViewOffCodePopup.fxml ManageOffCodesController class");
            e.printStackTrace();
        }

    }

    public void changeSortType(MouseEvent mouseEvent) throws IOException, ParseException {
        if (sortType.equals("ASCENDING")) sortType = "DESCENDING";
        else sortType = "ASCENDING";

        updateContent();
    }


    public void createNewOffCode(MouseEvent mouseEvent) throws IOException {
        Stage parent = (Stage) ((Button) mouseEvent.getSource()).getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/GUI/BossProfile/ManageOffCodes/NewOffCodePopup.fxml"));
        Popup popup = new Popup();
        popup.getContent().add(root);
        popup.setOnHiding(e -> {
            try {
                updateContent();
            } catch (ParseException ex) {
                ex.printStackTrace();
            } catch (IOException ex) {
                System.err.println("error in updating content after closing popup");
            }
        });
        popup.show(parent);
    }
}
