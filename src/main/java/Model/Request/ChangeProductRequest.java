package Model.Request;

import Model.Account.Salesman;
import Model.Product.Product;
import Model.Request.Enum.*;
import Model.Storage;

import java.io.Serializable;
import java.text.ParseException;

public class ChangeProductRequest extends Request implements Serializable {
    ProductAttributes attribute;
    String updatedInfo;
    private static final long serialVersionUID = 6529685098267757690L;

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
        Product product = (Product) object;
        if (attribute.equals(ProductAttributes.BRAND)) {
            assert product != null;
            product.setBrand(updatedInfo);
        } else if (attribute.equals(ProductAttributes.NAME)) {
            assert product != null;
            product.setName(updatedInfo);
        } else if (attribute.equals(ProductAttributes.DESCRIPTION)) {
            assert product != null;
            product.setDescription(updatedInfo);
        } else if (attribute.equals(ProductAttributes.IS_ON_SALE)) {
            assert product != null;
            product.setIsOnSale(accountUsername, Boolean.parseBoolean(updatedInfo));
        }
    }

    public ProductAttributes getAttribute() {
        return attribute;
    }

    public void setAttribute(ProductAttributes attribute) {
        this.attribute = attribute;
    }

    public String getUpdatedInfo() {
        return updatedInfo;
    }

    public void setUpdatedInfo(String updatedInfo) {
        this.updatedInfo = updatedInfo;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String toStringChangeProduct() {
        Product product = (Product) object;
        Salesman salesman = (Salesman) Storage.getAccountWithUsername(accountUsername);
        String result = "";
        result += "General information of salesman: " + "\n";
        assert salesman != null;
        result += salesman.toStringForRequest() + "\n";
        assert product != null;
        return result + "Product Name: " + product.getName() + "\n" +
                "Product ID: " + product.getProductID() + "\n" +
                "Attribute to change: " + attribute.name().toLowerCase() + "\n"
                + "New attribute value: " + updatedInfo
                + "Confirmation State: " + confirmation.name() + "\n";
    }

}
