package GUI.ProductView;

import GUI.Media.Audio;
import GUI.MenuHandler;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.stage.Stage;

import java.io.IOException;
import java.text.ParseException;

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
        double width = imageView.getFitWidth();
        double height = imageView.getFitHeight();

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
        MenuHandler.getServer().clientToServer("get product picture path+" + productId);
        String path = MenuHandler.getServer().serverToClient();
        if (path != null) {
            imageView.setImage(new Image(path));
        }
        zoom();
        MenuHandler.getServer().clientToServer("view product+" + MenuHandler.getUsername() + "+" + productId);
        String serverAnswer = MenuHandler.getServer().serverToClient();
        String ans = "";
        for (int i = 0; i < serverAnswer.split("\n").length - 4; i++) {
            ans += serverAnswer.split("\n")[i] + "\n";
        }
        infoText.setText(ans);
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

    public void doComment(ActionEvent actionEvent) {
        //comment
    }

    public void addToCart(ActionEvent actionEvent) throws IOException {
        Audio.playClick6();
        if (addButton.getText().equalsIgnoreCase("Add To Cart")) {
            //add to cart
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

    public void starPopup(ActionEvent mouseEvent) {
        Audio.playClick5();
        //star popup
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
}
