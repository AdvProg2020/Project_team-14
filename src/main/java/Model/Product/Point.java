package Model.Product;

import Model.RandomString;

import java.io.Serializable;

import static Model.Storage.*;

public class Point implements Serializable {
    private String pointID;
    private String username;
    private String productID;
    private int point;
    private static final long serialVersionUID = 6529685098267757690L;

    public Point(String username, String productID, int point) {
        this.username = username;
        this.point = point;
        this.productID = productID;
        allPoints.add(this);
        this.pointID = createID();
    }

    public static boolean isTherePointWithID(String pointID) {
        return getPointByID(pointID) != null;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public static Point getPointByID(String pointID) {
        for (Point point : allPoints) {
            if (point.pointID.equals(pointID)) {
                return point;
            }
        }
        return null;
    }

    public static boolean isTherePointForProduct(String productID) {
        return getAveragePointForProduct(productID) != -1;
    }

    public static double getAveragePointForProduct(String productID) {
        if (getNumberOfPeopleVotedForProduct(productID) == 0) {
            return -1;
        }
        int sum = 0;
        for (Point point : allPoints) {
            if (point.productID.equals(productID)) {
                sum += point.point;
            }
        }
        return (double) sum / (double) getNumberOfPeopleVotedForProduct(productID);
    }

    public static int getNumberOfPeopleVotedForProduct(String productID) {
        int numberOfPeopleVoted = 0;
        for (Point point : allPoints) {
            if (point.productID.equals(productID)) {
                numberOfPeopleVoted++;
            }
        }
        return numberOfPeopleVoted;
    }

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public String createID() {
        return RandomString.createID("Point");
    }

    public String getPointID() {
        return this.pointID;
    }

    public void setPointID(String pointID) {
        this.pointID = pointID;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public int getPoint() {
        return point;
    }
}
