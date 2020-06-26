package GUI.SalesmanProfile.ManageSale;

import GUI.Media.Audio;
import GUI.MenuHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Popup;

import java.io.IOException;
import java.text.ParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ManageSaleController {

    public TableView saleTable;
    public TableColumn number;
    public TableColumn percentage;
    public TableColumn startDate;
    public TableColumn endDate;
    public TableColumn status;
    public TableColumn moreInfo;


    @FXML
    public void initialize() throws ParseException, IOException {
        number.setCellValueFactory(new PropertyValueFactory<>("num"));
        percentage.setCellValueFactory(new PropertyValueFactory<>("percentage"));
        startDate.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        endDate.setCellValueFactory(new PropertyValueFactory<>("endDate"));
        status.setCellValueFactory(new PropertyValueFactory<>("status"));
        moreInfo.setCellValueFactory(new PropertyValueFactory<>("moreInfo"));
        update();
    }

    private void update() throws ParseException, IOException {
        saleTable.getItems().clear();
        String toServer = "show sales" + "+" + MenuHandler.getUsername();
        MenuHandler.getServer().clientToServer(toServer);
        String respond = MenuHandler.getServer().serverToClient();

        if (respond.equals("nothing found")) {
            saleTable.setPlaceholder(new Label("You don't have any sale yet!!"));
            return;
        }

        String[] infoLines = respond.split("\n");
        for (int i = 1; i < infoLines.length; i++) {
            addInfoToTable(infoLines[i], i);
        }
    }

    private void addInfoToTable(String line, int row) {
        String num = Integer.toString(row);

        Matcher matcher = getMatcher(line, "Percentage");
        matcher.find();
        String percentage = matcher.group(1);

        matcher = getMatcher(line, "StartDate");
        matcher.find();
        String startDate = matcher.group(1);

        matcher = getMatcher(line, "EndDate");
        matcher.find();
        String endDate = matcher.group(1);

        matcher = getMatcher(line, "Confirmation");
        matcher.find();
        String status = matcher.group(1);

        matcher = getMatcher(line, "SaleId");
        matcher.find();
        String moreInfo = matcher.group(1);

        saleTable.getItems().add(new SaleRow(num, percentage, startDate, endDate, status, moreInfo));
    }

    public static class SaleRow {
        String num;
        String percentage;
        String startDate;
        String endDate;
        String status;
        String moreInfo;

        public SaleRow(String num, String percentage, String startDate, String endDate, String status, String moreInfo) {
            this.num = num;
            this.percentage = percentage;
            this.startDate = startDate;
            this.endDate = endDate;
            this.status = status;
            this.moreInfo = moreInfo;
        }

        public String getNum() {
            return num;
        }

        public String getPercentage() {
            return percentage;
        }

        public String getStartDate() {
            return startDate;
        }

        public String getEndDate() {
            return endDate;
        }

        public String getStatus() {
            return status;
        }

        public String getMoreInfo() {
            return moreInfo;
        }
    }

    private Matcher getMatcher(String command, String type) {
        Pattern pattern = Pattern.compile(type + ":(.[^ ]+)");
        return pattern.matcher(command);
    }


    public void newSale(MouseEvent mouseEvent) throws IOException {
        Audio.playClick2();
        Popup popup = new Popup();
        popup.getContent().add(FXMLLoader.load(getClass().getResource("/GUI/SalesmanProfile/ManageSale/NewSale.fxml")));
        popup.show(((Button) mouseEvent.getSource()).getScene().getWindow());
        popup.setOnHiding(e -> {
            try {
                update();
            } catch (ParseException ex) {
                ex.printStackTrace();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
    }
}
