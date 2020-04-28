package Controller.DataBase;

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

import java.io.*;

import static ModelTest.Storage.*;

public class startOfProgramme extends DataBase {

    public void startProgramme() throws IOException, ClassNotFoundException {
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
        EndOfProgramme endOfProgramme=new EndOfProgramme();
        endOfProgramme.run();
    }


    @Override
    protected void updateCustomers() throws IOException, ClassNotFoundException {
        String path = "src\\main\\resources\\DataBase\\Customers";
        File[] files = new File(path).listFiles();
        assert files != null;
        for (File file : files) {
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(file.getAbsolutePath()));
            Customer s = (Customer) in.readObject();
            allAccounts.add(s);
        }

    }

    @Override
    protected void updateBosses() throws IOException, ClassNotFoundException {
        String path = "src\\main\\resources\\DataBase\\Bosses";
        File[] files = new File(path).listFiles();
        assert files != null;
        for (File file : files) {
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(file.getAbsolutePath()));
            Boss s = (Boss) in.readObject();
            allAccounts.add(s);
        }
    }

    @Override
    protected void updateSalesmen() throws IOException, ClassNotFoundException {
        String path = "src\\main\\resources\\DataBase\\Salesmen";
        File[] files = new File(path).listFiles();
        assert files != null;
        for (File file : files) {
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(file.getAbsolutePath()));
            Salesman s = (Salesman) in.readObject();
            allAccounts.add(s);
        }

    }

    @Override
    protected void updateCategories() throws IOException, ClassNotFoundException {
        String path = "src\\main\\resources\\DataBase\\Sales";
        File[] files = new File(path).listFiles();
        assert files != null;
        for (File file : files) {
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(file.getAbsolutePath()));
            Category s = (Category) in.readObject();
            allCategories.add(s);
        }
    }

    @Override
    protected void updateBuyLogs() throws IOException, ClassNotFoundException {
        String path = "src\\main\\resources\\DataBase\\BuyLogs";
        File[] files = new File(path).listFiles();
        assert files != null;
        for (File file : files) {
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(file.getAbsolutePath()));
            BuyLog s = (BuyLog) in.readObject();
            allBuyLogs.add(s);
        }

    }

    @Override
    protected void updateSales() throws IOException, ClassNotFoundException {
        String path = "src\\main\\resources\\DataBase\\Sales";
        File[] files = new File(path).listFiles();
        assert files != null;
        for (File file : files) {
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(file.getAbsolutePath()));
            Sale s = (Sale) in.readObject();
            allSales.add(s);
        }
    }

    @Override
    protected void updateOffCodes() throws IOException, ClassNotFoundException {
        String path = "src\\main\\resources\\DataBase\\OffCodes";
        File[] files = new File(path).listFiles();
        assert files != null;
        for (File file : files) {
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(file.getAbsolutePath()));
            OffCode s = (OffCode) in.readObject();
            allOffCodes.add(s);
        }

    }

    @Override
    protected void updateProducts() throws IOException, ClassNotFoundException {
        String path = "src\\main\\resources\\DataBase\\Products";
        File[] files = new File(path).listFiles();
        assert files != null;
        for (File file : files) {
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(file.getAbsolutePath()));
            Product s = (Product) in.readObject();
            allProducts.add(s);
        }

    }

    @Override
    protected void updateComments() throws IOException, ClassNotFoundException {
        String path = "src\\main\\resources\\DataBase\\Comments";
        File[] files = new File(path).listFiles();
        assert files != null;
        for (File file : files) {
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(file.getAbsolutePath()));
            Comment s = (Comment) in.readObject();
            allComments.add(s);
        }
    }


    @Override
    protected void updateSpecialOffCodes() throws IOException, ClassNotFoundException {
        String path = "src\\main\\resources\\DataBase\\SpecialOffCodes";
        File[] files = new File(path).listFiles();
        assert files != null;
        for (File file : files) {
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(file.getAbsolutePath()));
            SpecialOffCode s = (SpecialOffCode) in.readObject();
            allSpecialOffCodes.add(s);
        }

    }

    @Override
    protected void updatePoints() throws IOException, ClassNotFoundException {
        String path = "src\\main\\resources\\DataBase\\Points";
        File[] files = new File(path).listFiles();
        assert files != null;
        for (File file : files) {
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(file.getAbsolutePath()));
            Point s = (Point) in.readObject();
            allPoints.add(s);
        }
    }

}

