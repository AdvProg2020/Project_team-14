package Model.Request;

import Model.Account.Salesman;
import Model.Confirmation;
import Model.Off.Off;
import Model.Off.Sale;
import Model.Product.Comment;
import Model.Product.Product;

import Model.Request.Enum.RequestType;
import Model.Storage;

import java.io.Serializable;
import java.text.ParseException;
import java.util.ArrayList;

import static Model.RandomString.createID;

public class Request implements Serializable {
    protected String requestID;
    protected String accountUsername;
    protected Object object;
    protected RequestType requestType;
    protected Confirmation confirmation;

    //request for making/adding a product, note for changing a sale we shall use ChangeProductRequest class instead

    public Request(String accountUsername, Product product, String type) {
        this.requestID = createID("Request");
        this.accountUsername = accountUsername;
        this.object = product;
        if (type.equalsIgnoreCase(String.valueOf(RequestType.CHANGE_PRODUCT))) {
            requestType = RequestType.CHANGE_PRODUCT;
        } else if (type.equalsIgnoreCase(String.valueOf(RequestType.DELETE_PRODUCT))) {
            requestType = RequestType.DELETE_PRODUCT;
        } else if (type.equalsIgnoreCase(String.valueOf(RequestType.ADD_NEW_PRODUCT))) {
            requestType = RequestType.ADD_NEW_PRODUCT;
        } else if (type.equalsIgnoreCase(String.valueOf(RequestType.ADD_TO_PRODUCT))) {
            requestType = RequestType.ADD_TO_PRODUCT;
        }
        Storage.getAllRequests().add(this);
        this.confirmation = Confirmation.CHECKING;
    }

    //request for making/adding a sale, note for changing a sale we shall use ChangeSaleRequest class instead

    public Request(String accountUsername, Sale sale, String type) {
        this.requestID = createID("Request");
        this.accountUsername = accountUsername;
        this.object = sale;
        if (type.equalsIgnoreCase(String.valueOf(RequestType.CHANGE_SALE))) {
            requestType = RequestType.CHANGE_SALE;
        } else if (type.equalsIgnoreCase(String.valueOf(RequestType.DELETE_SALE))) {
            requestType = RequestType.DELETE_SALE;
        } else if (type.equalsIgnoreCase(String.valueOf(RequestType.ADD_NEW_SALE))) {
            requestType = RequestType.ADD_NEW_SALE;
        }
        Storage.getAllRequests().add(this);
        this.confirmation = Confirmation.CHECKING;
    }

    //request for registering a salesman

    public Request(String accountUsername) {
        this.requestID = createID("Request");
        this.accountUsername = accountUsername;
        this.object = null;
        this.requestType = RequestType.REGISTER_SALESMAN;
        Storage.getAllRequests().add(this);
        this.confirmation = Confirmation.CHECKING;
    }

    //request for adding a comment

    public Request(Comment comment) {
        this.requestID = createID("Request");
        this.accountUsername = comment.getSenderUsername();
        this.object = comment;
        this.requestType = RequestType.COMMENT_CONFIRMATION;
        Storage.getAllRequests().add(this);
        this.confirmation = Confirmation.CHECKING;
    }

    public static ArrayList<Request> getCheckingRequests() {
        ArrayList<Request> result = new ArrayList<>();
        for (Request request : Storage.getAllRequests()) {
            if (request.confirmation.equals(Confirmation.CHECKING)) {
                result.add(request);
            }
        }
        return result;
    }

    public Object getObject() {
        return object;
    }

    public Confirmation getConfirmation() {
        return confirmation;
    }

    public String getRequestID() {
        return requestID;
    }

    public RequestType getRequestType() {
        return requestType;
    }

    public String getAccountUsername() {
        return accountUsername;
    }

    public void setAccountUsername(String accountUsername) {
        this.accountUsername = accountUsername;
    }

    //it accepts the request and makes the needed changes in objects as desired

