package Model.Request;

import Model.Account.Salesman;
import Model.Confirmation;
import Model.Off.Sale;
import Model.Product.Product;
import Model.RandomString;
import Model.Storage;

import java.io.Serializable;
import java.text.ParseException;
import java.util.ArrayList;

enum RequestType {
    REGISTER_SALESMAN, ADD_NEW_PRODUCT, CHANGE_PRODUCT, ADD_NEW_SALE, CHANGE_SALE, DELETE_PRODUCT, DELETE_SALE;
}

public class Request extends RandomString implements Serializable {
    protected String requestID;
    protected String salesmanID;
    protected Object object;
    protected RequestType requestType;
    protected Confirmation confirmation;

    //request for making/adding a product, note for changing a sale we shall use ChangeProductRequest class instead

    public Request(String salesmanID, Product product, String type) {
        this.requestID = createID("Request");
        this.salesmanID = salesmanID;
        this.object = product;
        if (type.equalsIgnoreCase(String.valueOf(RequestType.CHANGE_PRODUCT))) {
            requestType = RequestType.CHANGE_PRODUCT;
        } else if (type.equalsIgnoreCase(String.valueOf(RequestType.DELETE_PRODUCT))) {
            requestType = RequestType.DELETE_PRODUCT;
        } else if (type.equalsIgnoreCase(String.valueOf(RequestType.ADD_NEW_PRODUCT))) {
            requestType = RequestType.ADD_NEW_PRODUCT;
        }
        Storage.allRequest.add(this);
        this.confirmation = Confirmation.CHECKING;
    }

    //request for making/adding a sale, note for changing a sale we shall us ChangeSaleRequest class instead

    public Request(String salesmanID, Sale sale, String type) {
        this.requestID = createID("Request");
        this.salesmanID = salesmanID;
        this.object = sale;
        if (type.equalsIgnoreCase(String.valueOf(RequestType.CHANGE_SALE))) {
            requestType = RequestType.CHANGE_SALE;
        } else if (type.equalsIgnoreCase(String.valueOf(RequestType.DELETE_SALE))) {
            requestType = RequestType.DELETE_SALE;
        } else if (type.equalsIgnoreCase(String.valueOf(RequestType.ADD_NEW_SALE))) {
            requestType = RequestType.ADD_NEW_SALE;
        }
        Storage.allRequest.add(this);
        this.confirmation = Confirmation.CHECKING;
    }

    //request for registering a salesman

    public Request(String salesmanID) {
        this.requestID = createID("Request");
        this.salesmanID = salesmanID;
        this.object = null;
        this.requestType = RequestType.REGISTER_SALESMAN;
        Storage.allRequest.add(this);
        this.confirmation = Confirmation.CHECKING;
    }

    public ArrayList<Request> getCheckingRequests() {
        ArrayList<Request> result = new ArrayList<>();
        for (Request request : Storage.allRequest) {
            if (request.confirmation.equals(Confirmation.CHECKING)) {
                result.add(request);
            }
        }
        return result;
    }

    //it accepts the request and makes the needed changes in objects as desired

    public void accept() throws ParseException {
        if (this.requestType.equals(RequestType.CHANGE_SALE)) {
            this.confirmation = Confirmation.ACCEPTED;
            if (this instanceof ChangeSaleRequest) {
                ((ChangeSaleRequest) this).updateAttributeWithUpdateInfo();
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
            product.deleteForSalesman(salesmanID);
        } else if (this.requestType.equals(RequestType.ADD_NEW_PRODUCT)) {
            this.confirmation = Confirmation.ACCEPTED;
            Product product = (Product) object;
            product.setConfirmationState(salesmanID, Confirmation.ACCEPTED);
        } else if (this.requestType.equals(RequestType.CHANGE_PRODUCT)) {
            this.confirmation = Confirmation.ACCEPTED;
            if (this instanceof ChangeProductRequest) {
                ((ChangeProductRequest) this).updateAttributeWithUpdateInfo();
            }
        } else if (this.requestType.equals(RequestType.REGISTER_SALESMAN)) {
            this.confirmation = Confirmation.ACCEPTED;
            Salesman salesman = (Salesman) Storage.getAccountWithUsername(salesmanID);
            assert salesman != null;
            salesman.setConfirmationState(Confirmation.ACCEPTED);
        }

    }

    //as a change is denied so no change will occur
    //when adding is denied we set the confirmation state of the object to DENIED
    //when deleting is denied mean no change should happen
    //when registering new salesman is denied it means that we set the confirmation state DENIED


    public void deny() {

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
            product.setConfirmationState(salesmanID, Confirmation.DENIED);
        } else if (this.requestType.equals(RequestType.CHANGE_PRODUCT)) {
            this.confirmation = Confirmation.DENIED;
        } else if (this.requestType.equals(RequestType.REGISTER_SALESMAN)) {
            this.confirmation = Confirmation.DENIED;
            Salesman salesman = (Salesman) Storage.getAccountWithUsername(salesmanID);
            assert salesman != null;
            salesman.setConfirmationState(Confirmation.DENIED);
        }

    }
}
