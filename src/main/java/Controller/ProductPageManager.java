package Controller;

import Controller.SortFactorEnum.listProductSortFactor;
import Model.Category.Category;
import Model.Product.Comment;
import Model.Product.Product;
import Exception.*;

import java.util.ArrayList;
import java.util.Comparator;

import static java.lang.String.valueOf;

public class ProductPageManager {

    //filter factor: 0 -> [category name] , 1-> [be in sale:boolean] ,
    public void showProducts(String sortFactor, ArrayList<String> filterFactor) throws SortFactorNotAvailableException {
        Category category = Category.getCategoryByName(filterFactor.get(0));
        assert category != null;
        ArrayList<Product> products = ProductManager.getArrayListOfProductsFromArrayListOfProductIDs(category.getAllProductIDs());
        StringBuilder ans = new StringBuilder("All Products in [" + filterFactor.get(0) + "] category:");
        if (sortFactor.equalsIgnoreCase(valueOf(listProductSortFactor.ALPHABETICALLY))) {
            products.sort(Comparator.comparing(Product::getName));
        } else if (sortFactor.equalsIgnoreCase(valueOf(listProductSortFactor.PRICE))) {
            products.sort(Comparator.comparingInt(Product::getMinimumPrice));
        } else if (sortFactor.equalsIgnoreCase(valueOf(listProductSortFactor.SEEN_COUNT))) {
            products.sort((Comparator.comparingInt(Product::getSeenCount)));
        } else if (sortFactor.equals("")) {
            products.sort((Comparator.comparingInt(Product::getSeenCount)));
        } else if (sortFactor.equalsIgnoreCase(valueOf(listProductSortFactor.HIGHEST_POINT))) {
            products.sort((Comparator.comparingDouble(Product::getAveragePoint)));
        } else {
            throw new SortFactorNotAvailableException("the sort factor isn't authentic " + "\n" +
                    "the available sort factors: " + listProductSortFactor.getValues() + "\n");
        }

        for (Product product : products) {
            ans.append("\n").append(product.toStringForCustomerView());
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

    public void showProductDigest(String productID) {
        Product product = Product.getProductWithID(productID);
        assert product != null;
        Server.setAnswer(product.toStringForCustomerView());
    }

    //public void addProductToCart(String productID)

    public void compareProduct(String productID1, String productID2) {
        Product product1 = Product.getProductWithID(productID1);
        Product product2 = Product.getProductWithID(productID2);
        if (product1 == null | product2 == null) {
            Server.setAnswer("error, one of the products doesn't exist");
            return;
        }
        String ans = "Comparision [" + productID1 + " VS " + productID2 + "] :" + "Name:\t\t" + product1.getName() + "\t\t\t\t\t\t" + product2.getName() +
                "Brand:\t\t" + product1.getBrand() + "\t\t\t\t\t\t" + product2.getBrand() +
                "Description:\t\t" + product1.getDescription() + "\t\t\t\t\t\t" + product2.getDescription() +
                "Min Price:\t\t" + product1.getMinimumPrice() + "\t\t\t\t\t\t" + product2.getMinimumPrice();
        Server.setAnswer(ans);
    }

    public void showCommentAndPointOfProduct(String productID) {
        Product product = Product.getProductWithID(productID);
        if (product == null) {
            Server.setAnswer("error, product doesn't exist");
            return;
        }
        StringBuilder ans = new StringBuilder("Average point of this product : " + product.getAveragePoint());
        ans.append("\nComments:\n").append(Comment.getCommentsForProductStringFormatted(productID));
    }

    public void addComment(String comment, String username, String productID) {
        new Comment(comment, username, productID);
    }

}
