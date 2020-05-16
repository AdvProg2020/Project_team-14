package Model.Request;

import Model.Product.Product;
import Model.Request.Enum.*;
import Model.Storage;

import java.text.ParseException;

public class ChangeProductRequest extends Request {
    ProductAttributes attribute;
    String updatedInfo;

    //update info for "is on sale" must be "true / false"

    public ChangeProductRequest(String salesmanID, Product product, String attribute, String updatedInfo) {
        super(salesmanID, product, RequestType.CHANGE_PRODUCT.name());
        if (attribute.equalsIgnoreCase("brand")) {
            this.attribute = ProductAttributes.BRAND;
        } else if (attribute.equalsIgnoreCase("name")) {
            this.attribute = ProductAttributes.NAME;
        } else if (attribute.equalsIgnoreCase("description")) {
            this.attribute = ProductAttributes.DESCRIPTION;
        } else if (attribute.equalsIgnoreCase("is on sale")) {
            this.attribute = ProductAttributes.IS_ON_SALE;
        }
        this.updatedInfo = updatedInfo;
    }

    public void updateAttributeWithUpdatedInfo() throws ParseException {
        Product product = (Product) Storage.getProductById(objectID);
        if (attribute.equals(ProductAttributes.BRAND)) {
            product.setBrand(updatedInfo);
        } else if (attribute.equals(ProductAttributes.NAME)) {
            product.setName(updatedInfo);
        } else if (attribute.equals(ProductAttributes.DESCRIPTION)) {
            product.setDescription(updatedInfo);
        } else if (attribute.equals(ProductAttributes.IS_ON_SALE)) {
            product.setIsOnSale(accountUsername, Boolean.parseBoolean(updatedInfo));
        }
    }

    public String toStringChangeProduct() {
        Product product = (Product) Storage.getProductById(objectID);
        return "Product Name:" + product.getName() + "\n" +
                "Attribute to change: " + attribute.name().toLowerCase() + "\n"
                + "New attribute value: " + updatedInfo
                + "Confirmation State: " + confirmation.name() + "\n";

    }

}
