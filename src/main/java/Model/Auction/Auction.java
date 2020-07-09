package Model.Auction;

import java.util.ArrayList;
import java.util.Date;

public class Auction {
    private static ArrayList<Auction> allAuctions = new ArrayList<>();
    private String salesmanID;
    private String productID;
    private int highestPrice;
    private String customerID;
    private Date startingDate;
    private Date endingDate;
    private AuctionChat auctionChat;

    public Auction(String salesmanID, String productID, Date startingDate, Date endingDate) {
        this.salesmanID = salesmanID;
        this.productID = productID;
        this.startingDate = startingDate;
        this.endingDate = endingDate;
        allAuctions.add(this);
        auctionChat = new AuctionChat(this);
    }

    public void updatePrice(String customerID, int highestPrice) {
        this.customerID = customerID;
        this.highestPrice = highestPrice;
    }

    public static ArrayList<Auction> getAllAuctions() {
        return allAuctions;
    }

    public static ArrayList<Auction> getAllAuctionsOfSalesmanWithID(String salesmanID) {
        ArrayList<Auction> buffer = new ArrayList<>();
        for (Auction auction : allAuctions) {
            if (auction.salesmanID.equals(salesmanID)) {
                buffer.add(auction);
            }
        }
        return buffer;
    }

    public static boolean isProductOnAuctionBySalesman(String salesmanID, String productID) {
        for (Auction auction : allAuctions) {
            if (auction.salesmanID.equals(salesmanID) && auction.productID.equals(productID)) {
                return true;
            }
        }
        return false;
    }

    public AuctionChat getAuctionChat() {
        return auctionChat;
    }

    public boolean isAuctionStillValid() {
        //not complete
        return false;
    }

    public String getChatStringFormatted() {
        return auctionChat.getChatStringFormatted();
    }
}
