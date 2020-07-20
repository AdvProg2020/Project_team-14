package GUI.ProductView;

import GUI.Media.Audio;
import GUI.MenuHandler;
import Menus.Menu;
import Model.Off.Sale;
import Model.Storage;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.Popup;
import javafx.stage.Stage;
import org.javatuples.Triplet;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Controller {

    public ImageView imageView;
    private static final int MIN_PIXELS = 10;
    public TextArea comment;
    public TextField commentTitle;
    public FlowPane comments;
    public Label minPrice;
    public Label minPriceField;
    public Label remainder;
    public Label price;
    public Label seller;
    public Label remainderField;
    public Label priceField;
    public Label sellerField;
    public Spinner count;
    public Label countField;
    public ComboBox chooseSeller;
    public Label chooseSellerField;
    public AnchorPane info;
    public Button starButton;
    public Label confirmationStateField;
    public Label confirmationState;
    public Button addButton;
    public Button commentButton;
    public TextArea infoText;
    public Button similarIcon;
    public ImageView status;
    public Label productName;


    private void reset(ImageView imageView, double width, double height) {
        imageView.setViewport(new Rectangle2D(0, 0, width, height));
    }

    private void shift(ImageView imageView, Point2D delta) {
        Rectangle2D viewport = imageView.getViewport();

        double width = imageView.getImage().getWidth();
        double height = imageView.getImage().getHeight();

        double maxX = width - viewport.getWidth();
        double maxY = height - viewport.getHeight();

        double minX = clamp(viewport.getMinX() - delta.getX(), 0, maxX);
        double minY = clamp(viewport.getMinY() - delta.getY(), 0, maxY);

        imageView.setViewport(new Rectangle2D(minX, minY, viewport.getWidth(), viewport.getHeight()));
    }

    private double clamp(double value, double min, double max) {
        if (value < min)
            return min;
        return Math.min(value, max);
    }

    private Point2D imageViewToImage(ImageView imageView, Point2D imageViewCoordinates) {
        double xProportion = imageViewCoordinates.getX() / imageView.getBoundsInLocal().getWidth();
        double yProportion = imageViewCoordinates.getY() / imageView.getBoundsInLocal().getHeight();

        Rectangle2D viewport = imageView.getViewport();
        return new Point2D(
                viewport.getMinX() + xProportion * viewport.getWidth(),
                viewport.getMinY() + yProportion * viewport.getHeight());
    }

    private void zoom() {
        double width = imageView.getImage().getWidth();
        double height = imageView.getImage().getHeight();

        imageView.setPreserveRatio(true);
        reset(imageView, 291.0, 276.0);

        ObjectProperty<Point2D> mouseDown = new SimpleObjectProperty<>();

        imageView.setOnMousePressed(e -> {
            Point2D mousePress = imageViewToImage(imageView, new Point2D(e.getX(), e.getY()));
            mouseDown.set(mousePress);
        });

        imageView.setOnMouseDragged(e -> {
            Point2D dragPoint = imageViewToImage(imageView, new Point2D(e.getX(), e.getY()));
            shift(imageView, dragPoint.subtract(mouseDown.get()));
            mouseDown.set(imageViewToImage(imageView, new Point2D(e.getX(), e.getY())));
        });

        imageView.setOnScroll(e -> {
            double delta = e.getDeltaY();
            Rectangle2D viewport = imageView.getViewport();
            double scale = clamp(Math.pow(1.01, delta),
                    Math.min(MIN_PIXELS / viewport.getWidth(), MIN_PIXELS / viewport.getHeight()),
                    Math.max(width / viewport.getWidth(), height / viewport.getHeight())
            );
            Point2D mouse = imageViewToImage(imageView, new Point2D(e.getX(), e.getY()));
            double newWidth = viewport.getWidth() * scale;
            double newHeight = viewport.getHeight() * scale;
            double newMinX = clamp(mouse.getX() - (mouse.getX() - viewport.getMinX()) * scale,
                    0, width - newWidth);
            double newMinY = clamp(mouse.getY() - (mouse.getY() - viewport.getMinY()) * scale,
                    0, height - newHeight);

            imageView.setViewport(new Rectangle2D(newMinX, newMinY, newWidth, newHeight));
        });

        imageView.setOnMouseClicked(e -> {
            if (e.getClickCount() == 2) {
                reset(imageView, width, height);
            }
        });
    }


    public void initialize() throws ParseException, IOException {
        String productId = MenuHandler.getProductID();
        MenuHandler.getServer().clientToServer("what is comment product ID+" + productId);
        String allComments = MenuHandler.getServer().serverToClient();
        if (!allComments.equals("none")) {
            for (String s : allComments.split("\n")) {
                String sender = s.split("\\+")[0].substring(8);
                String title = s.split("\\+")[1].substring(7);
                String message = s.split("\\+")[2].substring(9);
                Object object = FXMLLoader.load(getClass().getResource("/GUI/ProductView/CommentLayout.fxml"));
                ((Label) ((AnchorPane) object).getChildren().get(1)).setText(sender);
                ((Label) ((VBox) ((AnchorPane) object).getChildren().get(0)).getChildren().get(0)).setText(title);
                ((Label) ((VBox) ((AnchorPane) object).getChildren().get(0)).getChildren().get(1)).setText(message);
                comments.getChildren().add((Node) object);
            }
        }
        MenuHandler.getServer().clientToServer("get product picture path+" + productId);
        String path = MenuHandler.getServer().serverToClient();
        if (path != null) {
            if (!path.equalsIgnoreCase("none")) {
                imageView.setImage(new Image(path));
            }
        }
        zoom();
        if (MenuHandler.isIsUserLogin()) {
            MenuHandler.getServer().clientToServer("view product+" + MenuHandler.getUsername() + "+" + productId);
        } else {
            MenuHandler.getServer().clientToServer("view product+" + "offLine" + "+" + productId);
        }
        String serverAnswer = MenuHandler.getServer().serverToClient();
        StringBuilder ans = new StringBuilder();
        if (!MenuHandler.isIsUserLogin()) {
            for (int i = 0; i < serverAnswer.split("\n").length - 1; i++) {
                ans.append(serverAnswer.split("\n")[i]).append("\n");
            }
        } else {
            if (MenuHandler.getUserType().equalsIgnoreCase("Salesman")) {
                for (int i = 0; i < serverAnswer.split("\n").length - 4; i++) {
                    ans.append(serverAnswer.split("\n")[i]).append("\n");
                }
            } else if (MenuHandler.getUserType().equalsIgnoreCase("Boss")) {
                for (int i = 0; i < serverAnswer.split("\n").length; i++) {
                    ans.append(serverAnswer.split("\n")[i]).append("\n");
                }
            } else {
                for (int i = 0; i < serverAnswer.split("\n").length - 1; i++) {
                    ans.append(serverAnswer.split("\n")[i]).append("\n");
                }
            }
        }
        productName.setText("Product Name: " + serverAnswer.split("\n")[1].split("\\s")[1]);
        infoText.setText(ans.toString());
        infoText.setEditable(false);
        if (!MenuHandler.isIsUserLogin()) {
            customerLoad(serverAnswer);
        } else {
            if (MenuHandler.getUserType().equalsIgnoreCase("Boss")) {
                bossLoad(serverAnswer);
            } else if (MenuHandler.getUserType().equalsIgnoreCase("Salesman")) {
                salesmanLoad(serverAnswer);
            } else {
                customerLoad(serverAnswer);
            }
        }
    }

    private void customerLoad(String serverAnswer) throws ParseException, IOException {
        MenuHandler.getServer().clientToServer("get product min price+" + MenuHandler.getProductID());
        minPrice.setText(MenuHandler.getServer().serverToClient() + "$");
        MenuHandler.getServer().clientToServer("get product sellers+" + MenuHandler.getProductID());
        boolean yourProduct = false;
        for (String seller : MenuHandler.getServer().serverToClient().split(",")) {
            if (seller.startsWith("[")) {
                seller = seller.substring(1);
            }
            if (seller.contains("]")) {
                seller = seller.substring(0, seller.length() - 1);
            }
            if (seller.equals(MenuHandler.getUsername())) {
                yourProduct = true;
            }
            sellers.add(seller);
        }
        chooseSeller.getItems().addAll(sellers);
        addButton.setText("Add To Cart");
        updateSeller();
    }

    private void bossLoad(String serverAnswer) throws ParseException, IOException {
        comment.setEditable(false);
        starButton.setDisable(true);
        commentTitle.setEditable(false);
        MenuHandler.getServer().clientToServer("get product min price+" + MenuHandler.getProductID());
        minPrice.setText(MenuHandler.getServer().serverToClient() + "$");
        MenuHandler.getServer().clientToServer("get product sellers+" + MenuHandler.getProductID());
        boolean yourProduct = false;
        for (String seller : MenuHandler.getServer().serverToClient().split(",")) {
            if (seller.startsWith("[")) {
                seller = seller.substring(1);
            }
            if (seller.contains("]")) {
                seller = seller.substring(0, seller.length() - 1);
            }
            if (seller.equals(MenuHandler.getUsername())) {
                yourProduct = true;
            }
            sellers.add(seller);
        }
        chooseSeller.getItems().addAll(sellers);
        addButton.setText("Delete Product");
        commentButton.setDisable(true);
        count.setVisible(false);
        countField.setVisible(false);
        updateSeller();
    }

    ObservableList<String> sellers = FXCollections.observableArrayList();

    private void salesmanLoad(String serverAnswer) throws ParseException, IOException {
        comment.setEditable(false);
        starButton.setDisable(true);
        commentTitle.setEditable(false);
        MenuHandler.getServer().clientToServer("get product min price+" + MenuHandler.getProductID());
        minPrice.setText(MenuHandler.getServer().serverToClient() + "$");
        MenuHandler.getServer().clientToServer("get product sellers+" + MenuHandler.getProductID());
        boolean yourProduct = false;
        for (String seller : MenuHandler.getServer().serverToClient().split(",")) {
            if (seller.startsWith("[")) {
                seller = seller.substring(1);
            }
            if (seller.contains("]")) {
                seller = seller.substring(0, seller.length() - 1);
            }
            if (seller.equals(MenuHandler.getUsername())) {
                yourProduct = true;
            }
            sellers.add(seller);
        }
        chooseSeller.getItems().addAll(sellers);
        if (yourProduct) {
            chooseSeller.setValue(MenuHandler.getUsername());
            addButton.setText("Edit Your Info");
        } else {
            addButton.setText("Add Product");
        }
        commentButton.setDisable(true);
        count.setVisible(false);
        countField.setVisible(false);
        updateSeller();
    }

    private void updateSeller() throws ParseException, IOException {
        String s = (String) chooseSeller.getValue();
        if (s == null) return;
        if (s.equals("")) return;
        MenuHandler.getServer().clientToServer("view product+" + s + "+" + MenuHandler.getProductID());
        String respond = MenuHandler.getServer().serverToClient();
        seller.setText(s);
        System.out.println(respond);
        for (String string : respond.split("\n")) {
            if (string.startsWith("Your Price")) {
                price.setText(string.split("\\s")[2] + "$");
            } else if (string.startsWith("Your remainder")) {
                remainder.setText(string.split("\\s")[2]);
            } else if (string.startsWith("Confirmation")) {
                confirmationState.setText(string.split("\\s")[4]);
            }
        }
        count.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, Integer.parseInt(remainder.getText()), 0));
    }

    public void doComment(ActionEvent actionEvent) throws IOException, ParseException {
        if (!MenuHandler.isIsUserLogin()) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Yes For Go To Login Or No For Continue", ButtonType.YES, ButtonType.NO);
            alert.showAndWait();
            if (alert.getResult().equals(ButtonType.YES)) {
                MenuHandler.setLoginBackAddress("/GUI/ProductView/ProductViewLayout.fxml");
                MenuHandler.getStage().setScene(new Scene(FXMLLoader.load(getClass().getResource("/GUI/Login/Login.fxml"))));
            }
            return;
        }

        if (checkProductNameFormat(commentTitle.getText())) {
            if (checkDescriptionFormat(comment.getText())) {
                MenuHandler.getServer().clientToServer("comment product+" + MenuHandler.getUsername() + "+" +
                        MenuHandler.getProductID() + "+" + commentTitle.getText() + "+" + comment.getText());
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Comment Request Submitted", ButtonType.OK);
                alert.showAndWait();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Comment Format Should Be Done", ButtonType.OK);
                alert.showAndWait();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Title Input Format Should Be Done", ButtonType.OK);
            alert.showAndWait();
        }
    }

    public void addToCart(ActionEvent actionEvent) throws IOException, ParseException {
        Audio.playClick6();
        if (addButton.getText().equalsIgnoreCase("Add To Cart")) {
            if (chooseSeller.getValue() == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "You Should Choose A Seller Product First", ButtonType.OK);
                alert.showAndWait();
                return;
            }
            if (count.getValue().equals(0)) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Zero Isn't Possible", ButtonType.OK);
                alert.showAndWait();
                return;
            }
            if (confirmationState.getText().equalsIgnoreCase("ACCEPTED")) {
                ArrayList<Triplet<String, String, Integer>> cart = MenuHandler.getCart();
                for (Triplet<String, String, Integer> item : cart) {
                    if (item.getValue0().equals(chooseSeller.getValue())) {
                        if (item.getValue1().equals(MenuHandler.getProductID())) {
                            int counter = item.getValue2();
                            if (counter + (Integer) count.getValue() > (Integer.parseInt(remainder.getText()))) {
                                Alert alert = new Alert(Alert.AlertType.ERROR, "The Current Number Of Items + The Items You Already Added To Your Cart Is More Than Remainder", ButtonType.OK);
                                alert.showAndWait();
                            } else {
                                MenuHandler.getServer().clientToServer("Add To Cart+" + MenuHandler.getUsername() + "+" + (String) chooseSeller.getValue() + "+" + MenuHandler.getProductID() + "+" + (Integer) count.getValue());
                                Triplet addedItem = new Triplet<>((String) chooseSeller.getValue(), MenuHandler.getProductID(), (Integer) count.getValue() + counter);
                                MenuHandler.getCart().remove(item);
                                MenuHandler.getCart().add(addedItem);
                                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Added To The Cart", ButtonType.OK);
                                alert.showAndWait();
                            }
                            return;
                        }
                    }
                }
                Triplet addedItem = new Triplet<>((String) chooseSeller.getValue(), MenuHandler.getProductID(), (Integer) count.getValue());
                MenuHandler.getCart().add(addedItem);
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Added To The Cart", ButtonType.OK);
                alert.showAndWait();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Product Should Be Accepted", ButtonType.OK);
                alert.showAndWait();
            }
        } else if (addButton.getText().startsWith("Edit")) {
            javafx.stage.Popup popup = new Popup();
            Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            Parent addProductPopup = FXMLLoader.load(getClass().getResource("/GUI/ProductView/EditProductLayout.fxml"));
            popup.getContent().addAll(addProductPopup);
            popup.show(stage);
        } else if (addButton.getText().startsWith("Add")) {
            javafx.stage.Popup popup = new Popup();
            Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            Parent addProductPopup = FXMLLoader.load(getClass().getResource("/GUI/ProductView/JoinSellerLayout.fxml"));
            popup.getContent().addAll(addProductPopup);
            popup.show(stage);
        } else {
            //delete product
        }
    }

    public void showVideo(ActionEvent mouseEvent) throws IOException {
        Audio.playClick4();
        Parent root = FXMLLoader.load(getClass().getResource("/GUI/ProductView/Film/Film.fxml"));
        Stage stage = (Stage) ((Button) mouseEvent.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    public void starPopup(ActionEvent mouseEvent) throws IOException {
        Audio.playClick5();
        if (!MenuHandler.isIsUserLogin()) {
            return;
        }
        Popup popup = new Popup();
        Parent starPop = FXMLLoader.load(getClass().getResource("/GUI/ProductView/RatingLayout.fxml"));
        popup.getContent().addAll(starPop);
        Stage stage = MenuHandler.getStage();
        popup.show(stage);
    }

    public void changeSeller(ActionEvent actionEvent) throws IOException, ParseException {
        Audio.playClick2();
        updateSeller();
    }

    public void back(ActionEvent actionEvent) throws IOException {
        Audio.playClick7();
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource(MenuHandler.getBackProduct()));
        stage.setScene(new Scene(root));
    }

    public void similarProducts(ActionEvent actionEvent) throws IOException, ParseException {
        MenuHandler.getServer().clientToServer("similar product+" + MenuHandler.getProductID());
        if (!MenuHandler.getServer().serverToClient().equals("nothing")) {
            Audio.playClick4();
            Parent root = FXMLLoader.load(getClass().getResource("/GUI/ProductView/SimilarProduct/SimilarProduct.fxml"));
            Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "no similar product found!", ButtonType.OK);
            alert.showAndWait();
        }
    }

    public void compare(ActionEvent actionEvent) throws IOException {
        Audio.playClick5();
        Parent root = FXMLLoader.load(getClass().getResource("/GUI/ProductView/Compare.fxml"));
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    private boolean checkProductNameFormat(String input) {
        return getMatcher("([\\d\\w_\\-,\\s'])+", input).matches();
    }

    private boolean checkDescriptionFormat(String description) {
        return getMatcher("[\\w\\s\\.]+", description).matches();
    }

    private Matcher getMatcher(String regex, String command) {
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(command);
    }
}
