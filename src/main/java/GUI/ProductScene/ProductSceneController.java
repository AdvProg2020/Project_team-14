package GUI.ProductScene;

import GUI.MenuHandler;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;

public class ProductSceneController {

    public MediaView film;
    public TilePane products;
    public ComboBox sortFactor;
    public ComboBox filter;
    public FlowPane filterList;
    @FXML
    protected ImageView accountMenuButton;

    ObservableList<String> list = FXCollections.observableArrayList("Confirmation:ACCEPTED", "Confirmation:DENIED",
            "Confirmation:CHECKING", "Available", "Not Available");

    public void openPopUp(MouseEvent mouseEvent) throws IOException {
        Popup popup = new Popup();
        Stage stage = (Stage) ((ImageView) mouseEvent.getSource()).getScene().getWindow();
        MenuHandler.setStage(stage);
        if (MenuHandler.isIsUserLogin()) {
            Parent registerLoginPopUp = FXMLLoader.load(getClass().getResource("/GUI/MainMenuPopups/ProfilePopup.fxml"));
            popup.getContent().addAll(registerLoginPopUp);
            ((Label) ((VBox) registerLoginPopUp).getChildren().get(1)).setText(MenuHandler.getUsername());
            setProfileImageInProfilePopup(registerLoginPopUp);
            ((Button) ((HBox) ((VBox) registerLoginPopUp).getChildren().get(2)).getChildren().get(0)).setOnAction(event -> {
                Parent root = null;
                try {
                    if (MenuHandler.getUserType().equalsIgnoreCase("boss")) {
                        root = FXMLLoader.load(getClass().getResource("/GUI/ProfileLayout/ProfileLayout.fxml"));
                    } else if (MenuHandler.getUserType().equalsIgnoreCase("salesman")) {
                        root = FXMLLoader.load(getClass().getResource("/GUI/SalesmanProfile/SalesmanProfileLayout.fxml"));
                    } else {
                        root = FXMLLoader.load(getClass().getResource("/GUI/CustomerProfile/CustomerProfileLayout.fxml"));
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

                stage.setScene(new Scene(root));
                popup.getScene().getWindow().hide();
            });
            ((Button) ((HBox) ((VBox) registerLoginPopUp).getChildren().get(2)).getChildren().get(1)).setOnAction(event -> {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are You Sure You Want To Logout", ButtonType.YES, ButtonType.NO);
                popup.getScene().getWindow().hide();
                alert.showAndWait();
                if (alert.getResult().equals(ButtonType.YES)) {
                    try {
                        MenuHandler.getConnector().clientToServer("logout+" + MenuHandler.getUsername());
                        String serverAnswer = MenuHandler.getConnector().serverToClient();
                        if (serverAnswer.equalsIgnoreCase("logout successful")) {
                            Alert alert1 = new Alert(Alert.AlertType.INFORMATION, "Logout Successful", ButtonType.OK);
                            alert1.showAndWait();
                            MenuHandler.setIsUserLogin(false);
                            MenuHandler.setUsername(null);
                            MenuHandler.setUserType(null);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                Parent root = null;
                try {
                    root = FXMLLoader.load(getClass().getResource("/GUI/ProductScene/ProductScene.fxml"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                stage.setScene(new Scene(root));
                MenuHandler.setLoginBackAddress("/GUI/ProductScene/ProductScene.fxml");
                popup.getScene().getWindow().hide();
            });
            popup.show(stage);
        } else {
            Parent registerLoginPopUp = FXMLLoader.load(getClass().getResource("/GUI/MainMenuPopups/LoginRegisterPopup.fxml"));
            popup.getContent().addAll(registerLoginPopUp);
            ((Button) ((HBox) ((VBox) registerLoginPopUp).getChildren().get(1)).getChildren().get(0)).setOnAction(event -> {
                Parent root = null;
                try {
                    root = FXMLLoader.load(getClass().getResource("/GUI/Register/Register.fxml"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                stage.setScene(new Scene(root));
                MenuHandler.setLoginBackAddress("/GUI/ProductScene/ProductScene.fxml");
                popup.getScene().getWindow().hide();
            });
            ((Button) ((HBox) ((VBox) registerLoginPopUp).getChildren().get(1)).getChildren().get(1)).setOnAction(event -> {
                Parent root = null;
                try {
                    root = FXMLLoader.load(getClass().getResource("/GUI/Login/Login.fxml"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                stage.setScene(new Scene(root));
                MenuHandler.setLoginBackAddress("/GUI/ProductScene/ProductScene.fxml");
                popup.getScene().getWindow().hide();
            });
            popup.show(stage);
        }
    }

    private void setProfileImageInProfilePopup(Parent root) throws FileNotFoundException {
        if (!MenuHandler.getUserAvatar().equals("no image found")) {
            /*String path = "src/main/java/GUI/Register/resources/";
            String avatar = MenuHandler.getUserAvatar() + ".png";
            FileInputStream imageStream = new FileInputStream(path + avatar);
            Image image = new Image(imageStream);
            ImageView profileImage = (ImageView) ((VBox) root).getChildren().get(0);
            profileImage.setImage(image);

             */
        }
    }

    public void initialize() throws ParseException, IOException {
        String path = "src/main/java/GUI/ProductView/resources/mp4.mp4";
        Media media = new Media(new File(path).toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        film.setMediaPlayer(mediaPlayer);
        mediaPlayer.setAutoPlay(true);
        mediaPlayer.setOnEndOfMedia(() -> mediaPlayer.seek(Duration.ZERO));
        MenuHandler.getConnector().clientToServer("show the salesman+" + MenuHandler.getUsername());
        String salesman = MenuHandler.getConnector().serverToClient();
        filter.setItems(list);
        for (String s : salesman.split("\n")) {
            if (s.equalsIgnoreCase("here are")) {
                continue;
            }
            filter.getItems().add("Salesman:" + s);
        }
        MenuHandler.getConnector().clientToServer("show categories+" + MenuHandler.getUsername());
        String categories = MenuHandler.getConnector().serverToClient();
        for (String s : categories.split("\n")) {
            if (s.startsWith("Category")) {
                filter.getItems().add("Category:" + s.split("\\s")[2]);
            }
        }
        Platform.runLater(() -> {
            try {
                Stage stage = (Stage) accountMenuButton.getScene().getWindow();
                Popup popup = new Popup();
                HBox root = FXMLLoader.load(getClass().getResource("/GUI/Supporter/SupporterPopUp.fxml"));
                popup.getContent().add(root);
                double x = stage.getX() + stage.getWidth() - root.getPrefWidth() - 15;
                double y = stage.getY() + stage.getHeight() - root.getPrefHeight() - 15;
                popup.setAnchorX(x);
                popup.setAnchorY(y);
                MenuHandler.setSupporterPopup(popup);
                popup.show(stage);
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("something is wrong with supporter popup");
            }
        });
        update();
    }

    private void update() throws ParseException, IOException {
        StringBuilder categories = new StringBuilder();
        StringBuilder salesman = new StringBuilder();
        StringBuilder available = new StringBuilder();
        StringBuilder confirmationState = new StringBuilder();
        for (Object object : filterList.getChildren()) {
            if (object instanceof HBox) {
                Object object1 = ((HBox) object).getChildren().get(0);
                if (object1 instanceof Label) {
                    if (((Label) object1).getText().startsWith("Confirmation:")) {
                        if (confirmationState.toString().equals("")) {
                            confirmationState = new StringBuilder(((Label) object1).getText().substring(13));
                        } else {
                            confirmationState.append(",").append(((Label) object1).getText().substring(13));
                        }
                    } else if (((Label) object1).getText().startsWith("Category:")) {
                        if (categories.toString().equals("")) {
                            categories = new StringBuilder(((Label) object1).getText().substring(9));
                        } else {
                            categories.append(",").append(((Label) object1).getText().substring(9));
                        }
                    } else if (((Label) object1).getText().startsWith("Salesman:")) {
                        if (salesman.toString().equals("")) {
                            salesman = new StringBuilder(((Label) object1).getText().substring(9));
                        } else {
                            salesman.append(",").append(((Label) object1).getText().substring(9));
                        }
                    } else {
                        if (available.toString().equals("")) {
                            available = new StringBuilder(((Label) object1).getText());
                        } else {
                            available.append(",").append(((Label) object1).getText());
                        }
                    }
                }
            }
        }
        String command = "show products+" + MenuHandler.getUsername();
        if (available.toString().equals("") && salesman.toString().equals("") && categories.toString().equals("") && confirmationState.toString().equals("")) {

        } else {
            command += "+filters:";
            if (!available.toString().equals("")) {
                command += "+remainder+" + available;
            }
            if (!salesman.toString().equals("")) {
                command += "+salesmanIDs+" + salesman;
            }
            if (!categories.toString().equals("")) {
                command += "+categories+" + categories;
            }
            if (!confirmationState.toString().equals("")) {
                command += "+Confirmation+" + confirmationState;
            }
        }

        MenuHandler.getConnector().clientToServer(command);
        String serverAnswer = MenuHandler.getConnector().serverToClient();

        for (int i = 1; i <= products.getChildren().size(); ) {
            Object object = products.getChildren().remove(0);
        }
        if (serverAnswer.equals("nothing found")) {
            return;
        }
        for (String s : serverAnswer.split("\n")) {
            if (s.startsWith("here")) continue;
            Object object = FXMLLoader.load(getClass().getResource
                    ("/GUI/SalesmanProfile/ManageProduct/ProductCard.fxml"));
            products.getChildren().add((Node) object);
            Label label = (Label) ((AnchorPane) object).getChildren().get(1);
            label.setText(s.split("\\s")[4]);
            ((AnchorPane) object).setOnMouseEntered(event -> {
                Stage stage = (Stage) ((AnchorPane) event.getSource()).getScene().getWindow();
                stage.getScene().setCursor(Cursor.HAND);
            });
            ((AnchorPane) object).setOnMouseExited(event -> {
                Stage stage = (Stage) ((AnchorPane) event.getSource()).getScene().getWindow();
                stage.getScene().setCursor(Cursor.DEFAULT);
            });
            ((AnchorPane) object).setOnMouseClicked(event -> {
                MenuHandler.setBackProduct("/GUI/ProductScene/ProductScene.fxml");
                MenuHandler.setProductID(s.split("\\s")[2]);
                Stage stage = (Stage) ((AnchorPane) event.getSource()).getScene().getWindow();
                Parent root = null;
                try {
                    root = FXMLLoader.load(getClass().getResource("/GUI/ProductView/ProductViewLayout.fxml"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                stage.setScene(new Scene(root));
            });
            String productId = s.split("\\s")[2];
            MenuHandler.getConnector().clientToServer("get product picture path+" + productId);
            String path = MenuHandler.getConnector().serverToClient();
            if (path == null) {
                path = "file:\\F:\\AP\\AP\\Project_team-23\\src\\main\\resources\\Pictures\\default.png";
            }
            if (path.equalsIgnoreCase("none")) {
                path = "file:\\F:\\AP\\AP\\Project_team-23\\src\\main\\resources\\Pictures\\default.png";
            }
            ImageView imageView = (ImageView) ((AnchorPane) object).getChildren().get(0);
            Image image = new Image(path);
            imageView.setImage(image);
            ((AnchorPane) object).setStyle("-fx-padding: 0;" + "-fx-border-style: solid inside;"
                    + "-fx-border-width: 1;" + "-fx-border-insets: 2;"
                    + "-fx-border-radius: 2;" + "-fx-border-color: black;");
        }
    }

    public void chooseFilter() throws IOException, ParseException {
        String s = (String) filter.getValue();
        try {
            checkExistenceOfFilter(s);
        } catch (Exception e) {
            return;
        }
        for (Object object : filterList.getChildren()) {
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
            filterList.getChildren().remove(item);
            try {
                update();
            } catch (ParseException | IOException e) {
                e.printStackTrace();
            }
        });
        filterList.getChildren().add(item);
        update();
    }

    private void checkExistenceOfFilter(String filterFactor) throws Exception {
        for (Node child : filterList.getChildren()) {
            Label prevFactor = (Label) ((HBox) child).getChildren().get(0);
            if (prevFactor.getText().equals(filterFactor)) throw new Exception("filter exist");
        }
    }

    public void cartShow(MouseEvent mouseEvent) throws IOException {
        MenuHandler.getStage().setScene(new Scene(FXMLLoader.load(getClass().getResource("/GUI/Cart/Cart.fxml"))));
    }
}
