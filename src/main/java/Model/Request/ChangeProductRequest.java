package Model.Request;

import Model.Off.Sale;
import Model.Product.Product;

import java.text.ParseException;

enum ProductAttributes {
    BRAND, NAME, DESCRIPTION, IS_ON_SALE;
}

public class ChangeProductRequest extends Request {
    ProductAttributes attribute;
    String updatedInfo;

    //update info for "is on sale" must be "true / false"

    public ChangeProductRequest(String salesmanID, Product product, String attribute,String updatedInfo) {
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


    public void updateAttributeWithUpdateInfo() throws ParseException {
        Product product = (Product) object;
        if (attribute.equals(ProductAttributes.BRAND)) {
            product.setBrand(updatedInfo);
        } else if (attribute.equals(ProductAttributes.NAME)) {
            product.setName(updatedInfo);
        } else if (attribute.equals(ProductAttributes.DESCRIPTION)){
            product.setDescription(updatedInfo);
        } else if (attribute.equals(ProductAttributes.IS_ON_SALE)){
            product.setIsOnSale(salesmanID,Boolean.parseBoolean(updatedInfo));
        }
    }
}