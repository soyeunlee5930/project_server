package project.project.model;

public class Stock {
    private int id;
    private int productOptionsId;
    private int quantity;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProductOptionsId() {
        return productOptionsId;
    }

    public void setProductOptionsId(int productOptionsId) {
        this.productOptionsId = productOptionsId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
