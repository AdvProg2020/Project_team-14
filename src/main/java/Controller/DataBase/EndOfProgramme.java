package Controller.DataBase;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import ModelTest.Account.Boss;
import ModelTest.Account.Customer;
import ModelTest.Account.Salesman;
import ModelTest.Category.Category;
import ModelTest.Log.BuyLog;
import ModelTest.Off.OffCode;
import ModelTest.Off.Sale;
import ModelTest.Off.SpecialOffCode;
import ModelTest.Product.Comment;
import ModelTest.Product.Point;
import ModelTest.Product.Product;

import static ModelTest.Storage.*;

public class EndOfProgramme extends DataBase implements Runnable {

    public void updateFiles() throws IOException {
        updateBosses();
        updateBuyLogs();
        updateCategories();
        updateComments();
        updateCustomers();
        updateOffCodes();
        updatePoints();
        updateProducts();
        updateSales();
        updateSalesmen();
        updateSpecialOffCodes();
    }

    @Override
    protected void updateCustomers() throws IOException {
        String path = "src\\main\\resources\\DataBase\\Customers\\";
        for (Customer customer : getAllCustomers()) {
            storeObjectInFile(customer, path + customer.getUsername());
        }
    }

    @Override
    protected void updateBosses() throws IOException {
        String path = "src\\main\\resources\\DataBase\\Bosses\\";
        for (Boss boss : getAllBosses()) {
            storeObjectInFile(boss, path + boss.getUsername());
        }
    }

    @Override
    protected void updateSalesmen() throws IOException {
        String path = "src\\main\\resources\\DataBase\\Salesmen\\";
        for (Salesman salesman : getAllSalesmen()) {
            storeObjectInFile(salesman, path + salesman.getUsername());
        }
    }

    @Override
    protected void updateCategories() throws IOException {
        String path = "src\\main\\resources\\DataBase\\Categories\\";
        for (Category category : allCategories) {
            storeObjectInFile(category, path + category.getCategoryName());
        }
    }

    @Override
    protected void updateBuyLogs() throws IOException {
        String path = "src\\main\\resources\\DataBase\\BuyLogs\\";
        for (BuyLog buyLog : allBuyLogs) {
            storeObjectInFile(buyLog, path + buyLog.getBuyLogID());
        }
    }

    @Override
    protected void updateSales() throws IOException {
        String path = "src\\main\\resources\\DataBase\\Sales\\";
        for (Sale sale : allSales) {
            storeObjectInFile(sale, path + sale.getSaleID());
        }
    }

    @Override
    protected void updateOffCodes() throws IOException {
        String path = "src\\main\\resources\\DataBase\\OffCodes\\";
        for (OffCode offCode : allOffCodes) {
            storeObjectInFile(offCode, path + offCode.getOffCodeID());
        }
    }

    @Override
    protected void updateProducts() throws IOException {
        String path = "src\\main\\resources\\DataBase\\Products\\";
        for (Product product : allProducts) {
            storeObjectInFile(product, path + product.getProductID());
        }
    }

    @Override
    protected void updateComments() throws IOException {
        String path = "src\\main\\resources\\DataBase\\Comments\\";
        for (Comment comment : allComments) {
            storeObjectInFile(comment, path + comment.getCommentID());
        }
    }

    @Override
    protected void updatePoints() throws IOException {
        String path = "src\\main\\resources\\DataBase\\Points\\";
        for (Point point : allPoints) {
            storeObjectInFile(point, path + point.getPointID());
        }
    }

    @Override
    protected void updateSpecialOffCodes() throws IOException {
        String path = "src\\main\\resources\\DataBase\\SpecialOffCodes\\";
        for (SpecialOffCode specialOffCode : allSpecialOffCodes) {
            storeObjectInFile(specialOffCode, path + specialOffCode.getSpecialOffCodeID());
        }

    }

    @Override
    public void run() {
        try {
            clearFolder();
            updateFiles();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            Thread.sleep(TimeUnit.SECONDS.toMillis(10));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
