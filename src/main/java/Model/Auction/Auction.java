package Model.Auction;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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
    private static final long serialVersionUID = 6529685098267757690L;

    public Auction(String salesmanID, String productID, String startingDate, String endingDate) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        try {
            this.startingDate = formatter.parse(startingDate);
            this.endingDate = formatter.parse(endingDate);
        } catch (ParseException e) {
            //e.printStackTrace();
        }
        this.salesmanID = salesmanID;
        this.productID = productID;
        this.customerID = null;
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

    public static void setAllAuctions(ArrayList<Auction> allAuctions) {
        Auction.allAuctions = allAuctions;
    }

    public String getSalesmanID() {
        return salesmanID;
    }

    public void setSalesmanID(String salesmanID) {
        this.salesmanID = salesmanID;
    }

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public int getHighestPrice() {
        return highestPrice;
    }

    public void setHighestPrice(int highestPrice) {
        this.highestPrice = highestPrice;
    }

    public String getCustomerID() {
        return customerID;
    }

    public void setCustomerID(String customerID) {
        this.customerID = customerID;
    }

    public Date getStartingDate() {
        return startingDate;
    }

    public void setStartingDate(Date startingDate) {
        this.startingDate = startingDate;
    }

    public Date getEndingDate() {
        return endingDate;
    }

    public void setEndingDate(Date endingDate) {
        this.endingDate = endingDate;
    }

    public void setAuctionChat(AuctionChat auctionChat) {
        this.auctionChat = auctionChat;
    }

    public String getChatStringFormatted() {
        return auctionChat.getChatStringFormatted();
    }
}
