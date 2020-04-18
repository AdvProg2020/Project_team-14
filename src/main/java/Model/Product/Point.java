package Model.Product;

import Model.RandomString;

import java.io.Serializable;
import java.util.ArrayList;

public class Point extends RandomString implements Serializable {
    private static transient ArrayList<Point> allPoints = new ArrayList<>();
    private String pointID;
    private String username;
    private String productID;
    private int point;

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

    public static float getAveragePointForProduct(String productID) {
        int sum = 0;
        int numberOfPeopleVoted = 0;
        for (Point point : allPoints) {
            if (point.productID.equals(productID)) {
                sum += point.point;
                numberOfPeopleVoted++;
            }
        }
        if (numberOfPeopleVoted != 0) {
            return (float) sum / (float) numberOfPeopleVoted;
        } else {
            return -1;
        }
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

    public String createID() {
        return RandomString.createID("Point");
    }


}