    public void accept() throws ParseException {
        if (this.requestType.equals(RequestType.CHANGE_SALE)) {
            this.confirmation = Confirmation.ACCEPTED;
            if (this instanceof ChangeSaleRequest) {
                ((ChangeSaleRequest) this).updateAttributeWithUpdatedInfo();
            }
        } else if (this.requestType.equals(RequestType.DELETE_SALE)) {
            Sale sale = (Sale) object;
            this.confirmation = Confirmation.ACCEPTED;
            sale.setConfirmationState(Confirmation.DENIED);
        } else if (this.requestType.equals(RequestType.ADD_NEW_SALE)) {
            this.confirmation = Confirmation.ACCEPTED;
            Sale sale = (Sale) object;
            sale.setConfirmationState(Confirmation.ACCEPTED);
        } else if (this.requestType.equals(RequestType.DELETE_PRODUCT)) {
            Product product = (Product) object;
            this.confirmation = Confirmation.ACCEPTED;
            product.deleteForSalesman(accountUsername);
        } else if (this.requestType.equals(RequestType.ADD_NEW_PRODUCT)) {
            this.confirmation = Confirmation.ACCEPTED;
            Product product = (Product) object;
            product.setConfirmationState(accountUsername, Confirmation.ACCEPTED);
        } else if (this.requestType.equals(RequestType.CHANGE_PRODUCT)) {
            this.confirmation = Confirmation.ACCEPTED;
            if (this instanceof ChangeProductRequest) {
                ((ChangeProductRequest) this).updateAttributeWithUpdatedInfo();
            }
        } else if (this.requestType.equals(RequestType.REGISTER_SALESMAN)) {
            this.confirmation = Confirmation.ACCEPTED;
            Salesman salesman = (Salesman) Storage.getAccountWithUsername(accountUsername);
            assert salesman != null;
            salesman.setConfirmationState(Confirmation.ACCEPTED);
        } else if (this.requestType.equals(RequestType.COMMENT_CONFIRMATION)) {
            this.confirmation = Confirmation.ACCEPTED;
            Comment comment = (Comment) object;
            comment.setConfirmationState(Confirmation.ACCEPTED);
        } else if (this.requestType.equals(RequestType.ADD_TO_PRODUCT)) {
            this.confirmation = Confirmation.ACCEPTED;
            Product product = (Product) object;
            product.setConfirmationState(accountUsername, Confirmation.ACCEPTED);
        }
    }

    //as a change is denied so no change will occur
    //when adding is denied we set the confirmation state of the object to DENIED
    //when deleting is denied mean no change should happen
    //when registering new salesman is denied it means that we set the confirmation state DENIED


    public void decline() {
        if (this.requestType.equals(RequestType.CHANGE_SALE)) {
            this.confirmation = Confirmation.DENIED;
        } else if (this.requestType.equals(RequestType.DELETE_SALE)) {
            this.confirmation = Confirmation.DENIED;
        } else if (this.requestType.equals(RequestType.ADD_NEW_SALE)) {
            this.confirmation = Confirmation.DENIED;
            Sale sale = (Sale) object;
            sale.setConfirmationState(Confirmation.DENIED);
        } else if (this.requestType.equals(RequestType.DELETE_PRODUCT)) {
            this.confirmation = Confirmation.DENIED;
        } else if (this.requestType.equals(RequestType.ADD_NEW_PRODUCT)) {
            this.confirmation = Confirmation.DENIED;
            Product product = (Product) object;
            product.setConfirmationState(accountUsername, Confirmation.DENIED);
        } else if (this.requestType.equals(RequestType.CHANGE_PRODUCT)) {
            this.confirmation = Confirmation.DENIED;
        } else if (this.requestType.equals(RequestType.REGISTER_SALESMAN)) {
            this.confirmation = Confirmation.DENIED;
            Salesman salesman = (Salesman) Storage.getAccountWithUsername(accountUsername);
            assert salesman != null;
            salesman.setConfirmationState(Confirmation.DENIED);
            this.accountUsername = "deleted account";
        } else if (this.requestType.equals(RequestType.COMMENT_CONFIRMATION)) {
            this.confirmation = Confirmation.DENIED;
            Comment comment = (Comment) object;
            comment.setConfirmationState(Confirmation.DENIED);
        } else if (this.requestType.equals(RequestType.ADD_TO_PRODUCT)) {
            this.confirmation = Confirmation.ACCEPTED;
            Product product = (Product) object;
            product.setConfirmationState(accountUsername, Confirmation.DENIED);
        }
    }

