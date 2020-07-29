package GUI.Auction;

import GUI.MenuHandler;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.awt.*;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AuctionBetController {
    public TextField maxBet;
    public TextField myBet;
    public Label hour;
    public Label minute;
    public Button applyButton;
    Thread thread;
    public Label second;
    public VBox vBox;

    public void apply(ActionEvent actionEvent) throws IOException {
        int x = Integer.parseInt(myBet.getText());
        int y = Integer.parseInt(maxBet.getText());
        if (x <= y) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "More Money Required", ButtonType.OK);
            alert.showAndWait();
        } else {
            MenuHandler.getConnector().clientToServer("put bet+" + MenuHandler.getCurrentAuction() + "+" + MenuHandler.getUsername() + "+" + myBet.getText());
            String serverAnswer = MenuHandler.getConnector().serverToClient();
        }
    }

    public void back(ActionEvent actionEvent) throws IOException {
        MenuHandler.getStage().setScene(new Scene(FXMLLoader.load(getClass().getResource("/GUI/ProductScene/ProductScene.fxml"))));
    }

    private void load() throws IOException {
        MenuHandler.getConnector().clientToServer("get auction time+" + MenuHandler.getCurrentAuction());
        String time = MenuHandler.getConnector().serverToClient();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = formatter.parse(time);
        } catch (ParseException e) {
            //e.printStackTrace();
        }
        System.out.println(date);
        long x = date.getTime();
        Date date1 = new Date();
        long diff = date.getTime() - date1.getTime();
        Platform.runLater(() -> {
            hour.setText(String.valueOf(((int) (diff / (60 * 60 * 1000)))));
            minute.setText(String.valueOf(((int) (diff / (60 * 1000))) % 60));
            second.setText(String.valueOf((((int) (diff / (1000)))) % 60));
        });
        MenuHandler.getConnector().clientToServer("get certain auction+" + MenuHandler.getCurrentAuction() + "+" + MenuHandler.getUsername());
        String serverAnswer = MenuHandler.getConnector().serverToClient();
        if (serverAnswer.equalsIgnoreCase("no bet")) {
            maxBet.setText("0");
            return;
        }
        for (String s : serverAnswer.split("\n")) {
            String username = s.split("\\+")[0];
            String money = s.split("\\+")[1];
            maxBet.setText(money);
            HBox hBox = new HBox();
            hBox.setStyle("-fx-padding: 10;" + "-fx-border-style: solid inside;"
                    + "-fx-border-width: 2;" + "-fx-border-insets: 5;"
                    + "-fx-border-radius: 5;" + "-fx-border-color: blue;");
            hBox.setPrefWidth(422);
            Label userLabel = new Label(username);
            Label moneyLabel = new Label(money);
            hBox.getChildren().add(userLabel);
            hBox.getChildren().add(moneyLabel);
            vBox.setSpacing(5);
            //vBox.getChildren().add(hBox);
        }
        maxBet.setText(serverAnswer.split("\\+")[1]);
    }

    public void initialize() {
        if (MenuHandler.getUserType().equalsIgnoreCase("Salesman")) {
            applyButton.setDisable(true);
        }
        Thread thread;
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        load();
                        Thread.sleep(1000);
                    } catch (IOException | InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        thread.start();
    }
}
