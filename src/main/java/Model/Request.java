package Model;

public class Request {
    private String requestID;
    private String type;
    private String itemID;
    private String itemAttribute;
    private String updatedInfo;

    public Request(String type, String itemID) {
        this.type = type;
        this.itemID = itemID;
        requestID = RandomString.createID("Request");
        Storage.allRequests.add(this);
    }

    public Request(String type, String itemID, String itemAttribute, String updatedInfo) {
        this.type = type;
        this.itemID = itemID;
        this.itemAttribute = itemAttribute;
        this.updatedInfo = updatedInfo;
        requestID = RandomString.createID("Request");
        Storage.allRequests.add(this);
    }
}