    public String toStringForBoss() {
        return "Request Type: " + this.requestType + " Request ID: " + this.requestID + " Username: " + accountUsername;
    }

    private String toStringRegisterSalesman() {
        if (!this.accountUsername.equals("deleted account")) {
            Salesman salesman = (Salesman) Storage.getAccountWithUsername(accountUsername);
            String result = "";
            result += "General information of salesman: " + "\n";
            assert salesman != null;
            result += salesman.toStringForRequest();
            return result;
        } else {
            String result = "";
            result += "General information of salesman: " + "\n";
            result += "this account has been deleted";
            return result;
        }
    }

    private String toStringAddNewProduct() {
        Product product = (Product) object;
        Salesman salesman = (Salesman) Storage.getAccountWithUsername(accountUsername);
        String result = "";
        result += "General information of salesman: " + "\n";
        assert salesman != null;
        result += salesman.toStringForRequest() + "\n";
        assert product != null;
        return result + "General information of product: " + "\n" + product.toStringForBoss() +
                "Confirmation State: " + confirmation.name();
    }

    private String toStringAddToProduct() {
        Product product = (Product) object;
        Salesman salesman = (Salesman) Storage.getAccountWithUsername(accountUsername);
        String result = "";
        result += "General information of salesman: " + "\n";
        assert salesman != null;
        result += salesman.toStringForRequest() + "\n";
        assert product != null;
        return result + "General information of product: " + "\n" + product.toStringForAddToProduct(accountUsername) +
                "\nConfirmation State: " + confirmation.name();
    }

    private String toStringAddNewSale() {
        Sale sale = (Sale) object;
        return "Salesman username: " + accountUsername + "\n" + "General information of sale: " + "\n"
                + ((Off) sale).toString() + "\n" + "Confirmation State: " + confirmation.name() + "\n";
    }

    private String toStringDeleteSale() {
        Sale sale = (Sale) object;
        return "Salesman username: " + accountUsername + "\n" + "General information of sale: " + "\n"
                + sale.toString() + "\n";
    }

    private String toStringDeleteProduct() {
        Product product = (Product) object;
        return "Salesman username: " + accountUsername + "\n" + "General information of product: " + "\n"
                + product.toStringForBoss();
    }

    private String toStringCommentConfirmation() {
        Comment comment = (Comment) object;
        return comment.toStringForChecking();
    }


    public String toString() {
        String result = "Type: " + this.requestType.name() + "\n";
        result += "Confirmation State: " + this.getConfirmation().name() + "\n";
        if (requestType.equals(RequestType.CHANGE_PRODUCT)) {
            ChangeProductRequest changeProductRequest = (ChangeProductRequest) this;
            return result + changeProductRequest.toStringChangeProduct();
        } else if (requestType.equals(RequestType.ADD_NEW_PRODUCT)) {
            return result + toStringAddNewProduct();
        } else if (requestType.equals(RequestType.ADD_NEW_SALE)) {
            return result + toStringAddNewSale();
        } else if (requestType.equals(RequestType.CHANGE_SALE)) {
            ChangeSaleRequest changeSaleRequest = (ChangeSaleRequest) this;
            return result + changeSaleRequest.toStringChangeSale();
        } else if (requestType.equals(RequestType.DELETE_PRODUCT)) {
            return result + toStringDeleteProduct();
        } else if (requestType.equals(RequestType.DELETE_SALE)) {
            return result + toStringDeleteSale();
        } else if (requestType.equals(RequestType.REGISTER_SALESMAN)) {
            return result + toStringRegisterSalesman();
        } else if (requestType.equals(RequestType.COMMENT_CONFIRMATION)) {
            return result + toStringCommentConfirmation();
        } else if (requestType.equals(RequestType.ADD_TO_PRODUCT)) {
            return result + toStringAddToProduct();
        }
        return null;
    }

    public String getObjectID() {
        if (object instanceof Product) {
            return ((Product) object).getProductID();
        } else if (object instanceof Comment) {
            return ((Comment) object).getCommentID();
        } else if (object instanceof Sale) {
            return ((Sale) object).getSaleID();
        } else {
            return null;
        }
    }

}
