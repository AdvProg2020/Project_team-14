package Model.Product;

import Model.Account.Customer;
import Model.Account.Salesman;
import org.junit.Assert;
import org.junit.Test;
import java.util.ArrayList;

public class CommentTest {

    Salesman salesman = new Salesman("salesmanUser", "password", "firstname",
            "secondName", "h.hafezi2000@gmail.com", "09333805288",
            "SALESMAN", "company", 0);

    Product product = new Product("name", salesman.getUsername(), "brand", "description",
            1000, 100);

    Product product2 = new Product("name2", salesman.getUsername(), "brand2", "description2",
            1000, 100);


    Customer customer = new Customer("customerUser", "password", "firstname",
            "secondName", "h.hafezi2000@yahoo.com",
            "09333805288", "CUSTOMER", 1000);


    @Test
    public void getComment1() {
        Assert.assertNull(Comment.getCommentByID("aaaaaaaaaaaa"));
    }

    @Test
    public void getComment2() {
        Comment comment = new Comment("text", customer.getUsername(), product.getProductID());
        Assert.assertTrue(comment.isChecking());
        Assert.assertNotNull(Comment.getCommentByID(comment.getCommentID()));
    }

    @Test
    public void getCheckingComment() {
        ArrayList<String> arrayList = new ArrayList<>(Comment.getCheckingComments());
        Assert.assertEquals(arrayList.size(), 1);
    }

    @Test
    public void toStringForProductView() {
        Comment comment = new Comment("text", customer.getUsername(), product.getProductID());
        String result = "Sender: customerUser" + "\n";
        result += "Message: text" + "\n";
        Assert.assertTrue(comment.toStringForProductView().contains(result));
    }

    @Test
    public void toStringForChecking() {
        Comment comment = new Comment("text", customer.getUsername(), product.getProductID());
        String result = "Product Name: name";
        result += "Sender: customerUser" + "\n";
        result += "Message: text" + "\n";
        Assert.assertTrue(comment.toStringForChecking().contains(result));
    }

    @Test
    public void isThereCommentForProduct1() {
        Comment comment = new Comment("text", customer.getUsername(), product.getProductID());
        Assert.assertFalse(comment.isConfirmed());
        comment.setConfirmationState("ACCEPTED");
        Assert.assertTrue(Comment.isThereCommentForProduct(product.getProductID()));
    }

    @Test
    public void isThereCommentForProduct2() {
        Comment comment = new Comment("text", customer.getUsername(), product.getProductID());
        comment.setConfirmationState("DENIED");
        Assert.assertFalse(Comment.isThereCommentForProduct(product.getProductID()));
    }

    @Test
    public void isThereCommentForProduct3() {
        Assert.assertFalse(Comment.isThereCommentForProduct(product2.getProductID()));
    }


    @Test
    public void getProductID() {
        Comment comment = new Comment("text", customer.getUsername(), product.getProductID());
        Assert.assertEquals(product.getProductID(), comment.getProductID());
    }

    @Test
    public void createString() {
        Comment comment = new Comment("text", customer.getUsername(), product.getProductID());
        Assert.assertTrue(comment.createID().startsWith("Comment---"));
    }

    @Test
    public void getCommentsForProduct() {
        Comment comment = new Comment("text", customer.getUsername(), product.getProductID());
        Comment comment2 = new Comment("text", customer.getUsername(), product.getProductID());
        Comment comment3 = new Comment("text", customer.getUsername(), product.getProductID());
        comment.setConfirmationState("ACCEPTED");
        comment2.setConfirmationState("ACCEPTED");
        comment3.setConfirmationState("ACCEPTED");
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add(comment.getCommentID());
        arrayList.add(comment2.getCommentID());
        arrayList.add(comment3.getCommentID());
        Assert.assertEquals(Comment.getCommentsForProductWithID(product.getProductID()), arrayList);
    }

    @Test
    public void getCommentsForProductStringFormatted() {
        Comment comment = new Comment("text", customer.getUsername(), product.getProductID());
        Comment comment2 = new Comment("text", customer.getUsername(), product.getProductID());
        Comment comment3 = new Comment("text", customer.getUsername(), product.getProductID());
        comment.setConfirmationState("ACCEPTED");
        comment2.setConfirmationState("ACCEPTED");
        comment3.setConfirmationState("ACCEPTED");
        String result = comment.toStringForProductView() + comment2.toStringForProductView() + comment3.toStringForProductView();
        Assert.assertEquals(result, Comment.getCommentsForProductStringFormatted(product.getProductID()));

    }

}
