package GUI.Cart;

public class Cart {
    String salesman, productId, productName;
    int price, finalPrice, count;
    String isOnSale, saleId;

    public Cart(String salesman, String productId, String productName, int price, int finalPrice, int count, String isOnSale, String onSale) {
        this.salesman = salesman;
        this.productId = productId;
        this.productName = productName;
        this.price = price;
        this.finalPrice = finalPrice;
        this.count = count;
        this.isOnSale = isOnSale;
        saleId = onSale;
    }

    public String getSaleId() {
        return saleId;
    }

    public void setSaleId(String saleId) {
        this.saleId = saleId;
    }

    public String getSalesman() {
        return salesman;
    }

    public void setSalesman(String salesman) {
        this.salesman = salesman;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getFinalPrice() {
        return finalPrice;
    }

    public void setFinalPrice(int finalPrice) {
        this.finalPrice = finalPrice;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getIsOnSale() {
        return isOnSale;
    }

    public void setIsOnSale(String isOnSale) {
        this.isOnSale = isOnSale;
    }

    @Override
    public String toString() {
        return "Cart{" +
                "salesman='" + salesman + '\'' +
                ", productId='" + productId + '\'' +
                ", productName='" + productName + '\'' +
                ", price=" + price +
                ", finalPrice=" + finalPrice +
                ", count=" + count +
                ", isOnSale='" + isOnSale + '\'' +
                ", saleId='" + saleId + '\'' +
                '}';
    }
}
