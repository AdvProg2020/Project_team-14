package GUI.Auction;

import GUI.MenuHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class AuctionBetController {
    public TextField maxBet;
    public TextField myBet;
    public Label hour;
    public Label minute;
    public Label second;
    public VBox vBox;

    public void apply(ActionEvent actionEvent) {
    }

    public void back(ActionEvent actionEvent) throws IOException {
        MenuHandler.getStage().setScene(new Scene(FXMLLoader.load(getClass().getResource("/GUI/ProductScene/ProductScene.fxml"))));
    }

    private void load() throws IOException {
        MenuHandler.getConnector().clientToServer("get certain auction+" + MenuHandler.getCurrentAuction() + "+" + MenuHandler.getUsername());
        String serverAnswer = MenuHandler.getConnector().serverToClient();
        for (String s : serverAnswer.split("\n")) {
            String username = s.split("\\+")[0];
            String money = s.split("\\+")[1];
            HBox hBox = new HBox();
            Label userLabel = new Label(username);
            Label moneyLabel = new Label(money);
            hBox.getChildren().add(userLabel);
            hBox.getChildren().add(moneyLabel);
            vBox.getChildren().add(hBox);
        }
        MenuHandler.getConnector().clientToServer("get auction time+" + MenuHandler.getCurrentAuction());
        String time = MenuHandler.getConnector().serverToClient();
        //Date date = System.currentTimeMillis();
        //running here
    }

    public void initialize() {
        Thread thread;
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        load();
                        wait(900);
                    } catch (IOException | InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        thread.run();
    }
}
