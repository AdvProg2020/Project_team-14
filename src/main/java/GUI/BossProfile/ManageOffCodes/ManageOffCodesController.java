package GUI.BossProfile.ManageOffCodes;

import GUI.MenuHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

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

        offCodeList.getChildren().removeAll();
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
        String[] ansLines = ans.split("\n");
        for (int i = 2; i < ansLines.length; i++) {
            Parent root = FXMLLoader.load(getClass().getResource("OffCodeItem.fxml"));
            Label offCodeId = (Label) ((HBox) root).getChildren().get(1);
            Label percentage = (Label) ((HBox) root).getChildren().get(3);

            offCodeId.setText(ansLines[i].split("\\s")[0]);
            percentage.setText(ansLines[i].split("\\s")[1]);
            offCodeList.getChildren().add(root);
        }
    }

    public void changeSortType(MouseEvent mouseEvent) throws IOException, ParseException {
        if (sortType.equals("ASCENDING")) sortType = "DESCENDING";
        else sortType = "ASCENDING";

        updateContent();
    }
}
