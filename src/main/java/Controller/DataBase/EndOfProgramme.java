package Controller.DataBase;

import java.io.IOException;

import Model.Account.Boss;
import Model.Account.Customer;
import Model.Account.Salesman;
import Model.Cart.Cart;
import Model.Category.Category;
import Model.Log.BuyLog;
import Model.Log.SellLog;
import Model.Off.OffCode;
import Model.Off.Sale;
import Model.Off.SpecialOffCode;
import Model.Product.Comment;
import Model.Product.Point;
import Model.Product.Product;
import Model.Request.Request;
import Model.Storage;

import static Model.Storage.*;

public class EndOfProgramme extends DataBase {

    public void updateFiles() throws IOException {
        clearFolder();
        makeDirectories();
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
        updateCarts();
        updateSellLogs();
        updateRequests();
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
        for (Category category : getAllCategories()) {
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
        for (Product product : Storage.getAllProducts()) {
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
    void updateRequests() throws IOException {
        String path = "src\\main\\resources\\DataBase\\Requests\\";
        for (Request request : getAllRequests()) {
            storeObjectInFile(request, path + request.getRequestID());
        }

    }

    @Override
    void updateSellLogs() throws IOException {
        String path = "src\\main\\resources\\DataBase\\SellLogs\\";
        for (SellLog sellLog : allSellLogs) {
            storeObjectInFile(sellLog, path + sellLog.getSellLogID());
        }

    }

    @Override
    void updateCarts() throws IOException {
        String path = "src\\main\\resources\\DataBase\\Carts\\";
        for (Cart cart : allCarts) {
            storeObjectInFile(cart, path + cart.getCartID());
        }
    }

    @Override
    protected void updateSpecialOffCodes() throws IOException {
        String path = "src\\main\\resources\\DataBase\\SpecialOffCodes\\";
        for (SpecialOffCode specialOffCode : allSpecialOffCodes) {
            storeObjectInFile(specialOffCode, path + specialOffCode.getSpecialOffCodeID());
        }
    }

}
