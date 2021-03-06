package Model.Request;

import Model.Off.Sale;
import Model.Request.Enum.*;

import java.io.Serializable;
import java.text.ParseException;

public class ChangeSaleRequest extends Request implements Serializable {
    SaleAttributes attribute;
    String updatedInfo;
    private static final long serialVersionUID = 6529685098267757690L;

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

    public void updateAttributeWithUpdatedInfo() throws ParseException {
        Sale sale = (Sale) object;
        assert sale != null;
        if (attribute.equals(SaleAttributes.END_DATE)) {
            sale.setEnd(updatedInfo);
        } else if (attribute.equals(SaleAttributes.START_DATE)) {
            sale.setStart(updatedInfo);
        } else if (attribute.equals(SaleAttributes.PERCENTAGE)) {
            sale.setPercentage(updatedInfo);
        }
    }

    public SaleAttributes getAttribute() {
        return attribute;
    }

    public void setAttribute(SaleAttributes attribute) {
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

    public String toStringChangeSale() {
        return "Salesman username: " + accountUsername + "\n" +
                "Attribute to change: " + attribute.name().toLowerCase() + "\n"
                + "New attribute value: " + updatedInfo
                + "Confirmation State: " + confirmation.name() + "\n";
    }

}
