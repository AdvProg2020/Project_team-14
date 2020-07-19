package Bank.SQL;

import Bank.Account;
import Bank.Transaction;
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

import javax.xml.transform.Transformer;
import java.io.*;
import java.util.ArrayList;
import java.util.Base64;

public class Object implements Serializable {

    public static Object object = new Object();
    private ArrayList<Account> accounts = new ArrayList<>();
    private ArrayList<Transaction> transactions = new ArrayList<>();
    private static final long serialVersionUID = 4L;

    public Object() {
        transactions.addAll(Transaction.getAllTransaction());
        accounts.addAll(Account.getAllAccounts());
    }

    public String serialise(Object object) {
        String string = "";
        try {
            ByteArrayOutputStream bo = new ByteArrayOutputStream();
            ObjectOutputStream so = new ObjectOutputStream(bo);
            so.writeObject(object);
            so.flush();
            string = Base64.getEncoder().encodeToString(bo.toByteArray());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return string;
    }

    public static void deserialize(byte[] bytes) {
        try {
            ByteArrayInputStream bi = new ByteArrayInputStream(bytes);
            ObjectInputStream si = new ObjectInputStream(bi);
            Object obj = (Object) si.readObject();
            addToMemory(obj);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void addToMemory(Object object) {
        Account.getAllAccounts().clear();
        Account.getAllAccounts().addAll(object.accounts);
        Transaction.getAllTransaction().clear();
        Transaction.getAllTransaction().addAll(object.transactions);
    }

}
