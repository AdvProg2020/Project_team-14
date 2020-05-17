package Model.Request;

import Model.Off.Sale;
import Model.Product.Product;
import Model.Request.Enum.*;

import java.text.ParseException;

public class ChangeSaleRequest extends Request {
    SaleAttributes attribute;
    String updatedInfo;

    public ChangeSaleRequest(String salesmanID, Sale sale, String attribute, String updatedInfo) {
        super(salesmanID, sale, RequestType.CHANGE_SALE.name());
        if (attribute.equalsIgnoreCase("end date")) {
            this.attribute = SaleAttributes.END_DATE;
        } else if (attribute.equalsIgnoreCase("start date")) {
            this.attribute = SaleAttributes.START_DATE;
        } else if (attribute.equalsIgnoreCase("percentage")) {
            this.attribute = SaleAttributes.PERCENTAGE;
        }
        this.updatedInfo = updatedInfo;
    }

   /* public void updateAttributeWithUpdatedInfo() throws ParseException {
        Sale sale = (Sale) object;
        if (attribute.equals(SaleAttributes.END_DATE)) {
            sale.setEnd(updatedInfo);
        } else if (attribute.equals(SaleAttributes.START_DATE)) {
            sale.setStart(updatedInfo);
        } else if (attribute.equals(SaleAttributes.PERCENTAGE)) {
            sale.setPercentage(updatedInfo);
        }
    }*/

    public String toStringChangeSale() {
        return "Salesman username: " + accountUsername + "\n" +
                "Attribute to change: " + attribute.name().toLowerCase() + "\n"
                + "New attribute value: " + updatedInfo
                + "Confirmation State: " + confirmation.name() + "\n";
    }

}
