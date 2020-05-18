package Model.Product;

import Model.Confirmation;
import Model.Log.Log;
import Model.RandomString;
import Model.Request.Request;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

import static Model.Storage.*;

public class Comment implements Serializable {
    private String commentID;
    private String title;
    private String text;
    private String senderUsername;
    private String productID;
    private Confirmation confirmationState;
    private Date date;


    public Comment(String title, String text, String senderUsername, String productID) {
        this.title = title;
        this.text = text;
        this.senderUsername = senderUsername;
        this.productID = productID;
        this.confirmationState = Confirmation.CHECKING;
        this.commentID = createID();
        this.date = new Date();
        allComments.add(this);
        new Request(this);
    }

    public static Comment getCommentByID(String commentID) {
        for (Comment comment : allComments) {
            if (comment.commentID.equals(commentID)) {
                return comment;
            }
        }
        return null;
    }

    public static boolean canUserCommentForProduct(String username, String productID) {
        return Log.hasCustomerBoughtProduct(username, productID);
    }


    //  when we need get comments for a product for customer view we use the method below
    //and it return all commentIDs that have the property we want

    public static ArrayList<String> getCommentsForProductWithID(String productID) {
        ArrayList<String> list = new ArrayList<>();
        for (Comment comment : allComments) {
            if (comment.isConfirmed() && comment.getProductID().equals(productID)) {
                list.add(comment.commentID);
            }
        }
        return list;
    }

    public static String getCommentsForProductStringFormatted(String productID) {
        StringBuilder result = new StringBuilder();
        for (String commentID : getCommentsForProductWithID(productID)) {
            Comment comment = Comment.getCommentByID(commentID);
            assert comment != null;
            result.append(comment.toStringForProductView());
        }
        return result.toString();
    }

    //in the boss menu when the boss wants to check request one part is check comments
    //that are not still accepted nor denied, so with method below we get not checked commentIDs

    public static ArrayList<String> getCheckingComments() {
        ArrayList<String> list = new ArrayList<>();
        for (Comment comment : allComments) {
            if (comment.isChecking()) {
                list.add(comment.commentID);
            }
        }
        return list;
    }

    //it gives us customer view of a product

    public String toStringForProductView() {
        String result = "";
        result += "Sender: " + this.senderUsername + "\n";
        result += "Title: " + this.title + "\n";
        result += "Message: " + this.text + "\n";
        result += "Date: " + this.date.toString() + "\n";
        return result;
    }

    //it gives us boss view for a product

    public String toStringForChecking() {
        String result = "";
        result += "Product Name: " + Objects.requireNonNull(Product.getProductWithID(productID)).getName();
        result += "Sender: " + this.senderUsername + "\n";
        result += "Title: " + this.title + "\n";
        result += "Message: " + this.text + "\n";
        result += "Date: " + this.date.toString() + "\n";
        return result;
    }

    @Override
    public String toString() {
        String result = "";
        result += "Product Name: " + Objects.requireNonNull(Product.getProductWithID(productID)).getName();
        result += "Sender: " + this.senderUsername + "\n";
        result += "Title: " + this.title + "\n";
        result += "Message: " + this.text + "\n";
        result += "Date: " + this.date.toString() + "\n";
        return result;
    }

    public static boolean isThereCommentForProduct(String productID) {
        for (Comment comment : allComments) {
            if (comment.isConfirmed() && comment.productID.equals(productID)) {
                return true;
            }
        }
        return false;
    }

    public String getCommentID() {
        return commentID;
    }

    public String getSenderUsername() {
        return senderUsername;
    }

    public String getProductID() {
        return productID;
    }

    public boolean isConfirmed() {
        return confirmationState.equals(Confirmation.ACCEPTED);
    }

    public boolean isChecking() {
        return confirmationState.equals(Confirmation.CHECKING);
    }

    public void setConfirmationState(Confirmation confirmation) {
        this.confirmationState = confirmation;
    }

    String createID() {
        return RandomString.createID("Comment");
    }
}
