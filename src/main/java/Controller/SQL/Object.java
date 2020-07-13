package Controller.SQL;

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

import java.io.*;
import java.util.ArrayList;

public class Object {
    public static Object object;
    private ArrayList<Boss> bosses = new ArrayList<>();
    private ArrayList<BuyLog> buyLogs = new ArrayList<>();
    private ArrayList<Category> categories = new ArrayList<>();
    private ArrayList<Comment> comments = new ArrayList<>();
    private ArrayList<Customer> customers = new ArrayList<>();
    private ArrayList<OffCode> offCodes = new ArrayList<>();
    private ArrayList<Point> points = new ArrayList<>();
    private ArrayList<Product> products = new ArrayList<>();
    private ArrayList<Sale> sales = new ArrayList<>();
    private ArrayList<Salesman> salesmen = new ArrayList<>();
    private ArrayList<SpecialOffCode> specialOffCodes = new ArrayList<>();
    private ArrayList<SellLog> sellLogs = new ArrayList<>();
    private ArrayList<Cart> carts = new ArrayList<>();
    private ArrayList<Request> requests = new ArrayList<>();

    public Object() {
        bosses.addAll(Storage.getAllBosses());
        buyLogs.addAll(Storage.allBuyLogs);
        categories.addAll(Storage.getAllCategories());
        comments.addAll(Storage.allComments);
        customers.addAll(Storage.getAllCustomers());
        offCodes.addAll(Storage.allOffCodes);
        points.addAll(Storage.allPoints);
        products.addAll(Storage.getAllProducts());
        sales.addAll(Storage.allSales);
        salesmen.addAll(Storage.getAllSalesmen());
        specialOffCodes.addAll(Storage.allSpecialOffCodes);
        sellLogs.addAll(Storage.allSellLogs);
        carts.addAll(Storage.allCarts);
        requests.addAll(Storage.getAllRequests());
        object = this;
    }

    public String serialise() throws IOException {
        String string = "";
        try {
            ByteArrayOutputStream bo = new ByteArrayOutputStream();
            ObjectOutputStream so = new ObjectOutputStream(bo);
            so.writeObject(object);
            so.flush();
            string = bo.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return string;
    }

    public static void deserialize(String string) {
        if (string == null || string.equals("")) {
            return;
        }
        try {
            byte[] b = string.getBytes();
            ByteArrayInputStream bi = new ByteArrayInputStream(b);
            ObjectInputStream si = new ObjectInputStream(bi);
            Object obj = (Object) si.readObject();
            addToMemory(obj);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void addToMemory(Object object) {
        Storage.getAllBosses().clear();
        Storage.getAllBosses().addAll(object.bosses);
        Storage.allBuyLogs.clear();
        Storage.allBuyLogs.addAll(object.buyLogs);
        Storage.getAllCategories().clear();
        Storage.getAllCategories().addAll(object.categories);
        Storage.allComments.clear();
        Storage.allComments.addAll(object.comments);
        Storage.getAllCustomers().clear();
        Storage.getAllCustomers().addAll(object.customers);
        Storage.allOffCodes.clear();
        Storage.allOffCodes.addAll(object.offCodes);
        Storage.allPoints.clear();
        Storage.allPoints.addAll(object.points);
        Storage.getAllProducts().clear();
        Storage.getAllProducts().addAll(object.products);
        Storage.allSales.clear();
        Storage.allSales.addAll(object.sales);
        Storage.getAllSalesmen().clear();
        Storage.getAllSalesmen().addAll(object.salesmen);
        Storage.allSpecialOffCodes.clear();
        Storage.allSpecialOffCodes.addAll(object.specialOffCodes);
        Storage.allSellLogs.clear();
        Storage.allSellLogs.addAll(object.sellLogs);
        Storage.allCarts.clear();
        Storage.allCarts.addAll(object.carts);
        Storage.getAllRequests().clear();
        Storage.getAllRequests().addAll(object.requests);
    }

}
