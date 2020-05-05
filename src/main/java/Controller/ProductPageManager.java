package Controller;

import Model.Category.Category;
import Model.Product.Comment;
import Model.Product.Product;

import java.util.ArrayList;

public class ProductPageManager {

    //filter factor: 0 -> [category name] , 1-> [be in sale:boolean] ,
    public void showProducts(String sortFactor, ArrayList<String> filterFactor) {
        ArrayList<String> allProductIDs = Category.getCategoryByName(filterFactor.get(0)).getAllProductIDs();
        StringBuilder ans = new StringBuilder("All Products in [" + filterFactor.get(0) + "] category:");
        /*
         * sort array
         */
        for (String productID : allProductIDs) {
            ans.append("\n" + Product.getProductWithID(productID).getName());
        }
        Server.setAnswer(ans.toString());
    }

    public void goToProductPage(String productID) {
        if (Product.getProductWithID(productID) == null) {
            Server.setAnswer("error, product doesn't exist.");
        } else {
            Server.setAnswer("successful");
        }
    }

    //----------------------------------------------------------------------------------

    public void showProductDigest(String productID) {
        Server.setAnswer(Product.getProductWithID(productID).toStringForCustomerView());
    }

    //public void addProductToCart(String productID)

    public void compareProduct(String productID1, String productID2) {
        Product product1 = Product.getProductWithID(productID1);
        Product product2 = Product.getProductWithID(productID2);
        if (product1 == null | product2 == null) {
            Server.setAnswer("error, one of the products doesn't exist");
            return;
        }
        StringBuilder ans = new StringBuilder("Comparision [" + productID1 + " VS " + productID2 + "] :");
        ans.append("Name:\t\t" + product1.getName() + "\t\t\t\t\t\t" + product2.getName());
        ans.append("Brand:\t\t" + product1.getBrand() + "\t\t\t\t\t\t" + product2.getBrand());
        ans.append("Description:\t\t" + product1.getDescription() + "\t\t\t\t\t\t" + product2.getDescription());
        ans.append("Min Price:\t\t" + product1.getMinimumPrice() + "\t\t\t\t\t\t" + product2.getMinimumPrice());

        Server.setAnswer(ans.toString());
    }

    public void showCommentAndPointOfProduct(String productID) {
        Product product = Product.getProductWithID(productID);
        if (product == null) {
            Server.setAnswer("error, product doesn't exist");
            return;
        }
        StringBuilder ans = new StringBuilder("Average point of this product : " + product.getAveragePoint());
        ans.append("\nComments:\n" + Comment.getCommentsForProductStringFormatted(productID));
    }

    public void addComment(String comment, String username, String productID) {
        new Comment(comment, username, productID);
    }

}
